package com.tangxiaolv.router.utils;

/**
 * Record router time from[call] to [resolve/reject]
 */
public class PromiseTimer {

    private long start;

    public PromiseTimer() {
        start = System.currentTimeMillis();
    }

    public String getTime() {
        return String.valueOf(System.currentTimeMillis() - start);
    }
}
