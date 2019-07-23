package com.learn.test;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 求最长回文串
 */
public class LongestPalindromicSubstring {

    public static void main(String[] args) {
        getLongerepetitionStr("pop-upu");
    }

    public static void getLongerepetitionStr(String templet) {
        if (templet == null || templet.length() <= 0)
            return;
        int start = 0;
        int end = 0;
        List<String> res = new ArrayList<>();
        for (int i = 0; i < templet.length(); i++) {
            int len1 = compareAroundCenter(templet, i, i);
            int len2 = compareAroundCenter(templet, i, i+1);
            int len = Math.max(len1, len2);
            if (len > end - start){
                start = i - (len - 1) / 2;
                end = i + len / 2;
                res.add(templet.substring(start, end + 1));
                if (res.get(0).length() < templet.substring(start, end + 1).length()) {
                    res.remove(0);
                }
            }
        }
        for (String s : res) {
            System.out.println(s);
        }
    }

    private static int compareAroundCenter(String s, int left, int right) {
        int l = left;
        int r = right;
        while (l >= 0 && r < s.length() && s.charAt(l) == s.charAt(r)) {
            l--;
            r++;
        }
        return r - l - 1;
    }


}
