package com.leetcode;

import java.util.List;

/**
 * 双指针
 *
 * @author ligaoyong@gogpay.cn
 * @date 2020/4/17 10:11
 */
public class DoublePoint {

    /**
     * 524. 通过删除字母匹配到字典里最长单词
     *
     * @param s
     * @param d
     * @return
     */
    public String findLongestWord(String s, List<String> d) {
        String result = "";
        for (String each : d) {
            if (isSubStr(s,each)){
                if (each.length() > result.length()){
                    result = each;
                }
                if (each.length() == result.length() && each.compareTo(result) < 0){
                    result = each;
                }
            }
        }
        return result;
    }

    /**
     * 判断target是否是s删除某些字符得来 利用双指针
     *
     * @param s
     * @param target
     * @return
     */
    private boolean isSubStr(String s, String target) {
        int i = 0;
        int j = 0;
        while (i < s.length() && j < target.length()) {
            if (s.charAt(i) == target.charAt(j)) {
                j++;
            }
            i++;
        }
        if (j < target.length()) {
            return false;
        }
        return true;
    }

}
