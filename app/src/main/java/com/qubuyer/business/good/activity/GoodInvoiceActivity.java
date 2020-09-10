package com.qubuyer.business.good.activity;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.blankj.utilcode.util.ToastUtils;
import com.qubuyer.R;
import com.qubuyer.base.activity.BaseActivity;
import com.qubuyer.base.mvp.WrapperPresenter;
import com.qubuyer.bean.event.SOSelectInvoiceEvent;
import com.qubuyer.bean.mine.MineInvoiceEntitiy;
import com.qubuyer.constant.AppConstant;
import com.qubuyer.utils.EventBusUtil;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @date 创建时间:2019/3/26
 * @author Susong
 * @description 发票Activity
 & @version
 */
public class GoodInvoiceActivity extends BaseActivity{
    @BindView(R.id.rd_type)
    RadioGroup rd_type;
    @BindView(R.id.rb_type_no_use)
    RadioButton rb_type_no_use;
    @BindView(R.id.rb_type_use)
    RadioButton rb_type_use;

    @BindView(R.id.rd_title_type)
    RadioGroup rd_title_type;
    @BindView(R.id.rb_person_title)
    RadioButton rb_person_title;
    @BindView(R.id.rb_company_title)
    RadioButton rb_company_title;

    @BindView(R.id.ll_company)
    LinearLayout ll_company;
    @BindView(R.id.et_company_name)
    EditText et_company_name;
    @BindView(R.id.et_duty_paragraph)
    EditText et_duty_paragraph;


    @BindView(R.id.et_persona_phone)
    EditText et_persona_phone;
    @BindView(R.id.et_email)
    EditText et_email;

    @BindView(R.id.tv_save)
    TextView tv_save;

    @BindView(R.id.tv_title_title)
    TextView tv_title_title;
    @BindView(R.id.ll_psersonal_phone)
    LinearLayout ll_psersonal_phone;
    @BindView(R.id.v_line_one)
    View v_line_one;
    @BindView(R.id.ll_email)
    LinearLayout ll_email;
    @BindView(R.id.ll_desc)
    LinearLayout ll_desc;

    //是否使用发票 0:不开发票 1:电子发票
    private int mUseInvoice = 0;
    //发票类型 1:个人 2:单位
    private int mTitleType = 1;

    private MineInvoiceEntitiy mineInvoiceEntitiy;

    @Override
    protected int getContentView() {
        return R.layout.layout_activity_invoice;
    }

    @Override
    protected WrapperPresenter createP(Context context) {
        return null;
    }

