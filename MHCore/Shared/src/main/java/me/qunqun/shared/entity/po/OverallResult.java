package me.qunqun.shared.entity.po;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Entity
@Table(name = "overall_result")
public class OverallResult
{
	/**
	 * 总检结论项编号
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false)
	private Integer id;
	
	/**
	 * 总检结论项标题
	 */
	@Size(max = 40)
	@NotNull
	@Column(name = "title", nullable = false, length = 40)
	private String title;
	
	/**
	 * 总检结论项内容
	 */
	@Size(max = 400)
	@Column(name = "content", length = 400)
	private String content;

	
	/**
	 * 所属预约编号
	 */
	@NotNull
	@Column(name = "order_id", nullable = false)
	private Integer orderId;
	
	
//	/**
//	 * 所属预约
//	 */
//	@NotNull
//	@ManyToOne(fetch = FetchType.LAZY, optional = false)
//	@JoinColumn(name = "order_id", nullable = false)
//	@Deprecated
//	private Order order;
//
}