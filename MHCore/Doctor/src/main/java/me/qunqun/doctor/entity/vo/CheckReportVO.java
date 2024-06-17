package me.qunqun.doctor.entity.vo;

import lombok.Data;
import me.qunqun.shared.entity.po.OverallResult;

import java.util.Date;
import java.util.List;

@Data
public class CheckReportVO {
    private OrderVO order;
    private List<CheckItemReportVO> checkItemReports;
    private List<OverallResult> overallResults;

}
