package com.wxthxy.driving.mvp;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.wxthxy.driving.R;
import com.wxthxy.driving.app.ActivityStack;

import java.lang.reflect.ParameterizedType;


public abstract class MVPBaseActivity<V extends BaseView, T extends BasePresenterImpl<V>> extends AppCompatActivity implements BaseView {
    public T mPresenter;

    public boolean doubleBackToExit = false;
    private Toolbar mToolbar;
    private TextView mToolbarTitle;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = getInstance(this, 1);
        mPresenter.attachView((V) this);
        ActivityStack.getActivityManager().addActivity(this);
        //toolBar
        mToolbar = findViewById(R.id.toolbar);

        mToolbarTitle = findViewById(R.id.toolbar_title);

        if (mToolbar != null) {
            // 将Toolbar显示到界面
            setSupportActionBar(mToolbar);
        }
        if (mToolbarTitle != null) {
            // getTitle()的值是activity的android:lable属性值
            mToolbarTitle.setText(getTitle());
            // 设置默认的标题不显示
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }
    }

    /**
     * 获取头部标题的TextView
     */
    public TextView getToolbarTitle() {
        return mToolbarTitle;
    }


    public void exitDialog() {
        ActivityStack.getActivityManager().appexit();
    }

    @Override
    protected void onDestroy() {
        ActivityStack.getActivityManager().finishActivity(this);
        super.onDestroy();
        if (mPresenter != null)
            mPresenter.detachView();
    }

    @Override
    public Context getContext() {
        return this;
    }

    public <T> T getInstance(Object o, int i) {
        try {
            return ((Class<T>) ((ParameterizedType) (o.getClass()
                    .getGenericSuperclass())).getActualTypeArguments()[i])
                    .newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (ClassCastException e) {
            e.printStackTrace();
        }
        return null;
    }
}
