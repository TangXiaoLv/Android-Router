
package com.tangxiaolv.router;

import java.util.List;
import java.util.Map;

import android.net.Uri;
import android.text.TextUtils;

/**
 * Android Router Facade.
 *
 * Protocol Format: scheme://host/path?params=json
 */
public final class AndroidRouter {


    /**
     * Open url, usually invoked externally. like from browser.
     *
     * @param url scheme://host/path?params=json
     * @return {@link CPromise}
     */
    public static CPromise open(String url) {
        Promise promise = new Promise(new Asker(url));
        return new CPromise(promise);
    }

    /**
     * Usually invoked inner, Empty params.
     *
     * @param scheme The scheme of protocol
     * @param host   The host of protocol
     * @param path   The path of protocol
     * @return {@link CPromise}
     */
    public static CPromise open(String scheme, String host, String path) {
        Promise promise = new Promise(new Asker(scheme, host, path, null));
        return new CPromise(promise);
    }

    /**
     * Usually invoked inner.
     *
     * @param scheme The scheme of protocol
     * @param host   The host of protocol
     * @param path   The path of protocol
     * @param params The jsonObject params of protocol
     * @return {@link CPromise}
     */
    public static CPromise open(String scheme, String host, String path, Map<String, Object> params) {
        Promise promise = new Promise(new Asker(scheme, host, path, params));
        return new CPromise(promise);
    }

    /**
     * Usually invoked inner.
     *
     * @param scheme The scheme of protocol
     * @param host   The host of protocol
     * @param path   The path of protocol
     * @param params The jsonArray params of protocol
     * @return {@link CPromise}
     */
    public static CPromise open(String scheme, String host, String path, List params) {
        Promise promise = new Promise(new Asker(scheme, host, path, params));
        return new CPromise(promise);
    }

    /**
     * Usually invoked inner.
     *
     * @param scheme The scheme of protocol
     * @param host   The host of protocol
     * @param path   The path of protocol
     * @param json The json params of protocol
     * @return {@link CPromise}
     */
    public static CPromise open(String scheme, String host, String path, String json) {
        Promise promise = new Promise(new Asker(scheme, host, path, json));
        return new CPromise(promise);
    }

    /**
     * Find from cache pool
     *
     * @param tag The tag of {@link Promise}
     * @return {@link VPromise} if not find, return null.
     */
    public static VPromise findPromiseByTag(String tag) {
        if (TextUtils.isEmpty(tag)) return null;
        return RouterHelper.getInstance().popPromiseByTag(tag);
    }

    /**
     * Remove from cache pool
     *
     * @param tag The tag of {@link Promise}
     */
    public static void removePromiseByTag(String tag) {
        RouterHelper.getInstance().removePromiseByTag(tag);
    }
}
