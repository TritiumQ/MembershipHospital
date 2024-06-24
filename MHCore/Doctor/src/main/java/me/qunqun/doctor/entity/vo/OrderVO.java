package me.qunqun.doctor.entity.vo;

import lombok.Data;
import me.qunqun.shared.entity.po.Order;

import java.time.LocalDate;
import java.util.Date;

@Data
public class OrderVO {
    private Integer orderId;
    private String userId;
    private String userName;
    private String userBirthday;
    private Integer userSex;
    private Integer hospitalId;
    private String hospitalName;
    private LocalDate orderDate;
    private Integer packageId;
    private String packageName;
    private Integer status;

    public OrderVO fromOrder(Order order) {
        this.setOrderId(order.getId());
        this.setUserId(order.getUser().getId());
        this.setUserName(order.getUser().getRealName());
        this.setUserBirthday(order.getUser().getBirthday().toString());
        this.setUserSex(order.getUser().getSex());
        this.setOrderDate(order.getDate());
        this.setHospitalId(order.getHospital().getId());
        this.setHospitalName(order.getHospital().getName());
        this.setPackageId(order.getPackageField().getId());
        this.setPackageName(order.getPackageField().getName());
        this.setStatus(order.getState());
        return this;
    }
}
