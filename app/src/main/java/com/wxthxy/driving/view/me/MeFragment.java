package com.wxthxy.driving.view.me;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.wxthxy.driving.R;
import com.wxthxy.driving.mvp.MVPBaseFragment;
import com.wxthxy.driving.view.me.aboutHibeauty.AboutHibeautyActivity;
import com.wxthxy.driving.view.me.contactUs.ContactUsActivity;
import com.wxthxy.driving.view.me.helpCenter.HelpCenterActivity;
import com.wxthxy.driving.view.me.setting.SettingActivity;

/**
 * Created by Administrator on 2018/4/15.
 */

public class MeFragment extends MVPBaseFragment<MeContract.View, MePresenter> implements MeContract.View, OnClickListener {

    private View mView;
    private RelativeLayout mRlHelpCenter;
    private RelativeLayout mRlContactus;
    private RelativeLayout mRlAboutHibeauty;
    private RelativeLayout mRlSetting;

    @Override
    protected void initPrepare() {
        mRlHelpCenter = mView.findViewById(R.id.rl_help_center);
        mRlContactus = mView.findViewById(R.id.rl_contact_us);
        mRlAboutHibeauty = mView.findViewById(R.id.rl_about_hibeauty);
        mRlSetting = mView.findViewById(R.id.rl_setting);
        initListener();
    }

    private void initListener() {
        mRlHelpCenter.setOnClickListener(this);
        mRlContactus.setOnClickListener(this);
        mRlAboutHibeauty.setOnClickListener(this);
        mRlSetting.setOnClickListener(this);
    }

    @Override
    protected void onInvisible() {

    }

    @Override
    protected void initData() {

    }

    @Override
    protected View initView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_me, container, false);
        return mView;
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {

            case R.id.rl_help_center:

                toJumpPage(HelpCenterActivity.class);
                break;
            case R.id.rl_contact_us:
                toJumpPage(ContactUsActivity.class);
                break;
            case R.id.rl_about_hibeauty:
                toJumpPage(AboutHibeautyActivity.class);
                break;
            case R.id.rl_setting:
                toJumpPage(SettingActivity.class);
                break;
            default:
                break;

        }

    }

    private void toJumpPage(Class Class) {
        Intent intent = new Intent(getActivity(), Class);
        getActivity().startActivity(intent);
    }
}
