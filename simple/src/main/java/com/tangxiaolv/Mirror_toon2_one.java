
package com.tangxiaolv;

import java.lang.reflect.InvocationTargetException;

import com.tangxiaolv.router.ParamsWrapper;
import com.tangxiaolv.simple.Module;

public final class Mirror_toon2_one {

    private final Object main;

    public Mirror_toon2_one() throws IllegalAccessException, InstantiationException {
        this.main = Module.class.newInstance();
    }

    public final void invoke(String path, ParamsWrapper params)
            throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        main.getClass().getMethod("invoke").invoke(main, path, params);
    }
}
