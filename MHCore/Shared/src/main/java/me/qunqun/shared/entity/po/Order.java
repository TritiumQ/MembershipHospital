package me.qunqun.shared.entity.po;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.ColumnDefault;
import org.springframework.lang.Nullable;

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
//	@NotNull
//	@Column(name = "user_id", nullable = false)
//	private String userId;
	
	/**
	 * 所属医院编号
	 */
	@NotNull
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "hospital_id", nullable = false)
	@ToString.Exclude
	private Hospital hospital;
//	@NotNull
//	@Column(name = "hospital_id", nullable = false)
//	private Integer hospitalId;
	
	/**
	 * 所属套餐编号
	 */
	@NotNull
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "package_id", nullable = false)
	@ToString.Exclude
	private Package packageField;
//	@NotNull
//	@Column(name = "package_id", nullable = false)
//	private Integer packageId;
	
	
	/**
	 * 订单状态（1：未归档；2：已归档）
	 */
	@ColumnDefault("1")
	@Column(name = "state", nullable = false)
	private Integer state;
	
	@OneToMany(mappedBy = "order")
	@ToString.Exclude
	private Set<CheckItemReport> checkItemReports = new LinkedHashSet<>();
	
	@OneToMany(mappedBy = "order")
	@ToString.Exclude
	private Set<OverallResult> overallResults = new LinkedHashSet<>();
	
	
	/**
	 * 标记删除（0：未删除，非0：已删除）
	 */
	@ColumnDefault("0")
	@Column(name = "deprecated", nullable = false)
	private Boolean deprecated = false;
	
	/**
	 * 支付状态（0：未支付；1：已支付）
	 */
	@ColumnDefault("0")
	@Column(name = "pay", nullable = false)
	private Integer pay;
	
	/**
	 * 家属编号(编号小于等于0则表示为个人订单)
	 */
	@ManyToOne(fetch = FetchType.LAZY, optional = true)
	@JoinColumn(name = "family_id", nullable = true)
	@Nullable
	@ToString.Exclude
	private Family family;
	
}