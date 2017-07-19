
package com.tangxiaolv.router;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Params wrapper.The middle data map between from and to.
 */
public class ParamsWrapper {

    public static final String _PARAMS_ = "_params_";
    private Map result = new HashMap<>();

    public ParamsWrapper(Object params) throws JSONException {
        result = parseToMap(params);
    }

    public Object get(Object key) {
        if (key instanceof String && _PARAMS_.equals(key)) {
            return result;
        }
        return result.get(key);
    }

    @SuppressWarnings("all")
    public void append(Object appendParams) throws JSONException {
        result.putAll(parseToMap(appendParams));
    }

    @SuppressWarnings("all")
    public void put(String key, Object value) {
        result.put(key, value);
    }

    private Map parseToMap(Object params) throws JSONException {
        Map<String, Object> result = new HashMap<>();
        if (params instanceof Map) {//inner jsonObject
            return (Map) params;
        } else if (params instanceof List) {//inner jsonArray
            result.put(_PARAMS_, params);
        } else if (params instanceof String) {
            String json = params.toString();
            // JSONArray
            if (json.charAt(0) == '[' && json.charAt(json.length() - 1) == ']') {
                result.put(_PARAMS_, new JSONArray(json));
                return result;
            }
            JSONObject jObj = new JSONObject(json);
            Iterator<String> it = jObj.keys();
            while (it.hasNext()) {
                String key = it.next();
                Object value = jObj.get(key);
                if (value instanceof Double || value instanceof Float) {
                    value = value.toString();
                }
                result.put(key, value);
            }
        }
        return result;
    }
}