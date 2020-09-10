package com.qubuyer.business.order.activity;

import android.content.Context;
import android.os.Bundle;

import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.qubuyer.R;
import com.qubuyer.base.activity.BaseActivity;
import com.qubuyer.bean.order.OrderEntity;
import com.qubuyer.business.category.presenter.SecondCategoryGoodPresenter;
import com.qubuyer.business.order.adapter.OrderListPagerAdapter;
import com.qubuyer.business.order.view.OrderListViewPage;
import com.qubuyer.constant.AppConstant;
import com.qubuyer.customview.EnhanceTabLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;


/**
 * @date 创建时间:2019/3/29
 * @author Susong
 * @description 订单列表activity
 & @version
 */
public class OrderListActivity extends BaseActivity implements ViewPager.OnPageChangeListener{
    @BindView(R.id.etl_tab)
    EnhanceTabLayout etl_tab;
    @BindView(R.id.vp_page)
    ViewPager vp_page;

    private OrderListPagerAdapter mViewPagerAdapter;

    private int mSelectedOrderType;

    @Override
    protected int getContentView() {
        return R.layout.layout_activity_order_list;
    }

    @Override
    protected SecondCategoryGoodPresenter createP(Context context) {
        return new SecondCategoryGoodPresenter();
    }

    @Override
    protected void initWidget(Bundle savedInstanceState) {
        if (null != getIntent() && null != getIntent().getSerializableExtra(AppConstant.INTENT_EXTRA_KEY)) {
            mSelectedOrderType = (int) getIntent().getSerializableExtra(AppConstant.INTENT_EXTRA_KEY);
        }
        setTitle("我的订单");
        initTabAndViewPager();
    }

    @Override
    protected void initData() {
    }

    private void initTabAndViewPager() {
        ArrayList<String> titleList = new ArrayList<>();
        titleList.add("全部订单");
        titleList.add("待付款");
        titleList.add("待发货");
        titleList.add("待收货");
        titleList.add("待评价");
        List<OrderListViewPage> pageList = new ArrayList<>();
        OrderListViewPage page1 = new OrderListViewPage(this, OrderEntity.ORDER_GROUP_STATUS_ALL);
        page1.getView();
        OrderListViewPage page2 = new OrderListViewPage(this, OrderEntity.ORDER_GROUP_STATUS_OBLIGATION);
        page2.getView();
        OrderListViewPage page3 = new OrderListViewPage(this, OrderEntity.ORDER_GROUP_STATUS_WAIT_SEND);
        page3.getView();
        OrderListViewPage page4 = new OrderListViewPage(this, OrderEntity.ORDER_GROUP_STATUS_WAIT_RECEIVING);
        page4.getView();
        OrderListViewPage page5 = new OrderListViewPage(this, OrderEntity.ORDER_GROUP_STATUS_TO_BE_COMMENT);
        page5.getView();
        pageList.add(page1);
        pageList.add(page2);
        pageList.add(page3);
        pageList.add(page4);
        pageList.add(page5);
        for (int i = 0; i < titleList.size(); i++) {
            etl_tab.addTab(titleList.get(i));
        }
        mViewPagerAdapter = new OrderListPagerAdapter(this, pageList);
        vp_page.setAdapter(mViewPagerAdapter);
        vp_page.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(etl_tab.getTabLayout()));
        vp_page.addOnPageChangeListener(this);
        etl_tab.setupWithViewPager(vp_page);
        switch (mSelectedOrderType) {
            case 0:
                etl_tab.getTabLayout().getTabAt(0).select();
                onPageSelected(0);
                break;
            case 1:
                etl_tab.getTabLayout().getTabAt(1).select();
                onPageSelected(1);
                break;
            case 2:
                etl_tab.getTabLayout().getTabAt(2).select();
                onPageSelected(2);
                break;
            case 3:
                etl_tab.getTabLayout().getTabAt(3).select();
                onPageSelected(3);
                break;
            case 4:
                etl_tab.getTabLayout().getTabAt(4).select();
                onPageSelected(4);
                break;
        }
        onPageSelected(0);
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
            for (OrderListViewPage temp : mViewPagerAdapter.getPageList()) {
                temp.destory();
            }
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        if (null != mViewPagerAdapter && !mViewPagerAdapter.getPageList().isEmpty()) {
            mViewPagerAdapter.getPageList().get(position).loadData();
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
