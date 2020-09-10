package com.qubuyer.customview;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.text.Editable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.KeyboardUtils;
import com.qubuyer.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTextChanged;
import butterknife.Unbinder;

/**
 * @author Susong
 * @date 创建时间:2019/1/10
 * @description 搜索View
 * & @version
 */
public class SearchView extends FrameLayout {
    private Unbinder unbinder;
    private OnSearchViewActionListener mOnSearchViewActionListener;

    @BindView(R.id.et_search_input)
    EditText et_search_input;
    @BindView(R.id.iv_cancel)
    ImageViewAutoLoad iv_cancel;
    @BindView(R.id.tv_cancel)
    TextView tv_cancel;

    public SearchView(Context context) {
        super(context);
        init();
    }

    public SearchView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public SearchView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        if (isInEditMode()) return;
        inflate(getContext(), R.layout.layout_search_view, this);
        unbinder = ButterKnife.bind(this, this);
        et_search_input.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH || actionId == 0 || actionId == EditorInfo.IME_ACTION_DONE || actionId == EditorInfo.IME_ACTION_GO) {
                    if (!TextUtils.isEmpty(v.getText().toString())) {
                        KeyboardUtils.hideSoftInput(SearchView.this);
                        setInputCursorVisible(false);
                        setInputFocus(false);
                        mOnSearchViewActionListener.onAction(OnSearchViewActionListener.ACTION_DONE, et_search_input.getText().toString());
                    }
                }
                return true;
            }
        });
    }

    @OnClick({R.id.et_search_input, R.id.iv_cancel, R.id.tv_cancel})
    public void onClickByButterknife(View view) {
        switch (view.getId()) {
            case R.id.tv_cancel:
                KeyboardUtils.hideSoftInput(this);
                if (null != mOnSearchViewActionListener) {
                    mOnSearchViewActionListener.onAction(OnSearchViewActionListener.ACTION_BACK, null);
                }
                break;
            case R.id.et_search_input:
                setInputCursorVisible(true);
                setInputFocus(true);
                break;
            case R.id.iv_cancel:
                et_search_input.setText("");
                setInputCursorVisible(false);
                setInputFocus(false);
                KeyboardUtils.hideSoftInput(this);
                if (null != mOnSearchViewActionListener) {
                    mOnSearchViewActionListener.onAction(mOnSearchViewActionListener.ACTION_CLEAR_INPUT, null);
                }
                break;
        }
    }

    public void setInputCursorVisible(boolean visible) {
        if (null != et_search_input) {
            et_search_input.setCursorVisible(visible);
        }
    }

    public void setInputFocus(boolean focus) {
        if (null != et_search_input) {
            if (focus) {
                et_search_input.requestFocus();
            } else {
                et_search_input.clearFocus();
            }
        }
    }

    public void setInputContent(String content) {
        if (null != et_search_input) {
            et_search_input.setText(content);
        }
    }

    public void setInputHintText(String text) {
        if (null != et_search_input) {
            et_search_input.setHint(text);
        }
    }

    public void setCancelTVVisible(boolean visible) {
        if (null != tv_cancel) {
            tv_cancel.setVisibility(visible ? VISIBLE : GONE);
        }
    }

    @OnTextChanged(value = R.id.et_search_input, callback = OnTextChanged.Callback.AFTER_TEXT_CHANGED)
    public void afterTextChangedByButterKnife(Editable s) {
        String content = s.toString();
        if (TextUtils.isEmpty(content)) {
            if (iv_cancel.getVisibility() == View.VISIBLE) {
                AnimatorSet animatorSet = new AnimatorSet();
                animatorSet.setDuration(500);
                animatorSet.playTogether(ObjectAnimator.ofFloat(iv_cancel, "scaleX", 1f, 0f), ObjectAnimator.ofFloat(iv_cancel, "scaleY", 1f, 0f));
                animatorSet.addListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animator animator) {
                        iv_cancel.setVisibility(View.INVISIBLE);
                        iv_cancel.setEnabled(false);
                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {

                    }

                    @Override
                    public void onAnimationRepeat(Animator animation) {

                    }
                });
                animatorSet.start();
                iv_cancel.setEnabled(false);
            }
        } else {
            if (iv_cancel.getVisibility() == View.INVISIBLE) {
                iv_cancel.setEnabled(true);
                iv_cancel.setVisibility(View.VISIBLE);
                AnimatorSet animatorSet = new AnimatorSet();
                animatorSet.setDuration(500);
                iv_cancel.setVisibility(View.VISIBLE);
                animatorSet.playTogether(ObjectAnimator.ofFloat(iv_cancel, "scaleX", 0f, 1f), ObjectAnimator.ofFloat(iv_cancel, "scaleY", 0f, 1f));
                animatorSet.addListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animator animator) {
                        if (null != iv_cancel) {
                            iv_cancel.setEnabled(true);
                        }
                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {

                    }

                    @Override
                    public void onAnimationRepeat(Animator animation) {

                    }
                });
                animatorSet.start();
            }
        }
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (null != unbinder) {
            unbinder.unbind();
        }
    }

    public void setOnSearchViewActionListener(OnSearchViewActionListener actionListener) {
        mOnSearchViewActionListener = actionListener;
    }

    public interface OnSearchViewActionListener {
        int ACTION_BACK = 1;
        int ACTION_DONE = 2;
        int ACTION_CLEAR_INPUT = 3;
        int ACTION_CANCEL = 4;

        void onAction(int action, String result);
    }
}
