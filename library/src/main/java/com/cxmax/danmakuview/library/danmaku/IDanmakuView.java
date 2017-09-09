package com.cxmax.danmakuview.library.danmaku;

import android.support.annotation.NonNull;

/**
 * @describe :
 * @usage :
 * <p>
 * </p>
 * Created by caixi on 17-9-9.
 */

public interface IDanmakuView {

    void prepare();

    void start();

    void stop();

    void pause();

    void resume();

    void release();

    void show();

    void hide();
}
