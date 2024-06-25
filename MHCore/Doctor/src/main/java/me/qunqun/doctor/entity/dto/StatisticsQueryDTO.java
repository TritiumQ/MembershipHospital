package me.qunqun.doctor.entity.dto;

import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class StatisticsQueryDTO {
    private Integer stsType;
    private LocalDate start;
    private LocalDate end;
    private String granularity;
    private List<RequiredDataDTO> requiredData;
}
