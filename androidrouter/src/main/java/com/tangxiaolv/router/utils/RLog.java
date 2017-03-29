
package com.tangxiaolv.router.utils;

import android.util.Log;

/**
 * Router log
 */
public final class RLog {
    private static final String TAG = "AndroidRouter";
    public static boolean DEBUG = ReflectTool.debugable();

    public static void e(String msg, Object... args) {
        if (DEBUG) {
            msg = String.format(msg, args);
            Log.e(TAG, msg);
        }
    }

    public static void i(String msg, Object... args) {
        if (DEBUG) {
            msg = String.format(msg, args);
            Log.i(TAG, msg);
        }
    }

    public static void d(String msg, Object... args) {
        if (DEBUG) {
            msg = String.format(msg, args);
            Log.d(TAG, msg);
        }
    }

    public static void v(String msg, Object... args) {
        if (DEBUG) {
            msg = String.format(msg, args);
            Log.v(TAG, msg);
        }
    }

    public static void w(String msg, Object... args) {
        if (DEBUG) {
            msg = String.format(msg, args);
            Log.w(TAG, msg);
        }
    }
}
