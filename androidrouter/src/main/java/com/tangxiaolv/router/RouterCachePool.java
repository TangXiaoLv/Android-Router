package com.tangxiaolv.router;

import android.util.LruCache;

import com.tangxiaolv.router.interfaces.IMirror;

import java.util.HashMap;

/**
 * Cache pool of {@link Promise} and {@link IMirror}
 */
class RouterCachePool {

    private final LruCache<String, IMirror> mirrorPool = new LruCache<>(20);
    private final HashMap<String, VPromise> promisePool = new HashMap<>();

    synchronized void addToPromisePool(String tag, VPromise p) {
        if (tag != null && p != null)
            promisePool.put(tag, p);
    }

    synchronized VPromise popPromise(String tag) {
        return promisePool.remove(tag);
    }

    synchronized void addToMirrorPool(String key, IMirror m) {
        if (key != null && m != null && mirrorPool.get(key) == null)
            mirrorPool.put(key, m);
    }

    synchronized IMirror findMirrorByName(String key) {
        if (key != null)
            return mirrorPool.get(key);
        return null;
    }

    void removePromiseByTag(String tag) {
        popPromise(tag);
    }

    int getPromisePoolSize() {
        return promisePool.size();
    }
}
