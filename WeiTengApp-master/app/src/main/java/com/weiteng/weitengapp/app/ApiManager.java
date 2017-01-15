package com.weiteng.weitengapp.app;

import com.google.gson.reflect.TypeToken;
import com.weiteng.weitengapp.bean.ExchangeBean;
import com.weiteng.weitengapp.bean.MoneyBean;
import com.weiteng.weitengapp.bean.UserInfoBean;
import com.weiteng.weitengapp.bean.resp.GetUserInfoResp;
import com.weiteng.weitengapp.bean.resp.LoginResp;
import com.weiteng.weitengapp.bean.resp.PreorderResp;
import com.weiteng.weitengapp.interf.IServerAPI;
import com.weiteng.weitengapp.module.http.Http;
import com.weiteng.weitengapp.module.http.HttpCookie;
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
    private HttpCookie mHttpCookie;

    private ApiManager() {
    }

    public static synchronized ApiManager getApiManager() {
        if (mApi == null) {
            mApi = new ApiManager();
        }

        return mApi;
    }

    @Override
    public void getVerifyCode(long time, final RequestCallBack<File> callBack) {
        Headers.Builder headersBuilder = new Headers.Builder();
        headersBuilder.add("Connection", "keep-alive");

        Http.get(VerifyCodeUrl, null, headersBuilder.build(), new HttpRequestCallBack() {
            @Override
            public void onResponse(Call call, final Response response) {
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
                mHttpCookie = HttpCookie.parseString(response.header("Set-Cookie"));
                new RequestCallBackProxy(callBack).onSuccess(verifyCode);
            }

            @Override
            public void onFailure(Call call, IOException e) {
                new RequestCallBackProxy(callBack).onFailure(e.getMessage());
            }
        });
    }


    @Override
    public void login(String username, String password, int userType, String code, int loginType, final RequestCallBack<LoginResp> callBack) {
        Headers.Builder headersBuilder = new Headers.Builder();
        headersBuilder.add("Connection", "keep-alive");
        headersBuilder.add("Cookie", mHttpCookie.getKVString("ci_session"));

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
                    LoginResp loginResp = JsonUtils.parseJsonToBean(response.body().string(), LoginResp.class);
                    new RequestCallBackProxy(callBack).onSuccess(loginResp);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call call, IOException e) {
                new RequestCallBackProxy(callBack).onFailure(e.getMessage());
            }
        });
    }

    @Override
    public void getUserInfo(final RequestCallBack<UserInfoBean> callBack) {
        Headers.Builder headersBuilder = new Headers.Builder();
        headersBuilder.add("Connection", "keep-alive");
        headersBuilder.add("Cookie", mHttpCookie.getKVString("ci_session"));

        Http.get(GetUserInfoUrl, null, headersBuilder.build(), new HttpRequestCallBack() {
            @Override
            public void onResponse(Call call, Response response) {
                try {
                    GetUserInfoResp userInfoResp = JsonUtils.parseJsonToBean(response.body().string(), GetUserInfoResp.class);
                    new RequestCallBackProxy(callBack).onSuccess(userInfoResp.userinfo);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call call, IOException e) {
                new RequestCallBackProxy(callBack).onFailure(e.getMessage());
            }
        });
    }

    @Override
    public void getExchange(int exchangeType, int page, int pagesize, long exchangeTime1, long exchangeTime2, final RequestCallBack<List<ExchangeBean>> callBack) {
        Headers.Builder headersBuilder = new Headers.Builder();
        headersBuilder.add("Connection", "keep-alive");
        headersBuilder.add("Cookie", mHttpCookie.getKVString("ci_session"));

        Http.get(GetExchangeUrl, null, headersBuilder.build(), new HttpRequestCallBack() {
            @Override
            public void onResponse(Call call, Response response) {
                try {
                    List<ExchangeBean> exchanges = (List<ExchangeBean>) JsonUtils.parseJsonToList(response.body().string(), new TypeToken<List<ExchangeBean>>() {
                    }.getType());
                    new RequestCallBackProxy(callBack).onSuccess(exchanges);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call call, IOException e) {
                new RequestCallBackProxy(callBack).onFailure(e.getMessage());
            }
        });
    }

    @Override
    public void getMoney(final RequestCallBack<List<MoneyBean>> callBack) {
        Headers.Builder headersBuilder = new Headers.Builder();
        headersBuilder.add("Connection", "keep-alive");
        headersBuilder.add("Cookie", mHttpCookie.getKVString("ci_session"));

        Http.get(GetMoneyUrl, null, headersBuilder.build(), new HttpRequestCallBack() {
            @Override
            public void onResponse(Call call, Response response) {
                try {
                    List<MoneyBean> moneys = (List<MoneyBean>) JsonUtils.parseJsonToList(response.body().string(), new TypeToken<List<MoneyBean>>() {
                    }.getType());
                    new RequestCallBackProxy(callBack).onSuccess(moneys);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call call, IOException e) {
                new RequestCallBackProxy(callBack).onFailure(e.getMessage());
            }
        });
    }

    @Override
    public void preorder(long orderTime, int moneyID, int shopID, final RequestCallBack<PreorderResp> callBack) {
        Headers.Builder headersBuilder = new Headers.Builder();
        headersBuilder.add("Connection", "keep-alive");
        headersBuilder.add("Cookie", mHttpCookie.getKVString("ci_session"));

        Http.get(PreOrderUrl, null, headersBuilder.build(), new HttpRequestCallBack() {
            @Override
            public void onResponse(Call call, Response response) {
                try {
                    PreorderResp preorderResp = JsonUtils.parseJsonToBean(response.body().string(), PreorderResp.class);
                    new RequestCallBackProxy(callBack).onSuccess(preorderResp);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call call, IOException e) {
                new RequestCallBackProxy(callBack).onFailure(e.getMessage());
            }
        });
    }

    public HttpCookie getCookie() {
        return mHttpCookie;
    }

    public void setCookie(HttpCookie cookie) {
        mHttpCookie = cookie;
    }

    public interface RequestCallBack<T> {
        void onSuccess(T data);

        void onSuccess(List<T> data);

        void onFailure(String error);
    }

    public class RequestCallBackProxy<T> implements RequestCallBack<T> {
        private RequestCallBack mCallBack;

        public RequestCallBackProxy(RequestCallBack callBack) {
            mCallBack = callBack;
        }

        @Override
        public void onSuccess(final T data) {
            ThreadUtils.runOnForeground(new Runnable() {
                @Override
                public void run() {
                    mCallBack.onSuccess(data);
                }
            });
        }

        @Override
        public void onSuccess(final List<T> data) {
            ThreadUtils.runOnForeground(new Runnable() {
                @Override
                public void run() {
                    mCallBack.onSuccess(data);
                }
            });
        }

        @Override
        public void onFailure(final String error) {
            ThreadUtils.runOnForeground(new Runnable() {
                @Override
                public void run() {
                    mCallBack.onFailure(error);
                }
            });
        }
    }
}
