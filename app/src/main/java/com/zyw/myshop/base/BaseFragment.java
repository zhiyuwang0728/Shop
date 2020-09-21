package com.zyw.myshop.base;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;


import com.zyw.myshop.interfaces.IBasePresenter;
import com.zyw.myshop.interfaces.IBaseView;

import butterknife.ButterKnife;
import butterknife.Unbinder;

public abstract class BaseFragment<P extends IBasePresenter> extends Fragment implements IBaseView {

    protected Context context;

    protected P mPresenter;
    private Unbinder bind;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View inflate = inflater.inflate(getLayout(), container, false);
        bind = ButterKnife.bind(this, inflate);
        context = getContext();
        return inflate;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);
        mPresenter = onCreatePresenter();
        if (mPresenter != null) {
            mPresenter.onAttachView(this);
            initData();
        }
    }

    protected abstract void initData();

    protected abstract P onCreatePresenter();

    protected abstract void initView(View view);

    protected abstract int getLayout();

    @Override
    public void showTips(String tips) {

    }

    @Override
    public void showLoading(int visible) {

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (bind != null) {
            bind.unbind();
        }

        if (mPresenter != null) {
            mPresenter.onDetach();
        }
    }
}