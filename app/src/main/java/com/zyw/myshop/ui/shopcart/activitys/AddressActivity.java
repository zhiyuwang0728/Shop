package com.zyw.myshop.ui.shopcart.activitys;

import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.weigan.loopview.LoopView;
import com.weigan.loopview.OnItemSelectedListener;
import com.zyw.mylibrary.ToastUtils;
import com.zyw.myshop.R;
import com.zyw.myshop.base.BaseActivity;
import com.zyw.myshop.bean.shopcart.AddressBean;
import com.zyw.myshop.interfaces.shop.IShop;
import com.zyw.myshop.presenters.shop.AddressPresenter;
import com.zyw.myshop.util.DP2PXUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AddressActivity extends BaseActivity<IShop.AddressPresenter> implements IShop.AddressView {

    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_remeber)
    TextView tvRemeber;
    @BindView(R.id.et_people)
    EditText etPeople;
    @BindView(R.id.et_phone)
    EditText etPhone;
    @BindView(R.id.et_address)
    TextView etAddress;
    private Map<Integer, List<AddressBean.DataBean>> addressMap;
    private PopupWindow popupWindow;
    private LoopView province, city, area;
    private TextView txtProvince, txtCity, txtArea;

    private int curProvinceId, curCityId, curAreaId; //当前省市区的ID
    private TextView txt_submit;

    @Override
    protected IShop.AddressPresenter onCreatePresenter() {
        return new AddressPresenter();
    }

    @Override
    protected void initData() {
        mPresenter.getAddressData(1);
    }

    @Override
    protected void initView() {
        ivBack.setVisibility(View.GONE);
        addressMap = new HashMap<>();
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_address;
    }


    @OnClick({R.id.tv_remeber, R.id.et_people, R.id.et_phone, R.id.et_address})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_remeber:
                break;
            case R.id.et_people:
                etPeople.setSelection(etPeople.getText().length());
                break;
            case R.id.et_phone:
                etPhone.setSelection(etPhone.getText().length());
                break;
            case R.id.et_address:
                initPopWindow();
                break;
        }
    }

    private void initPopWindow() {
        View popView = LayoutInflater.from(this).inflate(R.layout.layout_address_pop, null);
        int height = DP2PXUtils.dp2px(this, 250);
        popupWindow = new PopupWindow(popView, ViewGroup.LayoutParams.MATCH_PARENT, height);
        popupWindow.setFocusable(false);
        popupWindow.setOutsideTouchable(false);
        popupWindow.setContentView(popView);
        popView.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);

        province = popView.findViewById(R.id.adress_province);
        city = popView.findViewById(R.id.adress_city);
        area = popView.findViewById(R.id.adress_area);
        txtProvince = popView.findViewById(R.id.txt_province);
        txtCity = popView.findViewById(R.id.txt_city);
        txtArea = popView.findViewById(R.id.txt_area);
        txt_submit = popView.findViewById(R.id.txt_submit);
        txt_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                etAddress.setText(txtProvince.getText().toString()+txtCity.getText().toString()+txtArea.getText().toString());
                popupWindow.dismiss();
                popupWindow = null;
            }
        });

        popupWindow.showAtLocation(etAddress, Gravity.BOTTOM, 0, 0);


        //省份
        province.setListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(int index) {
                List<AddressBean.DataBean> proviceList = addressMap.get(1); //key为1固定为省的数据
                AddressBean.DataBean dataBean = proviceList.get(index);
                curProvinceId = dataBean.getId();
                mPresenter.getAddressData(curProvinceId);
                List<String> items = new ArrayList<>();
                items.add("请选择");
                city.setItems(items);
                txtProvince.setText(dataBean.getName());
                txtCity.setText("请选择城市");
                txtArea.setText("请选中区域");
            }
        });

        city.setListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(int index) {
                List<AddressBean.DataBean> cityList = addressMap.get(curProvinceId); //key省份id
                AddressBean.DataBean dataBean = cityList.get(index);
                curCityId = dataBean.getId();
                mPresenter.getAddressData(curCityId);
                area.setItems(new ArrayList<>());
                txtCity.setText(dataBean.getName());
                txtArea.setText("请选中区域");
            }
        });

        area.setListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(int index) {
                List<AddressBean.DataBean> areaList = addressMap.get(curCityId); //key省份id
                AddressBean.DataBean dataBean = areaList.get(index);
                curAreaId = dataBean.getId();
                txtArea.setText(dataBean.getName());
            }
        });

        //初始化省份的数据
        List<AddressBean.DataBean> pList = addressMap.get(1);
        if (pList == null) return;
        List<String> adresses = getAdressStrings(pList);
        if (pList == null || adresses.size() == 0) {
            mPresenter.getAddressData(1);
        } else {
            province.setItems(adresses);
            curProvinceId = pList.get(0).getId();
            txtProvince.setText(adresses.get(0));
        }

    }

    private static final String TAG = "AddressActivity";

    @Override
    public void getAddressReturn(AddressBean result) {
        Log.d(TAG, "getAddressReturn: " + result.toString());
        List<AddressBean.DataBean> list = null;
        int type = 0;
        for (AddressBean.DataBean item : result.getData()) {
            int key = item.getParent_id();
            list = addressMap.get(key);
            if (list == null) {
                list = new ArrayList<>();
                addressMap.put(key, list);
            }
            boolean bool = hasList(item.getId(), list);
            if (!bool) list.add(item);
            if (type == 0) {
                type = item.getType();
            }
        }
        if (list == null) return;
        List<String> adresses = getAdressStrings(list);
        if (type == 1) {
            //刷新省的数据
            if (province != null) {
                curProvinceId = list.get(0).getId();
                txtProvince.setText(list.get(0).getName());
                province.setItems(adresses);
            }
        } else if (type == 2) {
            //刷新市的数据
            if (city != null) {
                curCityId = list.get(0).getId();
                txtCity.setText(list.get(0).getName());
                city.setItems(adresses);
            }
        } else {
            //区
            if (area != null) {
                curAreaId = list.get(0).getId();
                txtArea.setText(list.get(0).getName());
                area.setItems(adresses);
            }
        }
    }

    /**
     * 判断当前的地址列表中是否有这个地址
     *
     * @param id
     * @param list
     * @return
     */
    private boolean hasList(int id, List<AddressBean.DataBean> list) {
        boolean bool = false;
        for (AddressBean.DataBean item : list) {
            if (item.getId() == id) {
                bool = true;
                break;
            }
        }
        return bool;
    }


    /**
     * 提取省市区的名字
     *
     * @param list
     * @return
     */
    private List<String> getAdressStrings(List<AddressBean.DataBean> list) {
        List<String> adresses = new ArrayList<>();
        for (AddressBean.DataBean item : list) {
            adresses.add(item.getName());
        }
        return adresses;
    }

    /**
     * 通过id获取当前数据中的对象
     *
     * @param id
     * @param list
     * @return
     */
    private AddressBean.DataBean getDataBeanById(int id, List<AddressBean.DataBean> list) {
        for (AddressBean.DataBean item : list) {
            if (item.getId() == id) return item;
        }
        return null;
    }
}