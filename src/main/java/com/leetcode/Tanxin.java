package com.leetcode;

/**
 * 贪心算法
 */
public class Tanxin {
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
        while ( i < s.length() && j < t.length()){
            if (s.charAt(i) == t.charAt(j)){
                i++;
                j++;
            }else {
                j++;
            }
        }
        return i == s.length();
    }

    public static void main(String[] args) {
        Tanxin tanxin = new Tanxin();
        int[] gas = {3, 3, 4};
        int[] cost = {3, 4, 4};
        tanxin.canCompleteCircuit(gas, cost);
    }
}
