package me.qunqun.shared.entity.po;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Entity
@Table(name = "doctor")
public class Doctor
{
	/**
	 * 医生编号
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false)
	private Integer id;
	
	/**
	 * 医生编码
	 */
	@Size(max = 20)
	@NotNull
	@Column(name = "code", nullable = false, length = 20)
	private String code;
	
	/**
	 * 真实姓名
	 */
	@Size(max = 20)
	@NotNull
	@Column(name = "real_name", nullable = false, length = 20)
	private String realName;
	
	/**
	 * 密码
	 */
	@Size(max = 20)
	@NotNull
	@Column(name = "password", nullable = false, length = 20)
	private String password;
	
	/**
	 * 性别
	 */
	@NotNull
	@Column(name = "sex", nullable = false)
	private Integer sex;
	
	/**
	 * 所属科室（1：检验科；2：内科；3：外科）
	 */
	@NotNull
	@Column(name = "dept_no", nullable = false)
	private Integer deptNo;
	
	@Size(max = 50)
	@NotNull
	@Column(name = "email", nullable = false, length = 50)
	private String email;
	
}