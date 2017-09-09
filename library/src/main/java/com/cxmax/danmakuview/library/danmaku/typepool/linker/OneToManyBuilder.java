package com.cxmax.danmakuview.library.danmaku.typepool.linker;

import android.support.annotation.NonNull;

import com.cxmax.danmakuview.library.danmaku.IAdapter;
import com.cxmax.danmakuview.library.danmaku.model.AbsDanmakuItemProvider;

/**
 * @describe :
 * @usage :
 * <p>
 * </p>
 * Created by caixi on 17-9-9.
 */

public class OneToManyBuilder<T> implements OneToManyFlow<T>,OneToManyEndpoint<T> {

    @NonNull private final IAdapter adapter;
    @NonNull private final Class<? extends T> clazz;
    private AbsDanmakuItemProvider<T>[] providers;

    public OneToManyBuilder(@NonNull IAdapter adapter, @NonNull Class<? extends T> clazz) {
        this.adapter = adapter;
        this.clazz = clazz;
    }

    @Override
    public void withLinker(@NonNull Linker<T> linker) {
        doRegister(linker);
    }

    @Override
    public void withClassLinker(@NonNull ClassLinker<T> classLinker) {
        doRegister(ClassLinkerWrapper.wrap(classLinker, providers));
    }

    @NonNull
    @Override
    public OneToManyEndpoint<T> to(@NonNull AbsDanmakuItemProvider<T>[] providers) {
        this.providers = providers;
        return this;
    }

    private void doRegister(@NonNull Linker<T> linker) {
        for (AbsDanmakuItemProvider<T> provider : providers) {
            adapter.registerWithLinker(clazz, provider, linker);
        }
    }

}
