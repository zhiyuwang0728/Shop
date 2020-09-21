package com.zyw.myshop.util;

import android.widget.Toast;

import com.zyw.myshop.app.MyApp;


public class ToastUtils {

    public static void onShortToast(String msg) {
        Toast.makeText(MyApp.getInstance(), msg, Toast.LENGTH_SHORT).show();
    }

    public static void onLongToast(String msg) {
        Toast.makeText(MyApp.getInstance(), msg, Toast.LENGTH_LONG).show();
    }
}
