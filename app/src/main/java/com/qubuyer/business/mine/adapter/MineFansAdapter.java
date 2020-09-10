package com.qubuyer.business.mine.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.util.ObjectUtils;
import com.blankj.utilcode.util.TimeUtils;
import com.qubuyer.R;
import com.qubuyer.bean.mine.MineFansEntity;
import com.qubuyer.business.mine.holder.MineFansVH;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


/**
 * @author Susong
 * @date 创建时间:2018/12/24
 * @description 我的粉丝Adapter
 * & @version
 */
public class MineFansAdapter extends RecyclerView.Adapter {
    private Context mContext;
    private List<MineFansEntity> mData;

    public MineFansAdapter(Context context) {
        mContext = context;
        mData = new ArrayList<>();
    }

    public void setData(List<MineFansEntity> list) {
        if (list != null) {
            int previousSize = mData.size();
            mData.clear();
            notifyItemRangeRemoved(0, previousSize);
            mData.addAll(list);
            notifyItemRangeInserted(0, list.size());
        }
    }

    public void addAll(List<MineFansEntity> list) {
        if (list != null) {
            int size = mData.size();
            this.mData.addAll(list);
            notifyItemRangeInserted(size, list.size());
            notifyItemRangeChanged(size, list.size());
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_mine_fans, parent, false);
        return new MineFansVH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof MineFansVH) {
            setCommonInfo((MineFansVH) holder, position);
        }
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    private void setCommonInfo(MineFansVH holder, int position) {
        if (position < 0 || position >= mData.size()) return;
        MineFansEntity entity = mData.get(position);
        holder.iv_img.setUriRoundCornerImage(mContext, entity.getHead_pic(), 5);
        holder.tv_name.setText(ObjectUtils.isNotEmpty(entity.getNickname()) ? entity.getNickname() : "");
        holder.tv_id.setText(String.format(Locale.CHINA, "ID: %d", entity.getUser_id()));
        holder.tv_price.setText(String.valueOf(entity.getMoney()));
        holder.tv_time.setText(TimeUtils.millis2String(entity.getReg_time() * 1000, "yyyy-MM-dd HH:mm:ss"));
    }
}
