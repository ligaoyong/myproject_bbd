package com.leetcode;

/**
 * 动态规划算法
 *
 * @author ligaoyong@gogpay.cn
 * @date 2020/1/6 15:25
 */
public class dp {
    /**
     * 求最长回文字串
     *
     * @param s
     * @return
     */
    public String longestPalindrome0(String s) {
        if (s == null || "".equals(s) || s.length() == 1) {
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

    private String getString(String[] s, int left, int right) {
        String string = String.join("", s);
        String max = "";
        left = left < 0 ? 0 : left;
        right = right >= s.length ? s.length - 1 : right;
        max = s[left];
        for (; left >= 0 && right < s.length && s[left].equals(s[right]); --left, ++right) {
            max = string.substring(left, right + 1);
        }
        return max;
    }

    /**
     * 最长回文子串: 动态规划写法
     * leetcode 通过
     *
     * @param s
     * @return
     */
    public String longestPalindrome(String s) {
        if (s == null || "".equals(s) || s.length() == 1) {
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

        for (int i = 1; i < s.length(); i++) {
            for (int j = 0; j < i; j++) {
                //这里相当于列举所有的情况 然后检查他是不是回文 如果不用动态规划 去检查回文还得用for 算法时间复杂度就是n的3次方
                if (s.charAt(j) == s.charAt(i)) {
                    //首尾字符相等 是不是回文子串取决于去掉收尾字串是不是回文 也即dp[j+1][i-1]
                    if (i - j < 3) {
                        //j-i之间只有3个字符 也即去掉收尾字符 只剩下一个了 这种情况肯定是回文
                        dp[j][i] = true;
                    } else {
                        //取决于去掉收尾字串是不是回文
                        dp[j][i] = dp[j + 1][i - 1];
                    }
                } else {
                    // 首位字符不相等 肯定不是回文
                    dp[j][i] = false;
                }

                //检查j-i是不是回文
                if (dp[j][i]) {
                    int curlenth = i - j + 1;
                    if (curlenth > length) {
                        length = curlenth;
                        start = j;
                    }
                }
            }
        }

        return s.substring(start, start + length);
    }

    /**
     * 正则表达式匹配
     *
     * @param s
     * @param p
     * @return
     */
    public boolean isMatch(String s, String p) {

        return true;
    }

    /**
     * 最大子序列和 leetcode 53题  还有两个测试用例没有通过 内存移除
     *
     * @param nums
     * @return
     */
    public int maxSubArray0(int[] nums) {
        if (nums.length < 2) {
            return nums[0];
        }
        //定义dp
        int[][] dp = new int[nums.length][nums.length];

        //初始化
        int max = Integer.MIN_VALUE;
        for (int i = 0; i < nums.length; i++) {
            dp[i][i] = nums[i];
            if (dp[i][i] > max) {
                max = dp[i][i];
            }
        }
        //状态转移
        for (int j = 1; j < nums.length; j++) {
            for (int i = 0; i < j; i++) {
                if (j - i < 2) {
                    dp[i][j] = nums[i] + nums[j];
                } else {
                    dp[i][j] = nums[i] + dp[i + 1][j - 1] + nums[j];
                }
                if (dp[i][j] > max) {
                    max = dp[i][j];
                }
            }
        }
        return max;
    }

    /**
     * 最大子序列和 leetcode 53题
     * 修改原数组的值
     *
     * @param nums
     * @return
     */
    public int maxSubArray(int[] nums) {
        if (nums.length < 2) {
            return nums[0];
        }
        int max = nums[0];
        for (int i = 1; i < nums.length; ++i) {
            //nums[i]表示在前i个元素中的最大子序和
            if (nums[i - 1] > 0) {
                nums[i] = nums[i] + nums[i - 1];
            }
            max = Math.max(nums[i], max);
        }
        return max;
    }

    /**
     * 爬楼梯问题 leetcode 70
     *
     * @param n
     * @return
     */
    public int climbStairs(int n) {
        if (n == 1) {
            return 1;
        }
        if (n == 2) {
            return 2;
        }
        int[] num = new int[n + 1];
        num[1] = 1;
        num[2] = 2;
        for (int i = 3; i <= n; i++) {
            num[i] = num[i - 1] + num[i - 2];
        }
        return num[n];
    }

    /**
     * 不同路径 leetcode 62
     * 动态规划 ok
     *
     * @param m
     * @param n
     * @return
     */
    public int uniquePaths(int m, int n) {
        // n是行 m是列
        int h = n;
        int l = m;
        int[][] dp = new int[h][l];

        //初始化
        for (int i = 0; i < h; i++) {
            dp[i][0] = 1;
        }
        for (int i = 0; i < l; i++) {
            dp[0][i] = 1;
        }
        //状态转移
        for (int i = 1; i < h; i++) {
            for (int j = 1; j < l; j++) {
                dp[i][j] = dp[i - 1][j] + dp[i][j - 1];
            }
        }
        return dp[h - 1][l - 1];
    }

    /**
     * 不同路径 leetcode 63 有障碍物 ok
     *
     * @param obstacleGrid
     * @return
     */
    public int uniquePathsWithObstacles(int[][] obstacleGrid) {
        if (obstacleGrid[0][0] == 1) {
            return 0;
        }
        // n是行 m是列
        int h = obstacleGrid.length;
        int l = obstacleGrid[0].length;
        int[][] dp = new int[h][l];
        dp[0][0] = 1;

        //初始化
        for (int i = 1; i < h; i++) {
            if (obstacleGrid[i][0] == 1) {
                dp[i][0] = 0;
            } else if (obstacleGrid[i - 1][0] != 1) {
                dp[i][0] = 1;
            } else {
                // 上方有障碍物
                obstacleGrid[i][0] = 1;
            }
        }
        for (int i = 1; i < l; i++) {
            if (obstacleGrid[0][i] == 1) {
                dp[0][i] = 0;
            } else if (obstacleGrid[0][i - 1] != 1) {
                dp[0][i] = 1;
            } else {
                obstacleGrid[0][i] = 1;
            }
        }
        //状态转移
        for (int i = 1; i < h; i++) {
            for (int j = 1; j < l; j++) {
                if (obstacleGrid[i][j] == 1) {
                    dp[i][j] = 0;
                } else {
                    //上方是否有障碍物
                    if (obstacleGrid[i - 1][j] != 1) {
                        dp[i][j] += dp[i - 1][j];
                    }
                    //前方是否有障碍物
                    if (obstacleGrid[i][j - 1] != 1) {
                        dp[i][j] += dp[i][j - 1];
                    }
                }
            }
        }
        return dp[h - 1][l - 1];
    }

    /**
     * 最小路径和  leetcode 64
     *  动态规划
     * @param grid
     * @return
     */
    public int minPathSum(int[][] grid) {
        int h = grid.length;
        int l = grid[0].length;
        //初始化列
        for (int i = 1; i < h; i++) {
            grid[i][0] += grid[i - 1][0];
        }
        //初始化行
        for (int i = 1; i < l; i++) {
            grid[0][i] += grid[0][i-1];
        }

        //执行状态转移
        for (int i = 1; i < h; i++) {
            for (int j = 1; j < l; j++) {
                grid[i][j] += Math.min(grid[i][j - 1], grid[i - 1][j]);
            }
        }
        return grid[h-1][l-1];
    }

    public static void main(String[] args) {
        Solution solution = new Solution();
        int[][] arg = {{0, 1, 0, 0, 0}, {1, 0, 0, 0, 0}, {0, 0, 0, 0, 0}, {0, 0, 0, 0, 0}};
        solution.uniquePathsWithObstacles(arg);
    }
}
