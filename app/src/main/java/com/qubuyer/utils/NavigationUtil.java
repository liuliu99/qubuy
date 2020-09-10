package com.qubuyer.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;

import com.qubuyer.constant.AppConstant;

import java.io.Serializable;

/**
 * @date 创建时间:2018/12/21
 * @author Susong
 * @description 控制Activity跳转工具类
 & @version
 */
public class NavigationUtil {

    public static <T> T getParcelableExtra(Activity activity) {
        Parcelable parcelable = activity.getIntent().getParcelableExtra(AppConstant.INTENT_EXTRA_KEY);
        activity = null;
        return (T) parcelable;
    }

    public static void overlay(Context context, Class<? extends Activity> targetClazz, int flags, Parcelable parcelable) {
        Intent intent = new Intent(context, targetClazz);
        setFlags(intent, flags);
        putParcelableExtra(intent, parcelable);
        context.startActivity(intent);
        context = null;
    }

    public static void overlay(Context context, Class<? extends Activity> targetClazz, Parcelable parcelable) {
        Intent intent = new Intent(context, targetClazz);
        putParcelableExtra(intent, parcelable);
        context.startActivity(intent);
        context = null;
    }

    public static void overlay(Context context, Class<? extends Activity> targetClazz, Serializable serializable) {
        Intent intent = new Intent(context, targetClazz);
        putSerializableExtra(intent, serializable);
        context.startActivity(intent);
        context = null;
    }

    public static void overlay(Context context, Class<? extends Activity> targetClazz) {
        Intent intent = new Intent(context, targetClazz);
        context.startActivity(intent);
        context = null;
    }

    public static void forward(Context context, Class<? extends Activity> targetClazz, int flags) {
        Intent intent = new Intent(context, targetClazz);
        setFlags(intent, flags);
        context.startActivity(intent);
        if (isActivity(context)) return;
        ((Activity) context).finish();
        context = null;
    }

    public static void forward(Context context, Class<? extends Activity> targetClazz, int flags, Parcelable parcelable) {
        Intent intent = new Intent(context, targetClazz);
        setFlags(intent, flags);
        intent.putExtra(AppConstant.INTENT_EXTRA_KEY, parcelable);
        context.startActivity(intent);
        if (isActivity(context)) return;
        ((Activity) context).finish();
        context = null;
    }

    public static void forward(Context context, Class<? extends Activity> targetClazz, Parcelable parcelable) {
        Intent intent = new Intent(context, targetClazz);
        putParcelableExtra(intent, parcelable);
        context.startActivity(intent);
        if (isActivity(context)) return;
        ((Activity) context).finish();
        context = null;
    }

    public static void forward(Context context, Class<? extends Activity> targetClazz, Serializable serializable) {
        Intent intent = new Intent(context, targetClazz);
        putSerializableExtra(intent, serializable);
        context.startActivity(intent);
        if (isActivity(context)) return;
        ((Activity) context).finish();
        context = null;
    }

    public static void forward(Context context, Class<? extends Activity> targetClazz) {
        Intent intent = new Intent(context, targetClazz);
        context.startActivity(intent);
        if (isActivity(context)) return;
        ((Activity) context).finish();
        context = null;
    }

    public static void startForResult(Context context, Class<? extends Activity> targetClazz, int flags) {
        Intent intent = new Intent(context, targetClazz);
        if (isActivity(context)) return;
        ((Activity) context).startActivityForResult(intent, flags);
        context = null;
    }

    public static void startForResult(Context context, Class<? extends Activity> targetClazz, int flags, Parcelable parcelable) {
        Intent intent = new Intent(context, targetClazz);
        if (isActivity(context)) return;
        putParcelableExtra(intent, parcelable);
        ((Activity) context).startActivityForResult(intent, flags);
        context = null;
    }

    public static void setResult(Context context, Class<? extends Activity> targetClazz, int flags, Parcelable parcelable) {
        Intent intent = new Intent(context, targetClazz);
        setFlags(intent, flags);
        putParcelableExtra(intent, parcelable);
        if (isActivity(context)) return;
        ((Activity) context).setResult(flags, intent);
        ((Activity) context).finish();
    }

    public static void setResult(Context context, Class<? extends Activity> targetClazz,int flags, Intent intent) {
        if (isActivity(context)) return;
        ((Activity) context).setResult(flags, intent);
        ((Activity) context).finish();
    }

    public static boolean isActivity(Context context) {
        if (!(context instanceof Activity)) return true;
        return false;
    }

    public static void setFlags(Intent intent, int flags) {
        if (flags < 0) return;
        intent.setFlags(flags);
    }

    public static void putParcelableExtra(Intent intent, Parcelable parcelable) {
        if (parcelable == null) return;
        intent.putExtra(AppConstant.INTENT_EXTRA_KEY, parcelable);
    }

    public static void putSerializableExtra(Intent intent, Serializable serializable) {
        if (serializable == null) return;
        intent.putExtra(AppConstant.INTENT_EXTRA_KEY, serializable);
    }
}
