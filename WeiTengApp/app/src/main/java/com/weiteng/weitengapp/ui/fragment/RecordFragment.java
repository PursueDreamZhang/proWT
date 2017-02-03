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

import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * Created by zcf on 2017/1/7.
 */

public class RecordFragment extends Fragment implements View.OnClickListener{
    private Button mBtSpending;
    private Button mBtRecharge;
    private Button mBtCash;
    private Button mBtChange;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = View.inflate(getContext(), R.layout.fragment_record, null);
        initView(root);

        return root;
    }

    private void initView(View view) {
        mBtSpending = (Button) view.findViewById(R.id.bt_spending);
        mBtRecharge = (Button) view.findViewById(R.id.bt_recharge);
        mBtCash = (Button) view.findViewById(R.id.bt_cash);
        mBtChange = (Button) view.findViewById(R.id.bt_change);
        mBtSpending.setOnClickListener(this);
        mBtRecharge.setOnClickListener(this);
        mBtCash.setOnClickListener(this);
        mBtChange.setOnClickListener(this);

    }


    @Override
    protected void initData() {
    }

    @Override
    protected void requestData() {
    }

    @Override
    public void onClick(View v) {
        Date date = new Date();
        Date d2 = new Date(0,0,0);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日 HH:mm");
        String end = sdf.format(date);
        String start = sdf.format(d2);
        switch (v.getId()){



            case R.id.bt_spending:
                RecordActivity.launch(getContext(),""+RecordActivity.SPENDING,start,end);

                break;
            case R.id.bt_recharge:
                RecordActivity.launch(getContext(),""+RecordActivity.RECHARGE,start,end);

                break;
            case R.id.bt_cash:
                RecordActivity.launch(getContext(),""+RecordActivity.CASH,start,end);

                break;
            case R.id.bt_change:
                RecordActivity.launch(getContext(),""+RecordActivity.CHANGE,start,end);
                /*Intent intent = new Intent(getActivity(),Tes.class);
                startActivity(intent);*/

                break;
        }
    }
}
