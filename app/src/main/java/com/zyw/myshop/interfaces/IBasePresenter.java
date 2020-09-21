package com.zyw.myshop.interfaces;

public interface IBasePresenter<V extends IBaseView> {

    void onAttachView(V v);

    void onDetach();
}
