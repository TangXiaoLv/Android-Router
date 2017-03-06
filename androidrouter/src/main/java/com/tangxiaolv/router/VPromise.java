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
     * @param type   type of result
     * @param result The result of whatever you want.
     */
    public void resolve(String type, Object result) {
        target.resolve(type, result);
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
