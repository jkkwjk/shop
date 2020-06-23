package com.jkk.demo.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.jkk.demo.model.dal.BaseDO;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.Table;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@Entity
@DynamicUpdate
@Table(name = "store_info")
public class Store extends BaseDO {

	private String storeName;

	@JsonIgnore
	private Integer userId;

	private String storeDescribe;

	private Integer credit;
}
