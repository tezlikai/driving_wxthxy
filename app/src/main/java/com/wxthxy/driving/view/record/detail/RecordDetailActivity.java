package com.wxthxy.driving.view.record.detail;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.wxthxy.driving.R;
import com.wxthxy.driving.database.LocationModel;
import com.wxthxy.driving.mvp.MVPBaseActivity;
import com.wxthxy.driving.util.TimeUtils;

import java.io.Serializable;

public class RecordDetailActivity extends MVPBaseActivity<RecordDetailContract.View, RecordDetailPresenter> implements RecordDetailContract.View {

    private static final String INTENT_DATA = "intent_data";

    private Toolbar mToolbar;
    private TextView mToolbarTitle;
    private TextView mStartTime;
    private TextView mEndTime;
    private TextView mTurnLeftSize;
    private TextView mTurnRightSize;
    private TextView mMileage;
    private TextView mSpeed;
    private LocationModel mData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record_detail);
        mToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        mToolbarTitle = findViewById(R.id.toolbar_title);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        mToolbarTitle.setText("行驶信息详情");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        getIntentData();

        initView();

        initData();
    }

    private void getIntentData() {
        mData = (LocationModel) getIntent().getSerializableExtra(INTENT_DATA);
    }

    private void initView() {
        mStartTime = findViewById(R.id.tv_start_time);
        mEndTime = findViewById(R.id.tv_endt_time);
        mTurnLeftSize = findViewById(R.id.tv_turn_left);
        mTurnRightSize = findViewById(R.id.tv_turn_right);
        mSpeed = findViewById(R.id.tv_speed);
        mMileage = findViewById(R.id.tv_mileage);
    }

    private void initData() {

        if (null != mData) {
            mStartTime.setText(TimeUtils.millis2String(mData.startTime * 1000L, "yyyy年MM月dd日 HH:mm:ss"));
            mEndTime.setText(TimeUtils.millis2String(mData.endTime * 1000L, "yyyy年MM月dd日 HH:mm:ss"));
            mTurnLeftSize.setText(mData.turnLeftSize + "");
            mTurnRightSize.setText(mData.turnRSize + "");
            mSpeed.setText(mData.averageVelocity + "km/h");
            mMileage.setText(mData.totalmileage + " km");
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
