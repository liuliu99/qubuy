package com.qubuyer.business.good.view;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.Paint;
import android.net.http.SslError;
import android.os.Build;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.webkit.SslErrorHandler;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.ViewPager;

import com.blankj.utilcode.util.ObjectUtils;
import com.blankj.utilcode.util.ScreenUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.qubuyer.R;
import com.qubuyer.base.activity.BaseActivity;
import com.qubuyer.bean.good.GoodAssessEntity;
import com.qubuyer.bean.good.GoodCommentEntity;
import com.qubuyer.bean.home.HomeGoodEntity;
import com.qubuyer.bean.shopcart.ShopCartGoodEntity;
import com.qubuyer.business.good.activity.GoodDetailActivity;
import com.qubuyer.business.good.activity.SOActivity;
import com.qubuyer.business.good.activity.VideoViewActivity;
import com.qubuyer.business.good.loader.GoodDetailBannerLoader;
import com.qubuyer.business.good.presenter.GoodDetailPresenter;
import com.qubuyer.business.login.activity.LoginActivity;
import com.qubuyer.constant.NetConstant;
import com.qubuyer.customview.ImageViewAutoLoad;
import com.qubuyer.utils.DialogUtil;
import com.qubuyer.utils.LogUtil;
import com.qubuyer.utils.NavigationUtil;
import com.qubuyer.utils.SessionUtil;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;
import com.youth.banner.listener.OnBannerListener;
import com.ysnows.page.Page;
import com.ysnows.page.PageBehavior;
import com.ysnows.page.PageContainer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @author Susong
 * @date 创建时间2019/3/23
 * @description 商品详情页viewpage
 */
public class GoodDetailViewPage extends GoodDetailBaseViewPage implements IGoodDetailView {
    //当前上下文
    private Context mContext;
    private View mView;
    private RelativeLayout rl_container_bottom;
    private PageContainer pc_container;
    private Page p_two;
    private Banner banner;
    private TextView tv_banner_number;
    private TextView tv_good_name;
    private TextView tv_good_desc;
    private TextView tv_good_price;
    private TextView tv_good_price_rebate;
    private TextView tv_good_discount_rebate;
    private TextView tv_good_sale_count;
    private TextView tv_good_bazaar_price;
    private RelativeLayout rl_sku;
    private TextView tv_sku_title;
    private TextView tv_sku_content;
    private WebView webview;
    private ImageViewAutoLoad iv_scroll_top;
    private ImageViewAutoLoad iv_video_player;

    private GoodDetailPresenter mPresenter;
    //商品ID
    private String mGoodId;

    private HomeGoodEntity mGoodEntity;

    //选择的商品sku列表
    private String[] mSelectedGoodSkuValues;
    //选中的商品属性模型
    private HomeGoodEntity.GoodSKUModel mSelectedGoodSkuModel;
    //商品属性对象
    private List<HomeGoodEntity.GoodSKUModel> mGoodSKUModel;
    private List<HomeGoodEntity.GoodSKUKeyValue> mGoodSKUKeyValue;
    //弹出的商品属性窗口
    private GoodSKUPopupWindow mGoodSKUPopupWindow;
    //选择的商品数量
    private int mSelectedGoodCount = 1;
    //是否点了加入购物车
    private boolean mIsClickAddCart;

    private OnPageScrollListener mListener;

    public GoodDetailViewPage(Context mContext, String goodId, RelativeLayout rl_container_bottom, OnPageScrollListener listener) {
        this.mContext = mContext;
        mGoodId = goodId;
        mListener = listener;
        this.rl_container_bottom = rl_container_bottom;
        createP();
    }

    protected void createP() {
        mPresenter = new GoodDetailPresenter();
        mPresenter.attachView(this);
    }

