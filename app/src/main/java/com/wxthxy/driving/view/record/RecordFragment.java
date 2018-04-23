package com.wxthxy.driving.view.record;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.orhanobut.logger.Logger;
import com.wxthxy.driving.R;
import com.wxthxy.driving.mvp.MVPBaseFragment;
import com.wxthxy.driving.view.record.adapter.RecordAdapter;

/**
 * Created by lk on 18-4-23.
 */

public class RecordFragment extends MVPBaseFragment<RecordContract.View, RecordPresenter> implements RecordContract.View {

    private static final String TAG = RecordFragment.class.getSimpleName();

    private View mView;

    private RecyclerView mRcvRecordContent;
    private RecordAdapter mAdapter;

    @Override
    protected void initPrepare() {
        mRcvRecordContent = mView.findViewById(R.id.rcv_record_content);
        mRcvRecordContent.setLayoutManager(new LinearLayoutManager(this.getContext()));

        /**添加item的添加和移除动画, 这里我们使用系统默认的动画*/
        mRcvRecordContent.setItemAnimator(new DefaultItemAnimator());
    }

    @Override
    protected void onInvisible() {

    }

    @Override
    protected void initData() {
        mAdapter = new RecordAdapter(getActivity());
        mAdapter.setmData(null);
        mRcvRecordContent.setAdapter(mAdapter);
        //TODO:获取相关数据
        mAdapter.setOnItemClickListener(new RecordAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Logger.d("position： = "+ position);
            }
        });
    }

    @Override
    protected View initView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_record, container, false);
        return mView;
    }
}
