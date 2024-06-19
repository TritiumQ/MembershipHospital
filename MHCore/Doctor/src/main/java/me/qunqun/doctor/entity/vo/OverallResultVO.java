package me.qunqun.doctor.entity.vo;

import lombok.Data;
import me.qunqun.shared.entity.po.OverallResult;

@Data
public class OverallResultVO {
    private Integer id;
    private String title;
    private String content;
    private Integer orderId;

    public OverallResultVO findOverallResult(OverallResult overallResult) {
        if (overallResult != null) {
            OverallResultVO overallResultVO = new OverallResultVO();
            overallResultVO.setId(overallResult.getId());
            overallResultVO.setTitle(overallResult.getTitle());
            overallResultVO.setContent(overallResult.getContent());
            overallResultVO.setOrderId(overallResult.getOrder().getId());
            return overallResultVO;
        }
        return null;
    }
}
