package com.wxthxy.driving.view.gps;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.orhanobut.logger.Logger;
import com.wxthxy.driving.R;
import com.wxthxy.driving.model.GPSInformationBase;
import com.wxthxy.driving.mvp.MVPBaseFragment;
import com.wxthxy.driving.util.ToastUtil;
import com.wxthxy.driving.view.custom.DashboardView;

import pub.devrel.easypermissions.EasyPermissions;

/**
 * Created by Administrator on 2018/3/31.
 */

public class GPSFragment extends MVPBaseFragment<GPSContract.View, GPSPresenter> implements GPSContract.View, OnGPSInforListener,
        View.OnClickListener {
    public static final int RC_CAMERA_PERMISSION = 7;

    private DashboardView mDashboardView;
    private TextView mDirection;
    private View mStart;
    private View mEnd;
    private View mView;

    @Override
    protected void initPrepare() {

        mDashboardView = mView.findViewById(R.id.dashboard_view);
        mDirection = mView.findViewById(R.id.direction_tv);
        mStart = mView.findViewById(R.id.start_btn);
        mEnd = mView.findViewById(R.id.end_btn);

        mStart.setOnClickListener(this);
        mEnd.setOnClickListener(this);
    }

    @Override
    protected void onInvisible() {

    }

    @Override
    protected void initData() {

    }

    @Override
    protected View initView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_gps, container, false);
        return mView;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.start_btn:
                mPresenter.initGPS();
                mPresenter.setOnGPSInforListener(this);
                showToast("开启GPS");
                break;
            case R.id.end_btn:
                mPresenter.removeGPS();
                showToast("关闭GPS");
                break;
        }
    }

    @Override
    public void onGPSInfor(GPSInformationBase infor) {
        if (infor == null) {
            Logger.e("gps 返回 null");
            return;
        }
        Logger.d("接收到GPS信息 =" + infor);
        //速度
        if (mDashboardView != null) {
            mDashboardView.setVelocity(infor.getSpeed());
        }
        if (mDirection != null && !TextUtils.isEmpty(infor.getDirection())) {
            mDirection.setText(infor.getDirection());
        }
        showToast(infor.toString());
    }

    @Override
    public void showToast(CharSequence message) {
        ToastUtil.showShort(getContext(), message);
    }

    @Override
    public void warnPermissions(String[] parms) {
        EasyPermissions.requestPermissions(this, "为使程序正常运行请打开相应权限", RC_CAMERA_PERMISSION, parms);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mPresenter.removeGPS();
    }
}