    public View getView() {
        if (mView == null) {
            mView = LayoutInflater.from(mContext).inflate(R.layout.layout_activity_good_detail_page, null);
            pc_container = mView.findViewById(R.id.pc_container);
            pc_container.setOnPageChanged(new PageBehavior.OnPageChanged() {
                @Override
                public void toTop() {
                    if (null != mListener) {
                        mListener.onPageScrollListener(1);
                    }
                    iv_scroll_top.setVisibility(View.GONE);
                }

                @Override
                public void toBottom() {
                    if (null != mListener) {
                        mListener.onPageScrollListener(2);
                    }
                    iv_scroll_top.setVisibility(View.VISIBLE);
                }
            });
            p_two = mView.findViewById(R.id.p_two);
            banner = mView.findViewById(R.id.banner);
            tv_banner_number = mView.findViewById(R.id.tv_banner_number);
            tv_good_name = mView.findViewById(R.id.tv_good_name);
            tv_good_desc = mView.findViewById(R.id.tv_good_desc);
            tv_good_price = mView.findViewById(R.id.tv_good_price);
            tv_good_price_rebate = mView.findViewById(R.id.tv_good_price_rebate);
            tv_good_price_rebate.setVisibility(View.GONE);
            tv_good_discount_rebate = mView.findViewById(R.id.tv_good_discount_rebate);
            tv_good_sale_count = mView.findViewById(R.id.tv_good_sale_count);
            tv_good_bazaar_price = mView.findViewById(R.id.tv_good_bazaar_price);
            rl_sku = mView.findViewById(R.id.rl_sku);
            rl_sku.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showGoodPerprotyPopupFromBottom();
                }
            });
            tv_sku_title = mView.findViewById(R.id.tv_sku_title);
            tv_sku_content = mView.findViewById(R.id.tv_sku_content);
            webview = mView.findViewById(R.id.webview);
            iv_scroll_top = mView.findViewById(R.id.iv_scroll_top);
            iv_scroll_top.setVisibility(View.GONE);
            iv_scroll_top.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    pc_container.backToTop();
                }
            });
            iv_video_player = mView.findViewById(R.id.iv_video_player);
            iv_video_player.setVisibility(View.GONE);
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
        if (null != mPresenter) {
            mPresenter.detachView();
            mPresenter.destoryModel();
            mPresenter = null;
        }
    }

    @Override
    public void loadData() {
        if (null == mGoodEntity && !TextUtils.isEmpty(mGoodId)) {
            mPresenter.getGoodDetail(mGoodId);
        }
    }

    @Override
    public void onShowGoodDetailToView(HomeGoodEntity entity) {
        mGoodEntity = entity;
        if (null != entity) {
            banner.setBannerStyle(BannerConfig.NOT_INDICATOR);
            //设置图片加载器
            banner.setImageLoader(new GoodDetailBannerLoader());
            //设置banner动画效果
            banner.setBannerAnimation(Transformer.Default);
            //设置自动轮播，默认为true
            banner.isAutoPlay(true);
            //设置轮播时间
            banner.setDelayTime(2500);
            //设置图片集合
            List<HomeGoodEntity.GoodImg> goodImgList = entity.getGoods_images();
            if (ObjectUtils.isEmpty(goodImgList)) {
                goodImgList = new ArrayList<>();
            }
            if (ObjectUtils.isNotEmpty(entity.getVideo_full_path())) {
                goodImgList.add(0, new HomeGoodEntity.GoodImg(entity.getVideo_full_path()));
            }
            banner.setOffscreenPageLimit(goodImgList.size());
            banner.setImages(goodImgList);
            banner.setOnBannerListener(new OnBannerListener() {
                @Override
                public void OnBannerClick(int position) {
                    if (ObjectUtils.isNotEmpty(entity.getVideo_full_path()) && position == 0) {
                        NavigationUtil.overlay(mContext, VideoViewActivity.class, entity.getVideo_full_path());
                    } else {
                    }
                }
            });
            if (ObjectUtils.isNotEmpty(goodImgList)) {
                tv_banner_number.setVisibility(View.VISIBLE);
                tv_banner_number.setText("1/" + goodImgList.size());
            } else {
                tv_banner_number.setVisibility(View.GONE);
            }
            List<HomeGoodEntity.GoodImg> finalGoodImgList = goodImgList;
            banner.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                }

                @Override
                public void onPageSelected(int position) {
                    tv_banner_number.setText((position + 1) + "/" + finalGoodImgList.size());
                    if (ObjectUtils.isNotEmpty(entity.getVideo_full_path()) && position == 0) {
                        iv_video_player.setVisibility(View.VISIBLE);
                    } else {
                        iv_video_player.setVisibility(View.GONE);
                    }
                }

                @Override
                public void onPageScrollStateChanged(int state) {

                }
            });
            banner.start();
            tv_good_name.setText(entity.getGoods_name());
            tv_good_desc.setText(entity.getGoods_remark());
            tv_good_price.setText("¥" + entity.getShop_price());
            tv_good_price_rebate.setText("折让" + entity.getRestore());
            if (entity.getDiscount_rebate() == -1) {
                tv_good_discount_rebate.setVisibility(View.GONE);
            } else {
                tv_good_discount_rebate.setVisibility(View.VISIBLE);
                tv_good_discount_rebate.setText(entity.getDiscount_rebate() + "折");
            }
            tv_good_sale_count.setText("销量: " + entity.getSales_sum() + "+");
            tv_good_bazaar_price.setText("市场价 ¥" + entity.getMarket_price());
            tv_good_bazaar_price.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG); //中划线
            if (null != mListener) {
                mListener.onShowGoodDetailEntityToActivity(entity);
                mListener.onShowGoodIsCollect(entity.getIs_collect());
            }

            mGoodSKUKeyValue = entity.getKey_value_list();
            mGoodSKUModel = entity.getGoods_sku_model();

            setGoodSkuInfo();

            setGoodDetailToWebView(entity.getGoods_content());
        }
    }

    @Override
    public void onShowShopCartListDataToView(List<ShopCartGoodEntity> list) {

    }

    @Override
    public void onShowCollectGoodResultToView(boolean result) {

    }

    @Override
    public void onShowAddGoodToCartResultToView(boolean result) {
        if (result) {
            ToastUtils.showShort("添加成功");
            ((GoodDetailActivity) mContext).onUpdateShopCartCount();
        }
    }

    @Override
    public void onShowGoodCommentListToView(List<GoodCommentEntity> list) {

    }

    @Override
    public void onShowGoodAssessToView(GoodAssessEntity entity) {

    }

    private void setGoodSkuInfo() {
        if (null != mSelectedGoodSkuModel) {
            tv_sku_title.setText("已选");
            tv_sku_content.setText(mSelectedGoodSkuModel.getSpecPropertyValue());
        } else if (null != mSelectedGoodSkuValues) {
            boolean isFull = true;
            tv_sku_title.setText("请选择");
            StringBuilder stringBuilder = new StringBuilder();
            for (int i = 0; i < mSelectedGoodSkuValues.length; i++) {
                if (TextUtils.isEmpty(mSelectedGoodSkuValues[i])) {
                    if (null != mGoodSKUKeyValue && !mGoodSKUKeyValue.isEmpty()) {
                        stringBuilder.append(mGoodSKUKeyValue.get(i).getKey() + " ");
                        isFull = false;
                    }
                }
            }
            if (!isFull) {
                tv_sku_content.setText(stringBuilder.toString());
            }
        } else {
            tv_sku_title.setText("请选择");
            tv_sku_content.setText("规格");
        }
//        setGoodPriceToView();
    }

    private void setGoodDetailToWebView(String goodDetailContent) {
        if (!TextUtils.isEmpty(goodDetailContent)) {
            goodDetailContent = goodDetailContent.replace("&nbsp;", "");
            webview.setVisibility(View.VISIBLE);

//            webview.setOnTouchListener(new View.OnTouchListener() {
//                @Override
//                public boolean onTouch(View v, MotionEvent ev) {
//                    ((WebView)v).requestDisallowInterceptTouchEvent(true);
//                    return false;
//                }
//            });
            webview.getSettings().setDefaultTextEncodingName("UTF-8");
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                webview.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.TEXT_AUTOSIZING);
            } else {
                webview.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NORMAL);
            }
            webview.getSettings().setSupportZoom(true);
            webview.getSettings().setBuiltInZoomControls(true);

            webview.getSettings().setUseWideViewPort(true);
            webview.getSettings().setLoadWithOverviewMode(true);
            webview.loadDataWithBaseURL(NetConstant.BASE_URL, getHtmlData(goodDetailContent), "text/html", "utf-8", null);

