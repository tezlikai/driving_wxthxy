package com.wxthxy.driving.view.login;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.wxthxy.driving.R;
import com.wxthxy.driving.mvp.MVPBaseActivity;
import com.wxthxy.driving.util.StringUtil;
import com.wxthxy.driving.view.main.MainActivity;
import com.wxthxy.driving.widget.ClearableEditText;

/**
 * 登录界面
 *
 * @author lk
 */

public class LoginActivity extends MVPBaseActivity<LoginContract.View, LoginPresenter> implements LoginContract.View, View.OnClickListener {

    private TextView mLogin;
    private ClearableEditText mAccount;
    private ClearableEditText mPassword;
    private ProgressBar mLoading;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        //去除标题栏和状态栏
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();
    }


    public void initView() {
        mAccount = findViewById(R.id.et_account);
        mPassword = findViewById(R.id.et_password);

        mLogin = findViewById(R.id.bt_login);
        mLoading = findViewById(R.id.iv_login_loading);

        initListener();
    }

    private void initListener() {
        mAccount.setOnClickListener(this);
        mPassword.setOnClickListener(this);
        mLogin.setOnClickListener(this);
        mLogin.setEnabled(false);

        mAccount.addTextChangedListener(acInputWatch);
        mPassword.addTextChangedListener(pwInputWatch);

    }

    private TextWatcher acInputWatch = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
        }

        @Override
        public void afterTextChanged(Editable s) {
            if (s.toString().length() != 0 && !TextUtils.isEmpty(mPassword.getText())) {
                mLogin.setBackgroundResource(R.mipmap.bt_login_press);
                mLogin.setEnabled(true);
            }
        }
    };

    private TextWatcher pwInputWatch = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
        }

        @Override
        public void afterTextChanged(Editable s) {
            if (s.toString().length() != 0 && !TextUtils.isEmpty(mAccount.getText())) {
                mLogin.setBackgroundResource(R.mipmap.bt_login_press);
                mLogin.setEnabled(true);
            }
        }
    };

    public void loginSuccess() {
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();

            if (isShouldHideKeyboard(v, ev)) {
                hideKeyboard(v.getWindowToken());
            }
        }
        return super.dispatchTouchEvent(ev);
    }

    private boolean isShouldHideKeyboard(View v, MotionEvent event) {
        if (v != null && (v instanceof EditText)) {
            int[] l = {0, 0};

            v.getLocationInWindow(l);

            int left = l[0];
            int top = l[1];
            int bottom = top + v.getHeight();
            int right = left + v.getWidth();

            if (event.getX() > left && event.getX() < right && event.getY() > top && event.getY() < bottom) {
                // 点击EditText的事件，忽略它。
                return false;
            } else {
                return true;
            }
        }

        // 如果焦点不是EditText则忽略，这个发生在视图刚绘制完，第一个焦点不在EditText上，和用户用轨迹球选择其他的焦点
        return false;
    }

    /**
     * 隐藏输入法
     *
     * @param token
     */
    private void hideKeyboard(IBinder token) {
        if (token != null) {
            InputMethodManager im = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            im.hideSoftInputFromWindow(token, InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {

            case R.id.bt_login:
                mLoading.setVisibility(View.VISIBLE);
                checkUser();
                break;

            default:
                break;
        }
    }

    private void checkUser() {

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                /**
                 *要执行的操作
                 */
                if (StringUtil.isEmpty(mAccount.getText())) {

                    Toast.makeText(LoginActivity.this, "用户名不能为空", Toast.LENGTH_LONG).show();
                    mLoading.setVisibility(View.GONE);
                    return;
                }

                if (StringUtil.isEmpty(mPassword.getText())) {
                    Toast.makeText(LoginActivity.this, "密码不能为空", Toast.LENGTH_LONG).show();
                    mLoading.setVisibility(View.GONE);
                    return;
                }

                if ("admin".equals(mAccount.getText().toString())) {
                } else {
                    Toast.makeText(LoginActivity.this, "用户名不存在，请重新输入", Toast.LENGTH_LONG).show();
                    mLoading.setVisibility(View.GONE);
                    return;
                }

                if ("123456".equals(mPassword.getText().toString())) {
                } else {
                    Toast.makeText(LoginActivity.this, "密码不正确，请重新输入", Toast.LENGTH_LONG).show();
                    mLoading.setVisibility(View.GONE);
                    return;
                }
                loginSuccess();
            }
        }, 2000);//3秒后执行Runnable中的run方法


    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (doubleBackToExit) {
                exitDialog();
                return true;
            }
            Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
            this.doubleBackToExit = true;
            new Handler().postDelayed(new Runnable() {

                @Override
                public void run() {
                    doubleBackToExit = false;

                }
            }, 2000);

            return false;
        }

        return super.onKeyUp(keyCode, event);
    }
}
