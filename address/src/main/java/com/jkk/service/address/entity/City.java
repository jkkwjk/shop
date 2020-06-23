package com.jkk.service.address.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "city")
@Data
public class City {
	@Id
	private String code;
	private String name;
	private String provinceCode;
}
