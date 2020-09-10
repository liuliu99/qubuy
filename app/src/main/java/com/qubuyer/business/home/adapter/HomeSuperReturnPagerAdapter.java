package com.qubuyer.business.home.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import androidx.viewpager.widget.PagerAdapter;

import com.qubuyer.business.home.view.HomeSuperReturnViewPage;

import java.util.List;

/**
 * @date 创建时间:2019/6/4
 * @author Susong
 * @description 首页超级返PagerAdapter
 & @version
 */
public class HomeSuperReturnPagerAdapter extends PagerAdapter {
    private Context mContext;
    private List<HomeSuperReturnViewPage> mPageList;

    public HomeSuperReturnPagerAdapter(Context mContext, List<HomeSuperReturnViewPage> pageList) {
        this.mContext = mContext;
        this.mPageList = pageList;
    }

    public List<HomeSuperReturnViewPage> getPageList() {
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
