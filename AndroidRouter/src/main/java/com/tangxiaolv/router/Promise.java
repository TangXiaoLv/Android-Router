
package com.tangxiaolv.router;

import android.os.Looper;
import android.os.SystemClock;
import android.text.TextUtils;

import com.tangxiaolv.router.exceptions.RouterException;

public final class Promise {

    private final Asker asker;
    private Resolve resolve;
    private Reject reject;
    private String tag;

    Promise(Asker asker) {
        this.asker = asker;
        if (asker != null) asker.setPromise(this);
    }

    public void call() {
        call(null, null);
    }

    public void call(Resolve resolve) {
        call(resolve, null);
    }

    public void call(Reject reject) {
        call(null, reject);
    }

    public void call(Resolve resolve, Reject reject) {
        this.resolve = resolve;
        this.reject = reject;
        if (asker != null) asker.request();
    }

    public void resolve(final Object result) {
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

    public void reject(Exception e) {
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

    public String getTag() {
        if (TextUtils.isEmpty(tag)) {
            tag = RouterHelper.getInstance().genPromiseTag();
            RouterHelper.getInstance().addToPromisePool(tag,this);
        }
        return tag;
    }
}
