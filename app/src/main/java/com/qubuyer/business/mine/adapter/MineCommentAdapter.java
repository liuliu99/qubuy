package com.qubuyer.business.mine.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.util.ObjectUtils;
import com.blankj.utilcode.util.TimeUtils;
import com.qubuyer.R;
import com.qubuyer.bean.home.HomeGoodEntity;
import com.qubuyer.bean.mine.MineCommentEntity;
import com.qubuyer.business.good.activity.GoodDetailActivity;
import com.qubuyer.business.good.adapter.GoodCommentImgAdapter;
import com.qubuyer.business.mine.holder.MineCommentVH;
import com.qubuyer.constant.AppConstant;
import com.qubuyer.customview.GoodCommentItemDecoration;
import com.qubuyer.utils.ImageUtil;
import com.qubuyer.utils.NavigationUtil;
import com.qubuyer.utils.StringUtil;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Susong
 * @date 创建时间2020/1/9
 * @description 我的评论列表adapter
 * @version
 */
public class MineCommentAdapter extends RecyclerView.Adapter {
    private Context mContext;
    private List<MineCommentEntity> mData;
    private MineCommentAdapterListener mListener;

    public MineCommentAdapter(Context context, MineCommentAdapterListener listener) {
        mContext = context;
        mListener = listener;
        mData = new ArrayList<>(0);
    }

    public void setData(List<MineCommentEntity> data) {
        if (data != null) {
            int previousSize = mData.size();
            mData.clear();
            notifyItemRangeRemoved(0, previousSize);
            mData.addAll(data);
            notifyItemRangeChanged(0, mData.size());
        }
    }

    public List<MineCommentEntity> getData() {
        return mData;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_mine_comment, parent, false);
        return new MineCommentVH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof MineCommentVH) {
            setCommonInfo((MineCommentVH) holder, position);
        }
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    private void setCommonInfo(MineCommentVH holder, int position) {
        MineCommentEntity entity = mData.get(position);
        MineCommentEntity.UserBean userBean = entity.getUser();
        HomeGoodEntity homeGoodEntity = entity.getGoods();
        ImageUtil.loadCircleImage(mContext, ObjectUtils.isNotEmpty(userBean) ? userBean.getHead_pic() + AppConstant.PICTURE_SUFFIX : "", holder.iv_user_headimg);
//        holder.iv_user_headimg.setDrawableIdRoundCircleImage(mContext, R.mipmap.ic_logo, 0);
        holder.tv_user_name.setText(ObjectUtils.isNotEmpty(userBean) && ObjectUtils.isNotEmpty(userBean.getNickname()) ? userBean.getNickname() : "");
        holder.tv_time.setText(ObjectUtils.isNotEmpty(entity.getAdd_time()) ? TimeUtils.millis2String(entity.getAdd_time() * 1000, new SimpleDateFormat("yyyy.MM.dd")) : "");
        holder.tv_content.setText(ObjectUtils.isNotEmpty(entity.getContent()) ? entity.getContent() : "");
        holder.rv_image_list.setLayoutManager(new GridLayoutManager(mContext, 4));
        holder.rv_image_list.addItemDecoration(new GoodCommentItemDecoration());
        holder.rv_image_list.setAdapter(new GoodCommentImgAdapter(mContext, entity.getImg_full(), new GoodCommentImgAdapter.OnCommentImgAdapterListener() {
            @Override
            public void onImgClick(int position1) {
                if (null != mListener) {
                    mListener.onImgClick(position, position1);
                }
            }
        }));
        holder.iv_img.setUri(mContext, ObjectUtils.isNotEmpty(homeGoodEntity) ? homeGoodEntity.getOriginal_img() :"");
        holder.tv_title.setText(ObjectUtils.isNotEmpty(homeGoodEntity) && ObjectUtils.isNotEmpty(homeGoodEntity.getGoods_name()) ? homeGoodEntity.getGoods_name() : "");
        holder.tv_good_price.setText(String.format("¥%s", ObjectUtils.isNotEmpty(homeGoodEntity) ? StringUtil.formatAmount(homeGoodEntity.getShop_price(), 2) : ""));
        holder.rl_good_container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ObjectUtils.isEmpty(homeGoodEntity)) return;
                NavigationUtil.overlay(mContext, GoodDetailActivity.class, homeGoodEntity.getGoods_id());
            }
        });
    }

    public interface MineCommentAdapterListener{
        void onImgClick(int firstPosition, int secondPosition);
    }
}
