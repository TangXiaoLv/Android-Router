
package com.tangxiaolv.router.utils;

import com.tangxiaolv.router.ParamsWrapper;
import com.tangxiaolv.router.exceptions.ValueParseException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
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
        } else if (expectedType.contains("[]")) {
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
                JSONArray jArray = from instanceof String ? new JSONArray((String) from) : (JSONArray) from;
                String arrayType = expectedType.replace("[]", "");
                Object[] targetArr = (Object[]) Array.newInstance(Class.forName(arrayType), jArray.length());
                int length = jArray.length();
                for (int i = 0; i < length; i++) {
                    Object o = jArray.get(i);
                    targetArr[i] = parse(o, arrayType);
                }
                from = targetArr;
            } else if (from != null && from.getClass().getName().contains("[")) {
                Object[] origin = (Object[]) from;
                String arrayType = expectedType.replace("[]", "");
                Object[] targetArr = (Object[]) Array.newInstance(Class.forName(arrayType), origin.length);
                Object first = origin[0];
                if (first != null && !first.getClass().getName().equals(arrayType)) {
                    for (int i = 0; i < origin.length; i++) {
                        targetArr[i] = parse(origin[i], arrayType);
                    }
                } else {
                    targetArr = origin;
                }
                from = targetArr;
            }
        } catch (JSONException e) {
            throw new ValueParseException("parse to " + expectedType + " type fail.", e);
        } catch (ClassNotFoundException e) {
            throw new ValueParseException("parse to " + expectedType + " type fail.", e);
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
                    if (generic.length() > 0) {
                        list.add(parse(o, generic));
                    } else {
                        list.add(o);
                    }
                }
                from = list;
            } else if (from instanceof List) {
                List origin = (List) from;
                List<Object> target = new ArrayList<>(origin.size());
                String listGeneric = getListGeneric(expectedType);
                Object first = origin.get(0);
                if (first != null && !first.getClass().getName().equalsIgnoreCase(listGeneric)) {
                    for (Object o : origin) {
                        target.add(parse(o, listGeneric));
                    }
                }
                from = target;
            } else if (from instanceof Map) {
                Object params = ((Map) from).get(ParamsWrapper._PARAMS_);
                from = parse(params, expectedType);
            }
        } catch (JSONException e) {
            throw new ValueParseException("parse to " + expectedType + " type fail.", e);
        }
        return from;
    }

    private static Object toMap(Object from, String expectType) {
        if (from instanceof String || from instanceof JSONObject) {
            try {
                JSONObject jObj = from instanceof String ? new JSONObject((String) from) : (JSONObject) from;
                HashMap<String, Object> map = new HashMap<>(jObj.length());
                Iterator<String> it = jObj.keys();
                while (it.hasNext()) {
                    String key = it.next();
                    Object v = jObj.get(key);
                    map.put(key, v);
                }
                from = map;
            } catch (Exception ignored) {
            }
        }
        return from;
    }

    private static Object toTargetObj(Object from, String expectType) throws ValueParseException {
        if (from instanceof String || from instanceof JSONObject) {
            from = parseJsonToObj(from, expectType);
        } else if (from instanceof Map) {
            from = parseMapToTarget(from, expectType);
        } else if (from != null && !expectType.equalsIgnoreCase(from.getClass().getName())) {
            from = parseObjToTarget(from, expectType);
        }
        return from;
    }

    //from json => to obj
    @SuppressWarnings("all")
    private static Object parseJsonToObj(Object from, String expectType) throws ValueParseException {
        try {
            JSONObject jObj = from instanceof String ? new JSONObject((String) from) : (JSONObject) from;
            Class<?> clazz = Class.forName(getNoGenericTypeName(expectType));
            Object target = clazz.newInstance();
            Iterator<String> it = jObj.keys();
            while (it.hasNext()) {
                try {
                    String key = it.next();
                    Field f = clazz.getDeclaredField(key);
                    String name = f.getName();
                    String type = getTypeNameWhithGeneric(f);
                    f.setAccessible(true);
                    f.set(target, parse(jObj.get(name), type));
                } catch (JSONException ignored) {
                } catch (NoSuchFieldException ignored) {
                }
            }
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
            Class<?> clazz = Class.forName(getNoGenericTypeName(expectType));
            Object target = clazz.newInstance();
            Field[] toFields = clazz.getDeclaredFields();
            Map params = (Map) from;
            for (Field toF : toFields) {
                String toKey = toF.getName();
                Object fromValue = params.get(toKey);
                toF.setAccessible(true);
                toF.set(target, parse(fromValue, getTypeNameWhithGeneric(toF)));
            }
            from = target;
        } catch (Exception e) {
            throw new ValueParseException("parse to " + expectType + " type fail.", e);
        }
        return from;
    }

    //from obj => to obj
    private static Object parseObjToTarget(Object from, String expectType) throws ValueParseException {
        try {
            Class<?> clazz = Class.forName(getNoGenericTypeName(expectType));
            Object target = clazz.newInstance();
            Field[] toFields = clazz.getDeclaredFields();
            for (Field toF : toFields) {
                try {
                    String toKey = toF.getName();
                    Field fromField = from.getClass().getDeclaredField(toKey);
                    fromField.setAccessible(true);
                    Object fromValue = fromField.get(from);
                    toF.setAccessible(true);
                    toF.set(target, parse(fromValue, getTypeNameWhithGeneric(toF)));
                } catch (NoSuchFieldException ignored) {
                }
            }
            from = target;
        } catch (Exception e) {
            throw new ValueParseException("parse to " + expectType + " type fail.", e);
        }
        return from;
    }

    private static String getListGeneric(String type) {
        if (type.contains("<")) {
            return type.substring(type.indexOf("<") + 1, type.indexOf(">"));
        }
        return "";
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

    // eg: List<Simple>
    @SuppressWarnings("all")
    private static String getTypeNameWhithGeneric(Field f) {
        String type = f.getType().getName();
        Class fieldType = f.getType();
        if (fieldType.isAssignableFrom(List.class)) {
            ParameterizedType pt = null;
            try {
                pt = (ParameterizedType) f.getGenericType();
                String genericType = ((Class) pt.getActualTypeArguments()[0]).getName();
                return type + "<" + genericType + ">";
            } catch (ClassCastException ignored) {
            }
        }
        return type;
    }
}
