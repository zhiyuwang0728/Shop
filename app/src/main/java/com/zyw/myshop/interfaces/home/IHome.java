package com.zyw.myshop.interfaces.home;

import com.zyw.myshop.bean.home.HomeBean;
import com.zyw.myshop.interfaces.IBasePresenter;
import com.zyw.myshop.interfaces.IBaseView;

import java.util.List;

public interface IHome {

    interface IView extends IBaseView {
        void getHomeDataReturn(List<HomeBean.HomeListBean> result);
    }

    interface IPresenter extends IBasePresenter<IView> {
        void getHomeData();
    }
}
