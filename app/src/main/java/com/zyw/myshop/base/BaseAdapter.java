package com.zyw.myshop.base;

import android.content.Context;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public abstract class BaseAdapter<T> extends RecyclerView.Adapter {

    protected List<T> mData = new ArrayList<>();

    protected Context context;

    public BaseAdapter(Context context) {
        this.context = context;
    }

    //添加数据/下拉刷新
    public void addData(List<T> mData) {
        if (mData != null) {
            this.mData.clear();
            this.mData.addAll(mData);
            notifyDataSetChanged();
        }
    }

    // 上拉加载
    public void loadMore(List<T> mData) {
        if (mData != null) {
            this.mData.addAll(mData);
            notifyDataSetChanged();
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(context).inflate(getLayout(), parent, false);
        BaseViewHolder baseViewHolder = new BaseViewHolder(inflate);

        //接口回调
        baseViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onClickItemListener != null) {
                    onClickItemListener.onClickItem(baseViewHolder.getLayoutPosition());
                }
            }
        });
        return baseViewHolder;
    }

    protected abstract int getLayout();

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        BaseViewHolder baseViewHolder=(BaseViewHolder) holder;
        T item = mData.get(position);
        onBindData(baseViewHolder, position, item);
    }

    protected abstract void onBindData(BaseViewHolder holder, int position, T item);

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public OnClickItemListener onClickItemListener;

    public void setOnClickItemListener(OnClickItemListener onClickItemListener) {
        this.onClickItemListener = onClickItemListener;
    }

    public interface OnClickItemListener {
        void onClickItem(int position);
    }

    public class BaseViewHolder extends RecyclerView.ViewHolder {
        //类似于HashMap的集合 key值只能是int 类型
        private SparseArray sparseArray;

        public BaseViewHolder(@NonNull View itemView) {
            super(itemView);
            sparseArray = new SparseArray();
        }

        //创建方法来找布局
        public View getView(int id) {
            //先判断容器中是否有该布局
            View view = (View) sparseArray.get(id);
            if (view == null) {
                //如果没有就找到该布局并把它添加进容器中
                view = itemView.findViewById(id);
                sparseArray.append(id, view);
            }
            return view;
        }
    }
}
