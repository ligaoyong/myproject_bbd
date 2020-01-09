package com.leetcode;

import com.google.common.collect.Lists;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 回溯算法 ：适合找出所有解的问题
 * <p>
 * 有模板的
 * backtrack(int[] candidates,List selected,List result){
 * // 1、根据candidates和path的关系查看是否有解
 * if(has_result){
 * //加入结果集
 * result.add(path);
 * }
 * // 2、变量候选集做出选择
 * for(int candidate : candidates){
 * //3、判断条件候选者candidate是否合法
 * if(isValid(candidate)){
 * //4、做出选择 将选择candidate加入到selected(已经选择的)
 * selected.add(candidate)
 * // 5、递归继续做选择
 * backtrack(int[] candidates,List selected,List result)
 * // 6、回溯 去掉刚刚的选择 for循环继续下一个选择
 * selected.add(selected.remove(selected.size()-1))
 * }
 * }
 * }
 * <p>
 * result = []
 * def backtrack(路径, 选择列表):
 * if 满足结束条件:
 * result.add(路径)
 * return
 * <p>
 * for 选择 in 选择列表:
 * 做选择
 * backtrack(路径, 选择列表)
 * 撤销选择
 *
 * @author ligaoyong@gogpay.cn
 * @date 2020/1/9 15:33
 */
public class Backtrack {
    public static void main(String[] args) {
        Backtrack backtrack = new Backtrack();
        List<String> strings = backtrack.letterCombinations("23");
        System.out.println(strings);
    }

    /**
     * leetcode 51 皇后问题 n x n棋盘
     *
     * @param n
     * @return
     */
    public List<List<String>> solveNQueens(int n) {
        //保存结果
        ArrayList<List<String>> result = new ArrayList<>();
        //保存每一步选择的结果
        ArrayList<String> selected = new ArrayList<>();
        backtrack(0, n, selected, result);
        //处理结果
        List<List<String>> collect = result.stream().map(each -> {
            each.sort(Comparator.naturalOrder());
            return each.stream().map(s -> {
                String col = s.split("-")[1];
                String[] item = new String[n];
                for (int i = 0; i < item.length; i++) {
                    if (Integer.valueOf(col) == i) {
                        item[i] = "Q";
                    } else {
                        item[i] = ".";
                    }
                }
                return String.join("", item);
            }).collect(Collectors.toList());
        }).collect(Collectors.toList());
        return collect;
    }

    /**
     * 回溯算法都是一个套路
     *
     * @param n
     * @param selected
     * @param result
     */
    private void backtrack(int rowStart, int n, ArrayList<String> selected, ArrayList<List<String>> result) {
        //得到一个解
        if (selected.size() == n) {
            result.add(new ArrayList<>(selected));
        }
        for (int colume = 0; colume < n; colume++) {
            //判断是否可以选择
            if (isValid(rowStart, colume, selected, n)) {
                //做出选择
                selected.add(rowStart + "-" + colume);
                //递归  在该次选择的基础上继续进行选择
                backtrack(rowStart + 1, n, selected, result);
                //回溯
                selected.remove(selected.size() - 1);
            }
        }
    }

    /**
     * 更具已选择的位置 查看当前放置的行能不能有效 （不被其他皇后攻击到）
     *
     * @param row
     * @param selected
     * @return
     */
    private boolean isValid(int row, int colume, ArrayList<String> selected, int n) {
        boolean valid = true;
        for (String s : selected) {
            String[] split = s.split("-");
            Integer selectedRow = Integer.valueOf(split[0]);
            Integer selectedColume = Integer.valueOf(split[1]);
            //同行同列不合法
            if (row == selectedRow || colume == selectedColume) {
                valid = false;
                break;
            }
            /*校验对角线*/
            int x = selectedRow;
            int y = selectedColume;
            //左上对角线
            while (x >= 0 && y >= 0 && x < n && y < n) {
                if (x == row && y == colume) {
                    valid = false;
                    break;
                }
                --x;
                --y;
            }
            if (!valid) {
                return valid;
            }
            //左上对角线
            x = selectedRow;
            y = selectedColume;
            while (x >= 0 && y >= 0 && x < n && y < n) {
                if (x == row && y == colume) {
                    valid = false;
                    break;
                }
                --x;
                ++y;
            }
            if (!valid) {
                return valid;
            }

            x = selectedRow;
            y = selectedColume;
            while (x >= 0 && y >= 0 && x < n && y < n) {
                if (x == row && y == colume) {
                    valid = false;
                    break;
                }
                ++x;
                --y;
            }
            if (!valid) {
                return valid;
            }
            x = selectedRow;
            y = selectedColume;
            while (x >= 0 && y >= 0 && x < n && y < n) {
                if (x == row && y == colume) {
                    valid = false;
                    break;
                }
                ++x;
                ++y;
            }
        }
        return valid;
    }


    /**
     * leetcode 46 全排列问题
     *
     * @param nums
     * @return
     */
    public List<List<Integer>> permute(int[] nums) {
        //保存所有结果
        ArrayList<List<Integer>> result = new ArrayList<>();
        //保存选择的结果
        ArrayList<Integer> selected = new ArrayList<>();
        backtrack1(nums, selected, result);
        return result;
    }

    /**
     * 所有回溯都遵循下面的模板代码
     *
     * @param nums     候选序列
     * @param selected 已选择序列
     */
    private void backtrack1(int[] nums, List<Integer> selected, ArrayList<List<Integer>> result) {
        //递归出口  代表获得一个解了
        if (selected.size() == nums.length) {
            result.add(new ArrayList<>(selected));
            return;
        }
        // 便利候选序列
        for (int i = 0; i < nums.length; i++) {
            //条件满足
            if (!selected.contains(nums[i])) {
                //做出选择
                selected.add(nums[i]);
                //递归继续往下做选择 ------如果选择有解 会被加入到result中
                backtrack1(nums, selected, result);
                //回退-回溯  ---------撤销刚刚做的选择(如果选择有解 会被加入到result中) for循环以后做另外一个选择
                selected.remove(selected.size() - 1);
            }
        }
    }


    /**
     * leetcode 17 回溯   电话号码的字母组合
     *
     * @param digits
     * @return
     */
    public List<String> letterCombinations(String digits) {
        ArrayList<String> res = new ArrayList<>();
        if (digits.length() == 0){
            return res;
        }
        String tmp = "";
        HashMap<String, String> map = new HashMap<>();
        map.put("2", "abc");
        map.put("3", "def");
        map.put("4", "ghi");
        map.put("5", "jkl");
        map.put("6", "mno");
        map.put("7", "pqrs");
        map.put("8", "tuv");
        map.put("9", "wxyz");
        String[] values = new String[digits.length()];
        for (int i = 0; i < digits.length(); i++) {
            values[i] = map.get(String.valueOf(digits.charAt(i)));
        }
        backtrack(values, tmp, res, 0);
        return res;
    }

    private void backtrack(String[] digits, String tmp, ArrayList<String> res, int start) {
        //1、先找递归出口
        if (digits.length == tmp.length()) {
            res.add(new String(tmp.toCharArray()));
            return;
        }
        //选择所有条件
        for (int i = 0; i < digits[start].length(); i++) {
            // 选择条件
            tmp += digits[start].charAt(i);
            //递归 一层一层往下找 知道找到递归出口
            backtrack(digits, tmp, res, start + 1);
            //回溯 换同一层的下一个
            tmp = tmp.substring(0, tmp.length() - 1);

        }
    }
}
