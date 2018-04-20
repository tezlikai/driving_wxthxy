package com.wxthxy.driving.widget;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wxthxy.driving.R;


/**
 * Created by lk on 17-9-21.
 */

public class CommonDialog extends Dialog implements View.OnClickListener {

    private TextView contentTxt;
    private TextView titleTxt;
    private TextView submitTxt;
    private TextView cancelTxt;
    private TextView singleTxt;

    private Context mContext;
    private String content;
    private OnClickListener listener;
    private String positiveName;
    private String negativeName;
    private String singlePositiveName;
    private String title;
    private boolean isSingle = false;
    private LinearLayout mDoubleBtns;
    private boolean isExitApp;

    public CommonDialog(Context context) {
        super(context, R.style.dialog);
        this.mContext = context;
    }

    public CommonDialog(Context context, int themeResId, String content) {
        super(context, themeResId);
        this.mContext = context;
        this.content = content;
    }

    public CommonDialog(Context context, int themeResId, String content, OnClickListener listener) {
        super(context, themeResId);
        this.mContext = context;
        this.mContext = context;
        this.content = content;
        this.listener = listener;
    }

    protected CommonDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        this.mContext = context;
    }


    public CommonDialog(@NonNull Context context, String content, OnClickListener listener) {
        super(context, R.style.dialog);
        this.mContext = context;
        this.content = content;
        this.listener = listener;
    }

    public CommonDialog(@NonNull Context context, boolean isExitApp, String content, OnClickListener listener) {
        super(context, R.style.dialog);
        this.mContext = context;
        this.content = content;
        this.isExitApp = isExitApp;
        this.listener = listener;
    }

    public CommonDialog(@NonNull Context context, String content, String negativeName, String positiveName, boolean isSingle, OnClickListener listener) {
        super(context, R.style.dialog);
        this.mContext = context;
        this.content = content;
        this.listener = listener;
        this.positiveName = positiveName;
        this.negativeName = negativeName;
        this.isSingle = isSingle;
    }

    public CommonDialog(@NonNull Context context, String content, boolean isSingle, OnClickListener listener) {
        super(context, R.style.dialog);
        this.mContext = context;
        this.content = content;
        this.listener = listener;
        this.isSingle = isSingle;
    }

    public CommonDialog(@NonNull Context context, String content, String singlePositiveName, boolean isSingle, OnClickListener listener) {
        super(context, R.style.dialog);
        this.mContext = context;
        this.content = content;
        this.listener = listener;
        this.singlePositiveName = singlePositiveName;
        this.isSingle = isSingle;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setListener(OnClickListener listener) {
        this.listener = listener;
    }

    public void setPositiveName(String positiveName) {
        this.positiveName = positiveName;
    }

    public void setNegativeName(String negativeName) {
        this.negativeName = negativeName;
    }

    public void setSinglePositiveName(String singlePositiveName) {
        this.singlePositiveName = singlePositiveName;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setSingle(boolean single) {
        isSingle = single;
    }

    public void setDoubleBtns(LinearLayout doubleBtns) {
        mDoubleBtns = doubleBtns;
    }

    public boolean isExitApp() {
        return isExitApp;
    }

    public void setExitApp(boolean exitApp) {
        isExitApp = exitApp;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_commom);
        setCanceledOnTouchOutside(false);
        initView();
        initEvent();
    }

    private void initEvent() {
        submitTxt.setOnClickListener(this);
        cancelTxt.setOnClickListener(this);
        singleTxt.setOnClickListener(this);
    }

    private void initView() {
        contentTxt = (TextView) findViewById(R.id.content);
        titleTxt = (TextView) findViewById(R.id.title);
        submitTxt = (TextView) findViewById(R.id.submit);
        cancelTxt = (TextView) findViewById(R.id.cancel);
        singleTxt = (TextView) findViewById(R.id.tv_sure_single);
        mDoubleBtns = (LinearLayout) findViewById(R.id.ll_buttons);

        contentTxt.setText(content);

        if (isExitApp){
            submitTxt.setTextColor(mContext.getResources().getColor(R.color.font_common_2));
            cancelTxt.setTextColor(mContext.getResources().getColor(R.color.overall_style));
        }else {
            submitTxt.setTextColor(mContext.getResources().getColor(R.color.overall_style));
            cancelTxt.setTextColor(mContext.getResources().getColor(R.color.font_common_2));
        }

        if (isSingle) {
            mDoubleBtns.setVisibility(View.GONE);
            singleTxt.setVisibility(View.VISIBLE);
            if (!TextUtils.isEmpty(singlePositiveName)) {
                singleTxt.setText(singlePositiveName);
            }

        } else {
            mDoubleBtns.setVisibility(View.VISIBLE);
            singleTxt.setVisibility(View.GONE);
            if (!TextUtils.isEmpty(positiveName)) {
                submitTxt.setText(positiveName);
            }

            if (!TextUtils.isEmpty(negativeName)) {
                cancelTxt.setText(negativeName);
            }
        }


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.cancel:
                if (listener != null) {
                    listener.onClick(this, false);
                }
                this.dismiss();
                break;

            case R.id.submit:
                if (listener != null) {
                    listener.onClick(this, true);
                }
                break;

            case R.id.tv_sure_single:
                if (listener != null) {
                    listener.onClick(this, true);
                }
                break;

            default:
                break;
        }
    }

    public interface OnClickListener {
        void onClick(Dialog dialog, boolean confirm);
    }
}
