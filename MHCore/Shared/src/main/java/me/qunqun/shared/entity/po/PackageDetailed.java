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
@Table(name = "package_detailed")
public class PackageDetailed
{
	/**
	 * 套餐明细编号（无意义主键）
	 */
	@Id
	@Column(name = "id", nullable = false)
	private Integer id;
	
	/**
	 * 套餐编号
	 */
	@Column(name = "package_id", nullable = false)
	private Integer packageId;
	
	/**
	 * 检查项编号
	 */
	@Column(name = "check_item_id", nullable = false)
	private Integer checkItemId;
	
}