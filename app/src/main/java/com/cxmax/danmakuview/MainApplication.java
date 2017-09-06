package com.cxmax.danmakuview;

import android.app.Application;
import com.squareup.leakcanary.LeakCanary;
/**
 * @describe :
 * @usage :
 * <p>
 * </p>
 * Created by caixi on 17-9-6.
 */

public class MainApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        if (BuildConfig.DEBUG && true) {
            if (LeakCanary.isInAnalyzerProcess(this)) {
                // This process is dedicated to LeakCanary for heap analysis.
                // You should not init your app in this process.
                return;
            }
            LeakCanary.install(this);
        }
    }
}
