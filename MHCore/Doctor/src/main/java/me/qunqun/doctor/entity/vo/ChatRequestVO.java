package me.qunqun.doctor.entity.vo;

import lombok.Data;

@Data
public class ChatRequestVO {
    private String message;
    private String mode;
    private String workspace;
}
