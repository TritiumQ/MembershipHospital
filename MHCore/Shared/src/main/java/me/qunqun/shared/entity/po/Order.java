package me.qunqun.shared.entity.po;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.ColumnDefault;

import java.time.LocalDate;
import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@ToString
@Entity
@Table(name = "`order`")
public class Order
{
	/**
	 * 订单编号
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false)
	private Integer id;
	
	/**
	 * 预约日期
	 */
	@NotNull
	@Column(name = "date", nullable = false)
	private LocalDate date;
	
	/**
	 * 客户编号
	 */
	@NotNull
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "user_id", nullable = false)
	@ToString.Exclude
	private User user;
	
	/**
	 * 所属医院编号
	 */
	@NotNull
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "hospital_id", nullable = false)
	@ToString.Exclude
	private Hospital hospital;
	
	/**
	 * 所属套餐编号
	 */
	@NotNull
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "package_id", nullable = false)
	@ToString.Exclude
	private Package packageField;
	
	/**
	 * 订单状态（1：未归档；2：已归档）
	 */
	@NotNull
	@ColumnDefault("1")
	@Column(name = "state", nullable = false)
	private Integer state;
	
	@OneToMany(mappedBy = "orderId")
	@ToString.Exclude
	private Set<CheckItemReport> checkItemReports = new LinkedHashSet<>();
	
	@OneToMany(mappedBy = "orderId")
	@ToString.Exclude
	private Set<OverallResult> overallResults = new LinkedHashSet<>();
	
}