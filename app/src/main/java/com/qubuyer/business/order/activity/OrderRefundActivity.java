package com.qubuyer.business.order.activity;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.TextView;

import com.blankj.utilcode.util.FileUtils;
import com.blankj.utilcode.util.ImageUtils;
import com.blankj.utilcode.util.ThreadUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.blankj.utilcode.util.Utils;
import com.qubuyer.R;
import com.qubuyer.base.activity.BaseActivity;
import com.qubuyer.bean.order.OrderEntity;
import com.qubuyer.bean.order.OrderRefundReasonEntity;
import com.qubuyer.business.mine.adapter.PicGridViewAdpter;
import com.qubuyer.business.order.presenter.OrderRefundPresenter;
import com.qubuyer.business.order.view.IOrderRefundView;
import com.qubuyer.business.order.view.RefundReasonDialog;
import com.qubuyer.constant.AppConstant;
import com.qubuyer.customview.BottomUpDialog;
import com.qubuyer.customview.ImageSelectorGridView;
import com.qubuyer.customview.ImageViewAutoLoad;
import com.qubuyer.customview.PictureZoomView;
import com.qubuyer.utils.DialogUtil;
import com.qubuyer.utils.EditTextUtils;
import com.qubuyer.utils.FileUtil;
import com.qubuyer.utils.NavigationUtil;
import com.qubuyer.utils.StringUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import androidx.annotation.Nullable;

import org.devio.takephoto.model.TImage;
import org.devio.takephoto.model.TResult;

import butterknife.BindView;
import butterknife.OnClick;


/**
 * @author Susong
 * @date 创建时间2019/3/31
 * @description 申请退款
 */
public class OrderRefundActivity extends BaseActivity<OrderRefundPresenter> implements IOrderRefundView {
    @BindView(R.id.iv_good_img)
    ImageViewAutoLoad iv_good_img;
    @BindView(R.id.tv_good_name)
    TextView tv_good_name;
    @BindView(R.id.tv_good_spec)
    TextView tv_good_spec;
    @BindView(R.id.tv_good_price)
    TextView tv_good_price;
    @BindView(R.id.tv_good_num)
    TextView tv_good_num;


    //    @BindView(R.id.tv_refund_price)
//    TextView tv_refund_price;
    @BindView(R.id.et_refund_price)
    EditText et_refund_price;
    @BindView(R.id.tv_refund_price_desc)
    TextView tv_refund_price_desc;
    @BindView(R.id.et_refund_explain)
    EditText et_refund_explain;

    @BindView(R.id.tv_refund_reason)
    TextView tv_refund_reason;
    @BindView(R.id.tv_pic_count)
    TextView tv_pic_count;
    @BindView(R.id.isgv_pic)
    ImageSelectorGridView isgv_pic;

    private OrderEntity mOrderEntity;
    private OrderEntity.SplitBean.OrderGoodBean mGoodEntity;

    private List<OrderRefundReasonEntity> mRefundReasonList;

    private RefundReasonDialog refundReasonDialog;

    private OrderRefundReasonEntity mSelectedRefundReasonEntity;

    private PicGridViewAdpter mPicGridViewAdpter;
    private List<String> mPicList = new ArrayList<>();

    private BottomUpDialog mBottomUpDialog;

    @Override
    protected int getContentView() {
        return R.layout.layout_activity_order_refund;
    }

    @Override
    protected OrderRefundPresenter createP(Context context) {
        return new OrderRefundPresenter();
    }

