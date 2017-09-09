package com.cxmax.danmakuview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.cxmax.danmakuview.bean.Text;
import com.cxmax.danmakuview.itemview.DanmakuTextItemProvider;
import com.cxmax.danmakuview.library.danmaku.DanmakuView;
import com.cxmax.danmakuview.library.danmaku.param.DanmakuOptions;
import com.cxmax.danmakuview.library.danmaku.param.DanmakuOrientation;

import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private DanmakuView danmakuView;
    private List<String> contents;
    {
        contents = Arrays.asList("搜狗","百度",
                "腾讯","360",
                "阿里巴巴","搜狐",
                "网易","新浪",
                "搜狗-上网从搜狗开始","百度一下,你就知道",
                "必应搜索-有求必应","好搜-用好搜，特顺手",
                "Android-谷歌","IOS-苹果",
                "Windows-微软","Linux");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initializeDanmakuView();
        findViewById(R.id.start_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                danmakuView.start();
            }
        });
        findViewById(R.id.stop_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                danmakuView.stop();
            }
        });
        findViewById(R.id.resume_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                danmakuView.resume();
            }
        });
        findViewById(R.id.pause_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                danmakuView.pause();
            }
        });
    }

    private void initializeDanmakuView() {
        danmakuView = (DanmakuView) findViewById(R.id.danmaku);

        danmakuView.register(Text.class, new DanmakuTextItemProvider());

        DanmakuOptions options = danmakuView.Options();
        options.setOrientation(DanmakuOrientation.DIRECTION_RIGHT_TO_LEFT)
                .setMaxRowNum(4)
                .setMaxShownNum(15)
                .setDuration(500);
        danmakuView.prepare(options);
    }

    @Override
    protected void onDestroy() {
        if (danmakuView != null) {
            danmakuView.release();
        }
        super.onDestroy();
    }
}
