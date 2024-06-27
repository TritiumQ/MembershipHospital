package me.qunqun.user.entity.po;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.math.BigDecimal;

@Getter
@Setter
@Accessors(chain = true)
@Entity
@Table(name = "year_report")
public class YearReport
{
	@Id
	@NotNull
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;
	
	@Column(name = "user_id")
	private String userId;
	
	@Column(name = "year")
	private Integer year;
	
	@Column(name = "outlay")
	private BigDecimal outlay;
}
