package com.jkk.demo.aspect;

import com.jkk.demo.exception.ParamValidFailedException;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.ValidatorFactory;
import javax.validation.executable.ExecutableValidator;
import java.lang.reflect.Method;
import java.util.Set;

@Component
@Slf4j
@Aspect
public class RequestParamValidAspect {
	@Pointcut("execution(* com.jkk.demo.controller.*.*(..)) && @annotation(com.jkk.demo.aspect.ValidParam)")
	public void controllerBefore() {
	}


	@Around("controllerBefore()")
	public Object before(ProceedingJoinPoint pjp) throws Throwable{
		Object target = pjp.getThis();
		// 获得切入方法参数
		Object[] args = pjp.getArgs();
		// 获得切入的方法
		Method method = ((MethodSignature) pjp.getSignature()).getMethod();

		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		ExecutableValidator executableValidator = factory.getValidator().forExecutables();

		// 执行校验，获得校验结果
		Set<ConstraintViolation<Object>> validResult = executableValidator.validateParameters(target, method, args);
		//如果有校验不通过的
		if (!validResult.isEmpty()) {
			ConstraintViolation<Object> objectConstraintViolation = validResult.iterator().next();
			throw new ParamValidFailedException(objectConstraintViolation.getMessage(), objectConstraintViolation.getPropertyPath().toString());
		}else {
			return pjp.proceed();
		}
		//返回第一条
	}
}
