package com.leetcode;

/**
 * 位运算
 *
 * @author ligaoyong@gogpay.cn
 * @date 2020/1/13 14:21
 */
public class BitManipulation {
    /**
     * 136. 只出现一次的数字
     * @param nums
     * @return
     */
    public int singleNumber(int[] nums) {
        int res = nums[0];
        for (int i=1;i<nums.length;i++){
            res ^= nums[i];
        }
        return res;
    }
}
