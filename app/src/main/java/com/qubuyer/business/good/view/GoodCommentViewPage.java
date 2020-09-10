package com.qubuyer.business.good.view;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.util.ConvertUtils;
import com.qubuyer.R;
import com.qubuyer.base.activity.BaseActivity;
import com.qubuyer.bean.good.GoodAssessEntity;
import com.qubuyer.bean.good.GoodCommentEntity;
import com.qubuyer.bean.home.HomeGoodEntity;
import com.qubuyer.bean.shopcart.ShopCartGoodEntity;
import com.qubuyer.business.good.adapter.GoodCommentAdapter;
import com.qubuyer.business.good.presenter.GoodDetailPresenter;
import com.qubuyer.customview.DecorationLLM;
import com.qubuyer.customview.ImageViewAutoLoad;
import com.qubuyer.customview.PictureZoomView;
import com.xingliuhua.xlhratingbar.XLHRatingBar;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Susong
 * @date 创建时间2019/3/24
 * @description 商品评论页
 */
public class GoodCommentViewPage extends GoodDetailBaseViewPage implements IGoodDetailView {
    //当前上下文
    private Context mContext;
    private View mView;
    private XLHRatingBar tv_rating_bar;
    private TextView tv_scoring_rate;
    private TextView tv_commment_all;
    private TextView tv_commment_good;
    private TextView tv_commment_center;
    private TextView tv_commment_bad;
    private RecyclerView rv_list;
    private ImageViewAutoLoad iv_scroll_top;

    private GoodDetailPresenter mPresenter;
    //商品ID
    private String mGoodId;
    //评论类型 0:全部 1:好评 2:中评 3:差评
    private int mCommentType;

    private int mOverallXScroll = 0;
    private int mHeight;

    private GoodCommentAdapter mAdapter;

    public GoodCommentViewPage(Context mContext, String goodId) {
        this.mContext = mContext;
        mGoodId = goodId;
        this.mHeight = ConvertUtils.dp2px(145);
        createP();
    }

    protected void createP() {
        mPresenter = new GoodDetailPresenter();
        mPresenter.attachView(this);
    }

