package com.cxmax.danmakuview.library.danmaku;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.animation.DecelerateInterpolator;

import com.cxmax.danmakuview.library.danmaku.model.AbsDanmakuItemProvider;

import java.util.HashSet;
import java.util.Set;

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

    {
        animators = new HashSet<>();
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
                final AbsDanmakuItemProvider child = danmakuView.generateChildView();
                //todo orientation
                @SuppressLint("ObjectAnimatorBinding")
                final ObjectAnimator objAnim =ObjectAnimator
                        //滑动位置是x方向滑动，从屏幕宽度+View的长度到左边0-View的长度
                        .ofFloat(child,"translationX" , child.getAnimOption().startPos, -child.getAnimOption().viewMeasuredWidth)
                        .setDuration(child.getAnimOption().speed);
                //设置移动的过程速度，开始快之后满
                objAnim.setInterpolator(new DecelerateInterpolator());
                //开始动画
                objAnim.start();

                objAnim.addListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {
                        child.getView().clearAnimation();
                        animators.remove(objAnim);
                        danmakuView.removeView(child.getView());
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
                for (ObjectAnimator animator: animators) {
                    animator.resume();
                }
                break;
            case ANIM_PAUSE:
                for (ObjectAnimator animator: animators) {
                    animator.pause();
                }
                break;
            case ANIM_STOP:
                for (ObjectAnimator animator: animators) {
                    animator.end();
                }
                break;

        }
    }

    public void release() {
        for (ObjectAnimator animator : animators) {
            animator.cancel();
        }
        removeCallbacksAndMessages(null);
    }
}
