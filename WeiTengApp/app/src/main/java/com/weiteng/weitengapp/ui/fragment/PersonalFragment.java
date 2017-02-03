package com.weiteng.weitengapp.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshScrollView;
import com.weiteng.weitengapp.R;
import com.weiteng.weitengapp.app.ApiManager;
import com.weiteng.weitengapp.bean.EventMessage;
import com.weiteng.weitengapp.bean.UserBean;
import com.weiteng.weitengapp.interf.IEventCode;
import com.weiteng.weitengapp.ui.DialogUtils;
import com.weiteng.weitengapp.ui.activity.ProfileActivity;
import com.weiteng.weitengapp.ui.activity.SettingActivity;
import com.weiteng.weitengapp.ui.view.Fragment;

import org.greenrobot.eventbus.EventBus;

import java.text.DecimalFormat;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * Created by Admin on 2017/1/7.
 */

public class PersonalFragment extends Fragment {
    private PullToRefreshScrollView ptrsv;
    private TextView tv_nickname;
    private TextView tv_moblie;
    private TextView tv_balance;
    private TextView tv_dayrate;
    private Button bt_transfer;
    private Button bt_preorder;
    private LinearLayout ll_profile;
    private LinearLayout ll_query;
    private LinearLayout ll_lock;
    private LinearLayout ll_setting;

    private UserBean mUserInfo;

    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = View.inflate(getContext(), R.layout.fragment_personal, null);

        ptrsv = (PullToRefreshScrollView) root.findViewById(R.id.fgmt_personal_ptrsv);
        tv_nickname = (TextView) root.findViewById(R.id.fgmt_personal_tv_nickname);
        tv_moblie = (TextView) root.findViewById(R.id.fgmt_personal_tv_mobile);
        tv_balance = (TextView) root.findViewById(R.id.fgmt_personal_tv_balance);
        tv_dayrate = (TextView) root.findViewById(R.id.fgmt_personal_tv_dayrate);
        bt_transfer = (Button) root.findViewById(R.id.fgmt_personal_bt_transfer);
        bt_preorder = (Button) root.findViewById(R.id.fgmt_personal_bt_preorder);
        ll_profile = (LinearLayout) root.findViewById(R.id.fgmt_personal_ll_profile);
        ll_query = (LinearLayout) root.findViewById(R.id.fgmt_personal_ll_query);
        ll_lock = (LinearLayout) root.findViewById(R.id.fgmt_personal_ll_lock);
        ll_setting = (LinearLayout) root.findViewById(R.id.fgmt_personal_ll_setting);

        ptrsv.setMode(PullToRefreshBase.Mode.PULL_FROM_START);
        ptrsv.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ScrollView>() {
            @Override
            public void onRefresh(PullToRefreshBase<ScrollView> refreshView) {
                switch (refreshView.getMode()) {
                    case PULL_FROM_START:
                        requestData();
                        break;
                    case BOTH:
                        break;
                    case DISABLED:
                        break;
                    case MANUAL_REFRESH_ONLY:
                        break;
                    case PULL_FROM_END:
                        break;
                }
            }
        });
        bt_transfer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Integer.parseInt(mUserInfo.CinterestType) == 1001) {
                    DialogUtils.showTransferDialog(getContext());
                } else {
                    mApp.showToast("定期存储用户无法进行转账业务");
                }
            }
        });
        bt_preorder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Integer.parseInt(mUserInfo.CinterestType) == 1001) {
                    DialogUtils.showUnfixedPreorderDialog(getContext(), mUserInfo.Cbalance);
                }
                if (Integer.parseInt(mUserInfo.CinterestType) > 1001) {
                    DialogUtils.showFixedPreorderDialog(getContext());
                }
            }
        });
        ll_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), ProfileActivity.class);
                intent.putExtra("user_info", mUserInfo);
                getContext().startActivity(intent);
            }
        });
        ll_query.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EventBus.getDefault().post(new EventMessage<Object>(IEventCode.EVENT_QUERY_RECORD, null));
            }
        });
        ll_lock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SweetAlertDialog lockDlg = new SweetAlertDialog(getContext(), SweetAlertDialog.WARNING_TYPE);
                lockDlg.setTitleText("挂失锁定");
                lockDlg.setContentText("不支持从客户端锁定帐号\n请前往开户商铺办理挂失锁定业务");
                lockDlg.setCancelable(false);
                lockDlg.setConfirmText("确定").show();
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
        ApiManager.getApiManager().getUserInfo(new ApiManager.RequestCallBack<UserBean>() {
            @Override
            public void onSuccess(UserBean data) {
                mUserInfo = data;
                DecimalFormat df = new DecimalFormat("#0.00");
                tv_nickname.setText(data.Cnickname);
                tv_moblie.setText(data.CuserMobile);
                tv_balance.setText(df.format(Double.parseDouble(data.Cbalance)));
                tv_dayrate.setText("日利率：" + df.format(Double.parseDouble(data.Cdaterate) * 100) + "%");
                ptrsv.onRefreshComplete();
            }

            @Override
            public void onSuccess(List<UserBean> data) {
            }

            @Override
            public void onFailure(String error) {
                mApp.showToast("网络错误");
                ptrsv.onRefreshComplete();
            }
        });
    }

    @Override
    protected void initData() {
        requestData();
    }

}
