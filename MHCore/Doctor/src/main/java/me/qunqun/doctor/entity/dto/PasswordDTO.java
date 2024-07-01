package me.qunqun.doctor.entity.dto;

import lombok.Data;

@Data
public class PasswordDTO {
    String code;
    String oldPassword;
    String newPassword;
}
