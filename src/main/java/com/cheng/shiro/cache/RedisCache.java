package com.cheng.shiro.cache;

import com.cheng.shiro.utils.ApplicationContextUtils;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.util.Collection;
import java.util.Set;

/**
 * Created by chenghx on 2020/12/15 15:24
 * 自定义Redis缓存实现
 */
public class RedisCache<k,v> implements Cache<k,v> {
    private String cacheName;

    public RedisCache(String cacheName) {
        this.cacheName = cacheName;
    }

    public RedisCache() {
    }

    @Override
    public v get(k k) throws CacheException {
        RedisTemplate redisTemplate = getRedisTemplate();
        return (v) redisTemplate.opsForHash().get(this.cacheName,k.toString());
    }

    @Override
    public v put(k k, v v) throws CacheException {
        RedisTemplate redisTemplate = getRedisTemplate();
        redisTemplate.opsForHash().put(this.cacheName,k.toString(),v);
        return null;
    }

    @Override
    public v remove(k k) throws CacheException {
        RedisTemplate redisTemplate = getRedisTemplate();
        return (v) redisTemplate.opsForHash().delete(this.cacheName,k.toString());
    }

    @Override
    public void clear() throws CacheException {
        RedisTemplate redisTemplate = getRedisTemplate();
        redisTemplate.delete(this.cacheName);
    }

    @Override
    public int size() {
        RedisTemplate redisTemplate = getRedisTemplate();
        return redisTemplate.opsForHash().size(this.cacheName).intValue();
    }

    @Override
    public Set<k> keys() {
        RedisTemplate redisTemplate = getRedisTemplate();
        return redisTemplate.opsForHash().keys(this.cacheName);
    }

    @Override
    public Collection<v> values() {
        RedisTemplate redisTemplate = getRedisTemplate();
        return redisTemplate.opsForHash().values(this.cacheName);
    }

    private RedisTemplate getRedisTemplate(){
        RedisTemplate redisTemplate = (RedisTemplate) ApplicationContextUtils.getBean("redisTemplate");
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setHashKeySerializer(new StringRedisSerializer());
        return redisTemplate;
    }
}
