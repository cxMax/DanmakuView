package com.cxmax.danmakuview;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.cxmax.danmakuview.bean.Image;
import com.cxmax.danmakuview.bean.Text;
import com.cxmax.danmakuview.itemview.DanmakuImageItemProvider;
import com.cxmax.danmakuview.itemview.DanmakuTextItemProvider;
import com.cxmax.danmakuview.library.danmaku.DanmakuView;
import com.cxmax.danmakuview.library.danmaku.param.DanmakuConfig;
import com.cxmax.danmakuview.library.danmaku.param.DanmakuOrientation;
import com.cxmax.danmakuview.library.danmaku.typepool.util.TypePoolAsserts;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private DanmakuView danmakuView;
    private List<String> contents;

    {
        contents = Arrays.asList("搜狗", "百度",
                "腾讯", "360",
                "阿里巴巴", "搜狐",
                "网易", "新浪",
                "搜狗-上网从搜狗开始", "百度一下,你就知道",
                "必应搜索-有求必应", "好搜-用好搜，特顺手",
                "Android-谷歌", "IOS-苹果",
                "Windows-微软", "Linux");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        danmakuView = (DanmakuView) findViewById(R.id.danmaku);
        initializePureTextDanmakuView();
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

    @NonNull
    private List<Text> initializeTexts() {
        List<Text> texts = new ArrayList<>();
        for (String s : contents) {
            texts.add(new Text(s));
        }
        return texts;
    }

    @NonNull
    private List<Image> initializeImages() {
        List<Image> images = new ArrayList<>();
        for (String s : contents) {
            Image image = new Image();
            image.setTitle(s);
            images.add(image);
        }
        return images;
    }



    private void initializePureTextDanmakuView() {

        danmakuView.register(Text.class, new DanmakuTextItemProvider());

        danmakuView.config()
                .setOrientation(DanmakuOrientation.DIRECTION_RIGHT_TO_LEFT)
                .setMaxRowNum(4)
                .setMaxShownNum(15)
                .setDuration(500);
        List<Text> data = initializeTexts();
        danmakuView.setData(data);
        danmakuView.prepare();
        TypePoolAsserts.assertAllRegistered(danmakuView, data);
    }

    private void initializeImageDanmakuView() {
        danmakuView.register(Image.class, new DanmakuImageItemProvider());

        danmakuView.config()
                .setOrientation(DanmakuOrientation.DIRECTION_RIGHT_TO_LEFT)
                .setMaxRowNum(4)
                .setMaxShownNum(15)
                .setDuration(500);
        List<Image> data = initializeImages();
        danmakuView.setData(data);
        danmakuView.prepare();
    }

    @Override
    protected void onDestroy() {
        if (danmakuView != null) {
            danmakuView.release();
        }
        super.onDestroy();
    }
}
