package com.example.dllo.food.tools;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * Created by XiaoyuLu on 16/11/14.
 *
 * 实现将字符串转换成utf_8格式
 */
public class UTF8Util {
    public static String stringToUTF8(String str) {
        String result = null;
        try {
            result = URLEncoder.encode(str, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return result;
    }
}
