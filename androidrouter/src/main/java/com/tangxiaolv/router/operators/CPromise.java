package com.tangxiaolv.router.operators;

import com.tangxiaolv.router.Promise;
import com.tangxiaolv.router.Reject;
import com.tangxiaolv.router.Resolve;
import com.tangxiaolv.router.exceptions.RouterRemoteException;

import java.util.concurrent.CountDownLatch;

/**
 * Proxy of {@link Promise}
 */
public class CPromise<T> {

    private Promise target;

    CPromise() {
    }

    public CPromise(Promise target) {
        this.target = target;
    }

    /**
     * Start route.Empty callback
     */
    public void call() {
        call(null, null);
    }

    /**
     * Start route.
     *
     * @param resolve Result callback
     * @param <R>     the output type
     */
    public <R> void call(Resolve<R> resolve) {
        call(resolve, null);
    }

    /**
     * Start route
     *
     * @param reject Exception callback
     */
    public void call(Reject reject) {
        call(null, reject);
    }

    /**
     * Start route
     *
     * @param resolve Result callback
     * @param reject  Exception callback
     * @param <R>     the output type
     */
    public <R> void call(Resolve<R> resolve, Reject reject) {
        target.call(resolve, reject);
    }

    /**
     * This method support reactive process.
     *
     * @param func a function to apply to each item emitted by the Publisher
     * @param <W>  the result type of remote.
     * @param <R>  the output type
     * @return a CPromise that emits the items from the source, transformed by the specified.
     */
    public <W, R> CPromise<R> call(Func<W, R> func) {
        return new PromiseCall<>(new CPromise<W>(target), func);
    }

    /**
     * Await the result returned.It will be block thread.
     *
     * @return result
     * @see CPromise#getValue(Reject reject)
     */
    public <R> R getValue() {
        return getValue(null);
    }

    /**
     * Await the result returned.It will be block thread.
     *
     * @param reject {@link Reject}
     * @return result Note:Didn't support different types cast.
     */
    @SuppressWarnings("unchecked")
    public <R> R getValue(final Reject reject) {
        final Object[] arr = new Object[2];
        final CountDownLatch latch = new CountDownLatch(1);
        target.allowGetVoidType();
        target.call(new Resolve<Object>() {
            @Override
            public void call(Object result) {
                if (result != void.class){
                    arr[0] = result;
                }
                latch.countDown();
            }
        }, new Reject() {
            @Override
            public void call(Exception e) {
                arr[1] = e;
                latch.countDown();
            }
        });

        try {
            latch.await();
            if (arr[1] instanceof Exception) throw new IllegalStateException();
        } catch (Exception e) {
            if (reject != null) {
                reject.call(new RouterRemoteException("getValue fail.", e));
            }
        }

        return (R) arr[0];
    }

    /**
     * Call on main thread.
     *
     * @return {@link CPromise}
     */
    public CPromise<T> callOnMainThread() {
        target.setThreadFlag(Promise.FLAG_CALL_MAIN);
        return this;
    }

    /**
     * Call on Sub-thread.
     *
     * @return {@link CPromise}
     */
    public CPromise<T> callOnSubThread() {
        target.setThreadFlag(Promise.FLAG_CALL_THREAD);
        return this;
    }

    /**
     * Callback on main thread.
     *
     * @return {@link CPromise}
     */
    public CPromise<T> returnOnMainThread() {
        target.setThreadFlag(Promise.FLAG_RETURN_MIAN);
        return this;
    }

    /**
     * Callback on Sub-thread.
     *
     * @return {@link CPromise}
     */
    public CPromise<T> returnOnSubThread() {
        target.setThreadFlag(Promise.FLAG_RETURN_THREAD);
        return this;
    }

    /**
     * Show route time consume.
     *
     * @return {@link Promise}
     */
    public CPromise<T> showTime() {
        target.showTime();
        return this;
    }

    /**
     * Returns a CPromise that applies a specified function to each item emitted by the source
     * CPromise and emits the results of these function applications.
     *
     * @param func a function to apply to each item emitted by the CPromise.
     * @param <R>  the output type
     * @return a CPromise that emits the items from the source, transformed by the specified.
     */
    public <R> CPromise<R> then(Func<T, R> func) {
        return new PromiseMap<>(this, func);
    }

    /**
     * Start route.
     */
    public void done() {
        done(null, null);
    }

    /**
     * Start route and process result.
     *
     * @param resolve Result callback
     */
    public void done(Resolve<T> resolve) {
        done(resolve, null);
    }

    /**
     * Start route and process exception.
     *
     * @param reject Exception callback
     */
    public void done(Reject reject) {
        done(null, reject);
    }

    /**
     * Start route and process result and exception.
     *
     * @param resolve Result callback
     * @param reject  Exception callback
     */
    @SuppressWarnings("unchecked")
    public void done(final Resolve<T> resolve, final Reject reject) {
        callActual(resolve, reject);
    }

    /**
     * Call to a CPromise-like and provides a callback to handle the items it emits.
     */
    void callActual(final Resolve<T> resolve, final Reject reject) {
        //CPromise-like need override
    }
}
