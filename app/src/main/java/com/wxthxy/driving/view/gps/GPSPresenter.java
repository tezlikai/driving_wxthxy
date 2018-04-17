package com.wxthxy.driving.view.gps;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.GpsSatellite;
import android.location.GpsStatus;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;

import com.baidu.location.BDLocation;
import com.orhanobut.logger.Logger;
import com.wxthxy.driving.baiduSdk.OnBaiduInforListener;
import com.wxthxy.driving.baiduSdk.SDKManager;
import com.wxthxy.driving.model.GPSInformationBase;
import com.wxthxy.driving.mvp.BasePresenterImpl;

import java.util.Iterator;

/**
 * Created by Administrator on 2018/3/31.
 */

public class GPSPresenter extends BasePresenterImpl<GPSContract.View> implements GPSContract.Presenter,
        OnBaiduInforListener {
    /**
     * 设定一个标准值，两次方位值在标准范围内则说明方向未发生变化，否则判断方向
     */
    private static final float STANDARD_ANGLE = 10f;

    public String[] parms = {Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION};

    private LocationManager mLocationManager;

    private OnGPSInforListener infor;

    /**
     * 记录上一次方位
     */
    private float previousBearing = 0f;

    public void setOnGPSInforListener(OnGPSInforListener infor) {
        this.infor = infor;
    }

    @Override
    public void initGPS() {
        mLocationManager = (LocationManager) mView.getContext().getSystemService(Context.LOCATION_SERVICE); // 位置
        if (!mLocationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            // TODO: Open GPS
        } else {
            String bestProvider = mLocationManager.getBestProvider(
                    getLocationCriteria(), true);
            // 获取位置信息
            // 如果不设置查询要求，getLastKnownLocation方法传人的参数为LocationManager.GPS_PROVIDER
            if (ActivityCompat.checkSelfPermission(mView.getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(mView.getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                mView.warnPermissions(parms);
                return;
            }
            Location location = mLocationManager.getLastKnownLocation(bestProvider);
            // 监听状态
            mLocationManager.addGpsStatusListener(gpsStatusListener);
            // 绑定监听，有4个参数
            // 参数1，设备：有GPS_PROVIDER和NETWORK_PROVIDER两种
            // 参数2，位置信息更新周期，单位毫秒
            // 参数3，位置变化最小距离：当位置距离变化超过此值时，将更新位置信息
            // 参数4，监听
            // 备注：参数2和3，如果参数3不为0，则以参数3为准；参数3为0，则通过时间来定时更新；两者为0，则随时刷新

            // 1秒更新一次，或最小位移变化超过1米更新一次；
            // 注意：此处更新准确度非常低，推荐在service里面启动一个Thread，在run中sleep(10000);然后执行handler.sendMessage(),更新位置
            mLocationManager.requestLocationUpdates(
                    bestProvider, 200, 0, locationListener);
        }
    }

    @Override
    public void removeGPS() {
        if (mLocationManager != null) {
            mLocationManager.removeGpsStatusListener(gpsStatusListener);
            mLocationManager = null;
        }
    }

    @Override
    public void getBaiduInfor() {
        SDKManager.getInstance().setOnBaiduInforListener(this);
    }

    /**
     * 返回查询条件
     *
     * @return
     */
    private Criteria getLocationCriteria() {
        Criteria criteria = new Criteria();
        // 设置定位精确度 Criteria.ACCURACY_COARSE比较粗略，Criteria.ACCURACY_FINE则比较精细
        criteria.setAccuracy(Criteria.ACCURACY_FINE);
        criteria.setSpeedRequired(true); // 设置是否要求速度
        criteria.setCostAllowed(false); // 设置是否允许运营商收费
        criteria.setBearingRequired(true); // 设置是否需要方位信息
        criteria.setAltitudeRequired(false); // 设置是否需要海拔信息
        criteria.setPowerRequirement(Criteria.POWER_LOW); // 设置对电源的需求
        return criteria;
    }

    /**
     * 状态监听
     */
    GpsStatus.Listener gpsStatusListener = new GpsStatus.Listener() {
        public void onGpsStatusChanged(int event) {
            switch (event) {
                case GpsStatus.GPS_EVENT_FIRST_FIX: // 第一次定位
                    Logger.i("GPS_EVENT_FIRST_FIX");
                    break;
                case GpsStatus.GPS_EVENT_SATELLITE_STATUS: // 卫星状态改变
                    if (ActivityCompat.checkSelfPermission(mView.getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                        mView.warnPermissions(parms);
                        return;
                    }
                    GpsStatus gpsStatus = mLocationManager.getGpsStatus(null); // 获取当前状态
                    int maxSatellites = gpsStatus.getMaxSatellites(); // 获取卫星颗数的默认最大值
                    Iterator<GpsSatellite> iters = gpsStatus.getSatellites()
                            .iterator(); // 创建一个迭代器保存所有卫星
                    int count = 0;
                    while (iters.hasNext() && count <= maxSatellites) {
                        GpsSatellite s = iters.next();
                        count++;
                    }
//                    Logger.i("Satellite Number:" + count);
                    break;
                case GpsStatus.GPS_EVENT_STARTED: // 定位启动
                    Logger.i("GPS_EVENT_STARTED");
                    break;
                case GpsStatus.GPS_EVENT_STOPPED: // 定位结束
                    Logger.i("GPS_EVENT_STOPPED");
                    break;
            }
        }
    };

    /**
     * 位置监听
     */
    private LocationListener locationListener = new LocationListener() {

        /**
         * 位置信息变化时触发
         */
        public void onLocationChanged(Location location) {
            // location.getAltitude(); -- 海拔
            Logger.d("onLocationChanged location = " + location);
            updateSpeedByLocation(location);
        }

        /**
         * GPS状态变化时触发
         */
        public void onStatusChanged(String provider, int status, Bundle extras) {
            switch (status) {
                case LocationProvider.AVAILABLE: // GPS状态为可见时
                    Logger.i("当前GPS状态为可见状态");
                    break;

                case LocationProvider.OUT_OF_SERVICE: // GPS状态为服务区外时
                    Logger.i("当前GPS状态为服务区外状态");
                    break;

                case LocationProvider.TEMPORARILY_UNAVAILABLE: // GPS状态为暂停服务时
                    Logger.i("当前GPS状态为暂停服务状态");
                    break;
            }
        }

        /**
         * GPS开启时触发
         */
        public void onProviderEnabled(String provider) {
            if (ActivityCompat.checkSelfPermission(mView.getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(mView.getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                mView.warnPermissions(parms);
                return;
            }
            Location location = mLocationManager.getLastKnownLocation(provider);
            Logger.d("onProviderEnabled location = " + location);
            updateSpeedByLocation(location);
        }

        /**
         * GPS禁用时触发
         */
        public void onProviderDisabled(String provider) {
            // updateView(null);
        }
    };

    /**
     * 根据Location获取速度
     *
     * @param location
     */
    private void updateSpeedByLocation(Location location) {
        // Location类的方法：
        // getAccuracy():精度（ACCESS_FINE_LOCATION／ACCESS_COARSE_LOCATION）
        // getAltitude():海拨
        // getBearing():方位，行动方向
        // getLatitude():纬度
        // getLongitude():经度
        // getProvider():位置提供者（GPS／NETWORK）
        // getSpeed():速度
        // getTime():时刻
        int tempSpeed = (int) (location.getSpeed() * 3.6); // m/s --> Km/h
        float bearing = location.getBearing();
        String dir = changeBearingToDirection(bearing);

        Logger.i("Speed:" + tempSpeed + " bearing:" + bearing);

        GPSInformationBase inforBase = new GPSInformationBase();
        inforBase.setSpeed(tempSpeed);
        inforBase.setBearing(bearing);
        inforBase.setDirection(dir);
        infor.onGPSInfor(inforBase);
    }

    private String changeBearingToDirection(float bearing) {
        if (previousBearing != 0f) {
            //向右方
            if (bearing - previousBearing >= STANDARD_ANGLE) {
                previousBearing = bearing;
                return "右拐";
            } else if (previousBearing - bearing > STANDARD_ANGLE) {
                previousBearing = bearing;
                return "左拐";
            } else {
                previousBearing = bearing;
                return "直行";
            }
        } else {
            previousBearing = bearing;
            return "直行";
        }
    }

    @Override
    public void onBaiduInfor(BDLocation bdLocation) {
        if (bdLocation == null) {
            Logger.e("百度定位返回null");
            return;
        }
        int tempSpeed = (int) (bdLocation.getSpeed() * 3.6); // m/s --> Km/h
        GPSInformationBase inforBase = new GPSInformationBase();
        inforBase.setSpeed((int)bdLocation.getSpeed());
        inforBase.setBearing(bdLocation.getDirection());
        inforBase.setDirection(String.valueOf(bdLocation.getSpeed()));
        infor.onGPSInfor(inforBase);
    }
}
