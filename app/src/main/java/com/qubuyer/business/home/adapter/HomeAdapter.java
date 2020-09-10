package com.qubuyer.business.home.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.blankj.utilcode.util.ConvertUtils;
import com.google.android.material.tabs.TabLayout;
import com.qubuyer.R;
import com.qubuyer.bean.home.HomeBannerEntity;
import com.qubuyer.bean.home.HomeCategoryEntity;
import com.qubuyer.bean.home.HomeGoodEntity;
import com.qubuyer.bean.home.HomeSaleTopEntity;
import com.qubuyer.bean.mine.UserEntity;
import com.qubuyer.business.good.activity.GoodDetailActivity;
import com.qubuyer.business.home.activity.LimitBuyActivity;
import com.qubuyer.business.home.activity.SecondGoodListActivity;
import com.qubuyer.business.home.holder.HomeBannerVH;
import com.qubuyer.business.home.holder.HomeCategoryVH;
import com.qubuyer.business.home.holder.HomeDailyDiscountVH;
import com.qubuyer.business.home.holder.HomeGoodFootVH;
import com.qubuyer.business.home.holder.HomeGoodInfoVH;
import com.qubuyer.business.home.holder.HomeGoodTopVH;
import com.qubuyer.business.home.holder.HomeSaleTopVH;
import com.qubuyer.business.home.holder.HomeSpecialVH;
import com.qubuyer.business.home.holder.HomeSuperReturnVH;
import com.qubuyer.business.home.loader.HomeBannerLoader;
import com.qubuyer.business.home.view.HomeCategoryViewPage;
import com.qubuyer.business.home.view.HomeDailyDiscountViewPage;
import com.qubuyer.business.home.view.HomeSaleTopViewPage;
import com.qubuyer.business.home.view.HomeSuperReturnViewPage;
import com.qubuyer.business.login.activity.LoginActivity;
import com.qubuyer.business.mine.activity.MineWalletActivity;
import com.qubuyer.business.mine.activity.SaleAmountActivity;
import com.qubuyer.constant.NetConstant;
import com.qubuyer.customview.BrowserActivity;
import com.qubuyer.utils.DialogUtil;
import com.qubuyer.utils.NavigationUtil;
import com.qubuyer.utils.PageUtil;
import com.qubuyer.utils.SessionUtil;
import com.qubuyer.utils.StringUtil;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;
import com.youth.banner.listener.OnBannerListener;

import java.util.ArrayList;
import java.util.List;


/**
 * @author Susong
 * @date 创建时间2019/3/9
 * @description 首页Adapter
 */
public class HomeAdapter extends RecyclerView.Adapter {
    private static final int ITEM_BANNER = 0x01;
    private static final int ITEM_CATEGORY = 0x02;
    private static final int ITEM_SALETOP = 0x05;
    private static final int ITEM_GOODTOP = 0x07;
    public static final int ITEM_INFO = 0x06;
    public static final int ITEM_FOOT = 0x08;
    public static final int ITEM_SPECIAL = 0x09;
    public static final int ITEM_SUPER_RETURN = 0x10;
    public static final int ITEM_DAILYDISCOUNT = 0x11;

    private Context mContext;
    //banner轮播图地址集合
    private List<HomeBannerEntity> mBannerList;
    //分类集合
    private List<HomeCategoryEntity> mCategoryList;
    //销售排行榜
    private HomeSaleTopEntity mSaleTop;
    //我的财富用户金额信息
    private UserEntity mUserEntity;
    //会员精选
    private List<HomeGoodEntity> mChoiceness;
    //每日限量
    private List<HomeGoodEntity> mLimit;
    //热门推荐
    private List<HomeGoodEntity> mSpecial;
    //新品首发
    private List<HomeGoodEntity> mFirstpublish;
    //超级返
    private List<HomeGoodEntity> mSuperReturn;
    //每日折扣
    private List<HomeGoodEntity> mDailyDiscount;
    //商品集合
    private List<HomeGoodEntity> mData;

    private List<View> mCategoryIndicatorList;
    private List<View> mSuperReturnIndicatorList;
    private List<View> mDailyDiscountIndicatorList;

