package com.qubuyer.business.mine.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.util.TimeUtils;
import com.qubuyer.R;
import com.qubuyer.bean.mine.IncomeEntity;
import com.qubuyer.business.mine.holder.IncomeListVH;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;


/**
 * @author Susong
 * @date 创建时间2019/6/23
 * @description 收支明细adapter
 * @version
 */
public class IncomeAdapter extends RecyclerView.Adapter {
    private Context mContext;
    private List<IncomeEntity> mData;

    public IncomeAdapter(Context context) {
        mContext = context;
        mData = new ArrayList<>();
    }

    public void setData(List<IncomeEntity> data) {
        if (data != null) {
            int previousSize = mData.size();
            mData.clear();
            notifyItemRangeRemoved(0, previousSize);
            mData.addAll(data);
            notifyItemRangeInserted(0, mData.size());
        }
    }

    public List<IncomeEntity> getData() {
        return mData;
    }

    public void addAll(List<IncomeEntity> contents) {
        if (contents != null) {
            int size = mData.size();
            size += 0;
            this.mData.addAll(contents);
            notifyItemRangeInserted(size, contents.size());
            notifyItemRangeChanged(size, contents.size());
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_income_list, parent, false);
        return new IncomeListVH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof IncomeListVH) {
            setItemTicket((IncomeListVH) holder, position);
        }
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    private void setItemTicket(IncomeListVH holder, int position) {
        IncomeEntity entity = mData.get(position);
        holder.tv_title.setText(entity.getRemark());
        holder.tv_time.setText(TimeUtils.millis2String(entity.getCreate_time(), new SimpleDateFormat("yyyy-MM-dd HH:mm")));
        if (!entity.getMoney().contains("-")) {
            holder.tv_price.setTextColor(Color.parseColor("#ff6809"));
        } else {
            holder.tv_price.setTextColor(Color.parseColor("#424242"));
        }
        holder.tv_price.setText(entity.getMoney());
    }
}
