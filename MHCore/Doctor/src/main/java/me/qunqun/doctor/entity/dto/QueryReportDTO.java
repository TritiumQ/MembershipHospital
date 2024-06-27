package me.qunqun.doctor.entity.dto;

import lombok.Data;
import me.qunqun.doctor.entity.vo.CheckItemReportVO;
import me.qunqun.doctor.entity.vo.OverallResultVO;

import java.util.List;

@Data
public class QueryReportDTO {
    private String orderId;
    private List<CheckItemReportVO> checkItemReports;
    private List<OverallResultVO> overallResults;

    public static QueryReportDTO fromEditReportDTO(EditReportDTO editReportDTO) {
        QueryReportDTO queryReportDTO = new QueryReportDTO();
        queryReportDTO.setOrderId(editReportDTO.getOrderId().toString());
        queryReportDTO.setCheckItemReports(editReportDTO.getCheckItemReports());
        queryReportDTO.setOverallResults(editReportDTO.getOverallResultsVO());
        return queryReportDTO;
    }
}
