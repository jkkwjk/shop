package com.jkk.demo.model.dal;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Data
@Table(name = "field_dictionary")
public class FiledDictionary{
	@Id
	@Column(name = "dictionary_code")
	private String code;

	private String fieldName;

	private String fieldValue;
}
