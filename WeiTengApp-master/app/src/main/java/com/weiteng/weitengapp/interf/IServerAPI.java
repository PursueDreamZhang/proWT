package com.weiteng.weitengapp.interf;

import com.weiteng.weitengapp.app.ApiManager;
import com.weiteng.weitengapp.bean.ExchangeBean;
import com.weiteng.weitengapp.bean.MoneyBean;
import com.weiteng.weitengapp.bean.UserInfoBean;
import com.weiteng.weitengapp.bean.resp.LoginResp;
import com.weiteng.weitengapp.bean.resp.PreorderResp;

import java.io.File;
import java.util.List;

/**
 * Created by Admin on 2017/1/7.
 */

public interface IServerAPI {
    String HOST = "http://weiteng.tdwebsite.cn";

    String VerifyCodeUrl = HOST + "/Login/getCode/?_=";
    String LoginUrl = HOST + "/Login/index";
    String GetUserInfoUrl = HOST + "/Api_user/userinfo";
    String GetExchangeUrl = HOST + "/Api_user/userExchangeQuery";
    String GetMoneyUrl = HOST + "/Api_user/moneyList";
    String PreOrderUrl = HOST + "/Api_user/orderAdd";

    //GET
    //params: *timestamp
    void getVerifyCode(long time, ApiManager.RequestCallBack<File> callBack);

    //POST
    //params: *username *password *userType *code *loginType
    void login(String username, String password, int userType, String code, int loginType, ApiManager.RequestCallBack<LoginResp> callBack);

    //GET
    //params:
    void getUserInfo(ApiManager.RequestCallBack<UserInfoBean> callBack);

    //POST
    //params: *exchangeType *page *pagesize exchangeTime1 exchangeTime2
    void getExchange(int exchangeType, int page, int pagesize, long exchangeTime1, long exchangeTime2, ApiManager.RequestCallBack<List<ExchangeBean>> callBack);

    //GET
    //params:
    void getMoney(ApiManager.RequestCallBack<List<MoneyBean>> callBack);

    //POST
    //params: *orderTime *moneyID *shopID
    void preorder(long orderTime, int moneyID, int shopID, ApiManager.RequestCallBack<PreorderResp> callBack);
}
