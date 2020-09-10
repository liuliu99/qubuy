package com.qubuyer.business.category.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

import com.qubuyer.R;
import com.qubuyer.bean.category.CategoryFirstEntity;
import com.qubuyer.bean.category.CategorySecondEntity;
import com.qubuyer.business.category.adapter.CategorySecondAdapter;
import com.qubuyer.constant.NetConstant;
import com.qubuyer.customview.ImageViewAutoLoad;

import java.util.List;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * @author Susong
 * @date 创建时间:2019/2/21
 * @description 订单列表ViewPage
 * & @version
 */
public class CategorySecondViewPage {
    //当前上下文
    private Context mContext;
    private View mView;
    private ImageViewAutoLoad iv_top_img;
    private RecyclerView rv_list;
    private CategorySecondAdapter mAdapter;

    private CategoryFirstEntity mFirstCategory;
    private List<CategorySecondEntity> mData;

    private CategorySecondAdapter.OnSecondCategoryListener mListener;

    public CategorySecondViewPage(Context mContext, CategoryFirstEntity firstCategory, List<CategorySecondEntity> data, CategorySecondAdapter.OnSecondCategoryListener listener) {
        this.mContext = mContext;
        this.mFirstCategory = firstCategory;
        this.mData = data;
        this.mListener = listener;
    }

    public View getView() {
        if (mView == null) {
            mView = LayoutInflater.from(mContext).inflate(R.layout.layout_fragment_category_second_page, null);
            iv_top_img = mView.findViewById(R.id.iv_top_img);
            iv_top_img.setUri(mContext, mFirstCategory.getImage());
            rv_list = mView.findViewById(R.id.rv_list);
            initRecyclerView();
        }
        return mView;
    }

    private void initRecyclerView() {
        mAdapter = new CategorySecondAdapter(mContext, mData, mListener);
//        mRecyclerView.addItemDecoration(new DecorationLLM(mContext, DecorationLLM.VERTICAL_LIST, R.drawable.shape_recyclerview_divider, DensityUtil.dip2px(mContext, 10)));
        rv_list.setLayoutManager(new GridLayoutManager(mContext, 2));
        rv_list.setAdapter(mAdapter);
    }
}


