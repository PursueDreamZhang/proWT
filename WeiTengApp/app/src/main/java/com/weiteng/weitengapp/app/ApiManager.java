package com.weiteng.weitengapp.app;

import com.weiteng.weitengapp.interf.IServerAPI;

/**
 * Created by Admin on 2017/1/7.
 */

public class ApiManager implements IServerAPI{
    private static ApiManager mApi;

    private ApiManager() {
    }

    public static synchronized ApiManager getApiManager() {
        if (mApi == null) {
            mApi = new ApiManager();
        }

        return mApi;
    }
}
