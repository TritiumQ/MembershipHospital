package me.qunqun.user.entity.po;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.Immutable;
import org.hibernate.annotations.View;

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
	 * 虚拟主键
	 */
	@Id
	@Column(name = "v_id")
	private Integer vId;
	/**
	 * 预约日期
	 */
	@NotNull
	@Column(name = "date", nullable = false)
	private LocalDate date;
	
	@NotNull
	@Column(name = "hospital_id", nullable = false)
	private Integer hospitalId;
	
	@NotNull
	@Column(name = "count", nullable = false)
	private Long count;
	
}