package com.qubuyer.business.mine.view;


import com.qubuyer.base.mvp.BaseView;
import com.qubuyer.bean.mine.MineCommentEntity;

import java.util.List;

public interface IMineCommentView extends BaseView {
    void onShowMineCommentListToView(List<MineCommentEntity> list);
    void destory();
}
