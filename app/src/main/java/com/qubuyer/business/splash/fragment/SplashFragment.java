package com.qubuyer.business.splash.fragment;

import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;

import com.qubuyer.R;
import com.qubuyer.base.fragment.BaseFragment;
import com.qubuyer.base.mvp.WrapperPresenter;
import com.qubuyer.bean.splash.SplashEntity;
import com.qubuyer.business.category.activity.SecondCategoryActivity;
import com.qubuyer.business.good.activity.GoodDetailActivity;
import com.qubuyer.business.main.MainActivity;
import com.qubuyer.constant.NetConstant;
import com.qubuyer.customview.ImageViewAutoLoad;
import com.qubuyer.utils.HandlerUtil;
import com.qubuyer.utils.NavigationUtil;
import com.qubyer.okhttputil.callback.HttpCallback;
import com.qubyer.okhttputil.helper.HttpInvoker;
import com.qubyer.okhttputil.helper.ServerResponse;

import androidx.fragment.app.Fragment;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;

public class SplashFragment extends BaseFragment implements HandlerUtil.HandleMsgListener {
    //闪屏页图片显示ImageView
    @BindView(R.id.fragment_splash_top_image)
    ImageViewAutoLoad mTopImage;

    @Override
    protected int getLayoutId() {
        return R.layout.layout_fragment_splash;
    }

    @Override
    protected WrapperPresenter createP(Fragment context) {
        return null;
    }

    @Override
    protected void initWidget(View root) {
        super.initWidget(root);
        mTopImage.setVisibility(View.VISIBLE);
        showSplashImg();
    }

    private void showSplashImg() {
        mTopImage.setImageResource(R.drawable.layer_splash);
        getAdImg();
    }

    private void getAdImg() {
        HttpInvoker.createBuilder(NetConstant.GET_SPLASH_AD_URL)
                .setMethodType(HttpInvoker.HTTP_REQUEST_METHOD_GET)
                .setClz(SplashEntity[].class)
                .build().sendAsyncHttpRequest(new HttpCallback() {
            @Override
            public void onHttpFinish(ServerResponse serverResponse, Map<String, String> requestParams, String requestUrl) {
                List<SplashEntity> list = (List<SplashEntity>) serverResponse.getResult();
                SplashEntity entity = null != list && !list.isEmpty() ? list.get(0) : null;
                HandlerUtil.getInstance().setHandleMsgListener(SplashFragment.this);
                if (null == entity || TextUtils.isEmpty(entity.getAd_code_full()) || null == entity.getGoods_id() || 0 == entity.getGoods_id()) {
                    HandlerUtil.getInstance().sendEmptyMessageDelayed(0, 1000);
                } else {
                    mTopImage.setUri(mContext, entity.getAd_code_full());
                    mTopImage.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            NavigationUtil.overlay(getActivity(), GoodDetailActivity.class, entity.getGoods_id());
                        }
                    });
                    HandlerUtil.getInstance().sendEmptyMessageDelayed(0, 1000);
                }
            }
        });
    }

    @Override
    public void handleMsg(Message msg) {
        switch (msg.what) {
            case 0:
//                SecondLevelCache.sharedInstance().clear(PPSH.KEY_WECHAT_LOGIN_UNIONID_CACHE);
//                Intent intent = new Intent(getActivity(), MainActivity.class);
//                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                activityBase.startActivity(intent);
//                activityBase.finish();
//                NavigationUtil.forward(getActivity(), Login1Activity.class);
                NavigationUtil.forward(getActivity(), MainActivity.class);
                break;
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        if (null != mTopImage) {
            Drawable d = mTopImage.getDrawable();
            if (null != d) d.setCallback(null);
            mTopImage.setImageDrawable(null);
            if (Build.VERSION.SDK_INT >= 19) {
                mTopImage.setBackground(null);
            } else {
                mTopImage.setBackgroundDrawable(null);
            }
            mTopImage = null;
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }
}
