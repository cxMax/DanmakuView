package com.cxmax.danmakuview.library.danmaku.typepool;

import android.support.annotation.CheckResult;
import android.support.annotation.NonNull;

import com.cxmax.danmakuview.library.danmaku.model.AbsDanmakuItemProvider;
import com.cxmax.danmakuview.library.danmaku.typepool.linker.Linker;
import com.cxmax.danmakuview.library.danmaku.typepool.linker.OneToManyFlow;

/**
 * @describe : 主要用于每个DanmakuItemView的类型注册和类型分发.
 * @usage :
 * <p>
 * </p>
 * Created by caixi on 17-9-9.
 */

public interface IAdapter {

    <T> void register(@NonNull Class<? extends T> clazz, @NonNull AbsDanmakuItemProvider<T> binder);

    @CheckResult
    <T> OneToManyFlow<T> register(@NonNull Class<? extends T> clazz);

    void registerAll(@NonNull final ITypePool pool);

    <T> void registerWithLinker(@NonNull Class<? extends T> clazz, @NonNull AbsDanmakuItemProvider<T> binder, @NonNull Linker<T> linker);

    int indexInTypesOf(@NonNull Object item);
}
