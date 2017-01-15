package com.weiteng.weitengapp.app;

import android.os.Environment;

import java.io.File;

/**
 * Created by Admin on 2016/11/8.
 */

public class Configuration {
    public static String DEFAULT_PATH_PREFIX = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "weiteng" + File.separator;
    public static String DEFAULT_LOG_PATH = DEFAULT_PATH_PREFIX + "log";
    public static String DEFAULT_CACHE_PATH = DEFAULT_PATH_PREFIX + "cache";
    public static String DEFAULT_DOWNLOAD_PATH = DEFAULT_PATH_PREFIX + "download";
    public static String EncryptKey = "1234567890";
}
