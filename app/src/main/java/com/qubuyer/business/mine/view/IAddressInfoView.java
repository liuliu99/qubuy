package com.qubuyer.business.mine.view;

import com.qubuyer.base.mvp.BaseView;
import com.qubuyer.bean.mine.MineAddressEntitiy;
import com.ywp.addresspickerlib.YwpAddressBean;

public interface IAddressInfoView extends BaseView {
    void oprateSucess();

    void setAddressDeatailView(MineAddressEntitiy entitiy);

    void setAddressSelectView(YwpAddressBean entitiy);
}
