package me.qunqun.shared.entity.po;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;
import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@ToString
@Entity
@Table(name = "user")
public class User
{
	/**
	 * 用户编号（手机号码）
	 */
	@Id
	@Size(max = 11)
	@Column(name = "id", nullable = false, length = 11)
	private String id;
	
	/**
	 * 密码
	 */
	@Size(max = 300)
	@NotNull
	@Column(name = "password", nullable = false, length = 300)
	private String password;
	
	/**
	 * 真实姓名
	 */
	@Size(max = 20)
	@NotNull
	@Column(name = "real_name", nullable = false, length = 20)
	private String realName;
	
	/**
	 * 用户性别（1：男；0女）
	 */
	@NotNull
	@Column(name = "sex", nullable = false)
	private Integer sex;
	
	/**
	 * 身份证号
	 */
	@Size(max = 18)
	@NotNull
	@Column(name = "id_card", nullable = false, length = 18)
	private String idCard;
	
	/**
	 * 出生日期
	 */
	@NotNull
	@Column(name = "birthday", nullable = false)
	private LocalDate birthday;
	
	/**
	 * 用户类型（1：普通用户；2：东软内部员工；3：其他）
	 */
	@NotNull
	@Column(name = "type", nullable = false)
	private Integer type;
	
	@OneToMany(mappedBy = "userId", fetch = FetchType.LAZY)
	private Set<Order> orders = new LinkedHashSet<>();
	
}