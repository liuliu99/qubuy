package com.qubuyer.business.order.model;

import com.qubuyer.bean.order.OrderCommentSubmitParamEntity;
import com.qubuyer.business.order.presenter.IOrderCommentListPresenter;
import com.qubuyer.constant.NetConstant;
import com.qubyer.okhttputil.callback.HttpCallback;
import com.qubyer.okhttputil.helper.HttpInvoker;
import com.qubyer.okhttputil.helper.ServerResponse;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OrderCommentListModel implements IOrderCommentListModel {
    private IOrderCommentListPresenter mPresenter;

    public OrderCommentListModel(IOrderCommentListPresenter mPresenter) {
        this.mPresenter = mPresenter;
    }

    @Override
    public void destroy() {
        mPresenter = null;
    }

    @Override
    public void submitComment(String orderId, List<OrderCommentSubmitParamEntity> goodList) {
        Map<String, File> fileParams = null;
        Map<String, String> params = new HashMap<>();
        params.put("id", orderId);
        if (null != goodList && !goodList.isEmpty()) {
            for (int i = 0; i < goodList.size(); i++) {
                OrderCommentSubmitParamEntity bean = goodList.get(i);
                params.put("goods[" + i + "][rec_id]", bean.getRec_id());
                params.put("goods[" + i + "][rank]", bean.getRank());
                params.put("goods[" + i + "][anonymous]", bean.getAnonymous());
                params.put("goods[" + i + "][content]", bean.getContent());
                if (null != bean.getImages() && !bean.getImages().isEmpty()) {
                    fileParams = new HashMap<>();
                    for (int j = 0; j < bean.getImages().size(); j++) {
                        String pic = bean.getImages().get(j);
                        fileParams.put("goods[" + i + "][imgs][" + j + "]", new File(pic));
                    }
                }
            }
        }
        if (null == fileParams || fileParams.isEmpty()) {
            HttpInvoker.createBuilder(NetConstant.ORDER_SUBMIT_COMMENT_URL)
                    .setParams(params)
                    .setMethodType(HttpInvoker.HTTP_REQUEST_METHOD_POST)
                    .setClz(String.class)
                    .build().sendAsyncHttpRequest(new HttpCallback() {
                @Override
                public void onHttpFinish(ServerResponse serverResponse, Map<String, String> requestParams, String requestUrl) {
                    if (null == mPresenter) return;
                    mPresenter.onSubmitComment(serverResponse);
                }
            });
        } else {
            HttpInvoker.createBuilder(NetConstant.ORDER_SUBMIT_COMMENT_URL)
                    .setParams(params)
                    .setFileParams(fileParams)
                    .setMethodType(HttpInvoker.HTTP_REQUEST_METHOD_FILEUP)
                    .setClz(String.class)
                    .build().sendAsyncHttpRequest(new HttpCallback() {
                @Override
                public void onHttpFinish(ServerResponse serverResponse, Map<String, String> requestParams, String requestUrl) {
                    if (null == mPresenter) return;
                    mPresenter.onSubmitComment(serverResponse);
                }
            });
        }
    }
}
