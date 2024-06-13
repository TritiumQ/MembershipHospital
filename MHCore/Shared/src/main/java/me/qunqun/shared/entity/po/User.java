package me.qunqun.shared.entity.po;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "user")
public class User
{
	/**
	 * 用户编号（手机号码）
	 */
	@Id
	@Column(name = "id", nullable = false, length = 11)
	private String id;
	
	/**
	 * 密码
	 */
	@Column(name = "password", nullable = false, length = 20)
	private String password;
	
	/**
	 * 真实姓名
	 */
	@Column(name = "real_name", nullable = false, length = 20)
	private String realName;
	
	/**
	 * 用户性别（1：男；0女）
	 */
	@Column(name = "sex", nullable = false)
	private Integer sex;
	
	/**
	 * 身份证号
	 */
	@Column(name = "id_card", nullable = false, length = 18)
	private String idCard;
	
	/**
	 * 出生日期
	 */
	@Column(name = "birthday", nullable = false)
	private LocalDate birthday;
	
	/**
	 * 用户类型（1：普通用户；2：东软内部员工；3：其他）
	 */
	@Column(name = "type", nullable = false)
	private Integer type;
	
}