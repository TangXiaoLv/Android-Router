
package com.tangxiaolv.router.utils;

import android.text.TextUtils;

import com.tangxiaolv.router.ParamsWrapper;
import com.tangxiaolv.router.exceptions.ValueParseException;
import com.tangxiaolv.router.interfaces.IRouter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Parse tool parameter, from type to expected type.
 */
public class ValueParser {

    /**
     * parse value to expect type.
     *
     * @param from         passed value
     * @param expectedType expected type. like 'java.lang.Integer'
     * @return expected expected value
     * @throws ValueParseException parse exception
     */
    public static Object parse(Object from, String expectedType) throws ValueParseException {
        if (TextUtils.isEmpty(expectedType)) return from;
        if ("int".equals(expectedType) || "java.lang.Integer".equals(expectedType)) {
            from = toInteger(from, 0);
        } else if ("boolean".equals(expectedType) || "java.lang.Boolean".equals(expectedType)) {
            from = toBoolean(from, false);
        } else if ("long".equals(expectedType) || "java.lang.Long".equals(expectedType)) {
            from = toLong(from, 0L);
        } else if ("double".equals(expectedType) || "java.lang.Double".equals(expectedType)) {
            from = toDouble(from, 0d);
        } else if ("float".equals(expectedType) || "java.lang.Float".equals(expectedType)) {
            from = toFloat(from, 0f);
        } else if (expectedType.contains("[")) {
            from = toArray(from, expectedType);
        } else if (expectedType.contains("java.util.List")) {
            from = toList(from, expectedType);
        } else if (expectedType.contains("java.util.Map")) {
            from = toMap(from, expectedType);
        } else if (!"java.lang.Object".equals(expectedType)) {
            from = toTargetObj(from, expectedType);
        }
        return from;
    }

    private static Integer toInteger(Object value, int defVal) {
        if (value instanceof Integer) {
            return (Integer) value;
        } else if (value instanceof Number) {
            return ((Number) value).intValue();
        } else if (value instanceof String) {
            try {
                return (int) Double.parseDouble((String) value);
            } catch (NumberFormatException ignored) {
            }
        }
        return defVal;
    }

    private static Float toFloat(Object value, float defVal) {
        if (value instanceof Float) {
            return (Float) value;
        } else if (value instanceof Number) {
            return ((Number) value).floatValue();
        } else if (value instanceof String) {
            try {
                return (float) Double.parseDouble((String) value);
            } catch (NumberFormatException ignored) {
            }
        }
        return defVal;
    }

    private static Long toLong(Object value, long defVal) {
        if (value instanceof Long) {
            return (Long) value;
        } else if (value instanceof Number) {
            return ((Number) value).longValue();
        } else if (value instanceof String) {
            try {
                return (long) Double.parseDouble((String) value);
            } catch (NumberFormatException ignored) {
            }
        }
        return defVal;
    }

    private static Double toDouble(Object value, double defVal) {
        if (value instanceof Double) {
            return (Double) value;
        } else if (value instanceof Number) {
            return ((Number) value).doubleValue();
        } else if (value instanceof String) {
            try {
                return Double.valueOf((String) value);
            } catch (NumberFormatException ignored) {
            }
        }
        return defVal;
    }

    private static Boolean toBoolean(Object value, boolean defVal) {
        if (value instanceof Boolean) {
            return (Boolean) value;
        } else if (value instanceof String) {
            String stringValue = (String) value;
            if ("true".equalsIgnoreCase(stringValue)) {
                return true;
            } else if ("false".equalsIgnoreCase(stringValue)) {
                return false;
            }
        }
        return defVal;
    }

    @SuppressWarnings("all")
    private static Object toArray(Object from, String expectedType) throws ValueParseException {
        try {
            if (from instanceof String || from instanceof JSONArray) {
                JSONArray jArray;
                if (from instanceof JSONArray) {
                    jArray = (JSONArray) from;
                } else {
                    //checked string format
                    if (!isJson(from.toString())) {
                        throw new ValueParseException("Expected " + expectedType + ",But The input string isn't json.");
                    }
                    jArray = new JSONArray((String) from);
                }

                String type = getExpectedArrayType(expectedType);
                Object target = newArray(type, jArray.length());
                int length = jArray.length();
                for (int i = 0; i < length; i++) {
                    setArray(target, type, i, jArray.get(i));
                }
                from = target;
            }

            //Instance array to expected array
            else if (from != null &&
                    from.getClass().isArray() &&
                    !from.getClass().getCanonicalName().equals(expectedType)) {
                Object[] origin = (Object[]) from;
                String type = getExpectedArrayType(expectedType);
                Object firstEle = origin.length == 0 ? null : origin[0];
                if (firstEle != null && !firstEle.getClass().getCanonicalName().equals(type)) {
                    Object target = newArray(type, origin.length);
                    for (int i = 0; i < origin.length; i++) {
                        setArray(target, type, i, origin[i]);
                    }
                    from = target;
                }
            }
        } catch (Exception e) {
            throw new ValueParseException(from.getClass().getCanonicalName() + " parse to " + expectedType + " type fail.", e);
        }
        return from;
    }

