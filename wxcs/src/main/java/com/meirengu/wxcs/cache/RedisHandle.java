/*
package com.meirengu.wxcs.cache;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPoolConfig;

*/
/**
 * ${DESCRIPTION}
 *
 * @author 建新
 * @create 2017-02-16 14:52
 *//*

@Configuration
@EnableCaching
public class RedisHandle extends CachingConfigurerSupport {

    private static Logger logger = LoggerFactory.getLogger(RedisHandle.class);

    private static RedisHandle ins = null;

    private static Integer OVER_TIME = 10*60*1000;

    private static JedisConnectionFactory jedisConnectionFactory = null;

    private static JedisPoolConfig poolConfig;


    public RedisHandle() throws Exception{

        if(jedisConnectionFactory == null){
            jedisConnectionFactory = new JedisConnectionFactory();
        }
        jedisConnectionFactory.setHostName("192.168.1.166");
        jedisConnectionFactory.setPort(6379);
        //应用连接池
        jedisConnectionFactory.setUsePool(true);
        poolConfig = jedisConnectionFactory.getPoolConfig();
        poolConfig.setMaxIdle(6);
        Jedis
    }

    public static synchronized void init() throws Exception{
        if(ins == null){
            ins = new RedisHandle();
        }else {
            throw new Exception("RedisHandle has been inited");
        }
    }

    public static String get(String key){

        return "";
    }

    public static void set(String key, String value){

    }


    @Bean
    public JedisConnectionFactory redisConnectionFactory() {
        JedisConnectionFactory redisConnectionFactory = new JedisConnectionFactory();

        // Defaults
        redisConnectionFactory.setHostName("192.168.1.166");
        redisConnectionFactory.setPort(6379);
        return redisConnectionFactory;
    }

    @Bean
    public RedisTemplate<String, String> redisTemplate(RedisConnectionFactory cf) {
        RedisTemplate<String, String> redisTemplate = new RedisTemplate<String, String>();
        redisTemplate.setConnectionFactory(cf);
        return redisTemplate;
    }

    @Bean
    public CacheManager cacheManager(RedisTemplate redisTemplate) {
        RedisCacheManager cacheManager = new RedisCacheManager(redisTemplate);

        // Number of seconds before expiration. Defaults to unlimited (0)
        cacheManager.setDefaultExpiration(3000); // Sets the default expire time (in seconds)
        return cacheManager;
    }

}
*/
