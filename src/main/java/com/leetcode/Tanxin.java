package com.leetcode;

import org.springframework.util.CollectionUtils;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;

/**
 * 贪心算法
 */
public class Tanxin {

    public static void main(String[] args) {
        Tanxin tanxin = new Tanxin();
        int[][] pe = new int[][]{new int[]{7, 0}, new int[]{4, 4}, new int[]{7, 1}, new int[]{5, 0}, new int[]{6, 1}, new int[]{5, 2}};
        int[][] ints = tanxin.reconstructQueue(pe);
        System.out.println();
    }

    /**
     * 406. 根据身高重建队列
     * 解：先按照身高排序 升高相等得按照第二个参数排序
     * 从大到小依次移动位置看是否满足条件：因为大的排好序后 小的挪动位置并不会影响到大得
     *
     * @param people
     * @return
     */
    public int[][] reconstructQueue(int[][] people) {
        Arrays.sort(people, (o1, o2) -> {
            if (o1[0] > o2[0]) {
                return -1;
            } else if (o1[0] < o2[0]) {
                return 1;
            } else {
                return o1[1] > o2[1] ? 1 : -1;
            }
        });
        for (int i = 1; i < people.length; i++) {
            if (i > people[i][1]) {
                //往前移 i-people[i][1]位
                int[] tmp = new int[]{people[i][0], people[i][1]};
                int index = people[i][1];
                int j = i - 1;
                for (; j >= index; j--) {
                    people[j + 1][0] = people[j][0];
                    people[j + 1][1] = people[j][1];
                }
                people[index][0] = tmp[0];
                people[index][1] = tmp[1];
            }
        }
        return people;
    }

    /**
     * 452. 用最少数量的箭引爆气球
     * 按照起始位置排序后 找的重叠区间的个数
     *
     * @param points
     * @return
     */
    public int findMinArrowShots(int[][] points) {
        if (points.length == 0) {
            return 0;
        }
        Arrays.sort(points, new myComparator());
        int result = 1;
        int end = points[0][1];
        for (int i = 1; i < points.length; i++) {
            //后一个与前面的没有重叠
            if (points[i][0] > end) {
                result++;
                end = points[i][1];
            } else {
                //后一个与前面的重叠且包含
                if (points[i][1] <= end) {
                    end = points[i][1];
                }
            }
        }
        return result;
    }

    /**
     * 435. 无重叠区间
     * 按照起始位置排序后 找无重叠区间的个数
     *
     * @param intervals
     * @return
     */
    public int eraseOverlapIntervals(int[][] intervals) {
        if (intervals.length == 0) {
            return 0;
        }
        Arrays.sort(intervals, new myComparator());
        int result = 0;
        int cur = 0;
        int next = 1;
        while (next < intervals.length) {
            if (intervals[next][0] >= intervals[cur][1]) {
                cur = next;
                next++;
            } else {
                if (intervals[next][1] <= intervals[cur][1]) {
                    cur = next;
                    next++;
                    result++;
                } else {
                    next++;
                    result++;
                }
            }
        }
        return result;
    }

    class myComparator implements Comparator<int[]> {
        @Override
        public int compare(int[] o1, int[] o2) {
            return o1[0] - o2[0];
        }
    }

    /**
     * 455. 分发饼干
     *
     * @param g
     * @param s
     * @return
     */
    public int findContentChildren(int[] g, int[] s) {
        if (g == null || s == null) {
            return 0;
        }
        Arrays.sort(g);
        Arrays.sort(s);
        int gi = 0;
        int si = 0;
        while (gi < g.length && si < s.length) {
            if (g[gi] <= s[si]) {
                gi++;
            }
            si++;
        }
        return gi;
    }

    /**
     * leetcode 122
     * 买股票问题
     * 今天比昨天高就卖
     * 明天比今天高就买
     *
     * @param prices
     * @return
     */
    public int maxProfit(int[] prices) {
        int maxProfit = 0;
        boolean holder = false;
        int mai = 0;
        for (int i = 0; i < prices.length; i++) {
            //先卖
            if (holder && prices[i] > mai) {
                holder = false;
                maxProfit += prices[i] - mai;
            }
            //在买
            if (!holder && i < prices.length - 1 && prices[i + 1] > prices[i]) {
                holder = true;
                mai = prices[i];
            }
        }
        return maxProfit;
    }

