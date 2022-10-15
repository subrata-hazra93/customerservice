package com.psl.util;

/**
 * Check any String is null or blank or empty
 */

public class StringUtil {

    private StringUtil(){}
    public static boolean isEmpty(String s){
        return s == null || s.isBlank() || s.isEmpty();
    }
}
