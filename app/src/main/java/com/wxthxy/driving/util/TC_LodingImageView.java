package com.wxthxy.driving.util;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.util.AttributeSet;

/**
 * Created by Administrator on 2016/4/14.
 */
public class TC_LodingImageView extends android.support.v7.widget.AppCompatImageView {
    public TC_LodingImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        AnimationDrawable anim = (AnimationDrawable)getBackground();
        anim.start();
    }


}
