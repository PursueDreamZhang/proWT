package com.weiteng.weitengapp.app;

import com.weiteng.weitengapp.interf.IHttpCode;

public class HttpManager implements IHttpCode {
	private static HttpManager mHttp;
	private static ApiManager mApi;

	private HttpManager() {
		mApi = ApiManager.getApiManager();
	}

	public static synchronized HttpManager getHttpManager() {
		if (mHttp == null) {
			mHttp = new HttpManager();
		}

		return mHttp;
	}
}
