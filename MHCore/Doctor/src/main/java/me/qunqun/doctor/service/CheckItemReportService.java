package me.qunqun.doctor.service;

import lombok.extern.slf4j.Slf4j;
import me.qunqun.doctor.annotation.RedisCacheEvict;
import me.qunqun.doctor.annotation.RedisCacheable;
import me.qunqun.doctor.entity.dto.EditReportDTO;
import me.qunqun.doctor.repo.*;
import me.qunqun.shared.entity.po.*;
import me.qunqun.doctor.entity.vo.CheckItemDetailedReportVO;
import me.qunqun.doctor.entity.vo.CheckItemReportVO;
import me.qunqun.doctor.entity.vo.OrderVO;
import me.qunqun.doctor.entity.reps.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Slf4j
@Service
public class CheckItemReportService {
    @Autowired
    private CheckItemReportRepository checkItemReportRepository;
    @Autowired
    private CheckItemDetailedReportRepository checkItemDetailedReportRepository;
    @Autowired
    private PackageRepository packageRepository;
    @Autowired
    private OverallResultRepository overallResultRepository;
    @Autowired
    private OrderRepository orderRepository;


    @Transactional
    @RedisCacheable(keyPrefix = "checkItemReports")
    public Result<List<CheckItemReportVO>> getCheckItemReports(OrderVO orderVO) {
        try {
            List<CheckItemDetailedReport> checkItemDetailedReports = checkItemDetailedReportRepository.findByOrderId(orderVO.getOrderId());
            log.info("checkItemDetailedReports: {}", checkItemDetailedReports);
            log.info("checkItemDetailedReports.size(): {}", checkItemDetailedReports.size());
            log.info("checkItemDetailedReports.isEmpty(): {}", checkItemDetailedReports.isEmpty());
            if (checkItemDetailedReports.isEmpty()) {
                // 初始化检查项目报告
                Integer packageId = orderVO.getPackageId();
                Set<CheckItem> checkItemSet = Objects.requireNonNull(packageRepository.findById(packageId).orElse(null)).getCheckItems();
                if (checkItemSet == null) {
                    return Result.error("检查项目为空，查询失败");
                }
                Order order = orderRepository.findById(orderVO.getOrderId()).orElse(null);
                for (CheckItem checkItem : checkItemSet) {
                    CheckItemReport checkItemReport = new CheckItemReport();
                    checkItemReport.setCheckItem(checkItem);
                    checkItemReport.setOrder(order);
                    checkItemReport.setReview(null);
                    checkItemReportRepository.save(checkItemReport);
                    for (CheckItemDetailed checkItemDetailed : checkItem.getCheckItemDetaileds()) {
                        CheckItemDetailedReport checkItemDetailedReport = new CheckItemDetailedReport();
                        checkItemDetailedReport.setCheckItemReport(checkItemReport);
                        checkItemDetailedReport.setOrder(order);
                        checkItemDetailedReport.setCheckItemDetailed(checkItemDetailed);
                        checkItemDetailedReport.setValue(null);
                        checkItemDetailedReport.setError(0);
                        checkItemDetailedReportRepository.save(checkItemDetailedReport);
                    }
                }
                checkItemDetailedReports = checkItemDetailedReportRepository.findByOrderId(orderVO.getOrderId());
            }
            List<CheckItemReportVO> checkItemReportVOs = convertToCheckItemReportVOs(checkItemDetailedReports);
            return Result.success(checkItemReportVOs);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private List<CheckItemReportVO> convertToCheckItemReportVOs(List<CheckItemDetailedReport> checkItemDetailedReports) {
        checkItemDetailedReports.sort(Comparator.comparingInt(o -> o.getCheckItemReport().getCheckItem().getId()));
        List<CheckItemReportVO> checkItemReportVOs = new ArrayList<>();
        Set<CheckItem> checkItemSet = new HashSet<>();
        for (CheckItemDetailedReport checkItemDetailedReport : checkItemDetailedReports) {
            checkItemSet.add(checkItemDetailedReport.getCheckItemReport().getCheckItem());
        }
        for (CheckItem checkItem : checkItemSet) {
            CheckItemReportVO checkItemReportVO = new CheckItemReportVO();
            checkItemReportVO.setCheckItemId(checkItem.getId());
            checkItemReportVO.setName(checkItem.getName());
            checkItemReportVO.setRemark(checkItem.getRemarks());
            checkItemReportVO.setContent(checkItem.getContent());
            checkItemReportVO.setMeaning(checkItem.getMeaning());
            List<CheckItemDetailedReportVO> checkItemDetailedReportVOs = new ArrayList<>();
            for (CheckItemDetailedReport checkItemDetailedReport : checkItemDetailedReports) {
                if (checkItemDetailedReport.getCheckItemReport().getCheckItem().getId().equals(checkItem.getId())) {
                    CheckItemDetailedReportVO checkItemDetailedReportVO = new CheckItemDetailedReportVO().fromCheckItemDetailedReport(checkItemDetailedReport);
                    checkItemDetailedReportVOs.add(checkItemDetailedReportVO);
                    checkItemReportVO.setReview(checkItemDetailedReport.getCheckItemReport().getReview());
                    checkItemReportVO.setId(checkItemDetailedReport.getCheckItemReport().getId());
                }
            }
            checkItemReportVO.setCheckItemDetailedReports(checkItemDetailedReportVOs);
            checkItemReportVOs.add(checkItemReportVO);
        }

        return checkItemReportVOs;
    }

    @Transactional
    @RedisCacheEvict(keyPrefix = "checkItemReports")
    public Result<String> editReport(EditReportDTO editReportDTO) {
        try {
            List<CheckItemReportVO> checkItemReportVOs = editReportDTO.getCheckItemReports();
            List<OverallResult> overallResults = editReportDTO.getOverallResults();
            Order order = orderRepository.findById(editReportDTO.getOrderId()).orElse(null);
            if (order == null) {
                throw new RuntimeException("检查单不存在，保存失败");
            }
            // 保存检查项目报告
            for (CheckItemReportVO checkItemReportVO : checkItemReportVOs) {
                for (CheckItemDetailedReportVO checkItemDetailedReportVO : checkItemReportVO.getCheckItemDetailedReports()) {
                    CheckItemDetailedReport checkItemDetailedReport = checkItemDetailedReportRepository.findById(checkItemDetailedReportVO.getId()).orElse(null);
                    if (checkItemDetailedReport == null) {
                        throw new RuntimeException("检查项目详细报告不存在，编辑失败");
                    }
                    checkItemDetailedReport.setValue(checkItemDetailedReportVO.getValue());
                    checkItemDetailedReport.setError(checkItemDetailedReportVO.getErrorType());
                    checkItemDetailedReportRepository.save(checkItemDetailedReport);
                }
                CheckItemReport checkItemReport = checkItemReportRepository.findById(checkItemReportVO.getId()).orElse(null);
                if (checkItemReport == null) {
                    throw new RuntimeException("检查项目报告不存在，编辑失败");
                }
                checkItemReport.setReview(checkItemReportVO.getReview());
                checkItemReportRepository.save(checkItemReport);
            }
            // 删除原有综合结果
            overallResultRepository.deleteByOrderId(order.getId());
            // 保存综合结果
            for (OverallResult overallResult : overallResults) {
                overallResult.setOrder(order);
                if (overallResult.getId() == null) {
                    overallResultRepository.save(overallResult);
                }else {
                    OverallResult overallResultO = overallResultRepository.findById(overallResult.getId()).orElse(null);
                    if (overallResultO == null) {
                        overallResultRepository.save(overallResult);
                    } else {
                        overallResultO.setTitle(overallResult.getTitle());
                        overallResultO.setContent(overallResult.getContent());
                        overallResultRepository.save(overallResultO);
                    }
                }
            }
            return Result.success("编辑成功");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Transactional
    @RedisCacheEvict(keyPrefix = "checkItemReports")
    public Result<String> saveReport(EditReportDTO editReportDTO) {
        try {
            Result<String> res = editReport(editReportDTO);
            if (res.getCode() != 200) {
                throw new RuntimeException(res.getMessage());
            }else {
                Order order = orderRepository.findById(editReportDTO.getOrderId()).orElse(null);
                if (order == null) {
                    throw new RuntimeException("检查单不存在，保存失败");
                }
                order.setState(2);
                orderRepository.save(order);
                return Result.success("保存成功");
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