    @Override
    protected void initWidget(Bundle savedInstanceState) {
        if (null != getIntent() && null != getIntent().getSerializableExtra(AppConstant.INTENT_EXTRA_KEY)) {
            mineInvoiceEntitiy = (MineInvoiceEntitiy) getIntent().getSerializableExtra(AppConstant.INTENT_EXTRA_KEY);
        }
        rd_type.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rb_type_no_use: //不开发票
                        mUseInvoice = 0;
                        tv_title_title.setVisibility(View.GONE);
                        rd_title_type.setVisibility(View.GONE);
                        ll_company.setVisibility(View.GONE);
                        ll_psersonal_phone.setVisibility(View.GONE);
                        v_line_one.setVisibility(View.GONE);
                        ll_email.setVisibility(View.GONE);
                        ll_desc.setVisibility(View.GONE);
                        break;
                    case R.id.rb_type_use: //电子发票
                        mUseInvoice = 1;
                        tv_title_title.setVisibility(View.VISIBLE);
                        rd_title_type.setVisibility(View.VISIBLE);
                        switch (mTitleType) {
                            case 1://个人
                                ll_company.setVisibility(View.GONE);
                                break;
                            case 2://单位
                                ll_company.setVisibility(View.VISIBLE);
                                break;
                        }
                        ll_psersonal_phone.setVisibility(View.VISIBLE);
                        v_line_one.setVisibility(View.VISIBLE);
                        ll_email.setVisibility(View.VISIBLE);
                        ll_desc.setVisibility(View.VISIBLE);
                        break;
                }
            }
        });

        tv_title_title.setVisibility(View.GONE);
        rd_title_type.setVisibility(View.GONE);
        ll_company.setVisibility(View.GONE);
        ll_psersonal_phone.setVisibility(View.GONE);
        v_line_one.setVisibility(View.GONE);
        ll_email.setVisibility(View.GONE);
        ll_desc.setVisibility(View.GONE);

        rd_title_type.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rb_person_title: //个人
                        mTitleType = 1;
                        rb_person_title.setChecked(true);
                        ll_company.setVisibility(View.GONE);
                        break;
                    case R.id.rb_company_title: //单位
                        mTitleType = 2;
                        rb_company_title.setChecked(true);
                        ll_company.setVisibility(View.VISIBLE);
                        break;
                }
            }
        });
        if (null != mineInvoiceEntitiy) {
            mUseInvoice = mineInvoiceEntitiy.getIsUse();
            mTitleType = mineInvoiceEntitiy.getType();
            if (mTitleType == 0) {
                mTitleType = 1;
            }
            switch (mUseInvoice) {
                case 0:
                    tv_title_title.setVisibility(View.GONE);
                    rd_title_type.setVisibility(View.GONE);
                    ll_company.setVisibility(View.GONE);
                    ll_psersonal_phone.setVisibility(View.GONE);
                    v_line_one.setVisibility(View.GONE);
                    ll_email.setVisibility(View.GONE);
                    ll_desc.setVisibility(View.GONE);

                    rb_type_no_use.setChecked(true);
                    break;
                case 1:
                    tv_title_title.setVisibility(View.VISIBLE);
                    rd_title_type.setVisibility(View.VISIBLE);
                    switch (mTitleType) {
                        case 1://个人
                            ll_company.setVisibility(View.GONE);
                            break;
                        case 2://单位
                            ll_company.setVisibility(View.VISIBLE);
                            break;
                    }
                    ll_psersonal_phone.setVisibility(View.VISIBLE);
                    v_line_one.setVisibility(View.VISIBLE);
                    ll_email.setVisibility(View.VISIBLE);
                    ll_desc.setVisibility(View.VISIBLE);

                    rb_type_use.setChecked(true);
                    break;
            }
            switch (mTitleType) {
                case 1:
                    rb_person_title.setChecked(true);
                    break;
                case 2:
                    rb_company_title.setChecked(true);
                    et_company_name.setText(mineInvoiceEntitiy.getCompanyName());
                    et_duty_paragraph.setText(mineInvoiceEntitiy.getCompanyDutyparagraph());
                    break;
            }
            et_persona_phone.setText(mineInvoiceEntitiy.getPhone());
            et_email.setText(mineInvoiceEntitiy.getEmail());
        }
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

    @OnClick({R.id.tv_save})
    public void onClickWithButterKnife(View v) {
        switch (v.getId()) {
            case R.id.tv_save:
                MineInvoiceEntitiy mineInvoiceEntitiy = new MineInvoiceEntitiy();
                if (mUseInvoice == 1) {
                    mineInvoiceEntitiy.setIsUse(mUseInvoice);
                    mineInvoiceEntitiy.setType(mTitleType);
                    switch (mTitleType) {
                        case 2:
                            String companyName = et_company_name.getText().toString();
                            if (TextUtils.isEmpty(companyName)) {
                                ToastUtils.showShort("请填写单位名称");
                                return;
                            }
                            mineInvoiceEntitiy.setCompanyName(companyName);
                            String dutyParagraph = et_duty_paragraph.getText().toString();
                            if (TextUtils.isEmpty(dutyParagraph)) {
                                ToastUtils.showShort("请填写纳税人识别号");
                                return;
                            }
                            mineInvoiceEntitiy.setCompanyDutyparagraph(dutyParagraph);
                            break;
                    }
                    String phone = et_persona_phone.getText().toString();
                    if (TextUtils.isEmpty(phone)) {
                        ToastUtils.showShort("请填写收票人手机");
                        return;
                    }
                    mineInvoiceEntitiy.setPhone(phone);
                    String email = et_email.getText().toString();
                    mineInvoiceEntitiy.setEmail(email);
                    mineInvoiceEntitiy.setContent("商品明细");
                } else {
                    mineInvoiceEntitiy.setIsUse(mUseInvoice);
                }
                EventBusUtil.sendEvent(new SOSelectInvoiceEvent(mineInvoiceEntitiy));
                finish();
                break;
        }
    }
}
