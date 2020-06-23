package com.jkk.service.address.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "area")
@Data
public class Area {
	@Id
	private String code;
	private String name;
	private String cityCode;
	private String provinceCode;
}
