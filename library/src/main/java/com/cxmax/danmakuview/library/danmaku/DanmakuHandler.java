package com.cxmax.danmakuview.library.danmaku;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;

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

class DanmakuHandler extends Handler {

    static final int ANIM_START = 0x100; //256
    static final int ANIM_RESUME = 0x101; //257
    static final int ANIM_PAUSE = 0x102; //258
    static final int ANIM_STOP = 0x103; //259

    @NonNull private DanmakuView danmakuView;
    private Set<ObjectAnimator> animators;
    private Random random;

    {
        animators = new CopyOnWriteArraySet<>();
    }

    DanmakuHandler(@NonNull DanmakuView danmakuView, Random random) {
        this.danmakuView = danmakuView;
        this.random = random;
    }

    public static DanmakuHandler create(@NonNull DanmakuView danmakuView, @NonNull Random random) {
        return new DanmakuHandler(danmakuView, random);
    }

    @Override
    public void handleMessage(Message msg) {
        super.handleMessage(msg);
        switch (msg.what) {
            case ANIM_START:
                final int pos = msg.arg1;
                final Object obj = danmakuView.getData().get(pos);
                final View child = (View) msg.obj;
                final ObjectAnimator objAnim = danmakuView.getAnimatorViaData(obj, child);
                objAnim.start();

                objAnim.addListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {
                        if (child instanceof ViewGroup) {
                            ((ViewGroup)child).removeAllViews();
                        }
                        animators.remove(objAnim);
                        danmakuView.removeView(child);
                        int index = random.nextInt(100) % danmakuView.getData().size();
                        danmakuView.createChildView(index);
                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {

                    }

                    @Override
                    public void onAnimationRepeat(Animator animation) {

                    }
                });
                animators.add(objAnim);
                break;

            case ANIM_RESUME:
                for (Animator animator: animators) {
                    if (animator.isPaused()) {
                        animator.resume();
                    }
                }
                break;

            case ANIM_PAUSE:
                for (Animator animator: animators) {
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
        for (Animator animator : animators) {
            animator.cancel();
        }
        animators.clear();
        removeCallbacksAndMessages(null);
        System.gc();
    }

    /**
     *
     * @param pos 获取到data , 从而获取到ObjectAnimator
     * @param view 开始动画的View
     */
    void sendSingleViewStartMessageInRandomDelay(int pos, View view) {
        Message message = new Message();
        message.what = DanmakuHandler.ANIM_START;
        message.arg1 = pos;
        message.obj = view;
        sendMessageDelayed(message, 800);
    }


}
