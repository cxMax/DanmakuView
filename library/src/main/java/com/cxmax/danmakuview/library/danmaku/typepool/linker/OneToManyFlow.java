package com.cxmax.danmakuview.library.danmaku.typepool.linker;

import android.support.annotation.CheckResult;
import android.support.annotation.NonNull;

import com.cxmax.danmakuview.library.danmaku.model.AbsDanmakuItemProvider;

/**
 * @describe :
 * @usage :
 * <p>
 * </p>
 * Created by caixi on 17-9-9.
 */

public interface OneToManyFlow<T> {

    @NonNull
    @CheckResult
    OneToManyEndpoint<T> to(@NonNull AbsDanmakuItemProvider<T>... binders);

}
