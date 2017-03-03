
package com.tangxiaolv.router.module;

import java.lang.reflect.Method;
import java.util.Map;

import com.tangxiaolv.router.ParamsWrapper;
import com.tangxiaolv.router.exceptions.NotFoundPathException;
import com.tangxiaolv.router.utils.ValueParser;

public class ModuleDelegater {

    static final String _METHOD = "_METHOD";
    static final String _ARGS = "_AGRS";
    static final String _TYPES = "_TYPES";

    static void invoke(String path, ParamsWrapper params, Object target,
                              Map<String, Object> mapping) throws Exception {
        Method method = (Method) mapping.get(path + _METHOD);
        if (method == null) {
            throw new NotFoundPathException("path not found: " + path);
        }

        String args = (String) mapping.get(path + _ARGS);
        String types = (String) mapping.get(path + _TYPES);
        boolean empty = args == null || args.length() == 0;
        if (!empty) {
            if (args.contains(",")) {
                String[] agrNames = args.split(",");
                String[] typeNames = types.split(",");
                Object[] arr = new Object[agrNames.length];
                for (int i = 0; i < agrNames.length; i++) {
                    arr[i] = ValueParser.parse(params.get(agrNames[i]), typeNames[i]);
                }
                method.invoke(target, arr);
                return;
            }
            method.invoke(target, ValueParser.parse(args, types));
            return;
        }
        method.invoke(target);
    }
}
