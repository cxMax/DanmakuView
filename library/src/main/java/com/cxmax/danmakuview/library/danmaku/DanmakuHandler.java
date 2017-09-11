package com.cxmax.danmakuview.library.danmaku;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.view.animation.LinearInterpolator;

import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * @describe :
 * @usage :
 * <p>
 * <p>
 * Created by cxmax on 2017/9/3.
 */

class  DanmakuHandler extends Handler {

    static final int ANIM_START = 0x100; //256
    static final int ANIM_RESUME = 0x101; //257
    static final int ANIM_PAUSE = 0x102; //258
    static final int ANIM_STOP = 0x103; //259

    @NonNull private DanmakuView danmakuView;
    private Set<ObjectAnimator> animators;

    {
        animators = new CopyOnWriteArraySet<>();
    }

    DanmakuHandler(@NonNull DanmakuView danmakuView) {
        this.danmakuView = danmakuView;
    }

    public static DanmakuHandler create(@NonNull DanmakuView danmakuView) {
        return new DanmakuHandler(danmakuView);
    }

    @Override
    public void handleMessage(Message msg) {
        super.handleMessage(msg);
        switch (msg.what) {
            case ANIM_START:
                List<View> children = danmakuView.getChildren();
                final int pos = new Random(System.currentTimeMillis()).nextInt(100) % children.size();
                final View child = children.get(pos);
                final ObjectAnimator objAnim = ObjectAnimator
                        .ofFloat(child,"translationX" , danmakuView.Options().width, -child.getWidth())
                        .setDuration(500);
                objAnim.setInterpolator(new LinearInterpolator());
                objAnim.start();

                objAnim.addListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {
                        danmakuView.removeView(child);
                        danmakuView.createChildrenView(pos, danmakuView.getData().get(pos), true);
                        animators.remove(animation);
                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {

                    }

                    @Override
                    public void onAnimationRepeat(Animator animation) {

                    }
                });
                animators.add(objAnim);
                sendNewStartMessageInRandomDelay();
                break;
            case ANIM_RESUME:
                for (ObjectAnimator animator: animators) {
                    if (animator.isPaused()) {
                        animator.resume();
                    }
                }
                sendNewStartMessageInRandomDelay();
                break;
            case ANIM_PAUSE:
                for (ObjectAnimator animator: animators) {
                    if (animator.isRunning()) {
                        animator.pause();
                    }
                }
                break;
            case ANIM_STOP:
                release();
                break;

        }
    }

    public void release() {
        for (ObjectAnimator animator : animators) {
            animator.cancel();
        }
        animators.clear();
        danmakuView.removeAllViews();
        removeCallbacksAndMessages(null);
    }

    private void sendNewStartMessageInRandomDelay() {
        this.sendEmptyMessageDelayed(ANIM_START, 800);
    }
}
