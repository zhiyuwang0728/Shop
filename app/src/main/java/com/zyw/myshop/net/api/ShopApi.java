package com.zyw.myshop.net.api;

import com.zyw.myshop.bean.home.HomeBean;
import com.zyw.myshop.bean.shopcart.AddShopBean;
import com.zyw.myshop.bean.shopcart.AddressBean;
import com.zyw.myshop.bean.shopcart.GetShopBean;
import com.zyw.myshop.bean.shopcart.RemoveDataBean;
import com.zyw.myshop.bean.shopcart.ShopDetailBean;

import io.reactivex.Flowable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ShopApi {

    @GET("index")
    Flowable<HomeBean> getHomeData();

    @GET("goods/detail?")
    Flowable<ShopDetailBean> getShopDetailData(@Query("id") int id);

    //添加商品接口
    @POST("cart/add")
    @FormUrlEncoded
    Flowable<AddShopBean> getAddShopData(@Field("goodsId") int goodsId, @Field("number") int number, @Field("productId") int productId);

    //获取购物车的数据
    @GET("cart/index")
    Flowable<GetShopBean> getShopData();

    //删除购物车
    @GET("cart/delete")
    Flowable<RemoveDataBean> getRemoveData(@Query("productIds") String productIds);

    //城市选择列表
    @GET("region/list")
    Flowable<AddressBean> getAddressData(@Query("parentId") int parentId);

}
