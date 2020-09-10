package com.qubuyer.customview;

import android.content.Context;
import android.util.AttributeSet;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

/**
 * 可设置最大高度的RecyclerView
 */
public class MaxHeightRecyclerView extends RecyclerView {
    //RecyclerView高度
    private int recyclerViewHeight = -1;

    public int getRecyclerViewHeight() {
        return recyclerViewHeight;
    }

    public void setRecyclerViewHeight(int recyclerViewHeight) {
        this.recyclerViewHeight = recyclerViewHeight;
    }

    public MaxHeightRecyclerView(Context context) {
        super(context);
    }

    public MaxHeightRecyclerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public MaxHeightRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        // TODO Auto-generated method stub
        if (recyclerViewHeight > -1) {
            heightMeasureSpec = MeasureSpec.makeMeasureSpec(recyclerViewHeight, MeasureSpec.AT_MOST);
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }
}
