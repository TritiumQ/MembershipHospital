package me.qunqun.doctor.entity.dto;

import lombok.Data;

@Data
public class LoginDTO {
    private String code;
    private String password;
    private Integer type;
}
