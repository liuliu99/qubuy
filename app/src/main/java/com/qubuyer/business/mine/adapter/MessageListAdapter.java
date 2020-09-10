package com.qubuyer.business.mine.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.qubuyer.R;
import com.qubuyer.bean.mine.MessageEntity;
import com.qubuyer.bean.order.OrderEntity;
import com.qubuyer.business.mine.activity.MessageDetailActivity;
import com.qubuyer.business.mine.holder.MessageListVH;
import com.qubuyer.business.order.activity.OrderDetailActivity;
import com.qubuyer.utils.NavigationUtil;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


/**
 * @author Susong
 * @date 创建时间:2019/4/15
 * @description 消息列表adapter
 * & @version
 */
public class MessageListAdapter extends RecyclerView.Adapter {
    private Context mContext;
    private List<MessageEntity> mData;
    private int mType;

    public MessageListAdapter(Context context, int type) {
        mContext = context;
        mType = type;
        mData = new ArrayList<>();
    }

    public void setData(List<MessageEntity> data) {
        if (data != null) {
            int previousSize = mData.size();
            mData.clear();
            notifyItemRangeRemoved(0, previousSize);
            mData.addAll(data);
            notifyItemRangeInserted(0, mData.size());
        }
    }

    public List<MessageEntity> getData() {
        return mData;
    }

    public void addAll(List<MessageEntity> contents) {
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
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_message_list, parent, false);
        return new MessageListVH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof MessageListVH) {
            setItemTicket((MessageListVH) holder, position);
        }
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    private void setItemTicket(MessageListVH holder, int position) {
        MessageEntity entity = mData.get(position);
        holder.tv_title.setText(entity.getTitle());
        holder.tv_time.setText(entity.getCreate_time());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mType == 1) {
                    NavigationUtil.overlay(mContext, MessageDetailActivity.class, entity.getId());
                } else {
                    OrderEntity orderEntity = new OrderEntity();
                    orderEntity.setOrder_id(Integer.parseInt(entity.getMaster_id()));
                    orderEntity.setId(Integer.parseInt(entity.getOrder_id()));
                    NavigationUtil.overlay(mContext, OrderDetailActivity.class, orderEntity);
                }
            }
        });
    }
}
