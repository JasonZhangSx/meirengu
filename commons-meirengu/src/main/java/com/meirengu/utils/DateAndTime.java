/**
 * DateAndTime.java
 * 
 * 功  能：日期和时间管理
 * 类  名：DateAndTime
 * 
 * ver		变更日		公司				变更人			变更内容
 * ------------------------------------------------------------
 * V1.00	2015	uqsoft		uqsoft		
 * 
 * Copyright (c) 2015 Uqsoft Co.,Ltd. All Rights Reserved.
 */

package com.meirengu.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 日期和时间管理
 * ■ 获取当前日期（固定格式：yyyy-MM-dd）
 * ■ 获取当前时间（固定格式：HH:mm:ss）
 * ■ 获取当前日期和时间（固定格式：yyyy-MM-dd HH:mm:ss）
 * ■ 获取当前日期和时间（自定义格式）
 * ■ 获取昨天的日期（固定格式：yyyy-MM-dd）
 * ■ 获取明天的日期（固定格式：yyyy-MM-dd）
 * ■ 把日期型数据转换成字符串
 * ■ 把字符串转换成日期型数据
 * ■ 日期时间格式转换
 * ■ 计算两个日期的间隔（yyyy MM dd HH mm ss）
 * ■ 计算日期加上一段间隔之后的新日期（yyyy MM dd HH mm ss）
 * 
 * @author 		uqsoft
 * @version		1.0		2015-06-23	新建
 */

public final class DateAndTime {
	
	private DateAndTime(){
	}
	
	/**
	 * 获取日期格式，只支持"yyyy-MM-dd HH:mm:ss"、"yyyy-MM-dd"、"MM/dd/yyyy HH:mm:ss"、"MM/dd/yyyy"四种格式。
	 * @param date	日期
	 * @return		格式
	 */
	private static String getFormat(String date) throws Exception {
		int x1 = date.indexOf("-");
		int x2 = date.indexOf("/");
		int x3 = date.indexOf(":");
		if(x1 != -1 && x3 != -1) {
			return "yyyy-MM-dd HH:mm:ss";
		}else if(x1 != -1 && x3 == -1) {
			return "yyyy-MM-dd";
		}else if(x2 != -1 && x3 != -1) {
			return "MM/dd/yyyy HH:mm:ss";
		}else if(x2 != -1 && x3 != -1) {
			return "MM/dd/yyyy";
		}else {
			throw new Exception("日期格式错误：" + date);
		}
	}
	
	/**
	 * 获取当前日期（固定格式：yyyy-MM-dd）
	 * @return	当前日期
	 */
	public static String getCurrentDate() {
		return getCurrentDateTime("yyyy-MM-dd");
	}
	
	/**
	 * 获取当前时间（固定格式：HH:mm:ss）
	 * @return	当前时间
	 */
	public static String getCurrentTime() {
		return getCurrentDateTime("HH:mm:ss");
	}
	
	/**
	 * 获取当前日期和时间（固定格式：yyyy-MM-dd HH:mm:ss）
	 * @return	当前日期和时间
	 */
	public static String getCurrentDateTime() {
		return getCurrentDateTime("yyyy-MM-dd HH:mm:ss");
	}
	
	/**
	 * 获取当前日期和时间（自定义格式）
	 * 参考格式：yyyy年MM月dd日HH时（hh时）mm分ss秒ms毫秒E本月第F个星期
	 * 对应的值：2009年07月22日15时（03时）05分30秒530毫秒星期三本月第4个星期
	 * @param format	日期时间的格式
	 * @return			当前日期和时间
	 */
	public static String getCurrentDateTime(String format) {
		SimpleDateFormat df = new SimpleDateFormat(format);
		return df.format(new Date());
	}
	
	/**
	 * 获取昨天的日期（固定格式：yyyy-MM-dd）
	 * @return	日期
	 */
	public static String getYesterday() {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		Calendar c = Calendar.getInstance();
		c.add(Calendar.DATE, -1);
		return df.format(c.getTime());
	}
	
	/**
	 * 获取明天的日期（固定格式：yyyy-MM-dd）
	 * @return	日期
	 */
	public static String getTomorrow() {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		Calendar c = Calendar.getInstance();
		c.add(Calendar.DATE, 1);
		return df.format(c.getTime());
	}
	
