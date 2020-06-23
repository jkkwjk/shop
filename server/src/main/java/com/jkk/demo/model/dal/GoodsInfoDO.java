package com.jkk.demo.model.dal;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Table;

@EqualsAndHashCode(callSuper = true)
@Entity
@DynamicInsert
@DynamicUpdate
@Data
@Table(name = "goods_info")
public class GoodsInfoDO extends BaseDO {
	private String goodsName;

	private String goodsDescribe;

	private Float goodsPrice;

	private Float goodsActualPrice;

	private Integer goodsStock;

	private Boolean goodsStatus;

	private String goodsPic;

	private Integer storeId;

	@Column(name = "dictionary_code")
	private String dicCode;
}
