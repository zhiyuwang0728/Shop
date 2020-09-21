package com.zyw.myshop.ui.home.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.zyw.myshop.R;
import com.zyw.myshop.base.BaseAdapter;
import com.zyw.myshop.bean.home.HomeBean;



public class SpecialAdapter extends BaseAdapter<HomeBean.DataBean.TopicListBean> {

    public SpecialAdapter(Context context) {
        super(context);
    }

    @Override
    protected int getLayout() {
        return R.layout.adapter_special_item;
    }

    @Override
    protected void onBindData(BaseViewHolder holder, int position, HomeBean.DataBean.TopicListBean item) {
        ImageView imgTopic = (ImageView) holder.getView(R.id.img_topic);
        TextView txtName = (TextView) holder.getView(R.id.txt_name);
        TextView txtPrice = (TextView) holder.getView(R.id.txt_price);
        TextView txtDes = (TextView) holder.getView(R.id.txt_des);

        Glide.with(context).load(item.getItem_pic_url()).into(imgTopic);
        txtDes.setText(item.getSubtitle());
        txtName.setText(item.getTitle());
        txtPrice.setText("¥" + item.getPrice_info() + "元/起");

    }
}
