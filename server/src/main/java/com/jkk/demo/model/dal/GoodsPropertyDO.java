package com.jkk.demo.model.dal;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.Table;

@EqualsAndHashCode(callSuper = true)
@Entity
@DynamicUpdate
@DynamicInsert
@Data
@Table(name = "goods_property_info")
public class GoodsPropertyDO extends BaseDO {
	private Integer goodsId;

	private String propertyName;

	private String propertyValue;
}
