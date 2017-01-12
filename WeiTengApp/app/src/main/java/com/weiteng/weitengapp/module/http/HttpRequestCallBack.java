package com.weiteng.weitengapp.module.http;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * Created by Admin on 2016/11/19.
 */

public interface HttpRequestCallBack extends Callback {
    @Override
    void onResponse(Call call, Response response);

    @Override
    void onFailure(Call call, IOException e);
}
