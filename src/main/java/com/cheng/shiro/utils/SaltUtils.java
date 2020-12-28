package com.cheng.shiro.utils;

import java.util.Random;

/**
 * Created by chenghx on 2020/12/9 15:27
 */
public class SaltUtils {
    public static String getSalt(int len){
        char[] chars = "ABCDEFGHIJKMNOPQRSTUVWXYZabcdefghijkmnopqrstuvwxyz1234567890!@#$%^&*()".toCharArray();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < len; i++) {
            char r = chars[new Random().nextInt(chars.length)];
            sb.append(r);
        }
        return sb.toString();
    }
}
