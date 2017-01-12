package com.weiteng.weitengapp.module.http;

import com.weiteng.weitengapp.util.ThreadUtils;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by Admin on 2016/11/19.
 */

public class HttpRequestCallBackProxy implements HttpRequestCallBack {
    private HttpRequestCallBack mCallBack;

    public HttpRequestCallBackProxy(HttpRequestCallBack callBack) {
        mCallBack = callBack;
    }

    @Override
    public void onFailure(final Call call, final IOException e) {
        ThreadUtils.runOnForeground(new Runnable() {
            @Override
            public void run() {
                mCallBack.onFailure(call, e);
            }
        });
    }

    @Override
    public void onResponse(final Call call, final Response response) {
        ThreadUtils.runOnForeground(new Runnable() {
            @Override
            public void run() {
                mCallBack.onResponse(call, response);
            }
        });
    }
}
