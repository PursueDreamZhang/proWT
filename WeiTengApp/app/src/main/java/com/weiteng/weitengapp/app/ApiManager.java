package com.weiteng.weitengapp.app;

import com.google.gson.reflect.TypeToken;
import com.weiteng.weitengapp.bean.ExchangeBean;
import com.weiteng.weitengapp.bean.MoneyBean;
import com.weiteng.weitengapp.bean.UserInfoBean;
import com.weiteng.weitengapp.bean.resp.LoginResp;
import com.weiteng.weitengapp.bean.resp.PreOrderResp;
import com.weiteng.weitengapp.interf.IServerAPI;
import com.weiteng.weitengapp.module.http.Http;
import com.weiteng.weitengapp.module.http.HttpRequestCallBack;
import com.weiteng.weitengapp.util.FileUtils;
import com.weiteng.weitengapp.util.JsonUtils;
import com.weiteng.weitengapp.util.ThreadUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import okhttp3.Call;
import okhttp3.FormBody;
import okhttp3.Headers;
import okhttp3.Response;

/**
 * Created by Admin on 2017/1/7.
 */

public class ApiManager implements IServerAPI {
    private static ApiManager mApi;

    private ApiManager() {
    }

    public static synchronized ApiManager getApiManager() {
        if (mApi == null) {
            mApi = new ApiManager();
        }

        return mApi;
    }

    @Override
    public void getVerifyCode(long time, final RequestCallBack<String> callBack) {
        Headers.Builder headersBuilder = new Headers.Builder();
        headersBuilder.add("Connection", "Keep-Alive");

        Http.get(VerifyCodeUrl, null, headersBuilder.build(), new HttpRequestCallBack() {
            @Override
            public void onResponse(Call call, final Response response) {
                ThreadUtils.runOnBackground(new Runnable() {
                    @Override
                    public void run() {
                        final File verifyCode = FileUtils.getFile(Configuration.DEFAULT_CACHE_PATH, "verify_code.png");
                        InputStream is = response.body().byteStream();
                        FileOutputStream fos = null;
                        try {
                            fos = new FileOutputStream(verifyCode);
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        }
                        byte[] buffer = new byte[1024];
                        int len = -1;
                        try {
                            while ((len = is.read(buffer)) != -1) {
                                fos.write(buffer, 0, len);
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        ThreadUtils.runOnForeground(new Runnable() {
                            @Override
                            public void run() {
                                callBack.onSuccess(verifyCode.getAbsolutePath());
                            }
                        });
                    }
                });
            }

            @Override
            public void onFailure(Call call, IOException e) {

            }
        });
    }

    @Override
    public void login(String username, String password, int userType, String code, int loginType, final RequestCallBack<LoginResp> callBack) {
        Headers.Builder headersBuilder = new Headers.Builder();
        headersBuilder.add("Connection", "Keep-Alive");

        FormBody.Builder formBuilder = new FormBody.Builder();
        formBuilder.add("username", username);
        formBuilder.add("password", password);
        formBuilder.add("userType", userType + "");
        formBuilder.add("code", code);
        formBuilder.add("loginType", loginType + "");

        Http.post(LoginUrl, null, formBuilder.build(), headersBuilder.build(), new HttpRequestCallBack() {
            @Override
            public void onResponse(Call call, Response response) {
                try {
                    callBack.onSuccess(JsonUtils.parseJsonToBean(response.body().string(), LoginResp.class));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call call, IOException e) {

            }
        });
    }

    @Override
    public void getUserInfo(final RequestCallBack<UserInfoBean> callBack) {
        Headers.Builder headersBuilder = new Headers.Builder();
        headersBuilder.add("Connection", "Keep-Alive");

        Http.get(GetUserInfoUrl, null, headersBuilder.build(), new HttpRequestCallBack() {
            @Override
            public void onResponse(Call call, Response response) {
                try {
                    callBack.onSuccess(JsonUtils.parseJsonToBean(response.body().string(), UserInfoBean.class));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call call, IOException e) {

            }
        });
    }

    @Override
    public void getExchange(int exchangeType, int page, int pagesize, long exchangeTime1, long exchangeTime2, final RequestCallBack<List<ExchangeBean>> callBack) {
        Headers.Builder headersBuilder = new Headers.Builder();
        headersBuilder.add("Connection", "Keep-Alive");
        headersBuilder.add("", "");

        Http.get(GetExchangeUrl, null, headersBuilder.build(), new HttpRequestCallBack() {
            @Override
            public void onResponse(Call call, Response response) {
                try {
                    callBack.onSuccess((List<ExchangeBean>) JsonUtils.parseJsonToList(response.body().string(), new TypeToken<List<ExchangeBean>>(){}.getType()));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call call, IOException e) {

            }
        });
    }

    @Override
    public void getMoney(final RequestCallBack<List<MoneyBean>> callBack) {
        Headers.Builder headersBuilder = new Headers.Builder();
        headersBuilder.add("Connection", "Keep-Alive");

        Http.get(GetMoneyUrl, null, headersBuilder.build(), new HttpRequestCallBack() {
            @Override
            public void onResponse(Call call, Response response) {
                try {
                    callBack.onSuccess((List<MoneyBean>) JsonUtils.parseJsonToList(response.body().string(), new TypeToken<List<MoneyBean>>(){}.getType()));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call call, IOException e) {

            }
        });
    }

    @Override
    public void preorder(long orderTime, int moneyID, int shopID, final RequestCallBack<PreOrderResp> callBack) {
        Headers.Builder headersBuilder = new Headers.Builder();
        headersBuilder.add("Connection", "Keep-Alive");

        Http.get(PreOrderUrl, null, headersBuilder.build(), new HttpRequestCallBack() {
            @Override
            public void onResponse(Call call, Response response) {
                try {
                    callBack.onSuccess(JsonUtils.parseJsonToBean(response.body().string(), PreOrderResp.class));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call call, IOException e) {

            }
        });
    }

    public interface RequestCallBack<T> {
        public void onSuccess(T data);

        public void onSuccess(List<T> data);

        public void onFailure(String error);
    }
}
