package me.qunqun.shared.entity.po;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@ToString
@Entity
@Table(name = "hospital")
public class Hospital
{
	/**
	 * 医院编号
	 */
	@Id
	@Column(name = "id", nullable = false)
	private Integer id;
	
	/**
	 * 医院名称
	 */
	@Size(max = 30)
	@NotNull
	@Column(name = "name", nullable = false, length = 30)
	private String name;
	
	/**
	 * 医院图片
	 */
	@NotNull
	@Lob
	@Column(name = "picture", nullable = false)
	private String picture;
	
	/**
	 * 医院电话
	 */
	@Size(max = 20)
	@NotNull
	@Column(name = "telephone", nullable = false, length = 20)
	private String telephone;
	
	/**
	 * 医院地址
	 */
	@Size(max = 100)
	@NotNull
	@Column(name = "address", nullable = false, length = 100)
	private String address;
	
	/**
	 * 营业时间
	 */
	@Size(max = 100)
	@NotNull
	@Column(name = "business_hours", nullable = false, length = 100)
	private String businessHours;
	
	/**
	 * 采血截止时间
	 */
	@Size(max = 30)
	@NotNull
	@Column(name = "deadline", nullable = false, length = 30)
	private String deadline;
	
	/**
	 * 预约人数规则
	 */
	@Size(max = 30)
	@NotNull
	@Column(name = "rule", nullable = false, length = 30)
	private String rule;
	
	/**
	 * 医院状态（1：正常；2：其他）
	 */
	@NotNull
	@Column(name = "state", nullable = false)
	private Integer state;
	
	@OneToMany(mappedBy = "hospital", fetch = FetchType.LAZY)
	@ToString.Exclude
	private Set<Order> orders = new LinkedHashSet<>();
	
}