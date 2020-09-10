package com.qubuyer.business.mine.presenter;

import com.qubyer.okhttputil.helper.ServerResponse;

public interface IMineLotteryRecordPresenter {
    void getMineLotteryRecordList();
    void onGetMineLotteryRecordList(ServerResponse serverResponse);
    void getLottery(String id, String consignee, String mobile, String province, String city, String district, String address);
    void onGetLottery(ServerResponse serverResponse);
}
