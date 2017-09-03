package com.cxmax.danmakuview.library.utils;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.DisplayMetrics;
import android.view.WindowManager;

import java.util.Collection;
import java.util.Map;

/**
 * @describe :
 * @usage :
 * <p>
 * </p>
 * Created by caixi on 17-9-1.
 */

public class Util {

    @NonNull public static DisplayMetrics getDisplayMetrics(@NonNull Context context) {
        WindowManager mWm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics dm = new DisplayMetrics();
        // 获取屏幕信息
        mWm.getDefaultDisplay().getMetrics(dm);
        return dm;
    }

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
