package com.qubuyer.base.mvp;

public abstract class WrapperPresenter<T extends BaseView> implements Presenter<T> {
    public T mView;

    public BaseModel mModel;

    @Override
    public void attachView(T view) {
        mView = view;
    }

    @Override
    public void detachView() {
        mView = null;
    }

    @Override
    public void attachModel(BaseModel model) {
        mModel = model;
    }

    @Override
    public void destoryModel() {
        mModel.destroy();
        mModel = null;
    }

    public boolean isViewNotNull() {
        return mView != null;
    }

    @Override
    public void destroy() {

    }
}
