package com.weiteng.weitengapp.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.weiteng.weitengapp.R;
import com.weiteng.weitengapp.ui.activity.RecordActivity;
import com.weiteng.weitengapp.ui.view.Fragment;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by zcf on 2017/1/7.
 */

public class RecordFragment extends Fragment {
    @InjectView(R.id.bt_spending)
    Button mBtSpending;
    @InjectView(R.id.bt_recharge)
    Button mBtRecharge;
    @InjectView(R.id.bt_cash)
    Button mBtCash;
    @InjectView(R.id.bt_change)
    Button mBtChange;

    @Override
    protected void requestData() {

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_record, null);

        ButterKnife.inject(this, view);
        return view;
    }

    @Override
    protected void initData() {
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }


    @OnClick({R.id.bt_spending, R.id.bt_recharge, R.id.bt_cash, R.id.bt_change})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bt_spending:
                RecordActivity.launch(getContext(),""+RecordActivity.SPENDING);

                break;
            case R.id.bt_recharge:
                RecordActivity.launch(getContext(),""+RecordActivity.RECHARGE);

                break;
            case R.id.bt_cash:
                RecordActivity.launch(getContext(),""+RecordActivity.CASH);

                break;
            case R.id.bt_change:
                RecordActivity.launch(getContext(),""+RecordActivity.CHANGE);

                break;
        }
    }
}
