package com.leetcode;

import java.util.HashSet;

/**
 * hash算法
 *
 * @author ligaoyong@gogpay.cn
 * @date 2020/1/21 11:51
 */
public class Hash {
    /**
     * 3. 无重复字符的最长子串
     * @param s
     * @return
     */
    public int lengthOfLongestSubstring(String s) {
        String[] split = s.split("");
        HashSet<String> set = new HashSet<>();
        int start = 0;
        set.add(split[0]);
        int end = 1;
        int length = 1;
        while (end < split.length){
            if (set.contains(split[end])){
                set.remove(split[start]);
                start++;
                end++;
            }else {
                set.add(split[end]);
                end++;
                if (set.size() > length) {
                    length = set.size();
                }
            }
        }
        return end -start;
    }

    public static void main(String[] args) {
        Hash hash = new Hash();
        int abcabcbb = hash.lengthOfLongestSubstring("abcabcbb");
        System.out.println(abcabcbb);
    }
}
