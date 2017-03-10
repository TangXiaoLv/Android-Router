package com.tangxiaolv.router;

import com.tangxiaolv.router.exceptions.RouterRemoteException;

import java.util.concurrent.CountDownLatch;

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
     * Await the result returned.It will block thread.
     *
     * @see CPromise#getValue(Reject reject)
     */
    public Object getValue() {
        return getValue(null);
    }

    /**
     * Await the result returned.It will block thread.
     *
     * @param reject {@link Reject}
     * @return result from remote.
     */
    public Object getValue(final Reject reject) {
        final Object[] arr = new Object[1];
        final CountDownLatch latch = new CountDownLatch(1);
        target.call(new Resolve() {
            @Override
            public void call(String type, Object result) {
                arr[0] = result;
                latch.countDown();
            }
        }, new Reject() {
            @Override
            public void call(Exception e) {
                if (reject != null) {
                    reject.call(e);
                }
                latch.countDown();
            }
        });

        try {
            latch.await();
        } catch (InterruptedException e) {
            if (reject != null) {
                reject.call(new RouterRemoteException("getValue fail.", e));
            }
        }

        return arr[0];
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
