
package com.tangxiaolv.router;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ParamsWrapper extends HashMap<String, Object> {

    private static final String __PARAMS__ = "__params__";
    private Map params;

    ParamsWrapper(Object params) throws JSONException {
        if (params instanceof String) {
            String json = (String) params;
            // JSONArray
            if (json.charAt(0) == '[' && json.charAt(json.length() - 1) == ']') {
                put(__PARAMS__, new JSONArray(json));
                return;
            }
            JSONObject jObj = new JSONObject(json);
            Iterator<String> it = jObj.keys();
            while (it.hasNext()) {
                String key = it.next();
                Object value = jObj.get(key);
                //TODO maybe need parse
                // if (value instanceof Double || value instanceof Float) {
                // BigDecimal db = new BigDecimal(value.toString());
                // value = db.toPlainString();
                // }
                put(key, value);
            }
        } else if (params instanceof Map) {
            this.params = (Map) params;
        }
    }

    @Override
    public Object get(Object key) {
        if (params != null) {
            return params.get(key);
        }
        return super.get(key);
    }

    @SuppressWarnings("all")
    @Override
    public Object put(String key, Object value) {
        if (params != null) {
            return params.put(key, value);
        }
        return super.put(key, value);
    }
}
