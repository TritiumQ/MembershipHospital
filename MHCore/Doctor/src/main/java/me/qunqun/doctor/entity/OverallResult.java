package me.qunqun.doctor.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;

@Data
@Entity
@Table(name = "overall_result")
public class OverallResult implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 总检结论项编号
     */
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 总检结论项标题
     */
    @Column(name = "title", nullable = false)
    private String title;

    /**
     * 总检结论项内容
     */
    @Column(name = "content")
    private String content;

    /**
     * 所属预约编号
     */
    @Column(name = "order_id", nullable = false)
    private Integer orderId;

}
