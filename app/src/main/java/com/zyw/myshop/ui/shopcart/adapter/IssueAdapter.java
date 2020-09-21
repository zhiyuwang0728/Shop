package com.zyw.myshop.ui.shopcart.adapter;

import android.content.Context;
import android.widget.TextView;

import com.zyw.myshop.R;
import com.zyw.myshop.base.BaseAdapter;
import com.zyw.myshop.bean.shopcart.ShopDetailBean;

import butterknife.BindView;

public class IssueAdapter extends BaseAdapter<ShopDetailBean.DataBeanX.IssueBean> {

    public IssueAdapter(Context context) {
        super(context);
    }

    @Override
    protected int getLayout() {
        return R.layout.adapter_shop_gooddetail;
    }

    @Override
    protected void onBindData(BaseViewHolder holder, int position, ShopDetailBean.DataBeanX.IssueBean item) {
        TextView tvShopQuestion = (TextView) holder.getView(R.id.tv_shop_question);
        TextView tvShopAnswer = (TextView) holder.getView(R.id.tv_shop_answer);
        tvShopQuestion.setText(item.getQuestion());
        tvShopAnswer.setText(item.getAnswer());
    }
}
