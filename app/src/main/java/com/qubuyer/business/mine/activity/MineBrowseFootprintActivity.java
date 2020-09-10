package com.qubuyer.business.mine.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.ConvertUtils;
import com.blankj.utilcode.util.ObjectUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.gavin.com.library.PowerfulStickyDecoration;
import com.gavin.com.library.StickyDecoration;
import com.gavin.com.library.listener.GroupListener;
import com.gavin.com.library.listener.OnGroupClickListener;
import com.gavin.com.library.listener.PowerGroupListener;
import com.mcxtzhang.swipemenulib.SwipeMenuLayout;
import com.qubuyer.R;
import com.qubuyer.base.activity.BaseActivity;
import com.qubuyer.bean.ToolbarMenuEntity;
import com.qubuyer.bean.event.GoToMainEvent;
import com.qubuyer.bean.mine.MineBrowseFootprintEntity;
import com.qubuyer.business.main.MainActivity;
import com.qubuyer.business.mine.adapter.MineBrowseFootprintAdapter;
import com.qubuyer.business.mine.presenter.MineBrowseFootprintListPresenter;
import com.qubuyer.business.mine.view.BrowseFootprintDecoration;
import com.qubuyer.business.mine.view.IMineBrowseFootprintListView;
import com.qubuyer.customview.AbsToolbar;
import com.qubuyer.customview.DecorationLLM;
import com.qubuyer.utils.DialogUtil;
import com.qubuyer.utils.EventBusUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;


/**
 * @author Susong
 * @date 创建时间:2019/4/1
 * @description 浏览足迹activity
 * & @version
 */
public class MineBrowseFootprintActivity extends BaseActivity<MineBrowseFootprintListPresenter> implements IMineBrowseFootprintListView {
    @BindView(R.id.rv_list)
    RecyclerView rv_list;
    @BindView(R.id.ll_no_data)
    LinearLayout ll_no_data;
    private ToolbarMenuEntity mToolbarMenuEntity;
    private List<ToolbarMenuEntity> mToolbarMenuEntityList;
    private MineBrowseFootprintAdapter mAdapter;
    private PowerfulStickyDecoration mPowerfulStickyDecoration;

    @Override
    protected int getContentView() {
        return R.layout.layout_activity_mine_browsefootprint;
    }

    @Override
    protected MineBrowseFootprintListPresenter createP(Context context) {
        return new MineBrowseFootprintListPresenter();
    }

    @Override
    protected void initWidget(Bundle savedInstanceState) {
        setTitle("浏览足迹");
        initMenu();
        initRecyclerView();
    }

    private void initMenu() {
        mToolbarMenuEntityList = new ArrayList<>();
        mToolbarMenuEntity = new ToolbarMenuEntity();
//        toolbarMenuEntity.setDpWidth(20);
//        toolbarMenuEntity.setDpHeight(20);
        mToolbarMenuEntity.setMenuContent("清空");
        mToolbarMenuEntity.setOnMenuOnClickListener(new AbsToolbar.OnMenuOnClickListener() {
            @Override
            public void onMenuOnClick(View view) {
                mPresenter.clearBrowseFootprint();
            }
        });
        mToolbarMenuEntityList.add(mToolbarMenuEntity);
        inflateMenu(mToolbarMenuEntityList);
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    protected void initData() {
        mPresenter.loadBrowseFootprintListData();
    }

    private void initRecyclerView() {
        mAdapter = new MineBrowseFootprintAdapter(this, new MineBrowseFootprintAdapter.OnBrowseFootprintOperationListener() {
            @Override
            public void onDeleteItemListener(MineBrowseFootprintEntity.ValueBean entity) {
                DialogUtil.getConfirmDialog(MineBrowseFootprintActivity.this, "确定要删除选中的浏览记录吗?", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int whichq) {
                        mPresenter.deleteBrowseFootprint(entity);
                    }
                }, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).show();
            }
        });
        PowerGroupListener listener = new PowerGroupListener() {
            @Override
            public String getGroupName(int position) {
                //获取组名，用于判断是否是同一组
                String time = mAdapter.getData().get(position).getTime();
                if (ObjectUtils.isNotEmpty(time)) {
                    return time;
                }
                return null;
            }

            @Override
            public View getGroupView(int position) {
                //获取自定定义的组View
                String time = mAdapter.getData().get(position).getTime();
                if (ObjectUtils.isNotEmpty(time)) {
                    View view = getLayoutInflater().inflate(R.layout.item_mine_browsefootprint_suspension_header, null, false);
                    ((TextView) view.findViewById(R.id.tv_title)).setText(time);
                    return view;
                } else {
                    return null;
                }
            }
        };
        mPowerfulStickyDecoration = PowerfulStickyDecoration.Builder
                .init(listener)
//                .setGroupBackground(Color.parseColor("#EEEEEE"))
//                .setGroupHeight(ConvertUtils.dp2px(30))
//                .setGroupTextColor(Color.parseColor("#333333"))
//                .setGroupTextSize(ConvertUtils.dp2px(13))
//                .setTextSideMargin(ConvertUtils.dp2px(15))
//                .setDivideColor(Color.parseColor("#E9E9E9"))
//                .setDivideHeight(ConvertUtils.dp2px(0.5f))
//                .setHeaderCount(0)
                .setOnClickListener(new OnGroupClickListener() {
                    @Override
                    public void onClick(int position, int id) {
                    }
                })
                .build();

        rv_list.addItemDecoration(new DecorationLLM(this, DecorationLLM.VERTICAL_LIST, R.drawable.shape_recyclerview_divider, ConvertUtils.dp2px(10)));
        rv_list.addItemDecoration(new BrowseFootprintDecoration(new BrowseFootprintDecoration.TitleAttributes(this)));
        rv_list.setLayoutManager(new LinearLayoutManager(this));
//        rv_list.addItemDecoration(mPowerfulStickyDecoration);
        rv_list.setAdapter(mAdapter);
    }

    @OnClick({R.id.ll_container, R.id.tv_go_main})
    public void onClickByButterKnfie(View v) {
        switch (v.getId()) {
            case R.id.ll_container:
                if (null != SwipeMenuLayout.getViewCache()) {
                    SwipeMenuLayout.getViewCache().quickClose();
                }
                break;
            case R.id.tv_go_main:
                EventBusUtil.sendEvent(new GoToMainEvent());
                ActivityUtils.finishOtherActivities(MainActivity.class);
                break;
        }
    }

    @Override
    public void showLoading() {
        showLoadingDialog();
    }

    @Override
    public void hideLoading() {
        dissmissLoadingDialog();
    }

    @Override
    public void onShowBrowseFootprintListToView(List<MineBrowseFootprintEntity.ValueBean> list) {
        mAdapter.setData(list);
//        mPowerfulStickyDecoration.clearCache();
        if (null == list || list.isEmpty()) {
            rv_list.setVisibility(View.GONE);
            ll_no_data.setVisibility(View.VISIBLE);
        } else {
            rv_list.setVisibility(View.VISIBLE);
            ll_no_data.setVisibility(View.GONE);
        }
    }

    @Override
    public void onDeleteBrowseFootprintResult(boolean success) {
        if (success) {
            ToastUtils.setGravity(Gravity.CENTER, 0, 0);
            showToastCenter("浏览足迹已删除");
        }
        mPresenter.loadBrowseFootprintListData();
    }

    @Override
    public void onClearBrowseFootprintResult(boolean success) {
        if (success) {
            ToastUtils.setGravity(Gravity.CENTER, 0, 0);
            showToastCenter("浏览足迹已清空");
        }
        mPresenter.loadBrowseFootprintListData();
    }
}
