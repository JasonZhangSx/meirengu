package jms;

import javax.management.MBeanServer;
import javax.management.MBeanServerFactory;
import javax.management.ObjectName;

/**
 * Created by root on 2017/6/4.
 */
public class DynamicTest {
    private static ObjectName objectName;
    private static MBeanServer mBeanServer;

    private static void init() throws Exception{
        ServerImpl serverImpl = new ServerImpl();
        DynamicServerMonitor serverMonitor = new DynamicServerMonitor(serverImpl);
        mBeanServer = MBeanServerFactory.createMBeanServer();
        objectName = new ObjectName("objectName:id=ServerMonitor1");
        mBeanServer.registerMBean(serverMonitor, objectName);
    }

    public static void manage() throws Exception{
        System.out.println("MBeanCount: " + mBeanServer.getMBeanCount());
        System.out.println("MBeanInfo: " + mBeanServer.getMBeanInfo(objectName));
        Long upTime = (Long)mBeanServer.getAttribute(objectName, "UpTime");
        System.out.println(upTime);
    }
    public static void main(String[] args) throws Exception{
        init();
        manage();
    }
}
