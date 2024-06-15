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
@Table(name = "check_item_report")
public class CheckItemReport
{
	/**
	 * 检查项报告主键
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false)
	private Integer id;
	
	/**
	 * 检查项编号
	 */
	@NotNull
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "check_item_id", nullable = false)
	@ToString.Exclude
	private CheckItem checkItem;
	
	/**
	 * 检查项名称
	 */
	@Size(max = 30)
	@NotNull
	@Column(name = "name", nullable = false, length = 30)
	private String name;
	
	/**
	 * 所属预约编号
	 */
	@NotNull
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "order_id", nullable = false)
	@ToString.Exclude
	private Order order;
	
	@OneToMany(mappedBy = "checkItemReport")
	private Set<CheckItemDetailedReport> checkItemDetailedReports = new LinkedHashSet<>();
	
}