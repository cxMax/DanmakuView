package com.cxmax.danmakuview.itemview;

import android.graphics.Color;
import android.graphics.Rect;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.text.TextPaint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
    private ConstraintLayout layout;

    @Override
    public int initializeLayoutRes() {
        return R.layout.item_danmaku_text_view;
    }

    @Override
    public void initView(@Nullable View root) {
        layout = (ConstraintLayout) root.findViewById(R.id.layout);
        title = (TextView) root.findViewById(R.id.title);
    }

    @Override
    public void updateView(Text text) {
        title.setText(text.title);
        Random random = new Random(System.currentTimeMillis());
        title.setTextColor(Color.rgb(random.nextInt(256), random.nextInt(256), random.nextInt(256)));
    }

    @Override
    public void onViewDetached() {

    }

}
