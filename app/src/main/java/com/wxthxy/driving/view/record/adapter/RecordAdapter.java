package com.wxthxy.driving.view.record.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.wxthxy.driving.R;
import com.wxthxy.driving.database.LocationModel;
import com.wxthxy.driving.util.TimeUtils;

import java.util.List;

/**
 * Created by lk on 18-4-23.
 */

public class RecordAdapter extends RecyclerView.Adapter {

    private static final String TAG = RecordAdapter.class.getSimpleName();

    private Context mContext;

    private List<LocationModel> mData;

    private final int NO_DATA = 0, HAS_DATA = 1;//无数据，有数据


    /**
     * 事件回调监听
     */
    private RecordAdapter.OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    private View mView;
    private LayoutInflater mInflater;


    public RecordAdapter(Context mContext) {
        this.mContext = mContext;
    }

    public RecordAdapter(Context mContext, List<LocationModel> mData) {
        this.mContext = mContext;
        this.mData = mData;
    }

    public void setmData(List<LocationModel> mData) {
        this.mData = mData;
    }

    public void updateData(List<LocationModel> data) {
        this.mData = data;
        notifyDataSetChanged();
    }


    /**
     * 确定使用哪种布局
     *
     * @param position
     * @return
     */
    @Override
    public int getItemViewType(int position) {
        if (null == mData || mData.size() <= 0) { //无数据情况处理
            return NO_DATA;
        } else {
            return HAS_DATA;
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        mInflater = LayoutInflater.from(parent.getContext());
        RecyclerView.ViewHolder mHolder = null;
        switch (viewType) {
            case NO_DATA:
                mHolder = new NoDataViewHolder(mInflater.inflate(R.layout.item_no_data, parent, false));
                break;
            case HAS_DATA:
                mHolder = new RecordViewHolder(mInflater.inflate(R.layout.item_car_msg_list, parent, false));
                break;
        }
        return mHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        if (null == mData || mData.size() <= 0) { //无数据的情况
            return;
        }
        switch (getItemViewType(position)) {
            case HAS_DATA:
                RecordViewHolder imageViewHolder = (RecordViewHolder) holder;
                final LocationModel locationModel = mData.get(position);
                imageViewHolder.mCarStartTime.setText("开始时间： "+TimeUtils.millis2String(locationModel.startTime * 1000L, "yyyy年MM月dd日 HH:mm:ss"));
                imageViewHolder.mCarEndTime.setText("结束时间： "+TimeUtils.millis2String(locationModel.endTime * 1000L, "yyyy年MM月dd日 HH:mm:ss"));
                imageViewHolder.mCarTotalMileage.setText("行驶里程数 ： " + locationModel.totalmileage);
                imageViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (onItemClickListener != null) {
                            onItemClickListener.onItemClick(v, position);
                        }
                    }
                });
                imageViewHolder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View view) {
                        onItemClickListener.onItemLongClick(view,locationModel.id);
                        return false;
                    }
                });
                break;
        }


    }

    @Override
    public int getItemCount() {
//        return null == mData ? 1 : mData.size(); // 正常数据为空的情况下返回0，这里在数据为空的情况下返回1，为了显示无数据的布局

        if (null == mData || mData.size() == 0) {
            return 1;
        } else {
            return mData.size();
        }
    }

    /**
     * 没有数据的布局
     */
    public static class NoDataViewHolder extends RecyclerView.ViewHolder {

        public NoDataViewHolder(View itemView) {
            super(itemView);
        }
    }

    /**
     * 有数据的布局
     */
    public static class RecordViewHolder extends RecyclerView.ViewHolder {

        private TextView mCarStartTime;
        private TextView mCarEndTime;
        private TextView mCarTotalMileage;
        private CardView mCvItemView;

        public RecordViewHolder(View itemView) {
            super(itemView);
            mCarStartTime = itemView.findViewById(R.id.tv_car_time);
            mCarEndTime = itemView.findViewById(R.id.tv_car_end_time);
            mCarTotalMileage = itemView.findViewById(R.id.tv_car_total_mileage);
            mCvItemView = itemView.findViewById(R.id.cv_item_view);
        }
    }

    public interface OnItemClickListener {

        void onItemClick(View view, int position);
        void onItemLongClick(View view, long position);
    }
}
