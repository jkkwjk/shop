package com.jkk.demo.model.dal;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Entity
@EntityListeners(AuditingEntityListener.class)
@DynamicUpdate
@DynamicInsert
@Data
@Table(name = "t_order")
public class OrderDO extends BaseDO{
	private String address;

	private String name;

	private String telephone;

	@CreatedDate
	private Date createTime;

	private Integer userId;

	private Integer status;
}
