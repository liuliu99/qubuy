package com.qubuyer.business.mine.view;


import com.qubuyer.base.mvp.BaseRefreshView;
import com.qubuyer.bean.mine.MessageEntity;

import java.util.List;

public interface IMessageListPageView extends BaseRefreshView {
    void onShowRefreshListToView(List<MessageEntity> list);
    void onShowLoadMoreListToView(List<MessageEntity> list);
    void destory();
}
