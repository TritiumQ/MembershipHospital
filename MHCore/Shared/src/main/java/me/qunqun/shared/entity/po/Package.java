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
@Table(name = "package")
public class Package
{
	/**
	 * 套餐编号
	 */
	@Id
	@Column(name = "id", nullable = false)
	private Integer id;
	
	/**
	 * 套餐名称
	 */
	@Column(name = "name", nullable = false)
	private String name;
	
	/**
	 * 套餐类型（1：男士餐套；0：女士套餐）
	 */
	@Column(name = "type", nullable = false)
	private Integer type;
	
	/**
	 * 套餐价格
	 */
	@Column(name = "price", nullable = false)
	private Integer price;
	
}