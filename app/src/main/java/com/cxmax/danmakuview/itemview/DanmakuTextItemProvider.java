package com.cxmax.danmakuview.itemview;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.TextView;

import com.cxmax.danmakuview.R;
import com.cxmax.danmakuview.bean.Text;
import com.cxmax.danmakuview.library.danmaku.model.AbsDanmakuItemProvider;

import java.util.Random;

/**
 * @describe :
 * @usage :
 * <p>
 * </p>
 * Created by caixi on 17-9-9.
 */

public class DanmakuTextItemProvider extends AbsDanmakuItemProvider<Text>{

    private TextView title;

    @Override
    public int initializeLayoutRes() {
        return R.layout.item_danmaku_text_view;
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
    public void updateView(Text text) {
        title.setText(text.title);
        Random random = new Random(System.currentTimeMillis());
        title.setTextColor(Color.rgb(random.nextInt(256), random.nextInt(256), random.nextInt(256)));
    }

}
