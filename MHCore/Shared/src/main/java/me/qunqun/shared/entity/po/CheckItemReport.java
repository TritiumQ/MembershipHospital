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
@Table(name = "check_item_report")
public class CheckItemReport
{
	/**
	 * 检查项报告主键
	 */
	@Id
	@Column(name = "id", nullable = false)
	private Integer id;
	
	/**
	 * 检查项编号
	 */
	@Column(name = "check_item_id", nullable = false)
	private Integer checkItemId;
	
	/**
	 * 检查项名称
	 */
	@Column(name = "name", nullable = false, length = 30)
	private String name;
	
	/**
	 * 所属预约编号
	 */
	@Column(name = "order_id", nullable = false)
	private Integer orderId;
	
}