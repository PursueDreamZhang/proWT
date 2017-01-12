package com.weiteng.weitengapp.util;

import java.io.File;
import java.io.IOException;

/**
 * Created by Admin on 2016/11/7.
 */

public class FileUtils {
    public static void createFile(String dir, String filename) {
        File file = new File(dir, filename);
        File parent = file.getParentFile();
        if (!parent.exists()) {
            parent.mkdirs();
        }
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static File getFile(String dir, String filename) {
        File file = new File(dir, filename);
        File parent = file.getParentFile();
        if (!parent.exists()) {
            parent.mkdirs();
        }
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return file;
    }

    public static File getDirectory(String dir) {
        File file = new File(dir);
        if (!file.exists()) {
            file.mkdirs();
        }

        return file;
    }

    public static long getFileSize(File file) {
        if (!file.exists()) {
            return -1;
        }

        long size = 0;
        if (file.isDirectory()) {
            File[] files = file.listFiles();
            for (File file_ : files) {
                size += getFileSize(file_);
            }
        }
        if (file.isFile()) {
            size = file.length();
        }

        return size;
    }
}
