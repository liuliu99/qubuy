package com.qubuyer.business.mine.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.qubuyer.R;
import com.qubuyer.customview.ImageViewAutoLoad;

import java.io.File;
import java.util.List;

/**
 * @author Susong
 * @date 创建时间:2019/4/19
 * @description 添加上传图片适配器
 * & @version
 */
public class PicGridViewAdpter extends BaseAdapter {
    private List<String> datas;
    private Context context;
    private LayoutInflater inflater;

    private boolean isNeedAdd;
    /**
     * 可以动态设置最多上传几张，之后就不显示+号了，用户也无法上传了
     * 默认9张
     */
    private int maxImages = 100;

    private OnPicOperationListener mListener;

    public PicGridViewAdpter(List<String> datas, boolean isNeedAdd, Context context, OnPicOperationListener listener) {
        this.datas = datas;
        this.context = context;
        this.isNeedAdd = isNeedAdd;
        this.mListener = listener;
        inflater = LayoutInflater.from(context);
    }

    public void setDatas(List<String> datas) {
        this.datas = datas;
        notifyDataSetChanged();
    }

    public List<String> getDatas() {
        return this.datas;
    }

    public void isNeedAdd(boolean isNeedAdd) {
        this.isNeedAdd = isNeedAdd;
        notifyDataSetChanged();
    }

    /**
     * 获取最大上传张数
     *
     * @return
     */
    public int getMaxImages() {
        return maxImages;
    }

    /**
     * 设置最大上传张数
     *
     * @param maxImages
     */
    public void setMaxImages(int maxImages) {
        this.maxImages = maxImages;
    }

    /**
     * 让GridView中的数据数目加1最后一个显示+号
     *
     * @return 返回GridView中的数量
     */
    @Override
    public int getCount() {
        if (!isNeedAdd) {
            return datas.size();
        }
        int count = datas == null ? 1 : datas.size() + 1;
        if (count >= maxImages) {
            return datas.size();
        } else {
            return count;
        }
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    public void notifyDataSetChanged(List<String> datas) {
        this.datas = datas;
        this.notifyDataSetChanged();
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_published_grida, parent, false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        if (datas != null && position < datas.size()) {
            final File file = new File(datas.get(position));
            if (file.exists()) {
                Glide.with(context).load(file).into(viewHolder.ivimage);
            } else {
                Glide.with(context).load(datas.get(position)).into(viewHolder.ivimage);
            }
            viewHolder.btdel.setVisibility(View.VISIBLE);
            viewHolder.btdel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    datas.remove(position);
                    isNeedAdd = true;
                    notifyDataSetChanged();
                    if (null != mListener) {
                        mListener.onDelClick(position);
                    }
                }
            });
        } else {
            if (isNeedAdd) {
                Glide.with(context).load(R.drawable.icon_add_pic).into(viewHolder.ivimage);
                viewHolder.ivimage.setScaleType(ImageView.ScaleType.FIT_XY);
                viewHolder.btdel.setVisibility(View.GONE);
                viewHolder.ivimage.setVisibility(View.VISIBLE);
            } else {
                viewHolder.ivimage.setVisibility(View.GONE);
                viewHolder.btdel.setVisibility(View.GONE);
            }
        }
        return convertView;
    }

    public class ViewHolder {
        final View root;
        final ImageViewAutoLoad ivimage;
        final ImageViewAutoLoad btdel;

        public ViewHolder(View root) {
            this.root = root;
            ivimage = root.findViewById(R.id.iv_image);
            btdel = root.findViewById(R.id.bt_del);
        }
    }

    public interface OnPicOperationListener{
        void onDelClick(int postion);
    }
}