    // maybe List<?>
    private static Object toList(Object from, String expectedType) throws ValueParseException {
        try {
            if (from instanceof String || from instanceof JSONArray) {
                JSONArray jArray = from instanceof String ? new JSONArray((String) from) : (JSONArray) from;
                String generic = getListGeneric(expectedType);
                ArrayList<Object> list = new ArrayList<>(jArray.length());
                int length = jArray.length();
                for (int i = 0; i < length; i++) {
                    Object o = jArray.get(i);
                    list.add(parse(o, generic));
                }
                from = list;
            } else if (from instanceof List) {
                List origin = (List) from;
                List<Object> target = new ArrayList<>(origin.size());
                String listGeneric = getListGeneric(expectedType);
                Object first = origin.get(0);
                if (first != null && !first.getClass().getCanonicalName().equalsIgnoreCase(listGeneric)) {
                    for (Object o : origin) {
                        target.add(parse(o, listGeneric));
                    }
                    from = target;
                }
            } else if (from instanceof Map) {
                Object params = ((Map) from).get(ParamsWrapper._PARAMS_);
                from = parse(params, expectedType);
            }
        } catch (JSONException e) {
            throw new ValueParseException("parse to " + expectedType + " type fail.", e);
        }
        return from;
    }

    private static Object toMap(Object from, String expectType) throws ValueParseException {
        try {
            if (from instanceof String || from instanceof JSONObject) {
                JSONObject jObj;
                if (from instanceof JSONObject) {
                    jObj = (JSONObject) from;
                } else {
                    //checked string format
                    if (!isJson(from.toString())) {
                        throw new ValueParseException("Expected " + expectType + ",But The input string isn't json.");
                    }
                    jObj = new JSONObject((String) from);
                }

                HashMap<String, String> map = new HashMap<>(jObj.length());
                Iterator<String> it = jObj.keys();
                while (it.hasNext()) {
                    String key = it.next();
                    map.put(key, jObj.get(key).toString());
                }
                from = map;
            }
        } catch (Exception e) {
            throw new ValueParseException("parse to " + expectType + " type fail.", e);
        }
        return from;
    }

    private static Object toTargetObj(Object from, String expectType) throws ValueParseException {
        if (from instanceof String || from instanceof JSONObject) {
            from = parseJsonToTarget(from, expectType);
        } else if (from instanceof Map) {
            from = parseMapToTarget(from, expectType);
        } else if (canToParse(from, expectType) &&
                !expectType.equalsIgnoreCase(from.getClass().getCanonicalName())) {
            from = parseObjToTarget(from, expectType);
        }
        return from;
    }

    private static boolean canToParse(Object from, String expectType) {
        try {
            if (!(from instanceof IRouter)) return false;

            Class<?>[] interfaces = Class.forName(expectType).getInterfaces();
            boolean hasIRouter = false;
            if (interfaces.length != 0) {
                for (Class<?> i : interfaces) {
                    if (IRouter.class.getCanonicalName().equals(i.getCanonicalName())) {
                        hasIRouter = true;
                        break;
                    }
                }
            }
            return hasIRouter;
        } catch (ClassNotFoundException e) {
            return false;
        }
    }

    //from json => to obj
    @SuppressWarnings("all")
    private static Object parseJsonToTarget(Object from, String expectType) throws ValueParseException {
        try {
            if (!isJson(from.toString())) return from;
            JSONObject jObj = from instanceof String ? new JSONObject((String) from) : (JSONObject) from;
            Class<?> targetClass = Class.forName(getNoGenericTypeName(expectType));
            Object target = targetClass.newInstance();
            do {
                Field[] fields = targetClass.getDeclaredFields();
                for (Field f : fields) {
                    String name = f.getName();
                    String type = ReflectTool.getFieldTypeWithGeneric(f);
                    f.setAccessible(true);
                    f.set(target, parse(jObj.get(name), type));
                }
                targetClass = targetClass.getSuperclass();
            } while (!(targetClass == Object.class));
            from = target;
        } catch (JSONException ignored) {
        } catch (Exception e) {
            throw new ValueParseException("parse to " + expectType + " type fail.", e);
        }
        return from;
    }

