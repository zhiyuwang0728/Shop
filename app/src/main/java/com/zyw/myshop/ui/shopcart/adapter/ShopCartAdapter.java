package com.zyw.myshop.ui.shopcart.adapter;

import android.content.Context;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.zyw.myshop.R;
import com.zyw.myshop.base.BaseAdapter;
import com.zyw.myshop.bean.shopcart.GetShopBean;
import com.zyw.myshop.common.CartCustomPopView;
import com.zyw.myshop.util.ToastUtils;

import butterknife.BindView;

public class ShopCartAdapter extends BaseAdapter<GetShopBean.DataBean.CartListBean> {

    public boolean isEditor;

    public ShopCartAdapter(Context context) {
        super(context);
    }

    @Override
    protected int getLayout() {
        return R.layout.adapter_shop_shopcart;
    }

    @Override
    protected void onBindData(BaseViewHolder holder, int position, GetShopBean.DataBean.CartListBean item) {
        CheckBox cbShopcart = (CheckBox) holder.getView(R.id.cb_shopcart);
        ImageView imgShopcart = (ImageView) holder.getView(R.id.img_shopcart);
        TextView tvShopcartName = (TextView) holder.getView(R.id.tv_shopcart_name);
        TextView tvShopcartPrice = (TextView) holder.getView(R.id.tv_shopcart_price);
        TextView tvShopcartNum = (TextView) holder.getView(R.id.tv_shopcart_num);
        TextView tvShopcartSelect = (TextView) holder.getView(R.id.txt_subtract);


        CartCustomPopView customview = (CartCustomPopView) holder.getView(R.id.customview);
        customview.initView();
        customview.setValue(item.getNumber());
        customview.setClickItem(new CartCustomPopView.ClickItem() {
            @Override
            public void onClick(int value) {
                item.setNumber(value);
                if(clickItemListener!=null){
                    clickItemListener.onValue();
                }
            }
        });
        //加载图片
        Glide.with(context).load(item.getList_pic_url()).into(imgShopcart);
        //商品名称
        tvShopcartName.setText(item.getGoods_name());
        //商品价格
        tvShopcartPrice.setText("¥" + item.getMarket_price());
        //商品数量
        tvShopcartNum.setText("X" + item.getNumber());


        if (!isEditor) {
            tvShopcartNum.setVisibility(View.VISIBLE);
            tvShopcartName.setVisibility(View.VISIBLE);
            customview.setVisibility(View.GONE);
            tvShopcartSelect.setVisibility(View.GONE);
        } else {
            tvShopcartNum.setVisibility(View.GONE);
            tvShopcartName.setVisibility(View.GONE);
            customview.setVisibility(View.VISIBLE);
            tvShopcartSelect.setVisibility(View.VISIBLE);
        }

        //默认为非选中的状态
        cbShopcart.setChecked(item.isSelect());
        cbShopcart.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                //当用户按压的时候
                if (buttonView.isPressed()) {
                    //将选中的状态改为当前按压时选中的状态
                    item.setSelect(isChecked);
                }
                if (clickItemListener != null) {
                    clickItemListener.onClickItem();
                }
            }
        });
    }

    public ClickItemListener clickItemListener;

    public void setClickItemListener(ClickItemListener clickItemListener) {
        this.clickItemListener = clickItemListener;
    }

    public interface ClickItemListener {
        void onClickItem();

        void onValue();
    }
}
