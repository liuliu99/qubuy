package com.qubuyer.business.good.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.qubuyer.R;
import com.qubuyer.bean.event.SOSelectAddressEvent;
import com.qubuyer.bean.mine.MineAddressEntitiy;
import com.qubuyer.business.mine.activity.AddressListActivity;
import com.qubuyer.constant.NetConstant;
import com.qubuyer.utils.NavigationUtil;
import com.qubyer.okhttputil.callback.HttpCallback;
import com.qubyer.okhttputil.helper.HttpInvoker;
import com.qubyer.okhttputil.helper.ServerResponse;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * @author Susong
 * @date 创建时间:2019/1/24
 * @description 鲜果特产提交订单页地址信息view
 * & @version
 */
public class SOAddressView extends FrameLayout {
    private Context mContext;
    private Unbinder unbinder;

    @BindView(R.id.rl_add_address)
    RelativeLayout rl_add_address;

    @BindView(R.id.rl_show_address)
    RelativeLayout rl_show_address;

    @BindView(R.id.tv_name)
    TextView tv_name;
    @BindView(R.id.tv_phone)
    TextView tv_phone;
    @BindView(R.id.tv_address)
    TextView tv_address;

    private OnSOAddressListener mListener;

    private MineAddressEntitiy mSelectedAddress;

    public SOAddressView(Context context) {
        super(context);
        init(context);
    }

    public SOAddressView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public SOAddressView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
//        if (isInEditMode()) return;
        mContext = context;
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
        inflate(getContext(), R.layout.layout_so_address_view, this);
        unbinder = ButterKnife.bind(this, this);
        getAddressListData();
    }

    @OnClick({R.id.rl_add_address, R.id.rl_show_address})
    public void onClickByButterknife(View view) {
        switch (view.getId()) {
            case R.id.rl_add_address:
                NavigationUtil.overlay(mContext, AddressListActivity.class, true);
                break;
            case R.id.rl_show_address:
                NavigationUtil.overlay(mContext, AddressListActivity.class, true);
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

    public void setListener(OnSOAddressListener mListener) {
        this.mListener = mListener;
    }

    public MineAddressEntitiy getSelectedAddress() {
        return mSelectedAddress;
    }

    /**
     * EventBus事件分发方法
     */
    @Subscribe
    public void onEventMainThread(SOSelectAddressEvent event) {
        if (null != event) {
            mSelectedAddress = event.getEntitiy();
            if (null != mSelectedAddress) {
                rl_add_address.setVisibility(GONE);
                rl_show_address.setVisibility(VISIBLE);
                tv_name.setText(mSelectedAddress.getConsignee());
                tv_phone.setText(mSelectedAddress.getMobile());
                tv_address.setText(mSelectedAddress.getAddress_area() + mSelectedAddress.getAddress());
            } else {
                rl_add_address.setVisibility(VISIBLE);
                rl_show_address.setVisibility(GONE);
            }
            if (null != mListener) {
                mListener.onAddressClickListener();
            }
        }
    }

    private void getAddressListData(){
        HttpInvoker.createBuilder(NetConstant.ADDRESS_LIST_URL)
                .setMethodType(HttpInvoker.HTTP_REQUEST_METHOD_POST)
                .setClz(MineAddressEntitiy[].class)
                .build().sendAsyncHttpRequest(new HttpCallback() {
            @Override
            public void onHttpFinish(ServerResponse serverResponse, Map<String, String> requestParams, String requestUrl) {
                List<MineAddressEntitiy> addressListEntitiy = (List<MineAddressEntitiy>) serverResponse.getResult();
                if (null != addressListEntitiy && !addressListEntitiy.isEmpty()) {
                    onEventMainThread(new SOSelectAddressEvent(addressListEntitiy.get(0)));
                }
            }
        });
    }

    public interface OnSOAddressListener {
        void onAddressClickListener();
    }
}
