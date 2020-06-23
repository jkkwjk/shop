package com.jkk.service.address.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "province")
@Data
public class Province {
	@Id
	private String code;

	private String name;
}
