package com.dsmpharm.bidding.utils;

import java.io.Serializable;

/** 
 * <br/>
 * Created by Grant on 2020/07/15
 */
public class Result<T> implements Serializable {

	// 返回码
	private Integer code;
	// 响应信息
	private String message;
	// 响应数据
	private T data;
	// 操作是否成功，也可以通过code判断
	private boolean flag;

	public Result() {
	}

	public Result(boolean flag, Integer code, String message, T data) {
		this.flag = flag;
		this.code = code;
		this.message = message;
		this.data = data;
	}

	public Result(boolean flag, Integer code, String message) {
		this.flag = flag;
		this.code = code;
		this.message = message;
	}

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public boolean isFlag() {
		return flag;
	}

	public void setFlag(boolean flag) {
		this.flag = flag;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Object getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}
}