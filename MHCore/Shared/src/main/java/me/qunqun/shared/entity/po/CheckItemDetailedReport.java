package me.qunqun.shared.entity.po;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.ColumnDefault;

@Getter
@Setter
@ToString
@Entity
@Table(name = "check_item_detailed_report")
public class CheckItemDetailedReport
{
	/**
	 * 检查项明细报告主键
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false)
	private Integer id;
	
	/**
	 * 检查项目明细值
	 */
	@Size(max = 100)
	@Column(name = "value", length = 100)
	private String value;
	
	/**
	 * 此项是否异常（0：无异常；1：异常）
	 */
	@NotNull
	@ColumnDefault("0")
	@Column(name = "error", nullable = false)
	private Integer error;
	
	/**
	 * 所属检查项报告编号
	 */
	@NotNull
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "check_item_report_id", nullable = false)
	@ToString.Exclude
	private CheckItemReport checkItemReport;
	
//	/**
//	 * 所属预约编号
//	 */
//	@NotNull
//	@Column(name = "order_id", nullable = false)
//	private Integer orderId;
//
	/**
	 * 所属预约编号
	 */
	@NotNull
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "order_id", nullable = false)
	@ToString.Exclude
	private Order order;
	
	@NotNull
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "check_item_detailed_id", nullable = false)
	@ToString.Exclude
	private CheckItemDetailed checkItemDetailed;
	
}