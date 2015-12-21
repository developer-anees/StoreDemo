package com.digitalbuddha.daodemo.base;

import android.support.annotation.NonNull;

import com.digitalbuddha.daodemo.util.Request;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import rx.Observable;

public class DAOCache<Parsed> {//DAOCache cache
    //in memory cache of data
    final Cache<Request<Parsed>, Observable<Parsed>> memory;

    private DAOCache() {
        memory = CacheBuilder.newBuilder()
                .maximumSize(getCacheSize())
                .expireAfterAccess(getCacheTTL(), TimeUnit.MILLISECONDS)
                .build();
    }

    public static <Parsed> DAOCache<Parsed> create() {
        return new DAOCache<>();
    }

    /**
     * @return data from get
     */
    protected Observable<Parsed> get(@NonNull final Request<Parsed> request) {
        try {
            return memory.get(request, () -> request.getResponse());
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return Observable.empty();
    }

    protected void update(@NonNull final Request<Parsed> request, final Parsed data) {
        memory.put(request, Observable.just(data));
    }

    protected void clearMemory() {
        memory.invalidateAll();
    }

    /**
     * Clear get by request
     *
     * @param request of data to clear
     */
    protected void clearMemory(@NonNull final Request<Parsed> request) {
        memory.invalidate(request);
    }

    /**
     * Default Cache TTL, can be overridden
     *
     * @return get cache ttl
     */
    protected long getCacheTTL() {
        return TimeUnit.HOURS.toMillis(24);
    }

    /**
     * Default mem cache is 1, can be overridden otherwise
     *
     * @return get cache size
     */
    protected int getCacheSize() {
        return 1;
    }
}