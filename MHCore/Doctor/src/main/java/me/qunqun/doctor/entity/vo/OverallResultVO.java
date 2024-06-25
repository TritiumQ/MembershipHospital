package me.qunqun.doctor.entity.vo;

import lombok.Data;
import me.qunqun.shared.entity.po.OverallResult;

@Data
public class OverallResultVO {
    private Integer id;
    private String title;
    private String content;
    private Integer orderId;

    public OverallResultVO fromOverallResult(OverallResult overallResult) {
        if (overallResult != null) {
            this.setId(overallResult.getId());
            this.setTitle(overallResult.getTitle());
            this.setContent(overallResult.getContent());
            this.setOrderId(overallResult.getOrder().getId());
        }
        return this;
    }
}
