package jms;

import javax.management.*;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

/**
 * Created by root on 2017/6/4.
 */
public class DynamicServerMonitor implements DynamicMBean{
    private final ServerImpl target;
    private MBeanInfo mBeanInfo;

    public DynamicServerMonitor(ServerImpl target){
        this.target = target;
    }

    //实现获取被管理的ServerImpl的upTime
    public long upTime(){
        return System.currentTimeMillis() - target.startTime;
    }

    //javax.management.MBeanServer 会通过查询 getAttribute("Uptime") 获得 "Uptime" 属性值
    public Object getAttribute(String attribute){
        if (attribute.equals("UpTime")) {
            return upTime();
        } else {
            return null;
        }
    }

    // 给出DynamicServerMonitor的元信息
    public MBeanInfo getMBeanInfo() {
        if (mBeanInfo == null) {
            try {
                Class cls = this.getClass();
                // 用反射获得"upTime"属性的读方法
                Method readMethod = cls.getMethod("upTime", new Class[0]);
                // 用反射获得构造方法
                Constructor constructor = cls.getConstructor(new Class[]{ServerImpl.class});
                // 关于“upTime”属性的元信息：名称为UpTime，只读属性（没有写方法）。
                MBeanAttributeInfo upTimeMBeanAttributeInfo = new MBeanAttributeInfo(
                        "UpTime","The time span since server start",readMethod,null);
                // 关于构造函数的元信息
                MBeanConstructorInfo mBeanConstructorInfo = new MBeanConstructorInfo(
                        "Constructor for ServerMonitor", constructor);
                mBeanInfo = new MBeanInfo(cls.getName(), "Monitor that controls the server",
                        new MBeanAttributeInfo[]{upTimeMBeanAttributeInfo},
                        new MBeanConstructorInfo[]{mBeanConstructorInfo},null,null);

            } catch (Exception e) {
                throw new Error(e);
            }
        }
        return mBeanInfo;
    }

    public AttributeList getAttributes(String[] arg0) {
        return null;
    }

    public Object invoke(String arg0, Object[] arg1, String[] arg2) throws MBeanException,ReflectionException {
        return null;
    }

    public void setAttribute(Attribute arg0) throws AttributeNotFoundException,InvalidAttributeValueException,MBeanException
    ,ReflectionException{
        return;
    }

    public AttributeList setAttributes(AttributeList arg0){
        return null;
    }

}
