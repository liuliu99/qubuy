package com.qubuyer.business.good.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import com.qubuyer.business.good.view.GoodDetailBaseViewPage;
import com.qubuyer.business.login.view.LoginBaseViewPage;

import java.util.List;

import androidx.viewpager.widget.PagerAdapter;

/**
 * @author Susong
 * @date 创建时间2019/3/23
 * @description 商品详情pageradapter
 * @version
 */
public class GoodDetailPagerAdapter extends PagerAdapter {
    private Context mContext;
    private List<GoodDetailBaseViewPage> mPageList;

    public GoodDetailPagerAdapter(Context mContext, List<GoodDetailBaseViewPage> pageList) {
        this.mContext = mContext;
        this.mPageList = pageList;
    }

    public List<GoodDetailBaseViewPage> getPageList() {
        return mPageList;
    }

//    @Override
//    public CharSequence getPageTitle(int position) {
//        switch (position) {
//            case 0:
//                return "未使用";
//            case 1:
//                return "已使用";
//            case 2:
//                return "已过期";
//        }
//        return super.getPageTitle(position);
//    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View pageView = mPageList.get(position).getView();
        if (container.indexOfChild(pageView) != -1) {
            return pageView;
        }
        container.addView(pageView);
        return pageView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public int getCount() {
        return mPageList.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }
}
