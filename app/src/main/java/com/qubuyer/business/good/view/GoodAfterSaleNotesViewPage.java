package com.qubuyer.business.good.view;

import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.WebSettings;

import com.blankj.utilcode.util.ConvertUtils;
import com.qubuyer.R;
import com.qubuyer.base.activity.BaseActivity;
import com.qubuyer.bean.good.GoodAssessEntity;
import com.qubuyer.bean.good.GoodCommentEntity;
import com.qubuyer.bean.home.HomeGoodEntity;
import com.qubuyer.bean.shopcart.ShopCartGoodEntity;
import com.qubuyer.constant.NetConstant;
import com.qubuyer.customview.ImageViewAutoLoad;
import com.qubuyer.customview.PowerWebView;
import com.qubuyer.utils.WebViewUtil;

import java.util.List;

/**
 * @author Susong
 * @date 创建时间2019/3/24
 * @description 商品售后须知页
 */
public class GoodAfterSaleNotesViewPage extends GoodDetailBaseViewPage implements IGoodDetailView {
    //当前上下文
    private Context mContext;
    private View mView;
    private ImageViewAutoLoad iv_scroll_top;
    //商品ID
    private String mGoodId;

    private int mOverallXScroll = 0;

    public GoodAfterSaleNotesViewPage(Context mContext, String goodId) {
        this.mContext = mContext;
        mGoodId = goodId;
    }

    public View getView() {
        if (mView == null) {
            mView = LayoutInflater.from(mContext).inflate(R.layout.layout_activity_good_buy_notes_page, null);
            PowerWebView webView = mView.findViewById(R.id.pw_webview);
            WebViewUtil.initWebView(webView, WebSettings.LOAD_NO_CACHE);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                webView.setOnScrollChangeListener(new View.OnScrollChangeListener() {
                    @Override
                    public void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                        mOverallXScroll += scrollY;
                        if (mOverallXScroll > ConvertUtils.dp2px(50)) {
                            iv_scroll_top.setVisibility(View.VISIBLE);
                        } else {
                            iv_scroll_top.setVisibility(View.GONE);
                        }
                    }
                });
            }
            webView.loadUrl(NetConstant.BASE_URL + "/home/html/afterSale");
            iv_scroll_top = mView.findViewById(R.id.iv_scroll_top);
            iv_scroll_top.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOverallXScroll = 0;
                    webView.scrollTo(0, 0);
                }
            });
            iv_scroll_top.setVisibility(View.GONE);
        }
        return mView;
    }

    @Override
    public void showLoading() {
        ((BaseActivity) mContext).showLoading();
    }

    @Override
    public void hideLoading() {
        ((BaseActivity) mContext).hideLoading();
    }

    @Override
    public void doResponseError(int code, String message) {
        ((BaseActivity) mContext).doResponseError(code, message);
    }

    @Override
    public void destory() {
    }

    @Override
    public void loadData() {
    }

    @Override
    public void onShowGoodDetailToView(HomeGoodEntity entity) {
    }

    @Override
    public void onShowShopCartListDataToView(List<ShopCartGoodEntity> list) {

    }

    @Override
    public void onShowCollectGoodResultToView(boolean result) {

    }

    @Override
    public void onShowAddGoodToCartResultToView(boolean result) {

    }

    @Override
    public void onShowGoodCommentListToView(List<GoodCommentEntity> list) {
    }

    @Override
    public void onShowGoodAssessToView(GoodAssessEntity entity) {

    }
}


