package com.meirengu.wxcs.util;

import java.io.UnsupportedEncodingException;
import java.util.ResourceBundle;

public final class PropUtil {
	
	ResourceBundle rb;

	private static PropUtil instance;
	
	public synchronized static PropUtil getInstance(){
		if(instance==null){
			instance = new PropUtil();
		}
		return instance;
	}
	
	private PropUtil(){
		rb = ResourceBundle.getBundle("wxcs");
	}
	
	public String getProperty(String propSequence) throws UnsupportedEncodingException{
		return new String(rb.getString(propSequence).getBytes("iso-8859-1"), "utf-8");
	}
	
}