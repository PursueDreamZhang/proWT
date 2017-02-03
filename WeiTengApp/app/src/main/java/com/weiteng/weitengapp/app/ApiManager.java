package com.weiteng.weitengapp.app;

import com.google.gson.reflect.TypeToken;
import com.weiteng.weitengapp.bean.ExchangeBean;
import com.weiteng.weitengapp.bean.MoneyBean;
import com.weiteng.weitengapp.bean.NoticeBean;
import com.weiteng.weitengapp.bean.NoticeDetailBean;
import com.weiteng.weitengapp.bean.ShopBean;
import com.weiteng.weitengapp.bean.UserBean;
import com.weiteng.weitengapp.bean.resp.CommonListResp;
import com.weiteng.weitengapp.bean.resp.CommonResp;
import com.weiteng.weitengapp.interf.IServerAPI;
import com.weiteng.weitengapp.module.http.Http;
import com.weiteng.weitengapp.module.http.HttpCookie;
import com.weiteng.weitengapp.module.http.HttpRequestCallBack;
import com.weiteng.weitengapp.util.FileUtils;
import com.weiteng.weitengapp.util.JsonUtils;
import com.weiteng.weitengapp.util.LogUtils;
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

        Http.get(VerifyCodeUrl + time / 1000, null, headersBuilder.build(), new HttpRequestCallBack() {
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
                } catch (Exception e) {
                    new RequestCallBackProxy(callBack).onFailure("网络错误");
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
    public void login(String username, String password, int userType, String code, int loginType, final RequestCallBack<CommonResp> callBack) {
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
                    String json = response.body().string();
                    CommonResp commonResp = JsonUtils.parseJsonToBean(json, CommonResp.class);

                    if (commonResp != null) {
                        new RequestCallBackProxy(callBack).onSuccess(commonResp);
                    } else {
                        new RequestCallBackProxy(callBack).onFailure("网络错误");
                    }
                } catch (Exception e) {
                    new RequestCallBackProxy(callBack).onFailure("网络错误");
                }
            }

            @Override
            public void onFailure(Call call, IOException e) {
                new RequestCallBackProxy(callBack).onFailure(e.getMessage());
            }
        });
    }

    @Override
    public void getUserInfo(final RequestCallBack<UserBean> callBack) {
        Headers.Builder headersBuilder = new Headers.Builder();
        headersBuilder.add("Connection", "keep-alive");
        headersBuilder.add("Cookie", mHttpCookie.getKVString("ci_session"));

        Http.get(GetUserInfoUrl, null, headersBuilder.build(), new HttpRequestCallBack() {
            @Override
            public void onResponse(Call call, Response response) {
                try {
                    String json = response.body().string();
                    UserBean userInfo = JsonUtils.parseJsonToBean(json, UserBean.class);

                    if (userInfo != null) {
                        new RequestCallBackProxy(callBack).onSuccess(userInfo);
                    } else {
                        new RequestCallBackProxy(callBack).onFailure("网络错误");
                    }
                } catch (Exception e) {
                    new RequestCallBackProxy(callBack).onFailure("网络错误");
                        }
                    }

            @Override
            public void onFailure(Call call, IOException e) {
                new RequestCallBackProxy(callBack).onFailure(e.getMessage());
                    }
                }
        );
    }

    @Override
    public void getExchange(int exchangeType, int page, int pagesize, long exchangeTime1,
                            long exchangeTime2, final RequestCallBack<ExchangeBean> callBack) {
        Headers.Builder headersBuilder = new Headers.Builder();
        headersBuilder.add("Connection", "keep-alive");
        headersBuilder.add("Cookie", mHttpCookie.getKVString("ci_session"));

        FormBody.Builder formBuilder = new FormBody.Builder();
        formBuilder.add("exchangeType", exchangeType + "");
        formBuilder.add("page", page + "");
        formBuilder.add("pagesize", pagesize + "");
        formBuilder.add("exchangeTime1", exchangeTime1 / 1000 + "");
        formBuilder.add("exchangeTime2", exchangeTime2 / 1000 + "");

        Http.post(GetExchangeUrl, null, formBuilder.build(), headersBuilder.build(), new HttpRequestCallBack() {
            @Override
            public void onResponse(Call call, Response response) {
                try {
                    String json = response.body().string();
                    CommonListResp exchangeResp = JsonUtils.parseJsonToBean(json, CommonListResp.class);
                    if (exchangeResp != null) {
                        new RequestCallBackProxy(callBack).onSuccess(exchangeResp.exchange);
                    } else {
                        new RequestCallBackProxy(callBack).onFailure("网络错误");
                    }
                } catch (Exception e) {
                    new RequestCallBackProxy(callBack).onFailure("网络错误");
                }
            }

            @Override
            public void onFailure(Call call, IOException e) {
                new RequestCallBackProxy(callBack).onFailure(e.getMessage());
            }
        });
    }

    @Override
    public void getMoney(final RequestCallBack<MoneyBean> callBack) {
        Headers.Builder headersBuilder = new Headers.Builder();
        headersBuilder.add("Connection", "keep-alive");
        headersBuilder.add("Cookie", mHttpCookie.getKVString("ci_session"));

        Http.get(GetMoneyUrl, null, headersBuilder.build(), new HttpRequestCallBack() {
            @Override
            public void onResponse(Call call, Response response) {
                try {
                    String json = response.body().string().replaceAll("\n", "").replaceAll("<div(.*)div>", "");
                    List<MoneyBean> moneys = null;
                    moneys = (List<MoneyBean>) JsonUtils.parseJsonToList(json, new TypeToken<List<MoneyBean>>() {
                    }.getType());
                    if (moneys != null) {
                        new RequestCallBackProxy(callBack).onSuccess(moneys);
                    } else {
                        new RequestCallBackProxy(callBack).onFailure("网络错误");
                    }
                } catch (Exception e) {
                    new RequestCallBackProxy(callBack).onFailure("网络错误");
                }
            }

            @Override
            public void onFailure(Call call, IOException e) {
                new RequestCallBackProxy(callBack).onFailure(e.getMessage());
            }
        });
    }

    @Override
    public void preorder(long orderTime, String moneyID, String money, String shopID, final RequestCallBack<CommonResp> callBack) {
        Headers.Builder headersBuilder = new Headers.Builder();
        headersBuilder.add("Connection", "keep-alive");
        headersBuilder.add("Cookie", mHttpCookie.getKVString("ci_session"));

        FormBody.Builder formBuilder = new FormBody.Builder();
        formBuilder.add("orderTime", orderTime / 1000 + "");
        if (moneyID != null) {
            formBuilder.add("moneyID", moneyID);
        } else {
            formBuilder.add("moneyID", "");
        }
        if (money != null) {
            formBuilder.add("money", money);
        } else {
            formBuilder.add("money", "");
        }
        formBuilder.add("shopID", shopID);

        Http.post(PreOrderUrl, null, formBuilder.build(), headersBuilder.build(), new HttpRequestCallBack() {
            @Override
            public void onResponse(Call call, Response response) {
                try {
                    String json = response.body().string().replaceAll("\n", "").replaceAll("<div(.*)div>", "");
                    CommonResp commonResp = JsonUtils.parseJsonToBean(json, CommonResp.class);
                    if (commonResp != null) {
                        new RequestCallBackProxy(callBack).onSuccess(commonResp);
                    } else {
                        new RequestCallBackProxy(callBack).onFailure("网络错误");
                    }
                } catch (Exception e) {
                    new RequestCallBackProxy(callBack).onFailure("网络错误");
                }
            }

            @Override
            public void onFailure(Call call, IOException e) {
                new RequestCallBackProxy(callBack).onFailure(e.getMessage());
            }
        });
    }

    @Override
    public void transfer(String username, String money,
                         final ApiManager.RequestCallBack<CommonResp> callBack) {
        Headers.Builder headersBuilder = new Headers.Builder();
        headersBuilder.add("Connection", "keep-alive");
        headersBuilder.add("Cookie", mHttpCookie.getKVString("ci_session"));

        FormBody.Builder formBuilder = new FormBody.Builder();
        formBuilder.add("username", username);
        formBuilder.add("money", money);

        Http.post(TransferUrl, null, formBuilder.build(), headersBuilder.build(), new HttpRequestCallBack() {
            @Override
            public void onResponse(Call call, Response response) {
                try {
                    String json = response.body().string();
                    CommonResp commonResp = JsonUtils.parseJsonToBean(json, CommonResp.class);
                    if (commonResp != null) {
                        new RequestCallBackProxy(callBack).onSuccess(commonResp);
                    } else {
                        new RequestCallBackProxy(callBack).onFailure("网络错误");
                    }
                } catch (Exception e) {
                    new RequestCallBackProxy(callBack).onFailure("网络错误");
                }
            }

            @Override
            public void onFailure(Call call, IOException e) {
                new RequestCallBackProxy(callBack).onFailure(e.getMessage());
            }
        });
    }

    @Override
    public void getNoticeList(String userType, String noticeType, int page, int pagesize, final RequestCallBack<NoticeBean> callBack) {
        Headers.Builder headersBuilder = new Headers.Builder();
        headersBuilder.add("Connection", "keep-alive");
        headersBuilder.add("Cookie", mHttpCookie.getKVString("ci_session"));

        FormBody.Builder formBuilder = new FormBody.Builder();
        formBuilder.add("userType", userType);
        formBuilder.add("noticeType", noticeType);
        if (page != -1) {
            formBuilder.add("page", page + "");
        }
        if (pagesize != -1) {
            formBuilder.add("pagesize", pagesize + "");
        }
        Http.post(NoticeListUrl, null, formBuilder.build(), headersBuilder.build(), new HttpRequestCallBack() {
            @Override
            public void onResponse(Call call, Response response) {
                try {
                    String json = response.body().string();
                    CommonListResp noticeListResp = JsonUtils.parseJsonToBean(json, CommonListResp.class);
                    if (noticeListResp != null) {
                        new RequestCallBackProxy(callBack).onSuccess(noticeListResp.noticeList);
                    } else {
                        new RequestCallBackProxy(callBack).onFailure("网络错误");
                    }
                } catch (Exception e) {
                    new RequestCallBackProxy(callBack).onFailure("网络错误");
                }
            }

            @Override
            public void onFailure(Call call, IOException e) {
                new RequestCallBackProxy(callBack).onFailure(e.getMessage());
            }
        });
    }

    @Override
    public void getNoticeDetail(String id, final RequestCallBack<NoticeDetailBean> callBack) {
        Headers.Builder headersBuilder = new Headers.Builder();
        headersBuilder.add("Connection", "keep-alive");
        headersBuilder.add("Cookie", mHttpCookie.getKVString("ci_session"));

        Http.get(NoticeDetailUrl + id, null, headersBuilder.build(), new HttpRequestCallBack() {
            @Override
            public void onResponse(Call call, Response response) {
                try {
                    String json = response.body().string();
                    List<NoticeDetailBean> noticeDetails = null;
                    noticeDetails = (List<NoticeDetailBean>) JsonUtils.parseJsonToList(json, new TypeToken<List<NoticeDetailBean>>() {
                    }.getType());

                    if (noticeDetails != null) {
                        new RequestCallBackProxy(callBack).onSuccess(noticeDetails);
                    } else {
                        new RequestCallBackProxy(callBack).onFailure("网络错误");
                    }
                } catch (Exception e) {
                    new RequestCallBackProxy(callBack).onFailure("网络错误");
                }
            }

            @Override
            public void onFailure(Call call, IOException e) {
                new RequestCallBackProxy(callBack).onFailure(e.getMessage());
            }
        });
    }

    @Override
    public void getShopList(String shopname, int page, int pagesize, final RequestCallBack<ShopBean> callBack) {
        Headers.Builder headersBuilder = new Headers.Builder();
        headersBuilder.add("Connection", "keep-alive");
        headersBuilder.add("Cookie", mHttpCookie.getKVString("ci_session"));

        FormBody.Builder formBuilder = new FormBody.Builder();
        formBuilder.add("shopname", shopname);
        formBuilder.add("page", page + "");
        formBuilder.add("pagesize", pagesize + "");
        Http.post(ShopListUrl, null, formBuilder.build(), headersBuilder.build(), new HttpRequestCallBack() {
            @Override
            public void onResponse(Call call, Response response) {
                try {
                    String json = response.body().string();
                    CommonListResp shopListResp = JsonUtils.parseJsonToBean(json, CommonListResp.class);
                    if (shopListResp != null) {
                        new RequestCallBackProxy(callBack).onSuccess(shopListResp.shopList);
                    } else {
                        new RequestCallBackProxy(callBack).onFailure("网络错误");
                    }
                } catch (Exception e) {
                    new RequestCallBackProxy(callBack).onFailure("网络错误");
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

    public void clearCookie() {
        mHttpCookie.clear();
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
                    if (mCallBack != null) {
                        mCallBack.onSuccess(data);
                    }
                }
            });
        }

        @Override
        public void onSuccess(final List<T> data) {
            ThreadUtils.runOnForeground(new Runnable() {
                @Override
                public void run() {
                    if (mCallBack != null) {
                        mCallBack.onSuccess(data);
                    }
                }
            });
        }

        @Override
        public void onFailure(final String error) {
            ThreadUtils.runOnForeground(new Runnable() {
                @Override
                public void run() {
                    if (error != null) {
                        LogUtils.e("Internet", error);
                    }
                    if (mCallBack != null) {
                        mCallBack.onFailure(error);
                    }
                }
            });
        }
    }
}
