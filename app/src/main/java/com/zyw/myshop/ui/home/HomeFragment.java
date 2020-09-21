package com.zyw.myshop.ui.home;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.zyw.myshop.MainActivity;
import com.zyw.myshop.R;
import com.zyw.myshop.base.BaseFragment;
import com.zyw.myshop.bean.home.HomeBean;
import com.zyw.myshop.interfaces.home.IHome;
import com.zyw.myshop.presenters.home.HomePresenter;
import com.zyw.myshop.ui.home.adapter.HomeAdapter;
import com.zyw.myshop.ui.shopcart.activitys.CartDetailActivity;
import com.zyw.myshop.util.ToastUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

//首页
public class HomeFragment extends BaseFragment<IHome.IPresenter> implements IHome.IView {
    @BindView(R.id.recycler_home_fragment)
    RecyclerView recyclerHomeFragment;
    @BindView(R.id.iv_back)
    ImageView ivBack;
    private List<HomeBean.HomeListBean> data;
    private HomeAdapter homeAdapter;

    @Override
    protected void initData() {
        mPresenter.getHomeData();
    }

    @Override
    protected IHome.IPresenter onCreatePresenter() {
        return new HomePresenter();
    }

    @Override
    protected void initView(View view) {
        ivBack.setVisibility(View.GONE);
        data = new ArrayList<>();
        GridLayoutManager gridLayoutManager = new GridLayoutManager(context, 2);
        homeAdapter = new HomeAdapter(data, context);
        homeAdapter.setSpanSizeLookup(new BaseQuickAdapter.SpanSizeLookup() {
            @Override
            public int getSpanSize(GridLayoutManager gridLayoutManager, int i) {
                int type = data.get(i).currentType;
                switch (type) {
                    case HomeBean.HomeListBean.TYPE_TOOLBAR:
                    case HomeBean.HomeListBean.TYPE_BANNER:
                    case HomeBean.HomeListBean.TYPE_TITLE:
                    case HomeBean.HomeListBean.TYPE_TITLE_TOP:
                    case HomeBean.HomeListBean.TYPE_SPECIAL:
                    case HomeBean.HomeListBean.TYPE_HOT:
                    case HomeBean.HomeListBean.TYPE_TAB:
                        return 2;
                    case HomeBean.HomeListBean.TYPE_BRAND:
                    case HomeBean.HomeListBean.TYPE_NEW:
                    case HomeBean.HomeListBean.TYPE_CATEGORY:
                        return 1;

                }
                return 0;
            }
        });

        recyclerHomeFragment.setLayoutManager(gridLayoutManager);
        homeAdapter.bindToRecyclerView(recyclerHomeFragment);

        homeAdapter.setOnClickItemListener(new HomeAdapter.OnClickItemListener() {
            @Override
            public void onClick(HomeBean.HomeListBean bean) {
                switch (bean.getItemType()) {
                    case HomeBean.HomeListBean.TYPE_TITLE:
                        String title = (String) bean.data;
                        if (title.equals("周一周四·新品首发")) {
                            com.zyw.mylibrary.ToastUtils.onShortToast(context,title);
                        }
                        break;
                    case HomeBean.HomeListBean.TYPE_TITLE_TOP:
                        String title2 = (String) bean.data;
                        if (title2.equals("品牌制造商直供")) {
                            com.zyw.mylibrary.ToastUtils.onShortToast(context,title2);
                        } else if (title2.equals("人气推荐")) {
                            com.zyw.mylibrary.ToastUtils.onShortToast(context,title2);
                        } else if (title2.equals("专题精选")) {
                            com.zyw.mylibrary.ToastUtils.onShortToast(context,title2);
                        } else {

                        }
                        break;
                    case HomeBean.HomeListBean.TYPE_BANNER:
                        //写适配器里
                        break;
                    case HomeBean.HomeListBean.TYPE_TAB:
                        //写适配器里
                        break;
                    case HomeBean.HomeListBean.TYPE_BRAND:
                        HomeBean.DataBean.BrandListBean brandData = (HomeBean.DataBean.BrandListBean) bean.data;
                        ToastUtils.onShortToast(brandData.getName());
                        break;
                    case HomeBean.HomeListBean.TYPE_NEW:
                        HomeBean.DataBean.NewGoodsListBean newData = (HomeBean.DataBean.NewGoodsListBean) bean.data;
                        ToastUtils.onShortToast(newData.getName());
                        break;
                    case HomeBean.HomeListBean.TYPE_HOT:
                        HomeBean.DataBean.HotGoodsListBean hotData = (HomeBean.DataBean.HotGoodsListBean) bean.data;
                        Intent intent = new Intent(context, CartDetailActivity.class);
                        intent.putExtra("id", hotData.getId());
                        intent.putExtra("data", hotData);
                        startActivityForResult(intent, 200);
                        break;
                    case HomeBean.HomeListBean.TYPE_SPECIAL:
                        //适配器
                        break;
                    case HomeBean.HomeListBean.TYPE_CATEGORY:
                        HomeBean.DataBean.CategoryListBean.GoodsListBean goodsData = (HomeBean.DataBean.CategoryListBean.GoodsListBean) bean.data;
                        ToastUtils.onShortToast(goodsData.getName());
                        break;
                    case HomeBean.HomeListBean.TYPE_TOOLBAR:
                        //适配器
                        break;
                }
            }
        });
    }

    @Override
    protected int getLayout() {
        return R.layout.fragment_home;
    }

    private static final String TAG = "HomeFragment";

    @Override
    public void getHomeDataReturn(List<HomeBean.HomeListBean> result) {
        Log.d(TAG, "getHomeDataReturn: " + result.toString());
        data.addAll(result);
        homeAdapter.notifyDataSetChanged();
    }

}
