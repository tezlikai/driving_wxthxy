package com.wxthxy.driving.view.gps;

import com.wxthxy.driving.model.GPSInformationBase;

/**
 * Created by Administrator on 2018/3/31.
 */

public interface OnGPSInforListener {
    /**
     * 百度定位SDK返回的信息
     * @param infor
     */
    void onGPSInfor(GPSInformationBase infor);
}
