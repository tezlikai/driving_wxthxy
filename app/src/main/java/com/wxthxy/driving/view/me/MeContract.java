package com.wxthxy.driving.view.me;

import com.wxthxy.driving.mvp.BasePresenter;
import com.wxthxy.driving.mvp.BaseView;

/**
 * Created by lk on 18-4-17.
 */

public class MeContract {

    interface View extends BaseView {

    }

    interface  Presenter extends BasePresenter<View> {

    }
}
