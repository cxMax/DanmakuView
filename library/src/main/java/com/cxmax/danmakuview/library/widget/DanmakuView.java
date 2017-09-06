package com.cxmax.danmakuview.library.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.CheckResult;
import android.support.annotation.IntDef;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.cxmax.danmakuview.library.R;
import com.cxmax.danmakuview.library.utils.Util;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @describe :
 * @usage :
 * <p>
 * </p>
 * Created by caixi on 17-8-31.
 */

public class DanmakuView extends RelativeLayout {

    private static final int DEFAULT_MAX_SHOWN_NUM = 15;
    private static final int DEFAULT_ROW_NUM = 4;
    static final int DEFAULT_DELAY_DURATION = 500;

    static final int DIRECTION_LEFT = 0x10000000;
    static final int DIRECTION_RIGHT = 0x20000000;

    @IntDef(value = {DIRECTION_LEFT, DIRECTION_RIGHT})
    @Retention(RetentionPolicy.SOURCE)
    @interface Direction {
    }

    @NonNull
    private Context context;
    private int width;
    private int height;
    private List<View> children;
    private int[] speeds;
    private int[] rowPos;
    int lastRow = 0;
    private Random random;
    private List<String> contents;
    private int childTextSize;
    private int childTextColor;
    private boolean childTextClickable;
    private DanmakuHandler handler;

    @Direction
    private int direction = DIRECTION_RIGHT;

    {
        children = new ArrayList<>();
        contents = new ArrayList<>();
        speeds = new int[]{
                3000, 4000, 5000, 6000
        };
        rowPos = new int[]{
                150, 140, 160, 150
        };
        random = new Random();
        handler = new DanmakuHandler(this);
    }

    public DanmakuView(Context context) {
        this(context, null);
    }

    public DanmakuView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DanmakuView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        initializeFields(context, attrs);
    }

    private void initializeFields(Context context, @Nullable AttributeSet attrs) {
        if (attrs != null) {
            TypedArray attr = context.obtainStyledAttributes(attrs, R.styleable.DanmakuView, 0, 0);
            if (attr != null) {
                childTextSize = attr.getDimensionPixelSize(R.styleable.DanmakuView_common_text_size, context.getResources().getDimensionPixelSize(R.dimen.danmaku_common_text_size_12));
                childTextColor = attr.getColor(R.styleable.DanmakuView_common_text_color, ContextCompat.getColor(context, R.color.white));
                childTextClickable = attr.getBoolean(R.styleable.DanmakuView_common_text_clickable, Boolean.FALSE);
                attr.recycle();
            }
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int specMode = MeasureSpec.getMode(widthMeasureSpec);
        int specSize = MeasureSpec.getSize(widthMeasureSpec);
        if (specMode == MeasureSpec.EXACTLY) {
            width = specSize;
        } else if (specMode == MeasureSpec.AT_MOST) {
            width = Util.getDisplayMetrics(context).widthPixels;
        }

        specMode = MeasureSpec.getMode(heightMeasureSpec);
        specSize = MeasureSpec.getSize(heightMeasureSpec);
        if (specMode == MeasureSpec.EXACTLY) {
            height = specSize;
        } else if (specMode == MeasureSpec.AT_MOST) {
            height = Util.getDisplayMetrics(context).heightPixels;
        }

        setMeasuredDimension(width, height);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            View view = getChildAt(i);
            LayoutParams lp = (LayoutParams) view.getLayoutParams();
            if (lp.leftMargin <= 0) {
                if (direction == DIRECTION_LEFT) {
                    view.layout(-view.getMeasuredWidth(), lp.topMargin,
                            0, lp.topMargin + view.getMeasuredHeight());
                } else {
                    view.layout(width, lp.topMargin, width + view.getMeasuredWidth(),
                            lp.topMargin + view.getMeasuredHeight());
                }
            }
        }
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (handler != null) {
            handler.cancel();
        }
    }

    public void addDanmakuViews(List<String> contents) {
        if (!Util.isEmpty(contents)) {
            this.contents = contents;
            for (int i = 0; i < (contents.size() <= DEFAULT_MAX_SHOWN_NUM ? contents.size() : DEFAULT_MAX_SHOWN_NUM); i++) {
                createChildView(i, contents.get(i), false);
            }
        }
    }

    public void start() {
        handler.cancel();
        for (int i = 0; i < children.size(); i++) {
            handler.sendEmptyMessageDelayed(i, i * DEFAULT_DELAY_DURATION);
        }
    }

    public void stop() {
        handler.cancel();
    }

    void createChildView(int index, String content, boolean recreate) {
        final TextView textView = new TextView(context);
        textView.setTextColor(childTextColor);
        textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, childTextSize);
        textView.setText(content);
        textView.setBackgroundColor(ContextCompat.getColor(getContext(),R.color.light_yellow));
        LayoutParams lp = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        int row = random.nextInt(100) % DEFAULT_ROW_NUM;
        while (row == lastRow) {
            row = random.nextInt(100) % DEFAULT_ROW_NUM;
        }
        int pos = random.nextInt(100) % DEFAULT_ROW_NUM;

        lp.topMargin = row * rowPos[pos];
        lastRow = row;
        textView.setLayoutParams(lp);
        textView.setPadding(40, 2, 40, 2);
        textView.setClickable(childTextClickable);
        textView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                //// TODO: 2017/9/3  interface
            }
        });
        addView(textView);
        if (recreate) {
            children.set(index, textView);
        } else {
            children.add(index, textView);
        }
    }

    @CheckResult
    @Direction
    public int getDirection() {
        return direction;
    }

    @NonNull
    public List<View> getChildren() {
        return children;
    }

    public int getViewWidth() {
        return width;
    }

    public int[] getSpeeds() {
        return speeds;
    }

    public Random getRandom() {
        return random;
    }

    public List<String> getContents() {
        return contents;
    }
}
