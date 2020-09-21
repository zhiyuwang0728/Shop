package com.zyw.myshop.test;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.zyw.myshop.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class TestActivity extends AppCompatActivity {

    @BindView(R.id.btn_click)
    Button btnClick;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        ButterKnife.bind(this);
        //

    }

    @OnClick(R.id.btn_click)
    public void onViewClicked() {
        Intent intent = new Intent();
        intent.setAction("imageActivity");
        intent.putExtra("img", "https://ss3.bdstatic.com/70cFv8Sh_Q1YnxGkpoWK1HF6hhy/it/u=331536043,3288893750&fm=26&gp=0.jpg");
        startActivity(intent);

    }
}