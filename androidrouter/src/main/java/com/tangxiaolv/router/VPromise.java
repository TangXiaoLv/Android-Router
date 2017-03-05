package com.tangxiaolv.router;

/**
 * Proxy of {@link Promise}
 */
public class VPromise {

    /**
     * target
     */
    private Promise target;

    VPromise(Promise target) {
        this.target = target;
    }

    /**
     * return result
     *
     * @param result The result of whatever you want.
     */
    public void resolve(Object result) {
        target.resolve(result);
    }

    /**
     * return exception
     */
    public void reject(Exception e) {
        target.reject(e);
    }

    public String getTag() {
        return target.getTag();
    }
}
