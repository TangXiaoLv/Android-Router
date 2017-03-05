
package com.tangxiaolv.router;

import android.os.Looper;
import android.text.TextUtils;

import com.tangxiaolv.router.exceptions.RouterException;

/**
 * Manage router send and receive.
 */
class Promise {

    private final Asker asker;
    private final VPromise mVPromise;
    private Resolve resolve;
    private Reject reject;
    private String tag;

    Promise(Asker asker) {
        this.asker = asker;
        this.mVPromise = new VPromise(this);
        if (asker != null) asker.setPromise(this);
    }

    /**
     * No want to receive.
     */
    void call() {
        call(null, null);
    }

    /**
     * Send router.Only receive success.
     *
     * @param resolve {@link Promise}
     */
    void call(Resolve resolve) {
        call(resolve, null);
    }

    /**
     * Send router.Only receive fail.
     *
     * @param reject {@link Promise}
     */
    void call(Reject reject) {
        call(null, reject);
    }

    /**
     * Send router.receive success and fail.
     *
     * @param resolve {@link Promise}
     */
    void call(Resolve resolve, Reject reject) {
        this.resolve = resolve;
        this.reject = reject;
        if (asker != null) asker.request();
    }

    void resolve(final Object result) {
        if (resolve == null)
            return;
        if (Looper.myLooper() == Looper.getMainLooper()) {
            resolve.call(result);
        } else {
            RouterHelper.HANDLER.post(new Runnable() {
                @Override
                public void run() {
                    resolve.call(result);
                }
            });
        }
    }

    void reject(Exception e) {
        if (e == null)
            e = new RouterException("unkownException");
        e.printStackTrace();
        if (reject == null)
            return;
        if (Looper.myLooper() == Looper.getMainLooper()) {
            reject.call(e);
        } else {
            final Exception _e = e;
            RouterHelper.HANDLER.post(new Runnable() {
                @Override
                public void run() {
                    reject.call(_e);
                }
            });
        }
    }

    VPromise getRPromise() {
        return mVPromise;
    }

    /**
     * Used by {@link AndroidRouter#findPromiseByTag(String)}
     *
     * @return Tag of {@link Promise}
     */
    String getTag() {
        if (TextUtils.isEmpty(tag)) {
            tag = RouterHelper.getInstance().genPromiseTag();
            RouterHelper.getInstance().addToPromisePool(tag, mVPromise);
        }
        return tag;
    }
}
