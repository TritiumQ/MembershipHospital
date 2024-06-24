package me.qunqun.doctor.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import me.qunqun.doctor.entity.vo.CheckItemDetailedReportVO;
import me.qunqun.doctor.entity.vo.CheckItemReportVO;

import java.util.*;

public class ReportConverter {
    public static String convertToReport(List<CheckItemReportVO> reportList) throws Exception {
        StringBuilder reportString = new StringBuilder("{ \"体检报告\": [");

        for (CheckItemReportVO report : reportList) {
            reportString.append("{ \"").append(report.getName()).append("\": {");
            reportString.append("\"简介\": \"").append(report.getMeaning()).append("\",");
            reportString.append("\"各项指标\": [");

            for (CheckItemDetailedReportVO detail : report.getCheckItemDetailedReports()) {
                reportString.append("{ \"").append(detail.getDetailName()).append("\": {");
                reportString.append("\"正常值\": \"").append(detail.getNormalValueDescription()).append("\",");
                reportString.append("\"化验值\": \"").append(detail.getValue()).append("\",");
                reportString.append("\"单位\": \"").append(detail.getUnit()).append("\",");
                reportString.append("\"是否异常\": \"").append(detail.getError() == null ? "无异常" : detail.getError()).append("\"");
                reportString.append("}},");
            }

            // Remove the last comma
            reportString.deleteCharAt(reportString.length() - 1);

            reportString.append("]");
            reportString.append("}},");
        }

        // Remove the last comma
        reportString.deleteCharAt(reportString.length() - 1);

        reportString.append("] }");

        return reportString.toString();
    }

    public static void main(String[] args) throws Exception {
        List<CheckItemReportVO> reportList = new ArrayList<>();


        String report = convertToReport(reportList);
        System.out.println(report);
    }
}
