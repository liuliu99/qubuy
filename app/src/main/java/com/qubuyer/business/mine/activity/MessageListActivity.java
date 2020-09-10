package com.qubuyer.business.mine.activity;

import android.content.Context;
import android.os.Bundle;

import com.google.android.material.tabs.TabLayout;
import com.qubuyer.R;
import com.qubuyer.base.activity.BaseActivity;
import com.qubuyer.business.category.presenter.SecondCategoryGoodPresenter;
import com.qubuyer.business.mine.adapter.MessageListPagerAdapter;
import com.qubuyer.business.mine.view.MessageListViewPage;
import com.qubuyer.customview.EnhanceTabLayout;

import java.util.ArrayList;
import java.util.List;

import androidx.viewpager.widget.ViewPager;
import butterknife.BindView;


/**
 * @date 创建时间:2019/4/15
 * @author Susong
 * @description 消息列表activity
 & @version
 */
public class MessageListActivity extends BaseActivity implements ViewPager.OnPageChangeListener{
    @BindView(R.id.etl_tab)
    EnhanceTabLayout etl_tab;
    @BindView(R.id.vp_page)
    ViewPager vp_page;

    private MessageListPagerAdapter mViewPagerAdapter;

    @Override
    protected int getContentView() {
        return R.layout.layout_activity_message;
    }

    @Override
    protected SecondCategoryGoodPresenter createP(Context context) {
        return new SecondCategoryGoodPresenter();
    }

    @Override
    protected void initWidget(Bundle savedInstanceState) {
        setTitle("消息");
        initTabAndViewPager();
    }

    @Override
    protected void initData() {
    }

    private void initTabAndViewPager() {
        ArrayList<String> titleList = new ArrayList<>();
        titleList.add("活动通知");
        titleList.add("系统消息");
        List<MessageListViewPage> pageList = new ArrayList<>();
        MessageListViewPage page2 = new MessageListViewPage(this, 1);
        page2.getView();
        MessageListViewPage page3 = new MessageListViewPage(this, 2);
        page3.getView();
        pageList.add(page2);
        pageList.add(page3);
        for (int i = 0; i < titleList.size(); i++) {
            etl_tab.addTab(titleList.get(i));
        }
        mViewPagerAdapter = new MessageListPagerAdapter(this, pageList);
        vp_page.setAdapter(mViewPagerAdapter);
        vp_page.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(etl_tab.getTabLayout()));
        vp_page.addOnPageChangeListener(this);
        etl_tab.setupWithViewPager(vp_page);
        onPageSelected(0);
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
        super.onDestroy();
        if (null != mViewPagerAdapter && null != mViewPagerAdapter.getPageList() && mViewPagerAdapter.getPageList().size() > 0) {
            for (MessageListViewPage temp : mViewPagerAdapter.getPageList()) {
                temp.destory();
            }
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        if (null != mViewPagerAdapter && !mViewPagerAdapter.getPageList().isEmpty()) {
            mViewPagerAdapter.getPageList().get(position).loadData();
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
