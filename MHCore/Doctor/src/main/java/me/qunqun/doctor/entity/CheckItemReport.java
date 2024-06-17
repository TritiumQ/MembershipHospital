package me.qunqun.doctor.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

@Data
@Entity
@Table(name = "check_item_report")
public class CheckItemReport implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 检查项报告主键
     */
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 检查项编号
     */
    @ManyToOne
    @JoinColumn(name = "check_item_id", nullable = false, updatable = false)
    private CheckItem checkItem;

    /**
     * 所属预约编号
     */
    @ManyToOne
    @JoinColumn(name = "order_id", nullable = false, updatable = false)
    private Order order;

    /**
     * 检查项报告备注
     */
    @Column(name = "review")
    private String review;

}
