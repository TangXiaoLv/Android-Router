
package com.tangxiaolv.router;

import java.util.Map;

import android.text.TextUtils;

/**
 * Android Router Facade.
 *
 * <p>scheme://host/path?params=json
 */
public final class AndroidRouter {

    public static CPromise open(String url) {
        Promise promise = new Promise(new Asker(url));
        return new CPromise(promise);
    }

    public static CPromise open(String scheme, String host, String path, Map<String, Object> params) {
        Promise promise = new Promise(new Asker(scheme, host, path, params));
        return new CPromise(promise);
    }

    public static RPromise popPromiseByTag(String tag) {
        if (TextUtils.isEmpty(tag)) return null;
        return RouterHelper.getInstance().popPromiseByTag(tag);
    }

    public static void removePromiseByTag(String tag) {
        RouterHelper.getInstance().removePromiseByTag(tag);
    }
}
