package com.cxmax.danmakuview.library.danmaku.typepool.util;

import android.support.annotation.NonNull;

import com.cxmax.danmakuview.library.danmaku.typepool.IAdapter;
import com.cxmax.danmakuview.library.utils.Asserts;

import java.util.List;

/**
 * @describe :
 * @usage :
 * <p>
 * </p>
 * Created by caixi on 17-9-10.
 */

public class TypePoolAsserts {

    public static void assertAllRegistered(@NonNull IAdapter adapter, @NonNull List<?> items) {
        if (Asserts.isEmpty(items)) {
            throw new NullPointerException("Your Items/List is empty.");
        }
        for (Object item : items) {
            adapter.indexInTypesOf(item);
        }
    }
}
