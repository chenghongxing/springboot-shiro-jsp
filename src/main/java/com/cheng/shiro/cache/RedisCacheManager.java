package com.cheng.shiro.cache;

import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.apache.shiro.cache.CacheManager;

/**
 * Created by chenghx on 2020/12/15 15:20
 * 自定义shiro缓存管理器
 */
public class RedisCacheManager implements CacheManager {
    /**
     *
     * @param cacheName 认证或授权的名称
     * @param <K>
     * @param <V>
     * @return
     * @throws CacheException
     */
    @Override
    public <K, V> Cache<K, V> getCache(String cacheName) throws CacheException {
        return new RedisCache<K,V>(cacheName);
    }
}
