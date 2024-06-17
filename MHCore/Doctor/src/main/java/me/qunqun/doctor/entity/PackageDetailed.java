package me.qunqun.doctor.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;

@Data
@Entity
@Table(name = "package_detailed")
public class PackageDetailed implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 套餐明细编号（无意义主键）
     */
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 套餐编号
     */
    @Column(name = "package_id", nullable = false)
    private Integer packageId;

    /**
     * 检查项编号
     */
    @Column(name = "check_item_id", nullable = false)
    private Integer checkItemId;

}
