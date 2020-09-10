package com.qubuyer.business.order.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import com.qubuyer.business.order.view.OrderListViewPage;

import java.util.List;

import androidx.viewpager.widget.PagerAdapter;

/**
 * @date 创建时间:2019/2/22
 * @author Susong
 * @description 订单列表PageAdapter
 & @version
 */
public class OrderListPagerAdapter extends PagerAdapter {
    private Context mContext;
    private List<OrderListViewPage> mPageList;

    public OrderListPagerAdapter(Context mContext, List<OrderListViewPage> pageList) {
        this.mContext = mContext;
        this.mPageList = pageList;
    }

    public List<OrderListViewPage> getPageList() {
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
