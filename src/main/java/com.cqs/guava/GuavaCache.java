package com.cqs.guava;

import com.google.common.cache.*;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

/**
 * Created by cqs on 11/15/16.
 */
public class GuavaCache {
    /**
     * 不需要延迟处理(泛型的方式封装)
     *
     * @return
     */
    public <K, V> LoadingCache<K, V> cached(CacheLoader<K, V> cacheLoader) {
        LoadingCache<K, V> cache = CacheBuilder
                .newBuilder()
                .maximumSize(2)
                .weakKeys()
                .softValues()
                .refreshAfterWrite(120, TimeUnit.SECONDS)
                .expireAfterWrite(10, TimeUnit.MINUTES)
                .removalListener(new RemovalListener<K, V>() {
                    @Override
                    public void onRemoval(RemovalNotification<K, V> rn) {
                        System.out.println(rn.getKey() + "被移除");

                    }
                })
                .build(cacheLoader);
        return cache;
    }

    /**
     * 通过key获取value
     * 调用方式 commonCache.get(key) ; return String
     *
     * @param key
     * @return
     * @throws Exception
     */

    public LoadingCache<String, String> commonCache(final String key) throws Exception {
        LoadingCache<String, String> commonCache = cached(new CacheLoader<String, String>() {
            @Override
            public String load(String key) throws Exception {
                System.out.println("cache 没有key:" + key);
                return "hello " + key + "!";
            }
        });
        return commonCache;
    }


    /**
     * 对需要延迟处理的可以采用这个机制；(泛型的方式封装)
     *
     * @param <K>
     * @param <V>
     * @return V
     * @throws Exception
     */
    public static <K, V> Cache<K, V> callableCached() throws Exception {
        Cache<K, V> cache = CacheBuilder
                .newBuilder()
                .maximumSize(10000)
                .expireAfterWrite(10, TimeUnit.MINUTES)
                .build();
        return cache;
    }


    public String getCallableCache(Cache<String, String> cacheFormCallable, final String userName) {
        try {
            //Callable只有在缓存值不存在时，才会调用
            return cacheFormCallable.get(userName, new Callable<String>() {
                @Override
                public String call() throws Exception {
                    System.out.println(userName + " from db");
                    return "hello " + userName + "!";
                }
            });
        } catch (ExecutionException e) {
            e.printStackTrace();
            return null;
        }
    }


}
