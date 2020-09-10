package com.qubuyer.business.good.view;


import com.qubuyer.base.mvp.BaseView;
import com.qubuyer.bean.good.GoodCommentEntity;
import com.qubuyer.bean.good.GoodSOResultEntity;
import com.qubuyer.bean.good.SOGoodInfoEntity;
import com.qubuyer.bean.home.HomeGoodEntity;
import com.qubuyer.bean.payment.CalcOrderPriceResultEntity;
import com.qubuyer.bean.shopcart.ShopCartGoodEntity;

import java.util.List;

public interface ISOView extends BaseView {
    void onShowSOGoodInfoToView(SOGoodInfoEntity entity);
    void onShowSubmitOrderResultToView(GoodSOResultEntity entity);
    void onShowCalcOrderPriceResultToView(CalcOrderPriceResultEntity entity);
    void destory();
}
