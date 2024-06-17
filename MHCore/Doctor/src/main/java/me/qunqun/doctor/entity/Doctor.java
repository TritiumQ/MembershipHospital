package me.qunqun.doctor.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

@Data
@Entity
@Table(name = "doctor")
public class Doctor implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 医生编号
     */
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 医生编码
     */
    @Column(name = "code", nullable = false)
    private String code;

    /**
     * 真实姓名
     */
    @Column(name = "real_name", nullable = false)
    private String realName;

    /**
     * 密码
     */
    @Column(name = "password", nullable = false)
    private String password;

    /**
     * 性别
     */
    @Column(name = "sex", nullable = false)
    private Integer sex;

    /**
     * 所属科室（1：检验科；2：内科；3：外科）
     */
    @Column(name = "dept_no", nullable = false)
    private Integer deptNo;

    @Column(name = "email", nullable = false)
    private String email;

}
