package com.wxthxy.driving.view.record.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wxthxy.driving.R;
import com.wxthxy.driving.model.CarMessage;

import java.util.List;

/**
 * Created by lk on 18-4-23.
 */

public class RecordAdapter extends RecyclerView.Adapter {

    private static final String TAG = RecordAdapter.class.getSimpleName();

    private Context mContext;

    private List<CarMessage> mData;

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

    public RecordAdapter(Context mContext, List<CarMessage> mData) {
        this.mContext = mContext;
        this.mData = mData;
    }

    public void setmData(List<CarMessage> mData) {
        this.mData = mData;
    }

    public void updateData(List<CarMessage> data) {
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
        switch (getItemViewType(position)){
            case HAS_DATA:
                RecordViewHolder imageViewHolder = (RecordViewHolder) holder;
                imageViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(onItemClickListener != null){
                            onItemClickListener.onItemClick(v, position);
                        }
                    }
                });
                break;
        }

    }

    @Override
    public int getItemCount() {
        return null == mData ? 1 : mData.size(); // 正常数据为空的情况下返回0，这里在数据为空的情况下返回1，为了显示无数据的布局
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

        public RecordViewHolder(View itemView) {
            super(itemView);
        }
    }

    public interface OnItemClickListener {

        void onItemClick(View view, int position);
    }
}