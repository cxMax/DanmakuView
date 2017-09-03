package com.cxmax.danmakuview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.cxmax.danmakuview.library.widget.DanmakuView;

import java.util.ArrayList;
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
        danmakuView = (DanmakuView) findViewById(R.id.danmaku);
        danmakuView.addDanmakuViews(contents);
        findViewById(R.id.hello_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                danmakuView.start();
            }
        });
    }
}
