package com.meirengu.medical.service.impl;

import com.meirengu.medical.model.Type;
import com.meirengu.medical.service.IRedisGenerator;
import com.meirengu.medical.util.SerializeTranscoder;
import com.meirengu.medical.vo.TypeVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Author: haoyang.Yu
 * Create Date: 2017/2/10 11:55.
 */
@Service
public abstract class RedisGeneratorImpl<K extends Serializable, V extends Serializable> implements IRedisGenerator {
    @Autowired
    protected RedisTemplate<K,V> redisTemplate ;
    @Autowired
    protected Jedis jedis;

    /**
     * 设置redisTemplate
     * @param redisTemplate the redisTemplate to set
     */
    public void setRedisTemplate(RedisTemplate<K, V> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    /**
     * 获取 RedisSerializer
     * <br>------------------------------<br>
     */
    protected RedisSerializer<String> getRedisSerializer() {
        return redisTemplate.getStringSerializer();
    }
    /**
     * 向redis中添加对象
     * @param keyValue key
     * @param object value
     * @return 成功or失败
     */
    @Override
    public boolean add(String keyValue,Object object) {
        boolean result = redisTemplate.execute(new RedisCallback<Boolean>() {
            public Boolean doInRedis(RedisConnection connection)
                    throws DataAccessException {
                RedisSerializer<String> serializer = getRedisSerializer();
                byte[] key  = serializer.serialize(keyValue);
                byte[] value = serializer.serialize(object.toString());
                return connection.setNX(key, value);
            }
        });
        return result;
    }

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        SerializeTranscoder<TypeVo> serializeTranscoder = new SerializeTranscoder<TypeVo>();
        List<TypeVo> list = new ArrayList<>();
        TypeVo typeVo = new TypeVo();
        typeVo.setTypeId(1);
        typeVo.setTypeName("123");
        typeVo.setTypeSort(123);
        TypeVo typeVo2 = new TypeVo();
        typeVo2.setTypeId(2);
        typeVo2.setTypeName("234");
        typeVo2.setTypeSort(234);
        list.add(typeVo);
        list.add(typeVo2);
        byte[] result1 = serializeTranscoder.objectToSerialize(typeVo);
        TypeVo typeVo1=serializeTranscoder.deserializeToObject(result1);
        System.out.println(typeVo1.toString());
        byte[] result = serializeTranscoder.listToSerialize(list);
        List<TypeVo> list1=serializeTranscoder.deserializeToList(result);
        System.out.println(list1);
    }
}
