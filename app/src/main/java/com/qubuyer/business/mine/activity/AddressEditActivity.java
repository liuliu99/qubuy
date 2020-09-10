package com.qubuyer.business.mine.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.StringUtils;
import com.qubuyer.R;
import com.qubuyer.base.activity.BaseActivity;
import com.qubuyer.bean.ToolbarMenuEntity;
import com.qubuyer.bean.event.SOSelectAddressEvent;
import com.qubuyer.bean.mine.MineAddressEntitiy;
import com.qubuyer.business.mine.presenter.AddressInfoPresenter;
import com.qubuyer.business.mine.view.IAddressInfoView;
import com.qubuyer.business.mine.view.SelectAreaDialog;
import com.qubuyer.constant.AppConstant;
import com.qubuyer.customview.AbsToolbar;
import com.qubuyer.customview.SwitchButton;
import com.qubuyer.utils.DialogUtil;
import com.ywp.addresspickerlib.YwpAddressBean;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * @author Susong
 * @date 创建时间2019/3/26
 * @description 地址添加和修改界面类
 */
public class AddressEditActivity extends BaseActivity<AddressInfoPresenter> implements IAddressInfoView {
    @BindView(R.id.rl_area)
    RelativeLayout rl_area;
    @BindView(R.id.tv_address_area)
    TextView tvAddressArea;
    @BindView(R.id.rl_del_address)
    RelativeLayout rlDelAddress;
    @BindView(R.id.slide_switch)
    SwitchButton slide_switch;
    @BindView(R.id.et_real_name)
    TextView etRealName;
    @BindView(R.id.et_phone_no)
    TextView etPhoneNo;
    @BindView(R.id.et_detail_address)
    TextView etDeatailAddress;


    private SelectAreaDialog selectAreaDialog;
    private ToolbarMenuEntity mToolbarMenuEntity;
    private List<ToolbarMenuEntity> mToolbarMenuEntityList;
    private MineAddressEntitiy addressEntitiy;

