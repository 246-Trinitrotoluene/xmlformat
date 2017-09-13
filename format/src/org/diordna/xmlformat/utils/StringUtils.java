package org.diordna.xmlformat.utils;

public class StringUtils {

    //字符串判空
    public static boolean isEmpty(String s) {
        if (s == null || "".equals(s)) {
            return true;
        }
        return false;
    }
}
