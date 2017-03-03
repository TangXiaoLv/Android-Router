
package com.tangxiaolv.router;

import java.util.Map;

import android.text.TextUtils;

import com.tangxiaolv.router.module.ModuleDelegater;

import static android.R.attr.tag;

/**
 * Android Router Facade.
 *
 * <p>scheme://host/path?params=json
 */
public final class AndroidRouter {

    public static Promise open(String url) {
        return new Promise(new Asker(url));
    }

    public static Promise open(String scheme, String host, String path, Map<String, Object> params) {
        return new Promise(new Asker(scheme, host, path, params));
    }

    public static Promise popPromiseByTag(String tag) {
        return RouterHelper.getInstance().popPromiseByTag(tag);
    }
    public static void removePromiseByTag(String tag) {
        RouterHelper.getInstance().removePromiseByTag(tag);
    }
}
