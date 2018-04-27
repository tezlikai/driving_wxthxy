package com.wxthxy.driving.view.record;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dinuscxj.refresh.RecyclerRefreshLayout;
import com.orhanobut.logger.Logger;
import com.wxthxy.driving.R;
import com.wxthxy.driving.common.AppConstants;
import com.wxthxy.driving.common.observer.IObservable;
import com.wxthxy.driving.common.observer.IObserver;
import com.wxthxy.driving.common.observer.ObserverHolder;
import com.wxthxy.driving.database.LocationModel;
import com.wxthxy.driving.mvp.MVPBaseFragment;
import com.wxthxy.driving.view.record.adapter.RecordAdapter;
import com.wxthxy.driving.view.record.detail.RecordDetailActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lk on 18-4-23.
 */

public class RecordFragment extends MVPBaseFragment<RecordContract.View, RecordPresenter> implements RecordContract.View {

    private static final String TAG = RecordFragment.class.getSimpleName();

    private View mView;

    private RecyclerView mRcvRecordContent;
    private RecordAdapter mAdapter;
    private List<LocationModel> mDatas = new ArrayList<>();
    private RecyclerRefreshLayout mRecyclerRefreshLayout;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ObserverHolder.getInstance().register(mObserver);
    }

    private IObserver mObserver = new IObserver() {
        @Override
        public void onMessageReceived(IObservable observable, Object msg, int flag) {
            Logger.d("msg:= "+"-----------"+"flag:="+flag);
            if (AppConstants.CAR_MESSAGE_SAVE_CODE == flag){
                mPresenter.loadingData();
            }
        }
    };


    @Override
    protected void initPrepare() {
        mRcvRecordContent = mView.findViewById(R.id.rcv_record_content);
        mRecyclerRefreshLayout = mView.findViewById(R.id.refresh_layout);
        mRecyclerRefreshLayout.setRefreshStyle(RecyclerRefreshLayout.RefreshStyle.NORMAL);
        mRcvRecordContent.setLayoutManager(new LinearLayoutManager(this.getContext()));

        /**添加item的添加和移除动画, 这里我们使用系统默认的动画*/
        mRcvRecordContent.setItemAnimator(new DefaultItemAnimator());

        mAdapter = new RecordAdapter(getActivity());

        mRcvRecordContent.setAdapter(mAdapter);

        //TODO:获取相关数据
        mAdapter.setOnItemClickListener(new RecordAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Logger.d("position： = " + position);
                toDetailPage(position);
            }
        });
        mRecyclerRefreshLayout.setOnRefreshListener(new RecyclerRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mPresenter.loadingData();
            }
        });


    }

    private void toDetailPage(int position) {
        LocationModel locationModel = mDatas.get(position);
        Intent intent  = new Intent(getActivity(), RecordDetailActivity.class);
        intent.putExtra("intent_data",locationModel);
        getActivity().startActivity(intent);
    }

    @Override
    protected void onInvisible() {

    }

    @Override
    protected void initData() {
        mPresenter.loadingData();
    }

    @Override
    protected View initView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_record, container, false);
        return mView;
    }

    @Override
    public void setData(List<LocationModel> mDataList) {
        mDatas = mDataList;
        Logger.d(TAG + "setData----------------");
        if (null != mAdapter) {
            mRecyclerRefreshLayout.setRefreshing(false);
            mAdapter.updateData(mDatas);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        ObserverHolder.getInstance().unregister(mObserver);
    }
}
