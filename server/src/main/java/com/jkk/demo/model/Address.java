package com.jkk.demo.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.jkk.demo.model.dal.BaseDO;
import com.jkk.demo.validator.CanBlankLength;
import com.jkk.demo.validator.CheckTelephone;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.validator.constraints.Length;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@DynamicUpdate
@Table(name = "user_address_info")
public class Address extends BaseDO {
	@JsonIgnore
	private Integer userId;

	@Pattern(regexp = "^(\\d+)-(\\d+)-(\\d+)")
	private String addressNum;

	@Length(min = 4, max = 100)
	private String addressDetail;

	@Length(max = 7)
	@NotBlank
	private String name;

	@CheckTelephone
	@NotBlank
	private String telephone;

	@NotNull
	private Boolean defaultStatus;

	@CanBlankLength(min = 6, max = 6)
	private String postcode;
}
