package com.jkk.demo.model.dal;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

@EqualsAndHashCode(callSuper = true)
@Entity
@DynamicUpdate
@DynamicInsert
@Data
@Table(name = "order_goods")
public class OrderGoodsDO extends BaseDO {
	private Integer orderId;

	private Integer goodsId;

	private Integer goodsNum;

	private String goodsName;

	private String goodsDescribe;

	private Float goodsActualPrice;

	private String goodsPic;

	private Integer storeId;

	private String prop;

	@Column(name = "dictionary_code")
	private String dicCode;
}
