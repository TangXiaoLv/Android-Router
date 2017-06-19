
package com.tangxiaolv.router.utils;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;
import android.content.pm.ApplicationInfo;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

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
            Method staticActivityThreadMethod = activityThreadClass.getDeclaredMethod("currentActivityThread");
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

    /**
     * Try to get generic of object.
     *
     * @param t   any object.
     * @param <T> input type
     * @return generic type
     */
    public static <T> String tryGetGeneric(T t) {
        ParameterizedType pt = null;
        Type[] types = t.getClass().getGenericInterfaces();
        if (types.length != 0 && types[0] instanceof ParameterizedType) {
            pt = (ParameterizedType) types[0];
        } else {
            Type type = t.getClass().getGenericSuperclass();
            if (type instanceof ParameterizedType) {
                pt = (ParameterizedType) type;
            }
        }

        if (pt == null) {
            return null;
        }

        Type[] actual= pt.getActualTypeArguments();
        String unsafe = actual[0].toString();
        String genericString;
        if (unsafe.contains(",")) {
            genericString = unsafe;
        } else {
            String[] elms = unsafe.split(" ");/*maybe get generic mark, like E,T*/
            genericString = elms.length <= 1 ? unsafe : elms[1];
        }
        return genericString.length() < 4 ? null : genericString;
    }

    // eg: List<Simple>
    @SuppressWarnings("all")
    public static String getFieldTypeWithGeneric(Field f) {
        Class fieldType = f.getType();
        //TODO Anonymous Inner Class Cant be find By Class.fromName,but getName could.
        String type = fieldType.getCanonicalName();
        if (fieldType.isAssignableFrom(List.class)) {
            try {
                ParameterizedType pt = (ParameterizedType) f.getGenericType();
                return pt.toString();
            } catch (ClassCastException ignored) {
            }
        }
        return type;
    }

    public static boolean debugable() {
        Context context = getApplication();
        return context != null && (context.getApplicationInfo().flags & ApplicationInfo.FLAG_DEBUGGABLE) != 0;
    }
}
