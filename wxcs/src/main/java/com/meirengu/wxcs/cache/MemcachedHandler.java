/*
package com.meirengu.wxcs.cache;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.danga.MemCached.MemCachedClient;
import com.danga.MemCached.SockIOPool;
import com.uqsoft.wxcs.util.ConstUtil;

public class MemcachedHandler {
	
	private static Logger logger = LoggerFactory.getLogger(MemcachedHandler.class);
	
	private static MemCachedClient mcc;
	private SockIOPool pool;
	
	private static MemcachedHandler ins = null;
	
	private static Integer OVER_TIME = 10*60*1000;
	
	public MemcachedHandler() throws Exception {
		try {
			pool = SockIOPool.getInstance();
			pool.setServers(ConstUtil.MEMCACHED_SERVERS);
			pool.setWeights(ConstUtil.MEMCACHED_WEIGHTS);
			pool.setInitConn(ConstUtil.MEMCACHED_CONN_INIT);
			pool.setMinConn(ConstUtil.MEMCACHED_CONN_MIN);
			pool.setMaxConn(ConstUtil.MEMCACHED_CONN_MAX);
			pool.setMaxIdle(ConstUtil.MEMCACHED_IDEL_MAX);
			pool.setMaintSleep(ConstUtil.MEMCACHED_MAINTSLEEP);
			pool.setNagle(ConstUtil.MEMCACHED_NAGEL);
			pool.setSocketTO(ConstUtil.MEMCACHED_SOCKETTO);
			pool.setSocketConnectTO(ConstUtil.MEMCACHED_SOCKETCONNECTTO);
			pool.initialize();
			mcc = new MemCachedClient();
			logger.error("MemcachedHandler has be inited");
		} catch (Exception e) {
			logger.error("MemcachedHandler init failure!" + e);
			e.printStackTrace();
			throw e;
		}
	}

	public static synchronized void init() throws Exception{
		if(ins==null){
			ins = new MemcachedHandler();
		}else{
			throw new Exception("MemcachedHandler has be inited");
		}
	}
	
	private void destroyAll(){
		mcc = null;
		pool.shutDown();
	}
	
	public synchronized static void destory(){
		if(ins!=null){
			ins.destroyAll();
			ins = null;
			logger.info("MemcachedHandler destroy");
		}else{
			logger.warn("MemcachedHandler has been destroyed!");
		}
	}	
	
	private MemCachedClient getMcc(){
		return mcc;
	}

	public static MemCachedClient getMemCachedClient(){
		return ins.getMcc();
	}

	public static void store(String key, Object value) {
		if (mcc != null && key != null && key.trim().length() > 0) {
			mcc.set(key, value, new Date(OVER_TIME));
			logger.debug("memcached store element success:key={},value={}", new Object[] { key, value });
		}else {
			logger.debug("memcached store element failure:key={},value={}", new Object[] { key, value });
		}
	}
	
	public static void store(String key, Object value, Date date){
		if (mcc != null && key != null && key.trim().length() > 0) {
			mcc.set(key, value, date);
			logger.debug("memcached store element success:key={},value={},date={}", new Object[] { key, value, date});
		}else {
			logger.debug("memcached store element failure:key={},value={},date={}", new Object[] { key, value, date});
		}
	}
	
	public static Object get(String key){
		if (mcc != null && key != null && key.trim().length() > 0){
			logger.debug("memcached get element success:key={},value={}", new Object[]{key, mcc.get(key)});
			return mcc.get(key);
		}else {
			logger.debug("memcached get element null:key={}", new Object[]{key});
			return null;
		}
	}
	
	
	public static void delete(String key) {
		if (mcc != null && key != null && key.trim().length() > 0){
			mcc.delete(key);
			logger.debug("memcached delete element success:key={}", new Object[]{key});
		}
	}

	
	public static void storeMobileAndIp(String mobileNo, String ip, String channel, String clientFeatureString, Date date){
		String memcache_key_mobile = channel+"_"+clientFeatureString+"_mobile";
		String memcache_key_ip = channel+"_"+clientFeatureString+"_ip";
		try {
			MemcachedHandler.store(memcache_key_mobile, mobileNo, date);
			MemcachedHandler.store(memcache_key_ip, ip, date);
			logger.debug("memcached store mobile and ip success:key={},value={},date={};key={},value={},date={}", new Object[] { memcache_key_mobile, mobileNo, date, memcache_key_ip, ip, date});
		} catch (Exception e) {
			logger.debug("memcached store mobile and ip failure:key={},value={},date={};key={},value={},date={}", new Object[] { memcache_key_mobile, mobileNo, date, memcache_key_ip, ip, date});
		}
	}
	
	
	public static void clear() {
		if (mcc != null){
			mcc.flushAll();
			logger.debug("memcached flushAll");
		}	
	}

	public static long incr(String key, long delta, int exp) {
	    long counter = -1L;
        try {
            if (mcc != null) {
                counter = mcc.incr(key, delta, exp);
            }
        } catch (Exception e) {
            logger.error("incr error: ", e);
        } 
        return counter;
	}
	
	public static long addOrIncr(String key, long delta) {
	    long counter = -1L;
        try {
            if (mcc != null) {
                counter = mcc.addOrIncr(key, delta, 1000*60*60*24);
            }
        } catch (Exception e) {
            logger.error("incr error: ", e);
        } 
        return counter;
	}

	public static void main (String [] args) {
	    SockIOPool pool = SockIOPool.getInstance();
        pool.setServers(new String[]{"192.168.141.65:11211"});
        pool.initialize();
        mcc = new MemCachedClient();
        //clear();
        String key = "wap.test_c_2013-5-16";
       //mcc.delete(key);
        long l = mcc.addOrIncr(key, 1);
       System.out.println(l);
       System.out.println(mcc.get(key));
//        MemcachedItem i = mcc.gets(key);
//        
//        System.out.println(i.getValue());
//        
//        mcc.cas(key, 13, i.getCasUnique());
//        
//        System.out.println(mcc.get(key));
        
        
	} 
}
*/
