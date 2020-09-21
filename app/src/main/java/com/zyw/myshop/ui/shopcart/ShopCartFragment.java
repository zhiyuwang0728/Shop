package com.zyw.myshop.ui.shopcart;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.zyw.myshop.R;
import com.zyw.myshop.base.BaseFragment;
import com.zyw.myshop.bean.shopcart.GetShopBean;
import com.zyw.myshop.bean.shopcart.RemoveDataBean;
import com.zyw.myshop.interfaces.shop.IShop;
import com.zyw.myshop.presenters.shop.ShopCartPresenter;
import com.zyw.myshop.ui.shopcart.activitys.AddressActivity;
import com.zyw.myshop.ui.shopcart.adapter.ShopCartAdapter;
import com.zyw.myshop.util.ToastUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

//购物车
public class ShopCartFragment extends BaseFragment<IShop.CartPresenter> implements IShop.CartView {

    @BindView(R.id.cb_all)
    CheckBox cbAll;
    @BindView(R.id.tv_money)
    TextView tvMoney;
    @BindView(R.id.tv_submit)
    TextView tvSubmit;
    @BindView(R.id.recycler_shopCart)
    RecyclerView recyclerShopCart;
    @BindView(R.id.tv_write)
    TextView tvWrite;
    @BindView(R.id.iv_back)
    ImageView ivBack;
    private ShopCartAdapter shopCartAdapter;
    private List<GetShopBean.DataBean.CartListBean> mData = new ArrayList<>();//将得到的数据全部存到容器中
    private boolean clickStatus = false;//条目选中时 全选按钮的状态
    private int allCount = 0;//总数量
    private int allPrice = 0;//总价钱


    @Override
    protected void initData() {

    }

    @Override
    public void onStart() {
        super.onStart();
        mPresenter.getShopCartData();
    }

    @Override
    protected IShop.CartPresenter onCreatePresenter() {
        return new ShopCartPresenter();
    }

    @Override
    protected void initView(View view) {
        ivBack.setVisibility(View.GONE);
        recyclerShopCart.setLayoutManager(new LinearLayoutManager(context));
        recyclerShopCart.addItemDecoration(new DividerItemDecoration(context, DividerItemDecoration.VERTICAL));
        shopCartAdapter = new ShopCartAdapter(context);
        recyclerShopCart.setAdapter(shopCartAdapter);
    }

    @Override
    protected int getLayout() {
        return R.layout.fragment_shopcart;
    }

    private static final String TAG = "ShopCartFragment";

    @Override
    public void getShopCartReturn(GetShopBean result) {
        Log.d(TAG, "getShopCartReturn: " + result.getData().getCartTotal().getGoodsCount());
        mData.clear();
        mData.addAll(result.getData().getCartList());
        shopCartAdapter.addData(result.getData().getCartList());

        shopCartAdapter.setClickItemListener(new ShopCartAdapter.ClickItemListener() {
            @Override
            public void onClickItem() {
                //接口回调 判断全选按钮的状态
                initCbAllStatus();
                //动态设置总价钱和总数量
                initNumAndPrice();
            }

            @Override
            public void onValue() {
                initNumAndPrice();
            }
        });
    }

    @Override
    public void getRemoveDataReturn(RemoveDataBean result) {
        if (result.getErrno() == 0) {

        } else {
            Log.d(TAG, "getRemoveDataReturn: " + result.getErrmsg());
            ToastUtils.onShortToast(result.getErrmsg());
        }
    }

    //计算价格
    private void initNumAndPrice() {
        allCount = 0;
        allPrice = 0;
        for (GetShopBean.DataBean.CartListBean bean : mData) {
            if (bean.isSelect() == true) {
                allCount += bean.getNumber();
                allPrice += (bean.getRetail_price() * bean.getNumber());
            }
        }
        tvMoney.setText("¥" + allPrice);
        cbAll.setText("全选(" + allCount + ")");
    }

    //判断列表条目是否为选中状态
    private void initCbAllStatus() {

        for (GetShopBean.DataBean.CartListBean item : mData) {

            if (item.isSelect() == false) {
                clickStatus = false;
                break;
            } else {
                clickStatus = true;
            }
        }
        cbAll.setChecked(clickStatus);
    }

    @OnClick({R.id.cb_all, R.id.tv_write, R.id.tv_submit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.cb_all:
                initSelectAll();
                break;
            case R.id.tv_write:
                initWrite();
                break;
            case R.id.tv_submit:
                initSubmit();
                break;
        }
    }

    //下单按钮
    private void initSubmit() {
        String string = tvSubmit.getText().toString();
        if ("下单".equals(string)) {
            //跳转到地址栏
            Intent intent = new Intent(context, AddressActivity.class);
            startActivity(intent);
        } else if ("删除所选".equals(string)) {
            //删除所选的数据
            StringBuilder sb = new StringBuilder();
            List<Integer> ids = getSelectId();
            for (Integer id : ids) {
                sb.append(id);
                sb.append(",");
            }
            if (sb.length() > 0) {

                sb.deleteCharAt(sb.length() - 1);
                String productIds = sb.toString();
                Log.d(TAG, "initSubmit: " + productIds);
                mPresenter.toRemoveData(productIds);
            } else {
                Toast.makeText(context, "没有选中要删除的商品", Toast.LENGTH_SHORT).show();
            }
        }
    }

    //编辑按钮
    private void initWrite() {
        String string = tvWrite.getText().toString();
        if ("编辑".equals(string)) {
            shopCartAdapter.isEditor = true;
            tvWrite.setText("完成");
            tvSubmit.setText("删除所选");
            tvMoney.setVisibility(View.GONE);
            shopCartAdapter.notifyDataSetChanged();
        } else if ("完成".equals(string)) {
            shopCartAdapter.isEditor = false;
            tvWrite.setText("编辑");
            tvSubmit.setText("下单");
            tvMoney.setVisibility(View.VISIBLE);
            shopCartAdapter.notifyDataSetChanged();
        }
    }

    //全选按钮
    private void initSelectAll() {
        //条目的状态跟随全选的状态
        for (GetShopBean.DataBean.CartListBean item : mData) {
            item.setSelect(cbAll.isChecked());
        }
        shopCartAdapter.notifyDataSetChanged();
    }

    //获取被选中条目的ID
    public List<Integer> getSelectId() {
        List<Integer> ids = new ArrayList<>();
        ids.clear();
        for (GetShopBean.DataBean.CartListBean mDatum : mData) {
            if (mDatum.isSelect() == true) {
                ids.add(mDatum.getProduct_id());
            }
        }
        return ids;
    }
}
