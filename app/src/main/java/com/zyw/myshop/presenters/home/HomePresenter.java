package com.zyw.myshop.presenters.home;

import com.zyw.myshop.base.BasePresenter;
import com.zyw.myshop.bean.home.HomeBean;
import com.zyw.myshop.common.CommonSubscriber;
import com.zyw.myshop.interfaces.IBaseView;
import com.zyw.myshop.interfaces.home.IHome;
import com.zyw.myshop.net.HttpManager;
import com.zyw.myshop.util.RxUtils;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.functions.Function;

import static com.zyw.myshop.bean.home.HomeBean.*;

public class HomePresenter extends BasePresenter<IHome.IView> implements IHome.IPresenter {
    @Override
    public void getHomeData() {
        addSubscribe(
                HttpManager.getInstance().getShopApi().getHomeData()
                        .compose(RxUtils.rxScheduler())
                        .map(new Function<HomeBean, List<HomeListBean>>() {
                            @Override
                            public List<HomeListBean> apply(HomeBean homeBean) throws Exception {
                                List<HomeListBean> list = new ArrayList<>();
                                //toolbar
                                HomeListBean toolTitle = new HomeListBean<>();
                                toolTitle.currentType = HomeListBean.TYPE_TOOLBAR;
                                toolTitle.data = "仿网易严选";
                                list.add(toolTitle);
                                //Banner数据
                                HomeListBean banner = new HomeListBean();
                                banner.currentType = HomeListBean.TYPE_BANNER;
                                banner.data = homeBean.getData().getBanner();
                                list.add(banner);
                                //Channel tab标题
                                HomeListBean channel = new HomeListBean();
                                channel.currentType = HomeListBean.TYPE_TAB;
                                channel.data = homeBean.getData().getChannel();
                                list.add(channel);
                                //brand分界线
                                HomeListBean brandTitle = new HomeListBean<>();
                                brandTitle.currentType = HomeListBean.TYPE_TITLE_TOP;
                                brandTitle.data = "品牌制造商直供";
                                list.add(brandTitle);
                                //brand  品牌列表
                                for (int i = 0; i < homeBean.getData().getBrandList().size(); i++) {
                                    HomeListBean brand = new HomeListBean();
                                    brand.currentType = HomeListBean.TYPE_BRAND;
                                    brand.data = homeBean.getData().getBrandList().get(i);
                                    list.add(brand);
                                }
                                //NewGoodListTitle
                                HomeListBean newGoodListTitle = new HomeListBean<>();
                                newGoodListTitle.currentType = HomeListBean.TYPE_TITLE;
                                newGoodListTitle.data = "周一周四·新品首发";
                                list.add(newGoodListTitle);
                                //NewGoodsList  新品
                                for (int i = 0; i < homeBean.getData().getNewGoodsList().size(); i++) {
                                    HomeListBean newGoodsList = new HomeListBean<>();
                                    newGoodsList.currentType = HomeListBean.TYPE_NEW;
                                    newGoodsList.data = homeBean.getData().getNewGoodsList().get(i);
                                    list.add(newGoodsList);
                                }
                                //HotGoodListTitle
                                HomeListBean hotGoodListTitle = new HomeListBean<>();
                                hotGoodListTitle.currentType = HomeListBean.TYPE_TITLE_TOP;
                                hotGoodListTitle.data = "人气推荐";
                                list.add(hotGoodListTitle);
                                //HotGoodsList  人气推荐
                                for (int i = 0; i < homeBean.getData().getHotGoodsList().size(); i++) {
                                    HomeListBean hotGoodsList = new HomeListBean<>();
                                    hotGoodsList.currentType = HomeListBean.TYPE_HOT;
                                    hotGoodsList.data = homeBean.getData().getHotGoodsList().get(i);
                                    list.add(hotGoodsList);
                                }
                                //SpecialTitle
                                HomeListBean specialTitle = new HomeListBean<>();
                                specialTitle.currentType = HomeListBean.TYPE_TITLE_TOP;
                                specialTitle.data = "专题精选";
                                list.add(specialTitle);
                                //Special 专题精选
                                HomeListBean special = new HomeListBean();
                                special.currentType = HomeListBean.TYPE_SPECIAL;
                                special.data = homeBean.getData().getTopicList();
                                list.add(special);
                                //Category  居家 餐厨 标题
                                for (int i = 0; i < homeBean.getData().getCategoryList().size(); i++) {
                                    HomeListBean categoryTitle = new HomeListBean();
                                    categoryTitle.currentType = HomeListBean.TYPE_TITLE_TOP;
                                    categoryTitle.data = homeBean.getData().getCategoryList().get(i).getName();
                                    list.add(categoryTitle);
                                    for (int i1 = 0; i1 < homeBean.getData().getCategoryList().get(i).getGoodsList().size(); i1++) {
                                        HomeBean.HomeListBean category = new HomeBean.HomeListBean();
                                        category.currentType = HomeListBean.TYPE_CATEGORY;
                                        category.data = homeBean.getData().getCategoryList().get(i).getGoodsList().get(i1);
                                        list.add(category);
                                    }
                                }


                                return list;
                            }
                        })
                        .subscribeWith(new CommonSubscriber<List<HomeListBean>>(mView) {
                            @Override
                            public void onNext(List<HomeListBean> list) {
                                mView.getHomeDataReturn(list);
                            }
                        })
        );
    }
}
