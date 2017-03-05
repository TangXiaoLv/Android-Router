
package com.tangxiaolv.simple;

import android.content.Context;

import com.tangxiaolv.annotations.RouterModule;
import com.tangxiaolv.annotations.RouterPath;
import com.tangxiaolv.router.AndroidRouter;
import com.tangxiaolv.router.VPromise;
import com.tangxiaolv.router.exceptions.RouterException;
import com.tangxiaolv.router.interfaces.IRouter;
import com.tangxiaolv.simple.entity.A;
import com.tangxiaolv.simple.entity.B;

import java.util.List;

@RouterModule(scheme = "android", host = "main")
public class MainModule implements IRouter {

    /**
     * no path router.
     *
     * Router => android://main
     *
     * @param context {@link android.app.Application}
     * @param scheme  router scheme
     * @param promise {@link VPromise}
     */
    @RouterPath
    public void def(Context context, String scheme, VPromise promise) {
        promise.resolve("from def ");

        String tag = promise.getTag();
        AndroidRouter.findPromiseByTag(tag).reject(new RouterException());
    }

    /*Router => android://main/activity/localActivity*/
    @RouterPath("/activity/localActivity")
    public void openLocalActivity(String scheme, Context context, VPromise promise) {
    }

    /**
     * from jsonObject
     *
     * Router => android://main/params/localActivity?params={'f':1,'i':2,'l':3,'d':4,'b':true}
     */
    @RouterPath("/params/basis")
    public void paramsBasis(float f, int i, long l, double d, boolean b,
                            String scheme, Context context, VPromise promise) {
    }

    @RouterPath("/params/complex")
    public void paramsComplex(B b, List<A> _params_, VPromise promise) {
    }

    @RouterPath("/parames/pakege")
    public void paramsPakege() {
    }

    //from jsonArray
    @RouterPath("/jsonArray")
    public void jsonArray(List<A> _params_, VPromise promise) {
    }
}
