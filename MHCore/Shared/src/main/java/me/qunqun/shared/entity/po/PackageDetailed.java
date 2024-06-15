package me.qunqun.shared.entity.po;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 体检套餐与体检项目联接表实体类
 * @deprecated 该类已废弃，使用{@link Package}类的{@link Package#checkItems}代替
 */
@Getter
@Setter
@ToString
@Entity
@Table(name = "package_detailed")
@Deprecated
public class PackageDetailed
{
	/**
	 * 套餐明细编号（无意义主键）
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false)
	private Integer id;
	
	/**
	 * 套餐编号
	 */
	@NotNull
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "package_id", nullable = false)
	@ToString.Exclude
	private Package packageField;
	
	/**
	 * 检查项编号
	 */
	@NotNull
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "check_item_id", nullable = false)
	@ToString.Exclude
	private CheckItem checkItem;
	
}