package com.wxthxy.driving.view.splash;

import android.content.Context;
import android.content.Intent;

import com.wxthxy.driving.mvp.BasePresenterImpl;
import com.wxthxy.driving.view.login.LoginActivity;
import com.wxthxy.driving.view.main.MainActivity;

/**
 * MVPPlugin
 *  邮箱 784787081@qq.com
 */

public class SplashPresenter extends BasePresenterImpl<SplashContract.View> implements SplashContract.Presenter{

    @Override
    public void toMainPage() {
        Intent intent = new Intent(mView.getContext(), LoginActivity.class);
        mView.getContext().startActivity(intent);
        mView.finishActivity();
    }
}
