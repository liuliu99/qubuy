package com.qubuyer.business.shopcart.adapter;

import android.content.Context;
import android.graphics.Color;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.EditText;

import com.blankj.utilcode.util.ConvertUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.mcxtzhang.swipemenulib.SwipeMenuLayout;
import com.qubuyer.R;
import com.qubuyer.bean.shopcart.ShopCartGoodEntity;
import com.qubuyer.business.good.activity.GoodDetailActivity;
import com.qubuyer.business.shopcart.holder.ShopCartGoodListVH;
import com.qubuyer.customview.AddSubUtils;
import com.qubuyer.utils.NavigationUtil;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


/**
 * @author Susong
 * @date 创建时间2019/3/21
 * @description 购物车商品列表adapter
 */
public class ShopCartGoodListAdapter extends RecyclerView.Adapter {
    private Context mContext;
    private List<ShopCartGoodEntity> mData;
    private ShopCartOperationListener mListener;

    public ShopCartGoodListAdapter(Context context, ShopCartOperationListener listener) {
        mContext = context;
        mData = new ArrayList<>();
        mListener = listener;
    }

    public void setData(List<ShopCartGoodEntity> data) {
        if (data != null) {
            int previousSize = mData.size();
            mData.clear();
            notifyItemRangeRemoved(0, previousSize);
            mData.addAll(data);
            notifyItemRangeInserted(0, mData.size());
        }
    }

    public void addAll(List<ShopCartGoodEntity> contents) {
        if (contents != null) {
            int size = mData.size();
            this.mData.addAll(contents);
            notifyItemRangeInserted(size, contents.size());
            notifyItemRangeChanged(size, contents.size());
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_shop_cart_good_list, parent, false);
        return new ShopCartGoodListVH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ShopCartGoodListVH) {
            setCommonInfo((ShopCartGoodListVH) holder, position);
        }
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    private void setCommonInfo(ShopCartGoodListVH holder, int position) {
        ShopCartGoodEntity entity = mData.get(position);
        if (entity.getSelected() == 1) {
            holder.cb_statu.setChecked(true);
        } else {
            holder.cb_statu.setChecked(false);
        }
        holder.fl_statu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    mListener.onCheckGoodClick(entity);
                }
            }
        });
        holder.cb_statu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    mListener.onCheckGoodClick(entity);
                }
            }
        });
//        holder.cb_statu.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                if (null != mListener) {
//                    mListener.onCheckGoodClick(entity);
//                }
//            }
//        });
        if (null != entity.getGoods()) {
            holder.iv_good_img.setUri(mContext, entity.getGoods().getOriginal_img());
        } else {
            holder.iv_good_img.setDrawableId(mContext, R.mipmap.ic_logo);
        }
        holder.tv_good_title.setText(entity.getGoods_name());
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("数量: ").append(entity.getGoods_num());
        if (!TextUtils.isEmpty(entity.getSpec_key_name())) {
            stringBuilder.append("规格: ").append(entity.getSpec_key_name());
        }
        holder.tv_number_spec.setText(stringBuilder.toString());

        holder.tv_good_price.setText(changeGoodPriceText("¥" + entity.getMember_goods_price()));
        holder.asu_count.setBuyMax(99)       // 最大购买数，默认为int的最大值
                .setInventory(99)       // 库存，默认为int的最大值
                .setPosition(position)
                .setCurrentNumber(entity.getGoods_num())    // 设置当前数，默认为1
                .setStep(1)             // 步长，默认为1
                .setBuyMin(1)           // 购买的最小值，默认为1
                .setOnWarnListener(new AddSubUtils.OnWarnListener() {
                    @Override
                    public void onWarningForInventory(EditText editText, int inventory) {
                        ToastUtils.showShort("库存不足");
                    }

                    @Override
                    public void onWarningForBuyMax(EditText editText, int max) {
                        ToastUtils.showShort("超过最大购买数");
                    }

                    @Override
                    public void onWarningForBuyMin(EditText editText, int min) {
                        ToastUtils.showShort("低于最小购买数");
                    }
                })
                .setOnChangeValueListener(new AddSubUtils.OnChangeValueListener() {
            @Override
            public void onChangeValue(int value, int p) {
                if (value == mData.get(holder.asu_count.getPosition()).getGoods_num()) return;
                if (null != mListener) {
                    mListener.onGoodNumChange(entity.getId() + "", value);
                }
            }
        });
        ((SwipeMenuLayout) holder.itemView).setIos(false).setLeftSwipe(true).setSwipeEnable(true);
        holder.btn_collect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    mListener.onGoodCollectClick(entity.getGoods_id() + "");
                }
            }
        });
        holder.btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    mListener.onGoodDeleteClick(entity.getId() + "");
                }
            }
        });
        holder.rl_container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavigationUtil.overlay(mContext, GoodDetailActivity.class, entity.getGoods_id());
            }
        });
    }

    private SpannableStringBuilder changeGoodPriceText(String value) {
        SpannableStringBuilder sp1 = new SpannableStringBuilder();
        sp1.append(value);

        sp1.setSpan(new ForegroundColorSpan(Color.parseColor("#FF681D")), 0, value.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        sp1.setSpan(new AbsoluteSizeSpan(ConvertUtils.dp2px(11)), 0, value.indexOf("¥") + 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        sp1.setSpan(new AbsoluteSizeSpan(ConvertUtils.dp2px(14)), value.indexOf("¥") + 1, value.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        return sp1;
    }

    public interface ShopCartOperationListener {
        void onGoodCollectClick(String goodId);

        void onGoodDeleteClick(String shopcartId);

        void onGoodNumChange(String cartId, int count);

        void onCheckGoodClick(ShopCartGoodEntity entity);
    }
}
