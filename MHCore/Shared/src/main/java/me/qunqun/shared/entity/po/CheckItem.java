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
@Table(name = "check_item")
public class CheckItem
{
	/**
	 * 检查项编号
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false)
	private Integer id;
	
	/**
	 * 检查项内容
	 */
	@Size(max = 200)
	@NotNull
	@Column(name = "content", nullable = false, length = 200)
	private String content;
	
	/**
	 * 检查项意义
	 */
	@Size(max = 200)
	@Column(name = "meaning", length = 200)
	private String meaning;
	
	/**
	 * 备注
	 */
	@Size(max = 50)
	@Column(name = "remarks", length = 50)
	private String remarks;
	
	@OneToMany(mappedBy = "checkItem")
	@ToString.Exclude
	private Set<CheckItemDetailed> checkItemDetaileds = new LinkedHashSet<>();
	
	@OneToMany(mappedBy = "checkItem")
	@ToString.Exclude
	private Set<CheckItemReport> checkItemReports = new LinkedHashSet<>();
	
	
}