
package com.tangxiaolv.router;

import java.lang.reflect.Method;
import java.net.URLDecoder;
import java.util.Map;

import com.tangxiaolv.router.exceptions.NotFoundRouterException;
import com.tangxiaolv.router.exceptions.RouterException;

import android.net.Uri;
import android.text.TextUtils;

class Asker {

    private static final String PREFIX = "com.tangxiaolv.router.module.Mirror_";
    private static final String PARAMS_SPAER = "?params=";

    private String scheme;
    private String host;
    private String path;
    private Object params;
    private Promise promise;
    private Exception _e;

    Asker(String url) {
        parse(url);
    }

    Asker(String scheme, String host, String path, Map params) {
        if (TextUtils.isEmpty(host) || TextUtils.isEmpty(scheme)) {
            reject(new NotFoundRouterException(null));
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
                reject(new RouterException(""));
                return;
            }

            String decodeUrl = URLDecoder.decode(url, "utf-8");
            Uri uri = Uri.parse(decodeUrl);
            scheme = uri.getScheme();
            host = uri.getHost().toLowerCase();
            path = uri.getPath() == null ? "" : uri.getPath().toLowerCase();
            String s = uri.toString();
            int index = s.indexOf(PARAMS_SPAER);
            if (index != -1) {
                params = s.substring(index + PARAMS_SPAER.length(), s.length());
            }
        } catch (Exception e) {
            this._e = new NotFoundRouterException(url);
        }
    }

    void request() {
        if (_e != null) {
            reject(_e);
            return;
        }
        searchAndInvoke();
    }

    private void searchAndInvoke() {
        String modlue = PREFIX + scheme + "_" + host;
        try {
            Class<?> clazz = Class.forName(modlue);
            Method method = clazz.getMethod(
                    "invoke",
                    String.class,
                    ParamsWrapper.class);
            ParamsWrapper wrapper = new ParamsWrapper(params);
            wrapper.put("scheme", scheme);
            wrapper.put("promise", promise);
            method.invoke(clazz.newInstance(), path, wrapper);
        } catch (Exception e) {
            reject(e);
        }
    }

    void setPromise(Promise promise) {
        this.promise = promise;
    }

    private void reject(Exception e) {
        if (promise != null) {
            promise.reject(e);
        }
    }
}
