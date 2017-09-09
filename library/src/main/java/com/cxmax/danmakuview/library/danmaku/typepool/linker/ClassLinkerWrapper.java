package com.cxmax.danmakuview.library.danmaku.typepool.linker;

import android.support.annotation.NonNull;

import com.cxmax.danmakuview.library.danmaku.model.AbsDanmakuItemProvider;

import java.util.Arrays;

/**
 * @describe :
 * @usage :
 * <p>
 * </p>
 * Created by caixi on 17-9-9.
 */

public class ClassLinkerWrapper<T> implements Linker<T> {

    @NonNull private final ClassLinker<T> classLinker;
    @NonNull private AbsDanmakuItemProvider<T>[] providers;

    public ClassLinkerWrapper(@NonNull ClassLinker<T> classLinker, @NonNull AbsDanmakuItemProvider<T>[] providers) {
        this.classLinker = classLinker;
        this.providers = providers;
    }

    @NonNull static <T> ClassLinkerWrapper<T> wrap(@NonNull ClassLinker<T> classLinker, @NonNull AbsDanmakuItemProvider<T>[] providers) {
        return new ClassLinkerWrapper<T>(classLinker, providers);
    }

    @Override
    public int index(@NonNull T t) {
        Class<?> userIndexClass = classLinker.index(t);
        for (int i = 0; i < providers.length; i++) {
            if (providers[i].getClass().equals(userIndexClass)) {
                return i;
            }
        }
        throw new IndexOutOfBoundsException(
                String.format("%s is out of your registered binders'(%s) bounds.",
                        userIndexClass.getName(), Arrays.toString(providers))
        );
    }

}
