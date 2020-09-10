package com.qubuyer.business.good.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.viewpager.widget.ViewPager;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.BarUtils;
import com.blankj.utilcode.util.ConvertUtils;
import com.blankj.utilcode.util.ObjectUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.google.android.material.tabs.TabLayout;
import com.qubuyer.R;
import com.qubuyer.base.activity.BaseActivity;
import com.qubuyer.bean.event.GoToMainEvent;
import com.qubuyer.bean.event.GoToShopCartEvent;
import com.qubuyer.bean.good.GoodAssessEntity;
import com.qubuyer.bean.good.GoodCommentEntity;
import com.qubuyer.bean.home.HomeGoodEntity;
import com.qubuyer.bean.shopcart.ShopCartGoodEntity;
import com.qubuyer.business.good.adapter.GoodDetailPagerAdapter;
import com.qubuyer.business.good.presenter.GoodDetailPresenter;
import com.qubuyer.business.good.view.GoodAfterSaleNotesViewPage;
import com.qubuyer.business.good.view.GoodBuyNotesViewPage;
import com.qubuyer.business.good.view.GoodCommentViewPage;
import com.qubuyer.business.good.view.GoodDetailBaseViewPage;
import com.qubuyer.business.good.view.GoodDetailViewPage;
import com.qubuyer.business.good.view.IGoodDetailView;
import com.qubuyer.business.main.MainActivity;
import com.qubuyer.constant.AppConstant;
import com.qubuyer.constant.NetConstant;
import com.qubuyer.customview.AbsToolbar;
import com.qubuyer.customview.BrowserActivity;
import com.qubuyer.customview.EnhanceTabLayout;
import com.qubuyer.customview.ImageViewAutoLoad;
import com.qubuyer.utils.EventBusUtil;
import com.qubuyer.utils.ShareSdkUtil1;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;


/**
 * @author Susong
 * @date 创建时间2019/3/23
 * @description 商品详情activity
 */
public class GoodDetailActivity extends BaseActivity<GoodDetailPresenter> implements IGoodDetailView, GoodDetailViewPage.OnPageScrollListener, ViewPager.OnPageChangeListener {

    @BindView(R.id.vp_page)
    ViewPager vp_page;

    @BindView(R.id.rl_tab_normal)
    RelativeLayout rl_tab_normal;

    @BindView(R.id.rl_tab_scroll)
    RelativeLayout rl_tab_scroll;
    @BindView(R.id.etl_tab)
    EnhanceTabLayout etl_tab;

    @BindView(R.id.tv_message_count)
    TextView tv_message_count;
    @BindView(R.id.iv_collect)
    ImageViewAutoLoad iv_collect;

    @BindView(R.id.rl_container_bottom)
    RelativeLayout rl_container_bottom;


    private GoodDetailPagerAdapter mViewPagerAdapter;

    private int mGoodId;

    private HomeGoodEntity mGoodEntity;

    private String mIsCollect;

    @Override
    protected int getContentView() {
        return R.layout.layout_activity_good_detail;
    }

    @Override
    protected GoodDetailPresenter createP(Context context) {
        return new GoodDetailPresenter();
    }

    @Override
    public AbsToolbar toolbar() {
        return null;
    }

    @Override
    protected void initWidget(Bundle savedInstanceState) {
        if (null != getIntent() && null != getIntent().getSerializableExtra(AppConstant.INTENT_EXTRA_KEY)) {
            mGoodId = (int) getIntent().getSerializableExtra(AppConstant.INTENT_EXTRA_KEY);
        }
        setStatusBarVisibility(false);
        initToolbar();
        initTabAndViewPager();
    }

