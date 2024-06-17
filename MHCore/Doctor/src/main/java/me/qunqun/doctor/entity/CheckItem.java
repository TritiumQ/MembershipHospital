package me.qunqun.doctor.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

@Data
@Entity
@Table(name = "check_item")
public class CheckItem implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 检查项编号
     */
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 检查项名称
     */
    @Size(max = 200)
    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    /**
     * 检查项内容
     */
    @Column(name = "content", nullable = false)
    private String content;

    /**
     * 检查项意义
     */
    @Column(name = "meaning")
    private String meaning;

    /**
     * 备注
     */
    @Column(name = "remarks")
    private String remarks;

}
