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
@Table(name = "overall_result")
public class OverallResult
{
	/**
	 * 总检结论项编号
	 */
	@Id
	@Column(name = "id", nullable = false)
	private Integer id;
	
	/**
	 * 总检结论项标题
	 */
	@Column(name = "title", nullable = false, length = 40)
	private String title;
	
	/**
	 * 总检结论项内容
	 */
	@Column(name = "content", length = 400)
	private String content;
	
	/**
	 * 所属预约编号
	 */
	@Column(name = "order_id", nullable = false)
	private Integer orderId;
	
}