package com.cxmax.danmakuview.library.danmaku.model;

import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cxmax.danmakuview.library.danmaku.param.AnimOption;

/**
 * @describe :
 * @usage :
 * <p>
 * </p>
 * Created by caixi on 17-9-7.
 */

public abstract class AbsDanmakuItemProvider<T> {

    private AnimOption animOption;
    private View view;

    public abstract View createView(@NonNull LayoutInflater inflater,@NonNull ViewGroup parent);

    public abstract void updateView(T t);

    public abstract void onViewDetached();

    public abstract int onMeasureWidth(T t);

    public abstract int onMeasureHeight(T t);

    public AnimOption getAnimOption() {
        return animOption;
    }

    public void setAnimOption(AnimOption animOptions) {
        this.animOption = animOptions;
    }

    public View getView() {
        return view;
    }

    public void setView(View view) {
        this.view = view;
    }
}
