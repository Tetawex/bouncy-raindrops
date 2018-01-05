package org.tetawex.kctd.base.util;

/**
 * Created by tetawex on 04.09.17.
 */
public class ImmutableBundle extends Bundle {
    private static ImmutableBundle bundle;

    public static ImmutableBundle get() {
        if (bundle == null)
            bundle = new ImmutableBundle();
        return bundle;
    }

    private ImmutableBundle() {
        super();
    }

    public void putItem(String key, Object item) {
        throw new UnsupportedOperationException("u cant modify the bundle, dummy");
    }
}
