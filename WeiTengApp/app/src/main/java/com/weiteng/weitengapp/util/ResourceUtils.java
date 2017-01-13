package com.weiteng.weitengapp.util;

import android.app.Application;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;


@SuppressWarnings("deprecation")
public class ResourceUtils {
	public static Resources getResources(Application app) {
		return app.getResources();
	}

	public static String getString(Application app, int resId) {
		return getResources(app).getString(resId);
	}

	public static String[] getStringArray(Application app, int resId) {
		return getResources(app).getStringArray(resId);
	}

	public static Drawable getDrawable(Application app, int resId) {
		return getResources(app).getDrawable(resId);
	}

	public static float getDimen(Application app, int resId) {
		return getResources(app).getDimension(resId);
	}

	public static int getColor(Application app, int resId) {
		return getResources(app).getColor(resId);
	}
}
