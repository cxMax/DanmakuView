package com.cxmax.danmakuview.library.danmaku.model;

import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


/**
 * @describe :
 * @usage :
 * <p>
 * </p>
 * Created by caixi on 17-9-7.
 */

public abstract class AbsDanmakuItemProvider<T> {

    private View view;

    public View createView(@NonNull LayoutInflater inflater,@Nullable ViewGroup parent) {
        view = inflater.inflate(initializeLayoutRes(), parent);
        initView(view);
        return view;
    }

    @LayoutRes public abstract int initializeLayoutRes();

    public abstract void initView(@NonNull View root);

    public abstract void updateView(T t);

    /**
     * release resource
     */
    public abstract void onViewDetached();

    public View getView() {
        return view;
    }
}
