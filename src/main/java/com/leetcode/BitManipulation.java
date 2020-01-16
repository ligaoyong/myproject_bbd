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
    public int singleNumber1(int[] nums) {
        int res = nums[0];
        for (int i=1;i<nums.length;i++){
            res ^= nums[i];
        }
        return res;
    }

    /**
     * 137. 只出现一次的数字 II 其他数字出现3次
     * 利用位运算
     */
    public int singleNumber(int[] nums) {
        int res = 0;
        //计算每一个比特位
        for (int i = 0;i<32;i++){
            int eachBitSum = 0;
            for (int j = 0;j<nums.length;j++){
                eachBitSum += (nums[j] >>> i) & 1;
            }
            if (eachBitSum % 3 != 0){
                // | 表示按位或 也即表示相加
                res |= 1 << i;
            }
        }
        return res;
    }

    public static void main(String[] args) {
        BitManipulation bitManipulation = new BitManipulation();
        int[] input = {2,2,3,2};
        int i = bitManipulation.singleNumber(input);
        System.out.println(i);
    }
}
