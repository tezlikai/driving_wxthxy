package com.wxthxy.driving.view.gps;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.orhanobut.logger.Logger;
import com.wxthxy.driving.R;
import com.wxthxy.driving.common.AppConstants;
import com.wxthxy.driving.common.observer.ObserverHolder;
import com.wxthxy.driving.database.LocationModel;
import com.wxthxy.driving.model.GPSInformationBase;
import com.wxthxy.driving.mvp.MVPBaseFragment;
import com.wxthxy.driving.util.MapDistance;
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
    private TextView mStart;
    private TextView mEnd;
    private View mView;
    private ImageView mRightImg;
    private ImageView mLeftImg;
    private ImageView mHeadImg;

    /**
     * 记录前一个纬度
     */
    private double preLatitude = 0d;

    /**
     * 记录前一个经度
     */
    private double preLongitude = 0d;

    /**
     * 统计开启时行驶距离
     */
    private double mDistance = 0d;

    private int mCountTurnLeft = 0;

    private int mCountTurnRight = 0;

    private int mCountTurnHead = 0;

    /**
     * 开始时间(单位：秒)
     */
    private long startTime;

    /**
     * 结束时间(单位：秒)
     */
    private long endTime;

    private boolean isTurnRighting = false;

    private boolean isTurnLeft = false;

    private boolean isTurnHeadB = false;

    private LocationModel mLocationModel;

    @Override
    protected void initPrepare() {

        mDashboardView = mView.findViewById(R.id.dashboard_view);
        mStart = mView.findViewById(R.id.start_btn);
        mEnd = mView.findViewById(R.id.end_btn);
        mEnd.setEnabled(false);
        mRightImg = mView.findViewById(R.id.direction_right);
        mLeftImg = mView.findViewById(R.id.direction_left);
        mHeadImg = mView.findViewById(R.id.direction_head);

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
                mStart.setBackgroundResource(R.mipmap.gps_btn_on);
                mEnd.setEnabled(true);
                mPresenter.getBaiduInfor();
                mPresenter.setOnGPSInforListener(this);
                startTime = System.currentTimeMillis() / 1000;
                showToast("开启GPS");
                break;
            case R.id.end_btn:
                mStart.setBackgroundResource(R.mipmap.gps_btn);
                mEnd.setEnabled(false);
                mPresenter.setOnGPSInforListener(null);
//                mPresenter.removeGPS();
//                showToast("关闭GPS,行驶距离" + mDistance + "右转次数 = " + mCountTurnRight + " 左转次数 = " + mCountTurnLeft + " 调头次数 = " + mCountTurnHead);
                endTime = System.currentTimeMillis() / 1000;

                //保存数据到数据库
                mLocationModel = new LocationModel();
                mLocationModel.startTime = startTime;
                mLocationModel.endTime = endTime;
                if (mCountTurnHead > 0) {
                    mLocationModel.isTurnAround = true;
                    mLocationModel.turnaroundSize = mCountTurnHead;
                } else {
                    mLocationModel.isTurnAround = false;
                    mLocationModel.turnaroundSize = 0;
                }
                if (mCountTurnRight > 0) {
                    mLocationModel.isTurnRight = true;
                    mLocationModel.turnRSize = mCountTurnRight;
                } else {
                    mLocationModel.isTurnRight = false;
                    mLocationModel.turnRSize = 0;
                }
                if (mCountTurnLeft > 0) {
                    mLocationModel.isTurnLeft = true;
                    mLocationModel.turnLeftSize = mCountTurnLeft;
                } else {
                    mLocationModel.isTurnLeft = false;
                    mLocationModel.turnLeftSize = 0;
                }
                mLocationModel.totalmileage = Math.round(mDistance);
                mLocationModel.save();

                mDistance = 0d;
                mCountTurnLeft = 0;
                mCountTurnHead = 0;
                mCountTurnRight = 0;
                isTurnLeft = false;
                isTurnRighting = false;
                isTurnHeadB = false;
                mRightImg.setBackgroundResource(R.mipmap.gps_right);
                mLeftImg.setBackgroundResource(R.mipmap.gps_left);
                mHeadImg.setBackgroundResource(R.mipmap.gps_head);
                ObserverHolder.getInstance().notifyObservers(AppConstants.CAR_MESSAGE_SAVE,AppConstants.CAR_MESSAGE_SAVE_CODE);
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
        dealDirectionDisplay(infor.getDirection(), infor.isTurnHead());

        if (preLongitude != 0d && preLatitude != 0d) {
            mDistance = mDistance + MapDistance.getInstance().getLatLngDistance(preLongitude, preLatitude, infor.getLongitude(), infor.getLatitude());
        }
        preLatitude = infor.getLatitude();
        preLongitude = infor.getLongitude();

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
        mStart.setBackgroundResource(R.mipmap.gps_btn);
        mEnd.setBackgroundResource(R.mipmap.gps_btn);
//        mPresenter.removeGPS();
        mDistance = 0d;
        mPresenter.setOnGPSInforListener(null);
    }

    /**
     * 处理通过方法判断出车辆的方向时，界面的显隐
     *
     * @param direction
     * @param isTurnHead
     */
    private void dealDirectionDisplay(int direction, boolean isTurnHead) {
        if (direction == AppConstants.TRUN_RIGHT) {
            mRightImg.setBackgroundResource(R.mipmap.gps_right_on);
            mLeftImg.setBackgroundResource(R.mipmap.gps_left);
            if (!isTurnRighting) {
                ++mCountTurnRight;
            }
            isTurnLeft = false;
            isTurnRighting = true;
        } else if (direction == AppConstants.TRUN_LEFT) {
            mLeftImg.setBackgroundResource(R.mipmap.gps_left_on);
            mRightImg.setBackgroundResource(R.mipmap.gps_right);
            if (!isTurnLeft) {
                ++mCountTurnLeft;
            }
            isTurnLeft = true;
            isTurnRighting = false;
        } else if (direction == AppConstants.STRAIGHT_LINE) {
            mRightImg.setBackgroundResource(R.mipmap.gps_right);
            mLeftImg.setBackgroundResource(R.mipmap.gps_left);
            isTurnRighting = false;
            isTurnLeft = false;
        }

        if (isTurnHead) {
            mRightImg.setBackgroundResource(R.mipmap.gps_right);
            mLeftImg.setBackgroundResource(R.mipmap.gps_left);
            mHeadImg.setBackgroundResource(R.mipmap.gps_head_on);
            if (!isTurnHeadB) {
                ++mCountTurnHead;
            }
            isTurnHeadB = true;
        } else {
            mHeadImg.setBackgroundResource(R.mipmap.gps_head);
            isTurnHeadB = false;
        }
    }
}
