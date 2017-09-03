package com.cxmax.widget;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * @describe :
 * @usage :
 * <p>
 * <p>
 * Created by cxmax on 2017/8/30.
 */

public class DanmakuView extends LinearLayout{

    private static final int MAX_SHOWN_NUM = 15;
    private static final int ROW_NUM = 4;
    private static final int DELAY_DURATION = 500;

    private Context context;
    private int width;
    private int slideDistance;
    private List<View> chlidren;
    private int[] speeds;
    private int[] bgResIds;
    private int[] rowPos;

    {
        chlidren = new ArrayList<>();
        speeds = new int[]{
                3000,4000,5000,6000
        };
        rowPos = new int[]{
                150,140,160,150
        };
    }


    public DanmakuView(Context context) {
        this(context, null);
    }

    public DanmakuView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DanmakuView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
}
