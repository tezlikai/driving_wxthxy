package com.wxthxy.driving.view.location;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.baidu.location.BDLocation;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationData;
import com.orhanobut.logger.Logger;
import com.wxthxy.driving.R;
import com.wxthxy.driving.baiduSdk.OnBaiduInforListener;
import com.wxthxy.driving.baiduSdk.SDKManager;
import com.wxthxy.driving.mvp.MVPBaseFragment;

/**
 * Created by Administrator on 2018/4/15.
 */

public class LocationFragment extends MVPBaseFragment<LocationContract.View, LocationPresenter> implements OnBaiduInforListener {
    private View mView;
    private MapView mMapView;
    private BaiduMap mBaiduMap;

    @Override
    protected void initPrepare() {
        mMapView = (MapView) mView.findViewById(R.id.bmapView);

        mBaiduMap = mMapView.getMap();
        MapStatusUpdate msu = MapStatusUpdateFactory.zoomTo(15.0f);
        mBaiduMap.setMapStatus(msu);
        // 开启定位图层
        mBaiduMap.setMyLocationEnabled(true);
        SDKManager.getInstance().setOnBaiduInforListener(this);
    }

    @Override
    protected void onInvisible() {

    }

    @Override
    protected void initData() {

    }

    @Override
    protected View initView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        SDKInitializer.initialize(this.getActivity().getApplication());
        mView = inflater.inflate(R.layout.fragment_location, container, false);
        return mView;
    }

    @Override
    public void onBaiduInfor(BDLocation bdLocation) {
        Logger.d("从百度地图定位到：方位" + bdLocation.getDirection() + " 速度" + bdLocation.getSpeed());
        if (bdLocation != null && mBaiduMap != null) {
            final MyLocationData locationData = new MyLocationData.Builder().accuracy(bdLocation.getRadius()).
                    accuracy(bdLocation.getRadius()).
                    direction(bdLocation.getDirection()).
                    latitude(bdLocation.getLatitude()).
                    longitude(bdLocation.getLongitude()).
                    build();
            mBaiduMap.setMyLocationData(locationData);

            // 设置定位图层的配置（定位模式，是否允许方向信息，用户自定义定位图标）
            BitmapDescriptor currentMarker = BitmapDescriptorFactory
                    .fromResource(R.mipmap.icon_geo);
            MyLocationConfiguration config = new MyLocationConfiguration(MyLocationConfiguration.LocationMode.FOLLOWING, true, currentMarker);
            mBaiduMap.setMyLocationConfiguration(config);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mBaiduMap == null) {
            return;
        }
        // 当不需要定位图层时关闭定位图层
        mBaiduMap.setMyLocationEnabled(false);
    }
}
