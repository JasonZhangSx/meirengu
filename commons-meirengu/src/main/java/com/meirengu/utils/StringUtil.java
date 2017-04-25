package com.meirengu.utils;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URLDecoder;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * <p>
 * Title: 字符串操作工具类
 * </p>
 * <p>
 * Description:
 * </p>
 * 
 * @author
 * @version
 */
public class StringUtil {

	private static final String splitStr = " "; // 分割符
	public final static String FORMAT_DATE = "yyyy-MM-dd";
	public final static String FORMAT_DATETIME = "yyyy-MM-dd HH:mm:ss";

	/**
	 * 按照分隔符把数组中的字符连接到一起
	 * 
	 * @param separator
	 * @param params
	 * @return
	 */
	public static String joinString(String separator, String... params) {
		StringBuilder tmpStr = new StringBuilder();
		// 判断参数是否正确，错误返回null
		if (isEmpty(separator) || isEmpty((Object) params)) {
			return null;
		}
		for (String str : params) {
			tmpStr.append(str).append(separator);
		}
		return tmpStr.substring(0, tmpStr.length() - 1);
	}

	/**
	 * 判断对象是否为空或者null,目前支持Map,List,String
	 * 
	 * @param obj
	 * @return
	 */

	@SuppressWarnings("rawtypes")
	public static boolean isEmpty(Object obj) {
		if (obj == null) {
			return true;
		}
		if (obj instanceof Map) {
			Map map = (Map) obj;
			if (map.isEmpty())
				return true;
		}
		if (obj instanceof List) {
			List list = (List) obj;
			if (list.isEmpty())
				return true;
		}
		if (obj instanceof String) {
			String str = (String) obj;
			if (str.isEmpty())
				return true;
		}
		return false;
	}

	/**
	 * 功能：把数组所有元素排序，并按照“参数=参数值”的模式用“&”字符拼接成字符串
	 * 
	 * @param params
	 *            需要排序并参与字符拼接的参数组
	 * @return 拼接后字符串
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static String CreateLinkString(Map params) {
		List keys = new ArrayList(params.keySet());
		Collections.sort(keys);

		String prestr = "";

		for (int i = 0; i < keys.size(); i++) {
			String key = (String) keys.get(i);
			String value = (String) params.get(key);

			if (i == keys.size() - 1) {// 拼接时，不包括最后一个&字符
				prestr = prestr + key + "=" + value;
			} else {
				prestr = prestr + key + "=" + value + "&";
			}
		}

		return prestr;
	}

	/**
	 * 功能：把数组所有元素排序，并按照“参数=参数值”的模式用“&”字符拼接成字符串
	 *
	 * @param t 需要进行排序的对象
	 * @return 拼接后字符串
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static <T> String CreateLinkString(T t) throws NoSuchMethodException, InvocationTargetException,
			IllegalAccessException {
		StringBuilder sb = new StringBuilder();
		Class<?> c = t.getClass();
		Field[] fields = c.getDeclaredFields();
		for(int i = 0; i < fields.length; i++ ){
			//获取属性的类型
			String oldName = fields[i].getName();
			//将属性的首字符大写，方便构造get，set方法
			String name = oldName.substring(0, 1).toUpperCase() + oldName.substring(1);

			Method m = c.getMethod("get"+name);
			//调用get方法获取对应的属性值
			Object value = m.invoke(t);
			if(!StringUtil.isEmpty(value)){
				sb.append(oldName).append("=").append(value).append("&");
			}
		}
		return sb.substring(0, sb.length()-1).toString();
	}

	/**
	 * 生成当前时间的字符串，格式：yyyy-MM-dd HH:mm:ss
	 * 
	 * @param flag
	 *            0 yyyy-MM-dd HH:mm:ss 1 yyyyMMddHHmmss
	 * @return
	 */
	public static String genDateStr(int flag) {
		Date date = new Date();
		if (0 == flag) {
			DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			return format.format(date);
		}
		if (1 == flag) {
			DateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
			return format.format(date);
		}
		return null;
	}

