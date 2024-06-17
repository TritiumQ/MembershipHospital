package me.qunqun.doctor.entity.vo;

import lombok.Data;
import me.qunqun.doctor.entity.CheckItemDetailedReport;

import java.util.List;

@Data
public class CheckItemReportVO {
    private Integer checkItemId;
    private String name;
    private String remark;
    private String content;
    private String meaning;
    private String review;
    private List<CheckItemDetailedReportVO> checkItemDetailedReports;

}
