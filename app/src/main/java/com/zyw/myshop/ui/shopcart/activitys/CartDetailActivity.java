package com.zyw.myshop.ui.shopcart.activitys;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.WebView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;
import com.youth.banner.loader.ImageLoader;
import com.zyw.myshop.R;
import com.zyw.myshop.base.BaseActivity;
import com.zyw.myshop.bean.home.HomeBean;
import com.zyw.myshop.bean.shopcart.AddShopBean;
import com.zyw.myshop.bean.shopcart.ShopDetailBean;
import com.zyw.myshop.common.CartCustomPopView;
import com.zyw.myshop.interfaces.shop.IShop;
import com.zyw.myshop.presenters.shop.ShopPresenter;
import com.zyw.myshop.sqllite.ShopCartSqlite;
import com.zyw.myshop.ui.shopcart.adapter.IssueAdapter;
import com.zyw.myshop.util.DP2PXUtils;
import com.zyw.myshop.util.SpUtils;
import com.zyw.myshop.util.ToastUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class CartDetailActivity extends BaseActivity<IShop.IPresenter> implements IShop.IView {


    //返回图片
    @BindView(R.id.iv_back)
    ImageView ivBack;
    //banner
    @BindView(R.id.banner_cart)
    Banner bannerCart;
    //商品名称
    @BindView(R.id.tv_cart_name)
    TextView tvCartName;
    //材质
    @BindView(R.id.tv_cart_material)
    TextView tvCartMaterial;
    //价格
    @BindView(R.id.tv_cart_price)
    TextView tvCartPrice;
    //厂家
    @BindView(R.id.tv_cart_manufactor)
    TextView tvCartManufactor;
    //规格数量
    @BindView(R.id.fl_cart_size)
    FrameLayout flCartSize;
    //评价数量
    @BindView(R.id.tv_cart_comment)
    TextView tvCartComment;
    //评论日期
    @BindView(R.id.tv_cart_date)
    TextView tvCartDate;
    //评论内容
    @BindView(R.id.tv_cart_desc)
    TextView tvCartDesc;
    //评论图片
    @BindView(R.id.ll_img)
    LinearLayout llImg;
    //控制评论的显示隐藏
    @BindView(R.id.ll_comment)
    LinearLayout llComment;
    //参数列表
    @BindView(R.id.ll_parameter)
    LinearLayout llParameter;
    //WebView
    @BindView(R.id.web_cart)
    WebView webCart;
    //常见问题
    @BindView(R.id.recycler_question)
    RecyclerView recyclerQuestion;
    //大家都在看
    @BindView(R.id.recycler_watch)
    RecyclerView recyclerWatch;
    //收藏图标
    @BindView(R.id.layout_collect)
    RelativeLayout layoutCollect;
    //购物车图标
    @BindView(R.id.layout_cart)
    RelativeLayout layoutCart;
    //立即购买
    @BindView(R.id.tv_cart_buy)
    TextView tvCartBuy;
    //评价条目
    @BindView(R.id.fl_cart_comment)
    FrameLayout flCartComment;
    //加入购物车
    @BindView(R.id.tv_toCart)
    TextView tvToCart;
    //大家都在看
    @BindView(R.id.tv_cart_watch)
    TextView tvCartWatch;
    //大家都在看
    @BindView(R.id.tv_cart_nickName)
    TextView tvCartNickName;
    @BindView(R.id.ll_bottom)
    LinearLayout llBottom;
    //商品数量
    @BindView(R.id.tv_shop_num)
    TextView shopNumber;
    //nestView
    @BindView(R.id.nest)
    NestedScrollView nest;
    private int shopNum=1;//商品的数量
    private boolean click = true;//第一次点击
    private int id;

    private ShopDetailBean shopDetailBean;
    private static final String TAG = "CartDetailActivity";
    private HomeBean.DataBean.HotGoodsListBean data;
    private String html = "<html>\n" +
            "            <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no\"/>\n" +
            "            <head>\n" +
            "                <style>\n" +
            "                    p{\n" +
            "                        margin:0px;\n" +
            "                    }\n" +
            "                    img{\n" +
            "                        width:100%;\n" +
            "                        height:auto;\n" +
            "                    }\n" +
            "                </style>\n" +
            "            </head>\n" +
            "            <body>\n" +
            "                $\n" +
            "            </body>\n" +
            "        </html>";
    private PopupWindow popupWindow;

    @Override
    protected IShop.IPresenter onCreatePresenter() {
        return new ShopPresenter();
    }

    @Override
    protected void onStart() {
        super.onStart();
        int shopNum = SpUtils.getInstance().getInt("shopNum");
        shopNumber.setText(String.valueOf(shopNum));
    }

    @Override
    protected void initData() {
        mPresenter.getShopDetailData(id);
    }

    @Override
    protected void initView() {
        Intent intent = getIntent();
        id = intent.getIntExtra("id", 0);
        data = (HomeBean.DataBean.HotGoodsListBean) intent.getSerializableExtra("data");
        ivBack.setVisibility(View.VISIBLE);
        tvCartName.setText(data.getName());//商品名称
        tvCartMaterial.setText(data.getGoods_brief());//材质
        tvCartPrice.setText("¥" + data.getRetail_price());//价格


    }

    @Override
    protected int getLayout() {
        return R.layout.activity_cart_detail;
    }

    @Override
    public void getShopDetailReturn(ShopDetailBean result) {
        shopDetailBean = result;
        Log.d(TAG, "getShopDetailReturn: " + result);
        //设置Banner
        initBanner(result.getData().getGallery());
        //参数设置
        initParameter(result.getData().getAttribute());
        //WebView
        initWebView(result.getData().getInfo());
        //常见问题
        initQuestion(result.getData().getIssue());
        //评价
        initComment(result.getData().getComment());

    }

    @Override
    public void getAddShopDataReturn(AddShopBean result) {
        if (result.getErrno() == 0) {
            Log.d(TAG, "getAddShopDataReturn: " + result.getData().getCartTotal().getGoodsCount());
            initToast();
            shopNumber.setText(result.getData().getCartTotal().getGoodsCount() + "");
            SpUtils.getInstance().setValue("shopNum",result.getData().getCartTotal().getGoodsCount());
        } else if (result.getErrno() == 400) {
            ToastUtils.onShortToast(result.getErrmsg());
        }

    }


    private void initComment(ShopDetailBean.DataBeanX.CommentBean comment) {
        //username
        tvCartNickName.setText("");
        // 时间
        tvCartDate.setText(comment.getData().getAdd_time());
        //内容
        tvCartDesc.setText(comment.getData().getContent());
        //两张图片
        List<ShopDetailBean.DataBeanX.CommentBean.DataBean.PicListBean> pic_list = comment.getData().getPic_list();
        Log.d(TAG, "initComment: " + pic_list.size());
        int margin = DP2PXUtils.dp2px(this, 10);
        llImg.removeAllViews();

        for (int i = 0; i < pic_list.size(); i++) {
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            params.setMargins(margin, margin, margin, margin);
            ImageView imageView = new ImageView(this);
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setLayoutParams(params);  //设置图片宽高
            Glide.with(this).load(pic_list.get(i).getPic_url()).placeholder(R.mipmap.ic_launcher).into(imageView);
            llImg.addView(imageView);
        }
    }

    //常见问题
    private void initQuestion(List<ShopDetailBean.DataBeanX.IssueBean> issue) {

        recyclerQuestion.setLayoutManager(new LinearLayoutManager(this));
        IssueAdapter issueAdapter = new IssueAdapter(this);
        issueAdapter.addData(issue);
        recyclerQuestion.setAdapter(issueAdapter);
    }

    //WebView
    private void initWebView(ShopDetailBean.DataBeanX.InfoBean infoBean) {
        if (!TextUtils.isEmpty(infoBean.getGoods_desc())) {
            String h5 = infoBean.getGoods_desc();
            html = html.replace("$", h5);

            webCart.loadDataWithBaseURL("about:blank", html, "text/html", "utf-8", null);
        }
    }

    //参数设置
    private void initParameter(List<ShopDetailBean.DataBeanX.AttributeBean> attribute) {
        llParameter.removeAllViews();
        TextView textView = new TextView(this);
        int margin = DP2PXUtils.dp2px(this, 6);
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) llParameter.getLayoutParams();
        params.leftMargin = margin;
        textView.setText("参数设置");
        textView.setTextColor(Color.BLACK);
        int size = DP2PXUtils.dp2px(this, 6);
        textView.setTextSize(size);
        textView.setLayoutParams(params);
        llParameter.addView(textView);
        for (ShopDetailBean.DataBeanX.AttributeBean item : attribute) {
            View view = LayoutInflater.from(this).inflate(R.layout.layout_parameter, null);
            TextView txt_parameter_name = view.findViewById(R.id.txt_parameter_name);
            TextView txt_parameter_value = view.findViewById(R.id.txt_parameter_value);
            txt_parameter_name.setText(item.getName());
            txt_parameter_value.setText(item.getValue());
            llParameter.addView(view);
        }
    }

    //Banner
    private void initBanner(List<ShopDetailBean.DataBeanX.GalleryBean> gallery) {
        bannerCart.setBannerAnimation(Transformer.ZoomOutSlide)
                .setBannerStyle(BannerConfig.CIRCLE_INDICATOR)
                .setIndicatorGravity(BannerConfig.CENTER)
                .setImages(gallery)
                .setImageLoader(new ImageLoader() {
                    @Override
                    public void displayImage(Context context, Object path, ImageView imageView) {
                        ShopDetailBean.DataBeanX.GalleryBean bean = (ShopDetailBean.DataBeanX.GalleryBean) path;
                        Glide.with(context).load(bean.getImg_url()).into(imageView);
                    }
                }).start();
    }


    @OnClick({R.id.iv_back, R.id.tv_cart_manufactor, R.id.fl_cart_size, R.id.tv_toCart, R.id.fl_cart_comment, R.id.layout_cart})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.tv_cart_manufactor:
                ToastUtils.onShortToast("跳厂家");
                break;
            case R.id.fl_cart_size:
                initPopWindow();
                click = false;
                break;
            case R.id.tv_toCart:
                if (click == true) {
                    click = false;
                    initPopWindow();
                } else {
                    initAddData();
                }

                break;
            case R.id.fl_cart_comment:
                ToastUtils.onShortToast("点击评价跳转");
                break;
            case R.id.layout_cart:
                //购物车图片 跳转到购物车fragment
                Intent intent = new Intent();
                setResult(300, intent);
                finish();
                break;

        }
    }

    private void initAddData() {
        String islogin = SpUtils.getInstance().getString("token");

        if (!TextUtils.isEmpty(islogin)) {
            if (popupWindow != null && popupWindow.isShowing()) {
                if (shopDetailBean != null) {
                    if (shopDetailBean.getData().getProductList().size() > 0) {
                        int goods_id = shopDetailBean.getData().getProductList().get(0).getGoods_id();
                        int goods_number = shopDetailBean.getData().getProductList().get(0).getId();
                        mPresenter.getAddShopData(goods_id, shopNum, goods_number);

                        popupWindow.dismiss();
                        popupWindow = null;
                    }
                }

            }
        } else {
            ToastUtils.onShortToast("用户未登录");
        }

        click = true;

    }

    private void initToast() {

        Toast toast2 = new Toast(this);
        View view = LayoutInflater.from(this).inflate(R.layout.toast_custom, null);

        toast2.setView(view);
        toast2.setGravity(Gravity.FILL_HORIZONTAL | Gravity.VERTICAL_GRAVITY_MASK, 0, 0);
        toast2.show();
    }

    private void initPopWindow() {
        View inflate = LayoutInflater.from(this).inflate(R.layout.layout_popwindow_shop, null);
        inflate.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
        ImageView imgPopDetail = inflate.findViewById(R.id.img_pop_detail);
        ImageView imgPopClose = inflate.findViewById(R.id.img_pop_close);
        TextView tvPopPrice = inflate.findViewById(R.id.tv_pop_price);
        TextView txtSubtract = inflate.findViewById(R.id.txt_subtract);
        TextView txtValue = inflate.findViewById(R.id.txt_value);
        TextView txtAdd = inflate.findViewById(R.id.txt_add);
        //图片
        Glide.with(this).load(data.getList_pic_url()).into(imgPopDetail);
        //价格
        tvPopPrice.setText("¥" + data.getRetail_price());
        //自定义+ -
        CartCustomPopView cartCustomPopView = inflate.findViewById(R.id.cart);
        cartCustomPopView.initView();
        cartCustomPopView.setClickItem(new CartCustomPopView.ClickItem() {

            @Override
            public void onClick(int value) {
                shopNum = value;
            }
        });
        nest.setFocusable(false);
        int height = DP2PXUtils.dp2px(this, 250);
        popupWindow = new PopupWindow(inflate, LinearLayout.LayoutParams.MATCH_PARENT, height);
        //popupWindow.setFocusable(true);//设置聚焦
        popupWindow.setOutsideTouchable(false);//点击外部消失
        //页面不可滑动
        nest.setFocusable(false);

        setAlpha(0.3f);
        //关闭图片
        imgPopClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
                nest.setFocusable(true);
                click = true;
                popupWindow = null;
            }
        });
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                setAlpha(1f);
                nest.setFocusable(true);
            }
        });
        int[] pt = new int[2];
        //获取到的屏幕宽高(除开了当前组件的宽高）
        llBottom.getLocationInWindow(pt);
        // Display display = getWindowManager().getDefaultDisplay();
        // int activityheight = display.getHeight();
        popupWindow.showAtLocation(llBottom, Gravity.NO_GRAVITY, 0, pt[1] - height);
    }


    public void setAlpha(float alpha) {
        getWindow().findViewById(R.id.nest).setAlpha(alpha);
    }
}