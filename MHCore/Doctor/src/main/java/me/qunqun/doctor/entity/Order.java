package me.qunqun.doctor.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.annotations.ColumnDefault;

import java.io.Serial;
import java.io.Serializable;
import java.sql.Date;
import java.time.LocalDate;

@Data
@Entity
@Table(name = "`order`")
public class Order implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 订单编号
     */
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 预约日期
     */
    @Column(name = "date", nullable = false)
    private LocalDate date;


    /**
     * 订单状态（1：未归档；2：已归档）
     */
    @Column(name = "state", nullable = false)
    private Integer state;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false, updatable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "hospital_id", nullable = false, updatable = false)
    private Hospital hospital;

    @ManyToOne
    @JoinColumn(name = "package_id", nullable = false, updatable = false)
    private Package packageInfo;

    /**
     * 标记删除（0：未删除，非0：已删除）
     */
    @NotNull
    @ColumnDefault("0")
    @Column(name = "deprecated", nullable = false)
    private Boolean deprecated = false;
}
