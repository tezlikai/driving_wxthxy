package com.wxthxy.driving.view.record;

import com.orhanobut.logger.Logger;
import com.raizlabs.android.dbflow.sql.language.SQLite;
import com.wxthxy.driving.database.LocationModel;
import com.wxthxy.driving.mvp.BasePresenterImpl;

import java.util.List;

/**
 * Created by lk on 18-4-17.
 */

public class RecordPresenter extends BasePresenterImpl<RecordContract.View> implements RecordContract.Presenter {


    private static final String TAG = RecordPresenter.class.getSimpleName();

    private List<LocationModel> mDatas;

    /**
     * 获取车辆信息数据
     */
    @Override
    public void loadingData() {
        mDatas = SQLite.select().from(LocationModel.class).queryList();

        Logger.d(TAG,mDatas.size());
        mView.setData(mDatas);
    }
}
