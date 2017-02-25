package com.meirengu.common;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StopWatch;

import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.JedisShardInfo;
import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPool;

/**
 * 全局状态码常量类
 *
 * @author Marvin
 * @create 2017-02-09 下午3:22
 */
public class RedisClient {
    
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private String cachePrefix = "MRG";
    private ShardedJedisPool shardedJedisPool;
    private String redisInstanceSelfID = null;

    public RedisClient(JedisPoolConfig poolConfig, String hosts) {

        List<JedisShardInfo> shards = new ArrayList<JedisShardInfo>();
        String[] urls = hosts.split("\\|");
        if (urls != null) {
            for (String url : urls) {
                if (!org.springframework.util.StringUtils.isEmpty(url)) {
                    String[] pair = url.split(":");
                    JedisShardInfo shard = new JedisShardInfo(pair[0], Integer.parseInt(pair[1]));
                    shards.add(shard);
                }
            }
        }
        shardedJedisPool = new ShardedJedisPool(poolConfig, shards);
        redisInstanceSelfID = java.util.UUID.randomUUID().toString();
    }

    public RedisClient(ShardedJedisPool shardedJedisPool) {
        this.shardedJedisPool = shardedJedisPool;
    }

    ShardedJedisPool getShardedJedisPool() {
        return shardedJedisPool;
    }

    public String getCachePrefix() {
        return this.cachePrefix;
    }

    public void setCachePrefix(String cachePrefix) {
        if (cachePrefix == null || cachePrefix.trim().equals(""))
            return;
        this.cachePrefix = cachePrefix;
    }

    /**
     * 获取完整的key
     * 
     * @param key
     * @return
     */
    protected String getPrefixKey(String key, boolean needSelfID) {
        if (this.cachePrefix != null) {
            if (needSelfID) {
                return this.cachePrefix + "#" + redisInstanceSelfID + "#" + key;
            }
            return this.cachePrefix + "#" + key;
        } else
            return key;
    }

    public boolean exists(String key) {
        return exists(key, true);
    }

    public boolean exists(String key, boolean needSelfID) {
        Boolean exist = false;
        ShardedJedis jedis = getShardedJedisPool().getResource();
        try {
            exist = jedis.exists(this.getPrefixKey(key, needSelfID));
        } finally {
            getShardedJedisPool().returnResource(jedis);
        }
        return exist;
    }

    public String get(String key) {
        ShardedJedis jedis = getShardedJedisPool().getResource();
        try{
            return jedis.get(key);
        }finally{
            getShardedJedisPool().returnResource(jedis);
        }
    }
    
    public void set(String key,String value) {
        ShardedJedis jedis = getShardedJedisPool().getResource();
        try{
            jedis.set(key, value);
        }finally{
            getShardedJedisPool().returnResource(jedis);
        }
    }
    
    public void set(String key,String value, boolean needSelfID) {
        ShardedJedis jedis = getShardedJedisPool().getResource();
        try{
            jedis.set(this.getPrefixKey(key, needSelfID), value);
        }finally{
            getShardedJedisPool().returnResource(jedis);
        }
    }

    public String get(String key, boolean needSelfID) {
        StopWatch watch = new StopWatch();
        watch.start();
        String value = null;
        ShardedJedis jedis = getShardedJedisPool().getResource();
        try {
            value = jedis.get(this.getPrefixKey(key, needSelfID));
        } finally {
            watch.stop();
            logger.debug("get:" + key + ":time" + watch.getTotalTimeMillis());
            getShardedJedisPool().returnResource(jedis);
        }
        return value;
    }

    public long expire(String key, int seconds) {
        return expire(key, seconds, true);
    }

    /**
     * 设置过期时间
     * 
     * @param key
     * @param seconds
     * @return
     */
    public long expire(String key, int seconds, boolean needSelfID) {
        ShardedJedis jedis = getShardedJedisPool().getResource();
        try {
            return jedis.expire(this.getPrefixKey(key, needSelfID), seconds);
        } finally {
            getShardedJedisPool().returnResource(jedis);
        }
    }

