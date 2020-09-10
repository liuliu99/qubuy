package com.qubuyer.business.order.adapter;

import android.content.Context;
import android.net.Uri;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.util.FileUtils;
import com.blankj.utilcode.util.Utils;
import com.qubuyer.R;
import com.qubuyer.base.activity.BaseActivity;
import com.qubuyer.bean.order.OrderEntity;
import com.qubuyer.business.mine.adapter.PicGridViewAdpter;
import com.qubuyer.business.order.holder.OrderCommentListVH;
import com.qubuyer.business.order.view.OrderCommentRatingView;
import com.qubuyer.customview.BottomUpDialog;
import com.qubuyer.customview.PictureZoomView;
import com.qubuyer.utils.DialogUtil;
import com.qubuyer.utils.FileUtil;
import com.xingliuhua.xlhratingbar.XLHRatingBar;

import java.io.File;
import java.util.ArrayList;
import java.util.List;


/**
 * @author Susong
 * @date 创建时间2019/4/1
 * @description 订单评论列表adapter
 */
public class OrderCommentListAdapter extends RecyclerView.Adapter {
    private Context mContext;
    private List<OrderEntity.SplitBean.OrderGoodBean> mData;
    private OrderCommentOperationListener mListener;

    public OrderCommentListAdapter(Context context, List<OrderEntity.SplitBean.OrderGoodBean> data, OrderCommentOperationListener listener) {
        mContext = context;
        mData = data;
        mListener = listener;
    }

    public void setData(List<OrderEntity.SplitBean.OrderGoodBean> data) {
        if (data != null) {
            int previousSize = mData.size();
            mData.clear();
            notifyItemRangeRemoved(0, previousSize);
            mData.addAll(data);
            notifyItemRangeInserted(0, mData.size());
        }
    }

    public List<OrderEntity.SplitBean.OrderGoodBean> getData() {
        return mData;
    }

    public void addAll(List<OrderEntity.SplitBean.OrderGoodBean> contents) {
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
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_order_comment_list, parent, false);
        return new OrderCommentListVH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof OrderCommentListVH) {
            setItemTicket((OrderCommentListVH) holder, position);
        }
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    private BottomUpDialog mBottomUpDialog;

    private void setItemTicket(OrderCommentListVH holder, int position) {
        OrderEntity.SplitBean.OrderGoodBean orderGoodBean = mData.get(position);
        if (null != orderGoodBean.getGoods() && !TextUtils.isEmpty(orderGoodBean.getGoods().getOriginal_img())) {
            holder.iv_good_img.setUri(mContext, orderGoodBean.getGoods().getOriginal_img());
        } else if (null != orderGoodBean.getGoods() && null != orderGoodBean.getGoods().getGoods_images() && !orderGoodBean.getGoods().getGoods_images().isEmpty()) {
            holder.iv_good_img.setUri(mContext, orderGoodBean.getGoods().getGoods_images().get(0).getOriginal_img_full());
        } else {
            holder.iv_good_img.setDrawableId(mContext, R.mipmap.ic_logo);
        }
        holder.tv_good_name.setText(orderGoodBean.getGoods_name());
        holder.tv_rating_bar.setEnabled(true);
        holder.tv_rating_bar.setRatingViewClassName(OrderCommentRatingView.class.getName());
        holder.tv_rating_bar.setNumStars(5);
        holder.tv_rating_bar.setRating(0);
        holder.tv_rating_bar.setOnRatingChangeListener(new XLHRatingBar.OnRatingChangeListener() {
            @Override
            public void onChange(float rating, int numStars) {
                switch ((int) rating) {
                    case 0:
                    case 1:
                    case 2:
                    case 3:
                        holder.tv_rating_desc.setText("一般");
                        break;
                    case 4:
                        holder.tv_rating_desc.setText("满意");
                        break;
                    case 5:
                        holder.tv_rating_desc.setText("非常满意");
                        break;
                }
            }
        });
        holder.tv_rating_desc.setText("一般");
        if (null == holder.picGridViewAdpter) {
            holder.picList = new ArrayList<>();
            holder.picGridViewAdpter = new PicGridViewAdpter(holder.picList, true, mContext, new PicGridViewAdpter.OnPicOperationListener() {
                @Override
                public void onDelClick(int position) {
                    holder.picList = holder.picGridViewAdpter.getDatas();
                    holder.tv_pic_count.setText(holder.picGridViewAdpter.getDatas().size() + "/4");
                }
            });
            holder.isgv_pic.setAdapter(holder.picGridViewAdpter);
            //图片点击
            holder.isgv_pic.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                    if (holder.picGridViewAdpter.getDatas().size() < 4 && position == (holder.picGridViewAdpter.getCount() - 1)) {
                        showPicSelectTypeDialog(holder);
                        if (null != mListener) {
                            mListener.onClickPicListener(holder);
                        }
                    } else {
                        PictureZoomView.actionStartFile(mContext, holder.picGridViewAdpter.getDatas(), position);
                    }
                }
            });
        }
        holder.ll_anonymity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (holder.cb_anonymity.isChecked()) {
                    holder.cb_anonymity.setChecked(false);
                } else {
                    holder.cb_anonymity.setChecked(true);
                }
            }
        });
    }

    private void showPicSelectTypeDialog(OrderCommentListVH holder) {
        if (null == mBottomUpDialog) {
            mBottomUpDialog = new BottomUpDialog(mContext, new String[]{"拍照", "从相册选择"}, new BottomUpDialog.OnPositionClickListener() {
                @Override
                public void onPositionClick(int position) {
                    switch (position) {
                        case 0:
                            ((BaseActivity) mContext).getTakePhoto().onPickFromCapture(getFilePath());
                            break;
                        case 1:
                            ((BaseActivity) mContext).getTakePhoto().onPickMultiple(4 - holder.picList.size());
                            break;
                    }
                    mBottomUpDialog.dismiss();
                }
            });
        }
        if (!mBottomUpDialog.isShowing()) {
            mBottomUpDialog.show();
            DialogUtil.setDialogWindowAttr(mBottomUpDialog, WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT, Gravity.BOTTOM, R.style.DialogBottomAnimation);
        }
    }

    private Uri getFilePath() {
        File fileDir = FileUtil.getOwnCacheDirectory(mContext, Utils.getApp().getPackageName() + "/cache/temp/");
        FileUtils.createOrExistsDir(fileDir);
        String name = String.valueOf(System.currentTimeMillis());
        File file = FileUtils.getFileByPath(fileDir.getAbsolutePath() + "/" + name + ".jpg");
        return Uri.fromFile(file);

//        if (FileUtils.createOrExistsDir(AppConstant.PICTURE_PATH)) {
//            String name = String.valueOf(System.currentTimeMillis());
//            File file = FileUtils.getFileByPath(AppConstant.PICTURE_PATH + name + ".jpg");
//            return Uri.fromFile(file);
//        }
//        return null;
    }

    public interface OrderCommentOperationListener{
        void onClickPicListener(OrderCommentListVH holder);
    }
}
