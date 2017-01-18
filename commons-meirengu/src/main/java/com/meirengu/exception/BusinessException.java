package com.meirengu.exception;

/**
 * 自定义运行时异常类
 * 对于运行时异常，可以不对其进行处理，也可以对其进行处理。
 *
 * @author Marvin
 * @create 2017-01-18 下午3:06
 */
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
