package com.jkk.demo.util;

import lombok.Data;
@Data
@SuppressWarnings("unchecked")
public class RestfulRes<T> {
	private int code;
	private String msg;
	private T data;

	public static <T> RestfulRes<T> success(T data){
		RestfulRes restfulRes = new RestfulRes<>();
		restfulRes.setCode(200);
		restfulRes.setData(data);
		return restfulRes;
	}
	public static <T> RestfulRes<T> success(){
		RestfulRes restfulRes = new RestfulRes<>();
		restfulRes.setCode(200);
		return restfulRes;
	}
	public static <T> RestfulRes<T> fail(String msg){
		RestfulRes restfulRes = new RestfulRes<>();
		restfulRes.setCode(505);
		restfulRes.setMsg(msg);
		return restfulRes;
	}
	public static <T> RestfulRes<T> fail(){
		RestfulRes restfulRes = new RestfulRes<>();
		restfulRes.setCode(505);
		return restfulRes;
	}
}
