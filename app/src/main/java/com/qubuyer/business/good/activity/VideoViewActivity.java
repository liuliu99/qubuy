package com.qubuyer.business.good.activity;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.MediaController;
import android.widget.VideoView;

import com.qubuyer.R;
import com.qubuyer.base.activity.BaseActivity;
import com.qubuyer.base.mvp.WrapperPresenter;
import com.qubuyer.constant.AppConstant;
import com.qubuyer.customview.AbsToolbar;
import com.qubuyer.customview.ImageViewAutoLoad;

import butterknife.BindView;
import butterknife.OnClick;


/**
 * @author Susong
 * @date 创建时间2020/1/13
 * @description 视频播放Activity
 * @version
 */
public class VideoViewActivity extends BaseActivity{
    @BindView(R.id.iv_close)
    ImageViewAutoLoad iv_close;
    @BindView(R.id.vv_player)
    VideoView vv_player;

    private String mVideoUrl;
    @Override
    protected int getContentView() {
        return R.layout.layout_activity_videoveiw;
    }

    @Override
    protected WrapperPresenter createP(Context context) {
        return null;
    }

    @Override
    public AbsToolbar toolbar() {
        return null;
    }

    @Override
    protected void initWidget(Bundle savedInstanceState) {
        if (null != getIntent() && null != getIntent().getSerializableExtra(AppConstant.INTENT_EXTRA_KEY)) {
            mVideoUrl = (String) getIntent().getSerializableExtra(AppConstant.INTENT_EXTRA_KEY);
        }
//        setStatusBarVisibility(false);
    }

    @Override
    protected void initData() {
        vv_player.setMediaController(new MediaController(this));
        vv_player.setVideoURI(Uri.parse(mVideoUrl));
        vv_player.start();
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
    protected void onDestroy() {
        vv_player.stopPlayback();
        super.onDestroy();
    }

    @OnClick({R.id.iv_close})
    public void onClickWithButterKnife(View v) {
        switch (v.getId()) {
            case R.id.iv_close:
                finish();
                break;
        }
    }
}
