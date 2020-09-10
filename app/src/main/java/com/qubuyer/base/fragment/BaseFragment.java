package com.qubuyer.base.fragment;

import android.content.Context;
import android.os.Bundle;
import android.os.Parcelable;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;

import com.blankj.utilcode.util.ToastUtils;
import com.qubuyer.R;
import com.qubuyer.base.activity.BaseActivity;
import com.qubuyer.base.activity.BaseCommonActivity;
import com.qubuyer.base.activity.BaseFragmentActivity;
import com.qubuyer.base.mvp.BaseView;
import com.qubuyer.base.mvp.WrapperPresenter;
import com.qubuyer.constant.AppConstant;
import com.qubuyer.customview.AbsToolbar;
import com.qubuyer.bean.ToolbarMenuEntity;
import com.qubyer.okhttputil.utils.HttpConstant;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import androidx.annotation.AnimRes;
import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * @author Susong
 * @date 创建时间:2018/12/19
 * @description base fragment
 * @version
 */
public abstract class BaseFragment<T extends WrapperPresenter> extends Fragment implements BaseActivity.OnBackPressedListener, OnFragmentResultListener, BaseView {
    protected T mPresenter;
    private BaseFragment last;
    private BaseFragment nextNode;
    public static final int OPER_PUSH = 1;
    public static final int OPER_BACK = 2;
    private int mCurrOper;
    public Context mContext;
    private View mRoot;
    private Unbinder mBind;
    //状态栏资源文件
    private int mStatusDrawable;
    //状态栏颜色
    private int mStatusColor;
    //状态栏是否可见
    private boolean mStatusVisibility = true;
    //返回键按钮图片
    private int mNavigationDrawable;
    //页面标题
    private CharSequence mTitle;
    //页面标题颜色
    private int mTitleColor;
    //Toolbar背景色
    private int mToolbarBackgroundColor;
    //Toolbar菜单栏
    private int mToolbarMenu;
    private List<ToolbarMenuEntity> toolbarMenuEntityList;
    private OnFragmentResultListener onFragmentResultListener;
    //Request Code
    public static final String KEY_REQUEST_CODE = "___request_code___";
    //Result Code
    public static final int RESULT_OK = BaseActivity.RESULT_OK;
    public static final int RESULT_CANCEL = BaseActivity.RESULT_CANCELED;
    public static final int RESULT_FIRST_USER = BaseActivity.RESULT_FIRST_USER;
    private int mResultCode = RESULT_CANCEL;
    //Result Data
    private Bundle mResultData;
    //是否调用了setResult方法
    private boolean mCalledSetResult = false;
    //设置是否更新
    protected boolean isUpdateStatusBar = false;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mContext = null;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initBundle(getArguments());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (mRoot != null) {
            ViewGroup parent = (ViewGroup) mRoot.getParent();
            if (parent != null)
                parent.removeView(mRoot);
        } else {
            mRoot = inflater.inflate(getLayoutId(), container, false);
            // Do something
            onBindViewBefore(mRoot);
            // Bind view
            mBind = ButterKnife.bind(this, mRoot);
            mPresenter = createP(this);
            // Get savedInstanceState
            if (savedInstanceState != null)
                onRestartInstance(savedInstanceState);
            if (null != mPresenter) {
                mPresenter.attachView(this);
            }
            // Init
            initWidget(mRoot);
            initData();
        }
        return mRoot;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (!needSetItemClick()) {
            if (getActivity() instanceof BaseActivity) {
                ((BaseActivity) getActivity()).setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        BaseFragment.this.onMenuItemClick(item);
                        return true;
                    }
                });
                ((BaseActivity) getActivity()).setOnTitleOnClickListener(new AbsToolbar.OnTitleOnClickListener() {
                    @Override
                    public void onTitleOnClick(View view) {
                        BaseFragment.this.onTitleClick(view);
                    }
                });
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (getActivity() instanceof BaseActivity && needUpdateStatusBar()) {
            if (0 != mStatusDrawable) {
                ((BaseActivity) getActivity()).setStatusBarDrawable(mStatusDrawable);
            }
            if (0 != mStatusColor) {
                ((BaseActivity) getActivity()).setStatusBarColor(mStatusColor);
            }
            ((BaseActivity) getActivity()).setStatusBarVisibility(mStatusVisibility);
        }
        if ((getActivity() instanceof BaseActivity || getActivity() instanceof BaseCommonActivity) && needUpdateToolbar()) {
                if (0 != mNavigationDrawable) {
                ((BaseActivity) getActivity()).setNavigationDrawable(mNavigationDrawable);
            } else {
                ((BaseActivity) getActivity()).setNavigationDrawable(R.drawable.icon_return_black);
            }
            if (!TextUtils.isEmpty(mTitle)) {
                getActivity().setTitle(mTitle);
            } else {
                getActivity().setTitle("");
            }
            if (0 != mTitleColor) {
                getActivity().setTitleColor(mTitleColor);
            } else {
                getActivity().setTitleColor(R.color.white);
            }
            if (0 != mToolbarBackgroundColor) {
                ((BaseActivity) getActivity()).setToolbarBackgroundColor(mToolbarBackgroundColor);
            }
            if (0 != mToolbarMenu) {
                ((BaseActivity) getActivity()).inflateMenu(mToolbarMenu);
            } else {
                ((BaseActivity) getActivity()).clearMenu();
            }
            if (null != toolbarMenuEntityList && !toolbarMenuEntityList.isEmpty()) {
                ((BaseActivity) getActivity()).inflateMenu(toolbarMenuEntityList);
            } else {
                ((BaseActivity) getActivity()).clearMenu();
            }
        }
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {
            if (getActivity() instanceof BaseActivity && needUpdateStatusBar()) {
                if (0 != mStatusDrawable) {
                    ((BaseActivity) getActivity()).setStatusBarDrawable(mStatusDrawable);
                }
                if (0 != mStatusColor) {
                    ((BaseActivity) getActivity()).setStatusBarColor(mStatusColor);
                }
                ((BaseActivity) getActivity()).setStatusBarVisibility(mStatusVisibility);
            }
            if (getActivity() instanceof BaseActivity && needUpdateToolbar()) {
                if (0 != mNavigationDrawable) {
                    ((BaseActivity) getActivity()).setNavigationDrawable(mNavigationDrawable);
                } else {
                    ((BaseActivity) getActivity()).setNavigationDrawable(R.drawable.icon_return_black);
                }
                if (!TextUtils.isEmpty(mTitle)) {
                    getActivity().setTitle(mTitle);
                } else {
                    getActivity().setTitle("");
                }
                if (0 != mTitleColor) {
                    getActivity().setTitleColor(mTitleColor);
                } else {
                    getActivity().setTitleColor(R.color.white);
                }
                if (0 != mToolbarBackgroundColor) {
                    ((BaseActivity) getActivity()).setToolbarBackgroundColor(mToolbarBackgroundColor);
                }
                if (0 != mToolbarMenu) {
                    ((BaseActivity) getActivity()).inflateMenu(mToolbarMenu);
                } else {
                    ((BaseActivity) getActivity()).clearMenu();
                }
                if (null != toolbarMenuEntityList && !toolbarMenuEntityList.isEmpty()) {
                    ((BaseActivity) getActivity()).inflateMenu(toolbarMenuEntityList);
                } else {
                    ((BaseActivity) getActivity()).clearMenu();
                }
            }
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (mBind != null && mBind != Unbinder.EMPTY) {
            try {
                mBind.unbind();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (mPresenter != null) {
            mPresenter.detachView();
            mPresenter.destoryModel();
        }
    }

    public View getBaseContentView() {
        return ((BaseActivity)mContext).getBaseContentView();
    }

    public void setStatusBarDrawable(int drawableId) {
        mStatusDrawable = drawableId;
    }

    public void setStatusBarColor(@ColorInt int colorId) {
        mStatusColor = colorId;
    }

    public void setStatusBarVisibility(boolean isVisibility) {
        mStatusVisibility = isVisibility;
    }

    public void setTitle(CharSequence title) {
        mTitle = title;
    }

    public void setTitle(@StringRes int resId) {
        setTitle(getString(resId));
    }

    public void setTitleColor(int colorId) {
        mTitleColor = colorId;
    }

    public void setNavigationDrawable(int drawableId) {
        mNavigationDrawable = drawableId;
    }

    public void setToolbarBackgroundColor(int colorId) {
        mToolbarBackgroundColor = colorId;
    }

    public void setToolbarMenu(int menuId) {
        mToolbarMenu = menuId;
    }

    public void setToolbarMenu(List<ToolbarMenuEntity> toolbarMenuEntities) {
        toolbarMenuEntityList = toolbarMenuEntities;
    }

    public boolean onNavigationClicked(View view) {
        if (getActivity() instanceof BaseFragmentActivity) {
            ((BaseFragmentActivity) getActivity()).pop();
            return true;
        }
        return false;
    }

    public void setOnBackPressedListener() {
        if (getActivity() instanceof BaseFragmentActivity) {
            ((BaseFragmentActivity) getActivity()).setOnBackPressedListener(this);
        }
    }

    protected void onBindViewBefore(View root) {
    }

    protected abstract int getLayoutId();

    /**
     * 请求数据,子类选择实现
     */
    protected abstract T createP(Fragment context);

    protected void initBundle(Bundle bundle) {
    }

    protected void initWidget(View root) {
    }

    protected void initData() {
    }

    protected void onRestartInstance(Bundle savedInstanceState) {
    }

    public String getTag1() {
        return this.getClass().getName();
    }

    /**
     * 是否需要手动调用Activity #setOnMenuItemClickListener()方法
     *
     * @return
     */
    protected boolean needSetItemClick() {
        return false;
    }

    protected void onMenuItemClick(MenuItem item) {
    }

    protected void onTitleClick(View view){}

    protected boolean needUpdateStatusBar() {
        return isUpdateStatusBar;
    }

    protected boolean needUpdateToolbar() {
        return true;
    }

    public final void push(Class<? extends BaseFragment> target, Map<String, Object> data) {
        if (getActivity() instanceof BaseFragmentActivity) {
            ((BaseFragmentActivity) getActivity()).push(target, data);
        }
    }

    public final void push(@AnimRes int enterAnimation, @AnimRes int exitAnimation, @NonNull Class<? extends BaseFragment> target, Map<String, Object> data) {
        if (getActivity() instanceof BaseFragmentActivity) {
            ((BaseFragmentActivity) getActivity()).push(enterAnimation, exitAnimation, target, data);
        }
    }

    public final void push(@AnimRes int enterAnimation, @AnimRes int exitAnimation, @NonNull Class<? extends BaseFragment> target) {
        if (getActivity() instanceof BaseFragmentActivity) {
            ((BaseFragmentActivity) getActivity()).push(enterAnimation, exitAnimation, target, null);
        }
    }

    public final void push(Class<? extends BaseFragment> target) {
        push(target, null);
    }

    public final void pushForResult(@AnimRes int enterAnimation, @AnimRes int exitAnimation, @NonNull Class<? extends BaseFragment> cls, Map<String, Object> data) {
        if (getActivity() instanceof BaseFragmentActivity) {
            ((BaseFragmentActivity) getActivity()).pushForResult(enterAnimation, exitAnimation, cls, data);
        }
    }

    public final void pop() {
        if (getActivity() instanceof BaseFragmentActivity) {
            ((BaseFragmentActivity) getActivity()).pop();
        }
    }

    public final void popToFragment(@NonNull Class<? extends BaseFragment> cls) {
        if (getActivity() instanceof BaseFragmentActivity) {
            ((BaseFragmentActivity) getActivity()).popToFragment(cls);
        }
    }

    public final void clearFragment(@NonNull Class<? extends BaseFragment> cls) {
        if (getActivity() instanceof BaseFragmentActivity) {
            ((BaseFragmentActivity) getActivity()).clearFragment(cls);
        }
    }

    public BaseFragment getLastNode() {
        return last;
    }

    public BaseFragment getNextNode() {
        return nextNode;
    }

    public void setLastNode(BaseFragment last) {
        this.last = last;
    }

    public void setNextNode(BaseFragment next) {
        nextNode = next;
    }

    public void setCurrOper(int currOper) {
        mCurrOper = currOper;
    }

    public int getCurrOper() {
        return mCurrOper;
    }

    public void setOnFragmentResultListener(OnFragmentResultListener onFragmentResultListener) {
        this.onFragmentResultListener = onFragmentResultListener;
    }

    public void onFragmentResult(int requestCode, int resultCode, Bundle data) {

    }

    public int obtainIntegerFromArgs(String key, int defaultValue) {
        Bundle data = getArguments();
        if (null != data) {
            return data.getInt(key);
        }
        return defaultValue;
    }

    public <T extends Parcelable> ArrayList<T> obtainParcelableArrayList(String key) {
        Bundle data = getArguments();
        if (null != data) {
            return data.getParcelableArrayList(key);
        }
        return null;
    }

    @Override
    public boolean onBackPressed() {
        return false;
    }

    public void showToastCenter(String msg) {
        //提示已发送
        ToastUtils.setBgResource(R.drawable.shape_toast_bg);
        ToastUtils.setMsgColor(getResources().getColor(R.color.white));
        ToastUtils.setGravity(Gravity.CENTER, 0, 0);
        ToastUtils.showShort(msg);
    }

    public final void startFragmentForResult(int requestCode, Class<? extends BaseFragment> target, Map<String, Object> data) {
        if (null == data) data = new HashMap<>();
        data.put(KEY_REQUEST_CODE, requestCode);
        pushForResult(R.anim.open_right_in, R.anim.close_left_out, target, data);
    }

    public final void setResult(int resultCode, Bundle data) {
        mResultCode = resultCode;
        mResultData = data;
        mCalledSetResult = true;
    }

    public final void finish() {
        if (!mCalledSetResult) throw new RuntimeException("请先调用setResult方法");
        pop();
        FragmentManager manager = getActivity().getSupportFragmentManager();
        manager.beginTransaction().remove(this).commitAllowingStateLoss();
        // 隐藏软键盘
        final InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(getView().getWindowToken(), 0);
        if (null != onFragmentResultListener) {
            onFragmentResultListener.onFragmentResult(obtainIntegerFromArgs(KEY_REQUEST_CODE, -1), mResultCode, mResultData);
        }
    }

    public void clearMenu() {
        if (getActivity() instanceof BaseActivity) {
            ((BaseActivity) getActivity()).clearMenu();
        }
    }

    @Override
    public void doResponseError(int code, String message) {
        if (AppConstant.NET_RESPONSE_CODE.get(code) != null) {
            if (code == AppConstant.CODE_NO_LOGIN) {
                ToastUtils.showShort("请登录");
            }
        } else {
            if (code != HttpConstant.Status.SERVER_STATUS_SUCCESSFUL && onNeedCallSuper()) {
                if (TextUtils.isEmpty(message)) {
                    message = getString(R.string.server_busy_retry_later);
                }
                ToastUtils.showShort(message);
            }
        }
    }

    /**
     * 如果不需要使用父类提示,请重写该方法
     */
    protected boolean onNeedCallSuper() {
        return true;
    }

    /**
     * 显示加载Dialog
     *
     * @return 当前的对话框对象
     */
    public void showLoadingDialog() {
        if (getActivity() instanceof BaseActivity) {
            ((BaseActivity) getActivity()).showLoadingDialog();
        }
    }

    /**
     * 关闭加载Dialog
     */
    public void dissmissLoadingDialog() {
        if (getActivity() instanceof BaseActivity) {
            ((BaseActivity) getActivity()).dissmissLoadingDialog();
        }
    }
}
