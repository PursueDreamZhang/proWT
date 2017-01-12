package com.weiteng.weitengapp.module.crash;

import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.weiteng.weitengapp.R;
import com.weiteng.weitengapp.base.BaseActivity;


public class CrashReportActivity extends BaseActivity {
    private TextView tv_line;
    private TextView tv_type;
    private TextView tv_class;
    private TextView tv_method;
    private TextView tv_cause;
    private TextView tv_stacktrace;

    private CrashData mCrashData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crash_report);
        initView();
        initData();
    }

    @Override
    protected void initView() {
        tv_line = (TextView) findViewById(R.id.aty_crash_report_tv_line);
        tv_type = (TextView) findViewById(R.id.aty_crash_report_tv_type);
        tv_class = (TextView) findViewById(R.id.aty_crash_report_tv_class);
        tv_method = (TextView) findViewById(R.id.aty_crash_report_tv_method);
        tv_cause = (TextView) findViewById(R.id.aty_crash_report_tv_cause);
        tv_stacktrace = (TextView) findViewById(R.id.aty_crash_report_tv_stacktrace);
        Button bt_sendReport = (Button) findViewById(R.id.aty_crash_report_bt_sendReport);

        tv_stacktrace.setMovementMethod(ScrollingMovementMethod.getInstance());
        tv_stacktrace.setHorizontallyScrolling(true);
        tv_stacktrace.setFocusable(true);

        bt_sendReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CrashSender.send(mCrashData);
            }
        });
    }

    @Override
    protected void initData() {
        mCrashData = (CrashData) getIntent().getSerializableExtra("crash_data");
        tv_line.setText(mCrashData.getLine());
        tv_type.setText(mCrashData.getType());
        tv_class.setText(mCrashData.getClazz());
        tv_method.setText(mCrashData.getMethod());
        tv_cause.setText(mCrashData.getCause());
        tv_stacktrace.setText(mCrashData.getStackTrace());
    }
}
