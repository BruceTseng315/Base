package com.kevin.infrastructure;

import java.io.Serializable;


/**
 * 
 * 
 * @author kevin
 *
 * @param <T>
 */

public class Result<T> implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/** 错误码. */
	private Integer code;

	/** 提示信息. */
	private String message;

	private String info;

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}

	/** 具体的内容. */
	private T data;
	
	final static int DEFAULTERRORCODE=500;
	
	final static int DEFAULTSUCCESSCODE=200;
	
	public final  static Result<?> errorresult;
	
	
	public final  static Result<?> successresult;
	
	
	
	static {
		errorresult = new Result<Object>(DEFAULTERRORCODE,"error");
		successresult = new Result<Object>(DEFAULTSUCCESSCODE,"success");
	}
	
	
	/**
	 * 返回错误信息  默认状态码
	 * @param errorMsg
	 * @return
	 */
	public static Result<?> errorrResult(String errorMsg) {
		return new Result<Object>(DEFAULTERRORCODE, errorMsg);
	}
	
	public static Result<?> successResult(String msg) {
		return new Result<Object>(DEFAULTSUCCESSCODE, msg);
	}
	
	/**
	 * 返回error信息  包含状态码
	 * @param code
	 * @param errorMsg
	 * @return
	 */
	public static Result<?> errorrResult(Integer code,String errorMsg) {
		return new Result<Object>(code, errorMsg);
	}



	public static Result<?> errorResult(ResultEnum resultEnum){
		return new Result<Object>(resultEnum.getCode(),resultEnum.getMsg());
	}
	
	
	public Result(){
		this(0,"success" , null);
	}

	
	

	public Result(Integer code, String message) {
		this(code,message,null);
	}


	public Result(T data) {
		this(0,"success" , data);
	}

	public Result(Integer code, String message, T data) {
		super();
		this.code = code;
		this.message = message;
		this.data = data;
	}
	public Result withInfo(String info){
		this.info = info;
		return this;
	}

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

	@Override
	public String toString() {
		return "Result [code=" + code + ", message=" + message + ", data=" + data + "]";
	}

	public static Result success(Object data){
		Result result = new Result(DEFAULTSUCCESSCODE,"success",data);
		
		return result;
	}

	public static Result success(int code,Object data){
		Result result = new Result(code,"success",data);

		return result;
	}



}
