package me.qunqun.doctor.service;

import me.qunqun.shared.entity.po.CheckItemDetailedReport;
import me.qunqun.shared.entity.po.CheckItem;
import me.qunqun.doctor.entity.vo.CheckItemDetailedReportVO;
import me.qunqun.doctor.entity.vo.CheckItemReportVO;
import me.qunqun.doctor.entity.vo.OrderVO;
import me.qunqun.doctor.repo.CheckItemDetailedReportRepository;
import me.qunqun.doctor.repo.CheckItemDetailedRepository;
import me.qunqun.doctor.repo.CheckItemReportRepository;
import me.qunqun.doctor.repo.CheckItemRepository;
import me.qunqun.doctor.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class CheckItemReportService {
    @Autowired
    private CheckItemReportRepository checkItemReportRepository;
    @Autowired
    private CheckItemDetailedReportRepository checkItemDetailedReportRepository;
    @Autowired
    private CheckItemRepository checkItemRepository;
    @Autowired
    private CheckItemDetailedRepository checkItemDetailedRepository;

    public Result<List<CheckItemReportVO>> getCheckItemReports(OrderVO orderVO) {
        try {
            List<CheckItemDetailedReport> checkItemDetailedReports = checkItemDetailedReportRepository.findByOrderId(orderVO.getOrderId());
            List<CheckItemReportVO> checkItemReportVOs = convertToCheckItemReportVOs(checkItemDetailedReports);
            return Result.success(checkItemReportVOs);
        } catch (Exception e) {
            return Result.error("获取检查项目报告失败");
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
                }
            }
            checkItemReportVO.setCheckItemDetailedReports(checkItemDetailedReportVOs);
            checkItemReportVOs.add(checkItemReportVO);
        }

        return checkItemReportVOs;
    }
}
