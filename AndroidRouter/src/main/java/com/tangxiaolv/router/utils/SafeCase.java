
package com.tangxiaolv.router.utils;

public class SafeCase {

    // "scheme,promise,key1,key2"
    // "java.lang.String,com.tangxiaolv.router.Promise,java.lang.String,java.lang.Boolean"
    public static Object getValue(Object input, String expectType)
            throws ClassCastException, NumberFormatException {
        if ("int".equals(expectType)) {
            input = getInt(input, Integer.MIN_VALUE);
        } else if ("boolean".equals(expectType)) {
            input = getBoolean(input, false);
        } else if ("long".equals(expectType)) {
            input = getLong(input, Long.MIN_VALUE);
        } else if ("double".equals(expectType)) {
            input = getDouble(input, Double.MIN_VALUE);
        } else if ("float".equals(expectType)) {
            input = getFloat(input, Long.MIN_VALUE);
        }
        return input;
    }

    private static int getInt(Object input, int defVal) {
        if (input == null)
            return defVal;
        if (input instanceof String) {
            return input.toString().length() > 0 ? Integer.parseInt(input.toString()) : defVal;
        }
        return (int) input;
    }

    private static long getLong(Object input, long defVal) {
        if (input == null)
            return defVal;
        if (input instanceof String) {
            return input.toString().length() > 0 ? Long.parseLong(input.toString()) : defVal;
        }
        return (long) input;
    }

    private static boolean getBoolean(Object input, boolean defVal) {
        if (input == null)
            return defVal;
        if (input instanceof String) {
            return input.toString().length() > 0 ? Boolean.parseBoolean(input.toString()) : defVal;
        }
        return (boolean) input;
    }

    private static double getDouble(Object input, double defVal) {
        if (input == null)
            return defVal;
        if (input instanceof String) {
            return input.toString().length() > 0 ? Double.parseDouble(input.toString()) : defVal;
        }
        return (double) input;
    }

    private static float getFloat(Object input, float defVal) {
        if (input == null)
            return defVal;
        if (input instanceof String) {
            return input.toString().length() > 0 ? Float.parseFloat(input.toString()) : defVal;
        }
        return (float) input;
    }
}
