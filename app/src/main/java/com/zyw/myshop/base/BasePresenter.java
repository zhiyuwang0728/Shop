package com.zyw.myshop.base;


import com.zyw.myshop.interfaces.IBasePresenter;
import com.zyw.myshop.interfaces.IBaseView;

import java.lang.ref.WeakReference;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

public abstract class BasePresenter<V extends IBaseView> implements IBasePresenter<V> {
    protected V mView;
    private WeakReference<V> weakReference;

    @Override
    public void onAttachView(V view) {
        //使用弱引用 不用之后就从内存中清除
        weakReference = new WeakReference<>(view);
        mView = weakReference.get();
    }

    @Override
    public void onDetach() {
        //接触关联，V层赋值为空  清空disposable对象
        mView = null;
        unbindSubscribe();
    }

    //相当于存放disposable对象的容器
    private CompositeDisposable compositeDisposable;

    //添加disposable对象
    public void addSubscribe(Disposable disposable) {
        //初始化容器
        if (compositeDisposable == null) {
            compositeDisposable = new CompositeDisposable();
        }

        compositeDisposable.add(disposable);
    }

    //清空disposable对象
    public void unbindSubscribe() {
        if (compositeDisposable != null) {
            compositeDisposable.clear();
        }
    }
}
