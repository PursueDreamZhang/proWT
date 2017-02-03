package com.weiteng.weitengapp.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.weiteng.weitengapp.R;
import com.weiteng.weitengapp.app.ApiManager;
import com.weiteng.weitengapp.app.Application;
import com.weiteng.weitengapp.app.Configuration;
import com.weiteng.weitengapp.base.BaseCompatActivity;
import com.weiteng.weitengapp.bean.resp.CommonResp;
import com.weiteng.weitengapp.util.CryptoUtils;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class LoginActivity extends BaseCompatActivity {
    private EditText et_username;
    private EditText et_password;
    private EditText et_verify_code;
    private ImageView iv_verify_code;
    private Button bt_login;
    private CheckBox cb_rem_user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();
        initData();
    }

    @Override
    protected void initView() {
        et_username = (EditText) findViewById(R.id.aty_login_et_username);
        et_password = (EditText) findViewById(R.id.aty_login_et_password);
        et_verify_code = (EditText) findViewById(R.id.aty_login_et_verify_code);
        iv_verify_code = (ImageView) findViewById(R.id.aty_login_iv_verify_code);
        cb_rem_user = (CheckBox) findViewById(R.id.aty_login_cb_rem_user);
        bt_login = (Button) findViewById(R.id.aty_login_bt_login);

        iv_verify_code.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Glide.clear(iv_verify_code);
                getVerifyCode();
            }
        });

        bt_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login();
            }
        });
    }

    @Override
    protected void initData() {
        boolean rem_user = mApp.getPreference().getBoolean("rem_user", false);
        if (rem_user) {
            String username = mApp.getPreference().getString("username", "");
            String password = mApp.getPreference().getString("password", "");
            if (!"".equals(username)) {
                username = new String(CryptoUtils.AESDecrypt(CryptoUtils.Hex2Byte(username), Configuration.EncryptKey, "Crypto"));
            }
            if (!"".equals(password)) {
                password = new String(CryptoUtils.AESDecrypt(CryptoUtils.Hex2Byte(password), Configuration.EncryptKey, "Crypto"));
            }

            et_username.setText(username);
            et_password.setText(password);
        }
        cb_rem_user.setChecked(rem_user);

        getVerifyCode();
    }

    private void getVerifyCode() {
        ApiManager.getApiManager().getVerifyCode(System.currentTimeMillis(), new ApiManager.RequestCallBack<File>() {
            @Override
            public void onSuccess(File data) {
                Glide.with(LoginActivity.this).load(data).error(R.mipmap.broken_image).placeholder(R.mipmap.loader)
                        .skipMemoryCache(true).diskCacheStrategy(DiskCacheStrategy.NONE).into(iv_verify_code);
            }

            @Override
            public void onSuccess(List<File> data) {
            }

            @Override
            public void onFailure(String error) {
                showToast("获取验证码失败！\n点击验证码重试");
            }
        });
    }

    private void login() {
        final String username = et_username.getText().toString();
        final String password = et_password.getText().toString();
        String verifyCode = et_verify_code.getText().toString();

        if (TextUtils.isEmpty(username)) {
            showToast("请输入帐号");
            return;
        }
        if (TextUtils.isEmpty(password)) {
            showToast("请输入密码");
            return;
        }
        if (TextUtils.isEmpty(verifyCode)) {
            showToast("请输入验证码");
            return;
        }

        int userType = 103;
        int loginType = 1;

        final SweetAlertDialog loginDlg = new SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE);
        loginDlg.setTitleText("正在登录");
        loginDlg.setCancelable(false);
        loginDlg.show();

        ApiManager.getApiManager().login(username, password, userType, verifyCode, loginType, new ApiManager.RequestCallBack<CommonResp>() {
            @Override
            public void onSuccess(CommonResp data) {
                if (data.info != null) {
                    saveUser(username, password);
                    ((Application) mApp).saveApiCookie();
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                    loginDlg.dismiss();
                    finish();
                } else {
                    loginDlg.changeAlertType(SweetAlertDialog.ERROR_TYPE);
                    loginDlg.setTitleText(data.errorinfo);
                    getVerifyCode();
                }
            }

            @Override
            public void onSuccess(List<CommonResp> data) {
            }

            @Override
            public void onFailure(String error) {
                loginDlg.changeAlertType(SweetAlertDialog.ERROR_TYPE);
                loginDlg.setTitleText("网络错误");
            }
        });
    }

    private void saveUser(String username, String password) {
        mApp.getPreference().edit().putBoolean("rem_user", cb_rem_user.isChecked()).commit();
        if (cb_rem_user.isChecked()) {
            try {
                mApp.getPreference().edit().putString("username", CryptoUtils.Byte2Hex(CryptoUtils.AESEncrypt(username.getBytes("utf-8"), Configuration.EncryptKey, "Crypto"))).commit();
                mApp.getPreference().edit().putString("password", CryptoUtils.Byte2Hex(CryptoUtils.AESEncrypt(password.getBytes("utf-8"), Configuration.EncryptKey, "Crypto"))).commit();
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
    }
}

