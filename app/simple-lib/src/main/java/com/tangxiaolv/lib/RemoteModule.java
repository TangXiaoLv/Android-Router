package com.tangxiaolv.lib;

import android.app.Application;
import android.content.Context;
import android.content.Intent;

import com.tangxiaolv.annotations.RouterModule;
import com.tangxiaolv.annotations.RouterPath;
import com.tangxiaolv.router.VPromise;
import com.tangxiaolv.router.interfaces.IRouter;

@RouterModule(scheme = "android|remote", host = "lib")
public class RemoteModule implements IRouter {

    @RouterPath("/openRemoteActivity")
    public void openRemoteActivity(Application context, String scheme, VPromise promise) {
        Intent intent = new Intent(context, RemoteActivity.class);
        intent.putExtra("tag", promise.getTag());
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }
}
