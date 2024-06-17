package me.qunqun.doctor.service;

import lombok.extern.slf4j.Slf4j;
import me.qunqun.shared.entity.po.OverallResult;
import me.qunqun.doctor.repo.OverallResultRepository;
import me.qunqun.doctor.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class OverallResultService {

    @Autowired
    private OverallResultRepository overallResultRepository;

    public Result<List<OverallResult>> getOverallResult(Integer orderId) {
        try {
            List<OverallResult> overallResults = overallResultRepository.findByOrderId(orderId);
            return Result.success(overallResults);
        } catch (Exception e) {
            log.error("获取体检评价结果失败", e);
            return Result.error("获取体检评价结果失败");
        }

    }
}
