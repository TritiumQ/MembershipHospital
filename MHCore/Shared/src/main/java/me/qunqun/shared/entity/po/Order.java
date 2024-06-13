package me.qunqun.shared.entity.po;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "`order`")
public class Order
{
	/**
	 * 订单编号
	 */
	@Id
	@Column(name = "id", nullable = false)
	private Integer id;
	
	/**
	 * 预约日期
	 */
	@Column(name = "date", nullable = false)
	private LocalDate date;
	
	/**
	 * 客户编号
	 */
	@Column(name = "user_id", nullable = false, length = 11)
	private String userId;
	
	/**
	 * 所属医院编号
	 */
	@Column(name = "hospital_id", nullable = false)
	private Integer hospitalId;
	
	/**
	 * 所属套餐编号
	 */
	@Column(name = "package_id", nullable = false)
	private Integer packageId;
	
	/**
	 * 订单状态（1：未归档；2：已归档）
	 */
	@ColumnDefault("1")
	@Column(name = "state", nullable = false)
	private Integer state;
	
}