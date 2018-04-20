package com.wxthxy.driving.view.me.aboutHibeauty;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;

import com.wxthxy.driving.R;
import com.wxthxy.driving.mvp.MVPBaseActivity;
import com.wxthxy.driving.util.AppUtil;


public class AboutHibeautyActivity extends MVPBaseActivity<AboutHibeautyContract.View, AboutHibeautyPresenter> implements AboutHibeautyContract.View {

    private TextView mAppVersion;
    private Toolbar mToolbar;
    private TextView mToolbarTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_hibeauty);
        mToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        mToolbarTitle = findViewById(R.id.toolbar_title);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        mToolbarTitle.setText("关于应用");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        initView();
    }

    public void initView() {
        mAppVersion = findViewById(R.id.tv_app_version);
        initData();
    }

    private void initData() {
        mAppVersion.setText("v" + AppUtil.getVersionName(this));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
