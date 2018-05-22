package com.wxthxy.driving.view.login;


import com.wxthxy.driving.mvp.BasePresenter;
import com.wxthxy.driving.mvp.BaseView;

/**
 * Created by lk on 17-9-7.
 */

public class LoginContract {

    interface View extends BaseView {
    }

    interface Presenter extends BasePresenter<View> {

    }

}