    private void initToolbar() {
        rl_tab_normal.setVisibility(View.VISIBLE);
        rl_tab_scroll.setVisibility(View.GONE);

        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ConvertUtils.dp2px(43));
        layoutParams.setMargins(0, BarUtils.getStatusBarHeight(), 0, 0);
        rl_tab_normal.setLayoutParams(layoutParams);
//        rl_tab_scroll.setLayoutParams(layoutParams);
    }

    @Override
    protected void initData() {
        mPresenter.getShopCartGoodList();
    }

    private void initTabAndViewPager() {
        ArrayList<String> titleList = new ArrayList<>();
        titleList.add("详情");
        titleList.add("评价");
        titleList.add("购买须知");
        titleList.add("售后");
        List<GoodDetailBaseViewPage> pageList = new ArrayList<>();
        GoodDetailBaseViewPage page1 = new GoodDetailViewPage(this, mGoodId + "", rl_container_bottom, this);
        page1.getView();
        GoodCommentViewPage page2 = new GoodCommentViewPage(this, mGoodId + "");
        page2.getView();
        GoodBuyNotesViewPage page3 = new GoodBuyNotesViewPage(this, mGoodId + "");
        page3.getView();
        GoodAfterSaleNotesViewPage page4 = new GoodAfterSaleNotesViewPage(this, mGoodId + "");
        page4.getView();
        pageList.add(page1);
        pageList.add(page2);
        pageList.add(page3);
        pageList.add(page4);
        for (int i = 0; i < titleList.size(); i++) {
            etl_tab.addTab(titleList.get(i));
        }
        mViewPagerAdapter = new GoodDetailPagerAdapter(this, pageList);
        vp_page.setAdapter(mViewPagerAdapter);
        vp_page.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(etl_tab.getTabLayout()));
        vp_page.addOnPageChangeListener(this);
        etl_tab.setupWithViewPager(vp_page);
        etl_tab.getTabLayout().getTabAt(0).select();
        onPageSelected(0);
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
    protected void onDestroy() {
        super.onDestroy();
        for (GoodDetailBaseViewPage temp : mViewPagerAdapter.getPageList()) {
            temp.destory();
        }
    }

    @OnClick({R.id.fl_return, R.id.fl_share, R.id.fl_go_main, R.id.iv_return, R.id.iv_share, R.id.iv_go_main, R.id.ll_shop_cart, R.id.ll_collect, R.id.tv_add_shop_cart, R.id.tv_buy_now, R.id.ll_service})
    public void onClickWithButterKnife(View v) {
        switch (v.getId()) {
            case R.id.fl_return:
            case R.id.iv_return: //返回
                finish();
                break;
            case R.id.fl_share: //分享
            case R.id.iv_share:
                HomeGoodEntity entity = ObjectUtils.isNotEmpty(mViewPagerAdapter) && ObjectUtils.isNotEmpty(mViewPagerAdapter.getPageList()) ? ((GoodDetailViewPage)mViewPagerAdapter.getPageList().get(0)).getGoodEntity() : null;
                if (ObjectUtils.isEmpty(entity)) {
                    ToastUtils.showShort("数据异常, 请刷新页面重试");
                    return;
                }
                String url = String.format("%s/home/html/goodsShare?id=%s", NetConstant.BASE_URL, entity.getGoods_id());
                ShareSdkUtil1.Builder.getInstanse(this)
                        .setTitle(entity.getGoods_name())
                        .setText("还有更多奖励等你一起拿")
                        .setImageUrl(ObjectUtils.isNotEmpty(entity.getGoods_images()) ? entity.getGoods_images().get(0).getImage_url() : null)
                        .setUrl(url)
                        .setTitleUrl(url)
                        .showShareMenu();
                break;
            case R.id.fl_go_main:
            case R.id.iv_go_main: //去首页
                EventBusUtil.sendEvent(new GoToMainEvent());
                ActivityUtils.finishOtherActivities(MainActivity.class);
                break;
            case R.id.ll_shop_cart: //购物车
                EventBusUtil.sendEvent(new GoToShopCartEvent());
                ActivityUtils.finishOtherActivities(MainActivity.class);
                break;
            case R.id.ll_collect: //收藏
                if (checkLogin()) {
                    mPresenter.collectGood(null != mGoodEntity ? mGoodEntity.getGoods_id() + "" : "");
                }
                break;
            case R.id.tv_add_shop_cart: //加入购物车
//                mPresenter.addGoodToCart(mGoodId + "", 1, null);
                if (null != mViewPagerAdapter && null != mViewPagerAdapter.getPageList()) {
                    ((GoodDetailViewPage) mViewPagerAdapter.getPageList().get(0)).addShoppingCart();
                }
                break;
            case R.id.tv_buy_now: //立即购买
//                HashMap map = new HashMap();
//                map.put("good_id", mGoodEntity.getGoods_id() + "");
////                map.put("item_id", mGoodEntity.getItem_id() + "");
//                map.put("item_id", "79");
//                map.put("buy_type", 1);
//                NavigationUtil.overlay(this, SOActivity.class, map);

                if (null != mViewPagerAdapter && null != mViewPagerAdapter.getPageList()) {
                    ((GoodDetailViewPage) mViewPagerAdapter.getPageList().get(0)).buyNow();
                }
                break;
            case R.id.ll_service: //客服
                if (checkLogin()) {
                    Intent intent = new Intent(this, BrowserActivity.class);
                    intent.putExtra(BrowserActivity.KEY_URL, NetConstant.ONLINE_SERVICE_URL);
                    intent.putExtra(BrowserActivity.KEY_DEFALT_TITLE, "在线客服");
                    intent.putExtra(BrowserActivity.KEY_NEED_UPDATE_TITLE, false);
                    startActivity(intent);
                }
                break;
        }
    }

    @Override
    public void onShowGoodDetailToView(HomeGoodEntity entity) {

    }

    @Override
    public void onShowShopCartListDataToView(List<ShopCartGoodEntity> list) {
        if (null != list && !list.isEmpty()) {
            ArrayList<ShopCartGoodEntity> goodList = new ArrayList<>();
            for (int i = 0; i < list.size(); i++) {
                ShopCartGoodEntity entity = list.get(i);
                if (entity.getIs_on_sale() == 0) {
                    goodList.add(entity);
                }
            }
            if (goodList.size() > 0) {
                tv_message_count.setVisibility(View.VISIBLE);
                tv_message_count.setText(goodList.size() + "");
            } else {
                tv_message_count.setVisibility(View.GONE);
                tv_message_count.setText("");
            }
        } else {
            tv_message_count.setVisibility(View.GONE);
            tv_message_count.setText("");
        }
    }

    @Override
    public void onShowCollectGoodResultToView(boolean result) {
        if (result) {
            if (mIsCollect.equalsIgnoreCase("0")) {
                iv_collect.setDrawableId(this, R.drawable.icon_good_detail_collect_selected);
                mIsCollect = "1";
            } else if (mIsCollect.equalsIgnoreCase("1")) {
                iv_collect.setDrawableId(this, R.drawable.icon_good_detail_collect_normal);
                mIsCollect = "0";
            }
        }
    }

    @Override
    public void onShowAddGoodToCartResultToView(boolean result) {
        if (result) {
            ToastUtils.showShort("添加成功");
            mPresenter.getShopCartGoodList();
        }
    }

    @Override
    public void onShowGoodCommentListToView(List<GoodCommentEntity> list) {

    }

    @Override
    public void onShowGoodAssessToView(GoodAssessEntity entity) {

    }

    @Override
    public void destory() {

    }

    @Override
    public void onShowGoodDetailEntityToActivity(HomeGoodEntity entity) {
        mGoodEntity = entity;
    }

    @Override
    public void onPageScrollListener(int type) {
        switch (type) {
            case 1:
                setStatusBarVisibility(false);
                rl_tab_normal.setVisibility(View.VISIBLE);
                rl_tab_scroll.setVisibility(View.GONE);
                break;
            case 2:
                setStatusBarVisibility(true);
                rl_tab_normal.setVisibility(View.GONE);
                rl_tab_scroll.setVisibility(View.VISIBLE);
                break;
        }
    }

    @Override
    public void onShowGoodIsCollect(String isCollect) {
        mIsCollect = isCollect;
        if (isCollect.equalsIgnoreCase("0")) {
            iv_collect.setDrawableId(this, R.drawable.icon_good_detail_collect_normal);
        } else {
            iv_collect.setDrawableId(this, R.drawable.icon_good_detail_collect_selected);
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        if (null != mViewPagerAdapter && null != mViewPagerAdapter.getPageList()) {
            mViewPagerAdapter.getPageList().get(position).loadData();
        }
        if (position > 0) {
            setStatusBarVisibility(true);
            rl_tab_normal.setVisibility(View.GONE);
            rl_tab_scroll.setVisibility(View.VISIBLE);
        } else {
            setStatusBarVisibility(false);
            rl_tab_normal.setVisibility(View.VISIBLE);
            rl_tab_scroll.setVisibility(View.GONE);
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    public void onUpdateShopCartCount() {
        mPresenter.getShopCartGoodList();
    }
}
