
package com.tangxiaolv.router;

import android.util.Log;

class RLog {
    private static final String TAG = "AndroidRouter";
    private static boolean DEBUG = BuildConfig.DEBUG;

    static void e(String msg) {
        if (DEBUG)
            Log.e(TAG, msg);
    }

    static void i(String msg) {
        if (DEBUG)
            Log.i(TAG, msg);
    }

    static void d(String msg) {
        if (DEBUG)
            Log.d(TAG, msg);
    }

    static void v(String msg) {
        if (DEBUG)
            Log.v(TAG, msg);
    }

    static void w(String msg) {
        if (DEBUG)
            Log.w(TAG, msg);
    }
}
