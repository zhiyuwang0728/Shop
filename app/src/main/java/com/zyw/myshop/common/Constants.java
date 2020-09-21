package com.zyw.myshop.common;


import com.zyw.myshop.app.MyApp;
import com.zyw.myshop.bean.home.HomeBean;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Constants {

    public static final String PATH_DATA = MyApp.getInstance().getExternalCacheDir().getAbsolutePath() + File.separator + "data";

    public static final String PATH_CACHE = PATH_DATA + "/mall";
    public static final String BASE_HOMEURL = "http://cdwan.cn/api/";



}
