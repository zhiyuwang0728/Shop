package com.zyw.myshop.ui.home.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;
import com.youth.banner.listener.OnBannerListener;
import com.youth.banner.loader.ImageLoader;
import com.zyw.myshop.R;
import com.zyw.myshop.base.BaseAdapter;
import com.zyw.myshop.bean.home.HomeBean;
import com.zyw.myshop.util.DP2PXUtils;
import com.zyw.myshop.util.String2Drawable;
import com.zyw.myshop.util.SystemUtils;
import com.zyw.myshop.util.ToastUtils;

import java.util.ArrayList;
import java.util.List;

public class HomeAdapter extends BaseMultiItemQuickAdapter<HomeBean.HomeListBean, BaseViewHolder> {
    /**
     * Same as QuickAdapter#QuickAdapter(Context,int) but with
     * some initialization data.
     *
     * @param data A new list is created out of this one to avoid mutable list
     */

    private Context context;
    private SpecialAdapter specialAdapter;

    public HomeAdapter(List<HomeBean.HomeListBean> data, Context context) {
        super(data);
        this.context = context;
        addItemType(HomeBean.HomeListBean.TYPE_TOOLBAR, R.layout.adapter_home_tool);
        //banner
        addItemType(HomeBean.HomeListBean.TYPE_BANNER, R.layout.adapter_home_banner);
        //tab导航栏
        addItemType(HomeBean.HomeListBean.TYPE_TAB, R.layout.adapter_home_tab);
        //带分割线的标题
        addItemType(HomeBean.HomeListBean.TYPE_TITLE_TOP, R.layout.adapter_home_titletop);
        //品牌列表
        addItemType(HomeBean.HomeListBean.TYPE_BRAND, R.layout.adapter_home_brand);
        //不带分割线的标题
        addItemType(HomeBean.HomeListBean.TYPE_TITLE, R.layout.adapter_home_title);
        //新品首发列表
        addItemType(HomeBean.HomeListBean.TYPE_NEW, R.layout.adapter_home_new);
        //人气推荐列表
        addItemType(HomeBean.HomeListBean.TYPE_HOT, R.layout.adapter_home_hot);
        //专题精选
        addItemType(HomeBean.HomeListBean.TYPE_SPECIAL, R.layout.adapter_home_special);
        //居家
        addItemType(HomeBean.HomeListBean.TYPE_CATEGORY, R.layout.adapter_home_category);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void convert(BaseViewHolder helper, HomeBean.HomeListBean item) {
        switch (item.getItemType()) {
            case HomeBean.HomeListBean.TYPE_TITLE:
                initTitle(helper, (String) item.data);
                break;
            case HomeBean.HomeListBean.TYPE_TITLE_TOP:
                initTitleTop(helper, (String) item.data);
                break;
            case HomeBean.HomeListBean.TYPE_BANNER:
                initBanner(helper, (List<HomeBean.DataBean.BannerBean>) item.data);
                break;
            case HomeBean.HomeListBean.TYPE_TAB:
                initTab(helper, (List<HomeBean.DataBean.ChannelBean>) item.data);
                break;
            case HomeBean.HomeListBean.TYPE_BRAND:
                initBrand(helper, (HomeBean.DataBean.BrandListBean) item.data);
                break;
            case HomeBean.HomeListBean.TYPE_NEW:
                initNewGood(helper, (HomeBean.DataBean.NewGoodsListBean) item.data);
                break;
            case HomeBean.HomeListBean.TYPE_HOT:
                initHot(helper, (HomeBean.DataBean.HotGoodsListBean) item.data);
                break;
            case HomeBean.HomeListBean.TYPE_SPECIAL:
                initTopic(helper, (List<HomeBean.DataBean.TopicListBean>) item.data);
                break;
            case HomeBean.HomeListBean.TYPE_CATEGORY:
                initCategory(helper, (HomeBean.DataBean.CategoryListBean.GoodsListBean) item.data);
                break;
            case HomeBean.HomeListBean.TYPE_TOOLBAR:
                initToolBar(helper);
        }

        helper.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onClickItemListener != null) {
                    onClickItemListener.onClick(item);
                }
            }
        });
    }

    private void initToolBar(BaseViewHolder helper) {
        RelativeLayout search_rl = helper.getView(R.id.search_rl);
        search_rl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastUtils.onShortToast("搜索");
            }
        });
    }

    private void initCategory(BaseViewHolder helper, HomeBean.DataBean.CategoryListBean.GoodsListBean data) {
        ImageView iv_category = helper.getView(R.id.iv_category);
        TextView txt_category_name = helper.getView(R.id.txt_category_name);
        TextView txt_category_price = helper.getView(R.id.txt_category_price);
        Glide.with(context).load(data.getList_pic_url()).into(iv_category);
        txt_category_name.setText(data.getName());
        txt_category_price.setText("¥" + data.getRetail_price());
    }

    //标题
    private void initTitleTop(BaseViewHolder helper, String data) {
        helper.setText(R.id.tv_title_top, data);
    }

    //專題精選
    private void initTopic(BaseViewHolder helper, List<HomeBean.DataBean.TopicListBean> data) {
        RecyclerView recyclerView = helper.getView(R.id.recycler_special);

        if (specialAdapter == null) {
            specialAdapter = new SpecialAdapter(context);
            recyclerView.setLayoutManager(new LinearLayoutManager(context, RecyclerView.HORIZONTAL, false));
            specialAdapter.addData(data);
            recyclerView.setAdapter(specialAdapter);
            specialAdapter.setOnClickItemListener(new BaseAdapter.OnClickItemListener() {
                @Override
                public void onClickItem(int position) {
                    ToastUtils.onShortToast(data.get(position).getTitle());
                }
            });
        } else if (recyclerView.getAdapter() == null) {
            recyclerView.setAdapter(specialAdapter);
        }
    }

    //人氣推薦
    private void initHot(BaseViewHolder helper, HomeBean.DataBean.HotGoodsListBean data) {
        TextView txt_hot_name = helper.getView(R.id.txt_hot_name);
        TextView txt_hot_title = helper.getView(R.id.txt_hot_title);
        TextView txt_hot_price = helper.getView(R.id.txt_hot_price);
        ImageView img_hot = helper.getView(R.id.img_hot);

        Glide.with(context).load(data.getList_pic_url()).into(img_hot);
        txt_hot_name.setText(data.getName());
        txt_hot_price.setText("¥" + data.getRetail_price());
        txt_hot_title.setText(data.getGoods_brief());
    }

    //新品推薦
    private void initNewGood(BaseViewHolder helper, HomeBean.DataBean.NewGoodsListBean data) {
        ImageView img_new = helper.getView(R.id.img_new);
        TextView txt_new_name = helper.getView(R.id.txt_new_name);
        TextView txt_new_price = helper.getView(R.id.txt_new_price);

        Glide.with(context).load(data.getList_pic_url()).into(img_new);
        txt_new_name.setText(data.getName());
        txt_new_price.setText("¥" + data.getRetail_price());

    }

    //品牌
    private void initBrand(BaseViewHolder helper, HomeBean.DataBean.BrandListBean data) {
        ImageView img_brand = helper.getView(R.id.img_brand);
        TextView txt_brand_name = helper.getView(R.id.txt_brand_name);
        TextView txt_brand_price = helper.getView(R.id.txt_brand_price);

        Glide.with(context).load(data.getNew_pic_url()).into(img_brand);
        txt_brand_name.setText(data.getName());
        txt_brand_price.setText(data.getFloor_price() + "元/起");
    }

    //导航栏
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void initTab(BaseViewHolder helper, List<HomeBean.DataBean.ChannelBean> data) {
        LinearLayout linearLayout = helper.getView(R.id.mLL);
        if (linearLayout.getChildCount() == 0) {
            for (HomeBean.DataBean.ChannelBean item : data) {
                TextView tab = new TextView(context);
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT, 1);
                int size = DP2PXUtils.dp2px(context, 5);
                tab.setText(item.getName());
                tab.setTextSize(size);
                tab.setGravity(Gravity.CENTER);
                tab.setLayoutParams(params);
//                Drawable drawable = String2Drawable.StringToDrawable("https://yanxuan.nosdn.127.net/6c03ca93d8fe404faa266ea86f3f1e43.png");
                Drawable icon = context.getDrawable(R.mipmap.ic_launcher);
                icon.setBounds(0, 0, 80, 80);
                tab.setCompoundDrawables(null, icon, null, null);
                linearLayout.addView(tab);


                String name = item.getName();
                tab.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        switch (item.getId()) {
                            case 1:
                                ToastUtils.onShortToast(name);
                                break;
                            case 2:
                                ToastUtils.onShortToast(name);
                                break;
                            case 3:
                                ToastUtils.onShortToast(name);
                                break;
                            case 4:
                                ToastUtils.onShortToast(name);
                                break;
                            case 5:
                                ToastUtils.onShortToast(name);
                                break;
                        }
                    }
                });
            }
        }
    }

    //初始化Banner
    private void initBanner(BaseViewHolder helper, List<HomeBean.DataBean.BannerBean> data) {
        Banner banner = helper.getView(R.id.mBanner);
        if (banner.getTag() == null || (int) banner.getTag() == 0) {
            banner.setTag(1);
            banner.setBannerAnimation(Transformer.ZoomOutSlide)
                    .setBannerStyle(BannerConfig.CIRCLE_INDICATOR)
                    .setIndicatorGravity(BannerConfig.CENTER)
                    .setImages(data)
                    .setImageLoader(new ImageLoader() {
                        @Override
                        public void displayImage(Context context, Object path, ImageView imageView) {
                            HomeBean.DataBean.BannerBean bean = (HomeBean.DataBean.BannerBean) path;
                            Glide.with(context).load(bean.getImage_url()).into(imageView);
                        }
                    }).start();

            banner.setOnBannerListener(new OnBannerListener() {
                @Override
                public void OnBannerClick(int position) {
                    ToastUtils.onShortToast(data.get(position).getName());
                }
            });
        }


    }

    //标题
    private void initTitle(BaseViewHolder helper, String data) {
        helper.setText(R.id.tv_title, data);
    }

    OnClickItemListener onClickItemListener;

    public void setOnClickItemListener(OnClickItemListener onClickItemListener) {
        this.onClickItemListener = onClickItemListener;
    }

    public interface OnClickItemListener {
        void onClick(HomeBean.HomeListBean bean);
    }
}
