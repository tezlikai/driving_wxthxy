package com.wxthxy.driving.view.record;

import com.wxthxy.driving.database.LocationModel;
import com.wxthxy.driving.mvp.BasePresenter;
import com.wxthxy.driving.mvp.BaseView;

import java.util.List;

/**
 * Created by lk on 18-4-17.
 */

public class RecordContract {

    interface View extends BaseView {

        void setData(List<LocationModel> mDatas);
    }

    interface  Presenter extends BasePresenter<View> {

        void loadingData();
    }
}
