package com.cxmax.danmakuview.library.danmaku.typepool.linker;

import android.support.annotation.NonNull;

import com.cxmax.danmakuview.library.danmaku.model.AbsDanmakuItemProvider;

/**
 * @describe :
 * @usage :
 * <p>
 * </p>
 * Created by caixi on 17-9-9.
 */

public interface ClassLinker<T> {

    @NonNull
    Class<? extends AbsDanmakuItemProvider<T>> index(@NonNull T t);
}
