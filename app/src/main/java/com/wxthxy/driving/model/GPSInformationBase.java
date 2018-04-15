package com.wxthxy.driving.model;

/**
 * Created by Administrator on 2018/3/31.
 */

public class GPSInformationBase {

    /**
     * 速度
     */
    private int speed;

    /**
     * 方位
     */
    private float bearing;

    /**
     * 方向
     */
    private String direction;

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public float getBearing() {
        return bearing;
    }

    public void setBearing(float bearing) {
        this.bearing = bearing;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    @Override
    public String toString() {
        return "GPSInformationBase{" +
                "speed=" + speed +
                ", bearing=" + bearing +
                ", direction='" + direction + '\'' +
                '}';
    }
}
