package com.qubuyer.base.fragment;

import android.os.Bundle;

/**
 * @date 创建时间:2018/12/19
 * @author Susong
 * @description 针对add remove的延迟加载
 & @version
 */
public abstract class LazyBaseFragmentForAdd extends BaseFragment {
    private boolean isViewInitiated;
    private boolean isVisibleToUser;
    private boolean isDataInitiated;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        isViewInitiated = true;
        prepareFetchData();
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        this.isVisibleToUser = isVisibleToUser;
        prepareFetchData();
    }

    /**
     * 懒加载方法
     */
    protected abstract void fetchData();

    public boolean prepareFetchData() {
        return prepareFetchData(false);
    }

    public boolean prepareFetchData(boolean forceUpdate) {
        if (isVisibleToUser && isViewInitiated && (!isDataInitiated || forceUpdate)) {
            fetchData();
            isDataInitiated = true;
            return true;
        }
        return false;
    }
}
