
package com.tangxiaolv.router;

import java.lang.reflect.Method;
import java.net.URLDecoder;
import java.util.Map;

import com.tangxiaolv.router.exceptions.NotFoundRouterException;
import com.tangxiaolv.router.exceptions.RouterException;
import com.tangxiaolv.router.interfaces.IMirror;
import com.tangxiaolv.router.utils.RLog;
import com.tangxiaolv.router.utils.ReflectTool;

import android.net.Uri;
import android.text.TextUtils;

import org.json.JSONException;

class Asker {

    private static final String MIRROR_PREFIX = "com.tangxiaolv.router.module.Mirror_";
    private static final String URL_PARAMS = "?params=";

    private String scheme;
    private String host;
    private String path;
    private Object params;
    private Promise promise;
    private Exception _e;
    private Method targetMethod;

    Asker(String url) {
        parse(url);
    }

    Asker(String scheme, String host, String path, Map params) {
        if (TextUtils.isEmpty(host) || TextUtils.isEmpty(scheme)) {
            reject(new RouterException("scheme or host isEmpty."));
            return;
        }
        this.scheme = scheme;
        this.host = host.toLowerCase();
        this.path = path == null ? "" : path.toLowerCase();
        this.params = params;
    }

    private void parse(String url) {
        try {
            if (TextUtils.isEmpty(url)) {
                reject(new RouterException("url isEmpty."));
                return;
            }

            String decodeUrl = URLDecoder.decode(url, "utf-8");
            Uri uri = Uri.parse(decodeUrl);
            scheme = uri.getScheme();
            host = uri.getHost().toLowerCase();
            path = uri.getPath() == null ? "" : uri.getPath().toLowerCase();
            String s = uri.toString();
            int index = s.indexOf(URL_PARAMS);
            if (index != -1) {
                params = s.substring(index + URL_PARAMS.length(), s.length());
            }
        } catch (Exception e) {
            this._e = new NotFoundRouterException("invalid router url: " + url);
        }
    }

    void request() {
        if (_e == null) {
            searchAndInvoke();
            return;
        }
        reject(_e);
    }

    private void searchAndInvoke() {
        RLog.d("send router url: " + getUrl());
        String mirror = MIRROR_PREFIX + scheme + "_" + host;
        try {
            Class<?> clazz = null;
            if (targetMethod == null) {
                clazz = Class.forName(mirror);
                targetMethod = clazz.getMethod("invoke", String.class, ParamsWrapper.class);
            }
            //find from cache pool
            IMirror target = RouterHelper.getInstance().findMirrorByKey(mirror);
            if (target == null && clazz != null) {
                target = (IMirror) clazz.newInstance();
                RouterHelper.getInstance().addToMirrorPool(mirror, target);
            }
            targetMethod.invoke(target, path, createParamsWrapper(params));
        } catch (ClassNotFoundException e) {
            reject(new NotFoundRouterException("invalid router url: " + getUrl()));
        } catch (Exception e) {
            reject(e);
        }
    }

    private ParamsWrapper createParamsWrapper(Object params) throws JSONException {
        ParamsWrapper wrapper = new ParamsWrapper(params);
        wrapper.put("scheme", scheme);
        wrapper.put("promise", promise);
        wrapper.put("context", ReflectTool.getApplication());
        return wrapper;
    }

    void setPromise(Promise promise) {
        this.promise = promise;
    }

    private void reject(Exception e) {
        if (promise != null) {
            promise.reject(e);
        }
    }

    private String getUrl() {
        String param = params == null ? "" : params.toString();
        return scheme + "://" + host + "/" + path + URL_PARAMS + param;
    }
}
