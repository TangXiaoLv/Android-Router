package com.tangxiaolv.router;

/**
 * Promise {@link Promise#reject} {@link Promise#resolve} Proxy
 */
public class RPromise {

    private Promise target;

    RPromise(Promise target) {
        this.target = target;
    }

    public void resolve(Object result) {
        target.resolve(result);
    }

    public void reject(Exception e) {
        target.reject(e);
    }
}
