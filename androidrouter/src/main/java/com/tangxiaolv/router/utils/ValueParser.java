
package com.tangxiaolv.router.utils;

import com.tangxiaolv.router.exceptions.ValueParserException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

/**
 * Helper for value to parse expected type.
 */
public class ValueParser {

    /**
     * parse value to expect type.
     *
     * @param input        input
     * @param expectedType expected type. like 'java.lang.Integer'
     * @return expected value
     */
    public static Object parse(Object input, String expectedType) throws ValueParserException {
        if ("int".equals(expectedType) || "java.lang.Integer".equals(expectedType)) {
            input = toInteger(input, 0);
        } else if ("boolean".equals(expectedType) || "java.lang.Boolean".equals(expectedType)) {
            input = toBoolean(input, false);
        } else if ("long".equals(expectedType) || "java.lang.Long".equals(expectedType)) {
            input = toLong(input, 0L);
        } else if ("double".equals(expectedType) || "java.lang.Double".equals(expectedType)) {
            input = toDouble(input, 0d);
        } else if ("float".equals(expectedType) || "java.lang.Float".equals(expectedType)) {
            input = toFloat(input, 0f);
        } else if (expectedType.contains("java.util.List")) {
            input = toList(input, expectedType);
        } else if (expectedType.contains("java.util.Map")) {
            input = toMap(input, expectedType);
        } else if (!"java.lang.Object".equals(expectedType)) {
            input = toTargetObj(input, expectedType);
        }
        return input;
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

    // maybe List<?>
    private static Object toList(Object input, String expectType) throws ValueParserException {
        try {
            if (input instanceof String || input instanceof JSONArray) {
                JSONArray jArray = input instanceof String ? new JSONArray((String) input)
                        : (JSONArray) input;
                String genericity = getListGeneric(expectType);
                ArrayList<Object> list = new ArrayList<>(jArray.length());
                int length = jArray.length();
                for (int i = 0; i < length; i++) {
                    Object o = jArray.get(i);
                    if (genericity.length() > 0) {
                        list.add(parse(o, genericity));
                    } else {
                        list.add(o);
                    }
                }
                input = list;
            } else if (input != null && input instanceof List) {
                List origin = (List) input;
                List<Object> target = new ArrayList<>();
                for (Object o : origin) {
                    target.add(parseObjToTarget(o, getListGeneric(expectType)));
                }
                input = target;
            }
        } catch (JSONException e) {
            throw new ValueParserException(" parse expected value fail.", e);
        }
        return input;
    }

    private static Object toMap(Object input, String expectType) {
        if (input instanceof String || input instanceof JSONObject) {
            try {
                JSONObject jObj = input instanceof String ? new JSONObject((String) input)
                        : (JSONObject) input;
                HashMap<String, Object> map = new HashMap<>(jObj.length());
                Iterator<String> it = jObj.keys();
                while (it.hasNext()) {
                    String key = it.next();
                    Object v = jObj.get(key);
                    map.put(key, v);
                }
                input = map;
            } catch (Exception ignored) {
            }
        }
        return input;
    }

    private static Object toTargetObj(Object input, String expectType) throws ValueParserException {
        if (input instanceof String || input instanceof JSONObject) {
            input = parseJsonToObj(input, expectType);
        } else if (input != null && !expectType.equalsIgnoreCase(input.getClass().getName())) {
            input = parseObjToTarget(input, expectType);
        }
        return input;
    }

    //json => obj
    @SuppressWarnings("all")
    private static Object parseJsonToObj(Object input, String expectType) throws ValueParserException {
        try {
            JSONObject jObj = input instanceof String ? new JSONObject((String) input) : (JSONObject) input;
            Class<?> clazz = Class.forName(getNoGenericTypeName(expectType));
            Object target = clazz.newInstance();
            Iterator<String> it = jObj.keys();
            while (it.hasNext()) {
                try {
                    String key = it.next();
                    Field f = clazz.getDeclaredField(key);
                    if (f != null) {
                        String name = f.getName();
                        String type = getTypeNameWhithGeneric(f);
                        f.setAccessible(true);
                        f.set(target, parse(jObj.get(name), type));
                    }
                } catch (JSONException ignored) {
                } catch (NoSuchFieldException ignored) {
                }
            }
            input = target;
        } catch (Exception e) {
            throw new ValueParserException(" parse expected value fail.", e);
        }
        return input;
    }

    //from obj => to obj
    private static Object parseObjToTarget(Object input, String expectType) throws ValueParserException {
        try {
            Class<?> clazz = Class.forName(getNoGenericTypeName(expectType));
            Object target = clazz.newInstance();
            Field[] toFields = clazz.getDeclaredFields();
            for (Field toF : toFields) {
                try {
                    String toKey = toF.getName();
                    Field fromField = input.getClass().getDeclaredField(toKey);
                    fromField.setAccessible(true);
                    Object fromValue = fromField.get(input);
                    toF.setAccessible(true);
                    toF.set(target, parse(fromValue, getTypeNameWhithGeneric(toF)));
                } catch (NoSuchFieldException ignored) {
                }
            }
            input = target;
        } catch (Exception e) {
            throw new ValueParserException(" parse expected value fail.", e);
        }
        return input;
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


    // eg => List<Simple>
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