    public long remove(String key) {
        return remove(key, true);
    }

    /**
     * 删除
     * 
     * @param key 缓存KEY
     */
    public long remove(String key, boolean needSelfID) {
        StopWatch watch = new StopWatch();
        watch.start();
        ShardedJedis jedis = getShardedJedisPool().getResource();
        try {
            return jedis.del(this.getPrefixKey(key, needSelfID));
        } finally {
            watch.stop();
            logger.debug("remove:" + key + ":time" + watch.getTotalTimeMillis());
            getShardedJedisPool().returnResource(jedis);
        }
    }

    public void put(String key, String value) {
        put(key, value, true);
    }

    public void put(String key, String value, boolean needSelfID) {
        StopWatch watch = new StopWatch();
        watch.start();
        ShardedJedis jedis = getShardedJedisPool().getResource();
        try {
            jedis.set(getPrefixKey(key, needSelfID), value);
        } finally {
            getShardedJedisPool().returnResource(jedis);
        }
        watch.stop();
        logger.debug("put:" + key + ":time" + watch.getTotalTimeMillis());
    }

    public void put(String key, String value, int seconds) {
        put(key, value, seconds, true);
    }

    public void put(String key, String value, int seconds, boolean needSelfID) {
        put(key, value, needSelfID);
        expire(key, seconds, needSelfID);
    }

    public void sadd(String key, String value) {
        sadd(key, value, true);
    }

    public void sadd(String key, String value, boolean needSelfID) {
        // ShardedJedis jedis = getShardedJedisPool().getResource();
        ShardedJedis jedis = getShardedJedisPool().getResource();
        try {
            jedis.sadd(getPrefixKey(key, needSelfID), value);
        } finally {
            // getShardedJedisPool().returnResource(jedis);
            getShardedJedisPool().returnResource(jedis);
        }
    }

    public void srem(String key, String value) {
        srem(key, value, true);
    }

    public void srem(String key, String value, boolean needSelfID) {
        StopWatch watch = new StopWatch();
        watch.start();
        // ShardedJedis jedis = getShardedJedisPool().getResource();
        ShardedJedis jedis = getShardedJedisPool().getResource();
        try {
            String host = jedis.getShard(this.getPrefixKey(key, needSelfID)).getClient().getHost();
            int port = jedis.getShard(this.getPrefixKey(key, needSelfID)).getClient().getPort();
            logger.debug("host = " + host + ", port = " + port);
            jedis.srem(getPrefixKey(key, needSelfID), value);
        } finally {
            // getShardedJedisPool().returnResource(jedis);
            getShardedJedisPool().returnResource(jedis);
        }
        watch.stop();
        logger.debug("srem:" + key + ":time" + watch.getTotalTimeMillis());
    }

    public Set<String> smembers(String key) {
        return smembers(key, true);
    }

    public Set<String> smembers(String key, boolean needSelfID) {
        StopWatch watch = new StopWatch();
        watch.start();
        ShardedJedis jedis = getShardedJedisPool().getResource();
        try {
            String host = jedis.getShard(this.getPrefixKey(key, needSelfID)).getClient().getHost();
            int port = jedis.getShard(this.getPrefixKey(key, needSelfID)).getClient().getPort();
            logger.debug("host = " + host + ", port = " + port);
            Set<String> members = jedis.smembers(getPrefixKey(key, needSelfID));
            return members;
        } finally {
            watch.stop();
            logger.debug("smembers:" + key + ":time" + watch.getTotalTimeMillis());
            getShardedJedisPool().returnResource(jedis);
        }
    }
    
    public boolean sismember(String key, String member, boolean needSelfID){
        ShardedJedis jedis = getShardedJedisPool().getResource();
        try {
            Boolean isMember = jedis.sismember(getPrefixKey(key, needSelfID), member);
            return isMember;
        } finally {
            getShardedJedisPool().returnResource(jedis);
        }
    }
    
