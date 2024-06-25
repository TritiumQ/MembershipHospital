package me.qunqun.doctor.entity.vo;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class FlowVO {
    private String flowName;
    private List<String> flowData;
    private List<String> flow;
    private Integer flowLength;

    public FlowVO(String flowName, List<String> flowData) {
        this.flowName = flowName;
        this.flowData = flowData;
        this.flow = new ArrayList<>();
        this.flow.add(flowName);
        this.flow.addAll(flowData);
        this.flowLength = flowData.size();
    }
}
