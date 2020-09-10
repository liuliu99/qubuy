package com.qubuyer.customview;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.qubuyer.R;
import com.qubuyer.base.holder.BaseViewHolder;

/**
 * @author Susong
 * @date 创建时间:2019/3/5
 * @description 底部弹出Dialog
 * & @version
 */
public class BottomUpDialog extends BaseDialog implements AdapterView.OnItemClickListener, View.OnClickListener {
    private ListView listView;
    private String[] title;
    private OnPositionClickListener listener;
    private OnPositionClickListener2 listener2;
    private TextView btnCancel;

    public BottomUpDialog(Context context, String[] title) {
        super(context);
        this.title = title;
        setContentView();

    }

    public BottomUpDialog(Context context, String[] title, OnPositionClickListener listener) {
        super(context);
        this.title = title;
        this.listener = listener;
        setContentView();
    }

    public BottomUpDialog(Context context, String[] title, OnPositionClickListener2 listener2) {
        super(context);
        this.title = title;
        this.listener2 = listener2;
        setContentView();

    }

    public BottomUpDialog(Context context, int theme) {
        super(context, theme);
        this.title = title;
        setContentView();
    }

    private void setContentView() {
        setContentView(R.layout.bottom_up_dialog_layout);
        listView = findViewById(R.id.listview);
        btnCancel = findViewById(R.id.btnCancel);
        btnCancel.setOnClickListener(this);
//        if (title.length > 5) {
//            findViewById(R.id.balanceView).setVisibility(View.VISIBLE);
//        } else {
//            findViewById(R.id.balanceView).setVisibility(View.GONE);
//        }
        ButtomUpListAdapter mAdapter = new ButtomUpListAdapter(getContext(), title);
        listView.setAdapter(mAdapter);
        listView.setOnItemClickListener(this);
    }

    protected BottomUpDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnCancel:
                dismiss();
                break;
        }
    }

    class ButtomUpListAdapter extends ArrayAdapter<String> {

        public ButtomUpListAdapter(Context context, String[] list) {
            super(context, 0, list);
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            BaseViewHolder holder = BaseViewHolder.getViewHolder(getContext(), convertView, parent, R.layout.layout_bottom_up_dlg_item, position);
            TextView title = holder.getView(R.id.title);
            View itemView = holder.getView(R.id.itemView);
            title.setText(getItem(position));
//            if (getCount() == 1) {// 只有一条，四角都有圆角
//                itemView.setBackgroundResource(R.drawable.selector_bottom_up_item);
//            } else {
//                if (position == 0) {// 第一条顶部左右圆角
//                    itemView.setBackgroundResource(R.drawable.selector_dlg_radius_top_left_right);
//                } else if (position < getCount() - 1) {// 中间没有圆角
//                    itemView.setBackgroundResource(R.drawable.dlg_no_selector);
//                } else {// 最后一条底部圆角
//                    itemView.setBackgroundResource(R.drawable.dlg_radius_bottom_left_right_selector);
//                }
//            }
            return holder.getConvertView();
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (listener != null && title != null) {
            listener.onPositionClick(position);
        }
        if (listener2 != null && title != null) {
            listener2.onPositionClick(position, this);
        }
    }

    public interface OnPositionClickListener {
        void onPositionClick(int position);
    }

    public interface OnPositionClickListener2 {
        void onPositionClick(int position, BottomUpDialog dialog);
    }
}
