package com.qubuyer.business.shopcart.fragment;

import android.content.DialogInterface;
import android.graphics.Color;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;
import android.util.LongSparseArray;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.util.BarUtils;
import com.blankj.utilcode.util.ConvertUtils;
import com.blankj.utilcode.util.ObjectUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.qubuyer.R;
import com.qubuyer.base.fragment.BaseFragment;
import com.qubuyer.bean.event.GoToMainEvent;
import com.qubuyer.bean.home.HomeGoodEntity;
import com.qubuyer.bean.payment.CalcOrderPriceResultEntity;
import com.qubuyer.bean.shopcart.ShopCartGoodEntity;
import com.qubuyer.business.good.activity.SOActivity;
import com.qubuyer.business.login.activity.LoginActivity;
import com.qubuyer.business.shopcart.adapter.ShopCartGoodListAdapter;
import com.qubuyer.business.shopcart.adapter.ShopCartLoseEfficacyGoodListAdapter;
import com.qubuyer.business.shopcart.adapter.ShopCartSpecialGoodListAdapter;
import com.qubuyer.business.shopcart.presenter.ShopCartPresenter;
import com.qubuyer.business.shopcart.view.IShopCartView;
import com.qubuyer.customview.DecorationLLM;
import com.qubuyer.customview.OverSrollView;
import com.qubuyer.customview.ShopCartSpecialGoodItemDecoration;
import com.qubuyer.utils.DialogUtil;
import com.qubuyer.utils.EventBusUtil;
import com.qubuyer.utils.NavigationUtil;
import com.qubuyer.utils.SessionUtil;
import com.qubuyer.utils.StringUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class ShopCartFragment extends BaseFragment<ShopCartPresenter> implements IShopCartView {
    @BindView(R.id.rv_good_list)
    RecyclerView rv_good_list;
    @BindView(R.id.rv_lose_efficacy_good_list)
    RecyclerView rv_lose_efficacy_good_list;
    @BindView(R.id.ll_no_data)
    LinearLayout ll_no_data;
    @BindView(R.id.rv_special_good_list)
    RecyclerView rv_special_good_list;

    @BindView(R.id.cb_statu)
    CheckBox cb_statu;
    @BindView(R.id.tv_all_check)
    TextView tv_all_check;
    @BindView(R.id.tv_total)
    TextView tv_total;
    @BindView(R.id.tv_go_buy)
    TextView tv_go_buy;

    @BindView(R.id.osv_container)
    OverSrollView osv_container;
    @BindView(R.id.tv_msg1)
    TextView tv_msg1;
    @BindView(R.id.ll_bottom)
    LinearLayout ll_bottom;

    private ShopCartGoodListAdapter shopCartGoodListAdapter;
    private ShopCartLoseEfficacyGoodListAdapter shopCartLoseEfficacyGoodListAdapter;
    private ShopCartSpecialGoodListAdapter shopCartSpecialGoodListAdapter;

    private LongSparseArray<ShopCartGoodEntity> mCheckedGoodMap = new LongSparseArray<>();

    private boolean mIsAllCheck = true;

    @Override
    protected int getLayoutId() {
        return R.layout.layout_fragment_shop_cart;
    }

    @Override
    protected ShopCartPresenter createP(Fragment context) {
        return new ShopCartPresenter();
    }

    @Override
    protected void initWidget(View root) {
        isUpdateStatusBar = true;
        setStatusBarVisibility(true);
        setStatusBarColor(getResources().getColor(R.color.white));
        setTitle("购物车");
        initRecyclerView();
    }

    @Override
    public void onResume() {
        super.onResume();
        BarUtils.setStatusBarLightMode(getActivity(), true);
        if (!isHidden()) {
            getData();
        }
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        if (!hidden) {
            isUpdateStatusBar = true;
            BarUtils.setStatusBarLightMode(getActivity(), true);
            getData();
        } else {
            isUpdateStatusBar = false;
        }
        super.onHiddenChanged(hidden);
    }

    private void getData() {
        mPresenter.getShopCartSpecialGoodList();
        if (SessionUtil.getInstance().isLogined()) {
            mPresenter.getShopCartGoodList();
        } else {
            rv_good_list.setVisibility(View.GONE);
//            shopCartGoodListAdapter.setData(new ArrayList<>(0));
            rv_lose_efficacy_good_list.setVisibility(View.GONE);
//            shopCartLoseEfficacyGoodListAdapter.setData(new ArrayList<>(0));
            ll_no_data.setVisibility(View.VISIBLE);

            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            params.setMargins(0, 0, 0, ConvertUtils.dp2px(10));
            osv_container.setLayoutParams(params);
            ll_bottom.setVisibility(View.GONE);
        }
    }

    @Override
    public void showLoading() {
        showLoadingDialog();
    }

    @Override
    public void hideLoading() {
        dissmissLoadingDialog();
    }

    private void initRecyclerView() {
        shopCartGoodListAdapter = new ShopCartGoodListAdapter(mContext, new ShopCartGoodListAdapter.ShopCartOperationListener() {
            @Override
            public void onGoodCollectClick(String goodId) {
                mPresenter.collecGood(goodId);
            }

            @Override
            public void onGoodDeleteClick(String shopcartId) {
                mPresenter.deleteGood(shopcartId);
                mCheckedGoodMap.remove(Long.parseLong(shopcartId));
            }

            @Override
            public void onGoodNumChange(String cartId, int count) {
                mPresenter.goodCountChange(cartId, count);
            }

            @Override
            public void onCheckGoodClick(ShopCartGoodEntity entity) {
                if (entity.getSelected() == 0) {
                    mCheckedGoodMap.put(entity.getId(), entity);
                    mPresenter.goodChecked(entity.getId() + "");
                } else {
                    mCheckedGoodMap.remove(entity.getId());
                    mPresenter.goodUnCheck(entity.getId() + "");
                }
            }
        });
        rv_good_list.setNestedScrollingEnabled(false);
        rv_good_list.addItemDecoration(new DecorationLLM(mContext, DecorationLLM.VERTICAL_LIST, R.drawable.shape_recyclerview_divider, ConvertUtils.dp2px(15)));
        rv_good_list.setLayoutManager(new LinearLayoutManager(mContext));
        rv_good_list.setAdapter(shopCartGoodListAdapter);

        shopCartLoseEfficacyGoodListAdapter = new ShopCartLoseEfficacyGoodListAdapter(mContext, new ShopCartLoseEfficacyGoodListAdapter.ShopCartLoseEfficacyOperationListener() {
            @Override
            public void onClearAllGood() {

            }
        });
        rv_lose_efficacy_good_list.setNestedScrollingEnabled(false);
        rv_lose_efficacy_good_list.setLayoutManager(new LinearLayoutManager(mContext));
        rv_lose_efficacy_good_list.setAdapter(shopCartLoseEfficacyGoodListAdapter);

        shopCartSpecialGoodListAdapter = new ShopCartSpecialGoodListAdapter(mContext);
        GridLayoutManager mLayoutManager = new GridLayoutManager(mContext, 2);
        mLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                if (shopCartSpecialGoodListAdapter.getItemViewType(position) == ShopCartSpecialGoodListAdapter.ITEM_TOP) {
                    return mLayoutManager.getSpanCount();
                }
                return 1;
            }
        });
        rv_special_good_list.setNestedScrollingEnabled(false);
        rv_special_good_list.addItemDecoration(new ShopCartSpecialGoodItemDecoration());
        rv_special_good_list.setItemAnimator(null);//设置动画为null来解决闪烁问题
        rv_special_good_list.setLayoutManager(mLayoutManager);
        rv_special_good_list.setAdapter(shopCartSpecialGoodListAdapter);
    }

    @OnClick({R.id.tv_go_main, R.id.cb_statu, R.id.tv_all_check, R.id.tv_go_buy})
    public void onClickByButterKnife(View v) {
        switch (v.getId()) {
            case R.id.tv_go_main:
                EventBusUtil.sendEvent(new GoToMainEvent());
                break;
            case R.id.cb_statu:
            case R.id.tv_all_check:
                mPresenter.goodAllCheckOrNot(mIsAllCheck ? 2 : 1);
                break;
            case R.id.tv_go_buy:
                if (!checkLogin()) return;
                if (mCheckedGoodMap.size() > 0) {
                    HashMap map = new HashMap();
                    map.put("buy_type", 2);
                    NavigationUtil.overlay(mContext, SOActivity.class, map);
                } else {
                    ToastUtils.showShort("请选择要购买的商品");
                }
                break;
        }
    }

    @Override
    public void onShowShopCartListDataToView(List<ShopCartGoodEntity> list) {
        if (null != list && !list.isEmpty()) {
            ArrayList<ShopCartGoodEntity> goodList = new ArrayList<>();
            ArrayList<ShopCartGoodEntity> loseEfficacyGoodList = new ArrayList<>();
            for (int i = 0; i < list.size(); i++) {
                ShopCartGoodEntity entity = list.get(i);
                if (entity.getIs_on_sale() == 0) {
                    goodList.add(entity);
                } else {
                    loseEfficacyGoodList.add(entity);
                }
            }
            if (goodList.size() > 0) {
                mIsAllCheck = true;
                rv_good_list.setVisibility(View.VISIBLE);
                shopCartGoodListAdapter.setData(goodList);
                for (ShopCartGoodEntity entity : list) {
                    if (entity.getSelected() == 0) {
                        mIsAllCheck = false;
                        break;
                    }
                }
                if (mIsAllCheck) {
                    for (ShopCartGoodEntity entity : list) {
                        mCheckedGoodMap.put(entity.getId(), entity);
                    }
                    cb_statu.setChecked(true);
                } else {
                    cb_statu.setChecked(false);
                    mCheckedGoodMap.clear();
                    for (ShopCartGoodEntity entity : list) {
                        if (entity.getSelected() == 1) {
                            mCheckedGoodMap.put(entity.getId(), entity);
                        }
                    }
                }
                calcGoodTotalPrice();
            } else {
                rv_good_list.setVisibility(View.GONE);
            }
            if (loseEfficacyGoodList.size() > 0) {
                rv_lose_efficacy_good_list.setVisibility(View.VISIBLE);
                shopCartLoseEfficacyGoodListAdapter.setData(loseEfficacyGoodList);
            } else {
                rv_lose_efficacy_good_list.setVisibility(View.GONE);
                shopCartLoseEfficacyGoodListAdapter.setData(new ArrayList<>(0));
            }
            ll_no_data.setVisibility(View.GONE);

            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            params.setMargins(0, 0, 0, ConvertUtils.dp2px(55));
            osv_container.setLayoutParams(params);
            ll_bottom.setVisibility(View.VISIBLE);
        } else {
            rv_good_list.setVisibility(View.GONE);
//            shopCartGoodListAdapter.setData(new ArrayList<>(0));
            rv_lose_efficacy_good_list.setVisibility(View.GONE);
//            shopCartLoseEfficacyGoodListAdapter.setData(new ArrayList<>(0));
            ll_no_data.setVisibility(View.VISIBLE);

            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            params.setMargins(0, 0, 0, ConvertUtils.dp2px(10));
            osv_container.setLayoutParams(params);
            ll_bottom.setVisibility(View.GONE);
        }
        mPresenter.calcOrderPrice();
    }

    @Override
    public void onShowShopCartSpecialListDataToView(List<HomeGoodEntity> list) {
        if (null != list && !list.isEmpty()) {
            rv_special_good_list.setVisibility(View.VISIBLE);
            shopCartSpecialGoodListAdapter.setData(list);
        } else {
            rv_special_good_list.setVisibility(View.GONE);
        }
    }

    @Override
    public void onShowDeleteGoodResultToView(boolean result) {
        if (result) {
            ToastUtils.showShort("删除成功");
            mPresenter.getShopCartGoodList();
        }
    }

    @Override
    public void onShowCollectGoodResultToView(boolean result) {
        if (result) {
            ToastUtils.showShort("收藏成功");
        }
    }

    @Override
    public void onShowGoodCountChangeResultToView(boolean result) {
        if (result) {
            mPresenter.getShopCartGoodList();
        }
    }

    @Override
    public void onShowGoodCheckedResultToView(boolean result) {
        if (result) {
            mPresenter.getShopCartGoodList();
//            calcGoodTotalPrice();
        }
    }

    @Override
    public void onShowGoodUnCheckResultToView(boolean result) {
        if (result) {
            mPresenter.getShopCartGoodList();
//            calcGoodTotalPrice();
        }
    }

    @Override
    public void onShowGoodAllCheckOrNotResultToView(boolean result) {
        if (result) {
            if (mIsAllCheck) {
                mIsAllCheck = false;
                cb_statu.setChecked(false);
            } else {
                mIsAllCheck = true;
                cb_statu.setChecked(true);
            }
        }
        mPresenter.getShopCartGoodList();
    }

    @Override
    public void onShowClearLoseEfficacyResultToView(boolean result) {
        if (result) {
            mPresenter.getShopCartGoodList();
        }
    }

    @Override
    public void onShowCalcOrderPriceResultToView(CalcOrderPriceResultEntity entity) {
        if (ObjectUtils.isNotEmpty(entity) && ObjectUtils.isNotEmpty(entity.getMsg1())) {
            tv_msg1.setVisibility(View.VISIBLE);
            tv_msg1.setText(entity.getMsg1());
        } else {
            tv_msg1.setVisibility(View.GONE);
        }
    }

    private void calcGoodTotalPrice() {
        float totalPrice = 0;
        for (int i = 0; i < mCheckedGoodMap.size(); i++) {
            ShopCartGoodEntity entity = mCheckedGoodMap.valueAt(i);
            totalPrice += Float.parseFloat(entity.getMember_goods_price()) * entity.getGoods_num();
        }
        if (totalPrice > 0) {
            tv_total.setVisibility(View.VISIBLE);
            tv_total.setText(changeTotalText("合计: ¥" + StringUtil.formatAmount(totalPrice, 2)));
        } else {
            tv_total.setVisibility(View.GONE);
            tv_total.setText("0.00");
        }
        if (mCheckedGoodMap.size() > 0) {
            tv_go_buy.setText("去结算(" + mCheckedGoodMap.size() + ")");
        } else {
            tv_go_buy.setText("去结算");
        }
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

    private SpannableStringBuilder changeTotalText(String value) {
        SpannableStringBuilder sp1 = new SpannableStringBuilder();
        sp1.append(value);

        sp1.setSpan(new AbsoluteSizeSpan(ConvertUtils.dp2px(12)), 0, value.indexOf(":") + 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        sp1.setSpan(new ForegroundColorSpan(Color.parseColor("#333333")), 0, value.indexOf(":") + 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        sp1.setSpan(new AbsoluteSizeSpan(ConvertUtils.dp2px(14)), value.indexOf(":") + 1, value.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        sp1.setSpan(new ForegroundColorSpan(Color.parseColor("#FF681D")), value.indexOf(":") + 1, value.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        return sp1;
    }
}