    //from params => to obj
    private static Object parseMapToTarget(Object from, String expectType) throws ValueParseException {
        try {
            Class<?> targetClass = Class.forName(getNoGenericTypeName(expectType));
            Object target = targetClass.newInstance();
            Field[] toFields = targetClass.getDeclaredFields();
            Map params = (Map) from;
            do {
                for (Field toF : toFields) {
                    String toKey = toF.getName();
                    Object fromValue = params.get(toKey);
                    toF.setAccessible(true);
                    toF.set(target, parse(fromValue, ReflectTool.getFieldTypeWithGeneric(toF)));
                }
                targetClass = targetClass.getSuperclass();
            } while (!(targetClass == Object.class));
            from = target;
        } catch (Exception e) {
            throw new ValueParseException("parse to " + expectType + " type fail.", e);
        }
        return from;
    }

    //from obj => to obj
    private static Object parseObjToTarget(Object from, String expectType) throws ValueParseException {
        try {
            Class<?> targetClass = Class.forName(getNoGenericTypeName(expectType));
            Object target = targetClass.newInstance();
            Map<String, Object> kvs = extractKeyValue(from);
            do {
                Field[] toFields = targetClass.getDeclaredFields();
                for (Field toF : toFields) {
                    toF.setAccessible(true);
                    Object fromValue = kvs.get(toF.getName());
                    toF.set(target, parse(fromValue, ReflectTool.getFieldTypeWithGeneric(toF)));
                }
                targetClass = targetClass.getSuperclass();
            } while (!(targetClass == Object.class));
            from = target;
        } catch (Exception e) {
            throw new ValueParseException("parse to " + expectType + " type fail.", e);
        }
        return from;
    }

    private static Map<String, Object> extractKeyValue(Object obj) throws IllegalAccessException {
        Class<?> clazz = obj.getClass();
        HashMap<String, Object> kvs = new HashMap<>();
        do {
            Field[] fields = clazz.getDeclaredFields();
            for (Field f : fields) {
                f.setAccessible(true);
                Object value = f.get(obj);
                kvs.put(f.getName(), value);
            }
            clazz = clazz.getSuperclass();
        } while (!(clazz == Object.class));
        return kvs;
    }

    private static String getListGeneric(String type) {
        return type.contains("<") ? type.substring(type.indexOf("<") + 1, type.indexOf(">")) : "";
    }

    private static String[] getMapGenericity(String type) {
        if (type.contains("<")) {
            return type.substring(type.indexOf("<") + 1, type.indexOf(">")).split(",");
        }
        return null;
    }

    private static String getNoGenericTypeName(String className) {
        int index = className.indexOf("<");
        if (index != -1) {
            className = className.substring(0, index);
        }
        return className;
    }

    private static boolean isJson(String str) {
        return (str.contains("{") && str.lastIndexOf("}") != -1) ||
                (str.contains("[") && str.lastIndexOf("]") != -1);
    }

    private static String getExpectedArrayType(String expectedType) {
        if (expectedType.contains("[]")) {
            expectedType = expectedType.replace("[]", "");
        }
        return expectedType;
    }

    private static Object newArray(String arrayType, int length) throws ClassNotFoundException {
        Object arr;
        if ("int".equals(arrayType)) {
            arr = new int[length];
        } else if ("boolean".equals(arrayType)) {
            arr = new boolean[length];
        } else if ("long".equals(arrayType)) {
            arr = new long[length];
        } else if ("double".equals(arrayType)) {
            arr = new double[length];
        } else if ("float".equals(arrayType)) {
            arr = new float[length];
        } else if ("java.lang.String".equals(arrayType)) {
            arr = new String[length];
        } else {
            arr = Array.newInstance(Class.forName(arrayType), length);
        }
        return arr;
    }

    private static void setArray(Object array, String arrayType, int index, Object value) throws ValueParseException {
        if ("int".equals(arrayType)) {
            Array.setInt(array, index, (Integer) parse(value, arrayType));
        } else if ("boolean".equals(arrayType)) {
            Array.setBoolean(array, index, (Boolean) parse(value, arrayType));
        } else if ("long".equals(arrayType)) {
            Array.setLong(array, index, (Long) parse(value, arrayType));
        } else if ("double".equals(arrayType)) {
            Array.setDouble(array, index, (Double) parse(value, arrayType));
        } else if ("float".equals(arrayType)) {
            Array.setFloat(array, index, (Float) parse(value, arrayType));
        } else {
            Array.set(array, index, parse(value, arrayType));
        }
    }
}
