package com.qubuyer.business.order.activity;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;

import com.blankj.utilcode.util.FileUtils;
import com.blankj.utilcode.util.ImageUtils;
import com.blankj.utilcode.util.ThreadUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.qubuyer.R;
import com.qubuyer.base.activity.BaseActivity;
import com.qubuyer.bean.ToolbarMenuEntity;
import com.qubuyer.bean.order.OrderCommentSubmitParamEntity;
import com.qubuyer.bean.order.OrderEntity;
import com.qubuyer.business.order.adapter.OrderCommentListAdapter;
import com.qubuyer.business.order.holder.OrderCommentListVH;
import com.qubuyer.business.order.presenter.OrderCommentListPresenter;
import com.qubuyer.business.order.view.IOrderCommentListView;
import com.qubuyer.constant.AppConstant;
import com.qubuyer.customview.AbsToolbar;
import com.qubuyer.utils.NavigationUtil;
import com.xingliuhua.xlhratingbar.XLHRatingBar;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.devio.takephoto.model.TImage;
import org.devio.takephoto.model.TResult;

import butterknife.BindView;


/**
 * @author Susong
 * @date 创建时间2019/4/2
 * @description 发表评论列表
 */
public class OrderCommentListActivity extends BaseActivity<OrderCommentListPresenter> implements IOrderCommentListView {
    @BindView(R.id.rv_list)
    RecyclerView rv_list;

    private OrderEntity mOrderEntity;

    private OrderCommentListAdapter mAdapter;
    private ToolbarMenuEntity mToolbarMenuEntity;
    private List<ToolbarMenuEntity> mToolbarMenuEntityList;
    private OrderCommentListVH mCurrentSelectedHolder;

    @Override
    protected int getContentView() {
        return R.layout.layout_activity_order_comment_list;
    }

    @Override
    protected OrderCommentListPresenter createP(Context context) {
        return new OrderCommentListPresenter();
    }

    @Override
    protected void initWidget(Bundle savedInstanceState) {
        setTitle("发表评论");
        initMenu();
        if (null != getIntent() && null != getIntent().getSerializableExtra(AppConstant.INTENT_EXTRA_KEY)) {
            mOrderEntity = (OrderEntity) getIntent().getSerializableExtra(AppConstant.INTENT_EXTRA_KEY);
        }
        initRecyclerView();
    }

    private void initMenu() {
        mToolbarMenuEntityList = new ArrayList<>();
        mToolbarMenuEntity = new ToolbarMenuEntity();
//        toolbarMenuEntity.setDpWidth(20);
//        toolbarMenuEntity.setDpHeight(20);
        mToolbarMenuEntity.setMenuContent("发布");
        mToolbarMenuEntity.setOnMenuOnClickListener(new AbsToolbar.OnMenuOnClickListener() {
            @Override
            public void onMenuOnClick(View view) {
                getCommentListData();
            }
        });
        mToolbarMenuEntityList.add(mToolbarMenuEntity);
        inflateMenu(mToolbarMenuEntityList);
    }

    private void getCommentListData() {
        List<OrderCommentSubmitParamEntity> list = new ArrayList<>();
        OrderCommentSubmitParamEntity entity;
        for (int i = 0; i < rv_list.getChildCount(); i++) {
            entity = new OrderCommentSubmitParamEntity();
            entity.setOrderId(mOrderEntity.getId() + "");
            entity.setRec_id(mOrderEntity.getOrder_goods().get(i).getRec_id() +"");
            View view = rv_list.getChildAt(i);
            XLHRatingBar tv_rating_bar = view.findViewById(R.id.tv_rating_bar);
            if (tv_rating_bar.getRating() == 0) {
                ToastUtils.showShort("请给第" + (i + 1) + "个商品添加评论星级");
                return;
            }
            entity.setRank(tv_rating_bar.getRating() + "");
            EditText et_comment_content = view.findViewById(R.id.et_comment_content);
            if (et_comment_content.getText().length() > 0) {
                entity.setContent(et_comment_content.getText().toString());
            } else {
                ToastUtils.showShort("请给第" + (i + 1) + "个商品添加评论内容");
                return;
            }
            CheckBox cb_anonymity = view.findViewById(R.id.cb_anonymity);
            if (cb_anonymity.isChecked()) {
                entity.setAnonymous("1");
            } else {
                entity.setAnonymous("0");
            }
            OrderCommentListVH holder = (OrderCommentListVH) rv_list.findViewHolderForAdapterPosition(i);
            if (null != holder) {
                entity.setImages(holder.picList);
            }
            list.add(entity);
        }
        mPresenter.submitComment(mOrderEntity.getId() + "", list);
    }

    @Override
    protected void initData() {
    }

    private void initRecyclerView() {
        mAdapter = new OrderCommentListAdapter(this, mOrderEntity.getOrder_goods(), new OrderCommentListAdapter.OrderCommentOperationListener() {
            @Override
            public void onClickPicListener(OrderCommentListVH holder) {
                mCurrentSelectedHolder = holder;
            }
        });
//        mRecyclerView.addItemDecoration(new DecorationLLM(mContext, DecorationLLM.VERTICAL_LIST, R.drawable.shape_recyclerview_divider, DensityUtil.dip2px(mContext, 10)));
        rv_list.setLayoutManager(new LinearLayoutManager(this));
        rv_list.setAdapter(mAdapter);
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
    public void onShowSumitCommentResultToView(boolean result) {
        if (result) {
            NavigationUtil.forward(this, OrderCommentResultActivity.class);
        }
    }

    @Override
    public void takeSuccess(TResult result) {
        if (null != result && null != result.getImages() && result.getImages().size() >= 1) {
            if (result.getImages().size() + mCurrentSelectedHolder.picList.size() > 4) {
                ToastUtils.showShort("最多可以上传4张图片");
                return;
            }
            showLoading();
            ThreadUtils.executeByCached(new ThreadUtils.SimpleTask<Object>() {

                @Nullable
                @Override
                public Object doInBackground() {
                    for (TImage image : result.getImages()) {
                        String selectedHeadImgPath = image.getOriginalPath();
                        if (FileUtils.getFileLength(selectedHeadImgPath) / 1048576 > 2) {
                            Bitmap bitmap = ImageUtils.getBitmap(selectedHeadImgPath);
                            bitmap = ImageUtils.bytes2Bitmap(ImageUtils.compressByQuality(bitmap, Long.valueOf(500 * 1024)));
                            ImageUtils.save(bitmap, selectedHeadImgPath, Bitmap.CompressFormat.JPEG);
                            mCurrentSelectedHolder.picList.add(selectedHeadImgPath);
                        } else {
                            mCurrentSelectedHolder.picList.add(selectedHeadImgPath);
                        }
                    }
                    return null;
                }

                @Override
                public void onSuccess(@Nullable Object result) {
                    hideLoading();
                    mCurrentSelectedHolder.picGridViewAdpter.setDatas(mCurrentSelectedHolder.picList);
                    if (mCurrentSelectedHolder.picList.size() >= 4) {
                        mCurrentSelectedHolder.picGridViewAdpter.isNeedAdd(false);
                    }
                    mCurrentSelectedHolder.tv_pic_count.setText(mCurrentSelectedHolder.picList.size() + "/4");
                }
            });
        }
    }

    @Override
    public void takeFail(TResult result, String msg) {
        ToastUtils.showShort(msg);
    }
}
