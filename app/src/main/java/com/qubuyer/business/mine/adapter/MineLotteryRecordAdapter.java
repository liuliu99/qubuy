package com.qubuyer.business.mine.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.qubuyer.R;
import com.qubuyer.bean.mine.MineLotteryRecordEntity;
import com.qubuyer.business.mine.holder.MineLotteryRecordVH;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Susong
 * @date 创建时间2020/1/10
 * @description 我的抽奖记录adapter
 * @version
 */
public class MineLotteryRecordAdapter extends RecyclerView.Adapter {
    private Context mContext;
    private List<MineLotteryRecordEntity> mData;
    private MineLotteryRecordAdapterListener mListener;

    public MineLotteryRecordAdapter(Context context, MineLotteryRecordAdapterListener listener) {
        mContext = context;
        mListener = listener;
        mData = new ArrayList<>(0);
    }

    public void setData(List<MineLotteryRecordEntity> data) {
        if (data != null) {
            int previousSize = mData.size();
            mData.clear();
            notifyItemRangeRemoved(0, previousSize);
            mData.addAll(data);
            notifyItemRangeChanged(0, mData.size());
        }
    }

    public List<MineLotteryRecordEntity> getData() {
        return mData;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_mine_lotteryrecord, parent, false);
        return new MineLotteryRecordVH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof MineLotteryRecordVH) {
            setCommonInfo((MineLotteryRecordVH) holder, position);
        }
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    private void setCommonInfo(MineLotteryRecordVH holder, int position) {
        MineLotteryRecordEntity entity = mData.get(position);
        holder.iv_image.setUri(mContext, entity.getMain_image_full_path());
        holder.tv_content.setText(entity.getName());
        if (entity.getIs_get() == 0) {
            holder.tv_get_lottery.setText("立即领取");
            holder.tv_get_lottery.setTextColor(Color.parseColor("#FFFFFF"));
            holder.tv_get_lottery.setBackground(ContextCompat.getDrawable(mContext, R.drawable.shape_lotteryrecord_btn_unclaimed_bg));
            holder.tv_get_lottery.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (null != mListener) {
                        mListener.onGetLottery(entity);
                    }
                }
            });
        } else {
            holder.tv_get_lottery.setText("已领取");
            holder.tv_get_lottery.setTextColor(Color.parseColor("#666666"));
            holder.tv_get_lottery.setBackground(ContextCompat.getDrawable(mContext, R.drawable.shape_lotteryrecord_btn_alreadyreceived_bg));
            holder.tv_get_lottery.setOnClickListener(null);
//            holder.tv_get_lottery.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    if (null != mListener) {
//                        mListener.onGetLottery(entity);
//                    }
//                }
//            });
        }
    }

    public interface MineLotteryRecordAdapterListener{
        void onGetLottery(MineLotteryRecordEntity entity);
    }
}
