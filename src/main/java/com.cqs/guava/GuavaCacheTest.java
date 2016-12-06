package com.cqs.guava;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import org.junit.Test;

import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

/**
 * Created by cqs on 11/15/16.
 */

public class GuavaCacheTest {

    GuavaCache cache = new GuavaCache();

    @Test
    public void TestLoadingCache() throws Exception {
        LoadingCache<String, String> cacheBuilder = CacheBuilder
                .newBuilder()
                .maximumSize(2)//容量
                .expireAfterWrite(2, TimeUnit.SECONDS)
                .build(new CacheLoader<String, String>() {
                    @Override
                    public String load(String key) throws Exception {
                        System.out.println("没有找到key:" + key);
                        String strProValue = "put key:" + key;
                        return strProValue;
                    }
                });

        String jerry = cacheBuilder.get("jerry");
        System.out.println("jerry value:" + jerry);
        TimeUnit.MILLISECONDS.sleep(1500);
        jerry = cacheBuilder.get("jerry");
        System.out.println("-------------------------");
        TimeUnit.MILLISECONDS.sleep(1500);
        jerry = cacheBuilder.get("jerry");
        System.out.println("-------------------------");
        TimeUnit.MILLISECONDS.sleep(1500);
        jerry = cacheBuilder.get("jerry");
        System.out.println("-------------------------");

        String peida = cacheBuilder.get("peida");
        System.out.println("peida value:" + peida);
        cacheBuilder.put("harry", "ssdded");
        String harry = cacheBuilder.get("harry");
        System.out.println("harry value:" + harry);
    }

    @Test
    public void testCallableCache() throws Exception {
        Cache<String, String> cache = CacheBuilder.newBuilder().maximumSize(1000).build();
        String resultVal = cache.get("jerry", new Callable<String>() {
            public String call() {
                String strProValue = "hello " + "jerry" + "!";
                return strProValue;
            }
        });
        System.out.println("jerry value : " + resultVal);

        resultVal = cache.get("peida", new Callable<String>() {
            public String call() {
                String strProValue = "hello " + "peida" + "!";
                return strProValue;
            }
        });
        String ifPresent = cache.getIfPresent("333");
        System.out.println(ifPresent);
        System.out.println("peida value : " + resultVal);
    }

    @Test
    public void testCache() throws Exception {
        LoadingCache<String, String> commonCache = cache.commonCache("peida");
        String peida = commonCache.get("peida");
        System.out.println("peida:" + peida);
        peida = commonCache.get("peida");
        String harry = commonCache.get("harry");
        System.out.println("harry:" + harry);
        String lisa = commonCache.get("lisa");
        System.out.println("lisa:" + lisa);
    }

    @Test
    public void testCallableCache2() throws Exception {
        final String u1name = "peida";
        final String u2name = "jerry";
        final String u3name = "lisa";
        Cache<String, String> callableCached = GuavaCache.callableCached();
        System.out.println("peida:" + cache.getCallableCache(callableCached,u1name));
        cache.getCallableCache(callableCached,u1name);
        System.out.println("jerry:" + cache.getCallableCache(callableCached,u2name));
        cache.getCallableCache(callableCached,u2name);
        System.out.println("lisa:" + cache.getCallableCache(callableCached,u3name));
        cache.getCallableCache(callableCached,u3name);
        System.out.println("peida:" + cache.getCallableCache(callableCached,u1name));
        cache.getCallableCache(callableCached,u1name);

    }
}