	/**
	 * 判断数组strs中是否有str这个值
	 * 
	 * @param strs
	 * @param str
	 * @return 有true 没有false
	 */
	public static boolean hasInArray(String[] strs, String str) {
		for (String tmp : strs) {
			if (tmp.equals(str)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 逐个判断数组names中的每个字符串是否为空
	 * 
	 * @param params
	 * @return 有一个为空true 都不为空 false
	 */
	public static boolean isEmpty(String... params) {
		if (params == null)
			return true;
		for (String param : params) {
			if (param == null || param.isEmpty()) {
				return true;
			} else {
				continue;
			}
		}
		return false;
	}

	/**
	 * 逐个判断数组names中的每个字符串是否为空
	 * 
	 * @param objs
	 * @return 有一个为空true 都不为空 false
	 */
	public static boolean isEmpty(Object ... objs) {
		for (Object obj : objs) {
			if (isEmpty(obj)) {
				return true;
			} else {
				continue;
			}
		}
		return false;
	}

	/**
	 * 判断字符串是不是为空，返回处理后的字符串 可以根据需要截取一定长度的字符串
	 * 
	 * @maxLength 0表示不截取，返回全部字符串 当maxLength>0时返回截取长度的字符串
	 * 
	 */
	public static String getValue(String srcValue, int maxLength) {
		if (srcValue == null || srcValue.toString().equals(""))
			return "";
		if (maxLength > 0 && srcValue.toString().length() > maxLength) {
			return srcValue.toString().substring(0, maxLength);
		} else {
			return srcValue.toString();
		}
	}

	/**
	 * 如果字符串为null或者空串返回空"" 否则直接返回
	 * 
	 * @param srcValue
	 * @return 处理后的值
	 */
	public static String getValue(String srcValue) {
		if (srcValue == null || srcValue.isEmpty())
			return "";
		return srcValue;
	}

	public static int getIntValue(String srcValue) {
		if (srcValue == null || srcValue.toString().equals(""))
			return 0;
		return Integer.parseInt(srcValue);
	}

	public static int getIntValue(HttpServletRequest request, String srcValue) {
		return getIntValue(request.getParameter(srcValue));
	}

	public static long getLongValue(String srcValue) {
		if (srcValue == null || srcValue.toString().equals(""))
			return 0;
		return Integer.parseInt(srcValue);
	}

	public static long getLongValue(HttpServletRequest request, String srcValue) {
		return getLongValue(request.getParameter(srcValue));
	}

	public static double getDoubleValue(String srcValue) {
		if (srcValue == null || srcValue.toString().equals(""))
			return 0;
		return Double.parseDouble(srcValue);
	}

	public static double getDoubleValue(HttpServletRequest request,
			String srcValue) {
		return getDoubleValue(request.getParameter(srcValue));
	}

	/**
	 * 获得前台请求参数
	 * 
	 * @param request
	 * @param requestKey
	 * @return srcValue参数对应的值
	 */
	public static String getValue(HttpServletRequest request, String requestKey) {
		String decode = null;
		try {
			String sourceValue = getValue(request.getParameter(requestKey));
			decode = URLDecoder.decode(sourceValue, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return decode;
	}

	/**
	 * 获得字符串数组的值 以逗号分隔
	 * 
	 * @param srcArray
	 * @return 以逗号分隔的值
	 */
	public static String getValue(String[] srcArray) {
		StringBuffer result = new StringBuffer();
		for (int i = 0; i < srcArray.length; i++) {
			result.append(srcArray[i] + ",");
		}
		return deleteComma(result.toString());
	}

	/**
	 * @dateValue 日期对象，可以是java.util.Date和java.sql.Date
	 * @dateType 格式化的类型,date和datetime
	 */
	public static String getValue(Object dateValue, String dateType) {
		if (dateValue == null)
			return "";
		if (dateValue instanceof java.sql.Date) {
			return dateValue.toString();
		} else if (dateValue instanceof Date) {
			if (dateType.equals("date")) {
				SimpleDateFormat sfdate = new SimpleDateFormat(
						FORMAT_DATE);
				return sfdate.format(dateValue);
			} else if (dateType.equals("datetime")) {
				SimpleDateFormat sftime = new SimpleDateFormat(
						FORMAT_DATETIME);
				return sftime.format(dateValue);
			} else {
				return "非法日期格式[" + dateType + "]";
			}
		} else {
			return "非日期类型";
		}
	}

	/**
	 * 用字符串获得日期
	 * 
	 * @throws ParseException
	 * @dateValue 日期字符串
	 * @dateType 格式化的类型,date和datetime
	 */
	public static Date getDate(String dateValue, String dateType)
			throws ParseException {
		if (dateValue == null)
			return null;
		if (dateType.equals("date")) {
			SimpleDateFormat sfdate = new SimpleDateFormat(FORMAT_DATE);
			return sfdate.parse(dateValue);
		} else if (dateType.equals("datetime")) {
			SimpleDateFormat sftime = new SimpleDateFormat(FORMAT_DATETIME);
			return sftime.parse(dateValue);
		}
		return null;
	}

	/**
	 * 用字符串获得java.sql.Date日期
	 * 
	 * @throws ParseException
	 * @dateValue 日期字符串
	 * @dateType 格式化的类型,date和datetime
	 */
	public static java.sql.Date getSqlDate(String dateValue, String dateType)
			throws ParseException {
		Date date = getDate(dateValue, dateType);
		if (date == null) {
			return null;
		}
		return new java.sql.Date(date.getTime());
	}

	/**
	 * 删除字符串最后的逗号
	 * 
	 * @param src
	 * @return
	 */
	public static String deleteComma(String src) {
		src = getValue(src);
		if (!src.endsWith(",")) {
			return src;
		}
		src = src.substring(0, src.length() - 1);
		return src;
	}

	// 取数字字符串 用 splitStr 分割
	private static String getNumberString() {
		StringBuffer buf = new StringBuffer();
		for (int i = 0; i < 10; i++) {
			buf.append(String.valueOf(i));
			buf.append(splitStr);
		}
		return buf.toString();
	}

	// 取大写字母字符串 用 splitStr 分割
	private static String getUppercase() {
		StringBuffer buf = new StringBuffer();
		for (int i = 0; i < 26; i++) {
			buf.append(String.valueOf((char) ('A' + i)));
			buf.append(splitStr);
		}
		return buf.toString();
	}

	// 取小写字母字符串 用 splitStr 分割
	private static String getLowercase() {
		StringBuffer buf = new StringBuffer();
		for (int i = 0; i < 26; i++) {
			buf.append(String.valueOf((char) ('a' + i)));
			buf.append(splitStr);
		}
		return buf.toString();
	}

	// 根据所取的字符串类型连接相应的字符串并返回
	private static String getString(String type) {
		StringBuffer pstr = new StringBuffer();
		if (type.length() > 0) {
			if (type.indexOf('i') != -1)
				pstr.append(getNumberString());
			if (type.indexOf('l') != -1)
				pstr.append(getLowercase());
			if (type.indexOf('u') != -1)
				pstr.append(getUppercase());
		}
		return pstr.toString();
	}

	/**
	 * 取随机字符串
	 * 
	 * @param length
	 *            返回随机字符串的长度
	 * @param type
	 *            要取的字符串类型: i、取数字 l、取小写字母 u、取大写字母 s、取特殊字符
	 * @return String 随机字符串
	 */
	public static String getRandomString(int length, String type) {
		String allStr = getString(type);
		String[] arrStr = allStr.split(splitStr);
		StringBuffer pstr = new StringBuffer();
		if (length > 0) {
			for (int i = 0; i < length; i++) {
				pstr.append(arrStr[new Random().nextInt(arrStr.length)]);
			}
		}
		return pstr.toString();
	}

	/**
	 * 生成随机数（32位）
	 * @return
     */
	public static String createNonceStr() {
		return UUID.randomUUID().toString().replaceAll("-", "");
	}

	/**
	 * 生成指定位数的随机数（32位之内）
	 * @param i
	 * @return
     */
	public static String createNonceStr(Integer i){
		return UUID.randomUUID().toString().replaceAll("-", "").substring(0, i);
	}

	/**
	 * 判断字符串是否为整数
	 * @param str
	 * @return
     */
	public static boolean isInteger(String str){
		Pattern pattern = Pattern.compile("[0-9]*");
		Matcher isNum = pattern.matcher(str);
		if( !isNum.matches() ){
			return false;
		}
		return true;
	}

	/**
	 * 判断字符串是否为数字
	 * @param str
	 * @return
     */
	public static boolean isNumeric(String str){
		Pattern pattern = Pattern.compile("-?[0-9]+.?[0-9]+");
		Matcher isNum = pattern.matcher(str);
		if( !isNum.matches() ){
			return false;
		}
		return true;
	}

}
