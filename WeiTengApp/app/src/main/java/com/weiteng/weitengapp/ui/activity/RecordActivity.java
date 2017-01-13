package com.weiteng.weitengapp.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.reflect.TypeToken;
import com.weiteng.weitengapp.R;
import com.weiteng.weitengapp.base.BaseCompatActivity;
import com.weiteng.weitengapp.bean.SpendingInfo;
import com.weiteng.weitengapp.module.http.Http;
import com.weiteng.weitengapp.module.http.RequestCallBack;
import com.weiteng.weitengapp.ui.adapter.RecordItemAdapter;
import com.weiteng.weitengapp.ui.view.XListView;
import com.weiteng.weitengapp.util.JsonUtils;
import com.weiteng.weitengapp.util.LogUtils;
import com.weiteng.weitengapp.util.ResourceUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import okhttp3.Call;
import okhttp3.FormBody;
import okhttp3.Response;

/**
 * Created by zcf on 2017/1/8.
 */

public class RecordActivity extends BaseCompatActivity implements XListView.IXListViewListener {

    @InjectView(R.id.search)
    TextView mSearch;
    @InjectView(R.id.list_view)
    XListView mListView;
    @InjectView(R.id.ll_record_norecord)
    LinearLayout mLlRecord;
    @InjectView(R.id.ll_record_nonet)
    LinearLayout mLlRecordNonet;
    @InjectView(R.id.tb_re_ac)
    Toolbar mTbReAc;
    @InjectView(R.id.toolbar_title)
    TextView mToolbarTitle;
    private ArrayList<SpendingInfo> items = new ArrayList<SpendingInfo>();
    private RecordItemAdapter mAdapter;
    private String url;
    public static int RECHARGE = 501;
    public static int SPENDING = 502;
    public static int CASH = 503;
    public static int CHANGE = 504;
    private int NETFAILED = 0;
    private int NETSUCCESS = 1;
    private int NORECORD = 2;
    private String mCategory = "";
    private int mPage;
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


    public static void launch(Context context, String category) {
        Intent intent = new Intent();
        intent.setClass(context, RecordActivity.class);
        intent.putExtra("category", category);
        context.startActivity(intent);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record);
        mCategory = getIntent().getStringExtra("category");
        //String category = "5";
        mPage = 1;

        LogUtils.e("类", "" + mCategory);
        ButterKnife.inject(this);
        initView();
        initData();
    }

    @Override
    protected void initView() {

        switch (mCategory) {
            case "501":
                mToolbarTitle.setText(ResourceUtils.getString(mApp, R.string.recharge));
                break;
            case "502":
                mToolbarTitle.setText(ResourceUtils.getString(mApp, R.string.spending));
                break;
            case "503":
                mToolbarTitle.setText(ResourceUtils.getString(mApp, R.string.cash));
                break;
            case "504":
                mToolbarTitle.setText(ResourceUtils.getString(mApp, R.string.change));
                break;

        }

    }

    private void geneItems() {
        getResponse();


    }

    private void getResponse() {
        FormBody.Builder builder = new FormBody.Builder();
        builder.add("exchangeType", mCategory).add("page", mPage + "")
                .add("pagesize", "20");
        url = ResourceUtils.getString(mApp,R.string.url);
        LogUtils.e("url地址", url);
        Http.post(url + "Api_user/userExchangeQuery", null, builder.build(), new RequestCallBack() {
            @Override
            public void onResponse(Call call, Response response) {
                String json = response.toString();
                mPage += 1;
                LogUtils.e("json数据",json);
                pearJson(json);

            }

            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
                Message message = Message.obtain();
                message.what = NETFAILED;
                mHandler.sendMessage(message);

            }
        });
    }

    private void pearJson(String json) {
        if (TextUtils.isEmpty(json)) {
            Message message = Message.obtain();
            message.what = NORECORD;
            mHandler.sendMessage(message);
        }
        try {

            List<SpendingInfo> list = (List<SpendingInfo>) JsonUtils.parseJsonToList(json, new TypeToken<List<SpendingInfo>>() {
            }.getType());
            items.addAll(list);
            if (items != null) {
                Message message = Message.obtain();
                message.what = NETSUCCESS;
                mHandler.sendMessage(message);
            }
        } catch (Exception e) {
            Message message = Message.obtain();
            message.what = NETFAILED;
            mHandler.sendMessage(message);
        }


    }


    @Override
    protected void initData() {

        mSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RecordActivity.this, DataSelectActivity.class);
                startActivity(intent);
            }
        });
        mAdapter = new RecordItemAdapter(items, this);
        mListView.setPullRefreshEnable(true);
        mListView.setPullLoadEnable(true);
        mListView.setAutoLoadEnable(true);
        mListView.setXListViewListener(this);
        mListView.setAdapter(mAdapter);

    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);

        if (hasFocus) {
            mListView.autoRefresh();
        }
    }

    @Override
    public void onRefresh() {
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                items.clear();
                geneItems();
                mAdapter = new RecordItemAdapter(items, RecordActivity.this);
                mListView.setAdapter(mAdapter);
                onLoad();
            }
        }, 2500);
    }

    @Override
    public void onLoadMore() {
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                geneItems();
                mAdapter.notifyDataSetChanged();
                onLoad();
            }
        }, 2500);
    }

    private void onLoad() {
        mListView.stopRefresh();
        mListView.stopLoadMore();
        mListView.setRefreshTime(getTime());
    }

    private String getTime() {
        //return new SimpleDateFormat("MM-dd HH:mm", Locale.CHINA).format(new Date());
        return null;
    }
}
