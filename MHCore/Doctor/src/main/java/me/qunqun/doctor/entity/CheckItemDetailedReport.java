package me.qunqun.doctor.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

@Data
@Entity
@Table(name = "check_item_detailed_report")
public class CheckItemDetailedReport implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 检查项明细报告主键
     */
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 检查项目明细值
     */
    @Column(name = "value")
    private String value;

    /**
     * 此项是否异常（0：无异常；1：异常）
     */
    @Column(name = "error", nullable = false)
    private Integer error;

    /**
     * 所属检查项报告编号
     */
    @ManyToOne
    @JoinColumn(name = "check_item_report_id", insertable = false, updatable = false)
    private CheckItemReport checkItemReport;

    /**
     * 所属预约编号
     */
    @ManyToOne
    @JoinColumn(name = "order_id", nullable = false, updatable = false)
    private Order order;


    @ManyToOne
    @JoinColumn(name = "check_item_detailed_id", nullable = false, updatable = false, insertable = false)
    private CheckItemDetailed checkItemDetailed;

}
