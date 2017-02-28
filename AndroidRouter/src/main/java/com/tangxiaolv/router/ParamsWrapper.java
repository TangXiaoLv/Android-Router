
package com.tangxiaolv.router;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

public class ParamsWrapper extends HashMap<String, Object> {

    private Map params;

    ParamsWrapper(Object params) throws JSONException {
        if (params instanceof String) {
            JSONObject jobj = new JSONObject((String) params);
            Iterator<String> it = jobj.keys();
            while (it.hasNext()) {
                String key = it.next();
                Object value = jobj.get(key);
                if (value instanceof Double || value instanceof Float) {
                    BigDecimal db = new BigDecimal(value.toString());
                    value = db.toPlainString();
                }
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

    @Override
    public Object put(String key, Object value) {
        if (params != null) {
            return params.put(key, value);
        }
        return super.put(key, value);
    }
}
