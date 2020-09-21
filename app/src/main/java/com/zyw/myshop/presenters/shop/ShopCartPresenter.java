package com.zyw.myshop.presenters.shop;

import com.zyw.myshop.base.BasePresenter;
import com.zyw.myshop.bean.shopcart.GetShopBean;
import com.zyw.myshop.bean.shopcart.RemoveDataBean;
import com.zyw.myshop.common.CommonSubscriber;
import com.zyw.myshop.interfaces.shop.IShop;
import com.zyw.myshop.net.HttpManager;
import com.zyw.myshop.util.RxUtils;

public class ShopCartPresenter extends BasePresenter<IShop.CartView> implements IShop.CartPresenter {
    @Override
    public void getShopCartData() {
        addSubscribe(
                HttpManager.getInstance().getShopApi().getShopData()
                        .compose(RxUtils.rxScheduler())
                        .subscribeWith(new CommonSubscriber<GetShopBean>(mView) {
                            @Override
                            public void onNext(GetShopBean getShopBean) {
                                mView.getShopCartReturn(getShopBean);
                            }
                        })
        );
    }

    @Override
    public void toRemoveData(String productIds) {
        addSubscribe(
                HttpManager.getInstance().getShopApi().getRemoveData(productIds)
                        .compose(RxUtils.rxScheduler())
                        .subscribeWith(new CommonSubscriber<RemoveDataBean>(mView) {
                            @Override
                            public void onNext(RemoveDataBean removeDataBean) {
                                mView.getRemoveDataReturn(removeDataBean);
                            }
                        })
        );
    }
}
