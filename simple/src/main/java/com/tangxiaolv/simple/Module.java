
package com.tangxiaolv.simple;

import android.app.Activity;

import com.tangxiaolv.annotations.RouterPath;
import com.tangxiaolv.annotations.RouterModule;
import com.tangxiaolv.router.Promise;

import static android.R.attr.name;
import static android.R.attr.scheme;

/**
 * Hell
 */
@RouterModule(scheme = "toon|toon2|toon3", host = "one")
public class Module {

    @RouterPath
    public void def(String scheme, Promise promise, String key1, boolean key2) {
        promise.resolve(scheme);
    }

    @RouterPath("/openOne")
    public void open1(int key1, String key2, String scheme, Promise promise) {
        promise.resolve(null);
    }

    @RouterPath("/openOne2")
    public void open2(Activity activity, String name, int age, String scheme,
            final Promise promise) {
        promise.resolve(null);
    }
}
