package com.qubuyer.business.category.view;


import com.qubuyer.base.mvp.BaseView;
import com.qubuyer.bean.category.CategoryFirstEntity;

import java.util.List;

public interface ICategoryView extends BaseView {
    void onShowFirstCategoryToView(List<CategoryFirstEntity> list);
}
