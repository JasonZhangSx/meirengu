package com.meirengu.exception;

/**
 * 自定义异常类
 * 对于非运行时异常，必须对其进行处理。处理方式有两种：
 * 1、使用try…catch…finally语句块进行捕获
 * 2、在产生异常的方法所在的方法声明throws Exception
 *
 * @author Marvin
 * @create 2017-01-18 下午3:08
 */
public class ManagerException extends Exception{

	private static final long serialVersionUID = 7643195293790044743L;
	private String errorCode;

	public ManagerException()
	{
		super();
	}

	public ManagerException(String message)
	{
		super(message);
	}

	public ManagerException(String message, String errorCode)
	{
		super(message);
		this.errorCode = errorCode;
	}

	public ManagerException(String message, Throwable cause)
	{
		super(message, cause);
	}

	public ManagerException(String message, Throwable cause, String errorCode)
	{
		super(message, cause);
		this.errorCode = errorCode;
	}

	public String getErrorCode()
	{
		return errorCode;
	}

	
}
