package jms;

/**
 * Created by root on 2017/6/4.
 */
public class ServerMonitor implements ServerMonitorMBean {

    private final ServerImpl target;

    public ServerMonitor(ServerImpl target){
        this.target = target;
    }

    public long getUpTime(){
        return System.currentTimeMillis() - target.startTime;
    }
}
