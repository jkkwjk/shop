package com.jkk.demo.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.jkk.demo.model.dal.UserDO;
import com.jkk.demo.validator.CheckEmail;
import com.jkk.demo.validator.CheckPwd;
import com.jkk.demo.validator.CheckTelephone;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.springframework.beans.BeanUtils;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
@Data
public class User {
	@NotBlank
	@Length(max = 13)
	private String name;

	@NotBlank
	@Length(min = 4, max = 13, message = "昵称要在4-13位")
	private String nickName;

	@NotNull
	private Boolean sex;

	private String icon;

	@CheckTelephone
	@NotBlank
	private String telephone;

	@JsonIgnore
	@CheckPwd
	@NotBlank
	private String pwd;

	@CheckEmail
	private String email;

	public UserDO convert(){
		UserDO userDO = new UserDO();
		BeanUtils.copyProperties(this,userDO);
		return userDO;
	}
}
