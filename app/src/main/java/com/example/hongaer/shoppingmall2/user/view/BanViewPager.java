package com.example.hongaer.shppingmall2.app;

import android.support.v4.view.ViewPager;
import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by hongaer on 2017/7/25.
 */

public class BanViewPager extends ViewPager {

    private boolean isCanScroll = true;
    public BanViewPager(Context context) {
        super(context);
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
    }
    public void scrollTo(int x, int y) {

        super.scrollTo(x, y);

    }
    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if (isCanScroll){

            return false;

        }else{

            return super.onInterceptTouchEvent(ev);

        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if (isCanScroll){

            return false;

        }else{

            return super.onTouchEvent(ev);

        }
    }

    public BanViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
}
