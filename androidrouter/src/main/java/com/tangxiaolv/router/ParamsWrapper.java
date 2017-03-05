
package com.tangxiaolv.router;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import static android.R.attr.value;

/**
 * Params wrap
 *
 * {@link Asker}
 */
public class ParamsWrapper {

    private static final String _PARAMS_ = "_params_";
    private Map params;

    ParamsWrapper(Object params) throws JSONException {
        if (params instanceof Map) {
            this.params = (Map) params;
        } else if (params instanceof String) {
            Map<String, Object> m = new HashMap<>();
            String json = params.toString();
            // JSONArray
            if (json.charAt(0) == '[' && json.charAt(json.length() - 1) == ']') {
                m.put(_PARAMS_, new JSONArray(json));
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
                m.put(key, value);
            }
            this.params = m;
        }
    }

    public Object get(Object key) {
        if (key instanceof String && _PARAMS_.equals(key)) {
            return params;
        }
        return params == null ? null : params.get(key);
    }

    @SuppressWarnings("all")
    public void put(String key, Object value) {
        if (params != null)
            params.put(key, value);

    }
}
