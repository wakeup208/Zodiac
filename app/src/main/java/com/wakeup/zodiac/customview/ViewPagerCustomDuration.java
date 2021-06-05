package com.wakeup.zodiac.customview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.animation.Interpolator;

import androidx.viewpager.widget.ViewPager;

import java.lang.reflect.Field;

public class ViewPagerCustomDuration extends ViewPager {
    public ViewPagerCustomDuration(Context context) {
        super(context);
        scrollInitViewPager();
    }

    public ViewPagerCustomDuration(Context context, AttributeSet attrs) {
        super(context, attrs);
        scrollInitViewPager();
    }

    private ScrollerCustomDuration mScroller = null;

    private void scrollInitViewPager() {
        try {
            Field scroller = ViewPager.class.getDeclaredField("mScroller");
            scroller.setAccessible(true);
            Field interpolator = ViewPager.class.getDeclaredField("sInterpolator");
            interpolator.setAccessible(true);
            mScroller = new ScrollerCustomDuration(getContext(), (Interpolator) interpolator.get(null));
            scroller.set(this, mScroller);
        } catch (Exception e) {
        }
    }
}
