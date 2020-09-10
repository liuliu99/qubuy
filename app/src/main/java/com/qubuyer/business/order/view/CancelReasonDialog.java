package com.qubuyer.business.order.view;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.blankj.utilcode.util.ConvertUtils;
import com.qubuyer.R;
import com.qubuyer.bean.order.OrderRefundReasonEntity;
import com.qubuyer.business.order.adapter.OrderRefundReasonAdapter;
import com.qubuyer.customview.BaseDialog;
import com.qubuyer.customview.ImageViewAutoLoad;
import com.qubuyer.customview.MaxHeightRecyclerView;

import java.util.List;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author Susong
 * @date 创建时间2019/4/15
 * @description 取消原因
 * @version
 */
public class CancelReasonDialog extends BaseDialog {
    private Context mContext;

    @BindView(R.id.iv_cancel)
    ImageViewAutoLoad iv_cancel;
    @BindView(R.id.rv_list)
    MaxHeightRecyclerView rv_list;
    @BindView(R.id.tv_confirm)
    TextView tv_confirm;

    private SelectRefunReasonListener mListener;

    private List<OrderRefundReasonEntity> mData;

    private OrderRefundReasonAdapter mAdapter;

    private OrderRefundReasonEntity mSelectedRefundReasonEntity;

    public CancelReasonDialog(Context context, SelectRefunReasonListener listener) {
        super(context);
        mContext = context;
        mListener = listener;
        setContent();
    }

    public void setData(List<OrderRefundReasonEntity> mData) {
        this.mData = mData;
        mSelectedRefundReasonEntity = mData.get(0);
        mAdapter.setData(mData);
    }

    private void setContent() {
        setContentView(R.layout.layout_dialog_cancel_reason);
        ButterKnife.bind(this);
        rv_list.setRecyclerViewHeight(ConvertUtils.dp2px(300));
        mAdapter = new OrderRefundReasonAdapter(mContext, rv_list, new OrderRefundReasonAdapter.OnRefundReasonListListener() {
            @Override
            public void onRefundReasonClickListener(OrderRefundReasonEntity entity) {
                mSelectedRefundReasonEntity = entity;
            }
        });
        rv_list.setLayoutManager(new LinearLayoutManager(mContext, RecyclerView.VERTICAL, false));
        rv_list.setAdapter(mAdapter);
    }

    @OnClick({R.id.iv_cancel, R.id.tv_confirm})
    public void onClickByButterKnife(View v) {
        switch (v.getId()) {
            case R.id.iv_cancel:
                dismiss();
                break;
            case R.id.tv_confirm:
                dismiss();
                if (null != mListener) {
                    mListener.onRefundReasonClickListener(mSelectedRefundReasonEntity);
                }
                break;
        }
    }

    public interface SelectRefunReasonListener {
        void onRefundReasonClickListener(OrderRefundReasonEntity entity);
    }
}
