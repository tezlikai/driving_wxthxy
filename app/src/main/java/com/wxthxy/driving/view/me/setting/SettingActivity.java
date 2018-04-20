package com.wxthxy.driving.view.me.setting;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.wxthxy.driving.R;
import com.wxthxy.driving.mvp.MVPBaseActivity;
import com.wxthxy.driving.util.CleanUtils;
import com.wxthxy.driving.widget.CommonDialog;

import java.util.HashMap;


/**
 * @author lk
 */
public class SettingActivity extends MVPBaseActivity<SettingContarct.View, SettingPresenter> implements SettingContarct.View, View.OnClickListener {

    private static final String TAG = SettingActivity.class.getSimpleName();

    private TextView mLogout;
    private TextView mCacheSize;
    private String mTotalCacheSize;
    private TextView mCacheClean;
    private Toolbar mToolbar;
    private TextView mToolbarTitle;
    private CommonDialog mDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        initView();
    }


    public void initView() {
        mToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        mToolbarTitle = findViewById(R.id.toolbar_title);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        mToolbarTitle.setText("设置");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mLogout = findViewById(R.id.tv_logout);
        mCacheSize = findViewById(R.id.tv_size_cache);
        mCacheClean = findViewById(R.id.tv_cache_clean);
        initEvent();
        initData();

    }

    private void initData() {
        initCacheSize();
    }

    private void initCacheSize() {
        try {
            mTotalCacheSize = CleanUtils.getTotalCacheSize(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
        mCacheSize.setText(mTotalCacheSize);
    }

    private void initEvent() {
        mLogout.setOnClickListener(this);
        mCacheClean.setOnClickListener(this);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.tv_logout:
                mDialog = new CommonDialog(this, "确定退出应用吗?", new CommonDialog.OnClickListener() {
                    @Override
                    public void onClick(Dialog dialog, boolean confirm) {
                        if (confirm) {
                            mDialog.dismiss();
                            exitDialog();
                        }else {
                            mDialog.dismiss();
                        }
                    }
                });
                mDialog.show();
                break;

            case R.id.tv_cache_clean:
                CleanUtils.cleanInternalCache();
                initCacheSize();
                break;
            default:
                break;
        }
    }
}
