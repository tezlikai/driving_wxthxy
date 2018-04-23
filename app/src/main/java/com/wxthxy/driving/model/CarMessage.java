package com.wxthxy.driving.model;

import java.io.Serializable;

/**
 * Created by lk on 18-4-23.
 */

public class CarMessage implements Serializable {

    /**
     * 开始监测时间
     */
    private long startTime;


    /**
     * 结束监测时间
     */
    private long endTime;

    /**
     * 车辆行驶里程
     */
    private long totalmileage;

    /**
     * 车辆掉头
     */
    private boolean isTurnAround;

    /**
     * 车辆掉头 车辆掉头时间
     */
    private long turnAroundTime;

    /**
     * 车辆掉头次数
     */
    private int turnaroundSize;

    /**
     * 车辆转弯
     */
    private String turn;

    /**
     * 车辆转弯时间
     */
    private long turnTime;

    public long getStartTime() {
        return startTime;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    public long getEndTime() {
        return endTime;
    }

    public void setEndTime(long endTime) {
        this.endTime = endTime;
    }

    public long getTotalmileage() {
        return totalmileage;
    }

    public void setTotalmileage(long totalmileage) {
        this.totalmileage = totalmileage;
    }

    public boolean isTurnAround() {
        return isTurnAround;
    }

    public void setTurnAround(boolean turnAround) {
        isTurnAround = turnAround;
    }

    public long getTurnAroundTime() {
        return turnAroundTime;
    }

    public void setTurnAroundTime(long turnAroundTime) {
        this.turnAroundTime = turnAroundTime;
    }

    public int getTurnaroundSize() {
        return turnaroundSize;
    }

    public void setTurnaroundSize(int turnaroundSize) {
        this.turnaroundSize = turnaroundSize;
    }

    public String getTurn() {
        return turn;
    }

    public void setTurn(String turn) {
        this.turn = turn;
    }

    public long getTurnTime() {
        return turnTime;
    }

    public void setTurnTime(long turnTime) {
        this.turnTime = turnTime;
    }
}
