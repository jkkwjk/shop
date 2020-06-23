package com.jkk.demo.handler;

import com.alibaba.fastjson.JSON;
import com.jkk.demo.util.RestfulRes;
import com.sun.deploy.net.HttpResponse;
import org.apache.commons.lang.StringUtils;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLIntegrityConstraintViolationException;

@ControllerAdvice
public class AllExceptionHandler {

	/**
	 * 数据库约束异常
	 * @param e
	 */
	@ExceptionHandler(value = DataIntegrityViolationException.class)
	public void DataIntegrityViolationExceptionHandler(HttpServletResponse res, DataIntegrityViolationException e) throws IOException {
		String a = StringUtils.substringBetween(e.getMessage(),"SQL [n/a]; constraint [","]; nested exception is ");
		res.setCharacterEncoding("utf-8");
		res.getWriter().print(JSON.toJSONString(RestfulRes.fail(a)));
	}
}
