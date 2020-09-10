package com.qubuyer.customview;

import android.app.Dialog;
import android.content.Context;

import com.qubuyer.R;


public class BaseDialog extends Dialog {
    protected String TAG = getClass().getSimpleName();

    public BaseDialog(Context context) {
        super(context, R.style.custom_dlg);
    }

    public BaseDialog(Context context, int theme) {
        super(context, theme);
    }

    protected BaseDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }
}
