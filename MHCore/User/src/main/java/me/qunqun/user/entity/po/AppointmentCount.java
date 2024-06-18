package me.qunqun.user.entity.po;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.Immutable;

import java.time.LocalDate;

/**
 * Mapping for DB view
 */
@Getter
@Setter
@ToString
@Entity
@Immutable
@Table(name = "appointment_count")
public class AppointmentCount
{
	/**
	 * 预约日期
	 */
	@Id
	@NotNull
	@Column(name = "date", nullable = false)
	private LocalDate date;
	
	@NotNull
	@Column(name = "count", nullable = false)
	private Long count;
	
}