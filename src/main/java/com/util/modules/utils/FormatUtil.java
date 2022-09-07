package com.util.modules.utils;

public class FormatUtil {

    public static String phoneConvert(String s) {
        s = s.replaceAll("-", "");

        if(s==null || s.equals("")) return "";

        if(s.length()==11) {
            return s.substring(0, 3) + "-" + s.substring(3, 7) + "-" + s.substring(7, 11);
        } else if(s.length()==10) {
            return s.substring(0, 3) + "-" + s.substring(3, 6) + "-" + s.substring(6, 10);
        } else if(s.length()==9) {
            return s.substring(0, 2) + "-" + s.substring(2, 5) + "-" + s.substring(5, 9);
        } else {
            return s;
        }
    }
}
