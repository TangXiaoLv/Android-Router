
package com.tangxiaolv.router;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Params wrapper
 *
 * {@link Asker}
 */
public class ParamsWrapper{

    public static final String _PARAMS_ = "_params_";
    private Map params = new HashMap<>();

    @SuppressWarnings("unchecked")
    ParamsWrapper(Object params) throws JSONException {
        if (params instanceof Map) {//inner jsonObject
            this.params = (Map) params;
        } else if (params instanceof List) {//inner jsonArray
            this.params.put(_PARAMS_, params);
        } else if (params instanceof String) {
            String json = params.toString();
            // JSONArray
            if (json.charAt(0) == '[' && json.charAt(json.length() - 1) == ']') {
                this.params.put(_PARAMS_, new JSONArray(json));
                return;
            }
            JSONObject jObj = new JSONObject(json);
            Iterator<String> it = jObj.keys();
            while (it.hasNext()) {
                String key = it.next();
                Object value = jObj.get(key);
                //TODO maybe need parse
                if (value instanceof Double || value instanceof Float) {
                    BigDecimal db = new BigDecimal(value.toString());
                    value = db.toPlainString();
                }
                this.params.put(key, value);
            }
        }
    }

    public Object get(Object key) {
        if (key instanceof String && _PARAMS_.equals(key)) {
            return params;
        }
        return params.get(key);
    }

    @SuppressWarnings("all")
    public void put(String key, Object value) {
        if (params != null)
            params.put(key, value);
    }
}
