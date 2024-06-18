package me.qunqun.shared.entity.po;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@Accessors(chain = true)
@ToString
@Entity
@Table(name = "package")
public class Package
{
	/**
	 * 套餐编号
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false)
	private Integer id;
	
	/**
	 * 套餐名称
	 */
	@Size(max = 255)
	@NotNull
	@Column(name = "name", nullable = false)
	private String name;
	
	/**
	 * 套餐类型（1：男士餐套；0：女士套餐）
	 */
	@NotNull
	@Column(name = "type", nullable = false)
	private Integer type;
	
	/**
	 * 套餐价格
	 */
	@NotNull
	@Column(name = "price", nullable = false)
	private Integer price;
	
	
	/**
	 * 套餐所包含的检查项
	 */
	@ManyToMany
	@JoinTable(name = "package_detailed",
			joinColumns = @JoinColumn(name = "package_id"),
			inverseJoinColumns = @JoinColumn(name = "check_item_id"))
	@ToString.Exclude
	private Set<CheckItem> checkItems = new LinkedHashSet<>();
	
	
	@OneToMany(mappedBy = "packageField")
	@ToString.Exclude
	@Deprecated
	private Set<Order> orders = new LinkedHashSet<>();
	
}