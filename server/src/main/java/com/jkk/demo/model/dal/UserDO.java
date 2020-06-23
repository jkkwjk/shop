package com.jkk.demo.model.dal;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.jkk.demo.model.User;
import com.jkk.demo.model.dal.BaseDO;
import com.jkk.demo.validator.CheckEmail;
import com.jkk.demo.validator.CheckPwd;
import com.jkk.demo.validator.CheckTelephone;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.validator.constraints.Length;
import org.springframework.beans.BeanUtils;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import java.util.Date;

/**
 * 用户信息
 */
@EqualsAndHashCode(callSuper = true)
@Entity
@EntityListeners(AuditingEntityListener.class)
@DynamicUpdate
@DynamicInsert
@Data
@Table(name = "user")
public class UserDO extends BaseDO {

	private String name;

	private String nickName;

	private Boolean sex;

	private String icon;

	private String telephone;

	private String pwd;

	private Boolean status;

	private String email;

	private Date lastLoginTime;

	@CreatedDate
	private Date registerTime;

	public User convert(){
		User user = new User();
		BeanUtils.copyProperties(this,user);
		return user;
	}
}