    private String provinceCode, cityCode, districtCode;

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (null != selectAreaDialog) {
            selectAreaDialog.destory();
        }
    }

    @Override
    protected int getContentView() {
        return R.layout.layout_activity_address_info;
    }

    @Override
    protected void initWidget(Bundle savedInstanceState) {
        if (null != getIntent() && null != getIntent().getSerializableExtra(AppConstant.INTENT_EXTRA_KEY)) {
            addressEntitiy = (MineAddressEntitiy) getIntent().getSerializableExtra(AppConstant.INTENT_EXTRA_KEY);
        }
        setNavigationDrawable(R.drawable.icon_return_black);
        initMenu();
        if (null == addressEntitiy) {
            setTitle("新建地址");
            rlDelAddress.setVisibility(View.GONE);
            slide_switch.setStatusImmediately(false);
        } else {
            setTitle("编辑地址");
            etRealName.setText(addressEntitiy.getConsignee());
            etPhoneNo.setText(addressEntitiy.getMobile());
            etDeatailAddress.setText(addressEntitiy.getAddress());
            tvAddressArea.setText(addressEntitiy.getAddress_area());
            provinceCode = String.valueOf(addressEntitiy.getProvince());
            cityCode = String.valueOf(addressEntitiy.getCity());
            districtCode = String.valueOf(addressEntitiy.getDistrict());
            if (addressEntitiy.getIs_default() == 1) {
                slide_switch.setStatusImmediately(true);
            }
        }
        rl_area.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showSelectAreaDialog();
            }
        });

        rlDelAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                android.app.AlertDialog dialog = DialogUtil.getConfirmDialog(AddressEditActivity.this, "确定删除该地址吗？", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mPresenter.delAddress(addressEntitiy.getAddress_id() + "");
                    }
                }).create();
                dialog.show();
            }
        });
        initSelectAreaDialog();
    }

    @Override
    protected void initData() {
        //0 新增，1 修改
        mPresenter.getAddressSelectList();
    }

    private void initMenu() {
        mToolbarMenuEntityList = new ArrayList<>();
        mToolbarMenuEntity = new ToolbarMenuEntity();
//        toolbarMenuEntity.setDpWidth(20);
//        toolbarMenuEntity.setDpHeight(20);
        mToolbarMenuEntity.setMenuContent("保存");
        mToolbarMenuEntity.setMenuId(R.id.add_address);
//        toolbarMenuEntity.setMenuDrawaleId(R.drawable.icon_mine_item_passenger);
        mToolbarMenuEntity.setOnMenuOnClickListener(new AbsToolbar.OnMenuOnClickListener() {
            @Override
            public void onMenuOnClick(View view) {
                MineAddressEntitiy addressEntitiy = new MineAddressEntitiy();
                if (!StringUtils.isEmpty(etRealName.getText().toString())) {
                    addressEntitiy.setConsignee(etRealName.getText().toString());
                } else {
                    showToastCenter("收货人姓名不能为空");
                    return;
                }
                if (!StringUtils.isEmpty(etPhoneNo.getText().toString())) {
                    String phone = etPhoneNo.getText().toString();
                    addressEntitiy.setMobile(phone);
//                    addressEntitiy.setPhone_text(phone.replace(phone.substring(3, 7), "****"));
                } else {
                    showToastCenter("收货人手机号码不能为空");
                    return;
                }
                if (!StringUtils.isEmpty(etDeatailAddress.getText().toString())) {
                    addressEntitiy.setAddress(etDeatailAddress.getText().toString());
                    addressEntitiy.setProvince(Integer.parseInt(provinceCode));
                    addressEntitiy.setCity(Integer.parseInt(cityCode));
                    addressEntitiy.setDistrict(Integer.parseInt(districtCode));
//                    String[] addressArea = tvAddressArea.getText().toString().split(" ");
//                    addressEntitiy.setProvince(addressArea[0]);
//                    addressEntitiy.setCity(addressArea[1]);
//                    addressEntitiy.setDistrict(addressArea[2]);
                } else {
                    showToastCenter("收货地址不能为空");
                    return;
                }

                if (slide_switch.getSwitchStatus() == 1) {
                    addressEntitiy.setIs_default(1);
                } else {
                    addressEntitiy.setIs_default(0);
                }
                if (null == AddressEditActivity.this.addressEntitiy) {
                    mPresenter.requestNewAddress(addressEntitiy);
                } else {
                    addressEntitiy.setAddress_id(AddressEditActivity.this.addressEntitiy.getAddress_id() + "");
                    mPresenter.requestUpdateAddress(addressEntitiy);
                }
            }
        });
        mToolbarMenuEntityList.add(mToolbarMenuEntity);
        inflateMenu(mToolbarMenuEntityList);
    }

    public void initSelectAreaDialog() {
        if (null == selectAreaDialog) {
            selectAreaDialog = new SelectAreaDialog(this, new SelectAreaDialog.OnSelectedAddressListener() {
                @Override
                public void onSelectedAddress(String address, String provinceCode, String cityCode, String districtCode) {
                    AddressEditActivity.this.provinceCode = provinceCode;
                    AddressEditActivity.this.cityCode = cityCode;
                    AddressEditActivity.this.districtCode = districtCode;
                    tvAddressArea.setText(address);
                    tvAddressArea.setTextColor(Color.parseColor("#333333"));
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

    @Override
    public void oprateSucess() {
        EventBus.getDefault().post(new SOSelectAddressEvent(addressEntitiy));
        finish();
    }

    @Override
    public void setAddressDeatailView(MineAddressEntitiy entitiy) {
        etRealName.setText(entitiy.getConsignee());
        etPhoneNo.setText(entitiy.getMobile());
        etDeatailAddress.setText(entitiy.getAddress());
        tvAddressArea.setText(entitiy.getAddress_area());
        if (entitiy.getIs_default() == 1) {
            slide_switch.setStatusImmediately(true);
        }
    }

    @Override
    public void setAddressSelectView(YwpAddressBean entitiy) {
        if (null != entitiy && null != selectAreaDialog) {
            selectAreaDialog.setAreaData(entitiy);
        }
    }
}
