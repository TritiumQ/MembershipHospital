package me.qunqun.doctor.entity.dto;

import lombok.Data;
import me.qunqun.shared.entity.po.OverallResult;
import me.qunqun.doctor.entity.vo.CheckItemReportVO;

import java.util.List;

@Data
public class EditReportDTO {
    private Integer orderId;
    private List<CheckItemReportVO> checkItemReports;
    private List<OverallResult> overallResults;
}
