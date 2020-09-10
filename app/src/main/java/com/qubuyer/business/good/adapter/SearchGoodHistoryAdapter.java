package com.qubuyer.business.good.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.qubuyer.R;
import com.qubuyer.bean.home.HomeGoodEntity;
import com.qubuyer.business.good.holder.SearchGoodHistoryListVH;
import com.qubuyer.business.good.operation.SearchGoodHistoryManager;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


/**
 * @date 创建时间:2019/1/15
 * @author Susong
 * @description 搜索商品历史记录Adapter
 & @version
 */
public class SearchGoodHistoryAdapter extends RecyclerView.Adapter {
    private Context mContext;
    private List<HomeGoodEntity> mData;
    private OnSearchGoodHistoryOperaListener mListener;

    public SearchGoodHistoryAdapter(Context context, OnSearchGoodHistoryOperaListener listener) {
        mContext = context;
        mListener = listener;
        mData = new ArrayList<>();
    }

    public void setData(List<HomeGoodEntity> data) {
        if (data != null && !data.isEmpty()) {
            int previousSize = mData.size();
            mData.clear();
            notifyItemRangeRemoved(0, previousSize);
            mData.addAll(data);
            notifyItemRangeChanged(0, mData.size());
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_search_good_history_list, parent, false);
        return new SearchGoodHistoryListVH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof SearchGoodHistoryListVH) {
            setCommonInfo((SearchGoodHistoryListVH) holder, position);
        }
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    private void setCommonInfo(SearchGoodHistoryListVH holder, int position) {
        HomeGoodEntity entity = mData.get(position);
        if (!TextUtils.isEmpty(entity.getGoods_name())) {
            holder.tv_text.setText(entity.getGoods_name());
        } else {
            holder.tv_text.setText("");
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HomeGoodEntity bean = mData.get(position);
                SearchGoodHistoryManager.getInstance().push(bean);
                if (null != mListener) {
                    mListener.onSearchHistoryClickListener(bean.getGoods_name());
                }
            }
        });
        holder.iv_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HomeGoodEntity bean = mData.get(position);
                if (null != mListener) {
                    mListener.onDeleteHistoryListener(bean);
                }
            }
        });
    }

    public interface OnSearchGoodHistoryOperaListener{
        void onSearchHistoryClickListener(String key);
        void onDeleteHistoryListener(HomeGoodEntity entity);
    }
}
