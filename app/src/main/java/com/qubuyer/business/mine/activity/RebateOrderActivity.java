package com.qubuyer.business.mine.activity;

import android.content.Context;
import android.os.Bundle;

import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.qubuyer.R;
import com.qubuyer.base.activity.BaseActivity;
import com.qubuyer.business.category.presenter.SecondCategoryGoodPresenter;
import com.qubuyer.business.mine.adapter.RebateOrderPagerAdapter;
import com.qubuyer.business.mine.view.RebateOrderViewPage;
import com.qubuyer.customview.EnhanceTabLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;


/**
 * @author Susong
 * @date 创建时间2019/4/3
 * @description 折让订单activity
 * @version
 */
public class RebateOrderActivity extends BaseActivity implements ViewPager.OnPageChangeListener{
    @BindView(R.id.etl_tab)
    EnhanceTabLayout etl_tab;
    @BindView(R.id.vp_page)
    ViewPager vp_page;

    private RebateOrderPagerAdapter mViewPagerAdapter;
    private int mSelectedPosition;
    private boolean mIsRefreshFirst = true;

    @Override
    protected int getContentView() {
        return R.layout.layout_activity_rebate_order;
    }

    @Override
    protected SecondCategoryGoodPresenter createP(Context context) {
        return new SecondCategoryGoodPresenter();
    }

    @Override
    protected void initWidget(Bundle savedInstanceState) {
        setTitle("折让订单");
        initTabAndViewPager();
    }

    @Override
    protected void initData() {
    }

    private void initTabAndViewPager() {
        ArrayList<String> titleList = new ArrayList<>();
        titleList.add("待结算");
        titleList.add("已结算");
        List<RebateOrderViewPage> pageList = new ArrayList<>();
        RebateOrderViewPage page2 = new RebateOrderViewPage(this, 2);
        page2.getView();
        RebateOrderViewPage page3 = new RebateOrderViewPage(this, 1);
        page3.getView();
        pageList.add(page2);
        pageList.add(page3);
        for (int i = 0; i < titleList.size(); i++) {
            etl_tab.addTab(titleList.get(i));
        }
        mViewPagerAdapter = new RebateOrderPagerAdapter(this, pageList);
        vp_page.setAdapter(mViewPagerAdapter);
        vp_page.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(etl_tab.getTabLayout()));
        vp_page.addOnPageChangeListener(this);
        etl_tab.setupWithViewPager(vp_page);
        onPageSelected(0);
    }

    @Override
    public void onResume() {
        super.onResume();
        refreshData();
    }

    @Override
    public void showLoading() {
        showLoadingDialog();
    }

    @Override
    public void hideLoading() {
        dissmissLoadingDialog();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (null != mViewPagerAdapter && null != mViewPagerAdapter.getPageList() && mViewPagerAdapter.getPageList().size() > 0) {
            for (RebateOrderViewPage temp : mViewPagerAdapter.getPageList()) {
                temp.destory();
            }
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        mSelectedPosition = position;
        if (null != mViewPagerAdapter && !mViewPagerAdapter.getPageList().isEmpty()) {
            mViewPagerAdapter.getPageList().get(position).loadData();
        }
    }

    private void refreshData(){
        if (mIsRefreshFirst) {
            mIsRefreshFirst = false;
            return;
        }
        if (null != mViewPagerAdapter && !mViewPagerAdapter.getPageList().isEmpty()) {
            mViewPagerAdapter.getPageList().get(mSelectedPosition).autoRefreshData();
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
