package com.jkk.demo.validator;

import com.jkk.demo.util.ValueValidator;
import org.apache.commons.lang.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class CheckEmailValidator implements ConstraintValidator<CheckEmail,String> {
	@Override
	public void initialize(CheckEmail constraintAnnotation) {

	}

	@Override
	public boolean isValid(String str, ConstraintValidatorContext constraintValidatorContext) {
		if (!StringUtils.isNotBlank(str)) {
			return true;
		}
		return ValueValidator.checkEmail(str);
	}
}
