package com.qubuyer.business.mine.activity;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.ObjectUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.qubuyer.R;
import com.qubuyer.base.activity.BaseActivity;
import com.qubuyer.bean.event.MineGetLetteryEvent;
import com.qubuyer.bean.mine.MineAddressEntitiy;
import com.qubuyer.business.mine.presenter.AddressInfoPresenter;
import com.qubuyer.business.mine.view.IAddressInfoView;
import com.qubuyer.business.mine.view.SelectAreaDialog;
import com.qubuyer.utils.EventBusUtil;
import com.ywp.addresspickerlib.YwpAddressBean;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author Susong
 * @date 创建时间2020/1/11
 * @description 奖品领取地址添加
 * @version
 */
public class MineLotteryAddressEditActivity extends BaseActivity<AddressInfoPresenter> implements IAddressInfoView {
    @BindView(R.id.rl_area)
    RelativeLayout rl_area;
    @BindView(R.id.tv_address_area)
    TextView tv_address_area;
    @BindView(R.id.et_real_name)
    TextView et_real_name;
    @BindView(R.id.et_phone_no)
    TextView et_phone_no;
    @BindView(R.id.et_detail_address)
    TextView et_detail_address;

    private SelectAreaDialog selectAreaDialog;
    private String provinceCode, cityCode, districtCode;

    @Override
    protected int getContentView() {
        return R.layout.layout_activity_lottery_address_edit;
    }

    @Override
    protected void initWidget(Bundle savedInstanceState) {
        setTitle("填写收货地址");
        rl_area.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showSelectAreaDialog();
            }
        });
        initSelectAreaDialog();
    }

    @Override
    protected void initData() {
        mPresenter.getAddressSelectList();
    }

    public void initSelectAreaDialog() {
        if (null == selectAreaDialog) {
            selectAreaDialog = new SelectAreaDialog(this, new SelectAreaDialog.OnSelectedAddressListener() {
                @Override
                public void onSelectedAddress(String address, String provinceCode, String cityCode, String districtCode) {
                    MineLotteryAddressEditActivity.this.provinceCode = provinceCode;
                    MineLotteryAddressEditActivity.this.cityCode = cityCode;
                    MineLotteryAddressEditActivity.this.districtCode = districtCode;
                    tv_address_area.setText(address);
                    tv_address_area.setTextColor(Color.parseColor("#333333"));
                }
            });
        }
    }

    /**
     * 选择验收图片对话框
     */
    public void showSelectAreaDialog() {
        if (!selectAreaDialog.isShowing()) {
            selectAreaDialog.show();
        }
    }

    @Override
    protected AddressInfoPresenter createP(Context context) {
        return new AddressInfoPresenter();
    }

    @Override
    public void showLoading() {
        showLoadingDialog();
    }

    @Override
    public void hideLoading() {
        dissmissLoadingDialog();
    }

    @OnClick({R.id.tv_confirm})
    public void onClickWithButterKnife(View view) {
        switch (view.getId()) {
            case R.id.tv_confirm:
                String name = et_real_name.getText().toString();
                String mobile = et_phone_no.getText().toString();
                String address = et_detail_address.getText().toString();
                if (ObjectUtils.isEmpty(name)
                        || ObjectUtils.isEmpty(mobile)
                        || ObjectUtils.isEmpty(provinceCode)
                        || ObjectUtils.isEmpty(cityCode)
                        || ObjectUtils.isEmpty(districtCode)
                        || ObjectUtils.isEmpty(address)){
                    ToastUtils.showShort("请填写完整信息");
                    return;
                }
                EventBusUtil.sendEvent(new MineGetLetteryEvent(provinceCode, cityCode, districtCode, name, mobile, address));
                finish();
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (null != selectAreaDialog) {
            selectAreaDialog.destory();
        }
    }

    @Override
    public void oprateSucess() {

    }

    @Override
    public void setAddressDeatailView(MineAddressEntitiy entitiy) {

    }

    @Override
    public void setAddressSelectView(YwpAddressBean entitiy) {
        if (null != entitiy && null != selectAreaDialog) {
            selectAreaDialog.setAreaData(entitiy);
        }
    }
}
