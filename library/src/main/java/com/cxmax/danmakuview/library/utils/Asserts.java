package com.cxmax.danmakuview.library.utils;

import java.util.Collection;
import java.util.Map;

/**
 * @describe :
 * @usage :
 * <p>
 * </p>
 * Created by caixi on 17-9-1.
 */

public class Asserts {

    public static <T> boolean isEmpty(T t) {
        if (t instanceof Map) {
            return ((Map) t).isEmpty();
        } else if (t instanceof Collection) {
            return ((Collection) t).isEmpty();
        }
        return isNullable(t);
    }

    public static boolean isNullable(Object o) {
        return o == null;
    }
}
