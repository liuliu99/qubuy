package com.qubuyer.business.mine.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.qubuyer.R;
import com.qubuyer.base.activity.BaseActivity;
import com.qubuyer.bean.ToolbarMenuEntity;
import com.qubuyer.bean.event.SOSelectAddressEvent;
import com.qubuyer.bean.mine.MineAddressEntitiy;
import com.qubuyer.business.mine.adapter.AddressListAdapter;
import com.qubuyer.business.mine.presenter.AddressListPresenter;
import com.qubuyer.business.mine.view.IAddressListView;
import com.qubuyer.constant.AppConstant;
import com.qubuyer.customview.AbsToolbar;
import com.qubuyer.customview.RecyclerViewDivider;
import com.qubuyer.utils.DialogUtil;
import com.qubuyer.utils.NavigationUtil;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;

/**
 * @author Susong
 * @date 创建时间2019/3/27
 * @description 地址列表管理界面
 * @version
 */
public class AddressListActivity extends BaseActivity<AddressListPresenter> implements IAddressListView {
    @BindView(R.id.address_listView)
    RecyclerView addressListView;
    @BindView(R.id.ll_no_data)
    FrameLayout llNoData;
    @BindView(R.id.btn_address)
    TextView btnAddress;
    @BindView(R.id.layout_list)
    RelativeLayout layout_list;

    private AddressListAdapter mAdapter;
    private int currentPage = 1;
    private int totalPage;
    private String mUserId;

    private ToolbarMenuEntity mToolbarMenuEntity;
    private List<ToolbarMenuEntity> mToolbarMenuEntityList;
    private List<MineAddressEntitiy> mdata;

    private boolean mIsClosePage;

    @Override
    protected int getContentView() {
        return R.layout.layout_activity_address_list;
    }

    @Override
    protected void initWidget(Bundle savedInstanceState) {
        super.initWidget(savedInstanceState);
        if (null != getIntent() && null != getIntent().getSerializableExtra(AppConstant.INTENT_EXTRA_KEY)) {
            mIsClosePage = (boolean) getIntent().getSerializableExtra(AppConstant.INTENT_EXTRA_KEY);
        }
        setTitle("收货地址");
        mdata = new ArrayList<>();
        mAdapter = new AddressListAdapter(this, mdata);
        addressListView.setAdapter(mAdapter);
        //设置listview垂直如何显示
        addressListView.setLayoutManager(new LinearLayoutManager(this));
        addressListView.addItemDecoration(new RecyclerViewDivider(
                this, LinearLayoutManager.HORIZONTAL, 20, getResources().getColor(R.color.common_bg)));
        //recyclerView点击事件
        mAdapter.setOnItemClickListener(new AddressListAdapter.OnItemClickListener() {

            @Override
            public void setDefultAddressOnClick(View holder, int position) {
                mPresenter.setDefAddress(mdata.get(position).getAddress_id() + "");
            }

            @Override
            public void setEditAddressOnClick(View holder, int position) {
                NavigationUtil.overlay(AddressListActivity.this, AddressEditActivity.class, mdata.get(position));
            }

            @Override
            public void setDelAddressOnClick(View holder, int position) {
                android.app.AlertDialog dialog = DialogUtil.getConfirmDialog(AddressListActivity.this, "确定删除该地址吗？", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mPresenter.delAddress(mdata.get(position).getAddress_id() + "");
                    }
                }).create();
                dialog.show();
            }

            @Override
            public void setItemOnClick(MineAddressEntitiy entitiy) {
                EventBus.getDefault().post(new SOSelectAddressEvent(entitiy));
                if (mIsClosePage) {
                    finish();
                }
            }
        });

        btnAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavigationUtil.overlay(AddressListActivity.this, AddressEditActivity.class);
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.requestAdressList("1", "15");
    }

    @Override
    protected void initData() {
        super.initData();
    }

    @Override
    protected AddressListPresenter createP(Context context) {
        return new AddressListPresenter();
    }

    @Override
    public void setAddressView(List<MineAddressEntitiy> data) {
        if (data != null && data.size() > 0) {
            mdata = data;
            mAdapter.setDatas(mdata);
            layout_list.setVisibility(View.VISIBLE);
            llNoData.setVisibility(View.GONE);
        } else {
            layout_list.setVisibility(View.GONE);
            llNoData.setVisibility(View.VISIBLE);
            EventBus.getDefault().post(new SOSelectAddressEvent());
        }
    }

    @Override
    public void loadListView() {
        mPresenter.requestAdressList("1", "15");
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

}
