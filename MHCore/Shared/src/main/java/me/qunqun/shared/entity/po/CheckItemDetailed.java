package me.qunqun.shared.entity.po;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

@Getter
@Setter
@Entity
@Table(name = "check_item_detailed")
public class CheckItemDetailed
{
	/**
	 * 检查项明细编号
	 */
	@Id
	@Column(name = "id", nullable = false)
	private Integer id;
	
	/**
	 * 检查项细明名称
	 */
	@Column(name = "name", nullable = false, length = 40)
	private String name;
	
	/**
	 * 检查项明细单位
	 */
	@Column(name = "unit", length = 20)
	private String unit;
	
	/**
	 * 检查项细明正常值范围中的最小值
	 */
	@Column(name = "min_range")
	private Double minRange;
	
	/**
	 * 检查项细明正常值范围中的最大值
	 */
	@Column(name = "max_range")
	private Double maxRange;
	
	/**
	 * 检查项细明正常值（非数字型）
	 */
	@Column(name = "normal_value", length = 20)
	private String normalValue;
	
	/**
	 * 检查项验证范围说明文字
	 */
	@Column(name = "normal_value_description", length = 20)
	private String normalValueDescription;
	
	/**
	 * 检查项明细类型（1：数值围范验证型；2：数值相等验证型；3：无需验证型；4：描述型；5：其它）
	 */
	@ColumnDefault("1")
	@Column(name = "type", nullable = false)
	private Integer type;
	
	/**
	 * 所属检查项编号
	 */
	@Column(name = "check_item_id", nullable = false)
	private Integer checkItemId;
	
	/**
	 * 备注
	 */
	@Column(name = "remarks", length = 100)
	private String remarks;
	
}