package me.qunqun.shared.entity.po;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "doctor")
public class Doctor
{
	/**
	 * 医生编号
	 */
	@Id
	@Column(name = "id", nullable = false)
	private Integer id;
	
	/**
	 * 医生编码
	 */
	@Column(name = "code", nullable = false, length = 20)
	private String code;
	
	/**
	 * 真实姓名
	 */
	@Column(name = "real_name", nullable = false, length = 20)
	private String realName;
	
	/**
	 * 密码
	 */
	@Column(name = "password", nullable = false, length = 20)
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
	
}