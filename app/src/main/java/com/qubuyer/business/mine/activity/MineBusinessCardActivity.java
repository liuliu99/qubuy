package com.qubuyer.business.mine.activity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.blankj.utilcode.util.FileUtils;
import com.blankj.utilcode.util.ThreadUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.blankj.utilcode.util.Utils;
import com.qubuyer.R;
import com.qubuyer.base.activity.BaseActivity;
import com.qubuyer.bean.mine.MineCardEntity;
import com.qubuyer.bean.mine.MinePosterEntity;
import com.qubuyer.bean.mine.UserEntity;
import com.qubuyer.business.mine.presenter.MineCardPresenter;
import com.qubuyer.business.mine.view.IMineCardView;
import com.qubuyer.customview.ImageViewAutoLoad;
import com.qubuyer.utils.FileUtil;
import com.qubuyer.utils.ImageUtil;
import com.qubuyer.utils.MultipleShareWxUtil;

import java.io.File;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;


/**
 * @author Susong
 * @date 创建时间:2019/4/8
 * @description 我的名片activity
 * & @version
 */
public class MineBusinessCardActivity extends BaseActivity<MineCardPresenter> implements IMineCardView {
    @BindView(R.id.iv_qr_code)
    ImageViewAutoLoad iv_qr_code;
    @BindView(R.id.tv_mine_invite_code)
    TextView tv_mine_invite_code;
    @BindView(R.id.tv_share_qr_code)
    TextView tv_share_qr_code;
    @BindView(R.id.tv_save_location)
    TextView tv_save_location;

    private MineCardEntity mineCardEntity;

    //操作类型 0:分享二维码 1:保存海报到相册
    private int mType;

    @Override
    protected int getContentView() {
        return R.layout.layout_activity_mine_business_card;
    }

    @Override
    protected MineCardPresenter createP(Context context) {
        return new MineCardPresenter();
    }

    @Override
    protected void initWidget(Bundle savedInstanceState) {
        setTitle("我的名片");
//        mQrBitmap = QRCodeUtil.createQRCodeBitmap("test", ConvertUtils.dp2px(106), ConvertUtils.dp2px(106));
//        iv_qr_code.setImageBitmap(mQrBitmap);
    }

    @Override
    protected void initData() {
        mPresenter.getQrCode();
    }

    @Override
    public void showLoading() {
        showLoadingDialog();
    }

    @Override
    public void hideLoading() {
        dissmissLoadingDialog();
    }

    @OnClick({R.id.tv_share_qr_code, R.id.tv_save_location})
    public void onClickWithButterKnife(View view) {
        switch (view.getId()) {
            case R.id.tv_share_qr_code: //分享二维码
                mType = 0;
                mPresenter.getPoster();
//                ImageUtil.downloadImage(this, mineCardEntity.getOrigin_qrcode_full(), new ImageUtil.DownImgListener() {
//                    @Override
//                    public void onDownSuccess(File file) {
//                        ThreadUtils.executeByCached(new ThreadUtils.SimpleTask<Object>() {
//                            @Nullable
//                            @Override
//                            public Object doInBackground() throws Throwable {
//                                Bitmap bitmap = ImageUtils.getBitmap(file);
//                                File file1 = getFilePath();
//                                ImageUtils.save(bitmap, file1, Bitmap.CompressFormat.JPEG);
//                                return file1;
//                            }
//
//                            @Override
//                            public void onSuccess(@Nullable Object result) {
//                                ArrayList files = new ArrayList();
//                                files.add(result);
//                                MultipleShareWxUtil.shareImage(MineBusinessCardActivity.this, 0, files, null);
//                            }
//                        });
//                    }
//                });
                break;
            case R.id.tv_save_location: //保存到相册
                mType = 1;
                mPresenter.getPoster();
                break;
        }
    }

    private File getFilePath() {
        File fileDir = FileUtil.getOwnCacheDirectory(this, Utils.getApp().getPackageName() + "/cache/temp/");
        FileUtils.createOrExistsDir(fileDir);
        String name = String.valueOf(System.currentTimeMillis());
        return FileUtils.getFileByPath(fileDir.getAbsolutePath() + "/" + name + ".jpg");

//        if (FileUtils.createOrExistsDir(AppConstant.PICTURE_PATH)) {
//            String name = String.valueOf(System.currentTimeMillis());
//            File file = FileUtils.getFileByPath(AppConstant.PICTURE_PATH + name + ".jpg");
//            return Uri.fromFile(file);
//        }
//        return null;
    }

    private void savePosterToPic(File file) {
        //系统相册目录
        String galleryPath = Environment.getExternalStorageDirectory()
                + File.separator + Environment.DIRECTORY_DCIM
                + File.separator + "Camera" + File.separator;
        ThreadUtils.executeByCached(new ThreadUtils.SimpleTask<Object>() {
            @Nullable
            @Override
            public Object doInBackground() throws Throwable {
                File picdir = new File(galleryPath);
                if (!picdir.exists()) {
                    picdir.mkdirs();
                }
                long tempName = System.currentTimeMillis();
                File file2 = new File(picdir, tempName + ".jpg");
                FileUtils.copy(file, file2);

                //通知相册更新
                MediaStore.Images.Media.insertImage(getContentResolver(), file2.getPath(), file2.getName(), null);
                Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
                Uri uri = Uri.fromFile(file2);
                intent.setData(uri);
                sendBroadcast(intent);
                return galleryPath + tempName + ".jpg";
            }

            @Override
            public void onSuccess(@Nullable Object result) {
                if (mType == 0) {
                    ArrayList files = new ArrayList();
                    files.add(new File(String.valueOf(result)));
                    MultipleShareWxUtil.shareImage(MineBusinessCardActivity.this, 0, files, null);
                } else {
                    ToastUtils.showShort("保存成功");
                }
            }
        });
    }

    @Override
    public void onShowQrCodeInfoToView(MineCardEntity entity) {
        mineCardEntity = entity;
        if (null != entity) {
            iv_qr_code.setUri(this, entity.getOrigin_qrcode_full());
        }
    }

    @Override
    public void onShowPosterInfoToView(MinePosterEntity entity) {
        if (null != entity) {
            ImageUtil.downloadImage(this, entity.getUrl(), new ImageUtil.DownImgListener() {
                @Override
                public void onDownSuccess(File file) {
                    savePosterToPic(file);
                }
            });
        }
    }

    @Deprecated
    @Override
    public void onShowUserInfoToView(UserEntity entity) {

    }
}
