package com.qubuyer.business.mine.fragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.blankj.utilcode.util.BarUtils;
import com.qubuyer.R;
import com.qubuyer.base.fragment.BaseFragment;
import com.qubuyer.bean.event.GoToMainEvent;
import com.qubuyer.bean.mine.UserEntity;
import com.qubuyer.bean.mine.UserMessageCountEntity;
import com.qubuyer.bean.mine.WalletInfoEntity;
import com.qubuyer.business.login.activity.LoginActivity;
import com.qubuyer.business.mine.activity.AddressListActivity;
import com.qubuyer.business.mine.activity.MessageListActivity;
import com.qubuyer.business.mine.activity.MineBrowseFootprintActivity;
import com.qubuyer.business.mine.activity.MineBusinessCardHomeActivity;
import com.qubuyer.business.mine.activity.MineCollectActivity;
import com.qubuyer.business.mine.activity.MineCommentListActivity;
import com.qubuyer.business.mine.activity.MineWalletActivity;
import com.qubuyer.business.mine.activity.RebateOrderActivity;
import com.qubuyer.business.mine.activity.SaleAmountActivity;
import com.qubuyer.business.mine.activity.SettingActivity;
import com.qubuyer.business.mine.presenter.MinePresenter;
import com.qubuyer.business.mine.view.IMineView;
import com.qubuyer.business.order.activity.OrderListActivity;
import com.qubuyer.business.order.activity.OrderRefundListActivity;
import com.qubuyer.business.register.activity.RegisterActivity;
import com.qubuyer.constant.NetConstant;
import com.qubuyer.customview.BrowserActivity;
import com.qubuyer.customview.ImageViewAutoLoad;
import com.qubuyer.utils.DialogUtil;
import com.qubuyer.utils.EventBusUtil;
import com.qubuyer.utils.ImageUtil;
import com.qubuyer.utils.NavigationUtil;
import com.qubuyer.utils.SessionUtil;

import butterknife.BindView;
import butterknife.OnClick;

public class MineFragment extends BaseFragment<MinePresenter> implements IMineView {

    @BindView(R.id.tv_message_count)
    TextView tv_message_count;

    @BindView(R.id.iv_user_headimg)
    ImageViewAutoLoad iv_user_headimg;
    @BindView(R.id.ll_login_register)
    LinearLayout ll_login_register;
    @BindView(R.id.tv_login)
    TextView tv_login;
    @BindView(R.id.tv_register)
    TextView tv_register;
    @BindView(R.id.tv_user_name)
    TextView tv_user_name;
    @BindView(R.id.tv_user_id)
    TextView tv_user_id;
    @BindView(R.id.tv_user_level)
    TextView tv_user_level;

    @BindView(R.id.tv_balance)
    TextView tv_balance;
    @BindView(R.id.tv_rebate)
    TextView tv_rebate;
    @BindView(R.id.tv_bonus)
    TextView tv_bonus;
    @BindView(R.id.tv_brokerage)
    TextView tv_brokerage;

    @BindView(R.id.tv_obligation_message_count)
    TextView tv_obligation_message_count;
    @BindView(R.id.tv_to_send_message_count)
    TextView tv_to_send_message_count;
    @BindView(R.id.tv_received_message_count)
    TextView tv_received_message_count;
    @BindView(R.id.tv_remain_comment_message_count)
    TextView tv_remain_comment_message_count;
    @BindView(R.id.tv_refund_message_count)
    TextView tv_refund_message_count;

    @Override
    protected int getLayoutId() {
        return R.layout.layout_fragment_mine;
    }

    @Override
    protected MinePresenter createP(Fragment context) {
        return new MinePresenter();
    }

    @Override
    protected void initWidget(View root) {
        isUpdateStatusBar = true;
        setStatusBarVisibility(true);
        setStatusBarDrawable(R.drawable.shape_status_bg);
    }

    @Override
    public void onResume() {
        super.onResume();
        BarUtils.setStatusBarLightMode(getActivity(), false);
        if (!isHidden()) {
            setUserInfo();
        }
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        if (!hidden) {
            isUpdateStatusBar = true;
            BarUtils.setStatusBarLightMode(getActivity(), false);
            setUserInfo();
        } else {
            isUpdateStatusBar = false;
        }
        super.onHiddenChanged(hidden);
    }

