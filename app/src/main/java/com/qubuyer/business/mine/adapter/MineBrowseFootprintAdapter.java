package com.qubuyer.business.mine.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.util.ToastUtils;
import com.mcxtzhang.swipemenulib.SwipeMenuLayout;
import com.qubuyer.R;
import com.qubuyer.bean.home.HomeGoodEntity;
import com.qubuyer.bean.mine.MineBrowseFootprintEntity;
import com.qubuyer.business.good.activity.GoodDetailActivity;
import com.qubuyer.business.mine.holder.MineBrowseFootprintVH;
import com.qubuyer.utils.NavigationUtil;
import com.qubuyer.utils.StringUtil;

import java.util.ArrayList;
import java.util.List;


/**
 * @author Susong
 * @date 创建时间:2018/12/24
 * @description 我的浏览足迹Adapter
 * & @version
 */
public class MineBrowseFootprintAdapter extends RecyclerView.Adapter {
    private Context mContext;
    private List<MineBrowseFootprintEntity.ValueBean> mData;
    private OnBrowseFootprintOperationListener mOnBrowseFootprintOperationListener;

    public MineBrowseFootprintAdapter(Context context, OnBrowseFootprintOperationListener onBrowseFootprintOperationListener) {
        mContext = context;
        mData = new ArrayList<>();
        mOnBrowseFootprintOperationListener = onBrowseFootprintOperationListener;
    }

    public void setData(List<MineBrowseFootprintEntity.ValueBean> list) {
        if (list != null) {
            int previousSize = mData.size();
            mData.clear();
            notifyItemRangeRemoved(0, previousSize);
            mData.addAll(list);
            notifyItemRangeInserted(0, list.size());
        }
    }

    public void addAll(List<MineBrowseFootprintEntity.ValueBean> list) {
        if (list != null) {
            int size = mData.size();
            this.mData.addAll(list);
            notifyItemRangeInserted(size, list.size());
            notifyItemRangeChanged(size, list.size());
        }
    }

    public List<MineBrowseFootprintEntity.ValueBean> getData() {
        return mData;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_mine_browsefootprint, parent, false);
        return new MineBrowseFootprintVH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof MineBrowseFootprintVH) {
            setCommonInfo((MineBrowseFootprintVH) holder, position);
        }
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    private void setCommonInfo(MineBrowseFootprintVH holder, int position) {
        MineBrowseFootprintEntity.ValueBean valueBean = mData.get(position);
        HomeGoodEntity entity = valueBean.getGoods();

        holder.iv_img.setUriRoundCornerImage(mContext, entity.getOriginal_img(), 5);
        holder.tv_title.setText(entity.getGoods_name());
        holder.tv_good_price.setText("¥" + StringUtil.formatAmount(entity.getShop_price(), 2));
        holder.tv_dis_price.setText("折让：" + StringUtil.formatAmount(entity.getRestrore_price(), 2));
        holder.iv_add_shop_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (entity.getIs_on_sale() == 0) {
                    ToastUtils.showShort("商品不在售");
                    return;
                }
                NavigationUtil.overlay(mContext, GoodDetailActivity.class, entity.getGoods_id());
            }
        });
        ((SwipeMenuLayout) holder.itemView).setIos(false).setLeftSwipe(true).setSwipeEnable(true);
        holder.btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mOnBrowseFootprintOperationListener) {
                    mOnBrowseFootprintOperationListener.onDeleteItemListener(valueBean);
                }
            }
        });
        holder.rl_container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (entity.getIs_on_sale() == 0) {
                    ToastUtils.showShort("商品不在售");
                    return;
                }
                NavigationUtil.overlay(mContext, GoodDetailActivity.class, entity.getGoods_id());
            }
        });
//        if (ObjectUtils.isEmpty(valueBean.getTime())) {
//            HomeGoodEntity entity = valueBean.getGoods();
//            holder.iv_img.setUriRoundCornerImage(mContext, entity.getOriginal_img(), 5);
//            holder.tv_title.setText(entity.getGoods_name());
//            holder.tv_good_price.setText("¥" + StringUtil.formatAmount(entity.getShop_price(), 2));
//            holder.tv_dis_price.setText("折让：" + StringUtil.formatAmount(entity.getRestrore_price(), 2));
//            holder.iv_add_shop_cart.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    if (entity.getIs_on_sale() == 0) {
//                        ToastUtils.showShort("商品不在售");
//                        return;
//                    }
//                    NavigationUtil.overlay(mContext, GoodDetailActivity.class, entity.getGoods_id());
//                }
//            });
//            ((SwipeMenuLayout) holder.itemView).setIos(false).setLeftSwipe(true).setSwipeEnable(true);
//            holder.btn_delete.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    if (null != mOnBrowseFootprintOperationListener) {
//                        mOnBrowseFootprintOperationListener.onDeleteItemListener(valueBean);
//                    }
//                }
//            });
//            holder.rl_container.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    if (entity.getIs_on_sale() == 0) {
//                        ToastUtils.showShort("商品不在售");
//                        return;
//                    }
//                    NavigationUtil.overlay(mContext, GoodDetailActivity.class, entity.getGoods_id());
//                }
//            });
//        } else {
//            ViewGroup.LayoutParams layoutParams = holder.rl_container.getLayoutParams();
//            layoutParams.height = ConvertUtils.dp2px(0);
//            holder.rl_container.setLayoutParams(layoutParams);
//
//            ViewGroup.LayoutParams layoutParams1 = holder.sml_container.getLayoutParams();
//            layoutParams1.height = ConvertUtils.dp2px(0);
//            holder.sml_container.setLayoutParams(layoutParams1);
//            holder.sml_container.setPadding(0, 0, 0, 0);
//        }
    }

    public int getNextSortLetterPosition(int position) {
        if (mData == null || mData.isEmpty() || mData.size() <= position + 3) {
            return -1;
        }
        int resultPosition = -1;
        for (int index = position + 1; index < mData.size(); index++) {
            if (!TextUtils.isEmpty(mData.get(position).getTime()) && !mData.get(position).getTime().equals(mData.get(index).getTime())) {
                resultPosition = index;
                break;
            }
        }
        return resultPosition;
    }

    public String getSortLetters(int position) {
        if (mData == null || mData.isEmpty()) {
            return null;
        }
        return mData.get(position).getTime();
    }

    public interface OnBrowseFootprintOperationListener {
        void onDeleteItemListener(MineBrowseFootprintEntity.ValueBean entity);
    }
}
