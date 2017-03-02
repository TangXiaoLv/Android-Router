
package com.tangxiaolv.simple;

import android.app.Activity;
import android.content.Context;
import android.util.JsonToken;

import com.tangxiaolv.annotations.RouterPath;
import com.tangxiaolv.annotations.RouterModule;
import com.tangxiaolv.router.Promise;

import org.json.JSONObject;
import org.json.JSONStringer;
import org.json.JSONTokener;

import java.util.List;
import java.util.Map;

import static android.R.attr.name;
import static android.R.attr.scheme;
import static android.R.id.list;

/**
 * Hell
 */
@RouterModule(scheme = "toon|toon2|toon3", host = "one")
public class Module {

    @RouterPath
    public void def(Context context,String scheme, Promise promise, String key1, boolean key2) {
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

    @RouterPath("/entity")
    public void open3(Map<String, String> entity, Promise promise) {
        promise.resolve(entity.toString());
    }

    @RouterPath("/list")
    public void open3(List<Entity> params, Promise promise) {
        promise.resolve(params.toString());
    }

    @RouterPath("/empty")
    public void open3(Promise promise) {
    }
}
