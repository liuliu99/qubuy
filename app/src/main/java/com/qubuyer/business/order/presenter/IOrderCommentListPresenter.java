package com.qubuyer.business.order.presenter;


import com.qubuyer.bean.order.OrderCommentSubmitParamEntity;
import com.qubyer.okhttputil.helper.ServerResponse;

import java.util.List;

public interface IOrderCommentListPresenter {
    void submitComment(String orderId, List<OrderCommentSubmitParamEntity> goodList);
    void onSubmitComment(ServerResponse serverResponse);
}
