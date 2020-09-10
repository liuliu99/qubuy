package com.qubuyer.business.payment.view;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.WindowManager;
import android.widget.FrameLayout;

import com.alipay.sdk.app.PayTask;
import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.ConvertUtils;
import com.blankj.utilcode.util.KeyboardUtils;
import com.blankj.utilcode.util.ScreenUtils;
import com.blankj.utilcode.util.ThreadUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.qubuyer.R;
import com.qubuyer.base.activity.BaseActivity;
import com.qubuyer.bean.event.WechatPayStatusEvent;
import com.qubuyer.bean.mine.UserWalletEntity;
import com.qubuyer.bean.payment.AliPayResultEntity;
import com.qubuyer.bean.payment.PayListEntity;
import com.qubuyer.bean.payment.PayResultEntity;
import com.qubuyer.bean.payment.WechatPayParamEntity;
import com.qubuyer.business.main.MainActivity;
import com.qubuyer.business.payment.activity.PaymentResultActivity;
import com.qubuyer.business.payment.adapter.SOPayListAdapter;
import com.qubuyer.business.payment.presenter.SOPayPresenter;
import com.qubuyer.constant.AppConstant;
import com.qubuyer.customview.DecorationLLM;
import com.qubuyer.utils.DialogUtil;
import com.qubuyer.utils.EventBusUtil;
import com.qubuyer.utils.NavigationUtil;
import com.qubuyer.utils.StringUtil;
import com.qubuyer.utils.WechatUtils;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import org.greenrobot.eventbus.Subscribe;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * @author Susong
 * @date 创建时间:2019/1/22
 * @description 提交订单页支付列表view
 * & @version
 */
public class SOPayListView extends FrameLayout implements ISOPayView {
    private Context mContext;
    private Unbinder unbinder;

    @BindView(R.id.rv_list)
    RecyclerView rv_list;

    private OnSOPayListListener mListener;

    private SOPayListAdapter mAdapter;

    private SOPayPresenter mPresenter;

    private String mOrderPayId;

    //1:微信 2:支付宝 3:银联 4:账户余额支付
    private int mSelectedPayType = 1;

    private boolean mIsPay;
    private boolean mIsAlipaySuccess;

    //用户钱包信息
    private UserWalletEntity mUserWalletEntity;

    //选中的价格日期实体
    private String mSelectedPrice = "0";

    //输入支付密码弹框
    private PayPwdDialog mPayPwdDialog;

    public SOPayListView(Context context) {
        super(context);
        init(context);
    }

    public SOPayListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public SOPayListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void createP() {
        mPresenter = new SOPayPresenter();
        mPresenter.attachView(this);
    }

    private void init(Context context) {
        mContext = context;
        EventBusUtil.register(this);
        createP();
        inflate(getContext(), R.layout.layout_so_pay_list_view, this);
        unbinder = ButterKnife.bind(this, this);
        rv_list.setLayoutManager(new LinearLayoutManager(mContext, RecyclerView.VERTICAL, false));
        rv_list.addItemDecoration(new DecorationLLM(mContext, DecorationLLM.VERTICAL_LIST, R.drawable.shape_recyclerview_divider, ConvertUtils.dp2px(0)));
        mAdapter = new SOPayListAdapter(mContext, rv_list, new OnSOPayListListener() {
            @Override
            public void onSOPayListClickListener(PayListEntity entity) {
                mSelectedPayType = entity.getType();
            }
        });
        rv_list.setAdapter(mAdapter);
        mPresenter.getUserWallet();
        mPresenter.loadPayListData();
        if (AppConstant.DEBUG) {
//            EnvUtils.setEnv(EnvUtils.EnvEnum.SANDBOX);
        }
    }

