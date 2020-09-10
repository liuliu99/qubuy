package com.qubuyer.business.order.holder;

import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.qubuyer.R;
import com.qubuyer.business.mine.adapter.PicGridViewAdpter;
import com.qubuyer.customview.ImageSelectorGridView;
import com.qubuyer.customview.ImageViewAutoLoad;
import com.xingliuhua.xlhratingbar.XLHRatingBar;

import java.util.List;

public class OrderCommentListVH extends RecyclerView.ViewHolder {
    public ImageViewAutoLoad iv_good_img;
    public TextView tv_good_name;
    public XLHRatingBar tv_rating_bar;
    public TextView tv_rating_desc;
    public EditText et_comment_content;
    public LinearLayout ll_anonymity;
    public CheckBox cb_anonymity;
    public ImageSelectorGridView isgv_pic;
    public TextView tv_pic_count;

    public PicGridViewAdpter picGridViewAdpter;
    public List<String> picList;

    public OrderCommentListVH(View itemView) {
        super(itemView);
        iv_good_img = itemView.findViewById(R.id.iv_good_img);
        tv_good_name = itemView.findViewById(R.id.tv_good_name);
        tv_rating_bar = itemView.findViewById(R.id.tv_rating_bar);
        tv_rating_desc = itemView.findViewById(R.id.tv_rating_desc);
        et_comment_content = itemView.findViewById(R.id.et_comment_content);
        ll_anonymity = itemView.findViewById(R.id.ll_anonymity);
        cb_anonymity = itemView.findViewById(R.id.cb_anonymity);
        isgv_pic = itemView.findViewById(R.id.isgv_pic);
        tv_pic_count = itemView.findViewById(R.id.tv_pic_count);
    }
}