    private int mCategoryLastPosition = 0;
    private int mSuperReturnLastPosition = 0;
    private int mDailyDiscountLastPosition = 0;

    private OnGoodTopClickListener mOnGoodTopClickListener;

    public HomeAdapter(Context context, OnGoodTopClickListener onGoodTopClickListener) {
        mContext = context;
        mBannerList = new ArrayList<>();
        mCategoryList = new ArrayList<>();
        mData = new ArrayList<>();
        mCategoryIndicatorList = new ArrayList<>();
        mSuperReturnIndicatorList = new ArrayList<>();
        mDailyDiscountIndicatorList = new ArrayList<>();
        mOnGoodTopClickListener = onGoodTopClickListener;
    }

    public void setData(List<HomeGoodEntity> data) {
        if (data != null) {
            int previousSize = mData.size();
            mData.clear();
            notifyItemRangeRemoved(7, previousSize);
            mData.addAll(data);
            notifyItemRangeInserted(7, mData.size());
        }
    }

    public void setClickData(List<HomeGoodEntity> data) {
        if (data != null) {
            int previousSize = mData.size();
            mData.clear();
            notifyItemRangeRemoved(4, previousSize);
            mData.addAll(data);
            notifyItemRangeInserted(4, mData.size());
        }
    }

    public void addAll(List<HomeGoodEntity> contents) {
        if (contents != null) {
            int size = mData.size();
            size += 7;
            this.mData.addAll(contents);
            notifyItemRangeInserted(size, contents.size());
            notifyItemRangeChanged(size, contents.size());
        }
    }

    public void setBannerData(@NonNull List<HomeBannerEntity> bannerEntityList) {
        mBannerList.clear();
        mBannerList.addAll(bannerEntityList);
        notifyItemChanged(0);
    }

    public void setCategoryData(@NonNull List<HomeCategoryEntity> categoryEntityList) {
        mCategoryList.clear();
        mCategoryList.addAll(categoryEntityList);
        notifyItemChanged(1);
    }

    public void setSaleTop(@NonNull HomeSaleTopEntity homeSaleTopEntity) {
        mSaleTop = homeSaleTopEntity;
        notifyItemChanged(2);
    }

    public void setUserEntity(@NonNull UserEntity userEntity) {
        mUserEntity = userEntity;
        notifyItemChanged(2);
    }

    public void setChoiceness(List<HomeGoodEntity> mChoiceness) {
        this.mChoiceness = mChoiceness;
    }

    public void setLimit(List<HomeGoodEntity> mLimit) {
        this.mLimit = mLimit;
    }

    public void setSpecial(List<HomeGoodEntity> mSpecial) {
        this.mSpecial = mSpecial;
    }

    public void setFirstpublish(List<HomeGoodEntity> mFirstpublish) {
        this.mFirstpublish = mFirstpublish;
        notifyItemChanged(3);
    }

    public void setSuperReturn(List<HomeGoodEntity> mSuperReturn) {
        this.mSuperReturn = mSuperReturn;
        notifyItemChanged(4);
    }

    public void setDailyDiscount(List<HomeGoodEntity> mDailyDiscount) {
        this.mDailyDiscount = mDailyDiscount;
        notifyItemChanged(5);
    }

