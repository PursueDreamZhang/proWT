package com.weiteng.weitengapp.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.weiteng.weitengapp.R;
import com.weiteng.weitengapp.app.ApiManager;
import com.weiteng.weitengapp.bean.UserInfoBean;
import com.weiteng.weitengapp.ui.activity.LockActivity;
import com.weiteng.weitengapp.ui.activity.PreorderActivity;
import com.weiteng.weitengapp.ui.activity.ProfileActivity;
import com.weiteng.weitengapp.ui.activity.QueryActivity;
import com.weiteng.weitengapp.ui.activity.SettingActivity;
import com.weiteng.weitengapp.ui.activity.TransActivity;
import com.weiteng.weitengapp.ui.view.Fragment;

import java.text.DecimalFormat;
import java.util.List;

/**
 * Created by Admin on 2017/1/7.
 */

public class PersonalFragment extends Fragment {
    private TextView tv_nickname;
    private TextView tv_moblie;
    private TextView tv_balance;
    private TextView tv_dayrate;
    private Button bt_trans;
    private Button bt_preorder;
    private LinearLayout ll_profile;
    private LinearLayout ll_query;
    private LinearLayout ll_lock;
    private LinearLayout ll_setting;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = View.inflate(getContext(), R.layout.fragment_personal, null);

        tv_nickname = (TextView) root.findViewById(R.id.fgmt_personal_tv_nickname);
        tv_moblie = (TextView) root.findViewById(R.id.fgmt_personal_tv_mobile);
        tv_balance = (TextView) root.findViewById(R.id.fgmt_personal_tv_balance);
        tv_dayrate = (TextView) root.findViewById(R.id.fgmt_personal_tv_dayrate);
        bt_trans = (Button) root.findViewById(R.id.fgmt_personal_bt_trans);
        bt_preorder = (Button) root.findViewById(R.id.fgmt_personal_bt_preorder);
        ll_profile = (LinearLayout) root.findViewById(R.id.fgmt_personal_ll_profile);
        ll_query = (LinearLayout) root.findViewById(R.id.fgmt_personal_ll_query);
        ll_lock = (LinearLayout) root.findViewById(R.id.fgmt_personal_ll_lock);
        ll_setting = (LinearLayout) root.findViewById(R.id.fgmt_personal_ll_setting);

        bt_trans.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getContext().startActivity(new Intent(getContext(), TransActivity.class));
            }
        });
        bt_preorder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getContext().startActivity(new Intent(getContext(), PreorderActivity.class));
            }
        });
        ll_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getContext().startActivity(new Intent(getContext(), ProfileActivity.class));
            }
        });
        ll_query.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getContext().startActivity(new Intent(getContext(), QueryActivity.class));
            }
        });
        ll_lock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getContext().startActivity(new Intent(getContext(), LockActivity.class));
            }
        });
        ll_setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getContext().startActivity(new Intent(getContext(), SettingActivity.class));
            }
        });

        return root;
    }

    @Override
    protected void requestData() {
        ApiManager.getApiManager().getUserInfo(new ApiManager.RequestCallBack<UserInfoBean>() {
            @Override
            public void onSuccess(UserInfoBean data) {
                DecimalFormat df = new DecimalFormat("#0.00");

                tv_nickname.setText(data.Cnickname);
                tv_moblie.setText(data.CuserMobile);
                tv_balance.setText(df.format(Double.parseDouble(data.Cbalance)));
                tv_dayrate.setText("日利率：" + df.format(Double.parseDouble(data.Cdaterate) * 100) + "%");
            }

            @Override
            public void onSuccess(List<UserInfoBean> data) {

            }

            @Override
            public void onFailure(String error) {

            }
        });
    }

    @Override
    protected void initData() {
        requestData();
    }
}
