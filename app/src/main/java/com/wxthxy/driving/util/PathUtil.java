package com.wxthxy.driving.util;

import android.text.TextUtils;

/**
 * Created by lk on 18-1-23.
 */

public class PathUtil {

    /**
     * 获取扩展名
     */
    public static String getExtension(String fileName) {
        if (TextUtils.isEmpty(fileName)) {
            return null;
        } else {
            return fileName.substring(fileName.lastIndexOf(".")+1);
        }
    }

}
