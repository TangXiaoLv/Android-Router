
package com.tangxiaolv.router;

import android.net.Uri;
import android.text.TextUtils;

import com.tangxiaolv.router.exceptions.NotFoundRouterException;
import com.tangxiaolv.router.exceptions.RouterException;
import com.tangxiaolv.router.interfaces.IMirror;
import com.tangxiaolv.router.utils.ReflectTool;

import org.json.JSONException;

import java.lang.reflect.Method;
import java.net.URLDecoder;
import java.util.List;
import java.util.Map;

/**
 * Parse url and invoke {@link IMirror}
 */
class Asker {

    private static final String MIRROR_PREFIX = "com.tangxiaolv.router.module.Mirror_";
    private static final String URL_PARAMS = "?params=";

    private String scheme;
    private String host;
    private String path;
    private Object params;
    private Object appendParams;
    private Promise promise;
    private Exception _e;

    /**
     * @param urlWithParams scheme://host/path?params=json
     */
    Asker(String urlWithParams) {
        parse(urlWithParams);
    }

    Asker(String url, Object appendParams) {
        this.appendParams = appendParams;
        parse(url);
    }

    Asker(String scheme, String host, String path, Object params) {
        if (TextUtils.isEmpty(host) || TextUtils.isEmpty(scheme)) {
            reject(new RouterException("scheme or host isEmpty."));
            return;
        }
        this.scheme = scheme;
        this.host = host.toLowerCase();//ignore case
        this.path = path == null ? "" : path.toLowerCase();//ignore case
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
            host = uri.getHost().toLowerCase();//ignore case
            path = uri.getPath() == null ? "" : uri.getPath().toLowerCase();//ignore case
            String s = uri.toString();
            int index = s.indexOf(URL_PARAMS);
            if (index != -1) {
                params = s.substring(index + URL_PARAMS.length(), s.length());
            }
        } catch (Exception e) {
            this._e = new NotFoundRouterException("invalid router url: " + url);
        }
    }


    /**
     * real send router
     */
    void request() {
        if (_e == null) {
            searchAndInvoke();
            return;
        }
        reject(_e);
    }

    private void searchAndInvoke() {
        //RLog.d("send router url: " + getUrl());
        String mirror = MIRROR_PREFIX + scheme + "_" + host;
        try {
            Class<?> clazz = Class.forName(mirror);
            Method targetMethod = clazz.getMethod("invoke", String.class, ParamsWrapper.class);
            //find from cache pool
            IMirror target = RouterHelper.getInstance().findMirrorByKey(mirror);
            if (target == null) {
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
        wrapper.put("promise", promise.getVPromise());
        if (wrapper.get("context") == null) {
            wrapper.put("context", ReflectTool.getApplication());
        }

        if (appendParams != null) {
            wrapper.append(appendParams);
        }
        return wrapper;
    }

    void setPromise(Promise promise) {
        this.promise = promise;
    }

    private void reject(Exception e) {
        promise.reject(e);
    }

    private String getUrl() {
        String param = params == null ? "" : URL_PARAMS + params.toString();
        return scheme + "://" + host + path + param;
    }
}