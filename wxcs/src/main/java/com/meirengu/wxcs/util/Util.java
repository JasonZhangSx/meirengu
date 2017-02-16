/**
 * 
 */
package com.meirengu.wxcs.util;

import java.io.File;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.StringTokenizer;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Util {
    
    private static final Logger logger = LoggerFactory.getLogger(Util.class);
    
    /**
     * 截断字符串为指定长度，添加了对双字节字符（中文）的判断，以使截断的字符串不会出现乱码
     * sunxing 20091102 create
     * @param s 目标字符串
     * @param l 制定长度
     * @return 截断后的字符串
     */
	public static String truncate(String s, int l){
		if(s==null){
			return null;
		}
		if(s.getBytes().length<=l){
			return s;
		}
		StringBuilder sb = new StringBuilder();
		int len = 0;
		for(int i=0; i<s.length()&&len<l; i++){
			Character ch = s.charAt(i);
			int charlen = 0;
			if (ch.toString().getBytes().length == 1) {
				charlen = 1;
			}else{
				charlen = 2;
			}
			if(len+charlen<=l){
				sb.append(ch.charValue());
				len += charlen;
			}else{
				break;
			}
		}
		return sb.toString();
	}

	/**
	 * 将字符串按照最大长度截成字符串数组，添加了对双字节字符（中文）的判断，以使截断的字符串不会出现乱码
	 * @param s 目标字符串
	 * @param l 制定长度
	 * @return 截断后的字符串数组
	 */
	public static String[] split(String s, int l){
//		if(l<2){
//			return null;
//		}
//		if(s==null){
//			return null;
//		}
//		if(s.getBytes().length<=l){
//			return new String[]{s};
//		}
//		String[] sp = new String[s.getBytes().length%l==0?s.getBytes().length/l:s.getBytes().length/l+1];
//		for(int i=0; i<sp.length; i++){
//			sp[i] = truncate(s, l);
//			s = s.substring(sp[i].length());
//		}
//		return sp;
		return split1(s, l);
	}
	
	/**
	 * 将字符串按照最大长度截成字符串数组
	 * maxianzhong 20091214 create
	 * @param s 目标字符串
	 * @param l 制定长度
	 * @return 截断后的字符串数组
	 */
	public static String[] split1(String s, int l){
		if (s == null || s.trim().length() <= 0) {
			return null;
		}
		if (s.length() < l) {
			return new String[]{s};
		}
		String[] sp = new String[s.length()%l == 0 ? s.length()/l : s.length()/l + 1];
		for(int i=0; i < sp.length; i++){
			sp[i] = truncate1(s, l);
			s = s.substring(sp[i].length());
		}
		return sp;
	}
	
    /**
     * 截断字符串为指定长度
     * maxianzhong 20091214 create
     * @param s 目标字符串
     * @param l 制定长度
     * @return 截断后的字符串
     */
	public static String truncate1(String s, int l){
		if (s == null || s.trim().length() <= 0) {
			return "";
		}
		StringBuilder sb = new StringBuilder();
		int len = 0;
		for (int i = 0; i < s.length() && len < l; i++) {
			sb.append(s.charAt(i));
			len++;
		}
		return sb.toString();
	}
	
    /**
     * 判断字符串是否是空 这里空串确认条件是： null； 长度为0的串 全部由空格组成的串
     */
    public static boolean isEmptyStr(String target) {
        return target == null ? true : target.trim().length() == 0 ? true : false;
    }

    /**
     * 判断字符串是否是数字
     * 
     * @param str
     * @return
     */
    public static boolean isNumeric(String str){
        if (isEmptyStr(str)) {
            return false;
        }
        Pattern pattern = Pattern.compile("[0-9]*");
        return pattern.matcher(str).matches();   
    }
    
    /**
     * 判断字符串是否是数字
     * 
     * @param str
     * @return
     */
    public static boolean isDateYYYYMMDD(String str){
        if (isEmptyStr(str)) {
            return false;
        }
        Pattern pattern = Pattern.compile("\\d{4}-\\d{2}-\\d{2}");
        return pattern.matcher(str).matches();   
    }
    
    /**
     * 获取分页起始记录
     * @param from
     * @return
     */
    public static Integer getOffset(String from){
        Integer offset = -1; //默认-1
        if (from != null) {
            try {
                offset = Integer.valueOf(from);
            }
            catch (Exception e) {
                ;
            }
        }
        offset = offset == -1 ? offset : offset - 1;
        return offset;
    }
    
    /**
     * 获取分页大小
     * @param limit
     * @return
     */
    public static Integer getSize(String limit){
        Integer size = -1;//默认-1
        if (limit != null) {
            try {
                size = Integer.valueOf(limit);
            }
            catch (Exception e) {
                ;
            }
        }
        return size;
    }
    
    /**
     * 获取MsgFlag
     * flag:一个根据sender和receiver组合的32位字符串，
     * 组合规则是: 用a扩充位数到16位(min(sender, receiver))+用a扩充位数到16位(max(sender, receiver))
     * @param idUser
     * @param idContact
     * @return
     */
    public static String getFormatMsgFlag(Long idUser, Long idContact){
        Long minId = idUser < idContact ? idUser : idContact;
        Long maxId = idUser > idContact ? idUser : idContact;
        String flag = StringUtils.leftPad(minId.toString(), 16, 'a') + StringUtils.leftPad(maxId.toString(), 16, 'a');
        return flag;
    }
    
    /**
     * 将str 转换为 xml
     * maxianzhong 20091103 create
     * @param input
     * @return
     */
    public static String conStrToWml(String input) {
        StringBuffer ret = new StringBuffer();
        int len = input.length();
        for (int i = 0; i < len; i++) {
            char c = input.charAt(i);
            switch (c) {
            case '<':
                ret.append("&lt;");
                break;
            case '>':
                ret.append("&gt;");
                break;
            case '&':
                ret.append("&amp;");
                break;
            case '"':
                ret.append("&quot;");
                break;
            case '\'':
                ret.append("&apos;");
                break;
            case '$':
                ret.append("$$");
                break;
            case 0x1f:
                break;
            default:
                ret.append(c);
                break;
            }
        }
        return ret.toString();
    }
    
    /**
     * 对字符串进行url编码.
     * 
     * @param astr 源字符串
     * @param encoding 编码
     * @return String
     */
    @SuppressWarnings("deprecation")
    public static String urlEncode(String astr, String encoding) {
        if (isEmptyStr(astr)) {
            return "";
        }
        String aRes = astr;
        try {
            if (!isEmptyStr(encoding)) {
                aRes = URLEncoder.encode(astr, encoding);
            }else {
                aRes = URLEncoder.encode(astr);
            }
        }
        catch (Exception e) {
            logger.error(e.getMessage());
        }

        return aRes;
    }

    /**
     * 对编过码的字符串进行解码.
     * 
     * @param astr String
     * @param decoding 编码方式，如果不指定使用默认编码
     * @return String
     */
    @SuppressWarnings("deprecation")
    public static String urlDecode(String astr, String decoding) {
        if (isEmptyStr(astr)) {
            return "";
        }
        String aRes = astr;
        try {
            if (!isEmptyStr(decoding)) {
                aRes = URLDecoder.decode(astr, decoding);
            }else {
                aRes = URLDecoder.decode(astr);
            }
        }
        catch (Exception e) {
            logger.error(e.getMessage());
        }

        return aRes;
    }

    /**
     * 确保文件路径存在,如果不存在,建立相关路径
     * 
     * @param dir String
     * @throws Exception
     * @return boolean 如果路径已经存在,返回ture,否则返回false
     */
    public static boolean insurePath(String dir) throws Exception {
        boolean hasExists = true;
        File f = new File(dir);
        if (!f.exists()) {
            hasExists = false;
            insurePath(getFatherDir(dir));
            f.mkdir();
        }
        f = null;
        return hasExists;
    }

    /**
     * 得到父目录 注意:目录格式应该符合当前系统文件目录格式
     * 
     * @param dir String 绝对路径
     * @throws Exception
     * @return String
     */
    public static String getFatherDir(String dir) throws Exception {
        if (dir == null) {
            throw new NullPointerException("dir参数不能空");
        }
        StringTokenizer st = new StringTokenizer(dir, File.separator);
        int n = st.countTokens();
        StringBuffer sb = new StringBuffer(File.separator);
        while (st.hasMoreTokens()) {
            n--;
            if (n > 1) {
                sb.append(st.nextToken()).append(File.separator);
            }
            else if (n == 1) {
                sb.append(st.nextToken());
            }
            else {
                break;
            }
        }
        return sb.toString();
    }

    /**
     * 删除目录
     * 
     * @param target String 绝对目录名
     * @throws Exception
     */
    public static void del(String target) throws Exception {
        File tag = new File(target);
        if (tag.exists()) {
            if (tag.isDirectory()) {
                String[] subfiles = tag.list();
                for (int i = 0; i < subfiles.length; i++) {
                    del(target.concat(File.separator).concat(subfiles[i]));
                }
            }
            tag.delete();
        }
        tag = null;
    }

    /**
     * 清除目录
     * 
     * @param dir String 绝对文件夹名
     * @throws Exception
     */
    public static void clear(String dir) throws Exception {
        File tag = new File(dir);
        if (tag.exists()) {
            if (tag.isDirectory()) {
                String[] subfiles = tag.list();
                for (int i = 0; i < subfiles.length; i++) {
                    del(dir.concat(File.separator).concat(subfiles[i]));
                }
            }
        }
        tag = null;
    }

    /**
     * 检查是否为合法手机号码（目前仅检查符合中国移动规范的手机号码）
     * 
     * @param mobileNo
     * @return
     */
    public static boolean isValidMobileNo(String mobileNo) {
        if (isEmptyStr(mobileNo)) {
            return false;
        }
		if (mobileNo.length() > 11) {
			mobileNo = mobileNo.substring(mobileNo.length() - 11);
		}
        return Pattern.matches("^(134|135|136|137|138|139|147|150|151|152|157|158|159|182|183|187|188)\\d{8}$", mobileNo) || Pattern.matches("1(3[012]|5[56]|8[56])\\d{8}", mobileNo)
        || Pattern.matches("1((33|53|8[019])\\d{8})|(349\\d{7})", mobileNo);
    }
    
    /**
     * 如果输入字符串为null，返回空串，否则返回输入字符串
     * @param target
     * @return
     */
    public static String nullToEmptyStr(String target){
    	return target==null?"":target;
    }
    
    /**
     * 如果输入字符串为null，返回默认串，否则返回输入字符串
     * @param target
     * @param defaultStr 默认串
     * @return
     */
    public static String nullToDefaultStr(String target, String defaultStr){
    	return target==null?defaultStr:target;
    }
    
    /**
     * 如果输入字符串为null或空串或全部由空格组成的串，返回默认串，否则返回输入字符串
     * @param target
     * @param defaultStr
     * @return
     */
    public static String emptyToDefaultStr(String target, String defaultStr){
    	return isEmptyStr(target)?defaultStr:target;
    }
    
    /**
     * UTC时间转换成北京时间
     * @param utcTime UTC时间
     * @return 北京时间
     */
    public static Date utcToBeijingTime(Date utcTime){
    	long tuneImpsServerTimeMs = 0L;
    	try{
    		tuneImpsServerTimeMs = Long.parseLong(PropUtil.getInstance().getProperty("tuneImpsServerTimeMs"));
    	}catch(Exception e){
    		;
    	}
    	return new Date(utcTime.getTime()+28800000L+tuneImpsServerTimeMs);
    }
    
    /**
     * 检查是否是合法email地址
     * @param email
     * @return
     */
    public static boolean isValidEmail(String email){
        if (isEmptyStr(email)) {
            return false;
        }
        return Pattern.matches("\\w+(\\.\\w+)*@\\w+(\\.\\w+)+", email);
    }
    
    /**
     * 检查是否是合法IP地址
     * @param ip
     * @return
     */
    public static boolean isValidIp(String ip){
        if (isEmptyStr(ip)) {
            return false;
        }
        return Pattern.matches("^(\\d{1,2}|1\\d\\d|2[0-4]\\d|25[0-5])\\.(\\d{1,2}|1\\d\\d|2[0-4]\\d|25[0-5])\\.(\\d{1,2}|1\\d\\d|2[0-4]\\d|25[0-5])\\.(\\d{1,2}|1\\d\\d|2[0-4]\\d|25[0-5])$", ip);
    }
    
    /**
     * 从uri中提取飞信号（sid）
     * @param uri
     * @return 飞信号或0L（如果未能取到）
     */
    public static Long getIdFetionFromUri(String uri){
/*    	可能的uri格式如:
    	sip:sid@domain;p=pool 或 
    	tel:mobile-no 或
    	sip:sid
*/
    	Long idFetion = 0L;
    	if(uri==null){
    		return idFetion;
    	}
    	if(uri.indexOf("sip:")>=0){
	    	if(uri.indexOf("@")>0){
	    		try{
	    			idFetion = Long.valueOf(uri.substring(uri.indexOf("sip:")+"sip:".length(), uri.indexOf("@")).trim());
	    		}catch(Exception e){
	    			;
	    		}
	    	}else{
	    		try{
	    			idFetion = Long.valueOf(uri.substring(uri.indexOf("sip:")+"sip:".length()).trim());
	    		}catch(Exception e){
	    			;
	    		}
	    	}
    	}
    	return idFetion;
    }

    /**
     * 从uri中提取群组号（idGroup）
     * @param uri
     * @return 群组号或0L（如果未能取到）
     */
    public static Long getIdGroupFromUri(String uri){
/*    	可能的uri格式如:
    	sip:sid@domain;p=pool 或 
    	tel:mobile-no 或
    	sip:sid
*/
    	Long idGroup = 0L;
    	if(uri==null){
    		return idGroup;
    	}
    	if(uri.indexOf("sip:PG")>=0){
	    	if(uri.indexOf("@")>0){
	    		try{
	    			idGroup = Long.valueOf(uri.substring(uri.indexOf("sip:PG")+"sip:PG".length(), uri.indexOf("@")).trim());
	    		}catch(Exception e){
	    			;
	    		}
	    	}else{
	    		try{
	    			idGroup = Long.valueOf(uri.substring(uri.indexOf("sip:PG")+"sip:PG".length()).trim());
	    		}catch(Exception e){
	    			;
	    		}
	    	}
    	}
    	return idGroup;
    }    
    
    
    /**
     * 从uri中提取手机号
     * @param uri
     * @return 手机号或""（如果未能取到）
     */
    public static String getMobileNoFromUri(String uri){
    	String mobileNo = "";
    	if(uri==null){
    		return mobileNo;
    	}
    	if(uri.indexOf("tel:")>=0){
    		mobileNo = uri.substring(uri.indexOf("tel:")+"tel:".length()).trim();
    	}
    	return mobileNo;
    }
    
    
    
    
    
	/**
	 * 提取类似{444=ddd, 333=ccc, 222=bbb, 111=aaa}字符串,封装返回map对象类型
	 * 
	 * @param keyValueStr 分隔字符串
	 * @param maxNum 分隔匹配最大数，大于maxNum的部分将忽略
	 * @return
	 */
	public static Map splitKeyValueStrToMap(String keyValueStr, int maxNum){
		Map<String, String> resultMap = new HashMap<String, String>();
		if (isEmptyStr(keyValueStr)) return null;
		try {
			keyValueStr = keyValueStr.trim();
			if ("{}".equals(keyValueStr)) return null;
			if (keyValueStr.length()-1 > 1) {
				keyValueStr = keyValueStr.substring(1, keyValueStr.length()-1).replaceAll(";p=", ";p&");
				String [] keyValueStrs = keyValueStr.split(",");
				if (keyValueStrs != null && keyValueStrs.length > 0) {
					int length = keyValueStrs.length > maxNum ? maxNum : keyValueStrs.length;
					for (int i = 0; i < length; i++) {
						String[] keyValue = keyValueStrs[i].trim().split("=");
						if (keyValue != null && keyValue.length == 2) {
							String key = keyValue[0].replaceAll(";p&", ";p=");
							String value = keyValue[1];
							resultMap.put(key, value);
						}
					}
				}
			}
		} catch (Exception e) {
			;
		}
		
		return resultMap;
	}
	
    public static String getMd5Str(String str){
		
		if(Util.isEmptyStr(str)){
			return "";
		}
	
		MessageDigest md = null;
	    try {
	        md = MessageDigest.getInstance("MD5");
	    } catch (NoSuchAlgorithmException e) {
	        e.printStackTrace();
	    }
	    md.update(str.getBytes());
	    return byte2Hex(md.digest());
	}
	
	private static String byte2Hex(byte[] paramArrayOfByte) {
        StringBuffer localStringBuffer = new StringBuffer();
        String str = "";
        for (int i = 0; i < paramArrayOfByte.length; ++i) {
            str = Integer.toHexString(paramArrayOfByte[i] & 0xFF);
            if (str.length() == 1){
                localStringBuffer.append("0");
            }
            localStringBuffer.append(str);
        }
        return localStringBuffer.toString().toUpperCase();
    }
	
	/**
	 * 获得字符串长度
	 * 
	 * @param value
	 * @return
	 */
	public static int lengthInt(String value) {
		int valueLength = 0;
		String chinese = "[\u0391-\uFFE5]";
		/* 获取字段值的长度，如果含中文字符，则每个中文字符长度为2，否则为1 */
		for (int i = 0; i < value.length(); i++) {
			/* 获取一个字符 */
			String temp = value.substring(i, i + 1);
			/* 判断是否为中文字符 */
			if (temp.matches(chinese)) {
				/* 中文字符长度为2 */
				valueLength += 2;
			} else {
				/* 其他字符长度为1 */
				valueLength += 1;
			}
		}
		return valueLength;
	}

	public static String filterPcMsg(String message){
		
		if(Util.isEmptyStr(message)){
			return "";
		}

    	message = message.replace("$", "\\$");
		message = message.replace("\n", "");
		message = message.replace("\r", "");
		message = message.replace("\u0085", "");
		message = message.replace("\u2028", "");
		message = message.replace("\u2029", "");
		
		
		int startIndex = message.indexOf("（使用电脑登录飞信");
    	if(startIndex>1){
    		message = message.substring(0, startIndex);
    	}
		
    	System.out.println(message);
    	
    	StringBuilder sb = new StringBuilder();
    	
    	boolean matches = Pattern.matches(".*<OBJECT\\s+TYPE=.*ID=.*>\\[图片\\]</OBJECT>.*", message);
		if(matches){
			
			String start = "<OBJECT";
			String  end  = "</OBJECT>";
			
			int a = message.indexOf(start);
	        while(a >= 0){
	            int b = message.indexOf(end, a+start.length());
	            if (b > 0){
	            	String bef = "";
	            	
	            	if(!Pattern.matches(".*<OBJECT\\s+TYPE=.*ID=.*>\\[图片\\]</OBJECT>.*", message.substring(a, b+end.length()))){
	            		bef = message.substring(0, b);
	            		sb.append(bef);
	            	} else {
	            	    	
	            	   if(a > 1){
	            		 bef = message.substring(0, a);
	            		 sb.append(bef);
	            	   }
	            	   
		               int next = message.indexOf(start,b+end.length());
		               if(next > 1){
		            	   sb.append("[图片]");
		               }
	            	}
	            	
	                message = message.substring(b+end.length());
	                a = message.indexOf(start);
	                
	                if(a < 0){
	                	sb.append(message);
	                }
	            }
	        }
        }
		
		return sb.toString().trim();
	}
	
	public static String getRandomString(int length) { //length表示生成字符串的长度  
	    String base = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRST0123456789";     
	    Random random = new Random();     
	    StringBuffer sb = new StringBuffer();     
	    for (int i = 0; i < length; i++) {     
	        int number = random.nextInt(base.length());     
	        sb.append(base.charAt(number));     
	    }     
	    return sb.toString();     
	 } 
	
    public static void main(String[] args) throws Exception {
    	
        System.out.println(getRandomString(16));
    }
}
