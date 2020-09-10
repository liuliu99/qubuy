package com.qubuyer.business.mine.view;


import com.qubuyer.base.mvp.BaseView;
import com.qubuyer.bean.mine.MineLotteryRecordEntity;

import java.util.List;

public interface IMineLotteryRecordView extends BaseView {
    void onShowMineLotteryRecordListToView(List<MineLotteryRecordEntity> list);
    void onShowMineGetLotteryResultToView(boolean success);
    void destory();
}
