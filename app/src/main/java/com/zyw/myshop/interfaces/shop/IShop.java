package com.zyw.myshop.interfaces.shop;

import com.zyw.myshop.bean.shopcart.AddShopBean;
import com.zyw.myshop.bean.shopcart.AddressBean;
import com.zyw.myshop.bean.shopcart.GetShopBean;
import com.zyw.myshop.bean.shopcart.RemoveDataBean;
import com.zyw.myshop.bean.shopcart.ShopDetailBean;
import com.zyw.myshop.interfaces.IBasePresenter;
import com.zyw.myshop.interfaces.IBaseView;

public interface IShop {
    interface IView extends IBaseView {
        void getShopDetailReturn(ShopDetailBean result);

        void getAddShopDataReturn(AddShopBean result);
    }

    interface IPresenter extends IBasePresenter<IView> {
        void getShopDetailData(int id);

        void getAddShopData(int goodsId, int number, int productId);
    }

    interface CartView extends IBaseView {
        void getShopCartReturn(GetShopBean result);

        void getRemoveDataReturn(RemoveDataBean result);
    }

    interface CartPresenter extends IBasePresenter<CartView> {
        void getShopCartData();

        void toRemoveData(String productIds);
    }

    interface AddressView extends IBaseView {
        void getAddressReturn(AddressBean result);
    }

    interface AddressPresenter extends IBasePresenter<AddressView> {
        void getAddressData(int parentId);
    }
}
