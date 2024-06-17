package me.qunqun.doctor.entity.vo;

import lombok.Data;
import me.qunqun.shared.entity.po.CheckItemDetailedReport;

@Data
public class CheckItemDetailedReportVO {
    private String detailName;
    private String value;
    private String error;
    private Integer errorType;
    private String unit;
    private Integer type;
    private Double minRange;
    private Double maxRange;
    private String normalValue;
    private String normalValueDescription;

    public CheckItemDetailedReportVO fromCheckItemDetailedReport(CheckItemDetailedReport checkItemDetailedReport) {
        this.detailName = checkItemDetailedReport.getCheckItemDetailed().getName();
        this.value = checkItemDetailedReport.getValue();
        this.error = checkItemDetailedReport.getError() == 0 ? "无异常" : "异常";
        this.errorType = checkItemDetailedReport.getError();
        this.unit = checkItemDetailedReport.getCheckItemDetailed().getUnit();
        this.type = checkItemDetailedReport.getCheckItemDetailed().getType();
        this.minRange = checkItemDetailedReport.getCheckItemDetailed().getMinRange();
        this.maxRange = checkItemDetailedReport.getCheckItemDetailed().getMaxRange();
        this.normalValue = checkItemDetailedReport.getCheckItemDetailed().getNormalValue();
        this.normalValueDescription = checkItemDetailedReport.getCheckItemDetailed().getNormalValueDescription();
        return this;
    }
}
