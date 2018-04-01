package com.wxthxy.driving.view.main;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import com.lzy.widget.AlphaIndicator;
import com.wxthxy.driving.R;
import com.wxthxy.driving.mvp.MVPBaseActivity;
import com.wxthxy.driving.view.gps.GPSFragment;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends MVPBaseActivity<MainContract.View, MainPresenter> implements MainContract.View {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ViewPager viewPager = (ViewPager) findViewById(R.id.viewPager);
        viewPager.setAdapter(new MainAdapter(getSupportFragmentManager()));
        AlphaIndicator alphaIndicator = (AlphaIndicator) findViewById(R.id.alphaIndicator);
        alphaIndicator.setViewPager(viewPager);
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
            fragments.add(TextFragment.newInstance(titles[1]));
            fragments.add(TextFragment.newInstance(titles[2]));
            fragments.add(TextFragment.newInstance(titles[3]));
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
}
