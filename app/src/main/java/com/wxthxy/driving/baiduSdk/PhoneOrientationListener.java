package com.wxthxy.driving.baiduSdk;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

/**
 * 由于百度的方位direction获取的是手机面朝正北是的方向，不是水平手机方向，与需求不符
 * 所以需要通过监听手机本身的方向传感器来替代百度的方向
 * Created by Administrator on 2018/4/17.
 */

public class PhoneOrientationListener implements SensorEventListener {
    private Context context;
    private SensorManager sensorManager;
    private Sensor sensor;

    private float lastX;

    private OnOrientationListener onOrientationListener;

    public PhoneOrientationListener(Context context) {
        this.context = context;
    }

    /**
     * 开启手机方向传感器
     */
    public void start() {
        // 获得传感器管理器
        sensorManager = (SensorManager) context
                .getSystemService(Context.SENSOR_SERVICE);
        if (sensorManager != null) {
            // 获得方向传感器
            sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION);
        }
        // 注册
        if (sensor != null) {//SensorManager.SENSOR_DELAY_UI
            sensorManager.registerListener(this, sensor,
                    SensorManager.SENSOR_DELAY_UI);
        }

    }

    /**
     * 停止传感器监测
     */
    public void stop() {
        sensorManager.unregisterListener(this);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        // 接受方向感应器的类型
        if (event.sensor.getType() == Sensor.TYPE_ORIENTATION) {
            // 这里我们可以得到数据，然后根据需要来处理
            float x = event.values[SensorManager.DATA_X];

            if (Math.abs(x - lastX) > 1.0) {
                onOrientationListener.onOrientationChanged(x);
            }
//            Log.e("DATA_X", x+"");
            lastX = x;

        }
    }

    public void setOnOrientationListener(OnOrientationListener onOrientationListener) {
        this.onOrientationListener = onOrientationListener;
    }

    public interface OnOrientationListener {
        void onOrientationChanged(float x);
    }
}
