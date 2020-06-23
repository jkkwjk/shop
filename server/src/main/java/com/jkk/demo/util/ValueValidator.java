package com.jkk.demo.util;

import java.util.regex.Pattern;

public class ValueValidator {
	public static Boolean checkPwd(String value){
//		return Pattern.matches("^(?![^a-zA-Z]+)(?!\\D+$)",value);
		return Pattern.matches("^(?=.*[0-9])(?=.*[a-zA-Z])(.{8,})$",value);
	}

	public static Boolean checkEmail(String value){
		return Pattern.matches("^([a-zA-Z]|[0-9])(\\w|\\-)+@[a-zA-Z0-9]+\\.([a-zA-Z]{2,4})$",value);
	}

	public static Boolean checkTelephone(String value){
		return Pattern.matches("^1[3456789]\\d{9}$",value);
	}
}
