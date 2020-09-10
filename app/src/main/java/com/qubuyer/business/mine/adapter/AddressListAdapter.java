package com.qubuyer.business.mine.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;


import com.qubuyer.R;
import com.qubuyer.bean.mine.MineAddressEntitiy;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 收货地址管理列表适配器
 */
public class AddressListAdapter extends RecyclerView.Adapter<AddressListAdapter.MyViewHolder> {

    private Context mContext;
    private List<MineAddressEntitiy> mDatas;
    private OnItemClickListener listener;
    private String mUserId;

    public AddressListAdapter(Context context, List<MineAddressEntitiy> datas) {
        mContext = context;
        mDatas = datas;
    }

    public void setDatas(List<MineAddressEntitiy> mDatas) {
        this.mDatas = mDatas;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return mDatas == null ? 0 : mDatas.size();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_address_info, parent, false);
        TypedValue typedValue = new TypedValue();
        mContext.getTheme().resolveAttribute(R.attr.selectableItemBackground, typedValue, true);
        view.setBackgroundResource(typedValue.resourceId);
        MyViewHolder viewHolder = new MyViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        MineAddressEntitiy data = mDatas.get(position);
        holder.receiverName.setText(data.getConsignee());
        holder.receiverAddress.setText(data.getAddress_area() + data.getAddress());
        holder.receiverPhone.setText(data.getMobile());
        if (data.getIs_default() == 1) {
            Drawable drawable = mContext.getResources().getDrawable(R.drawable.icon_selected);
            drawable.setBounds(0, 0, 38, 38);
            holder.tvSelected.setCompoundDrawables(drawable, null, null, null);
            holder.tvSelected.setText("默认地址");
        } else {
            Drawable drawable = mContext.getResources().getDrawable(R.drawable.unselected_icon);
            drawable.setBounds(0, 0, 38, 38);
            holder.tvSelected.setCompoundDrawables(drawable, null, null, null);
            holder.tvSelected.setText("设置为默认地址");
        }

        setOnClickListener(holder, position);
    }

    private void setOnClickListener(final MyViewHolder holder, final int position) {
        //设置默认地址事件
        holder.tvSelected.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.setDefultAddressOnClick(holder.tvSelected, position);
            }
        });
        //编辑事件
        holder.fl_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.setEditAddressOnClick(holder.tvEdit, position);
            }
        });
        //删除事件
        holder.fl_del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.setDelAddressOnClick(holder.tvDel, position);
            }
        });
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != listener) {
                    listener.setItemOnClick(mDatas.get(position));
                }
            }
        });
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public interface OnItemClickListener {
        void setDefultAddressOnClick(View holder, int position);

        void setEditAddressOnClick(View holder, int position);

        void setDelAddressOnClick(View holder, int position);

        void setItemOnClick(MineAddressEntitiy entitiy);
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.receiver_name)
        TextView receiverName;
        @BindView(R.id.receiver_phone)
        TextView receiverPhone;
        @BindView(R.id.receiver_address)
        TextView receiverAddress;

        @BindView(R.id.tv_selected)
        TextView tvSelected;
        @BindView(R.id.fl_edit)
        FrameLayout fl_edit;
        @BindView(R.id.tv_edit)
        TextView tvEdit;
        @BindView(R.id.tv_del)
        TextView tvDel;
        @BindView(R.id.fl_del)
        FrameLayout fl_del;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
