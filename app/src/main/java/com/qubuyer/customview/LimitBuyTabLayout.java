package com.qubuyer.customview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.os.Build;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.material.tabs.TabLayout;
import com.qubuyer.R;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.viewpager.widget.ViewPager;

/**
 * @author Susong
 * @date 创建时间2019/3/20
 * @description 限时购Tablayout
 */
public class LimitBuyTabLayout extends FrameLayout {
    private TabLayout mTabLayout;
    private List<String> mTabList;
    private List<View> mCustomViewList;
    private int mTabMode;

    public LimitBuyTabLayout(@NonNull Context context) {
        super(context);
        init(context, null);
    }

    public LimitBuyTabLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public LimitBuyTabLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public LimitBuyTabLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context, attrs);
    }

    private void readAttr(Context context, AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.EnhanceTabLayout);
        mTabMode = typedArray.getInt(R.styleable.EnhanceTabLayout_tab_Mode, 1);
        typedArray.recycle();
    }

    private void init(Context context, AttributeSet attrs) {
        readAttr(context, attrs);
        mTabList = new ArrayList<>();
        mCustomViewList = new ArrayList<>();
        View view = LayoutInflater.from(getContext()).inflate(R.layout.layout_enhance_tablayout, this, true);
        mTabLayout = view.findViewById(R.id.tl_tab);
        // 添加属性
        mTabLayout.setTabMode(mTabMode == 1 ? TabLayout.MODE_FIXED : TabLayout.MODE_SCROLLABLE);
        mTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                // onTabItemSelected(tab.getPosition());
                // Tab 选中之后，改变各个Tab的状态
                for (int i = 0; i < mTabLayout.getTabCount(); i++) {
                    View view = mTabLayout.getTabAt(i).getCustomView();
                    if (view == null) {
                        return;
                    }
                    LinearLayout rl_container = view.findViewById(R.id.rl_container);
                    TextView tv_time = view.findViewById(R.id.tv_time);
                    TextView tv_status = view.findViewById(R.id.tv_status);
                    if (i == tab.getPosition()) { // 选中状态
//                        rl_container.setBackgroundColor(Color.parseColor("#FF681D"));
//                        view.setBackgroundColor(Color.parseColor("#FF681D"));
                        tv_time.setTextColor(Color.parseColor("#FF681D"));
                        tv_status.setTextColor(Color.parseColor("#FF681D"));
                    } else {// 未选中状态
//                        rl_container.setBackgroundColor(Color.parseColor("#FFFFFF"));
//                        view.setBackgroundColor(Color.parseColor("#FFFFFF"));
                        tv_time.setTextColor(Color.parseColor("#3F4346"));
                        tv_status.setTextColor(Color.parseColor("#666666"));
                    }
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    public List<View> getCustomViewList() {
        return mCustomViewList;
    }

    public void addOnTabSelectedListener(TabLayout.OnTabSelectedListener onTabSelectedListener) {
        mTabLayout.addOnTabSelectedListener(onTabSelectedListener);
    }

    /**
     * 与TabLayout 联动
     *
     * @param viewPager
     */
    public void setupWithViewPager(@Nullable ViewPager viewPager) {
        mTabLayout.addOnTabSelectedListener(new ViewPagerOnTabSelectedListener(viewPager, this));
    }

    /**
     * retrive TabLayout Instance
     *
     * @return
     */
    public TabLayout getTabLayout() {
        return mTabLayout;
    }

    /**
     * 添加tab
     */
    public void addTab(String time, String status) {
        View customView = getTabView(getContext(), time, status);
        mCustomViewList.add(customView);
        mTabLayout.addTab(mTabLayout.newTab().setCustomView(customView));
    }

    public static class ViewPagerOnTabSelectedListener implements TabLayout.OnTabSelectedListener {

        private final ViewPager mViewPager;
        private final WeakReference<LimitBuyTabLayout> mTabLayoutRef;

        public ViewPagerOnTabSelectedListener(ViewPager viewPager, LimitBuyTabLayout enhanceTabLayout) {
            mViewPager = viewPager;
            mTabLayoutRef = new WeakReference<>(enhanceTabLayout);
        }

        @Override
        public void onTabSelected(TabLayout.Tab tab) {
            mViewPager.setCurrentItem(tab.getPosition());
            LimitBuyTabLayout mTabLayout = mTabLayoutRef.get();
            List<View> customViewList = mTabLayout.getCustomViewList();
            if (customViewList == null || customViewList.size() == 0) {
                return;
            }
            for (int i = 0; i < customViewList.size(); i++) {
                View view = customViewList.get(i);
                if (view == null) {
                    return;
                }
                LinearLayout rl_container = view.findViewById(R.id.rl_container);
                TextView tv_time = view.findViewById(R.id.tv_time);
                TextView tv_status = view.findViewById(R.id.tv_status);
                if (i == tab.getPosition()) { // 选中状态
//                    rl_container.setBackgroundColor(Color.parseColor("#FF681D"));
                    tv_time.setTextColor(Color.parseColor("#FFFFFF"));
                    tv_status.setTextColor(Color.parseColor("#FFFFFF"));
                } else {// 未选中状态
//                    rl_container.setBackgroundColor(Color.parseColor("#FFFFFF"));
                    tv_time.setTextColor(Color.parseColor("#3F4346"));
                    tv_status.setTextColor(Color.parseColor("#666666"));
                }
            }
        }

        @Override
        public void onTabUnselected(TabLayout.Tab tab) {
        }

        @Override
        public void onTabReselected(TabLayout.Tab tab) {
        }
    }

    /**
     * 获取Tab 显示的内容
     *
     * @param context
     * @param
     * @return
     */
    public static View getTabView(Context context, String time, String status) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_limit_buy_tab_layout, null);
        TextView tv_time = view.findViewById(R.id.tv_time);
        tv_time.setText(time);
        TextView tv_status = view.findViewById(R.id.tv_status);
        tv_status.setText(status);
        return view;
    }
}