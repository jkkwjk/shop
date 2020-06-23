package com.jkk.demo.validator;

import com.jkk.demo.util.ValueValidator;
import org.apache.commons.lang.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class CanBlankLengthValidator implements ConstraintValidator<CanBlankLength,String> {
	private int max = 0;
	private int min = 0;
	@Override
	public void initialize(CanBlankLength constraintAnnotation) {
		this.max = constraintAnnotation.max();
		this.min = constraintAnnotation.min();
	}

	@Override
	public boolean isValid(String str, ConstraintValidatorContext constraintValidatorContext) {
		if (!StringUtils.isNotBlank(str)) {
			return true;
		}
		int len = str.length();
		return len<=max && len>=min;
	}
}
