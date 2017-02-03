package com.weiteng.weitengapp.interf;

import com.weiteng.weitengapp.app.ApiManager;
import com.weiteng.weitengapp.bean.ExchangeBean;
import com.weiteng.weitengapp.bean.MoneyBean;
import com.weiteng.weitengapp.bean.NoticeBean;
import com.weiteng.weitengapp.bean.NoticeDetailBean;
import com.weiteng.weitengapp.bean.ShopBean;
import com.weiteng.weitengapp.bean.UserBean;
import com.weiteng.weitengapp.bean.resp.CommonResp;

import java.io.File;

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
    String PreOrderUrl = HOST + "/Api_user/customerGetCashOrderAdd";
    String TransferUrl = HOST + "/Api_user/exchangeC2C";
    String NoticeListUrl = HOST + "/Api_user/noticeCustomerList";
    String NoticeDetailUrl = HOST + "/Api_user/readNotice/";
    String ShopListUrl = HOST + "/Api_user/orderShopList";

    //GET
    //params: *timestamp
    void getVerifyCode(long time, ApiManager.RequestCallBack<File> callBack);

    //POST
    //params: *username *password *userType *code *loginType
    void login(String username, String password, int userType, String code, int loginType, ApiManager.RequestCallBack<CommonResp> callBack);

    //GET
    //params:
    void getUserInfo(ApiManager.RequestCallBack<UserBean> callBack);

    //POST
    //params: *exchangeType *page *pagesize exchangeTime1 exchangeTime2
    void getExchange(int exchangeType, int page, int pagesize, long exchangeTime1, long exchangeTime2, ApiManager.RequestCallBack<ExchangeBean> callBack);

    //GET
    //params:
    void getMoney(ApiManager.RequestCallBack<MoneyBean> callBack);

    //POST
    //params: *orderTime *moneyID *shopID
    void preorder(long orderTime, String moneyID, String money, String shopID, ApiManager.RequestCallBack<CommonResp> callBack);

    //POST
    //params: *username *money
    void transfer(String username, String money, ApiManager.RequestCallBack<CommonResp> callBack);

    //POST
    //params: *userType page pagesixe\
    void getNoticeList(String userType, String noitceType, int page, int pagesize, ApiManager.RequestCallBack<NoticeBean> callBack);

    //GET
    //params: *id
    void getNoticeDetail(String id, ApiManager.RequestCallBack<NoticeDetailBean> callBack);

    //POST
    //params: *shopname, *page, *pagesize
    void getShopList(String shopname, int page, int pagesize, ApiManager.RequestCallBack<ShopBean> callBack);

}
