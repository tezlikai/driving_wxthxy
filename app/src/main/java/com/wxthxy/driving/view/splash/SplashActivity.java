package com.wxthxy.driving.view.splash;

import android.Manifest;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.TextView;

import com.orhanobut.logger.Logger;
import com.wxthxy.driving.R;
import com.wxthxy.driving.common.AppConstants;
import com.wxthxy.driving.mvp.MVPBaseActivity;
import com.wxthxy.driving.util.SpUtils;

import java.util.List;

import pub.devrel.easypermissions.AppSettingsDialog;
import pub.devrel.easypermissions.EasyPermissions;

public class SplashActivity extends MVPBaseActivity<SplashContract.View, SplashPresenter> implements SplashContract.View, View.OnClickListener, EasyPermissions.PermissionCallbacks {

    public static final int RC_LOCATION_CONTACTS_PERM = 124;
    public static final int RC_CAMERA_PERMISSION = 7;

    private View mTvSplash;
    private boolean isFrist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        Logger.d("hello onStart");
        initView();
        initData();
        initEvent();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    /**
     * 初始化控件
     */
    private void initView() {

        mTvSplash = (TextView) findViewById(R.id.tv_splash);
        isFrist = (boolean) SpUtils.get(this, AppConstants.SP_FIRSTOPEN, true);
        if (isFrist) {
            checkPermissions();
        }

    }

    /**
     * 初始化数据
     */
    private void initData() {

    }

    /**
     * 初始化事件
     */
    private void initEvent() {

        mTvSplash.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.tv_splash:
                mPresenter.toMainPage();
                break;

            default:
                break;

        }
    }

    public void checkPermissions() {
        String[] parms = {Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.CAMERA,
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION};
        SpUtils.put(this, AppConstants.SP_FIRSTOPEN, false);
        if (EasyPermissions.hasPermissions(this, parms)) {//设置权限
            //TODO: 有对应权限的操作

        } else {
            EasyPermissions.requestPermissions(this, "为使程序正常运行请打开相应权限", RC_CAMERA_PERMISSION, parms);
        }
    }

    @Override
    public void finishActivity() {
        finish();
    }

    @Override
    public void onPermissionsGranted(int requestCode, @NonNull List<String> perms) {
        if (requestCode == RC_CAMERA_PERMISSION) {
            Logger.d("SplashActivity-----权限处理");
            //TODO:权限获取后的业务操作
        }
    }

    @Override
    public void onPermissionsDenied(int requestCode, @NonNull List<String> perms) {
        if (requestCode == RC_LOCATION_CONTACTS_PERM) {
            // Do something after user returned from app settings screen, like showing a Toast.
            if (EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {
                new AppSettingsDialog.Builder(this)
                        .setTitle("提示")
                        .setPositiveButton("设置")
                        .setNegativeButton("取消")
                        .setRequestCode(RC_LOCATION_CONTACTS_PERM)
                        .build()
                        .show();
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        // Forward results to EasyPermissions
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);

    }
}