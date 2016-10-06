//package com.deloitte.leaseclassification;
//
//import android.content.Context;
//import android.os.CountDownTimer;
//import android.util.AttributeSet;
//import android.view.KeyEvent;
//import android.view.MotionEvent;
//import android.view.animation.AnticipateOvershootInterpolator;
//import android.view.animation.Interpolator;
//import android.widget.ScrollView;
//
//public class SmoothScrollView extends ScrollView {
//
//    private static final long SCROLL_DURATION = 1500; //milliseconds
//    //interpolator for scroller
//    private static final Interpolator INTERPOLATOR = new AnticipateOvershootInterpolator(1);
//
//    private SmoothScroller smoothScroller;
//
//
//    public SmoothScrollView(Context context) {
//        this(context, null, 0);
//    }
//
//
//    public SmoothScrollView(Context context, AttributeSet attrs) {
//        this(context, attrs, 0);
//    }
//
//
//    public SmoothScrollView(Context context, AttributeSet attrs, int defStyle) {
//        super(context, attrs, defStyle);
//        setSmoothScrollingEnabled(true);
//    }
//
//
//    @Override
//    public boolean onTouchEvent(MotionEvent ev) {
//        if (smoothScroller != null)//we are scrolling
//            return true;
//        else return super.onTouchEvent(ev);
//    }
//
//    @Override
//    public boolean executeKeyEvent(KeyEvent ev) {
//        if (smoothScroller != null)//we are scrolling
//            return true;
//        else return super.executeKeyEvent(ev);
//    }
//
//    @Override
//    public boolean onInterceptTouchEvent(MotionEvent ev) {
//        if (smoothScroller != null)//we are scrolling
//            return true;
//        else return super.onInterceptTouchEvent(ev);
//    }
//
//    public void smoothScrollTo(int scrollX, int scrollY) {
//        if (smoothScroller != null) {
//            smoothScroller.cancel();
//        }
//        int deltaY = scrollY - getScrollY();
//        int deltaX = scrollX - getScrollX();
//        smoothScroller = new SmoothScroller(SCROLL_DURATION, getScrollX(), getScrollY(), deltaX, deltaY);
//        smoothScroller.start();
//    }
//
//    private class SmoothScroller extends CountDownTimer {
//
//        private int fromX;
//        private int fromY;
//        private int deltaX;
//        private int deltaY;
//        private float scrollTime;
//
//        public SmoothScroller(long scrollTime, int fromX, int fromY, int deltaX, int deltaY) {
//            super(scrollTime, 1);
//            this.scrollTime = scrollTime;
//            this.fromX = fromX;
//            this.fromY = fromY;
//            this.deltaX = deltaX;
//            this.deltaY = deltaY;
//        }
//
//        @Override
//        public void onTick(long millisUntilFinished) {
//            float delta = (scrollTime - millisUntilFinished) / scrollTime;
//            delta = INTERPOLATOR.getInterpolation(delta);
//            int x = fromX + ((int) (delta * deltaX));
//            int y = fromY + ((int) (delta * deltaY));
//            smoothScrollTo(x, y);
//        }
//
//        @Override
//        public void onFinish() {
//            float delta = 1f;
//            int x = fromX + ((int) (delta * deltaX));
//            int y = fromY + ((int) (delta * deltaY));
//            smoothScroller = null;
//            scrollTo(x, y);
//        }
//    }
//}