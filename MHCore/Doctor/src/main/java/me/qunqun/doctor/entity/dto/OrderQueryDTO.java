package me.qunqun.doctor.entity.dto;

import lombok.Data;

import java.time.LocalDate;
import java.util.Date;

@Data
public class OrderQueryDTO {
    private Integer orderId;
    private String uerPhone;
    private String userName;
    private Integer userSex;
    private Integer hospitalId;
    private LocalDate orderDate;
    private Integer packageId;
    private Integer status;
}
