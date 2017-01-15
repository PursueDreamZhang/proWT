package com.weiteng.weitengapp.module.http;

import com.weiteng.weitengapp.util.ThreadUtils;

import java.util.concurrent.TimeUnit;

import okhttp3.FormBody;
import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * Created by Admin on 2016/11/19.
 */

public class Http {
    private static OkHttpClient mOkHttp;
    private static HttpConfig mConfig = new HttpConfig();

    static {
        mOkHttp = new OkHttpClient.Builder().connectTimeout(10, TimeUnit.SECONDS).readTimeout(10, TimeUnit.SECONDS).writeTimeout(10, TimeUnit.SECONDS).build();
    }

    public static OkHttpClient getClient() {
        return mOkHttp;
    }

    public static void setConfig(HttpConfig config) {
        mConfig = config;
    }

    public static void get(final String url, final HttpRequestParams params, final HttpRequestCallBack callBack) {
        ThreadUtils.runOnBackground(new Runnable() {
            @Override
            public void run() {
                StringBuilder stringBuilder = new StringBuilder(url);

                if (params != null) {
                    if (!url.endsWith("&")) {
                        stringBuilder.append("&");
                    }
                    stringBuilder.append(params.toString());
                }

                Request request = new Request.Builder().url(stringBuilder.toString()).get().build();
                mOkHttp.newCall(request).enqueue(callBack);
            }
        });
    }

    public static void get(final String url, final HttpRequestParams params, final Headers headers, final HttpRequestCallBack callBack) {
        ThreadUtils.runOnBackground(new Runnable() {
            @Override
            public void run() {
                StringBuilder stringBuilder = new StringBuilder(url);

                if (params != null) {
                    if (!url.endsWith("&")) {
                        stringBuilder.append("&");
                    }
                    stringBuilder.append(params.toString());
                }

                Request request = new Request.Builder().url(stringBuilder.toString()).get().headers(headers).build();
                mOkHttp.newCall(request).enqueue(callBack);
            }
        });
    }

    public static void post(final String url, final HttpRequestParams params, final FormBody form, final HttpRequestCallBack callBack) {
        ThreadUtils.runOnBackground(new Runnable() {
            @Override
            public void run() {
                StringBuilder stringBuilder = new StringBuilder(url);

                if (params != null) {
                    if (!url.endsWith("&")) {
                        stringBuilder.append("&");
                    }
                    stringBuilder.append(params.toString());
                }

                Request request = new Request.Builder().url(stringBuilder.toString()).post(form).build();
                mOkHttp.newCall(request).enqueue(callBack);
            }
        });
    }

    public static void post(final String url, final HttpRequestParams params, final FormBody form, final Headers headers, final HttpRequestCallBack callBack) {
        ThreadUtils.runOnBackground(new Runnable() {
            @Override
            public void run() {
                StringBuilder stringBuilder = new StringBuilder(url);

                if (params != null) {
                    if (!url.endsWith("&")) {
                        stringBuilder.append("&");
                    }
                    stringBuilder.append(params.toString());
                }

                Request request = new Request.Builder().url(stringBuilder.toString()).post(form).headers(headers).build();
                mOkHttp.newCall(request).enqueue(callBack);
            }
        });
    }

    public static void postJson(final String url, final HttpRequestParams params, final String json, final HttpRequestCallBack callBack) {
        ThreadUtils.runOnBackground(new Runnable() {
            @Override
            public void run() {
                StringBuilder stringBuilder = new StringBuilder(url);

                if (params != null) {
                    if (!url.endsWith("&")) {
                        stringBuilder.append("&");
                    }
                    stringBuilder.append(params.toString());
                }

                Request request = new Request.Builder().url(stringBuilder.toString()).post(RequestBody.create(MediaType.parse("application/json; charset=utf-8"), json)).build();
                mOkHttp.newCall(request).enqueue(callBack);
            }
        });
    }

    public static void postSSL(final String url, final HttpRequestParams params, final FormBody form, final HttpRequestCallBack callBack) {
//TODO
    }

    public static void postSSL(final String url, final HttpRequestParams params, final FormBody form, final Headers headers, final HttpRequestCallBack callBack) {
//TODO
    }

    public static void postJsonSSL(final String url, final HttpRequestParams params, final String json, final HttpRequestCallBack callBack) {
//TODO
    }
}