    @Override
    protected void initWidget(Bundle savedInstanceState) {
        setTitle("申请退款");
        if (null != getIntent() && null != getIntent().getSerializableExtra(AppConstant.INTENT_EXTRA_KEY)) {
            HashMap map = (HashMap) getIntent().getSerializableExtra(AppConstant.INTENT_EXTRA_KEY);
            mOrderEntity = (OrderEntity) map.get("order_entity");
            mGoodEntity = (OrderEntity.SplitBean.OrderGoodBean) map.get("good_entity");
        }
        EditTextUtils.setPricePoint(et_refund_price);
        if (null != mGoodEntity) {
            iv_good_img.setUri(this, mGoodEntity.getOriginal_img_full());
            tv_good_name.setText(mGoodEntity.getGoods_name());
            tv_good_spec.setText(mGoodEntity.getSpec_key_name());
            tv_good_price.setText("¥" + StringUtil.formatAmount(mGoodEntity.getMember_goods_price(), 2));
            tv_good_num.setText("x" + mGoodEntity.getGoods_num());
//            tv_refund_price.setText("¥" + StringUtil.formatAmount(mGoodEntity.getMember_goods_price(), 2));
            tv_refund_price_desc.setText("最多¥" + StringUtil.formatAmount(mGoodEntity.getMember_goods_price(), 2) + ",含发货邮费¥0.00");
        }

        mPicGridViewAdpter = new PicGridViewAdpter(mPicList, true, this, new PicGridViewAdpter.OnPicOperationListener() {
            @Override
            public void onDelClick(int position) {
//                mPicList.clear();
//                mPicList.addAll(mPicGridViewAdpter.getDatas());
                mPicList = mPicGridViewAdpter.getDatas();
                tv_pic_count.setText(mPicList.size() + "/4");
            }
        });
        isgv_pic.setAdapter(mPicGridViewAdpter);
        //图片点击
        isgv_pic.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                if (mPicGridViewAdpter.getDatas().size() < 4 && position == (mPicGridViewAdpter.getCount() - 1)) {
                    showPicSelectTypeDialog();
                } else {
                    PictureZoomView.actionStartFile(OrderRefundActivity.this, mPicGridViewAdpter.getDatas(), position);
                }
            }
        });
    }

    @Override
    protected void initData() {
        mPresenter.getRefundReasonList();
    }

    @OnClick({R.id.rl_refund_reason, R.id.tv_submit})
    public void onClickWithButterKnfie(View v) {
        switch (v.getId()) {
            case R.id.rl_refund_reason: //退货原因
                showRefundReasonDialog();
                break;
            case R.id.tv_submit: //提交
                if (null == mSelectedRefundReasonEntity) {
                    ToastUtils.showShort("请选择退货原因");
                    return;
                }
                String refundPrice = et_refund_price.getText().toString();
                if (TextUtils.isEmpty(refundPrice)) {
                    ToastUtils.showShort("请输入退款金额");
                    return;
                }
                if (Float.parseFloat(refundPrice) <= 0) {
                    ToastUtils.showShort("请输入正确的退款金额");
                    return;
                }
                if (Float.parseFloat(refundPrice) > Float.parseFloat(mGoodEntity.getMember_goods_price())) {
                    ToastUtils.showShort("最多只能退款" + StringUtil.formatAmount(mGoodEntity.getMember_goods_price(), 2) + "元");
                    return;
                }
                mPresenter.submitRefund(mGoodEntity.getRec_id() + "", mSelectedRefundReasonEntity.getId() + "", refundPrice, et_refund_explain.getText().length() > 1 ? et_refund_explain.getText().toString() : "", mPicList);
                break;
        }
    }

    private void showPicSelectTypeDialog() {
        if (null == mBottomUpDialog) {
            mBottomUpDialog = new BottomUpDialog(this, new String[]{"拍照", "从相册选择"}, new BottomUpDialog.OnPositionClickListener() {
                @Override
                public void onPositionClick(int position) {
                    switch (position) {
                        case 0:
                            getTakePhoto().onPickFromCapture(getFilePath());
                            break;
                        case 1:
                            getTakePhoto().onPickMultiple(4 - mPicList.size());
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
        File fileDir = FileUtil.getOwnCacheDirectory(this, Utils.getApp().getPackageName() + "/cache/temp/");
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

    @Override
    public void takeSuccess(TResult result) {
        if (null != result && null != result.getImages() && result.getImages().size() >= 1) {
            if (result.getImages().size() + mPicList.size() > 4) {
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
                            mPicList.add(selectedHeadImgPath);
                        } else {
                            mPicList.add(selectedHeadImgPath);
                        }
                    }
                    return null;
                }

                @Override
                public void onSuccess(@Nullable Object result) {
                    hideLoading();
                    mPicGridViewAdpter.setDatas(mPicList);
                    if (mPicList.size() >= 4) {
                        mPicGridViewAdpter.isNeedAdd(false);
                    }
                    tv_pic_count.setText(mPicList.size() + "/4");
                }
            });
        }
    }

    @Override
    public void takeFail(TResult result, String msg) {
        ToastUtils.showShort(msg);
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
    public void onShowRefundReasonListToView(List<OrderRefundReasonEntity> list) {
        mRefundReasonList = list;
//        if (null != list && !list.isEmpty()) {
//            mSelectedRefundReasonEntity = list.get(0);
//        }
    }

    @Override
    public void onShowSubmitRefundResultToView(boolean result) {
        if (result) {
            NavigationUtil.forward(this, OrderRefundListActivity.class);
        }
    }

    private void showRefundReasonDialog() {
        if (null == mRefundReasonList || mRefundReasonList.isEmpty()) {
            ToastUtils.showShort("退款原因数据异常");
            return;
        }
        if (null == refundReasonDialog) {
            refundReasonDialog = new RefundReasonDialog(this, new RefundReasonDialog.SelectRefunReasonListener() {
                @Override
                public void onRefundReasonClickListener(OrderRefundReasonEntity entity) {
                    mSelectedRefundReasonEntity = entity;
                    tv_refund_reason.setText(mSelectedRefundReasonEntity.getName());
                }
            });
            refundReasonDialog.setData(mRefundReasonList);
        }
        if (!refundReasonDialog.isShowing()) {
            refundReasonDialog.show();
            DialogUtil.setDialogWindowAttr(refundReasonDialog, WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT, Gravity.BOTTOM, R.style.shopping_cart_anim);
        }
    }

    @Override
    public void destory() {

    }
}
