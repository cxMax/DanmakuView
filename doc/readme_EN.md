# DanmakuView

## Introduction : 
android customize barrage view

## preview

## feature
1. initialize the barrage item view via xml file , realize the view's scroll by animator , it has a higher customization
2. simplify the register and deliver of the multi barrage item views

## usage
1. extends AbsDanmakuItemProvider, this aims at dealing with the reaction of the barrage item view ; eg :   
```
public class DanmakuTextItemProvider extends AbsDanmakuItemProvider<T>{

    @Override
    public int initializeLayoutRes() {
        // return layout resource of root view
        return null;
    }

    @Override
    public ObjectAnimator generateChildAnimator(View child, View parent) {
        // return animator
        return null;
    }

    @Override
    public void initView(@NonNull View root) {
        // initialize the basic widget of root view
    }

    @Override
    public void updateView(T t) {
        // update data
    }

}
```
2. initialize the creation of DanmakuView in an activity or a fragment;
```
    1. register multi type of the barrage item view
    /*
     * Text.class - set data
     * DanmakuTextItemProvider - AbsDanmakuItemProvider
    /*
    danmakuView.register(Text.class, new DanmakuTextItemProvider());
    
    2. set up the configuration of DanmakuView
    danmakuView.config()
    
    3. set data list
    danmakuView.setData()

    4. control the action of DanmakuView 
    danmakuView.prepare()
    danmakuView.start();
    danmakuView.stop();
    danmakuView.pause();
    danmakuView.resume();
    danmakuView.release();
    danmakuView.show();
    danmakuView.hide();
```


## License MIT
  Copyright (C) 2017 cxMax  
  Copyright (C) 2017 DanmakuView