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
    public String longestPalindrome0(String s) {
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

    /**
     * 最长回文子串: 动态规划写法
     *  leetcode 通过
     * @param s
     * @return
     */
    public String longestPalindrome(String s) {
        if (s == null || "".equals(s) || s.length() == 1){
            return s;
        }

        /**
         * 使用动态规划的条件：
         *  1、大问题可以分解为小问题，由小问题的解得到大问题的解
         *  2、求解过程中会反复使用到相同的子问题的解
         *      比如斐波那契数列 f(5) = f(4) + F(3)
         *                            = f(3) + f(2) + f(3)
         *      若用递归的方式求解，会反复求解f(3)
         *      动态规划的思想时保存以及计算过的值，方便以后使用
         */

        //用dp[i][j]表示下标i到j的字串是不是回文子串
        boolean[][] dp = new boolean[s.length()][s.length()];
        for (int i = 0; i < s.length(); i++) {
            dp[i][i] = true;
        }

        //写出状态转移方程
        /**
         * dp[i][j] = (s(i) == s(j) && dp[i+1][i-1])
         *  s(i)表示第i个字符
         */

        //所有的操作都围绕这这个dp数组来进行
        int start = 0;
        int length = 1;

        for (int i=1;i<s.length();i++){
            for (int j=0;j<i;j++){
                //这里相当于列举所有的情况 然后检查他是不是回文 如果不用动态规划 去检查回文还得用for 算法时间复杂度就是n的3次方
                if (s.charAt(j) ==s.charAt(i)){
                    //首尾字符相等 是不是回文子串取决于去掉收尾字串是不是回文 也即dp[j+1][i-1]
                    if (i-j < 3){
                        //j-i之间只有3个字符 也即去掉收尾字符 只剩下一个了 这种情况肯定是回文
                        dp[j][i] = true;
                    }else {
                        //取决于去掉收尾字串是不是回文
                        dp[j][i] = dp[j+1][i-1];
                    }
                }else {
                    // 首位字符不相等 肯定不是回文
                    dp[j][i] = false;
                }

                //检查j-i是不是回文
                if (dp[j][i]){
                    int curlenth = i - j + 1;
                    if (curlenth > length){
                        length = curlenth;
                        start = j;
                    }
                }
            }
        }

        return s.substring(start,start+length);
    }
}
