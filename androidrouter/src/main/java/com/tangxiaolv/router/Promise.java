
package com.tangxiaolv.router;

import android.os.Looper;
import android.text.TextUtils;
import android.widget.Toast;

import com.tangxiaolv.router.exceptions.RouterException;
import com.tangxiaolv.router.exceptions.ValueParseException;
import com.tangxiaolv.router.utils.PromiseTimer;
import com.tangxiaolv.router.utils.RLog;
import com.tangxiaolv.router.utils.ReflectTool;
import com.tangxiaolv.router.utils.ValueParser;

/**
 * Manage router send and receive.
 */
public class Promise {

    /**
     * Call on main thread.{@link Promise#call(Resolve, Reject)}
     */
    public static final int FLAG_CALL_MAIN = 1 << 1;

    /**
     * Call on thread.{@link Promise#call(Resolve, Reject)}
     */
    public static final int FLAG_CALL_THREAD = 1 << 2;

    /**
     * return on main thread.
     *
     * {@link Promise#resolve(Object)} {@link Promise#reject(Exception)}}
     */
    public static final int FLAG_RETURN_MIAN = 1 << 3;

    /**
     * return on thread.
     *
     * {@link Promise#resolve(Object)} {@link Promise#reject(Exception)}}
     */
    public static final int FLAG_RETURN_THREAD = 1 << 4;

    private final Asker asker;
    private final VPromise promiseForReturn;
    private Resolve resolve;
    private Reject reject;
    private String tag;
    private PromiseTimer timer;
    private int flagMark = 0;
    private boolean voidTypeable = false;

    Promise(Asker asker) {
        this.asker = asker;
        this.promiseForReturn = new VPromise(this);
        if (asker != null) asker.setPromise(this);
    }

    /**
     * Send router. Receive success and fail.
     *
     * @param resolve {@link Promise}
     */
    public void call(Resolve resolve, Reject reject) {
        this.resolve = resolve;
        this.reject = reject;

        //call on main thread
        if ((flagMark & FLAG_CALL_MAIN) != 0 && !isMainThread()) {
            RouterHelper.HANDLER.post(new Runnable() {
                @Override
                public void run() {
                    asker.request();
                }
            });
        }

        //call on thread
        else if ((flagMark & FLAG_CALL_THREAD) != 0) {
            RouterHelper.EXECUTOR.execute(new Runnable() {
                @Override
                public void run() {
                    asker.request();
                }
            });
        }

        //call on current thread
        else {
            asker.request();
        }
    }

    @SuppressWarnings("unchecked")
    void resolve(Object result) {
        //Check void type.
        if (result == void.class && !voidTypeable) return;

        showToast();
        if (resolve == null) return;

        Object expected;
        try {
            String firstGeneric = ReflectTool.tryGetGeneric(resolve);
            expected = ValueParser.parse(result, firstGeneric);
        } catch (ValueParseException e) {
            reject(e);
            return;
        }

        final Object expectedResult = expected;

        //call on main thread
        if ((flagMark & FLAG_RETURN_MIAN) != 0 && !isMainThread()) {
            RouterHelper.HANDLER.post(new Runnable() {
                @Override
                public void run() {
                    try {
                        resolve.call(expectedResult);
                    } catch (Exception e) {
                        reject(e);
                    }
                }
            });
        }

        //call on thread
        else if ((flagMark & FLAG_RETURN_THREAD) != 0) {
            RouterHelper.EXECUTOR.execute(new Runnable() {
                @Override
                public void run() {
                    try {
                        resolve.call(expectedResult);
                    } catch (Exception e) {
                        reject(e);
                    }
                }
            });
        }

        //call on current thread
        else {
            try {
                resolve.call(expectedResult);
            } catch (Exception e) {
                reject(e);
            }
        }
    }

    void reject(Exception e) {
        showToast();
        if (e == null) e = new RouterException("unkownException");
        if (RLog.DEBUG) e.printStackTrace();
        if (reject == null) return;

        final Exception _e = e;
        //call on main thread
        if ((flagMark & FLAG_RETURN_MIAN) != 0 && !isMainThread()) {
            RouterHelper.HANDLER.post(new Runnable() {
                @Override
                public void run() {
                    reject.call(_e);
                }
            });
        }

        //call on thread
        else if ((flagMark & FLAG_RETURN_THREAD) != 0) {
            RouterHelper.EXECUTOR.execute(new Runnable() {
                @Override
                public void run() {
                    reject.call(_e);
                }
            });
        }

        //call on current thread
        else {
            reject.call(e);
        }
    }

    private boolean isMainThread() {
        return Looper.myLooper() == Looper.getMainLooper();
    }

    public void showTime() {
        timer = new PromiseTimer();
    }

    private void showToast() {
        if (timer == null) return;
        RouterHelper.HANDLER.post(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(ReflectTool.getApplication(), timer.getTime(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    VPromise getVPromise() {
        return promiseForReturn;
    }

    /**
     * Sync To obtain the return value.
     */
    public void allowGetVoidType() {
        this.voidTypeable = true;
    }

    public void setThreadFlag(int flag) {
        this.flagMark |= flag;
    }

    /**
     * Used by {@link AndroidRouter#findPromiseByTag(String)}
     *
     * @return Tag of {@link Promise}
     */
    String getTag() {
        if (TextUtils.isEmpty(tag)) {
            tag = RouterHelper.getInstance().genPromiseTag();
            RouterHelper.getInstance().addToPromisePool(tag, promiseForReturn);
        }
        return tag;
    }
}
