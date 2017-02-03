package com.weiteng.weitengapp.ui.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.weiteng.weitengapp.R;
import com.weiteng.weitengapp.base.BaseCompatActivity;
import com.weiteng.weitengapp.util.DateTimePickDialogUtil;
import com.weiteng.weitengapp.util.LogUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * Created by Administrator on 2017/1/10.
 */
public class DataSelectActivity extends BaseCompatActivity {


    private String initStartDateTime = ""; // 初始化开始时间
    private String initEndDateTime = ""; // 初始化结束时间
    private EditText mStartDateTime;
    private EditText mEndDateTime;
    private Button mButton;
    private String mCategory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_datasearch);
        initView();
        initData();
    }

    @Override
    protected void initView() {
        mCategory = getIntent().getStringExtra("category");
        mStartDateTime = (EditText) findViewById(R.id.inputDate);
        mEndDateTime = (EditText) findViewById(R.id.inputDate2);
        mButton = (Button) findViewById(R.id.bt_yes);
        mStartDateTime.setText(initStartDateTime);
        mEndDateTime.setText(initEndDateTime);
    }

    @Override
    protected void initData() {
        mStartDateTime.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                DateTimePickDialogUtil dateTimePicKDialog = new DateTimePickDialogUtil(
                        DataSelectActivity.this, initEndDateTime);
                dateTimePicKDialog.dateTimePicKDialog(mStartDateTime);

            }
        });

        mEndDateTime.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                DateTimePickDialogUtil dateTimePicKDialog = new DateTimePickDialogUtil(
                        DataSelectActivity.this, initEndDateTime);
                dateTimePicKDialog.dateTimePicKDialog(mEndDateTime);
            }
        });
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LogUtils.i("点击", "starttime");

                String s = mStartDateTime.getText().toString().trim();
                String s1 = mEndDateTime.getText().toString().trim();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日 HH:mm");
                try {
                    Date start = sdf.parse(s);
                    Date end = sdf.parse(s1);
                    if (start.getTime()>end.getTime()){
                        Toast.makeText(mApp, "输入时间不正确", Toast.LENGTH_SHORT).show();
                    }else {
                        RecordActivity.launch(DataSelectActivity.this, mCategory, s, s1);
                        finish();

                    }

                } catch (ParseException e) {
                    e.printStackTrace();
                }


            }
        });
    }
}
