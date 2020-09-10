package com.qubuyer.customview;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ScrollView;

import androidx.core.view.ViewCompat;

/**
 * @date 创建时间:2018/12/20
 * @author Susong
 * @description 过度上拉和下拉，具有回弹效果的ScrollView
 & @version
 */
public class OverSrollView extends ScrollView {
    private int transY = 0;
    private ViewGroup firstChild;
    private boolean scrollable = true; //是否可以滚动
    private boolean shouldInterceptTouchEvent = true;
    private boolean isVerticalTouch = true;

    private ScrollViewChangeListener mScrollViewChangeListener;

    public OverSrollView(Context context) {
        super(context);
        init();
    }

    public OverSrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public OverSrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public OverSrollView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    private void init() {
        final ViewTreeObserver viewTreeObserver = getViewTreeObserver();
        viewTreeObserver.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                if (Build.VERSION.SDK_INT <= 19) {
                    OverSrollView.this.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                } else {
                    OverSrollView.this.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                }
                firstChild = (ViewGroup) getChildAt(0);
                if (firstChild == null) {
                    throw new IllegalStateException("OverScrollView至少有一个子View");
                }
            }
        });
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        if (null != mScrollViewChangeListener) {
            mScrollViewChangeListener.onScrollChanged(this, l, t, oldl, oldt);
        }
    }

    @Override
    protected void onOverScrolled(int scrollX, int scrollY, boolean clampedX, boolean clampedY) {
        //        LogUtils.i(TAG, String.format("onOverScrolled : scrollX = %d ; scrollY = %d ; clampedX = %b ; clampedY = %b", scrollX, scrollY, clampedX, clampedY));
        super.onOverScrolled(scrollX, scrollY, clampedX, clampedY);
    }

    @Override
    protected boolean overScrollBy(int deltaX, int deltaY, int scrollX, int scrollY, int scrollRangeX, int scrollRangeY, int maxOverScrollX, int maxOverScrollY, boolean isTouchEvent) {
        //        LogUtils.i(TAG, String.format("overScrollBy : deltaY = %d ; scrollY = %d ; scrollRangeY = %d ; maxOverScrollY = %d ; isTouchEvent = %b ", deltaY, scrollY, scrollRangeY, maxOverScrollY, isTouchEvent));
        if (!scrollable || !isTouchEvent || Math.abs(deltaX) > Math.abs(deltaY)) {
            return super.overScrollBy(deltaX, deltaY, scrollX, scrollY, scrollRangeX, scrollRangeY, maxOverScrollX, maxOverScrollY, isTouchEvent);
        } else {
            if (Math.abs(transY) <= 300) {
                transY -= deltaY / 3;
                ViewCompat.setTranslationY(firstChild, transY);
            }
        }
        return super.overScrollBy(deltaX, deltaY, scrollX, scrollY, scrollRangeX, scrollRangeY, maxOverScrollX, maxOverScrollY, isTouchEvent);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        onTouchEvent(ev);
        if (!shouldInterceptTouchEvent) {
            return false;
        }
        if (!isVerticalTouch) {
            return false;
        }
        return super.onInterceptTouchEvent(ev);
    }

    public boolean isShouldInterceptTouchEvent() {
        return shouldInterceptTouchEvent;
    }

    public void setShouldInterceptTouchEvent(boolean shouldInterceptTouchEvent) {
        this.shouldInterceptTouchEvent = shouldInterceptTouchEvent;
    }

    public void setScrollable(boolean scrollable) {
        this.scrollable = scrollable;
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_UP:
                if (transY != 0) {
                    ViewCompat.animate(firstChild).translationY(0).start();
                    transY = 0;
                }
                isVerticalTouch = true;
                break;
        }
        return super.onTouchEvent(ev);
    }

    public void setScrollViewChangeListener(ScrollViewChangeListener mScrollViewChangeListener) {
        this.mScrollViewChangeListener = mScrollViewChangeListener;
    }

    public interface ScrollViewChangeListener {
        void onScrollChanged(ScrollView scrollView, int x, int y, int oldx, int oldy);
    }
}
