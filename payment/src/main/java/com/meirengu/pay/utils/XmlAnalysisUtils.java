package com.meirengu.pay.utils;

import com.meirengu.pay.vo.WxPayReturnData;
import com.meirengu.pay.vo.WxPaySendData;
import com.meirengu.utils.StringUtil;
import com.thoughtworks.xstream.XStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * ${DESCRIPTION}
 *
 * @author 建新
 * @create 2017-02-13 18:01
 */
public class XmlAnalysisUtils {

    private static XStream xStream;

    private static Logger LOGGER = LoggerFactory.getLogger(XmlAnalysisUtils.class);
    /**
     * 将entity转换为xml
     * @param t
     * @param <T>
     * @return
     * @throws NoSuchMethodException
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     */
    public static <T> String entityToXml(T t) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {

        StringBuilder sb = new StringBuilder();
        sb.append("<xml>");

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
                sb.append("<");
                sb.append(oldName);
                sb.append(">");
                sb.append("<![CDATA[");
                sb.append(value);
                sb.append("]]>");
                sb.append("</");
                sb.append(oldName);
                sb.append(">");
            }
        }
        sb.append("</xml>");
        return sb.toString();
    }


    public static String mapToXml(Map<String, Object> map){

        StringBuilder sb = new StringBuilder();
        sb.append("<xml>");

        if(map != null){
            for (Map.Entry<String, Object> entry: map.entrySet()){
                String key = entry.getKey();
                Object value = entry.getValue();
                sb.append("<");
                sb.append(key);
                sb.append(">");
                sb.append("<![CDATA[");
                sb.append(value);
                sb.append("]]>");
                sb.append("</");
                sb.append(key);
                sb.append(">");
            }
        }
        sb.append("</xml>");
        return sb.toString();
    }

    /**
     * 将xml变成实体
     * @param xml
     * @param xmlObj
     * @return
     */
    public static Object getObjectForXMl(String xml,Class<?> xmlObj){
        xStream = null;
        xStream = new XStream();
        xml = xml.replaceAll("xml", xmlObj.getName());
        LOGGER.debug(xml.replace("<![CDATA[","").replace("]]>", ""));
        return xStream.fromXML(xml);
    }

    /**
     * 将实体类变成xml字串
     * @param obj 实体类对象
     * @return
     */
    public static String getXMLForObject(Object obj){
        xStream = null;
        xStream = new XStream();
        return xStream.toXML(obj).replaceAll("__","_").replaceAll(obj.getClass().getName(), "xml");
    }

    public static void main(String[] args){
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("aaa", "1111");
        map.put("bbb", "2222");

        System.out.println(mapToXml(map));

        /*for (Map.Entry<String, Object> entry: map.entrySet()){
            System.out.println("key="+entry.getKey()+", value="+entry.getValue());
        }*/

        WxPaySendData send = new WxPaySendData();
        send.setAppid("1111");
        send.setBody("aaaa");
        send.setLimit_pay("no_credit");
        System.out.println(getXMLForObject(send));

    }
}
