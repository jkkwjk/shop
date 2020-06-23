package com.jkk.demo.validator;

import com.jkk.demo.util.ValueValidator;
import org.apache.commons.lang.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class CheckTelephoneValidator implements ConstraintValidator<CheckTelephone,String> {
	@Override
	public void initialize(CheckTelephone constraintAnnotation) {

	}

	@Override
	public boolean isValid(String str, ConstraintValidatorContext constraintValidatorContext) {
		if (!StringUtils.isNotBlank(str)) {
			return true;
		}
		return ValueValidator.checkTelephone(str);
	}
}
