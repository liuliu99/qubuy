package com.qubuyer.business.mine.view;


import com.qubuyer.base.mvp.BaseView;
import com.qubuyer.bean.mine.MessageEntity;

public interface IMessageDetailView extends BaseView {
    void onShowMessageDetailToView(String entitiy);
}
