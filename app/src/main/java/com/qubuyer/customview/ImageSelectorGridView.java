package com.qubuyer.customview;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.GridView;

/**
 * @author Susong
 * @date 创建时间:2019/4/19
 * @description 图片选择GridView
 * & @version
 */
public class ImageSelectorGridView extends GridView {

    public ImageSelectorGridView(Context context) {
        super(context);
    }

    public ImageSelectorGridView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ImageSelectorGridView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if (isInEditMode()) {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
            return;
        }
        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }
}
