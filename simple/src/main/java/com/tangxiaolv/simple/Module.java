
package com.tangxiaolv.simple;

import android.content.Context;

import com.tangxiaolv.annotations.RouterModule;
import com.tangxiaolv.annotations.RouterPath;
import com.tangxiaolv.router.AndroidRouter;
import com.tangxiaolv.router.CPromise;
import com.tangxiaolv.router.RPromise;

import java.util.List;

@RouterModule(scheme = "toon", host = "one")
public class Module {

    @RouterPath
    public void def(Context context, String scheme, RPromise promise, String key1, boolean key2) {
        promise.resolve(scheme);
    }

    @RouterPath("/openOne")
    public void open1(int key1, String key2, String scheme, RPromise promise) {
        promise.resolve(null);
    }

    @RouterPath("/openOne2")
    public void open2(String name, int age, String scheme, final RPromise promise) {
        promise.resolve(null);
    }

    @RouterPath("/entity")
    public void open3(Entity entity, RPromise promise) {
        promise.resolve(entity.toString());
    }

    @RouterPath("/list")
    public void open3(List<Entity> params, RPromise promise) {
        promise.resolve(params.toString());
    }

    @RouterPath("/empty")
    public void open3(RPromise promise) {
        String tag = AndroidRouter.open("www:///").getTag();

        CPromise open = AndroidRouter.open("www:///");
        RPromise rPromise = AndroidRouter.popPromiseByTag(tag);

    }
}
