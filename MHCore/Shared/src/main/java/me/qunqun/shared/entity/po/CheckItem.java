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
@Table(name = "check_item")
public class CheckItem
{
	/**
	 * 检查项编号
	 */
	@Id
	@Column(name = "id", nullable = false)
	private Integer id;
	
	/**
	 * 检查项名称
	 */
	@Column(name = "name", nullable = false, length = 30)
	private String name;
	
	/**
	 * 检查项内容
	 */
	@Column(name = "content", nullable = false, length = 200)
	private String content;
	
	/**
	 * 检查项意义
	 */
	@Column(name = "meaning", length = 200)
	private String meaning;
	
	/**
	 * 备注
	 */
	@Column(name = "remarks", length = 50)
	private String remarks;
	
}