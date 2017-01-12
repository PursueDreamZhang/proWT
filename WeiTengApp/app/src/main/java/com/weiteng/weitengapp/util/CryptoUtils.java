package com.weiteng.weitengapp.util;

import android.util.Base64;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

/**
 * Created by Admin on 2016/11/7.
 */

public class CryptoUtils {
    public static String MD5(String data) {
        String result = null;
        try {
            result = new String(Byte2Hex(MessageDigest.getInstance("MD5").digest(data.getBytes())));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        return result;
    }

    public static byte[] AESEncrypt(byte[] data, String key, String provider) {
        byte[] result = null;
        try {
            KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
            SecureRandom secureRandom = null;
            if (provider == null) {
                secureRandom = SecureRandom.getInstance("SHA1PRNG");
            } else {
                secureRandom = SecureRandom.getInstance("SHA1PRNG", provider);
            }
            secureRandom.setSeed(key.getBytes("utf-8"));
            keyGenerator.init(128, secureRandom);
            SecretKey secretKey = keyGenerator.generateKey();
            byte[] encoded = secretKey.getEncoded();
            SecretKeySpec secretKeySpec = new SecretKeySpec(encoded, "AES");
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec);
            result = cipher.doFinal(data);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    public static byte[] AESDecrypt(byte[] data, String key, String provider) {
        byte[] result = null;
        try {
            KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
            SecureRandom secureRandom = null;
            if (provider == null) {
                secureRandom = SecureRandom.getInstance("SHA1PRNG");
            } else {
                secureRandom = SecureRandom.getInstance("SHA1PRNG", provider);
            }
            secureRandom.setSeed(key.getBytes("utf-8"));
            keyGenerator.init(128, secureRandom);
            SecretKey secretKey = keyGenerator.generateKey();
            byte[] encoded = secretKey.getEncoded();
            SecretKeySpec secretKeySpec = new SecretKeySpec(encoded, "AES");
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.DECRYPT_MODE, secretKeySpec);
            result = cipher.doFinal(data);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    public static String B64Encrypt(String data) {
        return new String(Base64.encode(data.getBytes(), data.length()));
    }

    public static String B64Decrypt(String data) {
        return new String(Base64.decode(data.getBytes(), data.length()));
    }

    public static String Byte2Hex(byte[] bytes) {
        StringBuffer buffer = new StringBuffer();
        for (int i = 0; i < bytes.length; i++) {
            String hex = Integer.toHexString(bytes[i] & 0xFF);
            if (hex.length() == 1) {
                hex = '0' + hex;
            }
            buffer.append(hex.toUpperCase());
        }

        return buffer.toString();
    }

    public static byte[] Hex2Byte(String hex) {
        byte[] result = null;
        if (hex.length() > 0) {
            result = new byte[hex.length() / 2];
            for (int i = 0; i < hex.length() / 2; i++) {
                int high = Integer.parseInt(hex.substring(i * 2, i * 2 + 1), 16);
                int low = Integer.parseInt(hex.substring(i * 2 + 1, i * 2 + 2), 16);
                result[i] = (byte) (high * 16 + low);
            }
        }

        return result;
    }
}
