package com.qubuyer.business.order.model;

import com.qubuyer.bean.order.OrderRefundEntity;
import com.qubuyer.bean.order.OrderRefundReasonEntity;
import com.qubuyer.business.order.presenter.IOrderRefundPresenter;
import com.qubuyer.constant.NetConstant;
import com.qubyer.okhttputil.callback.HttpCallback;
import com.qubyer.okhttputil.helper.HttpInvoker;
import com.qubyer.okhttputil.helper.ServerResponse;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OrderRefundModel implements IOrderRefundModel {
    private IOrderRefundPresenter mPresenter;
    private int pageNo = 1;

    public OrderRefundModel(IOrderRefundPresenter mPresenter) {
        this.mPresenter = mPresenter;
    }

    @Override
    public void destroy() {
        mPresenter = null;
    }

    @Override
    public void getRefundReasonList() {
        HttpInvoker.createBuilder(NetConstant.GET_ORDER_REFUND_REASON_URL)
                .setMethodType(HttpInvoker.HTTP_REQUEST_METHOD_POST)
                .setClz(OrderRefundReasonEntity[].class)
                .build().sendAsyncHttpRequest(new HttpCallback() {
            @Override
            public void onHttpFinish(ServerResponse serverResponse, Map<String, String> requestParams, String requestUrl) {
                if (null == mPresenter) return;
                mPresenter.onGetRefundReasonList(serverResponse);
            }
        });
    }

    @Override
    public void submitRefund(String rec_id, String ids, String money, String note, List<String> imgs) {
        HashMap<String, String> map = new HashMap<>();
        map.put("rec_id", rec_id);
        map.put("ids[0]", ids);
        map.put("money", money);
        map.put("note", note);
        Map<String, File> fileParams = null;
        if (null != imgs && !imgs.isEmpty()) {
            fileParams = new HashMap<>();
            for (int i = 0; i < imgs.size(); i++) {
                String pic = imgs.get(i);
                fileParams.put("imgs[" + i + "]", new File(pic));
//                fileParams.put("imgs[]", new File(pic));
            }
        }
        if (null == fileParams || fileParams.isEmpty()) {
            HttpInvoker.createBuilder(NetConstant.GET_ORDER_REFUND_URL)
                    .setMethodType(HttpInvoker.HTTP_REQUEST_METHOD_POST)
                    .setParams(map)
                    .setClz(String.class)
                    .build().sendAsyncHttpRequest(new HttpCallback() {
                @Override
                public void onHttpFinish(ServerResponse serverResponse, Map<String, String> requestParams, String requestUrl) {
                    if (null == mPresenter) return;
                    mPresenter.onSubmitRefund(serverResponse);
                }
            });
        } else {
            HttpInvoker.createBuilder(NetConstant.GET_ORDER_REFUND_URL)
                    .setMethodType(HttpInvoker.HTTP_REQUEST_METHOD_FILEUP)
                    .setParams(map)
                    .setFileParams(fileParams)
                    .setClz(String.class)
                    .build().sendAsyncHttpRequest(new HttpCallback() {
                @Override
                public void onHttpFinish(ServerResponse serverResponse, Map<String, String> requestParams, String requestUrl) {
                    if (null == mPresenter) return;
                    mPresenter.onSubmitRefund(serverResponse);
                }
            });
        }
    }
}
