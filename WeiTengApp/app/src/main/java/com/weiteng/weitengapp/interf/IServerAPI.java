package com.weiteng.weitengapp.interf;

import com.weiteng.weitengapp.app.ApiManager;
import com.weiteng.weitengapp.bean.ExchangeBean;
import com.weiteng.weitengapp.bean.MoneyBean;
import com.weiteng.weitengapp.bean.UserInfoBean;
import com.weiteng.weitengapp.bean.resp.LoginResp;
import com.weiteng.weitengapp.bean.resp.PreOrderResp;

import java.util.List;

/**
 * Created by Admin on 2017/1/7.
 */

public interface IServerAPI {
    public String HOST = "http://weiteng.tdwebsite.cn";

    public String VerifyCodeUrl = HOST + "/Login/getCode/?_=";
    public String LoginUrl = HOST + "/Login/index";
    public String GetUserInfoUrl = HOST + "/Api_user/userinfo";
    public String GetExchangeUrl = HOST + "/Api_user/userExchangeQuery";
    public String GetMoneyUrl = HOST + "/Api_user/moneyList";
    public String PreOrderUrl = HOST + "/Api_user/orderAdd";

    //GET
    //params: *timestamp
    public void getVerifyCode(long time, ApiManager.RequestCallBack<String> callBack);

    //POST
    //params: *username *password *userType *code *loginType
    public void login(String username, String password, int userType, String code, int loginType, ApiManager.RequestCallBack<LoginResp> callBack);

    //GET
    //params:
    public void getUserInfo(ApiManager.RequestCallBack<UserInfoBean> callBack);

    //POST
    //params: *exchangeType *page *pagesize exchangeTime1 exchangeTime2
    public void getExchange(int exchangeType, int page, int pagesize, long exchangeTime1, long exchangeTime2, ApiManager.RequestCallBack<List<ExchangeBean>> callBack);

    //GET
    //params:
    public void getMoney(ApiManager.RequestCallBack<List<MoneyBean>> callBack);

    //POST
    //params: *orderTime *moneyID *shopID
    public void preorder(long orderTime, int moneyID, int shopID, ApiManager.RequestCallBack<PreOrderResp> callBack);
}
