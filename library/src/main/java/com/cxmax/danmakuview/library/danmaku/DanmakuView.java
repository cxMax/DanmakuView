package com.cxmax.danmakuview.library.danmaku;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;

import com.cxmax.danmakuview.library.R;
import com.cxmax.danmakuview.library.danmaku.model.AbsDanmakuItemProvider;
import com.cxmax.danmakuview.library.danmaku.param.DanmakuConfig;
import com.cxmax.danmakuview.library.danmaku.param.DanmakuOrientation;
import com.cxmax.danmakuview.library.danmaku.typepool.IAdapter;
import com.cxmax.danmakuview.library.danmaku.typepool.ITypePool;
import com.cxmax.danmakuview.library.danmaku.typepool.TypePoolImpl;
import com.cxmax.danmakuview.library.danmaku.typepool.linker.DefaultLinker;
import com.cxmax.danmakuview.library.danmaku.typepool.linker.Linker;
import com.cxmax.danmakuview.library.danmaku.typepool.linker.OneToManyBuilder;
import com.cxmax.danmakuview.library.danmaku.typepool.linker.OneToManyFlow;
import com.cxmax.danmakuview.library.utils.Asserts;
import com.cxmax.danmakuview.library.utils.DevicesUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static com.cxmax.danmakuview.library.danmaku.param.DanmakuOrientation.DIRECTION_BOTTOM_TO_TOP;
import static com.cxmax.danmakuview.library.danmaku.param.DanmakuOrientation.DIRECTION_LEFT_TO_RIGHT;
import static com.cxmax.danmakuview.library.danmaku.param.DanmakuOrientation.DIRECTION_RIGHT_TO_LEFT;
import static com.cxmax.danmakuview.library.danmaku.param.DanmakuOrientation.DIRECTION_TOP_TO_BOTTOM;

/**
 * @describe :
 * @usage :
 * <p>
 * </p>
 * Created by caixi on 17-8-31.
 */
public class DanmakuView extends RelativeLayout implements IAdapter, IDanmakuView {

    private static final String TAG = "com.cxmax.DanmakuView";

    @NonNull private final ITypePool typePool;
    @NonNull private List<?> data;
    @NonNull private DanmakuConfig options;
    @NonNull private Context context;
    @NonNull private DanmakuHandler handler;
    @NonNull private Random random;

    {
        data = new ArrayList<>();
        typePool = new TypePoolImpl();
        options = DanmakuConfig.create();
        random = new Random(System.currentTimeMillis());
        handler = DanmakuHandler.create(this, random);
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
        initializeCustomAttrs(context, attrs);
    }

