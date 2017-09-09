package com.cxmax.danmakuview.itemview;

import android.graphics.Rect;
import android.support.annotation.NonNull;
import android.text.TextPaint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cxmax.danmakuview.R;
import com.cxmax.danmakuview.bean.Image;
import com.cxmax.danmakuview.library.danmaku.model.AbsDanmakuItemProvider;

/**
 * @describe :
 * @usage :
 * <p>
 * </p>
 * Created by caixi on 17-9-9.
 */

public class DanmakuImageItemProvider extends AbsDanmakuItemProvider<Image> {

    private LinearLayout layout;
    private ImageView icon;
    private TextView title;

    @Override
    public View createView(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
        View root = inflater.inflate(R.layout.item_danmaku_image_view, parent);
        layout = (LinearLayout) root.findViewById(R.id.layout);
        icon = (ImageView) root.findViewById(R.id.icon);
        title = (TextView) root.findViewById(R.id.title);
        return root;
    }

    @Override
    public void updateView(Image image) {
        icon.setImageResource(R.mipmap.ic_launcher);
        title.setText(image.title);
    }

    @Override
    public void onViewDetached() {

    }

    @Override
    public int onMeasureWidth(Image image) {
        if (layout == null || image == null) {
            return 0;
        }
        return layout.getMeasuredWidth();
    }

    @Override
    public int onMeasureHeight(Image image) {
        if (layout == null || image == null) {
            return 0;
        }
        return layout.getMeasuredHeight();
    }
}
