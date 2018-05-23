package com.wxthxy.driving.database;

import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;

import java.io.Serializable;

/**
 * Created by Administrator on 2018/4/23.
 */

@Table(database = DBFlowDatabase.class)
public class LocationModel extends BaseModel implements Serializable{
    @Column
    @PrimaryKey(autoincrement = true)
    public long id;

    /**
     * 开始监测时间
     */
    @Column
    public long startTime;

    /**
     * 结束监测时间
     */
    @Column
    public long endTime;

    /**
     * 车辆行驶里程
     * km
     */
    @Column
    public long totalmileage;
    /**
     * 平均速度
     * km/h
     */
    @Column
    public long averageVelocity;

    /**
     * 车辆掉头
     */
    @Column
    public boolean isTurnAround;

    /**
     * 车辆掉头次数
     */
    @Column
    public int turnaroundSize;

    /**
     * 车辆左拐
     */
    @Column
    public boolean isTurnLeft;

    /**
     * 车辆左拐次数
     */
    @Column
    public int turnLeftSize;

    /**
     * 车辆右拐
     */
    @Column
    public boolean isTurnRight;

    /**
     * 车辆右拐次数
     */
    @Column
    public int turnRSize;
}
