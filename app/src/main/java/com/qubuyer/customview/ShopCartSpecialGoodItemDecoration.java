package com.qubuyer.customview;

import android.graphics.Rect;
import android.view.View;

import com.blankj.utilcode.util.ConvertUtils;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ShopCartSpecialGoodItemDecoration extends RecyclerView.ItemDecoration {
    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        if (parent.getChildAdapterPosition(view) > 0 && parent.getChildAdapterPosition(view) % 2 != 0) {
            outRect.set(0, ConvertUtils.dp2px(5), ConvertUtils.dp2px(2.5f), 0);
        } else if (parent.getChildAdapterPosition(view) > 0 && parent.getChildAdapterPosition(view) % 2 == 0) {
            outRect.set(ConvertUtils.dp2px(2.5f), ConvertUtils.dp2px(5), 0, 0);
        }
    }
}
