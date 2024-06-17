package me.qunqun.doctor.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;

@Data
@Entity
@Table(name = "package")
public class Package implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 套餐编号
     */
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 套餐名称
     */
    @Column(name = "name", nullable = false)
    private String name;

    /**
     * 套餐类型（1：男士餐套；0：女士套餐）
     */
    @Column(name = "type", nullable = false)
    private Integer type;

    /**
     * 套餐价格
     */
    @Column(name = "price", nullable = false)
    private Integer price;

}