    private void initializeCustomAttrs(Context context, @Nullable AttributeSet attrs) {
        if (attrs != null) {
            TypedArray attr = context.obtainStyledAttributes(attrs, R.styleable.DanmakuView, 0, 0);
            if (attr != null) {
                options.setCommonTextSize(attr.getDimensionPixelSize(R.styleable.DanmakuView_common_text_size, context.getResources().getDimensionPixelSize(R.dimen.danmaku_common_text_size_12)));
                options.setCommonTextColor(attr.getColor(R.styleable.DanmakuView_common_text_color, ContextCompat.getColor(context, R.color.black)));
                options.setCommonTextClickable(attr.getBoolean(R.styleable.DanmakuView_common_text_clickable, Boolean.FALSE));
                options.setDuration(attr.getInteger(R.styleable.DanmakuView_item_duration, DanmakuConfig.DEFAULT_DELAY_DURATION));
                options.setMaxRowNum(attr.getInteger(R.styleable.DanmakuView_max_row_num, DanmakuConfig.DEFAULT_ROW_NUM));
                options.setMaxShownNum(attr.getInteger(R.styleable.DanmakuView_max_children_num, DanmakuConfig.DEFAULT_MAX_SHOWN_NUM));
                options.setOrientation(attr.getInteger(R.styleable.DanmakuView_common_orientation, DIRECTION_RIGHT_TO_LEFT));
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
            options.width = specSize;
        } else if (specMode == MeasureSpec.AT_MOST) {
            options.width = DevicesUtil.getDisplayMetrics(context).widthPixels;
        }

        specMode = MeasureSpec.getMode(heightMeasureSpec);
        specSize = MeasureSpec.getSize(heightMeasureSpec);
        if (specMode == MeasureSpec.EXACTLY) {
            options.height = specSize;
        } else if (specMode == MeasureSpec.AT_MOST) {
            options.height = DevicesUtil.getDisplayMetrics(context).heightPixels;
        }

        setMeasuredDimension(options.width, options.height);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        // 摆放view位置的代码放在这里, 方便为了获取child的宽高
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            View view = getChildAt(i);
            RelativeLayout.LayoutParams lp = (LayoutParams) view.getLayoutParams();
            switch (options.getOrientation()) {
                case DIRECTION_LEFT_TO_RIGHT:
                    view.layout(-view.getWidth(), lp.topMargin, 0, lp.topMargin + view.getHeight());
                    break;
                case DIRECTION_RIGHT_TO_LEFT:
                    view.layout(-view.getWidth(), lp.topMargin, view.getWidth(), lp.topMargin + view.getHeight());
                    break;
                case DIRECTION_TOP_TO_BOTTOM:
                    view.layout(getWidth(), lp.topMargin + view.getHeight(), getWidth() + view.getWidth(), lp.topMargin);
                    break;
                case DIRECTION_BOTTOM_TO_TOP:
                    view.layout(getWidth(), lp.topMargin, getWidth() + view.getWidth(), lp.topMargin + view.getHeight());
                    break;
                default:
                    view.layout(getWidth(), lp.topMargin, getWidth() + view.getWidth(), lp.topMargin + view.getHeight());
                    break;
            }
        }

    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        release();
    }

    // ------------- 弹幕控制调用函数 -------------------

    @Override
    public void prepare() {
        assureNecessaryParamsNotNull();
    }

    @Override
    public void start() {
        if (getChildCount() == 0) {
            initializeChildren();
        }
    }

    @Override
    public void stop() {
        release();
    }

    @Override
    public void pause() {
        handler.removeMessages(DanmakuHandler.ANIM_START);
        handler.sendEmptyMessage(DanmakuHandler.ANIM_PAUSE);
    }

    @Override
    public void resume() {
        handler.sendEmptyMessage(DanmakuHandler.ANIM_RESUME);
    }

    @Override
    public void release() {
        handler.release();
        removeAllViews();
    }

    @Override
    public void show() {
        resume();
        setVisibility(VISIBLE);
    }

    @Override
    public void hide() {
        pause();
        setVisibility(GONE);
    }

    // ------------- 弹幕childrenView 的类型注册与类型分发 -------------------

    @Override
    public <T> void register(@NonNull Class<? extends T> clazz, @NonNull AbsDanmakuItemProvider<T> binder) {
        checkAndRemoveAllTypesIfNeed(clazz);
        typePool.register(clazz, binder, new DefaultLinker<T>());
    }

    @Override
    public <T> OneToManyFlow<T> register(@NonNull Class<? extends T> clazz) {
        checkAndRemoveAllTypesIfNeed(clazz);
        return new OneToManyBuilder<>(this, clazz);
    }

    @Override
    public void registerAll(@NonNull ITypePool pool) {
        final int size = pool.getClasses().size();
        for (int i = 0; i < size; i++) {
            registerWithoutChecking(pool.getClasses().get(i), pool.getItemViewProviders().get(i), pool.getLinkers().get(i));
        }
    }

    @Override
    public <T> void registerWithLinker(@NonNull Class<? extends T> clazz, @NonNull AbsDanmakuItemProvider<T> binder, @NonNull Linker<T> linker) {
        typePool.register(clazz, binder, linker);
    }

    @SuppressWarnings("unchecked")
    private void registerWithoutChecking(@NonNull Class clazz, @NonNull AbsDanmakuItemProvider provider, @NonNull Linker linker) {
        checkAndRemoveAllTypesIfNeed(clazz);
        typePool.register(clazz, provider, linker);
    }

