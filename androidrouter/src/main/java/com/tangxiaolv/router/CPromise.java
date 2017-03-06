package com.tangxiaolv.router;

/**
 * Proxy of {@link Promise}
 */
public class CPromise {

    private Promise target;

    CPromise(Promise target) {
        this.target = target;
    }

    public void call() {
        target.call(null, null);
    }

    public void call(Resolve resolve) {
        target.call(resolve, null);
    }

    public void call(Reject reject) {
        target.call(null, reject);
    }

    public void call(Resolve resolve, Reject reject) {
        target.call(resolve, reject);
    }

    public CPromise showTime(){
        target.showTime();
        return this;
    }
}
