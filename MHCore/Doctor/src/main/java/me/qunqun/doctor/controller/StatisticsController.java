package me.qunqun.doctor.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import me.qunqun.doctor.annotation.RedisCacheable;
import me.qunqun.doctor.entity.dto.RequiredDataDTO;
import me.qunqun.doctor.entity.dto.StatisticsQueryDTO;
import me.qunqun.doctor.entity.reps.Result;
import me.qunqun.doctor.entity.vo.StatisticsDataVO;
import me.qunqun.doctor.service.StatisticsService;
import me.qunqun.shared.entity.po.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Tag(name = "Statistics", description = "数据统计接口")
@Slf4j
@RestController
@RequestMapping("/api/statistics")
public class StatisticsController {
    @Autowired
    private StatisticsService statisticsService;

    @RequestMapping("/getStatistics")
    public Result<StatisticsDataVO> getDataAnalysis(@RequestBody StatisticsQueryDTO statisticsQueryDTO) {
        if (statisticsQueryDTO == null) {
            return Result.error(400, "参数错误");
        }
        Integer type = statisticsQueryDTO.getStsType();
        LocalDate start = statisticsQueryDTO.getStart();
        LocalDate end = statisticsQueryDTO.getEnd();
        try {
            if (type == 1) {
                StatisticsDataVO statisticsDataVO = statisticsService.getDataStatisticsT1(start, end, statisticsQueryDTO.getGranularity(), statisticsQueryDTO.getRequiredData());
                return Result.success(statisticsDataVO);
            } else {
                return Result.error(400, "统计请求类型错误");
            }
        } catch (Exception e) {
            log.error("数据统计异常", e);
            return Result.error(500, "服务器统计数据异常");
        }
    }



}
