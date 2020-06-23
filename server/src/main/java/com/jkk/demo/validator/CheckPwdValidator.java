package com.jkk.demo.validator;

import com.jkk.demo.util.ValueValidator;
import org.apache.commons.lang.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class CheckPwdValidator implements ConstraintValidator<CheckPwd,String> {
	@Override
	public void initialize(CheckPwd constraintAnnotation) {

	}

	@Override
	public boolean isValid(String str, ConstraintValidatorContext constraintValidatorContext) {
		if (!StringUtils.isNotBlank(str)) {
			return true;
		}
		return ValueValidator.checkPwd(str);
	}
}
