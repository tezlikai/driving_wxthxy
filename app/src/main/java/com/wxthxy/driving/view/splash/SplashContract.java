package com.wxthxy.driving.view.splash;

import android.content.Context;

import com.wxthxy.driving.mvp.BasePresenter;
import com.wxthxy.driving.mvp.BaseView;

/**
 * MVPPlugin
 *  邮箱 784787081@qq.com
 */

public class SplashContract {
    interface View extends BaseView {

        /**
         * 关闭闪屏页面
         */
        void finishActivity();
    }

    interface  Presenter extends BasePresenter<View> {

        /**
         * 跳转到主页面
         */
        void toMainPage();
    }
}
