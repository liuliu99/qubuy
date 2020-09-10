package com.qubuyer.business.mine.activity;

import android.content.Context;
import android.os.Bundle;

import com.google.android.material.tabs.TabLayout;
import com.qubuyer.R;
import com.qubuyer.base.activity.BaseActivity;
import com.qubuyer.business.category.presenter.SecondCategoryGoodPresenter;
import com.qubuyer.business.mine.adapter.SaleAmountPagerAdapter;
import com.qubuyer.business.mine.view.SaleAmountViewPage;
import com.qubuyer.customview.EnhanceTabLayout;

import java.util.ArrayList;
import java.util.List;

import androidx.viewpager.widget.ViewPager;
import butterknife.BindView;


/**
 * @author Susong
 * @date 创建时间2019/4/3
 * @description 销售总额activity
 * @version
 */
public class SaleAmountActivity extends BaseActivity implements ViewPager.OnPageChangeListener{
    @BindView(R.id.etl_tab)
    EnhanceTabLayout etl_tab;
    @BindView(R.id.vp_page)
    ViewPager vp_page;

    private SaleAmountPagerAdapter mViewPagerAdapter;

    @Override
    protected int getContentView() {
        return R.layout.layout_activity_sale_amount;
    }

    @Override
    protected SecondCategoryGoodPresenter createP(Context context) {
        return new SecondCategoryGoodPresenter();
    }

    @Override
    protected void initWidget(Bundle savedInstanceState) {
        setTitle("销售总额");
        initTabAndViewPager();
    }

    @Override
    protected void initData() {
    }

    private void initTabAndViewPager() {
        ArrayList<String> titleList = new ArrayList<>();
        titleList.add("已结算");
        titleList.add("待结算");
        List<SaleAmountViewPage> pageList = new ArrayList<>();
        SaleAmountViewPage page2 = new SaleAmountViewPage(this, 1);
        page2.getView();
        SaleAmountViewPage page3 = new SaleAmountViewPage(this, 2);
        page3.getView();
        pageList.add(page2);
        pageList.add(page3);
        for (int i = 0; i < titleList.size(); i++) {
            etl_tab.addTab(titleList.get(i));
        }
        mViewPagerAdapter = new SaleAmountPagerAdapter(this, pageList);
        vp_page.setAdapter(mViewPagerAdapter);
        vp_page.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(etl_tab.getTabLayout()));
        vp_page.addOnPageChangeListener(this);
        etl_tab.setupWithViewPager(vp_page);
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
            for (SaleAmountViewPage temp : mViewPagerAdapter.getPageList()) {
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
