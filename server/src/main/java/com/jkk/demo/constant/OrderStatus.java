package com.jkk.demo.constant;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
public enum OrderStatus {
	NO_PAY(0, "未付款"), WAIT_SHIP(1,"待发货"),
	ALREADY_SHIP(2,"已发货"), COMPLETE(3,"已完成");
	private Integer status;

	private String statusString;


	public static String getStatusString(int status) {
		for (OrderStatus c : OrderStatus.values()) {
			if (c.getStatus() == status) {
				return c.getStatusString();
			}
		}
		return null;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getStatusString() {
		return statusString;
	}

	public void setStatusString(String statusString) {
		this.statusString = statusString;
	}
}
