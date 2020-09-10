package com.qubuyer.business.mine.view;


import com.qubuyer.base.mvp.BaseView;
import com.qubuyer.bean.mine.MineAddressEntitiy;

import java.util.List;

public interface IAddressListView extends BaseView {
    void setAddressView(List<MineAddressEntitiy> data);

    void loadListView();
}
