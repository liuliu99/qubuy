package com.qubuyer.business.mine.view;

import android.content.Context;

import com.qubuyer.R;
import com.qubuyer.customview.BaseDialog;
import com.ywp.addresspickerlib.AddressPickerView;
import com.ywp.addresspickerlib.YwpAddressBean;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 选择地址dialog
 */
public class SelectAreaDialog extends BaseDialog {
    private Context mContext;
    @BindView(R.id.apv_address)
    AddressPickerView apv_address;

    private OnSelectedAddressListener mListener;

    public SelectAreaDialog(Context context, OnSelectedAddressListener listener) {
        super(context);
        mContext = context;
        mListener = listener;
        setContent();
    }

    private void setContent() {
        setContentView(R.layout.layout_dialog_select_area);
        ButterKnife.bind(this);
        apv_address.setOnAddressPickerSure(new AddressPickerView.OnAddressPickerSureListener() {
            @Override
            public void onSureClick(String address, String provinceCode, String cityCode, String districtCode) {
                if (null != mListener) {
                    mListener.onSelectedAddress(address, provinceCode, cityCode, districtCode);
                }
                dismiss();
            }

            @Override
            public void onCancel() {
                dismiss();
            }
        });
    }

    @Override
    public void dismiss() {
        super.dismiss();
    }

    public void destory() {
        if (null != apv_address) {
            apv_address.destory();
        }
    }

    public void setAreaData(YwpAddressBean bean) {
        if (null != apv_address) {
            apv_address.initData(bean);
        }
    }

    public interface OnSelectedAddressListener {
        void onSelectedAddress(String address, String provinceCode, String cityCode, String districtCode);
    }
}
