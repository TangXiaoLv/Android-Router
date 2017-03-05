
package com.tangxiaolv.simple;

import android.content.Context;

import com.tangxiaolv.annotations.RouterModule;
import com.tangxiaolv.annotations.RouterPath;
import com.tangxiaolv.router.AndroidRouter;
import com.tangxiaolv.router.CPromise;
import com.tangxiaolv.router.VPromise;
import com.tangxiaolv.simple.entity.A;

import java.util.List;

/*android://main?params=json*/
@RouterModule(scheme = "android", host = "main")
public class MainModule {

    //TODO 打包参数

    @RouterPath
    public void def(Context context, String scheme, VPromise promise) {
        promise.resolve(scheme);
    }

    @RouterPath("/activity/LocalActivity")
    public void openLocalActivity(String scheme, Context context, VPromise promise) {
    }

    @RouterPath("/params/")
    public void open3(A entity, VPromise promise) {
        promise.resolve(entity.toString());
    }

    @RouterPath("/params/")
    public void open3(List<A> _params_, VPromise promise) {
        promise.resolve(_params_.toString());
    }

    @RouterPath("/pakegeParames")
    public void open3() {
        String tag = AndroidRouter.open("www:///").getTag();

        CPromise open = AndroidRouter.open("www:///");
        VPromise vPromise = AndroidRouter.popPromiseByTag(tag);

    }
}
