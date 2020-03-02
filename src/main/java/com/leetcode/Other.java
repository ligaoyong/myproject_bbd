package com.leetcode;

/**
 * 其他题目
 *
 * @author ligaoyong@gogpay.cn
 * @date 2020/1/15 9:15
 */
public class Other {

    /**
     * 6. Z 字形变换
     * @param s
     * @param numRows
     * @return
     */
    public String convert(String s, int numRows) {
        if (numRows == 1){
            return s;
        }
        String[] res = new String[numRows];
        for (int i=0;i<res.length;i++) {
            res[i] = "";
        }
        int index = 0;
        boolean add = true;
        for (String s1:s.split("")) {
            if (index < 0 ){
                index = 1;
                add = true;
            }
            if (index == res.length){
                index = res.length -2;
                add = false;
            }
            res[index] += s1;
            if (add){
                index++;
            }else {
                index--;
            }
        }
        return String.join("", res);
    }

}
