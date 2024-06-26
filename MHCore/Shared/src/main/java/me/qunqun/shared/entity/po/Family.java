package me.qunqun.shared.entity.po;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
@ToString
@Entity
@Table(name = "family")
public class Family
{
	@Id
	@NotNull
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@NotNull
	@Column(name = "user_id", nullable = false)
	private String userId;
	
	@Size(max = 255)
	@NotNull
	@Column(name = "name", nullable = false)
	private String name;
	
	@Size(max = 255)
	@NotNull
	@Column(name = "id_card", nullable = false)
	private String idCard;
	
	@Size(max = 255)
	@NotNull
	@Column(name = "birthday", nullable = false)
	private String birthday;
	
	@Size(max = 255)
	@NotNull
	@Column(name = "phone", nullable = false)
	private String phone;
	
	@NotNull
	@Column(name="sex", nullable = false)
	private Integer sex;
}
