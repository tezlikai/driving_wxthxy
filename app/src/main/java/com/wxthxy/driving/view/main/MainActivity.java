package com.wxthxy.driving.view.main;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.KeyEvent;
import android.widget.Toast;

import com.jaeger.library.StatusBarUtil;
import com.lzy.widget.AlphaIndicator;
import com.wxthxy.driving.R;
import com.wxthxy.driving.baiduSdk.SDKManager;
import com.wxthxy.driving.mvp.MVPBaseActivity;
import com.wxthxy.driving.view.gps.GPSFragment;
import com.wxthxy.driving.view.location.LocationFragment;
import com.wxthxy.driving.view.me.MeFragment;
import com.wxthxy.driving.view.record.RecordFragment;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends MVPBaseActivity<MainContract.View, MainPresenter> implements MainContract.View {

    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        StatusBarUtil.setTranslucentForImageViewInFragment(MainActivity.this, null);
        mViewPager = (ViewPager) findViewById(R.id.viewPager);
        mViewPager.setOffscreenPageLimit(3);
        mViewPager.setAdapter(new MainAdapter(getSupportFragmentManager()));
        AlphaIndicator alphaIndicator = (AlphaIndicator) findViewById(R.id.alphaIndicator);
        alphaIndicator.setViewPager(mViewPager);
    }

    private class MainAdapter extends FragmentPagerAdapter {

        private List<Fragment> fragments = new ArrayList<>();
        private String[] titles = {//
                "第一页\n\n车辆行驶过程中的数据监测和显示",//
                "第二页\n\n车辆行驶历史数据的显示",//
                "第三页\n\n车辆当前位置的定位", //
                "第四页\n\n个人信息和应用设置"};

        public MainAdapter(FragmentManager fm) {
            super(fm);
            fragments.add(new GPSFragment());
            fragments.add(new RecordFragment());
            fragments.add(new LocationFragment());
            fragments.add(new MeFragment());
        }

        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        SDKManager.getInstance().startLocation(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        SDKManager.getInstance().stopLocation();
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (doubleBackToExit) {
                exitDialog();
                return true;
            }
            Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
            this.doubleBackToExit = true;
            new Handler().postDelayed(new Runnable() {

                @Override
                public void run() {
                    doubleBackToExit = false;

                }
            }, 2000);

            return false;
        }

        return super.onKeyUp(keyCode, event);
    }
}
