package com.zyw.myshop.app;

import android.app.Application;

import com.zyw.myshop.bean.home.HomeBean;

import java.util.ArrayList;
import java.util.List;

public class MyApp extends Application {

    private static MyApp myApp;


    @Override
    public void onCreate() {
        super.onCreate();
        myApp = this;
    }

    public static MyApp getInstance() {
        return myApp;
    }


}
