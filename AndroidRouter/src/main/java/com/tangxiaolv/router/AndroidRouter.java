
package com.tangxiaolv.router;

import java.util.Map;

import android.os.Handler;
import android.os.Looper;

/**
 * Android Router
 * <p>
 * scheme://host/path?params=json
 */
public final class AndroidRouter {

    static Handler HANDLER = new Handler(Looper.getMainLooper());

    public static Promise open(String url) {
        return new Promise(new Asker(url));
    }

    public static Promise open(String scheme, String host, String path,
            Map<String, Object> params) {
        return new Promise(new Asker(scheme, host, path, params));
    }
}
