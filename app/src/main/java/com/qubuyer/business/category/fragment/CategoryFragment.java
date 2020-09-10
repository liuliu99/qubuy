package com.qubuyer.business.category.fragment;

import android.view.View;

import com.blankj.utilcode.util.BarUtils;
import com.qubuyer.R;
import com.qubuyer.base.fragment.BaseFragment;
import com.qubuyer.bean.category.CategoryFirstEntity;
import com.qubuyer.bean.category.CategorySecondEntity;
import com.qubuyer.business.category.activity.SecondCategoryActivity;
import com.qubuyer.business.category.adapter.CategoryFirstAdapter;
import com.qubuyer.business.category.adapter.CategorySecondAdapter;
import com.qubuyer.business.category.adapter.CategorySecondPagerAdapter;
import com.qubuyer.business.category.presenter.CategoryPresenter;
import com.qubuyer.business.category.view.CategorySecondViewPage;
import com.qubuyer.business.category.view.ICategoryView;
import com.qubuyer.business.good.activity.SearchGoodActivity;
import com.qubuyer.customview.NoScrollViewPager;
import com.qubuyer.utils.NavigationUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.OnClick;

public class CategoryFragment extends BaseFragment<CategoryPresenter> implements ICategoryView, CategorySecondAdapter.OnSecondCategoryListener {
    @BindView(R.id.rv_first_category)
    RecyclerView rv_first_category;
    @BindView(R.id.vp_second_category)
    NoScrollViewPager vp_second_category;

    private CategoryFirstAdapter mAdapter;
    private CategorySecondPagerAdapter mSecondViewPagerAdapter;

    private CategoryFirstEntity categoryFirstEntity;

    @Override
    protected int getLayoutId() {
        return R.layout.layout_fragment_category;
    }

    @Override
    protected CategoryPresenter createP(Fragment context) {
        return new CategoryPresenter();
    }

    @Override
    protected void initWidget(View root) {
        isUpdateStatusBar = true;
        setStatusBarVisibility(true);
        setStatusBarColor(getResources().getColor(R.color.white));

        vp_second_category.setNoScroll(true);
        initRecyclerView();
        mPresenter.loadFirstCategory();
    }

    @Override
    public void onResume() {
        super.onResume();
        BarUtils.setStatusBarLightMode(getActivity(), true);
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        if (!hidden) {
            isUpdateStatusBar = true;
            BarUtils.setStatusBarLightMode(getActivity(), true);
        } else {
            isUpdateStatusBar = false;
        }
        super.onHiddenChanged(hidden);
    }

    @Override
    public void showLoading() {
        showLoadingDialog();
    }

    @Override
    public void hideLoading() {
        dissmissLoadingDialog();
    }

    private void initRecyclerView() {
        mAdapter = new CategoryFirstAdapter(mContext, rv_first_category, new CategoryFirstAdapter.OnCategoryFirstListener() {
            @Override
            public void onCategoryFirstClick(CategoryFirstEntity entity, int position) {
                categoryFirstEntity = entity;
                if (null == mSecondViewPagerAdapter) return;
                mSecondViewPagerAdapter.getPageList().get(position).getView();
                vp_second_category.setCurrentItem(position, false);
            }
        });
        rv_first_category.setLayoutManager(new LinearLayoutManager(mContext));
        rv_first_category.setAdapter(mAdapter);
    }

    private void initSecondViewPager(List<CategoryFirstEntity> list) {
        List<CategorySecondViewPage> pageList = new ArrayList<>();
        for (CategoryFirstEntity categoryFirstEntity : list) {
            CategorySecondViewPage page = new CategorySecondViewPage(mContext, categoryFirstEntity, categoryFirstEntity.getChilder(), this);
            page.getView();
            pageList.add(page);
        }
        mSecondViewPagerAdapter = new CategorySecondPagerAdapter(getActivity(), pageList);
        vp_second_category.setAdapter(mSecondViewPagerAdapter);
        vp_second_category.setCurrentItem(0);
    }

    @OnClick({R.id.ll_search})
    public void onClickByButterKnife(View v) {
        switch (v.getId()) {
            case R.id.ll_search: //搜索
                NavigationUtil.overlay(mContext, SearchGoodActivity.class);
                break;
        }
    }

    @Override
    public void onShowFirstCategoryToView(List<CategoryFirstEntity> list) {
        if (null != list && !list.isEmpty()) {
            list.get(0).setSelected(true);
            categoryFirstEntity = list.get(0);
        }
        mAdapter.setData(list);
        initSecondViewPager(list);
    }

    @Override
    public void onSecondCategoryClick(CategorySecondEntity entity) {
        HashMap map = new HashMap();
        map.put("first_category_entity", categoryFirstEntity);
        map.put("second_category_entity", entity);
        NavigationUtil.overlay(mContext, SecondCategoryActivity.class, map);
    }
}
