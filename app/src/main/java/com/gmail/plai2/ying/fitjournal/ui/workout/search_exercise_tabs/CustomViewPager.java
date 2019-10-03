package com.gmail.plai2.ying.fitjournal.ui.workout.search_exercise_tabs;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

import androidx.viewpager.widget.ViewPager;

public class CustomViewPager extends ViewPager {

    // Fields
    private Boolean mDisable = false;

    // Constructors
    public CustomViewPager(Context context) {
        super(context);
    }

    public CustomViewPager(Context context, AttributeSet attrs){
        super(context,attrs);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        return !mDisable && super.onInterceptTouchEvent(event);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        performClick();
        return !mDisable && super.onTouchEvent(event);
    }

    @Override
    public boolean performClick() {
        return !mDisable && super.performClick();
    }

    // Other methods
    // Enable/disable swiping
    public void disableSwipe(Boolean disable){
        mDisable = disable;
    }
}