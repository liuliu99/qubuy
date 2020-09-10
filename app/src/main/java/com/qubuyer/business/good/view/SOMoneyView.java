package com.qubuyer.business.good.view;

import android.content.Context;
import android.text.Html;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.ObjectUtils;
import com.qubuyer.R;
import com.qubuyer.utils.StringUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * @author Susong
 * @date 创建时间:2019/1/22
 * @description 提交订单页应付金额view
 * & @version
 */
public class SOMoneyView extends FrameLayout {
    private Context mContext;
    private Unbinder unbinder;

    @BindView(R.id.tv_money)
    TextView tv_money;
    @BindView(R.id.tv_msg1)
    TextView tv_msg1;
    @BindView(R.id.tv_msg2)
    TextView tv_msg2;

    private OnSOMoneyListener mListener;

    private View mContainerView;

    //选中的价格日期实体
    private String mSelectedPrice;
    //选中的购买数量
    private int mBuyCount = 1;
    //订单明细商品标题
    private String mGoodTitle;

    public SOMoneyView(Context context) {
        super(context);
        init(context);
    }

    public SOMoneyView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public SOMoneyView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        mContext = context;
        if (isInEditMode()) return;
        inflate(getContext(), R.layout.layout_so_money_view, this);
        unbinder = ButterKnife.bind(this, this);
//        ShadowDrawable.setShadowDrawable(rl_container, Color.parseColor("#FFFFFF"), 0, Color.parseColor("#40000000"), ConvertUtils.dp2px(2), 0, 0);
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (null != unbinder) {
            unbinder.unbind();
        }
    }

    public void setListener(OnSOMoneyListener mListener) {
        this.mListener = mListener;
    }

    public void setPayPrice(String mSelectedPrice) {
        this.mSelectedPrice = mSelectedPrice;
        if (null != tv_money) {
            tv_money.setText(StringUtil.formatAmount(Float.parseFloat(mSelectedPrice) * mBuyCount, 2));
        }
    }

    public void setMsg1(String msg) {
        if (ObjectUtils.isNotEmpty(msg)) {
            tv_msg1.setVisibility(VISIBLE);
            tv_msg1.setText(Html.fromHtml(msg));
        } else {
            tv_msg1.setVisibility(GONE);
        }
    }

    public void setMsg2(String msg) {
        if (ObjectUtils.isNotEmpty(msg)) {
            tv_msg2.setVisibility(VISIBLE);
            tv_msg2.setText(Html.fromHtml(msg));
        } else {
            tv_msg2.setVisibility(GONE);
        }
    }

    @OnClick({ R.id.tv_submit_order})
    public void onClickByButterKnife(View v) {
        switch (v.getId()) {
            case R.id.tv_submit_order:
                if (null != mListener) {
                    mListener.onSubmitOrderClickListener();
                }
                break;
        }
    }

    public void setGrayLayout(View view) {
        mContainerView = view;
    }

    public interface OnSOMoneyListener {
        void onOrderDetailClickListener();

        void onSubmitOrderClickListener();
    }
}
