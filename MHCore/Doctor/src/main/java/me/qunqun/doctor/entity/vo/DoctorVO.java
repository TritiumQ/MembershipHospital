package me.qunqun.doctor.entity.vo;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;
import me.qunqun.shared.entity.po.Doctor;

@Data
public class DoctorVO {

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

    private String token;

    public static DoctorVO fromDoctor(Doctor doctor, boolean withPassword) {
        DoctorVO doctorVO = new DoctorVO();
        doctorVO.setId(doctor.getId());
        doctorVO.setCode(doctor.getCode());
        doctorVO.setRealName(doctor.getRealName());
        doctorVO.setPassword(null);
        if (withPassword)
            doctorVO.setPassword(doctor.getPassword());
        doctorVO.setSex(doctor.getSex());
        doctorVO.setDeptNo(doctor.getDeptNo());
        doctorVO.setEmail(doctor.getEmail());
        doctorVO.setToken(null);
        return doctorVO;
    }
}
