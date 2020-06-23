package com.jkk.demo.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class ParamValidFailedException extends RuntimeException{
	private String message;

	public ParamValidFailedException(String message, String filed){
		this.message = filed + "->" +message;
	}
}
