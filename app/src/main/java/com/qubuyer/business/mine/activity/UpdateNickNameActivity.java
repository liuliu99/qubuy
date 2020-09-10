package com.qubuyer.business.mine.activity;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;

import com.blankj.utilcode.util.ToastUtils;
import com.qubuyer.R;
import com.qubuyer.base.activity.BaseActivity;
import com.qubuyer.bean.mine.UserEntity;
import com.qubuyer.business.mine.presenter.UpdateNicknamePresenter;
import com.qubuyer.business.mine.view.IUpdateNicknameView;
import com.qubuyer.customview.ImageViewAutoLoad;
import com.qubuyer.utils.SessionUtil;

import butterknife.BindView;
import butterknife.OnClick;


/**
 * @author Susong
 * @date 创建时间2019/4/12
 * @description 修改昵称activity
 * @version
 */
public class UpdateNickNameActivity extends BaseActivity<UpdateNicknamePresenter> implements IUpdateNicknameView {
    @BindView(R.id.et_nickname)
    EditText et_nickname;
    @BindView(R.id.iv_delete)
    ImageViewAutoLoad iv_delete;

    @Override
    protected int getContentView() {
        return R.layout.layout_activity_update_nickname;
    }

    @Override
    protected UpdateNicknamePresenter createP(Context context) {
        return new UpdateNicknamePresenter();
    }

    @Override
    protected void initWidget(Bundle savedInstanceState) {
        setTitle("昵称");
        et_nickname.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (null != s && s.length() > 0) {
                    iv_delete.setVisibility(View.VISIBLE);
                } else {
                    iv_delete.setVisibility(View.GONE);
                }
            }
        });
        iv_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                et_nickname.setText("");
            }
        });
    }

    @Override
    protected void initData() {
        setUserInfoToView();
    }

    private void setUserInfoToView() {
        UserEntity entity = SessionUtil.getInstance().getUserInfo();
        if (SessionUtil.getInstance().isLogined() && null != entity) {
            et_nickname.setText(entity.getNickname());
            et_nickname.setSelection(et_nickname.getText().length());
        } else {
            ToastUtils.showShort("用户信息异常");
        }
    }

    @OnClick({R.id.tv_save})
    public void onClickWithButterKnfie(View view) {
        switch (view.getId()) {
            case R.id.tv_save:
                String nickname = et_nickname.getText().toString();
                if (TextUtils.isEmpty(nickname)) {
                    ToastUtils.showShort("请输入昵称");
                    return;
                }
                mPresenter.updateNickname(nickname);
                break;
        }
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
    public void onShoUpdateNicknameResultToView(boolean result) {
        if (result) {
            ToastUtils.showShort("修改用户昵称成功");
            finish();
        }
    }
}
