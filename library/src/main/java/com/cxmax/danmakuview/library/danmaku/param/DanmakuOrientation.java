package com.cxmax.danmakuview.library.danmaku.param;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * @describe :
 * @usage :
 * <p>
 * </p>
 * Created by caixi on 17-9-9.
 */

public class DanmakuOrientation {

    public static final int DIRECTION_LEFT_TO_RIGHT = 0x10; //16
    public static final int DIRECTION_RIGHT_TO_LEFT = 0x11; //17
    public static final int DIRECTION_TOP_TO_BOTTOM = 0x12; //18
    public static final int DIRECTION_BOTTOM_TO_TOP = 0x13; //19

    @IntDef(value = {DIRECTION_LEFT_TO_RIGHT, DIRECTION_RIGHT_TO_LEFT, DIRECTION_TOP_TO_BOTTOM, DIRECTION_BOTTOM_TO_TOP})
    @Retention(RetentionPolicy.SOURCE)
    @interface Orientation {
    }

}
