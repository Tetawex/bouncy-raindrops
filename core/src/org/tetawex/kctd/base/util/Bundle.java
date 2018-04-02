package org.tetawex.kctd.base.util;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by tetawex on 28.01.17.
 */
public class Bundle {

    public static Bundle fromSingleValue(String key, Object value) {
        Bundle bundle = new Bundle();
        bundle.putItem(key, value);
        return bundle;
    }

    public static Bundle fromDoubleValue(String key1, Object val1, String key2, Object val2) {
        Bundle bundle = new Bundle();
        bundle.putItem(key1, val1);
        bundle.putItem(key2, val2);
        return bundle;
    }

    private Map<String, Object> items;

    public Bundle() {
        items = new HashMap<String, Object>();
    }

    public void putItem(String key, Object item) {
        items.put(key, item);
    }

    public <T> T getItem(String key, Class<T> c, T defaultValue) {
        if (items.containsKey(key))
            return c.cast(items.get(key));
        return defaultValue;
    }
}
