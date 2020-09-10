package com.qubuyer.business.mine.activity;

import android.content.Context;
import android.os.Bundle;

import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.qubuyer.R;
import com.qubuyer.base.activity.BaseActivity;
import com.qubuyer.base.mvp.WrapperPresenter;
import com.qubuyer.business.mine.adapter.IncomePagerAdapter;
import com.qubuyer.business.mine.view.IncomeViewPage;
import com.qubuyer.customview.EnhanceTabLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * @author Susong
 * @date 创建时间2019/6/23
 * @description 收支明细activity
 * @version
 */
public class IncomeActivity extends BaseActivity implements ViewPager.OnPageChangeListener{
    @BindView(R.id.etl_tab)
    EnhanceTabLayout etl_tab;
    @BindView(R.id.vp_page)
    ViewPager vp_page;

    private IncomePagerAdapter mViewPagerAdapter;

    @Override
    protected int getContentView() {
        return R.layout.layout_activity_income;
    }

    @Override
    protected WrapperPresenter createP(Context context) {
        return null;
    }

    @Override
    protected void initWidget(Bundle savedInstanceState) {
        setTitle("收支明细");
        initTabAndViewPager();
    }

    @Override
    protected void initData() {
    }

    private void initTabAndViewPager() {
        ArrayList<String> titleList = new ArrayList<>();
        titleList.add("全部");
        titleList.add("收入");
        titleList.add("提现");
        List<IncomeViewPage> pageList = new ArrayList<>();
        IncomeViewPage page1 = new IncomeViewPage(this, null);
        page1.getView();
        IncomeViewPage page2 = new IncomeViewPage(this, "income");
        page2.getView();
        IncomeViewPage page3 = new IncomeViewPage(this, "wa");
        page3.getView();
        pageList.add(page1);
        pageList.add(page2);
        pageList.add(page3);
        for (int i = 0; i < titleList.size(); i++) {
            etl_tab.addTab(titleList.get(i));
        }
        mViewPagerAdapter = new IncomePagerAdapter(this, pageList);
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
            for (IncomeViewPage temp : mViewPagerAdapter.getPageList()) {
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
