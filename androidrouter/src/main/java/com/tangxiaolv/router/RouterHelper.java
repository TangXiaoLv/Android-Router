
package com.tangxiaolv.router;

import android.os.Handler;
import android.os.Looper;
import android.os.SystemClock;

import com.tangxiaolv.router.interfaces.IMirror;

final class RouterHelper {

    static final Handler HANDLER = new Handler(Looper.getMainLooper());
    private final RouterCachePool cachePool = new RouterCachePool();

    private RouterHelper() {
    }

    private static class Lazy {
        static RouterHelper sRouterHelper = new RouterHelper();
    }

    static RouterHelper getInstance() {
        return Lazy.sRouterHelper;
    }

    VPromise popPromiseByTag(String tag) {
        return cachePool.popPromise(tag);
    }

    void removePromiseByTag(String tag) {
        cachePool.removePromiseByTag(tag);
    }

    void addToPromisePool(String tag, VPromise p) {
        cachePool.addToPromisePool(tag, p);
    }

    IMirror findMirrorByKey(String key) {
        return cachePool.findMirrorByName(key);
    }

    void addToMirrorPool(String key, IMirror m) {
        cachePool.addToMirrorPool(key, m);
    }

    String genPromiseTag() {
        int promisePoolSize = cachePool.getPromisePoolSize();
        return promisePoolSize + "_" + SystemClock.currentThreadTimeMillis();
    }
}
