package me.qunqun.doctor.entity.vo;

import lombok.Data;

import java.util.List;

@Data
public class CheckItemReportVO {
    private Integer id;
    private Integer checkItemId;
    private String name;
    private String remark;
    private String content;
    private String meaning;
    private String review;
    private List<CheckItemDetailedReportVO> checkItemDetailedReports;

}
