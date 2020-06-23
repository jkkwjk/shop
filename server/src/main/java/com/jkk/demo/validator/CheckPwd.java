package com.jkk.demo.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Constraint(validatedBy = CheckPwdValidator.class)
@Documented
public @interface CheckPwd {
	String message() default "CheckPwd.message";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};

	@Target({ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.CONSTRUCTOR, ElementType.PARAMETER, ElementType.TYPE_USE})
	@Retention(RetentionPolicy.RUNTIME)
	@Documented
	public @interface List {
		CheckPwd[] value();
	}
}
