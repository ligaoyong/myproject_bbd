package com.leetcode;

import com.google.common.collect.Lists;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 回溯算法 ：适合找出所有解的问题 本质上是DFS算法  也即深度优选算法
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

    /**
     * 组合总和
     *
     * @param candidates
     * @param target
     * @return
     */
    public List<List<Integer>> combinationSum(int[] candidates, int target) {
        List<List<Integer>> res = new ArrayList<>();
        ArrayList<Integer> selected = new ArrayList<>();
        backtrack(candidates, selected, target, 0, res);
        return res;
    }

    private void backtrack(int[] candidate, ArrayList<Integer> selected, int tartget, int start, List<List<Integer>> res) {
        //找到递归出口，也就是找到一个解
        if (selected.stream().reduce(0, Integer::sum) == tartget) {
            ArrayList<Integer> each = new ArrayList<>(selected);
            each.sort(Comparator.naturalOrder());
            //查找是否有重复
            List<List<Integer>> collect = res.stream().filter(item -> {
                if (item.size() == each.size()) {
                    for (int i = 0; i < item.size(); i++) {
                        if (!Objects.equals(item.get(i), each.get(i))) {
                            return false;
                        }
                    }
                    return true;
                } else {
                    return false;
                }
            }).collect(Collectors.toList());
            //结果集中没有这次的结果
            if (collect.size() == 0) {
                res.add(each);
            }
            return;
        }
        //遍历每个选择 每一层的候选起始位置由上一层指定
        for (int i = start; i < candidate.length; i++) {
            //判断选择条件
            if (selected.stream().reduce(0, Integer::sum) + candidate[i] <= tartget) {
                //做出选择
                selected.add(candidate[i]);
                //递归 指定下一层的起始位置 由于candidate的元素可以重复使用 所有每次都遍历所有
                backtrack(candidate, selected, tartget, start, res);
                //回溯 换同一层候选序列的其他选择 就像一个多叉树
                selected.remove(selected.size() - 1);
            }
        }
    }

    /**
     * 60 第n个排列
     *
     * @param n
     * @param k
     * @return
     */
    public String getPermutation(int n, int k) {
        List<String> res = new ArrayList<>();
        List<Integer> candidate = new ArrayList<>(n);
        for (int i = 1; i <= n; i++) {
            candidate.add(i);
        }
        backtrack(candidate, "", n, k, res);
        return res.get(0);
    }

    Integer nums = 0;

    private void backtrack(List<Integer> candidate, String tmp, int n, int k, List<String> res) {
        if (tmp.length() == n) {
            nums++;
            if (nums == k) {
                res.add(tmp);
            }
            return;
        }
        //候选序列每次都固定 当然也可以枝减掉已经在tmp中的
        for (Integer integer : candidate) {
            if (!tmp.contains(String.valueOf(integer))) {
                String tmp1 = tmp;
                //做出选择
                tmp += String.valueOf(integer);
                //递归网下一层 更改选择条件
                backtrack(candidate, tmp, n, k, res);
                //选择性回溯 选择选一个选项
                if (nums == k) {
                    break;
                }
                tmp = tmp1;
            }

        }
    }

    /**
     * 77. 组合   超时
     *
     * @param n
     * @param k
     * @return
     */
    public List<List<Integer>> combine(int n, int k) {
        List<List<Integer>> res = new ArrayList<>();
        List<Integer> tmp = new ArrayList<>();
        backtrack(n, k, 1, tmp, res);
        return res;
    }

    private void backtrack(int n, int k, int begin, List<Integer> tmp, List<List<Integer>> res) {
        if (k == 0) {
            ArrayList<Integer> each = new ArrayList<>(tmp);
            res.add(each);
            return;
        }
        for (int i = begin; i <= n; i++) {
            tmp.add(i);
            //begin设置为下一个数  这样就不会重复了 也不用判断条件了
            backtrack(n, k - 1, i + 1, tmp, res);
            tmp.remove(tmp.size() - 1);
        }
    }

    /**
     * leetcode 37 解数独
     * @param board
     */
//    public void solveSudoku(char[][] board) {
//        // 记录每个坐标填如的值
//        Map<String, Character> selected = new HashMap<>();
//        int blank = 0;
//        for (int i = 0;i<board.length;i++){
//            for (int j=0;j<board[i].length;j++){
//                if (board[i][j] == '.') {
//                    blank += 1;
//                }
//            }
//        }
//        char[] candidate = {'1','2','3','4','5','6','7','8','9'};
//        backtrack(board,candidate,selected,blank,0,0);
//    }
//
//    private void backtrack(char[][] board,char[] candidate,Map<String, Character> selected,int blank,int startRow,int startColume){
//        //1、找出递归出口  也就是找到解
//        if (selected.size() == blank){
//            return;
//        }
//        //编列可选序列
//        for (int i = startRow;i<board.length;i++){
//            for (int j=startColume;j<board[i].length;j++){
//                //找到待处理的位置
//                if (board[i][j] == '.' && !selected.containsKey(i+","+"j")) {
//                    //遍历选择
//                    for (char c : candidate){
//                        if (valid(board,selected,c,i,j)){
//                            //做出选择
//                            selected.put(i + "," + "j", c);
//                            //递归
//                            backtrack(board,candidate,selected,blank,startRow,startColume);
//                            if (selected.size() == blank){
//                                return;
//                            }
//                            //回溯
//                            selected.remove(i + "," + "j", c);
//                        }
//                    }
//                }
//            }
//        }
//
//    }
//
//    /**
//     *
//     * @param board 数独原图
//     * @param selected  已经填如的信息
//     * @param c 填如的数字
//     * @param i 行
//     * @param j 列
//     * @return
//     */
//    private boolean valid(char[][] board, Map<String, Character> selected, char c, int i, int j) {
//        return false;
//    }
}
