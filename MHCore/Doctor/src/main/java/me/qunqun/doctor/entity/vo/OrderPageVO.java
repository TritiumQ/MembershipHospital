package me.qunqun.doctor.entity.vo;

import lombok.Data;

import java.util.List;

@Data
public class OrderPageVO {
    List<OrderVO> orders;
    Integer pageNum;
}