    public List<HomeGoodEntity> getData() {
        return mData;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        switch (viewType) {
            case ITEM_BANNER:
                view = LayoutInflater.from(mContext).inflate(R.layout.item_home_banner, parent, false);
                return new HomeBannerVH(view);
            case ITEM_CATEGORY:
                view = LayoutInflater.from(mContext).inflate(R.layout.item_home_category, parent, false);
                return new HomeCategoryVH(view);
            case ITEM_SALETOP:
                view = LayoutInflater.from(mContext).inflate(R.layout.item_home_sale_top, parent, false);
                return new HomeSaleTopVH(view);
            case ITEM_SPECIAL:
                view = LayoutInflater.from(mContext).inflate(R.layout.item_home_special, parent, false);
                return new HomeSpecialVH(view);
            case ITEM_SUPER_RETURN:
                view = LayoutInflater.from(mContext).inflate(R.layout.item_home_super_return, parent, false);
                return new HomeSuperReturnVH(view);
            case ITEM_DAILYDISCOUNT:
                view = LayoutInflater.from(mContext).inflate(R.layout.item_home_dailydiscount, parent, false);
                return new HomeDailyDiscountVH(view);
            case ITEM_GOODTOP:
                view = LayoutInflater.from(mContext).inflate(R.layout.item_home_good_top1, parent, false);
                return new HomeGoodTopVH(view);
            case ITEM_FOOT:
                view = LayoutInflater.from(mContext).inflate(R.layout.item_home_good_foot, parent, false);
                return new HomeGoodFootVH(view);
            default:
                view = LayoutInflater.from(mContext).inflate(R.layout.item_home_good_info, parent, false);
                return new HomeGoodInfoVH(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof HomeBannerVH) {
            setBannerView((HomeBannerVH) holder);
        } else if (holder instanceof HomeCategoryVH) {
            setCategoryView((HomeCategoryVH) holder);
        } else if (holder instanceof HomeSaleTopVH) {
            setSaleTopView((HomeSaleTopVH) holder);
        } else if (holder instanceof HomeSpecialVH) {
            setSpecialView((HomeSpecialVH) holder);
        } else if (holder instanceof HomeSuperReturnVH) {
            setSuperReturnView((HomeSuperReturnVH) holder);
        } else if (holder instanceof HomeDailyDiscountVH) {
            setDailyDiscountView((HomeDailyDiscountVH) holder);
        } else if (holder instanceof HomeGoodTopVH) {
            setGoodTopView((HomeGoodTopVH) holder);
        } else if (holder instanceof HomeGoodInfoVH) {
            setCommonInfo((HomeGoodInfoVH) holder, position - 7);
        } else if (holder instanceof HomeGoodFootVH) {
            setItemFoot((HomeGoodFootVH) holder);
        }
    }

    @Override
    public int getItemViewType(int position) {
//        if (position - 3 >= mData.size()) {
//            return ITEM_FOOT;
//        }
        switch (position) {
            case 0:
                return ITEM_BANNER;
            case 1:
                return ITEM_CATEGORY;
            case 2:
                return ITEM_SALETOP;
            case 3:
                return ITEM_SPECIAL;
            case 4:
                return ITEM_SUPER_RETURN;
            case 5:
                return ITEM_DAILYDISCOUNT;
            case 6:
                return ITEM_GOODTOP;
            default:
                return ITEM_INFO;
        }
    }

    @Override
    public int getItemCount() {
        return 7 + mData.size();
    }

    private void setBannerView(HomeBannerVH holder) {
        FrameLayout.LayoutParams layoutParams;
        if (null != mBannerList && !mBannerList.isEmpty()) {
            layoutParams = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            holder.fl_container.setVisibility(View.VISIBLE);
            Banner banner = holder.banner;
            banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR);
            //设置图片加载器
            banner.setImageLoader(new HomeBannerLoader());
            //设置banner动画效果
            banner.setBannerAnimation(Transformer.Default);
            //设置自动轮播，默认为true
            banner.isAutoPlay(true);
            //设置轮播时间
            banner.setDelayTime(2500);
            banner.setOffscreenPageLimit(mBannerList.size());
            //设置指示器位置（当banner模式中有指示器时）
            banner.setIndicatorGravity(BannerConfig.CENTER);
            //设置图片集合
            banner.setImages(mBannerList);
            banner.setOnBannerListener(new OnBannerListener() {
                @Override
                public void OnBannerClick(int position) {
//                    ToastUtils.showShort(mBannerList.get(position).getAd_link());
                    if (null == mBannerList.get(position).getGoods_id() || 0 == mBannerList.get(position).getGoods_id()) return;
//                    Intent intent = new Intent(mContext, BrowserActivity.class);
//                    intent.putExtra(BrowserActivity.KEY_URL, mBannerList.get(position).getAd_link());
//                    mContext.startActivity(intent);
                    NavigationUtil.overlay(mContext, GoodDetailActivity.class, mBannerList.get(position).getGoods_id());
                }
            });
            banner.start();
        } else {
            layoutParams = new FrameLayout.LayoutParams(0, 0);
            holder.fl_container.setVisibility(View.GONE);
        }
        holder.fl_container.setLayoutParams(layoutParams);
    }

