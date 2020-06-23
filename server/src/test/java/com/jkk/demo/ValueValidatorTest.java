package com.jkk.demo;

import com.jkk.demo.util.ValueValidator;
import org.junit.jupiter.api.Test;

import java.util.regex.Pattern;

public class ValueValidatorTest {
	@Test
	void checkPwd(){
		System.out.println(Pattern.matches("^(\\d+)-(\\d+)-(\\d+)","32-156-1565"));
	}
}
