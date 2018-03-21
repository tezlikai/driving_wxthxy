package com.wxthxy.driving.app;

import android.app.Application;
import android.content.Context;

import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.BuildConfig;
import com.orhanobut.logger.Logger;

/**
 * Created by lk on 18-3-17.
 */

public class MyApp extends Application {

    private static Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;

        /**
         * 日志管理
         */


        Logger.addLogAdapter(new AndroidLogAdapter());

        Logger.addLogAdapter(new AndroidLogAdapter() {
            @Override public boolean isLoggable(int priority, String tag) {
                return BuildConfig.DEBUG;
            }
        });
    }

    /**
     * 获取全局的 Context
     * @return
     */
    public static Context getAppContext() {
        return mContext;
    }

}
