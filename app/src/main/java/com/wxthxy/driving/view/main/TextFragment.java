package com.wxthxy.driving.view.main;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.baidu.location.BDLocation;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationData;
import com.orhanobut.logger.Logger;
import com.wxthxy.driving.R;
import com.wxthxy.driving.baiduSdk.OnBaiduInforListener;
import com.wxthxy.driving.baiduSdk.SDKManager;

/**
 * Created by lk on 18-3-17.
 */

public class TextFragment extends Fragment implements OnBaiduInforListener {
    public static final String BUNDLE_TITLE = "title";
    private String mTitle = "DefaultValue";
    private View mView;
    private MapView mMapView;
    private BaiduMap mBaiduMap;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//        Bundle arguments = getArguments();
//        if (arguments != null) {
//            mTitle = arguments.getString(BUNDLE_TITLE);
//        }
//
//        TextView tv = new TextView(getActivity());
//        tv.setText(mTitle);
//        tv.setTextSize(30);
//        tv.setPadding(50, 50, 50, 50);
//        tv.setGravity(Gravity.CENTER);
        //在使用SDK各组件之前初始化context信息，传入ApplicationContext
        //注意该方法要再setContentView方法之前实现
        SDKInitializer.initialize(this.getActivity().getApplication());
        mView = inflater.inflate(R.layout.fragment_location, container, false);
        mMapView = (MapView)mView.findViewById(R.id.bmapView);
        mBaiduMap = mMapView.getMap();
        // 开启定位图层
        mBaiduMap.setMyLocationEnabled(true);
        SDKManager.getInstance().setOnBaiduInforListener(this);
        return mView;
    }

    public static TextFragment newInstance(String title) {
        Bundle bundle = new Bundle();
        bundle.putString(BUNDLE_TITLE, title);
        TextFragment fragment = new TextFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onBaiduInfor(BDLocation bdLocation) {
        Logger.d("从百度地图定位到：方位"+bdLocation.getDirection()+" 速度"+bdLocation.getSpeed());
        if (bdLocation != null && mBaiduMap != null){
            final MyLocationData locationData = new MyLocationData.Builder().accuracy(bdLocation.getRadius()).
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
        if (mBaiduMap == null){
            return;
        }
        // 当不需要定位图层时关闭定位图层
        mBaiduMap.setMyLocationEnabled(false);
    }
}

