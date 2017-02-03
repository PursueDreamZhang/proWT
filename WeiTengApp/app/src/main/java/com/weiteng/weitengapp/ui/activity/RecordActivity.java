package com.weiteng.weitengapp.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.weiteng.weitengapp.R;
import com.weiteng.weitengapp.app.ApiManager;
import com.weiteng.weitengapp.base.BaseCompatActivity;
import com.weiteng.weitengapp.bean.ExchangeBean;
import com.weiteng.weitengapp.ui.adapter.RecordItemAdapter;
import com.weiteng.weitengapp.util.LogUtils;
import com.weiteng.weitengapp.util.ResourceUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static android.R.id.message;


/**
 * Created by zcf on 2017/1/8.
 */

public class RecordActivity extends BaseCompatActivity {

    private LinearLayout mLlRecord;
    private LinearLayout mLlRecordNonet;
    private Toolbar mTbReAc;
    private TextView mToolbarTitle;
    private PullToRefreshListView mListView;
    private ArrayList<ExchangeBean> items = new ArrayList<ExchangeBean>();
    private RecordItemAdapter mAdapter;
    private String url;

    private TextView mSearch;


    public static int RECHARGE = 501;
    public static int SPENDING = 502;
    public static int CASH = 503;
    public static int CHANGE = 504;
    private int NETFAILED = 0;
    private int NETSUCCESS = 1;
    private int NORECORD = 2;
    private String mCategory = "";
    private int mPage;
    private long mStartTime;
    private long mEndTime;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    mListView.setVisibility(View.GONE);
                    mLlRecordNonet.setVisibility(View.VISIBLE);
                    break;
                case 1:
                    mListView.setVisibility(View.VISIBLE);
                    mLlRecord.setVisibility(View.GONE);
                    break;
                case 2:
                    mListView.setVisibility(View.GONE);
                    mLlRecord.setVisibility(View.VISIBLE);
                    break;
            }
        }
    };


    public static void launch(Context context, String category, String startTime, String endTime) {
        Intent intent = new Intent();
        intent.setClass(context, RecordActivity.class);
        intent.putExtra("category", category);
        intent.putExtra("startTime", startTime);
        intent.putExtra("endTime", endTime);

        context.startActivity(intent);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record);


        initView();
        initData();
    }

    @Override
    protected void initView() {
        mCategory = getIntent().getStringExtra("category");
        mPage = 1;
        String endTime = getIntent().getStringExtra("endTime");
        String startTime = getIntent().getStringExtra("startTime");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日 HH:mm");
        try {
            Date start = sdf.parse(startTime);
            Date end = sdf.parse(endTime);
            mStartTime = start.getTime();
            mEndTime = end.getTime();

        } catch (ParseException e) {
            e.printStackTrace();
        }


        mSearch = (TextView) findViewById(R.id.search);
        mLlRecord = (LinearLayout) findViewById(R.id.ll_record_norecord);
        mLlRecordNonet = (LinearLayout) findViewById(R.id.ll_record_nonet);
        mTbReAc = (Toolbar) findViewById(R.id.tb_re_ac);
        mToolbarTitle = (TextView) findViewById(R.id.toolbar_title);
        mListView = (PullToRefreshListView) findViewById(R.id.list_view);


        switch (mCategory) {
            case "501":
                mToolbarTitle.setText(ResourceUtils.getString(R.string.recharge));
                break;
            case "502":
                mToolbarTitle.setText(ResourceUtils.getString(R.string.spending));
                break;
            case "503":
                mToolbarTitle.setText(ResourceUtils.getString(R.string.cash));
                break;
            case "504":
                mToolbarTitle.setText(ResourceUtils.getString(R.string.change));
                break;
        }
    }

    private void geneItems() {
        getResponse();
    }

    private void getResponse() {


        //ApiManager.getApiManager().getExchange(Integer.parseInt(mCategory), mPage, 2, mStartTime,
        ApiManager.getApiManager().getExchange(501, 0, 2, 0,
                mEndTime,
                new ApiManager.RequestCallBack<ExchangeBean>() {
                    @Override
                    public void onSuccess(ExchangeBean data) {
                    }

                    @Override
                    public void onSuccess(List<ExchangeBean> data) {

                        LogUtils.e("数据", data.size() + "");
                        items.addAll(data);
                        mAdapter.notifyDataSetChanged();
                        if (data.size()==0){
                            Message message = Message.obtain();
                            message.what = NORECORD;
                            mHandler.sendMessage(message);
                        }else {
                            Message message = Message.obtain();
                            message.what = NETSUCCESS;
                            mHandler.sendMessage(message);
                        }

                    }

                    @Override
                    public void onFailure(String error) {
                        LogUtils.e("失败", error);
                        Message message = Message.obtain();
                        message.what = NETFAILED;
                        mHandler.sendMessage(message);
                    }
                });

    }


    @Override
    protected void initData() {

        mSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RecordActivity.this, DataSelectActivity.class);
                intent.putExtra("category", mCategory);
                startActivity(intent);
            }
        });
        geneItems();
        mAdapter = new RecordItemAdapter(items, this, mCategory);
        mListView.setMode(PullToRefreshBase.Mode.BOTH);
        mListView.setAdapter(mAdapter);
        LogUtils.e("上啦", items.size() + "");

        mListView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        items.clear();
                        geneItems();
                        LogUtils.e("上啦", "fsd");

                        mAdapter = new RecordItemAdapter(items, RecordActivity.this, mCategory);
                        mListView.setAdapter(mAdapter);
                        mListView.onRefreshComplete();
                    }
                }, 2500);
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        geneItems();
                        mAdapter.notifyDataSetChanged();
                        mListView.onRefreshComplete();
                    }
                }, 2500);
            }
        });

    }


}
