package com.wxthxy.driving.view.splash;

import android.Manifest;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.WindowManager;

import com.orhanobut.logger.Logger;
import com.wxthxy.driving.R;
import com.wxthxy.driving.common.AppConstants;
import com.wxthxy.driving.util.SpUtils;
import com.wxthxy.driving.mvp.MVPBaseActivity;
import com.wxthxy.driving.widget.CircleProgressbar;

import java.util.List;

import pub.devrel.easypermissions.AppSettingsDialog;
import pub.devrel.easypermissions.EasyPermissions;

public class SplashActivity extends MVPBaseActivity<SplashContract.View, SplashPresenter> implements SplashContract.View,EasyPermissions.PermissionCallbacks {

    public static final int RC_LOCATION_CONTACTS_PERM = 124;
    public static final int RC_CAMERA_PERMISSION = 7;

    private boolean isFrist;

    private CircleProgressbar mCircleProgressbar;

    private boolean isClick = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash);
        Logger.d("hello onStart");
        initView();
        initData();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    /**
     * 初始化控件
     */
    private void initView() {

        isFrist = (boolean) SpUtils.get(this, AppConstants.SP_FIRSTOPEN, true);
        mCircleProgressbar = findViewById(R.id.tv_red_skip);
        mCircleProgressbar.setOutLineColor(Color.TRANSPARENT);
        mCircleProgressbar.setInCircleColor(Color.parseColor("#505559"));
        mCircleProgressbar.setProgressColor(Color.parseColor("#1BB079"));
        mCircleProgressbar.setProgressLineWidth(5);
        mCircleProgressbar.setProgressType(CircleProgressbar.ProgressType.COUNT);
        mCircleProgressbar.setTimeMillis(5000);

        if (isFrist) {
            checkPermissions();
        }else {
            mCircleProgressbar.reStart();
            mCircleProgressbar.setCountdownProgressListener(1,progressListener);
        }

        mCircleProgressbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                isClick = true;
                mPresenter.toMainPage();
            }
        });
    }

    private CircleProgressbar.OnCountdownProgressListener progressListener = new CircleProgressbar.OnCountdownProgressListener() {
        @Override
        public void onProgress(int what, int progress)
        {

            if(what==1 && progress==100 && !isClick)
            {
                mPresenter.toMainPage();
            }

        }
    };

    /**
     * 初始化数据
     */
    private void initData() {

    }

    public void checkPermissions() {
        String[] parms = {Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.CAMERA,
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION};
        SpUtils.put(this, AppConstants.SP_FIRSTOPEN, false);
        if (EasyPermissions.hasPermissions(this, parms)) {//设置权限
            //TODO: 有对应权限的操作
            mCircleProgressbar.reStart();
            mCircleProgressbar.setCountdownProgressListener(1,progressListener);
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
        Logger.w("onPermissionsGranted"+ requestCode);
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