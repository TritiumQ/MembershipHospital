package me.qunqun.shared.entity.po;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
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
	@Column(name = "name", nullable = false, length = 30)
	private String name;
	
	/**
	 * 医院图片
	 */
	@Lob
	@Column(name = "picture", nullable = false)
	private String picture;
	
	/**
	 * 医院电话
	 */
	@Column(name = "telephone", nullable = false, length = 20)
	private String telephone;
	
	/**
	 * 医院地址
	 */
	@Column(name = "address", nullable = false, length = 100)
	private String address;
	
	/**
	 * 营业时间
	 */
	@Column(name = "business_hours", nullable = false, length = 100)
	private String businessHours;
	
	/**
	 * 采血截止时间
	 */
	@Column(name = "deadline", nullable = false, length = 30)
	private String deadline;
	
	/**
	 * 预约人数规则
	 */
	@Column(name = "rule", nullable = false, length = 30)
	private String rule;
	
	/**
	 * 医院状态（1：正常；2：其他）
	 */
	@Column(name = "state", nullable = false)
	private Integer state;
	
}