    @Override
    public int indexInTypesOf(@NonNull Object item) {
        int index = typePool.firstIndexOf(item.getClass());
        if (index != -1) {
            @SuppressWarnings("unchecked")
            Linker<Object> linker = (Linker<Object>) typePool.getLinkers().get(index);
            return index + linker.index(item);
        }
        throw new NullPointerException("you haven't registered the provider for  {className}.class in the TypePool ".replace("{className}", item.toString()));
    }

    private void checkAndRemoveAllTypesIfNeed(@NonNull Class<?> clazz) {
        if (!typePool.getClasses().contains(clazz)) {
            return;
        }
        Log.w(TAG, "You have registered the " + clazz.getSimpleName() + " type. " +
                "It will override the original binder(s).");
        while (true) {
            int index = typePool.getClasses().indexOf(clazz);
            if (index != -1) {
                typePool.getClasses().remove(index);
                typePool.getItemViewProviders().remove(index);
                typePool.getLinkers().remove(index);
            } else {
                break;
            }
        }
    }

    private void assureNecessaryParamsNotNull() {
        if (options.getCommonTextSize() == 0) {
            options.setCommonTextSize(context.getResources().getDimensionPixelSize(R.dimen.danmaku_common_text_size_12));
        }
        if (options.getCommonTextColor() == 0) {
            options.setCommonTextColor(ContextCompat.getColor(context, R.color.black));
        }
        if (options.getDuration() == 0) {
            options.setDuration(DanmakuConfig.DEFAULT_DELAY_DURATION);
        }
        if (options.getMaxRowNum() == 0) {
            options.setMaxRowNum(DanmakuConfig.DEFAULT_ROW_NUM);
        }
        if (options.getMaxShownNum() == 0) {
            options.setMaxShownNum(DanmakuConfig.DEFAULT_MAX_SHOWN_NUM);
        }
        if (Asserts.isEmpty(data)) {
            throw new IllegalStateException("you need setData() before prepare()");
        }
    }

    private void addChildLayoutParamsViaOrientation(@DanmakuOrientation.Orientation int orientation, RelativeLayout.LayoutParams lp) {
        switch (orientation) {
            case DIRECTION_LEFT_TO_RIGHT:
                lp.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
                break;
            case DIRECTION_RIGHT_TO_LEFT:
                lp.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
                break;
            case DIRECTION_TOP_TO_BOTTOM:
                lp.addRule(RelativeLayout.ALIGN_PARENT_TOP);
                break;
            case DIRECTION_BOTTOM_TO_TOP:
                lp.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
                break;
            default:
                lp.addRule(RelativeLayout.ALIGN_PARENT_TOP);
                break;
        }
    }


    private void initializeChildren() {
        for (int i = 0; i < data.size(); i++) {
            createChildView(i);
        }
    }

    void createChildView(int pos) {
        AbsDanmakuItemProvider provider = getProviderViaData(data.get(pos));
        // 创建 view
        View child =  provider.createView(LayoutInflater.from(context), null);
        // 数据填充 view
        provider.updateView(data.get(pos));
        RelativeLayout.LayoutParams lp = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        addChildLayoutParamsViaOrientation(options.getOrientation(), lp);
        lp.topMargin = random.nextInt(options.height);
        addView(child, lp);

        handler.sendSingleViewStartMessageInRandomDelay(pos, child);
    }

    @NonNull AbsDanmakuItemProvider getProviderViaData(Object obj) {
        int index = indexInTypesOf(obj);
        return typePool.getItemViewProviders().get(index);
    }

    ObjectAnimator getAnimatorViaData(Object obj, View child) {
        return getProviderViaData(obj).generateChildAnimator(child, this);
    }

    public void setData(@NonNull List<?> data) {
        this.data = data;
    }

    @NonNull
    public List<?> getData() {
        return data;
    }

    @NonNull
    public DanmakuConfig config() {
        return options;
    }

}
