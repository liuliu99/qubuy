package com.qubuyer.business.home.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

import com.qubuyer.R;
import com.qubuyer.bean.home.HomeCategoryEntity;
import com.qubuyer.business.home.adapter.HomeCategoryAdapter;

import java.util.List;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * @date 创建时间:2019/2/21
 * @author Susong
 * @description 首页分类ViewPage
 & @version
 */
public class HomeCategoryViewPage {
    //当前上下文
    private Context mContext;
    private View mView;

    private RecyclerView rv_list;

    private List<HomeCategoryEntity> mData;

    private HomeCategoryAdapter mAdapter;

    public HomeCategoryViewPage(Context mContext, List<HomeCategoryEntity> data) {
        this.mContext = mContext;
        this.mData = data;
    }

    public View getView() {
        if (mView == null) {
            mView = LayoutInflater.from(mContext).inflate(R.layout.layout_fragment_home_category_page, null);
            rv_list = mView.findViewById(R.id.rv_list);
            rv_list.setLayoutManager(new GridLayoutManager(mContext, 5));
            mAdapter = new HomeCategoryAdapter(mContext, mData);
            rv_list.setAdapter(mAdapter);
        }
        return mView;
    }
}


