package com.zyw.myshop.ui.shopcart.activitys;

import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.weigan.loopview.LoopView;
import com.zyw.myshop.R;
import com.zyw.myshop.base.BaseActivity;
import com.zyw.myshop.bean.shopcart.AddressBean;
import com.zyw.myshop.interfaces.shop.IShop;
import com.zyw.myshop.presenters.shop.AddressPresenter;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AddressActivity_Two extends BaseActivity<IShop.AddressPresenter> implements IShop.AddressView {
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_remeber)
    TextView tvRemeber;
    @BindView(R.id.et_people)
    EditText etPeople;
    @BindView(R.id.et_phone)
    EditText etPhone;
    @BindView(R.id.et_address)
    TextView etAddress;
    private LoopView province, city, area;
    private TextView txtProvince, txtCity, txtArea,txt_submit;
    @Override
    protected IShop.AddressPresenter onCreatePresenter() {
        return new AddressPresenter();
    }

    @Override
    protected void initData() {
        //先得到省份的依赖
        mPresenter.getAddressData(1);
    }

    @Override
    protected void initView() {

    }

    @Override
    protected int getLayout() {
        return R.layout.activity_address;
    }

    @Override
    public void getAddressReturn(AddressBean result) {

    }


    @OnClick(R.id.et_address)
    public void onViewClicked() {
        initPopWindow();
    }

    private void initPopWindow() {

        View popView = LayoutInflater.from(this).inflate(R.layout.layout_address_pop_two, null);
        province = popView.findViewById(R.id.adress_province);
        city = popView.findViewById(R.id.adress_city);
        area = popView.findViewById(R.id.adress_area);
        txtProvince = popView.findViewById(R.id.txt_province);
        txtCity = popView.findViewById(R.id.txt_city);
        txtArea = popView.findViewById(R.id.txt_area);
        txt_submit = popView.findViewById(R.id.txt_submit);
        PopupWindow popupWindow = new PopupWindow(popView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        popupWindow.setOutsideTouchable(true);


        popupWindow.showAtLocation(etAddress, Gravity.BOTTOM, 0, 0);
    }
}
