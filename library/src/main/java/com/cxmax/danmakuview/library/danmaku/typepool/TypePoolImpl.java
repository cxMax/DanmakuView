package com.cxmax.danmakuview.library.danmaku.typepool;

import android.support.annotation.NonNull;

import com.cxmax.danmakuview.library.danmaku.model.AbsDanmakuItemProvider;
import com.cxmax.danmakuview.library.danmaku.typepool.linker.Linker;

import java.util.ArrayList;
import java.util.List;

/**
 * @describe :
 * @usage :
 * <p>
 * </p>
 * Created by caixi on 17-9-9.
 */

public class TypePoolImpl implements ITypePool {

    private @NonNull final List<Class<?>> classes;
    private @NonNull final List<AbsDanmakuItemProvider<?>> providers;
    private @NonNull final List<Linker<?>> linkers;

    public TypePoolImpl() {
        this.classes = new ArrayList<>();
        this.providers = new ArrayList<>();
        this.linkers = new ArrayList<>();
    }

    public TypePoolImpl(int capacity) {
        this.classes = new ArrayList<>(capacity);
        this.providers = new ArrayList<>(capacity);
        this.linkers = new ArrayList<>(capacity);
    }

    public TypePoolImpl(@NonNull List<Class<?>> classes, @NonNull List<AbsDanmakuItemProvider<?>> binders, @NonNull List<Linker<?>> linkers) {
        this.classes = classes;
        this.providers = binders;
        this.linkers = linkers;
    }

    @Override
    public <T> void register(@NonNull Class<? extends T> clazz, @NonNull AbsDanmakuItemProvider<T> provider, @NonNull Linker<T> linker) {
        classes.add(clazz);
        providers.add(provider);
        linkers.add(linker);
    }

    @Override
    public int firstIndexOf(@NonNull Class<?> clazz) {
        int index = classes.indexOf(clazz);
        if (index != -1) {
            return index;
        }
        for (int i = 0; i < classes.size(); i++) {
            if (classes.get(i).isAssignableFrom(clazz)) {
                return i;
            }
        }
        return -1;
    }

    @NonNull
    @Override
    public List<Class<?>> getClasses() {
        return classes;
    }

    @NonNull
    @Override
    public List<AbsDanmakuItemProvider<?>> getItemViewProviders() {
        return providers;
    }

    @NonNull
    @Override
    public List<Linker<?>> getLinkers() {
        return linkers;
    }
}