    /**
     * leetcode 134 加油站问题
     *
     * @param gas
     * @param cost
     * @return
     */
    public int canCompleteCircuit(int[] gas, int[] cost) {
        for (int start = 0; start < gas.length; start++) {
            if (gas[start] >= cost[start]) {
                int gasmun = 0;
                int next = start;
                while (gasmun >= 0) {
                    //试着走到下一个节点
                    gasmun += (gas[next] - cost[next]);
                    //能走到下一个节点
                    if (gasmun >= 0) {
                        next = (next + 1) % gas.length;
                        if (next == start) {
                            break;
                        }
                    }
                }
                if (next == start) {
                    return start;
                }
            }
        }
        return -1;
    }

    /**
     * 392 判断 s 是否为 t 的子序列
     *
     * @param s
     * @param t
     * @return
     */
    public boolean isSubsequence(String s, String t) {
        int i = 0;
        int j = 0;
        while (i < s.length() && j < t.length()) {
            if (s.charAt(i) == t.charAt(j)) {
                i++;
                j++;
            } else {
                j++;
            }
        }
        return i == s.length();
    }

    /**
     * 55. 跳跃游戏 pass
     *
     * @param nums
     * @return
     */
    public boolean canJump(int[] nums) {
        if (nums.length == 1) {
            return true;
        }
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] == 0) {
                boolean jump = false;
                for (int j = i - 1; j >= 0; j--) {
                    if (i == nums.length - 1 && j + nums[j] >= i) {
                        jump = true;
                        break;
                    } else if (j + nums[j] > i) {
                        jump = true;
                        break;
                    }
                }
                if (!jump) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * 45. 跳跃游戏 II
     * 回溯思想-暴力-超时
     *
     * @param nums
     * @return
     */
    public int jump(int[] nums) {
        if (nums.length == 1) {
            return 0;
        }
        return jumpNums(nums, 0);
    }

    private int jumpNums(int[] nums, int start) {
        if (start + nums[start] >= nums.length - 1) {
            return 1;
        }
        int min = Integer.MAX_VALUE - 1;
        for (int i = 1; i <= nums[start]; i++) {
            min = Math.min(min, 1 + jumpNums(nums, start + i));
        }
        return min;
    }

    /**
     * 376. 摆动序列
     * 计算波峰波谷的个数
     *
     * @param nums
     * @return
     */
    public int wiggleMaxLength(int[] nums) {
        if (nums.length < 2) {
            return nums.length;
        }
        //波峰波谷的个数
        int res = 1;
        // 上一步的走势
        int pre = 0;
        // 当前步的走势
        int cur = 0;
        for (int i = 1; i < nums.length; i++) {
            if (nums[i] != nums[i - 1]) {
                cur = nums[i] > nums[i - 1] ? 1 : -1;
                //遇到波峰波谷
                if (cur != pre) {
                    res++;
                }
                pre = cur;
            }
        }
        return res;
    }

    /**
     * 402. 移掉K位数字 no
     * 思路：先移除比后一位大的数  再移除和后一位相等的数
     *
     * @param num
     * @param k
     * @return
     */
    public String removeKdigits(String num, int k) {
        String[] split = num.split("");
        String res = "";
        int i = 0;
        //移除比后面大的
        for (i = 0; i < num.length() - 1 && k > 0; i++) {
            if (Integer.valueOf(split[i]) > Integer.valueOf(split[i + 1])) {
                k--;
                continue;
            }
            res += split[i];
        }
        if (i < num.length()) {
            res += num.substring(i);
        }
        //移除相等的
        if (k > 0) {
            String[] split1 = res.split("");
            String res1 = "";
            for (i = 0; i < split1.length - 1 && k > 0; i++) {
                if (Integer.valueOf(split1[i]).equals(Integer.valueOf(split1[i + 1]))) {
                    k--;
                    continue;
                }
                res1 += split1[i];
            }
            if (k > 0) {
                res1 = res.substring(0);
            }
            res = res1;
        }
        if (k > 0) {
            res = res.substring(k);
        }
        if (res.startsWith("0")) {
            res = res.replaceFirst("[0]{1,}", "");
        }
        if (res.equals("")) {
            return "0";
        }
        return res;
    }


}
