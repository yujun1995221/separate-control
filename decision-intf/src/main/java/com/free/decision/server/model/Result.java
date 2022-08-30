package com.free.decision.server.model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class Result implements Serializable {

	//是否成功
	private boolean success;
	//操作结果代码
	private int code;
	//结果消息
	private String msg;
	//操作结果数据
	private Object data;
	//附加数据
	private Map<String,Object> ext;

	public Result(){
		this.success = false;
		this.code = 0;
		this.msg = "";
	}
	
	/**
	 * 构造方法
	 * @param success：是否成功
	 */
	public Result(boolean success) {
		this.success = success;
		this.code = 0;
		this.msg = "";
	}

	/**
	 * 构造方法
	 * @param success：是否成功
	 * @param code：操作结果代码
	 * @param msg：操作结果消息
	 */
	public Result(boolean success, int code, String msg) {
		this.success = success;
		this.code = code;
		this.msg = msg;
	}
	
	/**
	 * 构造方法
	 * @param success：是否成功
	 * @param code：操作结果代码
	 * @param msg：操作结果消息
	 * @param data：结果数据
	 */
	public Result(boolean success, int code, String msg, Object data) {
		this.success = success;
		this.code = code;
		this.msg = msg;
		this.data = data;
	}

	/**
	 * 构造方法
	 * @param success：是否成功
	 * @param msg：操作结果消息
	 * @param data：结果数据
	 */
	public Result(boolean success, String msg, Object data) {
		this.success = success;
		this.msg = msg;
		this.data = data;
	}

    /**
     * 构造方法
     * @param success：是否成功
     * @param msg：操作结果消息
     */
    public Result(boolean success, String msg) {
        this.success = success;
        this.msg = msg;
    }

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}
	
	public <T> T getData() {
		return (T) data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	//追加数据
	public void put(String key, Object val){
		if(ext == null){
			ext = new HashMap<String,Object>();
		}
		ext.put(key, val == null ? "" : val);
	}
}
