package com.zyw.imageloader;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;

public class ImageActivity extends AppCompatActivity {

    private ImageView mImg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image);
        initView();
        Intent intent = getIntent();
        String img = intent.getStringExtra("img");

        Glide.with(this).load(img).into(mImg);
    }

    private void initView() {
        mImg = (ImageView) findViewById(R.id.mImg);
    }
}