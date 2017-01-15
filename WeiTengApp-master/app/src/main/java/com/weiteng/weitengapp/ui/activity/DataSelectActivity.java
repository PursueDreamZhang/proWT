package com.weiteng.weitengapp.ui.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.weiteng.weitengapp.R;
import com.weiteng.weitengapp.app.ApiManager;
import com.weiteng.weitengapp.base.BaseCompatActivity;
import com.weiteng.weitengapp.util.DateTimePickDialogUtil;
import com.weiteng.weitengapp.util.LogUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by Administrator on 2017/1/10.
 */
public class DataSelectActivity extends BaseCompatActivity {
    @InjectView(R.id.inputDate)
    EditText startDateTime;
    @InjectView(R.id.inputDate2)
    EditText endDateTime;
    @InjectView(R.id.bt_yes)
    Button mButton;

    private String initStartDateTime = ""; // 初始化开始时间
    private String initEndDateTime = ""; // 初始化结束时间
    private String mCategory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_datasearch);
        ButterKnife.inject(this);
        mCategory = getIntent().getStringExtra("category");
        initView();
        initData();
    }

    @Override
    protected void initView() {
        startDateTime.setText(initStartDateTime);
        endDateTime.setText(initEndDateTime);
    }

    @Override
    protected void initData() {
        startDateTime.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                DateTimePickDialogUtil dateTimePicKDialog = new DateTimePickDialogUtil(
                        DataSelectActivity.this, initEndDateTime);
                dateTimePicKDialog.dateTimePicKDialog(startDateTime);

            }
        });

        endDateTime.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                DateTimePickDialogUtil dateTimePicKDialog = new DateTimePickDialogUtil(
                        DataSelectActivity.this, initEndDateTime);
                dateTimePicKDialog.dateTimePicKDialog(endDateTime);
            }
        });
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LogUtils.i("点击", "starttime");

                String s = startDateTime.getText().toString().trim();
                String s1 = endDateTime.getText().toString().trim();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日 HH:mm");

                try {
                    Date date = sdf.parse(s);
                    Date date2 = sdf.parse(s1);
                    RecordActivity.launch(DataSelectActivity.this,mCategory,true);

                } catch (ParseException e) {
                    e.printStackTrace();
                }
                finish();
            }
        });
    }
}