	/**
	 * 把String转换成日期
	 * @param date		String型日期
	 * @return			Date型日期
	 */
	public static Date convertStringToDate(String date) throws Exception {
		SimpleDateFormat df = new SimpleDateFormat(getFormat(date));
		return df.parse(date);
	}
	
	/**
	 * 把日期类型转换成String
	 * @param date		Date型日期
	 * @param format	转换成String型日期后的格式
	 * @return			String型日期
	 */
	public static String convertDateToString(Date date, String format) {
		SimpleDateFormat df = new SimpleDateFormat(format);
		return df.format(date);
	}
	
	/**
	 * 日期时间格式转换
	 * @param value			转换前的值
	 * @param format	转换后的格式
	 * @return				转换后的值
	 */
	public static String dateFormat(String value, String format) {
		try {
			Date date = convertStringToDate(value);
			return convertDateToString(date, format);
		} catch (Exception e) {
			return "";
		}
	}
	
	public static String dateFormat(Date date, String format) {
		try {
			return convertDateToString(date, format);
		} catch (Exception e) {
			return "";
		}
	}
	
	/**
	 * 计算两个日期的间隔（yyyy MM dd HH mm ss）
	 * @param type		间隔的单位：yyyy-年，MM-月，dd-日，HH-小时，mm-分钟，ss-秒，不填默认为日
	 * @param sdate1	String型日期
	 * @param sdate2	String型日期
	 * @return			间隔的数量。如果日期sdate2在日期sdate1之后，则结果为正数；如果日期sdate2在日期sdate1之前，则结果为负数
	 */
	public static int dateDiff(String type, String sdate1, String sdate2) throws Exception {
		Date date1 = new SimpleDateFormat(getFormat(sdate1)).parse(sdate1);
		Date date2 = new SimpleDateFormat(getFormat(sdate2)).parse(sdate2);
		Calendar cal1 = Calendar.getInstance();
		Calendar cal2 = Calendar.getInstance();
		cal1.setTime(date1);
		cal2.setTime(date2);
		int yearDiff = cal2.get(Calendar.YEAR) - cal1.get(Calendar.YEAR);
		if("yyyy".equals(type)){//年
			return yearDiff;
		}else if("MM".equals(type)){//月
			int monthDiff = yearDiff * 12 + cal2.get(Calendar.MONTH) - cal1.get(Calendar.MONTH);
			return monthDiff;
		}else {
			long ldate1 = date1.getTime() + cal1.get(Calendar.ZONE_OFFSET) + cal1.get(Calendar.DST_OFFSET);
			long ldate2 = date2.getTime() + cal2.get(Calendar.ZONE_OFFSET) + cal2.get(Calendar.DST_OFFSET);
			if("HH".equals(type)) {//小时
				return (int)((ldate2 - ldate1) / (3600000));
			}else if("mm".equals(type)) {//分钟
				return (int)((ldate2 - ldate1) / (60000));
			}else if("ss".equals(type)) {//秒
				return (int)((ldate2 - ldate1) / (1000));
			}else {//日
				return (int)((ldate2 - ldate1) / (3600000 * 24));
			}
		}
	}
	
	/**
	 * 计算日期加上一段间隔之后的新日期
	 * @param type		间隔的单位：yyyy-年，MM-月，dd-日，HH-小时，mm-分钟，ss-秒，不填默认为日
	 * @param sdate		String型日期
	 * @param num		间隔数量
	 * @return			返回新日期
	 */
	public static String dateAdd(String type, String sdate, int num) throws Exception{
		SimpleDateFormat df = new SimpleDateFormat(getFormat(sdate));
		Date date = df.parse(sdate);
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		if("yyyy".equals(type)){
			cal.add(Calendar.YEAR, num);
		}else if("MM".equals(type)){
			cal.add(Calendar.MONTH, num);
		}else if("HH".equals(type)){
			cal.add(Calendar.HOUR, num);
		}else if("mm".equals(type)){
			cal.add(Calendar.MINUTE, num);
		}else if("ss".equals(type)){
			cal.add(Calendar.SECOND, num);
		}else {
			cal.add(Calendar.DATE, num);
		}
		return df.format(cal.getTime());
	}
	
	
	public static void main(String args[]) throws Exception{
		
	}
}
