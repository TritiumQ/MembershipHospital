package me.qunqun.doctor.service;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import me.qunqun.doctor.annotation.RedisCacheable;
import me.qunqun.doctor.entity.vo.OverallResultVO;
import me.qunqun.shared.entity.po.OverallResult;
import me.qunqun.doctor.repo.OverallResultRepository;
import me.qunqun.doctor.entity.reps.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class OverallResultService {

    @Autowired
    private OverallResultRepository overallResultRepository;

    @RedisCacheable(keyPrefix = "checkReports")
    public Result<List<OverallResultVO>> getOverallResult(Integer orderId) {
        try {
            List<OverallResult> overallResults = overallResultRepository.findByOrderId(orderId);
            List<OverallResultVO> overallResultVOS = new ArrayList<>();
            for (OverallResult overallResult : overallResults) {
                OverallResultVO overallResultVO = new OverallResultVO();
                overallResultVO = overallResultVO.fromOverallResult(overallResult);
                overallResultVOS.add(overallResultVO);
            }
            return Result.success(overallResultVOS);
        } catch (Exception e) {
            log.error("获取体检评价结果失败", e);
            return Result.error("获取体检评价结果失败");
        }

    }
}
