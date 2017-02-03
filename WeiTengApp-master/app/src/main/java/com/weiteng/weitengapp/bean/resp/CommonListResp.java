package com.weiteng.weitengapp.bean.resp;

import com.weiteng.weitengapp.bean.ExchangeBean;
import com.weiteng.weitengapp.bean.NoticeBean;
import com.weiteng.weitengapp.bean.ShopBean;

import java.util.List;

/**
 * Created by Admin on 2017/1/18.
 */

public class CommonListResp {
    public List<ExchangeBean> exchange;
    public List<NoticeBean> noticeList;
    public List<ShopBean> shopList;
    public int count;
}
