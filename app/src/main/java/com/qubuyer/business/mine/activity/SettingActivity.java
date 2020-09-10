package com.qubuyer.business.mine.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.FileUtils;
import com.blankj.utilcode.util.ImageUtils;
import com.blankj.utilcode.util.ThreadUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.blankj.utilcode.util.Utils;
import com.qubuyer.R;
import com.qubuyer.base.activity.BaseActivity;
import com.qubuyer.bean.event.GoToMainEvent;
import com.qubuyer.bean.mine.UserEntity;
import com.qubuyer.business.mine.presenter.MineSettingPresenter;
import com.qubuyer.business.mine.view.IMineSettingView;
import com.qubuyer.business.register.activity.ForgetPwdActivity;
import com.qubuyer.customview.BottomUpDialog;
import com.qubuyer.customview.ImageViewAutoLoad;
import com.qubuyer.utils.DialogUtil;
import com.qubuyer.utils.EventBusUtil;
import com.qubuyer.utils.FileUtil;
import com.qubuyer.utils.ImageUtil;
import com.qubuyer.utils.NavigationUtil;
import com.qubuyer.utils.SessionUtil;

import java.io.File;

import androidx.annotation.Nullable;

import org.devio.takephoto.model.TResult;

import butterknife.BindView;
import butterknife.OnClick;


/**
 * @author Susong
 * @date 创建时间2019/4/11
 * @description 个人设置activity
 */
public class SettingActivity extends BaseActivity<MineSettingPresenter> implements IMineSettingView {
    @BindView(R.id.rl_user_img)
    RelativeLayout rl_user_img;
    @BindView(R.id.iv_user_headimg)
    ImageViewAutoLoad iv_user_headimg;

    @BindView(R.id.rl_user_name)
    RelativeLayout rl_user_name;
    @BindView(R.id.tv_user_name)
    TextView tv_user_name;

    @BindView(R.id.rl_address)
    RelativeLayout rl_address;

    @BindView(R.id.rl_user_phone)
    RelativeLayout rl_user_phone;
    @BindView(R.id.tv_user_phone)
    TextView tv_user_phone;

    @BindView(R.id.rl_update_pwd)
    RelativeLayout rl_update_pwd;

    @BindView(R.id.rl_thrid_account)
    RelativeLayout rl_thrid_account;

    @BindView(R.id.rl_login_out)
    RelativeLayout rl_login_out;


    private BottomUpDialog mBottomUpDialog;

    private String mSelectedHeadImgPath;

    private UserEntity mUserEntity;

    @Override
    protected int getContentView() {
        return R.layout.layout_activity_setting;
    }

    @Override
    protected MineSettingPresenter createP(Context context) {
        return new MineSettingPresenter();
    }

    @Override
    protected void initWidget(Bundle savedInstanceState) {
        setTitle("设置");
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.getUserInfo();
    }

    @Override
    protected void initData() {
    }

    @Override
    public void showLoading() {
        showLoadingDialog();
    }

    @Override
    public void hideLoading() {
        dissmissLoadingDialog();
    }

    @OnClick({R.id.rl_user_img, R.id.rl_user_name, R.id.rl_address, R.id.rl_user_phone, R.id.rl_update_pwd, R.id.rl_thrid_account, R.id.rl_login_out})
    public void onClickWithButterKnife(View view) {
        switch (view.getId()) {
            case R.id.rl_user_img: //头像
                showPicSelectTypeDialog();
                break;
            case R.id.rl_user_name: //昵称
                NavigationUtil.overlay(this, UpdateNickNameActivity.class);
                break;
            case R.id.rl_address: //地址管理
                NavigationUtil.overlay(this, AddressListActivity.class);
                break;
            case R.id.rl_user_phone: //手机号
                NavigationUtil.overlay(this, UpdatePhoneOneActivity.class);
                break;
            case R.id.rl_update_pwd: //修改密码
                NavigationUtil.overlay(this, ForgetPwdActivity.class);
                break;
            case R.id.rl_thrid_account: //第三方账户
                NavigationUtil.overlay(this, ThirdAccountActivity.class);
                break;
            case R.id.rl_login_out: //退出登录
                DialogUtil.getConfirmDialog(this, "提示", "确定要退出登录吗?", "确定", "取消", true, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mPresenter.loginOut();
                        SessionUtil.getInstance().clearUserInfo();
                    }
                }, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).show();
                break;
        }
    }

    @Override
    public void onShowUserInfoToView(UserEntity entity) {
        mUserEntity = entity;
        if (null != entity) {
            SessionUtil.getInstance().saveUserInfo(entity);
            ImageUtil.loadCircleImage(this, entity.getHead_pic(), iv_user_headimg);
            tv_user_name.setText(entity.getNickname());
            tv_user_phone.setText(entity.getMobile());
        } else {
            ToastUtils.showShort("用户信息异常");
        }
    }

    @Override
    public void onShowLoginOutResultToView(boolean result) {
        if (result) {
            EventBusUtil.sendEvent(new GoToMainEvent());
            finish();
        }
    }

    @Override
    public void onShowUpdateUserHeadImgResultToView(boolean result) {
        if (result) {
            ToastUtils.showShort("更新头像成功");
            mPresenter.getUserInfo();
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
                            getTakePhoto().onPickFromGallery();
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
            mSelectedHeadImgPath = result.getImages().get(0).getOriginalPath();
            if (FileUtils.getFileLength(mSelectedHeadImgPath) / 1048576 > 2) {
                showLoading();
                ThreadUtils.executeByCached(new ThreadUtils.SimpleTask<Object>() {

                    @Nullable
                    @Override
                    public Object doInBackground() throws Throwable {
                        Bitmap bitmap = ImageUtils.getBitmap(mSelectedHeadImgPath);
                        bitmap = ImageUtils.bytes2Bitmap(ImageUtils.compressByQuality(bitmap, Long.valueOf(500 * 1024)));
                        ImageUtils.save(bitmap, mSelectedHeadImgPath, Bitmap.CompressFormat.JPEG);
                        return null;
                    }

                    @Override
                    public void onSuccess(@Nullable Object result) {
                        hideLoading();
//                        ToastUtils.showShort(FileUtils.getFileSize(mSelectedHeadImgPath));
                        mPresenter.updateHeadImg(new File(mSelectedHeadImgPath));
                    }
                });
            } else {
                mPresenter.updateHeadImg(new File(mSelectedHeadImgPath));
            }
        }
    }

    @Override
    public void takeFail(TResult result, String msg) {
        ToastUtils.showShort(msg);
    }
}
