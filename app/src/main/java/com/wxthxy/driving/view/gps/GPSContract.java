package com.wxthxy.driving.view.gps;

import com.wxthxy.driving.mvp.BasePresenter;
import com.wxthxy.driving.mvp.BaseView;

/**
 * Created by Administrator on 2018/3/31.
 */

public class GPSContract {
    interface View extends BaseView{
        void showToast(CharSequence message);
        void warnPermissions(String[] parms);
    }

    interface Presenter extends BasePresenter<View>{
        void initGPS();
        void removeGPS();
        void getBaiduInfor();
    }
}
