
package com.tangxiaolv.router;

import android.os.Looper;
import android.text.TextUtils;
import android.widget.Toast;

import com.tangxiaolv.router.exceptions.RouterException;
import com.tangxiaolv.router.utils.PromiseTimer;
import com.tangxiaolv.router.utils.ReflectTool;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.T;

/**
 * Manage router send and receive.
 */
class Promise {

    private final Asker asker;
    private final VPromise mVPromise;
    private Resolve resolve;
    private Reject reject;
    private String tag;
    private PromiseTimer timer;

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
     * Send router. Receive success and fail.
     *
     * @param resolve {@link Promise}
     */
    void call(Resolve resolve, Reject reject) {
        this.resolve = resolve;
        this.reject = reject;
        if (asker != null) asker.request();
    }

    void resolve(final String type, final Object result) {
        showToast();
        if (resolve == null)
            return;
        if (Looper.myLooper() == Looper.getMainLooper()) {
            resolve.call(type, result);
        } else {
            RouterHelper.HANDLER.post(new Runnable() {
                @Override
                public void run() {
                    resolve.call(type, result);
                }
            });
        }
    }

    void reject(Exception e) {
        showToast();
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

    void showTime() {
        timer = new PromiseTimer();
    }

    private void showToast() {
        if (timer != null)
            Toast.makeText(ReflectTool.getApplication(), timer.getTime(), Toast.LENGTH_SHORT).show();
    }


    VPromise getVPromise() {
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
