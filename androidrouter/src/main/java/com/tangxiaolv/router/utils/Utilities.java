package com.tangxiaolv.router.utils;

import java.lang.reflect.InvocationTargetException;

public class Utilities {

    public static Throwable getRealException(Throwable e) {
        if (e instanceof InvocationTargetException) {
            return getRealException(((InvocationTargetException) e).getTargetException());
        } else {
            return e;
        }
    }
}
