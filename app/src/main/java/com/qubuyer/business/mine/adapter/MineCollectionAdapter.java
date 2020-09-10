package com.qubuyer.business.mine.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mcxtzhang.swipemenulib.SwipeMenuLayout;
import com.qubuyer.R;
import com.qubuyer.bean.mine.MineCollectEntity;
import com.qubuyer.business.good.activity.GoodDetailActivity;
import com.qubuyer.business.mine.holder.MineCollectionVH;
import com.qubuyer.utils.NavigationUtil;
import com.qubuyer.utils.StringUtil;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


/**
 * @author Susong
 * @date 创建时间:2018/12/24
 * @description 我的收藏Adapter
 * & @version
 */
public class MineCollectionAdapter extends RecyclerView.Adapter {
    private Context mContext;
    private List<MineCollectEntity> mData;
    private OnCollectionOperationListener mOnCollectionOperationListener;

    public MineCollectionAdapter(Context context, OnCollectionOperationListener onCollectionOperationListener) {
        mContext = context;
        mData = new ArrayList<>();
        this.mOnCollectionOperationListener = onCollectionOperationListener;
    }

    public void setData(List<MineCollectEntity> list) {
        if (list != null) {
            int previousSize = mData.size();
            mData.clear();
            notifyItemRangeRemoved(0, previousSize);
            mData.addAll(list);
            notifyItemRangeInserted(0, list.size());
        }
    }

    public void addAll(List<MineCollectEntity> data) {
        List<MineCollectEntity> list = new ArrayList<>();
        if (data != null) {
            int size = mData.size();
            this.mData.addAll(list);
            notifyItemRangeInserted(size, list.size());
            notifyItemRangeChanged(size, list.size());
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_mine_collection, parent, false);
        return new MineCollectionVH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof MineCollectionVH) {
            setCommonInfo((MineCollectionVH) holder, position);
        }
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    private void setCommonInfo(MineCollectionVH holder, int position) {
        MineCollectEntity goodsEntity = mData.get(position);
//        holder.iv_img.setDrawableIdRoundCornerImage(mContext, R.mipmap.ic_logo, 5);
        holder.iv_img.setUriRoundCornerImage(mContext, goodsEntity.getGoods().getOriginal_img(), 5);
        holder.tv_title.setText(goodsEntity.getGoods().getGoods_name());
        holder.tv_good_price.setText("¥" + StringUtil.formatAmount(goodsEntity.getShop_price(), 2));
        holder.tv_dis_price.setText("折让：" + StringUtil.formatAmount(goodsEntity.getRestrore_price(), 2));
        holder.iv_add_shop_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavigationUtil.overlay(mContext, GoodDetailActivity.class, goodsEntity.getGoods().getGoods_id());
            }
        });
        ((SwipeMenuLayout) holder.itemView).setIos(false).setLeftSwipe(true).setSwipeEnable(true);
        holder.btn_share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mOnCollectionOperationListener) {
                    mOnCollectionOperationListener.onShareItemListener(goodsEntity.getGoods());
                }
            }
        });
        holder.btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mOnCollectionOperationListener) {
                    mOnCollectionOperationListener.onDeleteItemListener(goodsEntity);
                }
            }
        });
        holder.rl_container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavigationUtil.overlay(mContext, GoodDetailActivity.class, goodsEntity.getGoods().getGoods_id());
            }
        });
    }

    public interface OnCollectionOperationListener {
        void onDeleteItemListener(MineCollectEntity entity);
        void onShareItemListener(MineCollectEntity.GoodsBean entity);
    }
}