    public void pay() {
        mIsPay = false;
        switch (mSelectedPayType) {
            case 1: //微信
                if (WechatUtils.isWeChatAppInstalled()) {
                    mPresenter.getWechatPayParam(mOrderPayId);
                } else {
                    ToastUtils.showShort("请安装最新版微信");
                }
                break;
            case 2: //支付宝
                mPresenter.getAlipayParam(mOrderPayId);
                break;
            case 3: //银联
                break;
            case 4: //账户余额
                DialogUtil.getConfirmDialog(mContext, "", "当前帐户还没设置支付密码", "前往设置", "取消", false, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
//                        NavigationUtil.overlay(mContext, AuthCodeActivity.class, 0);
                    }
                }, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).show();
                showInputPayPwdDialog();
                break;
        }
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (null != unbinder) {
            unbinder.unbind();
        }
    }

    public void setListener(OnSOPayListListener mListener) {
        this.mListener = mListener;
    }

    public void setData(List<PayListEntity> mData) {
        if (null != mAdapter) {
            mAdapter.setData(mData);
        }
    }

    public void setOrderPayId(String mOrderPayId) {
        this.mOrderPayId = mOrderPayId;
    }

    public void loadResult() {
        if (mIsPay && !mIsAlipaySuccess) {
            mPresenter.getOrderPayResult(mOrderPayId);
        }
//        else if (!mIsPay && !mIsAlipaySuccess) {
//            pay();
//        }
    }

    public void setPayPrice(String mSelectedPrice, int count) {
        this.mSelectedPrice = StringUtil.formatAmount(Float.parseFloat(mSelectedPrice) * count, 2);
    }

    public void setPayPrice(String mSelectedPrice) {
        setPayPrice(mSelectedPrice, 1);
    }

    @Override
    public void onShowPayListDataToView(List<PayListEntity> list) {
        if (null != mAdapter) {
            mAdapter.setData(list);
        }
    }

    @Override
    public void onShowWechatParamToView(WechatPayParamEntity entity) {
        if (null != entity) {
            if (TextUtils.isEmpty(entity.getAppid())
                    || TextUtils.isEmpty(entity.getPartnerid())
                    || TextUtils.isEmpty(entity.getPrepayid())
                    || TextUtils.isEmpty(entity.getNoncestr())
                    || TextUtils.isEmpty(entity.getTimestamp())
                    || TextUtils.isEmpty(entity.getSign())) {
                ToastUtils.showShort(R.string.pay_param_incomplete);
                mIsPay = false;
                return;
            }
            IWXAPI api = WXAPIFactory.createWXAPI(mContext, entity.getAppid());
            api.registerApp(entity.getAppid());
            PayReq payReq = new PayReq();
            payReq.appId = entity.getAppid();
            payReq.partnerId = entity.getPartnerid();
            payReq.prepayId = entity.getPrepayid();
            payReq.packageValue = "Sign=WXPay";
            payReq.nonceStr = entity.getNoncestr();
            payReq.timeStamp = entity.getTimestamp();
            payReq.sign = entity.getSign();
            api.sendReq(payReq);
        } else {
            mIsPay = false;
        }
    }

    @Override
    public void onShowAlipayParamToView(String result) {
        if (!TextUtils.isEmpty(result)) {
            ThreadUtils.executeByCached(new ThreadUtils.SimpleTask<Object>() {
                @Nullable
                @Override
                public Object doInBackground() {
                    PayTask alipay = new PayTask((Activity) mContext);
                    return alipay.payV2(result, true);
                }

                @Override
                public void onSuccess(@Nullable Object result) {
                    AliPayResultEntity payResult = new AliPayResultEntity((Map<String, String>) result);
                    /**
                     * 对于支付结果，请商户依赖服务端的异步通知结果。同步通知结果，仅作为支付结束的通知。
                     */
                    String resultInfo = payResult.getResult();// 同步返回需要验证的信息
                    String resultStatus = payResult.getResultStatus();
                    String memo = payResult.getMemo();
                    // 判断resultStatus 为9000则代表支付成功
                    if (TextUtils.equals(resultStatus, "9000")) {
                        // 该笔订单是否真实支付成功，需要依赖服务端的异步通知。
                        mIsPay = true;
                        mIsAlipaySuccess = true;
                        mPresenter.getOrderPayResult(mOrderPayId);
                    } else {
                        // 该笔订单真实的支付结果，需要依赖服务端的异步通知。
//                        ToastUtils.showShort(null != AliPayResultEntity.sError.get(resultStatus) ? AliPayResultEntity.sError.get(resultStatus) : resultStatus);
                        ToastUtils.showShort(memo);
                    }
                }
            });
        } else {
            ToastUtils.showShort(R.string.pay_param_incomplete);
            mIsPay = false;
        }
    }

    @Override
    public void onShowPayResultToView(boolean isSuccess, String resultString, PayResultEntity entity) {
        HashMap map = new HashMap();
        map.put("is_success", isSuccess);
        map.put("result_string", resultString);
        map.put("result_entity", entity);
        ActivityUtils.finishOtherActivities(MainActivity.class);
        NavigationUtil.overlay(mContext, PaymentResultActivity.class, map);
    }

    @Override
    public void onShowUserWalletToView(UserWalletEntity entity) {
        if (null != entity) {
            mUserWalletEntity = entity;
        } else {
            mUserWalletEntity = null;
        }
    }

    @Override
    public void showLoading() {
        if (mContext instanceof BaseActivity) {
            ((BaseActivity) mContext).showLoadingDialog();
        }
    }

    @Override
    public void hideLoading() {
        if (mContext instanceof BaseActivity) {
            ((BaseActivity) mContext).dissmissLoadingDialog();
        }
    }

    @Override
    public void doResponseError(int code, String message) {
        if (mContext instanceof BaseActivity) {
            ((BaseActivity) mContext).doResponseError(code, message);
        }
    }

    @Override
    public void destory() {
        if (null != mPresenter) {
            mPresenter.detachView();
            mPresenter.destoryModel();
            mPresenter = null;
        }
        EventBusUtil.unregister(this);
    }

    private void showInputPayPwdDialog() {
        if (null == mPayPwdDialog) {
            mPayPwdDialog = new PayPwdDialog(mContext);
            mPayPwdDialog.setTitle("支付金额");
            mPayPwdDialog.setListener(new PayPwdDialog.PayPwdListener() {
                @Override
                public void onInputPayPwdListener(String pwd) {
                    HideKeyBorad();
                    if (!pwd.equalsIgnoreCase("123456")) {
                        mPayPwdDialog.dismiss();
                        DialogUtil.getConfirmDialog(mContext, "", "支付密码错误", "重新输入", "忘记密码", false, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                if (!mPayPwdDialog.isShowing()) {
                                    mPayPwdDialog.show();
                                    DialogUtil.setDialogWindowAttr(mPayPwdDialog, ScreenUtils.getScreenWidth() - ConvertUtils.dp2px(126), WindowManager.LayoutParams.WRAP_CONTENT);
                                }
                            }
                        }, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
//                                NavigationUtil.overlay(mContext, AuthCodeActivity.class, 1);
                            }
                        }).show();
                    } else {

                        mPayPwdDialog.dismiss();
                    }
                }
            });
        }
        if (!mPayPwdDialog.isShowing()) {
            mPayPwdDialog.show();
            DialogUtil.setDialogWindowAttr(mPayPwdDialog, ScreenUtils.getScreenWidth() - ConvertUtils.dp2px(126), WindowManager.LayoutParams.WRAP_CONTENT);
        }
    }

    private void HideKeyBorad() {
        if (KeyboardUtils.isSoftInputVisible((Activity) mContext)) {
            KeyboardUtils.toggleSoftInput();
        }
    }

    /**
     * EventBus事件分发方法
     */
    @Subscribe
    public void onEventMainThread(WechatPayStatusEvent event) {
        if (null == event) return;
        switch (event.getStatus()) {
            case 1: //成功
                mIsPay = true;
                break;
            case 2: //失败
                mIsPay = false;
                break;
            case 3: //取消
                mIsPay = false;
                break;
        }
    }

    public interface OnSOPayListListener {
        void onSOPayListClickListener(PayListEntity entity);
    }
}
