package me.qunqun.doctor.entity.dto;

import lombok.Data;
import me.qunqun.doctor.entity.vo.OverallResultVO;
import me.qunqun.shared.entity.po.OverallResult;
import me.qunqun.doctor.entity.vo.CheckItemReportVO;

import java.util.List;

@Data
public class EditReportDTO {
    private Integer orderId;
    private List<CheckItemReportVO> checkItemReports;
    private List<OverallResultVO> overallResults;

    public Boolean setOverallResults(List<OverallResult> overallResults) {
        if (overallResults != null) {
            this.overallResults = overallResults.stream().map(overallResult -> {
                OverallResultVO overallResultVO = new OverallResultVO();
                overallResultVO.setId(overallResult.getId());
                overallResultVO.setTitle(overallResult.getTitle());
                overallResultVO.setContent(overallResult.getContent());
                if (overallResult.getOrder() != null)
                    overallResultVO.setOrderId(overallResult.getOrder().getId());
                return overallResultVO;
            }).toList();
            return true;
        }
        return false;
    }

    public List<OverallResult> getOverallResults() {
        if (overallResults != null) {
            return overallResults.stream().map(overallResultVO -> {
                OverallResult overallResult = new OverallResult();
                overallResult.setId(overallResultVO.getId());
                overallResult.setTitle(overallResultVO.getTitle());
                overallResult.setContent(overallResultVO.getContent());
                return overallResult;
            }).toList();
        }
        return null;
    }

    public List<OverallResultVO> getOverallResultsVO() {
        return overallResults;
    }
}
