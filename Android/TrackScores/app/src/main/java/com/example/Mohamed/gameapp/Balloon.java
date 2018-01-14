package com.example.Mohamed.gameapp;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;

import com.example.Mohamed.gameapp.utils.PixelHelper;

public class Balloon extends ImageView implements Animator.AnimatorListener, ValueAnimator.AnimatorUpdateListener {

    private ValueAnimator mAnimator;
    private BalloonListener mListener;
    private boolean mPopped;

    public Balloon(Context context) {
        super(context);
    }

    public Balloon(Context context, int color, int rawHeight) {
        super(context);

        mListener = (BalloonListener) context;

        this.setImageResource(R.drawable.balloon);
        this.setColorFilter(color);

        int rawWidth = rawHeight / 2; // fordi billedet er dobblet så højt som bredt

        int dpHeight = PixelHelper.pixelsToDp(rawHeight, context);
        int dpWidth = PixelHelper.pixelsToDp(rawWidth, context);
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(dpWidth, dpHeight);
        setLayoutParams(params);

    }

    public void releaseBalloon(int screenHeight, int duration) { // får ballonen til at stige opad
        mAnimator = new ValueAnimator();
        mAnimator.setDuration(duration);
        mAnimator.setFloatValues(screenHeight, 0f); //start to end, screenheight start end at 0
        mAnimator.setInterpolator(new LinearInterpolator());
        mAnimator.setTarget(this);
        mAnimator.addListener(this); // imlement methods AnimatorListener
        mAnimator.addUpdateListener(this); //implement methods Value.Animator
        mAnimator.start();
    }


    @Override
    public void onAnimationStart(Animator animator) {

    }

    @Override
    public void onAnimationEnd(Animator animator) {
        if (!mPopped) {
            mListener.popBalloon(this, false);
        }
    }

    @Override
    public void onAnimationCancel(Animator animator) {

    }

    @Override
    public void onAnimationRepeat(Animator animator) {

    }

    @Override // fra ValueAnimator.AnimatorUpdateListener, resten er fra Animator.AnimatorLisentner
    public void onAnimationUpdate(ValueAnimator valueAnimator) { //den gør animation klar til afyring tror jeg fra video
        setY((float) valueAnimator.getAnimatedValue());// sidste video i chap 3 lynda
    }

    @Override                                        // fra ImageVviewk-Class
    public boolean onTouchEvent(MotionEvent event) { // hva sker der når man røre
        if (!mPopped && event.getAction() == MotionEvent.ACTION_DOWN) {
            mListener.popBalloon(this, true); // //køre popballon
            mPopped = true;
            mAnimator.cancel();
        }
        return super.onTouchEvent(event);
    }

    public void setPopped(boolean popped) {
        mPopped = popped;
        if (popped) {
            mAnimator.cancel();
        }
    }

    public interface BalloonListener {

        void popBalloon(Balloon balloon, boolean userTouch);
    }
}
