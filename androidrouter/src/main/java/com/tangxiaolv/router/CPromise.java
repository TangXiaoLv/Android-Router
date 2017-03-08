package com.tangxiaolv.router;

/**
 * Proxy of {@link Promise}
 */
public class CPromise {

    private Promise target;

    CPromise(Promise target) {
        this.target = target;
    }

    public CPromise call() {
        return call(null, null);
    }

    public CPromise call(Resolve resolve) {
        return call(resolve, null);
    }

    public CPromise call(Reject reject) {
        return call(null, reject);
    }

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

    public CPromise callOnMainThread() {
        target.setThreadFlag(Promise.FLAG_CALL_MAIN);
        return this;
    }

    public CPromise callOnThread() {
        target.setThreadFlag(Promise.FLAG_CALL_THREAD);
        return this;
    }

    public CPromise returnOnMainThread() {
        target.setThreadFlag(Promise.FLAG_RETURN_MIAN);
        return this;
    }

    public CPromise returnOnThread() {
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
