package com.qubuyer.customview;

import android.graphics.Rect;
import android.view.View;

import com.blankj.utilcode.util.ConvertUtils;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class GoodCommentItemDecoration extends  RecyclerView.ItemDecoration {
    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        outRect.set(0, 0, ConvertUtils.dp2px(8), 0);
    }
}
