
package com.tangxiaolv.simple;

import android.app.Application;
import android.content.Intent;

import com.tangxiaolv.annotations.RouterModule;
import com.tangxiaolv.annotations.RouterPath;
import com.tangxiaolv.router.VPromise;
import com.tangxiaolv.router.exceptions.RouterRemoteException;
import com.tangxiaolv.router.interfaces.IRouter;
import com.tangxiaolv.simple.entity.A;
import com.tangxiaolv.simple.entity.B;
import com.tangxiaolv.simple.entity.C;
import com.tangxiaolv.simple.entity.Package;

import java.util.List;

/**
 * Support parameter types
 *
 * float
 * int
 * long
 * double
 * boolean
 * String
 * List<?>
 * Map<String,Object>
 * custom object
 */
@RouterModule(scheme = "android", host = "main")
public class MainModule implements IRouter {
    /**
     * no path route.
     *
     * Route => android://main
     *
     * @param context {@link android.app.Application}
     * @param scheme  router scheme
     * @param promise {@link VPromise}
     */
    @RouterPath
    public void def(Application context, String scheme, VPromise promise) {
        promise.resolve("", "from scheme: [" + scheme + "] " + "path: []");
    }

    /**
     * Route => android://main/activity/localActivity
     */
    @RouterPath("/activity/localActivity")
    public void openLocalActivity(Application context, VPromise promise) {
        String tag = promise.getTag();
        Intent intent = new Intent(context, LocalActivity.class);
        intent.putExtra("tag", tag);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    /**
     * Take out the value from json object
     *
     * Route => android://main/params/basis?params={'f':1,'i':2,'l':3,'d':4,'b':true}
     */
    @RouterPath("/params/basis")
    public void paramsBasis(float f, int i, long l, double d, boolean b,
                            String scheme, VPromise promise) {
        promise.resolve("", "from scheme: [" + scheme + "] " + "path: [/params/basis]");
    }

    /**
     * Take out the value from jsonObject
     *
     * Route => android://main/params/complex?params={'b':{},'listC':[]}
     */
    @RouterPath("/params/complex")
    public void paramsComplex(B b, List<C> listC, String scheme, VPromise promise) {
        promise.resolve("", "from scheme: [" + scheme + "] " + "path: [/params/complex]");
    }

    //from json object => to object
    @RouterPath("/jsonObject")
    public void paramsPakege(Package _params_, String scheme, VPromise promise) {
        promise.resolve("", "from scheme: [" + scheme + "] " + "path: [/jsonObject]");
    }

    //from json array => to list
    @RouterPath("/jsonArray")
    public void jsonArray(List<A> _params_, String scheme, VPromise promise) {
        promise.resolve("", "from scheme: [" + scheme + "] " + "path: [/jsonArray]");
    }

    //eg: from A => to B
    @RouterPath("/differentTypes")
    public void differentTypes(A a, List<A> listA, String scheme, VPromise promise) {
        promise.resolve("", "from scheme: [" + scheme + "] " + "path: [/differentTypes]");
    }

    //if return type != void, auto return value by call promise
    @RouterPath("/autoReturn")
    public String autoReturn(String scheme) {
        return "I'm auto return!!!!! ";
    }

    @RouterPath("/throwError")
    public void throwError(VPromise promise) {
        promise.reject(new RouterRemoteException("I'm error................."));
    }
}
