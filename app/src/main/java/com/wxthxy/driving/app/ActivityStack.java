package com.wxthxy.driving.app;

import android.app.Activity;

import java.util.Iterator;
import java.util.Stack;

public class ActivityStack {

    private static Stack<Activity> activityStack;
    private static ActivityStack instance;

    private ActivityStack() {
    }

    ;

    /**
     * 单个实例
     */
    public static ActivityStack getActivityManager() {
        if (instance == null) {
            instance = new ActivityStack();
        }
        return instance;
    }

    /**
     * add Activity to activityStack
     */
    public void addActivity(Activity activity) {
        if (activityStack == null) {
            activityStack = new Stack<Activity>();
        }
        String contextString = activity.toString();
        activityStack.add(activity);
    }

    public void cleanOtherActivity(Activity activity) {
        if (activityStack == null) {
            activityStack = new Stack<Activity>();
        }
        Iterator<Activity> iterator = activityStack.iterator();
        while (iterator.hasNext()) {
            iterator.next().finish();
            iterator.remove();
        }
    }


    /**
     * finish当前Activity（堆栈中最后一个压入的）
     */
    public void finishActivity() {
        Activity activity = activityStack.lastElement();
        finishActivity(activity);
    }

    /**
     * finish 指定的Activity
     */
    public void finishActivity(Activity activity) {
        if (activity != null) {
            activityStack.remove(activity);
        }
    }

    /**
     * finish 指定类名的Activity
     */
    public void finishActivity(Class<?> cls) {
        for (Activity activity : activityStack) {
            if (activity.getClass().equals(cls)) {
                finishActivity(activity);
            }
        }
    }

    /**
     * finish all Activity
     */
    public void finishAllActivity() {
        for (Activity activity : activityStack) {
            activity.finish();
        }
        activityStack.clear();
    }

    /**
     * 退出应用程序
     */
    public void appexit() {
        try {
            finishAllActivity();
            System.exit(0);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}

