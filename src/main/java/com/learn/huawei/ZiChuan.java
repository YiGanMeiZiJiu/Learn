package com.learn.huawei;

import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * User: 小王
 * Date: 2021-01-29
 * Time: 19:00
 * Description:
 *
 * 1、输出字符串中没有重复字符的最长连续子串长度
 * 输入: "abcabfgghcbb"
 * 输出: 5
 * 解释: 因为无重复字符的最长子串是 "cabfg"，所以其长度为 5。
 * 输入: "bbbbb"
 * 输出: 1
 * 解释: 因为无重复字符的最长子串是 "b"，所以其长度为 1。
 */
public class ZiChuan {
    public static void main(String[] args) {
        String input = "abcabfgghcbb";

        if (input == null || input.length() <= 0) {
            System.out.println(0);
            return;
        }
        Set<String> set  = new HashSet<>();
        String result = "";
        for (int i = 0; i < input.length(); i++) {
            for (int j = i+ 1; j <= input.length(); j++) {
                String temp = input.substring(i, j);
                set.add(temp);
            }
        }
        int max = 0;
        Iterator iterator = set.iterator();
        while (iterator.hasNext()) {
            LinkedHashSet<String> setchar = new LinkedHashSet<>();
            String sc = iterator.next().toString();
            for (int i = 0; i < sc.length(); i++) {
                setchar.add(String.valueOf(sc.charAt(i)));
            }
            if (setchar.size() == sc.length()) {
                int len = sc.length();
                if (max < len) {
                    max = len;
                    result = sc;
                }
            }
        }
        System.out.println(max);
    }
}
