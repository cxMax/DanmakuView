## 一些注意事项
1. 通过AbsDanmakuItemProvider去创建child的时候,避免过多的xml布局文件的层级, 最好1层, 否则会有卡顿问题. 例如 : 
```
/* copied from item_danmaku_text_view.xml */
<?xml version="1.0" encoding="utf-8"?>
<TextView xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    android:id="@+id/title"
    android:layout_width="wrap_content"
    android:layout_height="wrap_content"
    app:layout_constraintBottom_toBottomOf="parent"
    app:layout_constraintEnd_toEndOf="parent"
    app:layout_constraintStart_toStartOf="parent"
    app:layout_constraintTop_toTopOf="parent"
    tools:text="我是一条弹幕" />

```

2. AbsDanmakuItemProvider的updateView(T t)函数尽量避免做耗时的操作, 否则会有资源释放的问题;