    public void hset(String key, String field, String value, boolean needSelfID){
        StopWatch watch = new StopWatch();
        watch.start();
        ShardedJedis jedis = getShardedJedisPool().getResource();
        try {
            String host = jedis.getShard(this.getPrefixKey(key, needSelfID)).getClient().getHost();
            int port = jedis.getShard(this.getPrefixKey(key, needSelfID)).getClient().getPort();
            logger.debug("host = " + host + ", port = " + port);
            jedis.hset(getPrefixKey(key, needSelfID), field, value);
        } finally {
            watch.stop();
            logger.debug("hset:" + key + ":time" + watch.getTotalTimeMillis());
            getShardedJedisPool().returnResource(jedis);
        }
    }
    
    public Map<String,String> hgetAll(String key, boolean needSelfID){
        StopWatch watch = new StopWatch();
        watch.start();
        ShardedJedis jedis = getShardedJedisPool().getResource();
        try {
            String host = jedis.getShard(this.getPrefixKey(key, needSelfID)).getClient().getHost();
            int port = jedis.getShard(this.getPrefixKey(key, needSelfID)).getClient().getPort();
            logger.debug("host = " + host + ", port = " + port);
            return jedis.hgetAll(getPrefixKey(key, needSelfID));
        } finally {
            watch.stop();
            logger.debug("hset:" + key + ":time" + watch.getTotalTimeMillis());
            getShardedJedisPool().returnResource(jedis);
        }
    }

    public void incr(String key, int seconds, boolean needSelfID) {
        incr(key, needSelfID);
        expire(key, seconds, needSelfID);
    }
    
    public Long incr(String key){
        return this.incr(key, true);
    }
    
    public Long incr(String key, boolean needSelfID) {
        StopWatch watch = new StopWatch();
        watch.start();
        ShardedJedis jedis = getShardedJedisPool().getResource();
        try {
            //String host = jedis.getShard(this.getPrefixKey(key, needSelfID)).getClient().getHost();
            //int port = jedis.getShard(this.getPrefixKey(key, needSelfID)).getClient().getPort();
            //logger.debug("host = " + host + ", port = " + port);
            return jedis.incr(getPrefixKey(key, needSelfID));
        } finally {
            watch.stop();
            logger.debug("hset:" + key + ":time" + watch.getTotalTimeMillis());
            getShardedJedisPool().returnResource(jedis);
        }
    }
    
    public void eadd(String key, String member, long score){
        ShardedJedis jedis = getShardedJedisPool().getResource();
        long n = new Date().getTime();
        jedis.zadd(key, n, member);
        jedis.zremrangeByScore(key, 0, n - score);
        getShardedJedisPool().returnResource(jedis);
    }
    public void eadd(String key, String member){
        ShardedJedis jedis = getShardedJedisPool().getResource();
        long n = new Date().getTime();
        jedis.zadd(key, n, member);
        getShardedJedisPool().returnResource(jedis);
    }
    public void erem(String key, String member){
        ShardedJedis jedis = getShardedJedisPool().getResource();
        jedis.zrem(key, member);
        getShardedJedisPool().returnResource(jedis);
    }
    public Long ecard(String key, long score){
        ShardedJedis jedis = getShardedJedisPool().getResource();
        long n = new Date().getTime();
        jedis.zremrangeByScore(key, 0, n - score);
        getShardedJedisPool().returnResource(jedis);
        return jedis.zcard(key);
    }
    
    public static void main(String[] args) {
        JedisPoolConfig config = new JedisPoolConfig();
        RedisClient redisService = new RedisClient(config, "192.168.0.135:6379");
        
        ShardedJedis jedis = redisService.getShardedJedisPool().getResource();
        String key = "test_incr1";
        
        long a = jedis.incr(key);
       
        System.out.println(a);
        System.out.println(jedis.get(key));
      
    }

    public String getRedisInstanceSelfID() {
        return redisInstanceSelfID;
    }

    public void setRedisInstanceSelfID(String redisInstanceSelfID) {
        this.redisInstanceSelfID = redisInstanceSelfID;
    }
}
