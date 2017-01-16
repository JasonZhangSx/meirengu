package com.meirengu.sms.exception;


public class BusinessException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4662451780126333137L;

	public BusinessException(String msg) {
		super(msg);
	}

	public BusinessException(String msg, Exception e) {
		super(msg, e);
	}

	public BusinessException(Exception e) {
		super(e);
	}

}
