package com.leetcode;

/**
 * 分治算法
 *
 * @author ligaoyong@gogpay.cn
 * @date 2020/1/10 14:29
 */
public class Fenzhi {

    /**
     * 169 求众数 用分治法
     *
     * @param nums
     * @return
     */
    public int majorityElement(int[] nums) {
        return recur(nums,0,nums.length-1);
    }

    /**
     * 求指定区间的众数
     *
     * @param nums
     * @param left
     * @param right
     * @return
     */
    private int recur(int[] nums, int left, int right) {
        if (left == right) {
            return nums[left];
        }
        int mid = (right - left) / 2 + left;

        //求左区间的众数
        int leftMount = recur(nums, left, mid);
        //求右区间的众数
        int rightMount = recur(nums, mid + 1, right);

        if (leftMount == rightMount) {
            return leftMount;
        }

        /*左右区间的众数不相等 比较他们的个数*/
        int leftCount = 0;
        int rightCount = 0;
        for (int i = left; i <= mid; i++) {
            if (nums[i] == leftMount){
                leftCount++;
            }
        }
        for (int i = mid+1; i <= right; i++) {
            if (nums[i] == rightMount){
                rightCount++;
            }
        }
        return leftCount > rightCount ? leftMount : rightMount;
    }
}
