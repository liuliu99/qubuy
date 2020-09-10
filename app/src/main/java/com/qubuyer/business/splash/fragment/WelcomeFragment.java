package com.qubuyer.business.splash.fragment;

import android.os.Bundle;
import android.os.Message;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.qubuyer.R;
import com.qubuyer.base.fragment.BaseFragment;
import com.qubuyer.base.mvp.WrapperPresenter;
import com.qubuyer.business.main.MainActivity;
import com.qubuyer.business.splash.activity.SplashActivity;
import com.qubuyer.customview.ImageViewAutoLoad;
import com.qubuyer.utils.HandlerUtil;
import com.qubuyer.utils.NavigationUtil;

import androidx.annotation.Nullable;
import androidx.core.view.GestureDetectorCompat;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;
import butterknife.BindView;

public class WelcomeFragment extends BaseFragment implements ViewPager.OnPageChangeListener, HandlerUtil.HandleMsgListener {
    private int[] mWelPics = new int[]{R.drawable.w1, R.drawable.w2, R.drawable.w3};
    private int mCurrentSelectedPos;
    private GestureDetectorCompat mGestureDetector;
    private boolean mJumpOver;
    //    private LinearLayout mPagerIndicatorContainer;
//    private ImageView mSelectedIndicatorView;
    @BindView(R.id.viewPager)
    ViewPager mViewPager;

    @Override
    protected int getLayoutId() {
        return R.layout.layout_fragment_welcome;
    }

    @Override
    protected WrapperPresenter createP(Fragment context) {
        return null;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        HandlerUtil.getInstance().setHandleMsgListener(this);
        HandlerUtil.getInstance().sendEmptyMessageDelayed(0, 100);
    }

    @Override
    public void handleMsg(Message msg) {
//        mSelectedIndicatorView = (ImageView) mPagerIndicatorContainer.getChildAt(0);
        mViewPager.addOnPageChangeListener(this);
        mViewPager.setOffscreenPageLimit(2);
        mViewPager.setAdapter(new PagerAdapter() {

            @Override
            public int getCount() {
                return mWelPics.length;
            }

            @Override
            public boolean isViewFromObject(View view, Object object) {
                return view == object;
            }

            @Override
            public Object instantiateItem(ViewGroup container, int position) {
                View pagerItemView = LayoutInflater.from(container.getContext()).inflate(R.layout.layout_welcome_page, container, false);
                ImageViewAutoLoad welcomePageView = pagerItemView.findViewById(R.id.welcomePageView);
//                welcomePageView.setDrawableId(getActivity(), mWelPics[position]);
                welcomePageView.setImageResource(mWelPics[position]);
                if (position == mWelPics.length - 1) {
                    welcomePageView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            NavigationUtil.forward(getActivity(), MainActivity.class);
                        }
                    });
                }
                container.addView(pagerItemView);
                return pagerItemView;
            }

            @Override
            public void destroyItem(ViewGroup container, int position, Object object) {
                container.removeView((View) object);
            }
        });
        mGestureDetector = new GestureDetectorCompat(getActivity(), new GestureDetector.SimpleOnGestureListener() {
            @Override
            public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
                if (mCurrentSelectedPos == mWelPics.length - 1) {
                    if (mJumpOver) {
                        return true;
                    }
                    if (distanceX > 0) {
                        mJumpOver = true;
                    }
                }
                return super.onScroll(e1, e2, distanceX, distanceY);
            }
        });
        ((SplashActivity) getActivity()).setOnActivityDispatchTouchEventListener(new SplashActivity.OnActivityDispatchTouchEventListener() {
            @Override
            public boolean onActivityDispatchTouchEvent(MotionEvent ev) {
                mGestureDetector.onTouchEvent(ev);
                if (mJumpOver) {
                    ((SplashActivity) getActivity()).setOnActivityDispatchTouchEventListener(null);
//                            addFragment(R.id.activitySplashContainer, new SplashFragment());
//                            removeFragmentWithNoAnim(WelcomeFragment.this);
                    NavigationUtil.forward(getActivity(), MainActivity.class);
                }
                return false;
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (null != mViewPager) {
            mViewPager.removeAllViews();
            mViewPager.setAdapter(null);
        }
        mWelPics = null;
        mGestureDetector = null;
        HandlerUtil.getInstance().removeMessages(0);
        mViewPager = null;
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        mCurrentSelectedPos = position;
//        mSelectedIndicatorView.setImageResource(R.drawable.unselected);
//        ImageView selectedChild = (ImageView) mPagerIndicatorContainer.getChildAt(position);
//        selectedChild.setImageResource(R.drawable.selected);
//        mSelectedIndicatorView = selectedChild;
        if (position == mWelPics.length - 1) {
//            nextPageTextView.setVisibility(View.VISIBLE);
//            mPagerIndicatorContainer.setVisibility(View.GONE);
        } else {
//            nextPageTextView.setVisibility(View.GONE);
//            mPagerIndicatorContainer.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }
}
