package com.gaoxi.redis.service;

/**
 * @author 大闲人柴毛毛
 * @date 2017/11/1 下午3:15
 * @description
 */
import java.io.Serializable;
import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import com.alibaba.dubbo.config.annotation.Service;
import com.gaoxi.facade.redis.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

@org.springframework.stereotype.Service
@Service(version = "1.0.0")
public class RedisServiceImpl implements RedisService {

    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 批量删除对应的value
     *
     * @param keys
     */
    @Override
    public void remove(final String... keys) {
        for (String key : keys) {
            remove(key);
        }
    }

    /**
     * 批量删除key
     *
     * @param pattern
     */
    @Override
    public void removePattern(final String pattern) {
        Set<Serializable> keys = redisTemplate.keys(pattern);
        if (keys.size() > 0) {
            redisTemplate.delete(keys);
        }
    }

    /**
     * 删除对应的value
     *
     * @param key
     */
    @Override
    public void remove(final String key) {
        if (exists(key)) {
            redisTemplate.delete(key);
        }
    }

    /**
     * 判断缓存中是否有对应的value
     *
     * @param key
     * @return
     */
    @Override
    public boolean exists(final String key) {
        return redisTemplate.hasKey(key);
    }

    /**
     * 读取缓存
     *
     * @param key
     * @return
     */
    @Override
    public Serializable get(final String key) {
        Object result = null;
        ValueOperations<Serializable, Object> operations = redisTemplate.opsForValue();
        result = operations.get(key);
        return result.toString();
    }

    /**
     * 写入缓存
     *
     * @param key
     * @param value
     * @return
     */
    @Override
    public boolean set(final String key, Serializable value) {
        boolean result = false;
        try {
            ValueOperations<Serializable, Object> operations = redisTemplate.opsForValue();
            operations.set(key, value);
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 写入缓存
     *
     * @param key
     * @param value
     * @return
     */
    @Override
    public boolean set(final String key, Serializable value, Long expireTime) {
        boolean result = false;
        try {
            ValueOperations<Serializable, Object> operations = redisTemplate.opsForValue();
            operations.set(key, value);
            redisTemplate.expire(key, expireTime, TimeUnit.SECONDS);
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 存储map
     * @param key
     * @param map
     * @param expireTime 失效时间为null则永久生效（单位秒）
     * @return
     */
    @Override
    public <K,HK,HV> boolean setMap(K key, Map<HK, HV> map, Long expireTime){
        return true;
    }


    /**
     * 获取map
     * @param key
     * @param <K>
     * @param <HK>
     * @param <HV>
     * @return
     */
    @Override
    public <K,HK,HV> Map<HK,HV> getMap(final K key){
        return  new Map<HK, HV>() {
            @Override
            public int size() {
                return 0;
            }

            @Override
            public boolean isEmpty() {
                return false;
            }

            @Override
            public boolean containsKey(Object key) {
                return false;
            }

            @Override
            public boolean containsValue(Object value) {
                return false;
            }

            @Override
            public HV get(Object key) {
                return null;
            }

            @Override
            public HV put(HK key, HV value) {
                return null;
            }

            @Override
            public HV remove(Object key) {
                return null;
            }

            @Override
            public void putAll(Map<? extends HK, ? extends HV> m) {

            }

            @Override
            public void clear() {

            }

            @Override
            public Set<HK> keySet() {
                return null;
            }

            @Override
            public Collection<HV> values() {
                return null;
            }

            @Override
            public Set<Entry<HK, HV>> entrySet() {
                return null;
            }
        };
    }
}
