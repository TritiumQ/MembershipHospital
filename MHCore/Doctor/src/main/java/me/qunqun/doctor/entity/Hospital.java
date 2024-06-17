package me.qunqun.doctor.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;

@Data
@Entity
@Table(name = "hospital")
public class Hospital implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 医院编号
     */
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 医院名称
     */
    @Column(name = "name", nullable = false)
    private String name;

    /**
     * 医院图片
     */
    @Column(name = "picture", nullable = false)
    private String picture;

    /**
     * 医院电话
     */
    @Column(name = "telephone", nullable = false)
    private String telephone;

    /**
     * 医院地址
     */
    @Column(name = "address", nullable = false)
    private String address;

    /**
     * 营业时间
     */
    @Column(name = "business_hours", nullable = false)
    private String businessHours;

    /**
     * 采血截止时间
     */
    @Column(name = "deadline", nullable = false)
    private String deadline;

    /**
     * 预约人数规则
     */
    @Column(name = "rule", nullable = false)
    private String rule;

    /**
     * 医院状态（1：正常；2：其他）
     */
    @Column(name = "state", nullable = false)
    private Integer state;

}
