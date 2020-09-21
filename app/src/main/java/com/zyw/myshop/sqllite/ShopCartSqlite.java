package com.zyw.myshop.sqllite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.zyw.myshop.bean.home.HomeBean;

import java.util.ArrayList;
import java.util.List;

public class ShopCartSqlite extends SQLiteOpenHelper {
    private String tabName = "hotShop";

    public ShopCartSqlite(@Nullable Context context) {
        super(context, "shopcart.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table hotShop(id integer,name varchar(40),list_pic_url text ,retail_price float,goods_brief text)");
    }

    public void insertData(HomeBean.DataBean.HotGoodsListBean data) {
        SQLiteDatabase writableDatabase = getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put("id", data.getId());
        contentValues.put("name", data.getName());
        contentValues.put("list_pic_url", data.getList_pic_url());
        contentValues.put("retail_price", data.getRetail_price());
        contentValues.put("goods_brief", data.getGoods_brief());

        writableDatabase.insert(tabName, null, contentValues);
    }

    public List<HomeBean.DataBean.HotGoodsListBean> queryData() {
        SQLiteDatabase writableDatabase = getWritableDatabase();
        Cursor query = writableDatabase.query(tabName, null, null, null, null, null, null);

        List<HomeBean.DataBean.HotGoodsListBean> dataList = new ArrayList<>();
        while (query.moveToNext()) {
            int id = query.getInt(query.getColumnIndex("id"));
            String name = query.getString(query.getColumnIndex("name"));
            String list_pic_url = query.getString(query.getColumnIndex("list_pic_url"));
            float retail_price = query.getFloat(query.getColumnIndex("retail_price"));
            String goods_brief = query.getString(query.getColumnIndex("goods_brief"));
            HomeBean.DataBean.HotGoodsListBean hotGoodsListBean = new HomeBean.DataBean.HotGoodsListBean();
            hotGoodsListBean.setId(id);
            hotGoodsListBean.setName(name);
            hotGoodsListBean.setList_pic_url(list_pic_url);
            hotGoodsListBean.setRetail_price(retail_price);
            hotGoodsListBean.setGoods_brief(goods_brief);
            dataList.add(hotGoodsListBean);
        }
        return dataList;
    }

    public void deleteData(String name) {
        SQLiteDatabase writableDatabase = getWritableDatabase();
        writableDatabase.delete(tabName, "name =?", new String[]{name});
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
