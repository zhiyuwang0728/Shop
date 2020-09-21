package com.zyw.myshop.presenters.shop;

import com.zyw.myshop.base.BasePresenter;
import com.zyw.myshop.bean.shopcart.AddShopBean;
import com.zyw.myshop.bean.shopcart.ShopDetailBean;
import com.zyw.myshop.common.CommonSubscriber;
import com.zyw.myshop.interfaces.shop.IShop;
import com.zyw.myshop.net.HttpManager;
import com.zyw.myshop.util.RxUtils;

public class ShopPresenter extends BasePresenter<IShop.IView> implements IShop.IPresenter {
    @Override
    public void getShopDetailData(int id) {
        addSubscribe(
                HttpManager.getInstance().getShopApi().getShopDetailData(id)
                        .compose(RxUtils.rxScheduler())
                        .subscribeWith(new CommonSubscriber<ShopDetailBean>(mView) {
                            @Override
                            public void onNext(ShopDetailBean shopDetailBean) {
                                mView.getShopDetailReturn(shopDetailBean);
                            }
                        })
        );
    }

    @Override
    public void getAddShopData(int goodsId, int number, int productId) {
        addSubscribe(
                HttpManager.getInstance().getShopApi().getAddShopData(goodsId, number, productId)
                        .compose(RxUtils.rxScheduler())
                        .subscribeWith(new CommonSubscriber<AddShopBean>(mView) {
                            @Override
                            public void onNext(AddShopBean addShopBean) {
                                mView.getAddShopDataReturn(addShopBean);
                            }
                        })
        );
    }
}