    private void setUserInfo() {
        if (!SessionUtil.getInstance().isLogined()) {
            iv_user_headimg.setDrawableId(getActivity(), R.drawable.icon_default_headimg);
            tv_user_name.setVisibility(View.GONE);
            ll_login_register.setVisibility(View.VISIBLE);
            tv_user_id.setText("");
            tv_user_id.setVisibility(View.GONE);
            tv_user_level.setVisibility(View.GONE);
            tv_message_count.setText("");
            tv_message_count.setVisibility(View.GONE);
            tv_balance.setText("0.00");
            tv_rebate.setText("0.00");
            tv_bonus.setText("0.00");
            tv_brokerage.setText("0.00");

            tv_obligation_message_count.setVisibility(View.GONE);
            tv_to_send_message_count.setVisibility(View.GONE);
            tv_received_message_count.setVisibility(View.GONE);
            tv_remain_comment_message_count.setVisibility(View.GONE);
            tv_refund_message_count.setVisibility(View.GONE);
        } else {
            mPresenter.getUserInfo();
            mPresenter.getUserMessageCount();
            mPresenter.getWalletInfo();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @OnClick({R.id.iv_user_headimg,
            R.id.tv_login,
            R.id.tv_register,
            R.id.tv_user_name,
            R.id.tv_user_id,
            R.id.fl_setting,
            R.id.fl_message,
            R.id.rl_all_order,
            R.id.tv_all_order,
            R.id.iv_all_order,
            R.id.ll_obligation,
            R.id.ll_to_send,
            R.id.ll_received,
            R.id.ll_remain_comment,
            R.id.ll_refund,
            R.id.ll_balance,
            R.id.rl_all_wallet,
            R.id.tv_all_wallet,
            R.id.iv_all_wallet,
            R.id.ll_brokerage,
            R.id.ll_collect,
            R.id.ll_businesscard,
            R.id.ll_data_statistics,
            R.id.ll_address_manager,
            R.id.ll_online_customer_service,
            R.id.ll_browsefootprint,
            R.id.ll_help_center,
            R.id.ll_wallet,
            R.id.ll_rebate,
            R.id.ll_bonus,
            R.id.iv_lottery})
    public void onClickByButterKnfie(View v) {
        switch (v.getId()) {
            case R.id.fl_setting: //设置
                if (checkLogin()) {
                    NavigationUtil.overlay(mContext, SettingActivity.class);
                }
                break;
            case R.id.fl_message: //消息
                if (checkLogin()) {
                    NavigationUtil.overlay(mContext, MessageListActivity.class);
                }
                break;
            case R.id.tv_register: //注册
                NavigationUtil.overlay(mContext, RegisterActivity.class);
                break;
            case R.id.iv_user_headimg: //用户头像
            case R.id.tv_user_id: //用户id
            case R.id.tv_login: //登录
                checkLogin();
                break;
            case R.id.rl_all_order:
            case R.id.tv_all_order:
            case R.id.iv_all_order: //查看全部订单
                if (checkLogin()) {
                    NavigationUtil.overlay(mContext, OrderListActivity.class, 0);
                }
                break;
            case R.id.ll_obligation: //待付款
                if (checkLogin()) {
                    NavigationUtil.overlay(mContext, OrderListActivity.class, 1);
                }
                break;
            case R.id.ll_to_send: //待发货
                if (checkLogin()) {
                    NavigationUtil.overlay(mContext, OrderListActivity.class, 2);
                }
                break;
            case R.id.ll_received: //待收货
                if (checkLogin()) {
                    NavigationUtil.overlay(mContext, OrderListActivity.class, 3);
                }
                break;
            case R.id.ll_remain_comment: //待评价
                if (checkLogin()) {
//                    NavigationUtil.overlay(mContext, OrderListActivity.class, 4);
                    NavigationUtil.overlay(mContext, MineCommentListActivity.class);
                }
                break;
            case R.id.ll_refund: //退款/收货
                if (checkLogin()) {
                    NavigationUtil.overlay(mContext, OrderRefundListActivity.class);
                }
                break;
            case R.id.rl_all_wallet:
            case R.id.tv_all_wallet:
            case R.id.iv_all_wallet: //查看全部钱包
            case R.id.ll_balance: //余额
            case R.id.ll_bonus: //奖金
                if (checkLogin()) {
                    NavigationUtil.overlay(mContext, MineWalletActivity.class);
                }
                break;
            case R.id.ll_rebate: //折让
                if (checkLogin()) {
                    NavigationUtil.overlay(mContext, RebateOrderActivity.class);
                }
                break;
            //                    if (!isAgency()) {
            //                        Intent intent = new Intent(mContext, BrowserActivity.class);
            //                        intent.putExtra(BrowserActivity.KEY_URL, NetConstant.BASE_URL + "/home/html/application?token=" + SessionUtil.getInstance().getToken());
            //                        mContext.startActivity(intent);
            //                    } else {
            //                        NavigationUtil.overlay(mContext, MineWalletActivity.class);
            //                    }
            case R.id.ll_brokerage: //佣金
                if (checkLogin()) {
//                    if (!isAgency()) {
//                        Intent intent = new Intent(mContext, BrowserActivity.class);
//                        intent.putExtra(BrowserActivity.KEY_URL, NetConstant.BASE_URL + "/home/html/application?token=" + SessionUtil.getInstance().getToken());
//                        mContext.startActivity(intent);
//                    } else {
//                        NavigationUtil.overlay(mContext, SaleAmountActivity.class);
//                    }
                    NavigationUtil.overlay(mContext, SaleAmountActivity.class);
                }
                break;
            case R.id.ll_collect: //我的收藏
                if (checkLogin()) {
                    NavigationUtil.overlay(mContext, MineCollectActivity.class);
                }
                break;
            case R.id.ll_businesscard: //我的名片
                if (checkLogin()) {
                    if (!isAgency()) {
                        Intent intent = new Intent(mContext, BrowserActivity.class);
                        intent.putExtra(BrowserActivity.KEY_URL, NetConstant.BASE_URL + "/home/html/application?token=" + SessionUtil.getInstance().getToken());
                        mContext.startActivity(intent);
                    } else {
                        NavigationUtil.overlay(mContext, MineBusinessCardHomeActivity.class);
                    }
                }
                break;
            case R.id.ll_address_manager: //地址管理
                if (checkLogin()) {
                    NavigationUtil.overlay(mContext, AddressListActivity.class);
                }
                break;
            case R.id.ll_online_customer_service: //在线客服
                if (checkLogin()) {
                    Intent intent = new Intent(mContext, BrowserActivity.class);
                    intent.putExtra(BrowserActivity.KEY_URL, NetConstant.ONLINE_SERVICE_URL);
                    intent.putExtra(BrowserActivity.KEY_DEFALT_TITLE, "在线客服");
                    intent.putExtra(BrowserActivity.KEY_NEED_UPDATE_TITLE, false);
                    startActivity(intent);
                }
                break;
            case R.id.ll_browsefootprint: //浏览足迹
                NavigationUtil.overlay(getActivity(), MineBrowseFootprintActivity.class);
                break;
            case R.id.ll_help_center: //帮助中心
                Intent intent = new Intent(mContext, BrowserActivity.class);
                intent.putExtra(BrowserActivity.KEY_URL, NetConstant.BASE_URL + "/home/html/helpCenter");
                mContext.startActivity(intent);
                break;
            case R.id.iv_lottery: //抽奖
                if (checkLogin()) {
                    intent = new Intent(getActivity(), BrowserActivity.class);
                    intent.putExtra(BrowserActivity.KEY_URL, NetConstant.ONLINE_SERVICE_URL);
                    intent.putExtra(BrowserActivity.KEY_DEFALT_TITLE, "幸运抽大奖");
                    intent.putExtra(BrowserActivity.KEY_NEED_UPDATE_TITLE, false);
                    intent.putExtra(BrowserActivity.KEY_TO_WEBVIEW_TYPE, "from_mine_lottery");
                    intent.putExtra(BrowserActivity.KEY_URL, String.format("%s/user/turntable/tu?token=%s", NetConstant.BASE_URL, SessionUtil.getInstance().getToken()));
                    startActivity(intent);
                }
                break;
        }
    }

    @Override
    public void onShowUserInfoToView(UserEntity entity) {
        if (null != entity) {
            SessionUtil.getInstance().saveUserInfo(entity);
//            iv_user_headimg.setUri(mContext, NetConstant.BASE_URL + entity.getHead_pic());
            ImageUtil.loadCircleImage(mContext, entity.getHead_pic(), iv_user_headimg);
//            Glide.with(this).load(NetConstant.BASE_URL + entity.getHead_pic()).apply(RequestOptions.bitmapTransform(new CircleCrop())).into(iv_user_headimg);
            ll_login_register.setVisibility(View.GONE);
            tv_user_name.setVisibility(View.VISIBLE);
            tv_user_name.setText(entity.getNickname());
            tv_user_id.setVisibility(View.VISIBLE);
            tv_user_id.setText("ID:" + entity.getUser_id() + "");
            tv_user_level.setVisibility(View.VISIBLE);
            tv_user_level.setText(entity.getLevel_name());
            if (null != entity.getOrder()) {
                if (entity.getOrder().getWait_pay_count() > 0) {
                    tv_obligation_message_count.setVisibility(View.VISIBLE);
                    tv_obligation_message_count.setText(entity.getOrder().getWait_pay_count() + "");
                } else {
                    tv_obligation_message_count.setVisibility(View.GONE);
                }
                if (entity.getOrder().getWait_unsend_count() > 0) {
                    tv_to_send_message_count.setVisibility(View.VISIBLE);
                    tv_to_send_message_count.setText(entity.getOrder().getWait_unsend_count() + "");
                } else {
                    tv_to_send_message_count.setVisibility(View.GONE);
                }
                if (entity.getOrder().getWait_send_count() > 0) {
                    tv_received_message_count.setVisibility(View.VISIBLE);
                    tv_received_message_count.setText(entity.getOrder().getWait_send_count() + "");
                } else {
                    tv_received_message_count.setVisibility(View.GONE);
                }
                tv_remain_comment_message_count.setVisibility(View.GONE);
//                if (entity.getOrder().getWait_receive_count() > 0) {
//                    tv_remain_comment_message_count.setVisibility(View.VISIBLE);
//                    tv_remain_comment_message_count.setText(entity.getOrder().getWait_receive_count() + "");
//                } else {
//                    tv_remain_comment_message_count.setVisibility(View.GONE);
//                }
                if (entity.getOrder().getReturn_count() > 0) {
                    tv_refund_message_count.setVisibility(View.VISIBLE);
                    tv_refund_message_count.setText(entity.getOrder().getReturn_count() + "");
                } else {
                    tv_refund_message_count.setVisibility(View.GONE);
                }
            }
            tv_balance.setText(entity.getUser_money());
            tv_rebate.setText(entity.getRestore_money());
            tv_bonus.setText(entity.getBouns_money());
            tv_brokerage.setText(entity.getDistribut_money());
        } else {
            iv_user_headimg.setDrawableId(getActivity(), R.drawable.icon_default_headimg);
            ll_login_register.setVisibility(View.VISIBLE);
            tv_user_name.setVisibility(View.GONE);
            tv_user_id.setText("");
            tv_user_id.setVisibility(View.GONE);
            tv_user_level.setVisibility(View.GONE);
            tv_message_count.setText("");
            tv_message_count.setVisibility(View.GONE);
            tv_balance.setText("0");
            tv_rebate.setText("0");
            tv_bonus.setText("0");
            tv_brokerage.setText("0");

            tv_obligation_message_count.setVisibility(View.GONE);
            tv_to_send_message_count.setVisibility(View.GONE);
            tv_received_message_count.setVisibility(View.GONE);
            tv_remain_comment_message_count.setVisibility(View.GONE);
            tv_refund_message_count.setVisibility(View.GONE);
        }
    }

    @Override
    public void onShowLoginOutResultToView(boolean result) {
        if (result) {
//            setUserInfo();
            EventBusUtil.sendEvent(new GoToMainEvent());
        }
    }

    @Override
    public void onShowUserMessageCountDataToView(UserMessageCountEntity entity) {
        if (null != entity && entity.getCount() > 0) {
            tv_message_count.setVisibility(View.VISIBLE);
            tv_message_count.setText(entity.getCount() + "");
        } else {
            tv_message_count.setVisibility(View.GONE);
            tv_message_count.setText("0");
        }
    }

    @Override
    public void onShoWalletInfoToView(WalletInfoEntity entity) {
        if (null != entity) {
//            if (!TextUtils.isEmpty(entity.getMoney()) && Float.parseFloat(entity.getMoney()) > 100) {
//                tv_balance.setText(StringUtil.formatAmount(entity.getMoney(), 0));
//            } else {
//                tv_balance.setText(StringUtil.formatAmount(entity.getMoney(), 2));
//            }
            tv_balance.setText(entity.getMoney());
//            if (!TextUtils.isEmpty(entity.getRestore()) && Float.parseFloat(entity.getRestore()) > 100) {
//                tv_rebate.setText(StringUtil.formatAmount(entity.getRestore(), 0));
//            } else {
//                tv_rebate.setText(StringUtil.formatAmount(entity.getRestore(), 2));
//            }
            tv_rebate.setText(entity.getRestore());
//            if (!TextUtils.isEmpty(entity.getBonus()) && Float.parseFloat(entity.getBonus()) > 100) {
//                tv_bonus.setText(StringUtil.formatAmount(entity.getBonus(), 0));
//            } else {
//                tv_bonus.setText(StringUtil.formatAmount(entity.getBonus(), 2));
//            }
            tv_bonus.setText(entity.getBonus());
//            if (!TextUtils.isEmpty(entity.getDistribut()) && Float.parseFloat(entity.getDistribut()) > 100) {
//                tv_brokerage.setText(StringUtil.formatAmount(entity.getDistribut(), 0));
//            } else {
//                tv_brokerage.setText(StringUtil.formatAmount(entity.getDistribut(), 2));
//            }
            tv_brokerage.setText(entity.getDistribut());
        }
    }

    private boolean checkLogin() {
        if (!SessionUtil.getInstance().isLogined()) {
            DialogUtil.getConfirmDialog(mContext, "提示", "需要登录后，才能继续以下操作，是否现在登录？", "登录", "取消", false, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    NavigationUtil.overlay(mContext, LoginActivity.class);
                }
            }, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            }).show();
            return false;
        }
        return true;
    }

    private boolean isAgency() {
        UserEntity userEntity = SessionUtil.getInstance().getUserInfo();
        return null != userEntity && userEntity.getIs_distribut() == 1;
    }
}
