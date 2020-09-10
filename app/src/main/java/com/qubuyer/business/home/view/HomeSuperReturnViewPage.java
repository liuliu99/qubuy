package com.qubuyer.business.home.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.qubuyer.R;
import com.qubuyer.bean.home.HomeGoodEntity;
import com.qubuyer.business.home.adapter.HomeSuperReturnAdapter;

import java.util.List;

/**
 * @date 创建时间:2019/6/4
 * @author Susong
 * @description 首页超级返ViewPage
 & @version
 */
public class HomeSuperReturnViewPage {
    //当前上下文
    private Context mContext;
    private View mView;

    private RecyclerView rv_list;

    private List<HomeGoodEntity> mData;

    private HomeSuperReturnAdapter mAdapter;

    public HomeSuperReturnViewPage(Context mContext, List<HomeGoodEntity> data) {
        this.mContext = mContext;
        this.mData = data;
    }

    public View getView() {
        if (mView == null) {
            mView = LayoutInflater.from(mContext).inflate(R.layout.layout_fragment_home_category_page, null);
            rv_list = mView.findViewById(R.id.rv_list);
            rv_list.setLayoutManager(new GridLayoutManager(mContext, 3));
            mAdapter = new HomeSuperReturnAdapter(mContext, mData);
            rv_list.setAdapter(mAdapter);
        }
        return mView;
    }
}


