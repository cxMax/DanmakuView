package com.cxmax.danmakuview.library.danmaku.typepool;

import android.support.annotation.NonNull;

import com.cxmax.danmakuview.library.danmaku.model.AbsDanmakuItemProvider;
import com.cxmax.danmakuview.library.danmaku.typepool.linker.Linker;

import java.util.List;

/**
 * @describe :
 * @usage :
 * <p>
 * </p>
 * Created by caixi on 17-9-7.
 */

public interface ITypePool {

    <T> void register(@NonNull Class<? extends T> clazz, @NonNull AbsDanmakuItemProvider<T> provider, @NonNull Linker<T> linker);

    int firstIndexOf(@NonNull Class<?> clazz);

    @NonNull List<Class<?>> getClasses();

    @NonNull List<AbsDanmakuItemProvider<?>> getItemViewProviders();

    @NonNull List<Linker<?>> getLinkers();
}
