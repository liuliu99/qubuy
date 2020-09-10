package com.qubuyer.business.home.activity;

import android.content.Context;
import android.os.Bundle;
import android.util.LongSparseArray;

import androidx.viewpager.widget.ViewPager;

import com.blankj.utilcode.util.TimeUtils;
import com.google.android.material.tabs.TabLayout;
import com.qubuyer.R;
import com.qubuyer.base.activity.BaseActivity;
import com.qubuyer.bean.home.HomeGoodEntity;
import com.qubuyer.business.home.adapter.LimitBuyPagerAdapter;
import com.qubuyer.business.home.presenter.LimitBuyPresenter;
import com.qubuyer.business.home.view.ILimitBuyView;
import com.qubuyer.business.home.view.LimitBuyViewPage;
import com.qubuyer.customview.LimitBuyTabLayout;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;


/**
 * @author Susong
 * @date 创建时间2019/3/20
 * @description 限时购activity
 */
public class LimitBuyActivity extends BaseActivity<LimitBuyPresenter> implements ILimitBuyView, ViewPager.OnPageChangeListener {
    @BindView(R.id.etl_tab)
    LimitBuyTabLayout etl_tab;
    @BindView(R.id.vp_page)
    ViewPager vp_page;

    private LimitBuyPagerAdapter mViewPagerAdapter;

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (null != mViewPagerAdapter && null != mViewPagerAdapter.getPageList() && !mViewPagerAdapter.getPageList().isEmpty()) {
            for (LimitBuyViewPage temp : mViewPagerAdapter.getPageList()) {
                temp.destory();
            }
        }
    }

    @Override
    protected int getContentView() {
        return R.layout.layout_activity_limit_buy;
    }

    @Override
    protected LimitBuyPresenter createP(Context context) {
        return new LimitBuyPresenter();
    }

    @Override
    protected void initWidget(Bundle savedInstanceState) {
        setTitle("限时购");
    }

    @Override
    protected void initData() {
        mPresenter.loadLimitBuyData();
    }

    private void initTabAndViewPager(long serverTime, List<HomeGoodEntity> list) {
        List<LimitBuyViewPage> pageList = new ArrayList<>();
        LimitBuyViewPage page;
        int selectedCategoryPosition = 0;
        LongSparseArray<HomeGoodEntity> longSparseArray = new LongSparseArray<>();
        for (int i = 0; i < list.size(); i++) {
            longSparseArray.put(list.get(i).getStart_time(), null);
        }
        for (int i = 0; i < longSparseArray.size(); i++) {
            long timeLong = longSparseArray.keyAt(i);
            String timeString = TimeUtils.millis2String(timeLong * 1000, new SimpleDateFormat("MM月dd"));
            String statusString;
            if (timeLong < serverTime) {
                statusString = "已开抢";
            } else if (timeLong > serverTime) {
                statusString = "即将开抢";
            } else {
                selectedCategoryPosition = i;
                statusString = "抢购中";
            }
            etl_tab.addTab(timeString, statusString);
            List<HomeGoodEntity> homeGoodEntities = new ArrayList<>();
            for (int j = 0; j < list.size(); j++) {
                if (list.get(j).getStart_time() == timeLong) {
                    homeGoodEntities.add(list.get(j));
                }
            }
            page = new LimitBuyViewPage(this,
                    homeGoodEntities,
                    !homeGoodEntities.isEmpty() ? homeGoodEntities.get(0).getStart_time() : 0,
                    !homeGoodEntities.isEmpty() ? homeGoodEntities.get(0).getEnd_time() : 0,
                    serverTime);
            page.getView();
            pageList.add(page);
        }
        mViewPagerAdapter = new LimitBuyPagerAdapter(this, pageList);
        vp_page.setAdapter(mViewPagerAdapter);
        vp_page.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(etl_tab.getTabLayout()));
        vp_page.addOnPageChangeListener(this);
        etl_tab.setupWithViewPager(vp_page);
        etl_tab.getTabLayout().getTabAt(selectedCategoryPosition).select();
        onPageSelected(selectedCategoryPosition);
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
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void onShowLimitBuyDataToView(long serverTime, List<HomeGoodEntity> list) {
        if (null != list && !list.isEmpty()) {
            initTabAndViewPager(serverTime * 1000, list);
        }
    }
}