//            WebSettings settings = webview.getSettings();
//            settings.setJavaScriptEnabled(true);
//            settings.setDomStorageEnabled(true);
//            settings.setUseWideViewPort(true);
//            settings.setLoadWithOverviewMode(true);
//            webview.setWebViewClient(new MyWebViewClient());
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
//                settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.TEXT_AUTOSIZING);
//            } else {
//                settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NORMAL);
//            }
//            webview.loadData(getHtmlData(goodDetailContent), "text/html;charset=utf-8","utf-8");
//            webview.loadUrl("https://github.com/ysnows");
        } else {
            ToastUtils.showShort("当前商品暂无图文详情");
            p_two.setVisibility(View.GONE);
        }
    }

    private String getHtmlData(String bodyHTML) {
        String head = "<head>" +
                "<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0, user-scalable=no\"> " +
                "<style>html{padding:10px;} body{word-wrap:break-word;font-size:13px;padding:0px;margin:0px} p{padding:0px;margin:0px;font-size:13px;color:#222222;line-height:1.3;} img{padding:0px,margin:0px;max-width:100%; width:100%; height:auto;}</style>" +
                "</head>";
        return "<html>" + head + "<body>" + bodyHTML + "</body></html>";
    }

    /**
     * 弹出商品属性选择界面
     */
    private void showGoodPerprotyPopupFromBottom() {
        if (null != mGoodEntity && (null == mGoodEntity.getGoods_sku_model() || mGoodEntity.getGoods_sku_model().isEmpty())) {
            ToastUtils.showShort("该商品无规格信息,请直接购买");
            return;
        }
        if (null == mGoodSKUPopupWindow) {
            mGoodSKUPopupWindow = new GoodSKUPopupWindow(mContext);
            mGoodSKUPopupWindow.setWidth(ScreenUtils.getScreenWidth());
            mGoodSKUPopupWindow.setHeight(WindowManager.LayoutParams.WRAP_CONTENT);
            mGoodSKUPopupWindow.setInputMethodMode(PopupWindow.INPUT_METHOD_NEEDED);
            mGoodSKUPopupWindow.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
            mGoodSKUPopupWindow.setBackgroundDrawable(ContextCompat.getDrawable(mContext, R.color.transparent));
            mGoodSKUPopupWindow.setAnimationStyle(R.style.shopping_cart_anim);
            mGoodSKUPopupWindow.setOnAddOrMinusGoodCountListener(new GoodSKUPopupWindow.OnGoodDetailOperationGoodSkuListener() {

                @Override
                public void onCountAddMinus(int count) {
                    mSelectedGoodCount = count;
                }

                @Override
                public void onAddShoppingCart() {
                    addShoppingCart();
                    mGoodSKUPopupWindow.dismiss();
                }

                @Override
                public void onBuyNow() {
                    buyNow();
                    mGoodSKUPopupWindow.dismiss();
                }

                @Override
                public void onSetSelectedSkuGoodInfo(HomeGoodEntity.GoodSKUModel goodSKUModel, String[] selectedGoodSkuValues) {
                    mSelectedGoodSkuModel = goodSKUModel;
                    mSelectedGoodSkuValues = selectedGoodSkuValues;
                    setGoodSkuInfo();
                }
            });
            mGoodSKUPopupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
                @Override
                public void onDismiss() {
                    backgroundAlpha(1f);
                }
            });
            mGoodSKUPopupWindow.setCallType(0);
            mGoodSKUPopupWindow.setGoods(mGoodEntity);
        }
        if (!mGoodSKUPopupWindow.isShowing()) {
            mGoodSKUPopupWindow.setTempAddGoodCount(1);
            if (null != mGoodSKUKeyValue && null != mGoodSKUModel) {
                mGoodSKUPopupWindow.setGoodSKUInfo(mGoodSKUKeyValue, mGoodSKUModel);
            }
            mGoodSKUPopupWindow.setSelectedGoodSku(mSelectedGoodSkuValues);
            mGoodSKUPopupWindow.showAtLocation(rl_container_bottom, Gravity.BOTTOM, 0, 0);
            backgroundAlpha(0.7f);
        } else {
            mGoodSKUPopupWindow.dismiss();
        }
    }

    public void addShoppingCart() {
        mIsClickAddCart = true;
        if (!checkIsSelectedSku()) return;
        if (!checkGoodStock()) return;
        if (checkLogin()) {
            mPresenter.addGoodToCart(mGoodEntity.getGoods_id() + "", mSelectedGoodCount, null != mSelectedGoodSkuModel ? mSelectedGoodSkuModel.getSkuId() + "" : "");
        }
    }

    public void buyNow() {
        if (!checkIsSelectedSku()) return;
        if (!checkGoodStock()) return;
        if (checkLogin()) {
            HashMap map = new HashMap();
            map.put("good_id", mGoodEntity.getGoods_id() + "");
            map.put("item_id", null != mSelectedGoodSkuModel ? mSelectedGoodSkuModel.getSkuId() + "" : "");
            map.put("good_num", mSelectedGoodCount);
            map.put("buy_type", 1);
            NavigationUtil.overlay(mContext, SOActivity.class, map);
            if (null != mGoodSKUPopupWindow) {
                mGoodSKUPopupWindow.dismiss();
            }
        }
    }

    /**
     * 是否选择了商品sku
     *
     * @return
     */
    private boolean checkIsSelectedSku() {
        if (null != mGoodSKUModel && !mGoodSKUModel.isEmpty()) {
            if (null == mSelectedGoodSkuModel) {
                showGoodPerprotyPopupFromBottom();
                mIsClickAddCart = false;
                return false;
            }
        }
        mIsClickAddCart = false;
        return true;
    }

    /**
     * 商品库存是否充足
     *
     * @return
     */
    private boolean checkGoodStock() {
        if (null != mSelectedGoodSkuModel
                && mSelectedGoodSkuModel.getStockNum() < mSelectedGoodCount) {
            ToastUtils.showShort("商品库存不足");
            return false;
        }
        return true;
    }

    /**
     * 设置添加屏幕的背景透明度
     */
    public void backgroundAlpha(float bgAlpha) {
        WindowManager.LayoutParams lp = ((BaseActivity) mContext).getWindow().getAttributes();
        lp.alpha = bgAlpha; //0.0-1.0
        ((BaseActivity) mContext).getWindow().setAttributes(lp);
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

    public HomeGoodEntity getGoodEntity() {
        return mGoodEntity;
    }

    class MyWebViewClient extends WebViewClient {

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            LogUtil.e(url);
            view.loadUrl(url);
            return true;
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
        }

        @Override
        public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
            handler.proceed();
            super.onReceivedSslError(view, handler, error);
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
        }
    }

    public interface OnPageScrollListener {
        void onShowGoodDetailEntityToActivity(HomeGoodEntity entity);

        void onPageScrollListener(int type);

        void onShowGoodIsCollect(String isCollect);
    }
}


