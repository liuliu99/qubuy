package com.qubuyer.business.category.activity;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import com.google.android.material.tabs.TabLayout;
import com.qubuyer.R;
import com.qubuyer.base.activity.BaseActivity;
import com.qubuyer.bean.ToolbarMenuEntity;
import com.qubuyer.bean.category.CategoryFirstEntity;
import com.qubuyer.bean.category.CategorySecondEntity;
import com.qubuyer.bean.category.SecondCategoryGoodEntity;
import com.qubuyer.business.category.adapter.SecondCategoryGoodListPagerAdapter;
import com.qubuyer.business.category.presenter.SecondCategoryGoodPresenter;
import com.qubuyer.business.category.view.ISecondCategoryGoodView;
import com.qubuyer.business.category.view.SecondCategoryGoodListViewPage;
import com.qubuyer.business.good.activity.GoodDetailActivity;
import com.qubuyer.business.good.activity.SearchGoodActivity;
import com.qubuyer.constant.AppConstant;
import com.qubuyer.customview.AbsToolbar;
import com.qubuyer.customview.EnhanceTabLayout;
import com.qubuyer.customview.ImageViewAutoLoad;
import com.qubuyer.utils.NavigationUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import androidx.viewpager.widget.ViewPager;

import butterknife.BindView;


/**
 * @author Susong
 * @date 创建时间2019/3/20
 * @description 二级分类商品列表activity
 */
public class SecondCategoryActivity extends BaseActivity<SecondCategoryGoodPresenter> implements ISecondCategoryGoodView, ViewPager.OnPageChangeListener {
    @BindView(R.id.iv_banner)
    ImageViewAutoLoad iv_banner;
    @BindView(R.id.etl_tab)
    EnhanceTabLayout etl_tab;
    @BindView(R.id.vp_page)
    ViewPager vp_page;

    private SecondCategoryGoodListPagerAdapter mViewPagerAdapter;

    //一级分类名称
    private CategoryFirstEntity categoryFirstEntity;
    //二级分类实体
    private CategorySecondEntity mCategorySecondEntity;
    //一级分类项列表
    private List<CategorySecondEntity> mCategoryFristList;

    @Override
    protected int getContentView() {
        return R.layout.layout_activity_second_category;
    }

    @Override
    protected SecondCategoryGoodPresenter createP(Context context) {
        return new SecondCategoryGoodPresenter();
    }

    @Override
    protected void initWidget(Bundle savedInstanceState) {
        if (null != getIntent()) {
            HashMap map = (HashMap) getIntent().getSerializableExtra(AppConstant.INTENT_EXTRA_KEY);
            categoryFirstEntity = (CategoryFirstEntity) map.get("first_category_entity");
            mCategorySecondEntity = (CategorySecondEntity) map.get("second_category_entity");
        }
        setTitle(null != categoryFirstEntity ? categoryFirstEntity.getName() : "商品列表");
        initMenu();
    }

    private void initMenu() {
        List<ToolbarMenuEntity> toolbarMenuEntityList = new ArrayList<>();
        ToolbarMenuEntity toolbarMenuEntity = new ToolbarMenuEntity();
        toolbarMenuEntity.setDpWidth(20);
        toolbarMenuEntity.setDpHeight(20);
//        toolbarMenuEntity.setMenuContent("添加常用旅客");
        toolbarMenuEntity.setMenuDrawaleId(R.drawable.icon_home_search);
        toolbarMenuEntity.setOnMenuOnClickListener(new AbsToolbar.OnMenuOnClickListener() {
            @Override
            public void onMenuOnClick(View view) {
                NavigationUtil.overlay(SecondCategoryActivity.this, SearchGoodActivity.class);
            }
        });
        toolbarMenuEntityList.add(toolbarMenuEntity);
        inflateMenu(toolbarMenuEntityList);
    }

    @Override
    protected void initData() {
        mPresenter.refresh(null != categoryFirstEntity ? categoryFirstEntity.getId() + "" : "", null != mCategorySecondEntity ? mCategorySecondEntity.getId() + "" : null);
    }

    private void initBannerView(int position) {
        if (null != mCategoryFristList && !mCategoryFristList.isEmpty()) {
            CategorySecondEntity entity = mCategoryFristList.get(position);
            if (!TextUtils.isEmpty(entity.getBanner_image_full_path())) {
                iv_banner.setUri(this, entity.getBanner_image_full_path());
            } else {
                iv_banner.setDrawableId(this, R.drawable.placeholder);
            }
            if (null != entity.getBanner_image_goods_id() && 0 != entity.getBanner_image_goods_id()) {
                iv_banner.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        NavigationUtil.overlay(SecondCategoryActivity.this, GoodDetailActivity.class, entity.getBanner_image_goods_id());
                    }
                });
            }
        }
    }

    private void initTabAndViewPager(List<CategorySecondEntity> list) {
        ArrayList<String> titleList = new ArrayList<>();
        List<SecondCategoryGoodListViewPage> pageList = new ArrayList<>();
        SecondCategoryGoodListViewPage page;
        int selectedCategoryPosition = 0;
        for (int i = 0; i < list.size(); i++) {
            CategorySecondEntity entity = list.get(i);
            titleList.add(entity.getName());
            page = new SecondCategoryGoodListViewPage(this, null != categoryFirstEntity ? categoryFirstEntity.getId() + "" : "", entity.getId() + "");
            page.getView();
            pageList.add(page);
            if (entity.getSelected() == 1) {
                selectedCategoryPosition = i;
            }
        }
        for (int i = 0; i < titleList.size(); i++) {
            etl_tab.addTab(titleList.get(i));
        }
        if (titleList.size() > 5) {
            etl_tab.getTabLayout().setTabMode(TabLayout.MODE_SCROLLABLE);
        } else {
            etl_tab.getTabLayout().setTabMode(TabLayout.MODE_FIXED);
        }
        mViewPagerAdapter = new SecondCategoryGoodListPagerAdapter(this, pageList);
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
    protected void onDestroy() {
        super.onDestroy();
        if (null != mViewPagerAdapter && null != mViewPagerAdapter.getPageList() && mViewPagerAdapter.getPageList().size() > 0) {
            for (SecondCategoryGoodListViewPage temp : mViewPagerAdapter.getPageList()) {
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
            initBannerView(position);
            mViewPagerAdapter.getPageList().get(position).loadData();
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void onShowRefreshDataToView(SecondCategoryGoodEntity entity) {
        if (null != entity && null != entity.getCate()) {
            mCategoryFristList = entity.getCate();
//            initBannerView(0);
            initTabAndViewPager(entity.getCate());
        }
    }

    @Override
    public void onShowLoadMoreGoodDataToView(SecondCategoryGoodEntity entity) {

    }

    @Override
    public void destory() {

    }

    @Override
    public void finishLoadMore(int delay, boolean success, boolean noMoreData) {

    }

    @Override
    public void finishRefresh(boolean success) {

    }
}
