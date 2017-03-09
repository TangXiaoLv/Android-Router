package com.tangxiaolv.router;

/**
 * Proxy of {@link Promise}
 */
public class CPromise {

    private Promise target;

    CPromise(Promise target) {
        this.target = target;
    }

    /**
     * Start route.Empty callback
     *
     * @return {@link CPromise}
     */
    public CPromise call() {
        return call(null, null);
    }

    /**
     * Start route.
     *
     * @param resolve Result callback
     * @return @return {@link CPromise}
     */
    public CPromise call(Resolve resolve) {
        return call(resolve, null);
    }

    /**
     * Start route
     *
     * @param reject Exception callback
     * @return @return {@link CPromise}
     */
    public CPromise call(Reject reject) {
        return call(null, reject);
    }

    /**
     * Start route
     *
     * @param resolve Result callback
     * @param reject  Exception callback
     * @return @return {@link CPromise}
     */
    public CPromise call(Resolve resolve, Reject reject) {
        target.call(resolve, reject);
        return this;
    }

    /**
     * Return the mock result.
     *
     * @param type   mock type
     * @param result mock result
     */
    public CPromise mockResolve(String type, Object result) {
        target.resolve(type, result);
        return this;
    }

    /**
     * Call on main thread.
     */
    public CPromise callOnMainThread() {
        target.setThreadFlag(Promise.FLAG_CALL_MAIN);
        return this;
    }

    /**
     * Call on Sub-thread.
     */
    public CPromise callOnSubThread() {
        target.setThreadFlag(Promise.FLAG_CALL_THREAD);
        return this;
    }

    /**
     * Return on main thread.
     */
    public CPromise returnOnMainThread() {
        target.setThreadFlag(Promise.FLAG_RETURN_MIAN);
        return this;
    }

    /**
     * Return on Sub-thread.
     */
    public CPromise returnOnSubThread() {
        target.setThreadFlag(Promise.FLAG_RETURN_THREAD);
        return this;
    }

    /**
     * Show this time route time consume.
     *
     * @return {@link Promise}
     */
    public CPromise showTime() {
        target.showTime();
        return this;
    }
}
