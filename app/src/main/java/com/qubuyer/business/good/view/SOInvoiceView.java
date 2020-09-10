package com.qubuyer.business.good.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.qubuyer.R;
import com.qubuyer.bean.event.SOSelectInvoiceEvent;
import com.qubuyer.bean.mine.MineInvoiceEntitiy;
import com.qubuyer.bean.mine.UserEntity;
import com.qubuyer.business.good.activity.GoodInvoiceActivity;
import com.qubuyer.utils.NavigationUtil;
import com.qubuyer.utils.SessionUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * @author Susong
 * @date 创建时间2019/3/25
 * @description 提交订单发票view
 */
public class SOInvoiceView extends FrameLayout {
    private Context mContext;
    private Unbinder unbinder;

    @BindView(R.id.rl_invoice)
    RelativeLayout rl_invoice;

    @BindView(R.id.tv_invoice_mode)
    TextView tv_invoice_mode;

    private MineInvoiceEntitiy mSelectedInvoice;

    public SOInvoiceView(Context context) {
        super(context);
        init(context);
    }

    public SOInvoiceView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public SOInvoiceView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
//        if (isInEditMode()) return;
        mContext = context;
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
        inflate(getContext(), R.layout.layout_so_invoice_view, this);
        unbinder = ButterKnife.bind(this, this);

//        mSelectedInvoice = new MineInvoiceEntitiy();
//        mSelectedInvoice.setType(1);
//        UserEntity userEntity = SessionUtil.getInstance().getUserInfo();
//        if (null != userEntity) {
//            mSelectedInvoice.setPhone(userEntity.getMobile());
//        }
        onEventMainThread(new SOSelectInvoiceEvent(null));
    }

    @OnClick({R.id.rl_invoice})
    public void onClickByButterknife(View view) {
        switch (view.getId()) {
            case R.id.rl_invoice:
                NavigationUtil.overlay(mContext, GoodInvoiceActivity.class, mSelectedInvoice);
                break;
        }
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (null != unbinder) {
            unbinder.unbind();
        }
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
    }

    public MineInvoiceEntitiy getSelectedInvoice() {
        return mSelectedInvoice;
    }

    /**
     * EventBus事件分发方法
     */
    @Subscribe
    public void onEventMainThread(SOSelectInvoiceEvent event) {
        if (null != event) {
            mSelectedInvoice = event.getEntitiy();
            if (null != mSelectedInvoice && mSelectedInvoice.getIsUse() == 1) {
                tv_invoice_mode.setText("电子发票-" + (mSelectedInvoice.getType() == 1 ? "个人" : "单位"));
            } else {
                tv_invoice_mode.setText("不开发票");
            }
        }
    }
}
