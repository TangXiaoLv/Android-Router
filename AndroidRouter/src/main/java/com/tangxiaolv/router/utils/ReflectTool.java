
package com.tangxiaolv.router.utils;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;

public class ReflectTool {

    @SuppressLint("StaticFieldLeak")
    private static Context sApplication;

    /**
     * Get Application by reflect
     * 
     * @return Application
     */
    public static Context getApplication() {
        try {
            if (sApplication != null) {
                return sApplication.getApplicationContext();
            }
            Class<?> activityThreadClass = Class.forName("android.app.ActivityThread");
            Method staticActivityThreadMethod = activityThreadClass
                    .getDeclaredMethod("currentActivityThread");
            staticActivityThreadMethod.setAccessible(true);
            Object currentActivityThread = staticActivityThreadMethod.invoke(null);
            Field applicationField = activityThreadClass.getDeclaredField("mInitialApplication");
            applicationField.setAccessible(true);
            sApplication = (Application) applicationField.get(currentActivityThread);
            return sApplication;
        } catch (Exception e) {
            return null;
        }
    }
}
