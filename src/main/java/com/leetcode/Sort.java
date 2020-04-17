package com.leetcode;

import java.util.*;

/**
 * 排序
 *
 * @author ligaoyong@gogpay.cn
 * @date 2020/4/17 11:08
 */
public class Sort {

    public static void main(String[] args) {
        Sort sort = new Sort();
        int[] nums = {0};
        sort.sortColors(nums);
        System.out.println(nums);
    }

    /**
     * 75. 颜色分类
     *
     * @param nums
     */
    public void sortColors(int[] nums) {
        HashMap<Integer, Integer> hashMap = new HashMap<>();
        hashMap.put(0, 0);
        hashMap.put(1, 0);
        hashMap.put(2, 0);
        for (int num : nums) {
            hashMap.put(num, hashMap.getOrDefault(num, 0) + 1);
        }
        int j = 0;
        for (int i = 0; i < hashMap.get(0); i++,j++) {
            nums[j] = 0;
        }
        for (int i = 0; i < hashMap.get(1); i++,j++) {
            nums[j] = 1;
        }
        for (int i = 0; i < hashMap.get(2); i++,j++) {
            nums[j] = 2;
        }
    }

    /**
     * 451. 根据字符出现频率排序
     *
     * @param s
     * @return
     */
    public String frequencySort(String s) {
        //保存数字到次数的映射
        HashMap<Character, Integer> map = new HashMap<>(s.length());
        char[] chars = s.toCharArray();
        for (char c : chars) {
            map.put(c, map.getOrDefault(c, 0) + 1);
        }
        //使用桶排序(对出现次数的排序)
        Set<Character>[] list = new TreeSet[s.length() + 1];
        for (Character character : map.keySet()) {
            Integer count = map.get(character);
            if (list[count] == null) {
                list[count] = new TreeSet<>();
            }
            list[count].add(character);
        }
        //排序后处理
        StringBuilder result = new StringBuilder();
        for (int i = list.length - 1; i > 0; i--) {
            Set<Character> eachCount = list[i];
            if (eachCount != null && eachCount.size() > 0) {
                for (Character character : eachCount) {
                    for (int j = 0; j < i; j++) {
                        result.append(character);
                    }
                }
            }
        }
        return result.toString();
    }

    /**
     * 347. 前 K 个高频元素
     * 输入: nums = [1,1,1,2,2,3], k = 2
     * 输出: [1,2]
     * <p>
     * 使用桶排序
     *
     * @param nums
     * @param k
     * @return
     */
    public List<Integer> topKFrequent(int[] nums, int k) {
        //保存数字到次数的映射
        HashMap<Integer, Integer> map = new HashMap<>();
        for (int num : nums) {
            map.put(num, map.getOrDefault(num, 0) + 1);
        }
        //保存出现指定次数的数字
        ArrayList<Integer>[] array = new ArrayList[nums.length + 1];
        for (Integer key : map.keySet()) {
            //次数
            Integer count = map.get(key);
            //次数表示数组的下表 数组的值是一个list，list存的是出现改次数的数字
            if (array[count] == null) {
                array[count] = new ArrayList<>();
            }
            array[count].add(key);
        }
        //从后往前遍历array 找k个数字
        List<Integer> result = new ArrayList<>(k);
        for (int i = array.length - 1; i > 0 && result.size() < k; i--) {
            if (array[i] != null && array[i].size() > 0) { //有出现次数i的数字
                if (array[i].size() <= k - result.size()) {
                    result.addAll(array[i]);
                } else {
                    for (int j = 0; j < k - result.size(); j++) {
                        result.add(array[i].get(j));
                    }
                }
            }
        }
        return result;
    }
}
