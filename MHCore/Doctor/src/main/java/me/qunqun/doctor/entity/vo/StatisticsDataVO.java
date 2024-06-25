package me.qunqun.doctor.entity.vo;

import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class StatisticsDataVO {
    private Integer stsType;
    private LocalDate startMonth;
    private LocalDate endMonth;
    private String granularity;
    private List<FlowVO> data;
    private Integer flowNum;
}
