package com.wxthxy.driving.database;

import com.raizlabs.android.dbflow.annotation.Database;

/**
 * Created by Administrator on 2018/4/23.
 */

@Database(name = DBFlowDatabase.NAME,version = DBFlowDatabase.VERSION)
public class DBFlowDatabase {
    //数据库名称
    public static final String NAME = "DBFlowDatabase";
    //数据库版本号
    public static final int VERSION = 1;
}
