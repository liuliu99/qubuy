package com.qubuyer.business.mine.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.blankj.utilcode.util.ObjectUtils;
import com.qubuyer.R;
import com.qubuyer.base.activity.BaseActivity;
import com.qubuyer.bean.mine.MineCardEntity;
import com.qubuyer.bean.mine.MinePosterEntity;
import com.qubuyer.bean.mine.UserEntity;
import com.qubuyer.business.mine.presenter.MineCardPresenter;
import com.qubuyer.business.mine.view.IMineCardView;
import com.qubuyer.constant.NetConstant;
import com.qubuyer.customview.BrowserActivity;
import com.qubuyer.customview.ImageViewAutoLoad;
import com.qubuyer.utils.ImageUtil;
import com.qubuyer.utils.NavigationUtil;
import com.qubuyer.utils.SessionUtil;

import java.util.Locale;

import butterknife.BindView;
import butterknife.OnClick;


/**
 * @author Susong
 * @date 创建时间:2019/4/8
 * @description 我的名片首页activity
 * & @version
 */
public class MineBusinessCardHomeActivity extends BaseActivity<MineCardPresenter> implements IMineCardView {
    @BindView(R.id.iv_user_headimg)
    ImageViewAutoLoad iv_user_headimg;
    @BindView(R.id.tv_user_id)
    TextView tv_user_id;
    @BindView(R.id.tv_inviter)
    TextView tv_inviter;

    @Override
    protected int getContentView() {
        return R.layout.layout_activity_mine_business_card_home;
    }

    @Override
    protected MineCardPresenter createP(Context context) {
        return new MineCardPresenter();
    }

    @Override
    protected void initWidget(Bundle savedInstanceState) {
        setTitle("消费商中心");
    }

    @Override
    protected void initData() {
        mPresenter.getUserInfo();
    }

    @Override
    public void showLoading() {
        showLoadingDialog();
    }

    @Override
    public void hideLoading() {
        dissmissLoadingDialog();
    }

    @OnClick({R.id.rl_my_fans, R.id.rl_earnings, R.id.rl_business_card})
    public void onClickWithButterKnife(View view) {
        switch (view.getId()) {
            case R.id.rl_my_fans: //我的粉丝
                NavigationUtil.overlay(this, MineFansActivity.class);
                break;
            case R.id.rl_earnings: //推广收益
                NavigationUtil.overlay(this, SaleAmountActivity.class);
                break;
            case R.id.rl_business_card: //我的名片
                if (!isAgency()) {
                    Intent intent = new Intent(this, BrowserActivity.class);
                    intent.putExtra(BrowserActivity.KEY_URL, NetConstant.BASE_URL + "/home/html/application?token=" + SessionUtil.getInstance().getToken());
                    startActivity(intent);
                } else {
                    NavigationUtil.overlay(this, MineBusinessCardActivity.class);
                }
                break;
        }
    }

    @Deprecated
    @Override
    public void onShowQrCodeInfoToView(MineCardEntity entity) {
    }

    @Deprecated
    @Override
    public void onShowPosterInfoToView(MinePosterEntity entity) {
    }

    @Override
    public void onShowUserInfoToView(UserEntity entity) {
        if (null != entity) {
            SessionUtil.getInstance().saveUserInfo(entity);
            ImageUtil.loadCircleImage(this, entity.getHead_pic(), iv_user_headimg);
            tv_user_id.setVisibility(View.VISIBLE);
            tv_user_id.setText(String.format(Locale.CHINA, "ID: %d", entity.getUser_id()));
            tv_inviter.setText(String.format(Locale.CHINA, "邀请人: %s", ObjectUtils.isNotEmpty(entity.getSecond_leader_nickname()) ? entity.getSecond_leader_nickname() : ""));
        } else {
            iv_user_headimg.setDrawableId(this, R.drawable.icon_default_headimg);
            tv_user_id.setText(String.format(Locale.CHINA, "ID: %s", ""));
            tv_inviter.setText("邀请人: 无");
        }
    }

    private boolean isAgency() {
        UserEntity userEntity = SessionUtil.getInstance().getUserInfo();
        return null != userEntity && userEntity.getIs_distribut() == 1;
    }
}
