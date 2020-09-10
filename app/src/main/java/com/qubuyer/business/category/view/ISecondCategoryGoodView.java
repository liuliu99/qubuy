package com.qubuyer.business.category.view;


import com.qubuyer.base.mvp.BaseRefreshView;
import com.qubuyer.bean.category.SecondCategoryGoodEntity;

public interface ISecondCategoryGoodView extends BaseRefreshView {
    void onShowRefreshDataToView(SecondCategoryGoodEntity entity);

    void onShowLoadMoreGoodDataToView(SecondCategoryGoodEntity entity);

    void destory();
}