    public View getView() {
        if (mView == null) {
            mView = LayoutInflater.from(mContext).inflate(R.layout.layout_activity_good_comment_page, null);
            tv_rating_bar = mView.findViewById(R.id.tv_rating_bar);
            tv_rating_bar.setRatingViewClassName(GoodDetailCommentRatingView.class.getName());
            tv_rating_bar.setNumStars(5);
            tv_rating_bar.setRating(5);
            tv_rating_bar.setEnabled(false);
            tv_rating_bar.setOnRatingChangeListener(new XLHRatingBar.OnRatingChangeListener() {
                @Override
                public void onChange(float rating, int numStars) {
                }
            });
            tv_rating_bar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                }
            });
            tv_scoring_rate = mView.findViewById(R.id.tv_scoring_rate);
            tv_commment_all = mView.findViewById(R.id.tv_commment_all);
            tv_commment_all.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    tv_commment_all.setBackground(ContextCompat.getDrawable(mContext, R.drawable.shape_good_comment_selected));
                    tv_commment_all.setTextColor(ContextCompat.getColor(mContext, R.color.white));

                    tv_commment_good.setBackground(null);
                    tv_commment_good.setTextColor(ContextCompat.getColor(mContext, R.color.common_text_color1));

                    tv_commment_center.setBackground(null);
                    tv_commment_center.setTextColor(ContextCompat.getColor(mContext, R.color.common_text_color1));

                    tv_commment_bad.setBackground(null);
                    tv_commment_bad.setTextColor(ContextCompat.getColor(mContext, R.color.common_text_color1));

                    mCommentType = 0;
                    mPresenter.getGoodCommentList(mGoodId, mCommentType);
                }
            });
            tv_commment_good = mView.findViewById(R.id.tv_commment_good);
            tv_commment_good.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    tv_commment_all.setBackground(null);
                    tv_commment_all.setTextColor(ContextCompat.getColor(mContext, R.color.common_text_color1));

                    tv_commment_good.setBackground(ContextCompat.getDrawable(mContext, R.drawable.shape_good_comment_selected));
                    tv_commment_good.setTextColor(ContextCompat.getColor(mContext, R.color.white));

                    tv_commment_center.setBackground(null);
                    tv_commment_center.setTextColor(ContextCompat.getColor(mContext, R.color.common_text_color1));

                    tv_commment_bad.setBackground(null);
                    tv_commment_bad.setTextColor(ContextCompat.getColor(mContext, R.color.common_text_color1));

                    mCommentType = 1;
                    mPresenter.getGoodCommentList(mGoodId, mCommentType);
                }
            });
            tv_commment_center = mView.findViewById(R.id.tv_commment_center);
            tv_commment_center.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    tv_commment_all.setBackground(null);
                    tv_commment_all.setTextColor(ContextCompat.getColor(mContext, R.color.common_text_color1));

                    tv_commment_good.setBackground(null);
                    tv_commment_good.setTextColor(ContextCompat.getColor(mContext, R.color.common_text_color1));

                    tv_commment_center.setBackground(ContextCompat.getDrawable(mContext, R.drawable.shape_good_comment_selected));
                    tv_commment_center.setTextColor(ContextCompat.getColor(mContext, R.color.white));

                    tv_commment_bad.setBackground(null);
                    tv_commment_bad.setTextColor(ContextCompat.getColor(mContext, R.color.common_text_color1));

                    mCommentType = 2;
                    mPresenter.getGoodCommentList(mGoodId, mCommentType);
                }
            });
            tv_commment_bad = mView.findViewById(R.id.tv_commment_bad);
            tv_commment_bad.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    tv_commment_all.setBackground(null);
                    tv_commment_all.setTextColor(ContextCompat.getColor(mContext, R.color.common_text_color1));

                    tv_commment_good.setBackground(null);
                    tv_commment_good.setTextColor(ContextCompat.getColor(mContext, R.color.common_text_color1));

                    tv_commment_center.setBackground(null);
                    tv_commment_center.setTextColor(ContextCompat.getColor(mContext, R.color.common_text_color1));

                    tv_commment_bad.setBackground(ContextCompat.getDrawable(mContext, R.drawable.shape_good_comment_selected));
                    tv_commment_bad.setTextColor(ContextCompat.getColor(mContext, R.color.white));

                    mCommentType = 3;
                    mPresenter.getGoodCommentList(mGoodId, mCommentType);
                }
            });
            rv_list = mView.findViewById(R.id.rv_list);
            mAdapter = new GoodCommentAdapter(mContext, new GoodCommentAdapter.OnCommentAdapterListener() {
                @Override
                public void onImgClick(int firstPosition, int secondPosition) {
                    if (null != mAdapter.getData() && !mAdapter.getData().isEmpty() && null != mAdapter.getData().get(firstPosition).getImg_full() && !mAdapter.getData().get(firstPosition).getImg_full().isEmpty()) {
                        List<String> list = new ArrayList<>();
                        for (String path : mAdapter.getData().get(firstPosition).getImg_full()) {
                            if (path.contains("?")) {
                                list.add(path.substring(0, path.indexOf("?")));
                            } else {
                                list.add(path);
                            }
                        }
                        PictureZoomView.actionStartUrl(mContext, list, secondPosition);
//                        PictureZoomView.actionStartUrl(mContext, mAdapter.getData().get(firstPosition).getImg_full(), secondPosition);
                    }
                }
            });
            rv_list.setNestedScrollingEnabled(false);
            rv_list.addItemDecoration(new DecorationLLM(mContext, DecorationLLM.VERTICAL_LIST, R.drawable.shape_recyclerview_divider, ConvertUtils.dp2px(0)));
            rv_list.setLayoutManager(new LinearLayoutManager(mContext));
            rv_list.setAdapter(mAdapter);
            rv_list.addOnScrollListener(new RecyclerView.OnScrollListener() {
                @Override
                public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                    super.onScrollStateChanged(recyclerView, newState);
                }

                @Override
                public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                    super.onScrolled(recyclerView, dx, dy);
                    mOverallXScroll += dy;
                    if (mOverallXScroll <= 0) {
                        iv_scroll_top.setVisibility(View.GONE);
                    } else if (mOverallXScroll > mHeight) {
                        iv_scroll_top.setVisibility(View.VISIBLE);
                    }
                }
            });
            iv_scroll_top = mView.findViewById(R.id.iv_scroll_top);
            iv_scroll_top.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOverallXScroll = 0;
                    rv_list.scrollToPosition(0);
                }
            });
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
        if (!TextUtils.isEmpty(mGoodId) && (null == mAdapter || mAdapter.getItemCount() == 0)) {
            mPresenter.getGoodCommentList(mGoodId, mCommentType);
            mPresenter.getGoodAssess(mGoodId);
        }
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
        mAdapter.setData(list);
    }

    @Override
    public void onShowGoodAssessToView(GoodAssessEntity entity) {
        if (null == entity) return;
        tv_scoring_rate.setText(entity.getHigh_rate() + "%好评");
        tv_commment_all.setText("全部(" + entity.getTotal_sum() + ")");
        tv_commment_good.setText("好评(" + entity.getHigh_sum() + ")");
        tv_commment_center.setText("中评(" + entity.getCenter_sum() + ")");
        tv_commment_bad.setText("差评(" + entity.getLow_sum() + ")");
    }
}


