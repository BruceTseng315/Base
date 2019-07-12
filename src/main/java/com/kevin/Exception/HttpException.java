package com.kevin.Exception;

/**
 * 自定义的rest 异常
 * @author tzj
 *
 */
public class HttpException extends ServiceException{

	
	int  httpcode=200;
	
	
	
	public HttpException(String errormsg) {
		super(500,errormsg);
	}
	
	

	public HttpException(int code, String errormsg) {
		super(code, errormsg);
	}



	public HttpException(int code, int httpcode) {
		super(code);
		this.httpcode = httpcode;
	}

	public HttpException(int code) {
		super(code);
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 8844974604716037789L;



	public int getHttpcode() {
		return httpcode;
	}

	public void setHttpcode(int httpcode) {
		this.httpcode = httpcode;
	}

	
	
}
