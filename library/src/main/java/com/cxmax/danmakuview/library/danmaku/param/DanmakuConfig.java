package com.cxmax.danmakuview.library.danmaku.param;

/**
 * @describe : declare DanmakuView parameters
 * @usage :
 * <p>
 * </p>
 * Created by caixi on 17-9-9.
 */

public class DanmakuConfig {

    public static final int DEFAULT_MAX_SHOWN_NUM = 15;
    public static final int DEFAULT_ROW_NUM = 4;
    public static final int DEFAULT_DELAY_DURATION = 500;
    public static final int DEFAULT_MIN_DELAY_DURATION = 1000;
    public static final int DEFAULT_MAX_DELAY_DURATION = 2000;
    public static final int DEFAULT_MIN_SPEED = 6000; // ms
    public static final int DEFAULT_MAX_SPEED = 10000; // ms

    private int commonTextSize;
    private int commonTextColor;
    private boolean commonTextClickable;
    private int duration;
    private int maxRowNum;
    private int maxShownNum;

    @DanmakuOrientation.Orientation
    private int orientation = DanmakuOrientation.DIRECTION_LEFT_TO_RIGHT;
    private int speed;

    public int width; // 控件 宽
    public int height; // 控件 高

    public static DanmakuConfig create() {
        return new DanmakuConfig();
    }

    public DanmakuConfig() {
    }

    public DanmakuConfig setCommonTextSize(int commonTextSize) {
        this.commonTextSize = commonTextSize;
        return this;
    }

    public DanmakuConfig setCommonTextColor(int commonTextColor) {
        this.commonTextColor = commonTextColor;
        return this;
    }

    public DanmakuConfig setCommonTextClickable(boolean commonTextClickable) {
        this.commonTextClickable = commonTextClickable;
        return this;
    }

    public DanmakuConfig setDuration(int duration) {
        this.duration = duration;
        return this;
    }

    public DanmakuConfig setMaxRowNum(int maxRowNum) {
        this.maxRowNum = maxRowNum;
        return this;
    }

    public DanmakuConfig setMaxShownNum(int maxShownNum) {
        this.maxShownNum = maxShownNum;
        return this;
    }

    public DanmakuConfig setOrientation(@DanmakuOrientation.Orientation int orientation) {
        this.orientation = orientation;
        return this;
    }

    public DanmakuConfig setSpeed(int speed) {
        this.speed = speed;
        return this;
    }

    public int getCommonTextSize() {
        return commonTextSize;
    }

    public int getCommonTextColor() {
        return commonTextColor;
    }

    public boolean isCommonTextClickable() {
        return commonTextClickable;
    }

    public int getDuration() {
        return duration;
    }

    public int getMaxRowNum() {
        return maxRowNum;
    }

    public int getMaxShownNum() {
        return maxShownNum;
    }

    @DanmakuOrientation.Orientation public int getOrientation() {
        return orientation;
    }

    public int getSpeed() {
        return speed;
    }
}
