package com.cxmax.danmakuview.library.widget;

import android.animation.Animator;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewPropertyAnimator;
import android.view.animation.LinearInterpolator;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import static com.cxmax.danmakuview.library.widget.DanmakuView.DEFAULT_DELAY_DURATION;
import static com.cxmax.danmakuview.library.widget.DanmakuView.DIRECTION_RIGHT;

/**
 * @describe :
 * @usage :
 * <p>
 * <p>
 * Created by cxmax on 2017/9/3.
 */

class DanmakuHandler extends Handler {

    @NonNull
    private DanmakuView danmakuView;
    private Set<ViewPropertyAnimator> animators;

    {
        animators = new HashSet<>();
    }

    DanmakuHandler(@NonNull DanmakuView danmakuView) {
        this.danmakuView = danmakuView;
    }

    @Override
    public void handleMessage(Message msg) {
        super.handleMessage(msg);
        final int pos = msg.what;
        if (danmakuView.getChildren().size() <= pos) {
            return;
        }
        final View child = danmakuView.getChildren().get(pos);
        ViewPropertyAnimator animator = child.animate().translationXBy(
                danmakuView.getDirection() == DIRECTION_RIGHT ?
                        -(danmakuView.getViewWidth() + child.getWidth())
                        : danmakuView.getViewWidth() + child.getWidth());
        Random random = new Random(System.currentTimeMillis());
        int index = random.nextInt(100) % danmakuView.getSpeeds().length;
        animator.setDuration(danmakuView.getSpeeds()[index]);
        animator.setInterpolator(new LinearInterpolator());
        animator.setListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {

            }

            @Override
            public void onAnimationEnd(Animator animator) {
                // Random change of content
//                danmakuView.removeView(child);
//                int index = danmakuView.getRandom().nextInt(100) % danmakuView.getContents().size();
//                danmakuView.createChildView(pos, danmakuView.getContents().get(index), true);
                sendEmptyMessageDelayed(pos, DEFAULT_DELAY_DURATION);
                animators.remove(animator);
            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });
        animator.start();
        animators.add(animator);
    }

    public void cancel() {
        for (ViewPropertyAnimator animator : animators) {
            animator.cancel();
        }
        removeCallbacksAndMessages(null);
    }
}
