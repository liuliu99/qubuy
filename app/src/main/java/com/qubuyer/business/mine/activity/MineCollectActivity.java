package com.qubuyer.business.mine.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.ConvertUtils;
import com.blankj.utilcode.util.ObjectUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.mcxtzhang.swipemenulib.SwipeMenuLayout;
import com.qubuyer.R;
import com.qubuyer.base.activity.BaseActivity;
import com.qubuyer.bean.event.GoToMainEvent;
import com.qubuyer.bean.mine.MineCollectEntity;
import com.qubuyer.business.main.MainActivity;
import com.qubuyer.business.mine.adapter.MineCollectionAdapter;
import com.qubuyer.business.mine.presenter.MineCollectionListPresenter;
import com.qubuyer.business.mine.view.IMineCollectionListView;
import com.qubuyer.constant.NetConstant;
import com.qubuyer.customview.DecorationLLM;
import com.qubuyer.utils.DialogUtil;
import com.qubuyer.utils.EventBusUtil;
import com.qubuyer.utils.ShareSdkUtil1;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;


/**
 * @author Susong
 * @date 创建时间:2019/4/1
 * @description 我的收藏activity
 * & @version
 */
public class MineCollectActivity extends BaseActivity<MineCollectionListPresenter> implements IMineCollectionListView {
    @BindView(R.id.ll_container)
    LinearLayout ll_container;
    @BindView(R.id.rv_collection)
    RecyclerView rv_collection;
    @BindView(R.id.ll_no_data)
    LinearLayout ll_no_data;

    private MineCollectionAdapter mAdapter;

    @Override
    protected int getContentView() {
        return R.layout.layout_activity_mine_collect;
    }

    @Override
    protected MineCollectionListPresenter createP(Context context) {
        return new MineCollectionListPresenter();
    }

    @Override
    protected void initWidget(Bundle savedInstanceState) {
        setTitle("我的收藏");
        initRecyclerView();
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.loadCollectionListData();
    }

    @Override
    protected void initData() {
        mPresenter.loadCollectionListData();
    }

    private void initRecyclerView() {
        mAdapter = new MineCollectionAdapter(this, new MineCollectionAdapter.OnCollectionOperationListener() {
            @Override
            public void onDeleteItemListener(MineCollectEntity entity) {
                DialogUtil.getConfirmDialog(MineCollectActivity.this, "确定要删除选中的收藏吗?", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mPresenter.deleteCollection(entity);
                    }
                }, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).show();
            }

            @Override
            public void onShareItemListener(MineCollectEntity.GoodsBean entity) {
                if (ObjectUtils.isEmpty(entity)) {
                    ToastUtils.showShort("数据异常, 请刷新重试");
                    return;
                }
                String url = String.format("%s/home/html/goodsShare?id=%s", NetConstant.BASE_URL, entity.getGoods_id());
                ShareSdkUtil1.Builder.getInstanse(MineCollectActivity.this)
                        .setTitle(entity.getGoods_name())
                        .setText("还有更多奖励等你一起拿")
                        .setImageUrl(entity.getOriginal_img())
                        .setUrl(url)
                        .setTitleUrl(url)
                        .showShareMenu();
            }
        });
        rv_collection.addItemDecoration(new DecorationLLM(this, DecorationLLM.VERTICAL_LIST, R.drawable.shape_recyclerview_divider, ConvertUtils.dp2px(10)));
        rv_collection.setLayoutManager(new LinearLayoutManager(this));
        rv_collection.setAdapter(mAdapter);
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
    public void onShowCollectListToView(List<MineCollectEntity> list) {
        mAdapter.setData(list);
        if (null == list || list.isEmpty()) {
            rv_collection.setVisibility(View.GONE);
            ll_no_data.setVisibility(View.VISIBLE);
        } else {
            rv_collection.setVisibility(View.VISIBLE);
            ll_no_data.setVisibility(View.GONE);
        }
    }

    @Override
    public void onDeleteCollectionResult(boolean success) {
        if (success) {
            ToastUtils.setGravity(Gravity.CENTER, 0, 0);
            showToastCenter("已取消收藏");
        }
        mPresenter.loadCollectionListData();
    }
}
