package com.weiteng.weitengapp.util;

import android.content.res.Resources;
import android.graphics.drawable.Drawable;

import com.weiteng.weitengapp.app.Application;
import com.weiteng.weitengapp.base.BaseApplication;

@SuppressWarnings("deprecation")
public class ResourceUtils {
	private static BaseApplication mApp;

	public static void init(Application application) {
		mApp = (BaseApplication) application;
	}

	public static String getString(int resId) {
		return mApp.getResources().getString(resId);
	}

	public static String[] getStringArray(int resId) {
		return mApp.getResources().getStringArray(resId);
	}

	public static Drawable getDrawable(int resId) {
		return mApp.getResources().getDrawable(resId);
	}

	public static float getDimen(int resId) {
		return mApp.getResources().getDimension(resId);
	}

	public static int getColor(int resId) {
		return mApp.getResources().getColor(resId);
	}
}
