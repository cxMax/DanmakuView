package com.cxmax.danmakuview.itemview;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
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
    public int initializeLayoutRes() {
        return R.layout.item_danmaku_image_view;
    }

    @Override
    public void initView(@NonNull View root) {
        layout = (LinearLayout) root.findViewById(R.id.layout);
        icon = (ImageView) root.findViewById(R.id.icon);
        title = (TextView) root.findViewById(R.id.title);
    }

    @Override
    public void updateView(Image image) {
        icon.setImageResource(R.mipmap.ic_launcher);
        title.setText(image.title);
    }

    @Override
    public void onViewDetached() {
        layout.removeAllViews();
    }

}