    private void setCategoryView(HomeCategoryVH holder) {
        LinearLayout.LayoutParams layoutParams;
        if (null != mCategoryList && !mCategoryList.isEmpty()) {
//            mCategoryList.addAll(mCategoryList);
            layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            holder.ll_container.setLayoutParams(layoutParams);
            holder.ll_container.setVisibility(View.VISIBLE);
            List<HomeCategoryViewPage> pageList = new ArrayList<>();
            PageUtil<HomeCategoryEntity> pageUtil = new PageUtil<>(mCategoryList, 10);
            mCategoryIndicatorList.clear();
            holder.ll_indicator.removeAllViews();
            if (pageUtil.getTotalPages() > 1) {
                holder.ll_indicator.setVisibility(View.VISIBLE);
                View view;
                for (int i = 0; i < pageUtil.getTotalPages(); i++) {
                    view = new View(mContext);
                    LinearLayout.LayoutParams layoutParams1 = new LinearLayout.LayoutParams(ConvertUtils.dp2px(13), ConvertUtils.dp2px(3));
                    layoutParams1.setMargins(ConvertUtils.dp2px(5), 0, ConvertUtils.dp2px(5), 0);
                    view.setLayoutParams(layoutParams1);
                    if (i == 0) {
                        view.setBackground(ContextCompat.getDrawable(mContext, R.drawable.shape_home_category_indicator_selected));
                    } else {
                        view.setBackground(ContextCompat.getDrawable(mContext, R.drawable.shape_home_category_indicator_unselect));
                    }
                    mCategoryIndicatorList.add(view);
                    holder.ll_indicator.addView(view);
                }
            } else {
                holder.ll_indicator.setVisibility(View.GONE);
            }
            for (int i = 0; i < pageUtil.getTotalPages(); i++) {
                HomeCategoryViewPage page = new HomeCategoryViewPage(mContext, pageUtil.getObjects(i + 1));
                page.getView();
                pageList.add(page);
            }
            HomeCategoryPagerAdapter mViewPagerAdapter = new HomeCategoryPagerAdapter(mContext, pageList);
            holder.vp_category.setAdapter(mViewPagerAdapter);
            holder.vp_category.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                }

                @Override
                public void onPageSelected(int position) {
                    mCategoryIndicatorList.get(mCategoryLastPosition).setBackground(ContextCompat.getDrawable(mContext, R.drawable.shape_home_category_indicator_unselect));
                    mCategoryIndicatorList.get(position).setBackground(ContextCompat.getDrawable(mContext, R.drawable.shape_home_category_indicator_selected));
                    mCategoryLastPosition = position;
                }

                @Override
                public void onPageScrollStateChanged(int state) {

                }
            });
            holder.vp_category.setCurrentItem(0);
        } else {
            layoutParams = new LinearLayout.LayoutParams(0, 0);
            holder.ll_container.setLayoutParams(layoutParams);
            holder.ll_container.setVisibility(View.GONE);
        }
    }

    private void setSaleTopView(HomeSaleTopVH holder) {
        LinearLayout.LayoutParams layoutParams;
        if (null != mSaleTop) {
            layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            layoutParams.setMargins(0, ConvertUtils.dp2px(8), 0, 0);
            holder.ll_container.setVisibility(View.VISIBLE);


            holder.ll_more.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (SessionUtil.getInstance().isLogined()) {
                        NavigationUtil.overlay(mContext, MineWalletActivity.class);
                    } else {
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
                    }
                }
            });
            holder.tv_sale_totoal_amount.setText(StringUtil.formatAmount(mSaleTop.getTotal(), 2));
            holder.tv_yesterday_sale_amount.setText(StringUtil.formatAmount(mSaleTop.getTo_total(), 2));
            if (null != mUserEntity) {
                holder.tv_rebate.setText("折让 " + mUserEntity.getRestore_money());
                holder.tv_bonus.setText("奖金 " + mUserEntity.getBouns_money());
                holder.tv_brokerage.setText("佣金 " + mUserEntity.getDistribut_money());
            } else {
                holder.tv_rebate.setText("折让 0.00");
                holder.tv_bonus.setText("奖金 0.00");
                holder.tv_brokerage.setText("佣金 0.00");
            }


            holder.ll_rule.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mContext, BrowserActivity.class);
                    intent.putExtra(BrowserActivity.KEY_URL, NetConstant.BASE_URL + "/home/html/leaderboard");
                    mContext.startActivity(intent);
                }
            });
            ArrayList<String> titleList = new ArrayList<>();
            List<HomeSaleTopViewPage> pageList = new ArrayList<>();
            if (null != mSaleTop && null != mSaleTop.getUser() && null != mSaleTop.getUser().getWeek() && !mSaleTop.getUser().getWeek().isEmpty()) {
                titleList.add("周榜");
                HomeSaleTopViewPage page = new HomeSaleTopViewPage(mContext, mSaleTop.getUser().getWeek());
                page.getView();
                pageList.add(page);
            }
            if (null != mSaleTop && null != mSaleTop.getUser() && null != mSaleTop.getUser().getMonth() && !mSaleTop.getUser().getMonth().isEmpty()) {
                titleList.add("月榜");
                HomeSaleTopViewPage page = new HomeSaleTopViewPage(mContext, mSaleTop.getUser().getMonth());
                page.getView();
                pageList.add(page);
            }
            if (null != mSaleTop && null != mSaleTop.getUser() && null != mSaleTop.getUser().getSeason() && !mSaleTop.getUser().getSeason().isEmpty()) {
                titleList.add("季榜");
                HomeSaleTopViewPage page = new HomeSaleTopViewPage(mContext, mSaleTop.getUser().getSeason());
                page.getView();
                pageList.add(page);
            }
            if (null != mSaleTop && null != mSaleTop.getUser() && null != mSaleTop.getUser().getYear() && !mSaleTop.getUser().getYear().isEmpty()) {
                titleList.add("年榜");
                HomeSaleTopViewPage page = new HomeSaleTopViewPage(mContext, mSaleTop.getUser().getYear());
                page.getView();
                pageList.add(page);
            }
            holder.etl_tab.getTabLayout().removeAllTabs();
            for (int i = 0; i < titleList.size(); i++) {
                holder.etl_tab.addTab(titleList.get(i));
            }
            HomeSaleTopPagerAdapter mViewPagerAdapter = new HomeSaleTopPagerAdapter(mContext, pageList);
            holder.vp_page.setAdapter(mViewPagerAdapter);
            holder.vp_page.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(holder.etl_tab.getTabLayout()));
            holder.etl_tab.setupWithViewPager(holder.vp_page);
        } else {
            layoutParams = new LinearLayout.LayoutParams(0, 0);
            holder.ll_container.setVisibility(View.GONE);
        }
        holder.ll_container.setLayoutParams(layoutParams);
    }

    private void setSpecialView(HomeSpecialVH holder) {
        LinearLayout.LayoutParams layoutParams;
        if (null != mChoiceness || null != mLimit || null != mSpecial || null != mFirstpublish) {
            layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            layoutParams.setMargins(0, ConvertUtils.dp2px(8), 0, 0);
            holder.ll_container.setVisibility(View.VISIBLE);

            holder.ll_choiceness.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    NavigationUtil.overlay(mContext, SecondGoodListActivity.class, 1);
                }
            });
            holder.rv_choiceness.setLayoutManager(new LinearLayoutManager(mContext, RecyclerView.HORIZONTAL, false));
            HomeSpecialAdapter homeSpecialAdapter = new HomeSpecialAdapter(mContext, mChoiceness, new HomeSpecialAdapter.OnHomeSpecialListener() {
                @Override
                public void onClickListener() {
                    NavigationUtil.overlay(mContext, SecondGoodListActivity.class, 1);
                }
            });
            holder.rv_choiceness.setAdapter(homeSpecialAdapter);

            holder.ll_limit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    NavigationUtil.overlay(mContext, SecondGoodListActivity.class, 2);
                }
            });
            holder.rv_limit.setLayoutManager(new LinearLayoutManager(mContext, RecyclerView.HORIZONTAL, false));
            homeSpecialAdapter = new HomeSpecialAdapter(mContext, mLimit, new HomeSpecialAdapter.OnHomeSpecialListener() {
                @Override
                public void onClickListener() {
                    NavigationUtil.overlay(mContext, SecondGoodListActivity.class, 2);
                }
            });
            holder.rv_limit.setAdapter(homeSpecialAdapter);

            holder.ll_special.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    NavigationUtil.overlay(mContext, SecondGoodListActivity.class, 3);
                }
            });
            holder.rv_special.setLayoutManager(new LinearLayoutManager(mContext, RecyclerView.HORIZONTAL, false));
            homeSpecialAdapter = new HomeSpecialAdapter(mContext, mSpecial, new HomeSpecialAdapter.OnHomeSpecialListener() {
                @Override
                public void onClickListener() {
                    NavigationUtil.overlay(mContext, SecondGoodListActivity.class, 3);
                }
            });
            holder.rv_special.setAdapter(homeSpecialAdapter);

            holder.ll_firstpublish.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    NavigationUtil.overlay(mContext, SecondGoodListActivity.class, 4);
                }
            });
            holder.rv_firstpublish.setLayoutManager(new LinearLayoutManager(mContext, RecyclerView.HORIZONTAL, false));
            homeSpecialAdapter = new HomeSpecialAdapter(mContext, mFirstpublish, new HomeSpecialAdapter.OnHomeSpecialListener() {
                @Override
                public void onClickListener() {
                    NavigationUtil.overlay(mContext, SecondGoodListActivity.class, 4);
                }
            });
            holder.rv_firstpublish.setAdapter(homeSpecialAdapter);
        } else {
            layoutParams = new LinearLayout.LayoutParams(0, 0);
            holder.ll_container.setVisibility(View.GONE);
        }
        holder.ll_container.setLayoutParams(layoutParams);
    }

    private void setSuperReturnView(HomeSuperReturnVH holder) {
        LinearLayout.LayoutParams layoutParams;
        if (null != mSuperReturn && !mSuperReturn.isEmpty()) {
            layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            holder.ll_container.setLayoutParams(layoutParams);
            holder.ll_container.setVisibility(View.VISIBLE);

            holder.ll_more.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    NavigationUtil.overlay(mContext, SecondGoodListActivity.class, 5);
                }
            });

            List<HomeSuperReturnViewPage> pageList = new ArrayList<>();
            PageUtil<HomeGoodEntity> pageUtil = new PageUtil<>(mSuperReturn, 3);
            mSuperReturnIndicatorList.clear();
            holder.ll_indicator.removeAllViews();
            if (pageUtil.getTotalPages() > 1) {
                holder.ll_indicator.setVisibility(View.VISIBLE);
                View view;
                for (int i = 0; i < pageUtil.getTotalPages(); i++) {
                    view = new View(mContext);
                    LinearLayout.LayoutParams layoutParams1 = new LinearLayout.LayoutParams(ConvertUtils.dp2px(7), ConvertUtils.dp2px(7));
                    layoutParams1.setMargins(ConvertUtils.dp2px(9), 0, 0, 0);
                    view.setLayoutParams(layoutParams1);
                    if (i == 0) {
                        view.setBackground(ContextCompat.getDrawable(mContext, R.drawable.shape_home_super_return_indicator_selected));
                    } else {
                        view.setBackground(ContextCompat.getDrawable(mContext, R.drawable.shape_home_super_return_indicator_unselect));
                    }
                    mSuperReturnIndicatorList.add(view);
                    holder.ll_indicator.addView(view);
                }
            } else {
                holder.ll_indicator.setVisibility(View.GONE);
            }
            for (int i = 0; i < pageUtil.getTotalPages(); i++) {
                HomeSuperReturnViewPage page = new HomeSuperReturnViewPage(mContext, pageUtil.getObjects(i + 1));
                page.getView();
                pageList.add(page);
            }
            HomeSuperReturnPagerAdapter mViewPagerAdapter = new HomeSuperReturnPagerAdapter(mContext, pageList);
            holder.vp_super_return.setAdapter(mViewPagerAdapter);
            holder.vp_super_return.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                }

                @Override
                public void onPageSelected(int position) {
                    mSuperReturnIndicatorList.get(mSuperReturnLastPosition).setBackground(ContextCompat.getDrawable(mContext, R.drawable.shape_home_super_return_indicator_unselect));
                    mSuperReturnIndicatorList.get(position).setBackground(ContextCompat.getDrawable(mContext, R.drawable.shape_home_super_return_indicator_selected));
                    mSuperReturnLastPosition = position;
                }

                @Override
                public void onPageScrollStateChanged(int state) {

                }
            });
            holder.vp_super_return.setCurrentItem(0);
        } else {
            layoutParams = new LinearLayout.LayoutParams(0, 0);
            holder.ll_container.setLayoutParams(layoutParams);
            holder.ll_container.setVisibility(View.GONE);
        }
    }

    private void setDailyDiscountView(HomeDailyDiscountVH holder) {
        LinearLayout.LayoutParams layoutParams;
        if (null != mDailyDiscount && !mDailyDiscount.isEmpty()) {
            layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            holder.ll_container.setLayoutParams(layoutParams);
            holder.ll_container.setVisibility(View.VISIBLE);

            holder.ll_more.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    NavigationUtil.overlay(mContext, SecondGoodListActivity.class, 6);
                }
            });

            List<HomeDailyDiscountViewPage> pageList = new ArrayList<>();
            PageUtil<HomeGoodEntity> pageUtil = new PageUtil<>(mDailyDiscount, 3);
            mDailyDiscountIndicatorList.clear();
            holder.ll_indicator.removeAllViews();
            if (pageUtil.getTotalPages() > 1) {
                holder.ll_indicator.setVisibility(View.VISIBLE);
                View view;
                for (int i = 0; i < pageUtil.getTotalPages(); i++) {
                    view = new View(mContext);
                    LinearLayout.LayoutParams layoutParams1 = new LinearLayout.LayoutParams(ConvertUtils.dp2px(7), ConvertUtils.dp2px(7));
                    layoutParams1.setMargins(ConvertUtils.dp2px(9), 0, 0, 0);
                    view.setLayoutParams(layoutParams1);
                    if (i == 0) {
                        view.setBackground(ContextCompat.getDrawable(mContext, R.drawable.shape_home_super_return_indicator_selected));
                    } else {
                        view.setBackground(ContextCompat.getDrawable(mContext, R.drawable.shape_home_super_return_indicator_unselect));
                    }
                    mDailyDiscountIndicatorList.add(view);
                    holder.ll_indicator.addView(view);
                }
            } else {
                holder.ll_indicator.setVisibility(View.GONE);
            }
            for (int i = 0; i < pageUtil.getTotalPages(); i++) {
                HomeDailyDiscountViewPage page = new HomeDailyDiscountViewPage(mContext, pageUtil.getObjects(i + 1));
                page.getView();
                pageList.add(page);
            }
            HomeDailyDiscountPagerAdapter mViewPagerAdapter = new HomeDailyDiscountPagerAdapter(mContext, pageList);
            holder.vp_dailydiscount.setAdapter(mViewPagerAdapter);
            holder.vp_dailydiscount.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                }

                @Override
                public void onPageSelected(int position) {
                    mDailyDiscountIndicatorList.get(mDailyDiscountLastPosition).setBackground(ContextCompat.getDrawable(mContext, R.drawable.shape_home_super_return_indicator_unselect));
                    mDailyDiscountIndicatorList.get(position).setBackground(ContextCompat.getDrawable(mContext, R.drawable.shape_home_super_return_indicator_selected));
                    mDailyDiscountLastPosition = position;
                }

                @Override
                public void onPageScrollStateChanged(int state) {

                }
            });
            holder.vp_dailydiscount.setCurrentItem(0);
        } else {
            layoutParams = new LinearLayout.LayoutParams(0, 0);
            holder.ll_container.setLayoutParams(layoutParams);
            holder.ll_container.setVisibility(View.GONE);
        }
    }

    private void setGoodTopView(HomeGoodTopVH holder) {
//        if (holder.etl_tab.getTabLayout().getTabCount() > 0) return;
//        ArrayList<String> titleList = new ArrayList<>();
//        titleList.add("超级返");
//        titleList.add("每日限量");
//        titleList.add("热门推荐");
//        holder.etl_tab.getTabLayout().removeAllTabs();
//        for (int i = 0; i < titleList.size(); i++) {
//            holder.etl_tab.addTab(titleList.get(i));
//        }
//        holder.etl_tab.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
//            @Override
//            public void onTabSelected(TabLayout.Tab tab) {
//                if (null != mOnGoodTopClickListener) {
//                    mOnGoodTopClickListener.onGoodTopClick(tab.getPosition() + 1);
//                }
//            }
//
//            @Override
//            public void onTabUnselected(TabLayout.Tab tab) {
//
//            }
//
//            @Override
//            public void onTabReselected(TabLayout.Tab tab) {
//
//            }
//        });
//        holder.etl_tab.getTabLayout().getTabAt(0).select();
    }

    private void setCommonInfo(HomeGoodInfoVH holder, int pos) {
        if (null == mData || mData.isEmpty()) return;
        HomeGoodEntity entity = mData.get(pos);
        holder.iv_photo.setUri(mContext, entity.getOriginal_img());
//        holder.iv_photo.setDrawableId(mContext, R.mipmap.ic_logo);
        holder.tv_title.setText(entity.getGoods_name());
        holder.tv_price.setText("¥" + entity.getShop_price());
        holder.tv_rebate.setText("折让" + entity.getRestrore_price());
        holder.tv_real_price.setText("¥" + entity.getDiff_price());
        if (entity.getStore_count() > 0) {
            holder.iv_sell_out.setVisibility(View.GONE);
            holder.ll_real_price.setBackground(ContextCompat.getDrawable(mContext, R.drawable.shape_home_good_real_price_bg));
            holder.tv_real_price_title.setTextColor(Color.parseColor("#FF681D"));
            holder.tv_real_price.setTextColor(Color.parseColor("#FF681D"));
            holder.ll_now_buy.setBackground(ContextCompat.getDrawable(mContext, R.drawable.shape_home_good_now_buy_bg));
            holder.tv_now_buy.setText("马上抢");
            holder.ll_now_buy.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    NavigationUtil.overlay(mContext, GoodDetailActivity.class, entity.getGoods_id());
                }
            });
        } else {
            holder.iv_sell_out.setVisibility(View.VISIBLE);
            holder.ll_real_price.setBackground(ContextCompat.getDrawable(mContext, R.drawable.shape_home_good_real_price_sell_out_bg));
            holder.tv_real_price_title.setTextColor(Color.parseColor("#999999"));
            holder.tv_real_price.setTextColor(Color.parseColor("#999999"));
            holder.ll_now_buy.setBackground(ContextCompat.getDrawable(mContext, R.drawable.shape_home_good_now_buy_sell_out_bg));
            holder.tv_now_buy.setText("到货通知");
            holder.ll_now_buy.setOnClickListener(null);
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavigationUtil.overlay(mContext, GoodDetailActivity.class, entity.getGoods_id());
            }
        });
    }

    private void setItemFoot(HomeGoodFootVH holder) {
        LinearLayout.LayoutParams layoutParams;
        if (null != mData && !mData.isEmpty()) {
            layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ConvertUtils.dp2px(40));
            layoutParams.setMargins(0, ConvertUtils.dp2px(5), 0, 0);
            holder.ll_container.setVisibility(View.VISIBLE);
            holder.ll_container.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    NavigationUtil.overlay(mContext, LimitBuyActivity.class);
                }
            });
        } else {
            layoutParams = new LinearLayout.LayoutParams(0, 0);
            holder.ll_container.setVisibility(View.GONE);
            holder.ll_container.setOnClickListener(null);
        }
        holder.ll_container.setLayoutParams(layoutParams);
    }

    public interface OnGoodTopClickListener {
        void onGoodTopClick(int goodType);
    }
}
