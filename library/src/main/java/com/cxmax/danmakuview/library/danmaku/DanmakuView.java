package com.cxmax.danmakuview.library.danmaku;

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
import com.cxmax.danmakuview.library.danmaku.param.AnimOption;
import com.cxmax.danmakuview.library.danmaku.param.DanmakuOptions;
import com.cxmax.danmakuview.library.danmaku.param.DanmakuOrientation;
import com.cxmax.danmakuview.library.danmaku.typepool.IAdapter;
import com.cxmax.danmakuview.library.danmaku.typepool.ITypePool;
import com.cxmax.danmakuview.library.danmaku.typepool.TypePoolImpl;
import com.cxmax.danmakuview.library.danmaku.typepool.linker.DefaultLinker;
import com.cxmax.danmakuview.library.danmaku.typepool.linker.Linker;
import com.cxmax.danmakuview.library.danmaku.typepool.linker.OneToManyBuilder;
import com.cxmax.danmakuview.library.danmaku.typepool.linker.OneToManyFlow;
import com.cxmax.danmakuview.library.utils.DevicesUtil;

import java.util.Collections;
import java.util.List;
import java.util.Random;

import static com.cxmax.danmakuview.library.danmaku.param.DanmakuOptions.DEFAULT_MAX_SPEED;
import static com.cxmax.danmakuview.library.danmaku.param.DanmakuOptions.DEFAULT_MIN_SPEED;

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
    @NonNull private final DanmakuOptions options;
    @NonNull private Context context;
    @NonNull private DanmakuHandler handler;
    @NonNull LayoutInflater inflater;
    @NonNull private Random random;

    {
        data = Collections.emptyList();
        typePool = new TypePoolImpl();
        options = DanmakuOptions.create();
        handler = DanmakuHandler.create(this);
        random = new Random(System.currentTimeMillis());
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
        inflater = LayoutInflater.from(context);
        initializeCustomAttrs(context, attrs);
    }

    private void initializeCustomAttrs(Context context, @Nullable AttributeSet attrs) {
        if (attrs != null) {
            TypedArray attr = context.obtainStyledAttributes(attrs, R.styleable.DanmakuView, 0, 0);
            if (attr != null) {
                options.setCommonTextSize(attr.getDimensionPixelSize(R.styleable.DanmakuView_common_text_size, context.getResources().getDimensionPixelSize(R.dimen.danmaku_common_text_size_12)));
                options.setCommonTextColor(attr.getColor(R.styleable.DanmakuView_common_text_color, ContextCompat.getColor(context, R.color.black)));
                options.setCommonTextClickable(attr.getBoolean(R.styleable.DanmakuView_common_text_clickable, Boolean.FALSE));
                options.setDuration(attr.getInteger(R.styleable.DanmakuView_item_duration, DanmakuOptions.DEFAULT_DELAY_DURATION));
                options.setMaxRowNum(attr.getInteger(R.styleable.DanmakuView_max_row_num, DanmakuOptions.DEFAULT_ROW_NUM));
                options.setMaxShownNum(attr.getInteger(R.styleable.DanmakuView_max_children_num, DanmakuOptions.DEFAULT_MAX_SHOWN_NUM));
                options.setOrientation(attr.getInteger(R.styleable.DanmakuView_common_orientation, DanmakuOrientation.DIRECTION_RIGHT_TO_LEFT));
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
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        handler.release();
    }

    // ------------- 弹幕控制调用函数 -------------------

    public void prepare(@NonNull DanmakuOptions options) {
        assureNecessaryParamsNotNull(options);
        removeAllViews();
    }

    @Override
    public void prepare() {
        prepare(options);
    }

    @Override
    public void start() {
        handler.sendEmptyMessage(DanmakuHandler.ANIM_START);
    }

    @Override
    public void stop() {
        handler.removeMessages(DanmakuHandler.ANIM_START);
        handler.sendEmptyMessage(DanmakuHandler.ANIM_STOP);
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
        setVisibility(VISIBLE);
    }

    @Override
    public void hide() {
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
    public int indexInTypesOf(@NonNull Object item){
        int index = typePool.firstIndexOf(item.getClass());
        if (index != -1) {
            @SuppressWarnings("unchecked")
            Linker<Object> linker = (Linker<Object>) typePool.getLinkers().get(index);
            return index + linker.index(item);
        }
        throw new NullPointerException("you haven't registered the provider for  {className}.class in the TypePool ".replace("{className}", item.toString()));
    }

    @SuppressWarnings("unchecked")
    AbsDanmakuItemProvider generateChildView() {
        int pos = (int) (Math.random() * data.size());
        int index = indexInTypesOf(data.get(pos));
        AbsDanmakuItemProvider provider = typePool.getItemViewProviders().get(index);
        // 创建 view
        View child = provider.createView(inflater, null);
        // 数据填充 view
        provider.updateView(data.get(pos));
        LayoutParams params = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        params.addRule(RelativeLayout.ALIGN_PARENT_TOP);
        params.topMargin = random.nextInt(options.getMaxRowNum()) * provider.onMeasureHeight(data.get(pos));
        AnimOption animOption = new AnimOption();
        int start = this.getRight() - this.getLeft() - this.getPaddingLeft();
        animOption.viewMeasuredWidth = provider.onMeasureWidth(data.get(pos));
        Log.e(TAG, "generateChildView: " + provider.onMeasureWidth(data.get(pos)) );
        animOption.startPos = start;
        animOption.speed = (int) (DEFAULT_MIN_SPEED + (DEFAULT_MAX_SPEED - DEFAULT_MIN_SPEED) * Math.random());
        provider.setAnimOption(animOption);
        provider.setView(child);
        addView(child, params);
        return provider;
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

    private void assureNecessaryParamsNotNull(@NonNull DanmakuOptions options) {
        if (options.getCommonTextSize() == 0) {
            options.setCommonTextSize(context.getResources().getDimensionPixelSize(R.dimen.danmaku_common_text_size_12));
        }
        if (options.getCommonTextColor() == 0) {
            options.setCommonTextColor(ContextCompat.getColor(context, R.color.black));
        }
        if (options.getDuration() == 0) {
            options.setDuration(DanmakuOptions.DEFAULT_DELAY_DURATION);
        }
        if (options.getMaxRowNum() == 0) {
            options.setMaxRowNum(DanmakuOptions.DEFAULT_ROW_NUM);
        }
        if (options.getMaxShownNum() == 0) {
            options.setMaxShownNum(DanmakuOptions.DEFAULT_MAX_SHOWN_NUM);
        }
        if (options.getOrientation() == 0) {
            options.setOrientation(DanmakuOrientation.DIRECTION_RIGHT_TO_LEFT);
        }
    }

    public void setData(@NonNull List<?> data) {
        this.data = data;
    }

    @NonNull public DanmakuOptions Options() {
        return options;
    }
}
