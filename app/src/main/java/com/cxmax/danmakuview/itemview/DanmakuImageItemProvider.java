package com.cxmax.danmakuview.itemview;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.TextView;

import com.cxmax.danmakuview.R;
import com.cxmax.danmakuview.bean.Image;
import com.cxmax.danmakuview.library.danmaku.model.AbsDanmakuItemProvider;

import java.util.Random;

/**
 * @describe :
 * @usage :
 * <p>
 * </p>
 * Created by caixi on 17-9-9.
 */

public class DanmakuImageItemProvider extends AbsDanmakuItemProvider<Image> {

    private TextView title;

    @Override
    public int initializeLayoutRes() {
        return R.layout.item_danmaku_image_view;
    }

    @Override
    public ObjectAnimator generateChildAnimator(View child, View parent) {
        ObjectAnimator objAnim = ObjectAnimator
                .ofFloat(child,"translationX" , parent.getWidth(), -child.getWidth())
                .setDuration(new Random().nextInt(6000 - 3000) + 3000);
        objAnim.setInterpolator(new LinearInterpolator());
        return objAnim;
    }

    @Override
    public void initView(@NonNull View root) {
        title = (TextView) root.findViewById(R.id.title);
    }

    @Override
    public void updateView(Image image) {
        title.setText(image.title);
        Random random = new Random(System.currentTimeMillis());
        title.setTextColor(Color.rgb(random.nextInt(256), random.nextInt(256), random.nextInt(256)));
        if (title.getContext() != null) {
            Drawable drawable = title.getContext().getResources().getDrawable(R.mipmap.ic_launcher);
            drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
            title.setCompoundDrawables(drawable, null, null, null);
        }
    }

}
