package com.zyw.myshop.presenters.shop;

import com.zyw.myshop.base.BasePresenter;
import com.zyw.myshop.bean.shopcart.AddressBean;
import com.zyw.myshop.common.CommonSubscriber;
import com.zyw.myshop.interfaces.shop.IShop;
import com.zyw.myshop.net.HttpManager;
import com.zyw.myshop.util.RxUtils;

public class AddressPresenter extends BasePresenter<IShop.AddressView> implements IShop.AddressPresenter {
    @Override
    public void getAddressData(int parentId) {
        addSubscribe(
                HttpManager.getInstance().getShopApi().getAddressData(parentId)
                        .compose(RxUtils.rxScheduler())
                        .subscribeWith(new CommonSubscriber<AddressBean>(mView) {
                            @Override
                            public void onNext(AddressBean addressBean) {
                                mView.getAddressReturn(addressBean);
                            }
                        })
        );
    }
}
