package com.qubuyer.business.good.activity;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.qubuyer.R;
import com.qubuyer.base.activity.BaseActivity;
import com.qubuyer.base.listener.PauseOnScrollListener;
import com.qubuyer.bean.home.HomeGoodEntity;
import com.qubuyer.business.category.adapter.SecondCategoryGoodListAdapter;
import com.qubuyer.business.good.adapter.SearchGoodHistoryAdapter;
import com.qubuyer.business.good.operation.SearchGoodHistoryManager;
import com.qubuyer.business.good.presenter.SearchGoodPresenter;
import com.qubuyer.business.good.view.ISearchGoodView;
import com.qubuyer.business.home.adapter.HomeAdapter;
import com.qubuyer.customview.AbsToolbar;
import com.qubuyer.customview.GoodItemDecoration;
import com.qubuyer.customview.ImageViewAutoLoad;
import com.qubuyer.customview.OverSrollView;
import com.qubuyer.customview.SearchView;

import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author Susong
 * @date 创建时间:2019/1/14
 * @description 搜索商品Activity
 * & @version
 */
public class SearchGoodActivity extends BaseActivity<SearchGoodPresenter> implements ISearchGoodView {
    @BindView(R.id.sv_search_bar)
    SearchView sv_search_bar;

    @BindView(R.id.osv_container)
    OverSrollView osv_container;

    @BindView(R.id.ll_no_search_result)
    RelativeLayout ll_no_search_result;

    @BindView(R.id.ll_search_history)
    LinearLayout ll_search_history;
    @BindView(R.id.iv_delete_history)
    ImageViewAutoLoad iv_delete_history;
    @BindView(R.id.rv_search_history)
    RecyclerView rv_search_history;

    @BindView(R.id.rv_result)
    RecyclerView rv_result;

    private SearchGoodHistoryAdapter mSearchHistoryAdapter;
    private SecondCategoryGoodListAdapter mAdapterResult;

    //搜索关键字
    private String mKeyword;

    private GridLayoutManager mLayoutManager;

    @Override
    protected int getContentView() {
        return R.layout.layout_activity_search_good;
    }

    @Override
    protected SearchGoodPresenter createP(Context context) {
        return new SearchGoodPresenter();
    }

    @Override
    protected void initWidget(Bundle savedInstanceState) {
        ll_no_search_result.setVisibility(View.GONE);
        initSearchView();
        initSearchHistoryRecyclerView();
        initResultRecyclerView();
    }

    @Override
    public AbsToolbar toolbar() {
        return null;
    }

    @Override
    protected void initData() {
        mPresenter.loadAllData();
    }

    private void initSearchView() {
        sv_search_bar.setInputCursorVisible(false);
        sv_search_bar.setInputFocus(false);
        sv_search_bar.setInputHintText("搜一搜");
        sv_search_bar.setOnSearchViewActionListener(new SearchView.OnSearchViewActionListener() {
            @Override
            public void onAction(int action, String result) {
                switch (action) {
                    case SearchView.OnSearchViewActionListener.ACTION_BACK:
                        finish();
                        break;
                    case SearchView.OnSearchViewActionListener.ACTION_DONE:
                        if (!TextUtils.isEmpty(result)) {
                            mKeyword = result;
                            mPresenter.loadSearchResultFirstListByKey(result);
                        }
                        break;
                    case SearchView.OnSearchViewActionListener.ACTION_CLEAR_INPUT:
                        mKeyword = null;
                        osv_container.setVisibility(View.VISIBLE);
                        ll_no_search_result.setVisibility(View.GONE);
                        rv_result.setVisibility(View.GONE);

                        mPresenter.loadSearchHistoryInfo();
                        break;
                    case SearchView.OnSearchViewActionListener.ACTION_CANCEL:
                        mKeyword = null;
                        osv_container.setVisibility(View.VISIBLE);
                        ll_no_search_result.setVisibility(View.GONE);
                        rv_result.setVisibility(View.GONE);

                        mPresenter.loadSearchHistoryInfo();
                        break;
                }
            }
        });
    }

    private void initSearchHistoryRecyclerView() {
        rv_search_history.setLayoutManager(new LinearLayoutManager(this));
        mSearchHistoryAdapter = new SearchGoodHistoryAdapter(this, new SearchGoodHistoryAdapter.OnSearchGoodHistoryOperaListener() {
            @Override
            public void onSearchHistoryClickListener(String key) {
                if (!TextUtils.isEmpty(key)) {
                    mKeyword = key;
                    sv_search_bar.setInputContent(key);
                    mPresenter.loadSearchResultFirstListByKey(key);
                }
            }

            @Override
            public void onDeleteHistoryListener(HomeGoodEntity entity) {
                SearchGoodHistoryManager.getInstance().popCity(entity);
                mPresenter.loadSearchHistoryInfo();
            }

        });
        rv_search_history.setAdapter(mSearchHistoryAdapter);
    }

    private void initResultRecyclerView() {
        mAdapterResult = new SecondCategoryGoodListAdapter(this);
        rv_result.addItemDecoration(new GoodItemDecoration());
        rv_result.setLayoutManager(new GridLayoutManager(this, 2));
        rv_result.setAdapter(mAdapterResult);
        rv_result.addOnScrollListener(new PauseOnScrollListener(this));
        rv_result.setVisibility(View.GONE);
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
        SearchGoodHistoryManager.getInstance().saveAllToDisk();
    }

    @Override
    public void onShowSearchHistoryToView(List<HomeGoodEntity> list) {
        if (null != list && !list.isEmpty()) {
            ll_search_history.setVisibility(View.VISIBLE);
            mSearchHistoryAdapter.setData(list);
        } else {
            mSearchHistoryAdapter.setData(new ArrayList<>());
            ll_search_history.setVisibility(View.GONE);
        }
    }

    @Override
    public void onShowSearchResultFirstListToView(List<HomeGoodEntity> list) {
        if (null != list && !list.isEmpty()) {
            osv_container.setVisibility(View.GONE);
            rv_result.setVisibility(View.VISIBLE);
            mAdapterResult.setData(list);
        } else {
            osv_container.setVisibility(View.VISIBLE);
            ll_no_search_result.setVisibility(View.VISIBLE);
            rv_result.setVisibility(View.GONE);
            mAdapterResult.setData(new ArrayList<>());
//            mPresenter.loadSearchHistoryInfo();
        }
    }

    @OnClick({R.id.iv_delete_history})
    public void onClickByButterKnfie(View v) {
        switch (v.getId()) {
            case R.id.iv_delete_history:
                mPresenter.deleteAllSearchHistory();
                break;
        }
    }
}
