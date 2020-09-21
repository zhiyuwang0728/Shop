package com.zyw.myshop;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.tabs.TabLayout;
import com.zyw.mylibrary.ToastUtils;
import com.zyw.myshop.ui.classify.ClassifyFragment;
import com.zyw.myshop.ui.home.HomeFragment;
import com.zyw.myshop.ui.mine.MineFragment;
import com.zyw.myshop.ui.shopcart.ShopCartFragment;
import com.zyw.myshop.ui.special.SpecialFragment;
import com.zyw.myshop.util.SpUtils;

import butterknife.BindView;
import butterknife.ButterKnife;


public class MainActivity extends AppCompatActivity {


    @BindView(R.id.mFL)
    FrameLayout mFL;
    @BindView(R.id.tabLayout)
    TabLayout tabLayout;
    private FragmentManager supportFragmentManager;
    private HomeFragment homeFragment;
    private SpecialFragment specialFragment;
    private ClassifyFragment classifyFragment;
    private ShopCartFragment shopCartFragment;
    private MineFragment mineFragment;


    @Override
    protected void onStart() {
        super.onStart();
        //暂时
        SpUtils.getInstance().setValue("token", "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VyX2lkIjoiM2UzMjUxOGYtNzE4OS00NWE2LTkwYzYtNDE1ZWQ5YjdhN2E1IiwiaWF0IjoxNjAwNDg2ODMxfQ.yDJh7dWUHcLi8pgH55__NnN2guzfCcdyzk_6sqY9F2U");
        //SpUtils.getInstance().clearKey("token");
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        supportFragmentManager = getSupportFragmentManager();
        //初始化Fragment
        initFragment();
        //先将首页显示到首页
        initShowHome();
        //添加tab
        tabLayout.addTab(tabLayout.newTab().setCustomView(setBackground("首页", R.drawable.home_select)));
        tabLayout.addTab(tabLayout.newTab().setCustomView(setBackground("专题", R.drawable.classification_select)));
        tabLayout.addTab(tabLayout.newTab().setCustomView(setBackground("分类", R.drawable.special_select)));
        tabLayout.addTab(tabLayout.newTab().setCustomView(setBackground("购物车", R.drawable.shopcart_select)));
        tabLayout.addTab(tabLayout.newTab().setCustomView(setBackground("我的", R.drawable.me_select)));
        //添加监听 显示隐藏
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switchFragment(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    private void switchFragment(int position) {
        FragmentTransaction fragmentTransaction = supportFragmentManager.beginTransaction();
        switch (position) {
            case 0:
                if (!homeFragment.isAdded()) {
                    fragmentTransaction.add(R.id.mFL, homeFragment).show(homeFragment);
                }
                fragmentTransaction.show(homeFragment).hide(classifyFragment).hide(specialFragment).hide(shopCartFragment).hide(mineFragment);
                break;
            case 1:
                if (!specialFragment.isAdded()) {
                    fragmentTransaction.add(R.id.mFL, specialFragment).show(specialFragment);
                }
                fragmentTransaction.show(specialFragment).hide(classifyFragment).hide(homeFragment).hide(shopCartFragment).hide(mineFragment);
                break;
            case 2:
                if (!classifyFragment.isAdded()) {
                    fragmentTransaction.add(R.id.mFL, classifyFragment).show(classifyFragment);
                }
                fragmentTransaction.show(classifyFragment).hide(specialFragment).hide(homeFragment).hide(shopCartFragment).hide(mineFragment);
                break;
            case 3:
                if (!shopCartFragment.isAdded()) {
                    fragmentTransaction.add(R.id.mFL, shopCartFragment).show(shopCartFragment);
                }
                fragmentTransaction.show(shopCartFragment).hide(specialFragment).hide(homeFragment).hide(classifyFragment).hide(mineFragment);

                break;
            case 4:
                if (!mineFragment.isAdded()) {
                    fragmentTransaction.add(R.id.mFL, mineFragment).show(mineFragment);
                }
                fragmentTransaction.show(mineFragment).hide(specialFragment).hide(homeFragment).hide(classifyFragment).hide(shopCartFragment);
                break;

        }
        fragmentTransaction.commit();
    }

    private void initShowHome() {
        supportFragmentManager.beginTransaction()
                .add(R.id.mFL, homeFragment)
                .show(homeFragment)
                .commit();
    }


    private void initFragment() {
        homeFragment = new HomeFragment();
        specialFragment = new SpecialFragment();
        classifyFragment = new ClassifyFragment();
        shopCartFragment = new ShopCartFragment();
        mineFragment = new MineFragment();
    }


    public View setBackground(String name, int resource) {
        View inflate = LayoutInflater.from(this).inflate(R.layout.main_tabmodel, null);
        ImageView iv_search = inflate.findViewById(R.id.iv_select);
        TextView tv_select = inflate.findViewById(R.id.tv_select);
        iv_search.setBackgroundResource(resource);
        tv_select.setText(name);
        return inflate;
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        tabLayout.getTabAt(3).select();
    }
}