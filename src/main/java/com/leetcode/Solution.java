package com.leetcode;

/**
 * TODO
 *
 * @author ligaoyong@gogpay.cn
 * @date 2020/1/6 15:25
 */
public class Solution {
    /**
     * 求最长回文字串
     *
     * @param s
     * @return
     */
    public String longestPalindrome(String s) {
        if (s == null || "".equals(s) || s.length() == 1){
            return s;
        }
        String[] split = s.split("");
        String max = "";
        for (int i = 0; i < split.length; i++) {
            String max1 = getString(split, i, i + 1);
            String max2 = getString(split, i - 1, i + 1);
            String max3 = max1.length() > max2.length() ? max1 : max2;
            max = max.length() > max3.length() ? max : max3;
        }
        return max;
    }

    private String getString(String[] s,int left,int right){
        String string = String.join("", s);
        String max = "";
        left = left < 0 ? 0 : left;
        right = right >= s.length ? s.length - 1 : right;
        max = s[left];
        for (;left>=0 && right<s.length && s[left].equals(s[right]);--left,++right){
            max = string.substring(left, right + 1);
        }
        return max;
    }
}
