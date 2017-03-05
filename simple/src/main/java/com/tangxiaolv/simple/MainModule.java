
package com.tangxiaolv.simple;

import android.content.Context;

import com.tangxiaolv.annotations.RouterModule;
import com.tangxiaolv.annotations.RouterPath;
import com.tangxiaolv.router.AndroidRouter;
import com.tangxiaolv.router.CPromise;
import com.tangxiaolv.router.RPromise;
import com.tangxiaolv.simple.entity.A;

import java.util.List;

/*android://main?params=json*/
@RouterModule(scheme = "android", host = "main")
public class MainModule {

    //TODO 打包参数

    @RouterPath
    public void def(Context context, String scheme, RPromise promise) {
        promise.resolve(scheme);
    }

    @RouterPath("/activity/LocalActivity")
    public void openLocalActivity(String scheme, Context context, RPromise promise) {
    }

    @RouterPath("/params/")
    public void open3(A entity, RPromise promise) {
        promise.resolve(entity.toString());
    }

    @RouterPath("/params/")
    public void open3(List<A> __params__, RPromise promise) {
        promise.resolve(__params__.toString());
    }

    @RouterPath("/pakegeParames")
    public void open3() {
        String tag = AndroidRouter.open("www:///").getTag();

        CPromise open = AndroidRouter.open("www:///");
        RPromise rPromise = AndroidRouter.popPromiseByTag(tag);

    }
}
