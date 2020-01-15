package com.test;

/**
 * 华为算法题
 */
public class Main {
    public static void main(String[] args) {
        /*Scanner in = new Scanner(System.in);
        while (in.hasNextInt()) {// 注意，如果输入是多个测试用例，请通过while循环处理多个测试用例
            String input = in.nextLine();
            String[] s = input.split(" ");
            add(s[0], s[1]);
        }*/
        System.out.println(add("0","0"));
    }

    public static String add(String a,String b){
        char[] aChars = a.toCharArray();
        char[] bChars = b.toCharArray();
        //转换成十进制
        int aNums = 0;
        for (int i=0;i < aChars.length;i++){
            aNums +=  Integer.valueOf(String.valueOf(aChars[aChars.length -1 - i])) * Math.pow(7, Double.valueOf(String.valueOf(i)));
        }
        //转换成十进制
        int bNums = 0;
        for (int i=0;i < bChars.length;i++){
            bNums +=  Integer.valueOf(String.valueOf(bChars[bChars.length -1 - i])) * Math.pow(7, Double.valueOf(String.valueOf(i)));
        }
        int sum = aNums + bNums;
        if (sum == 0){
            return "0";
        }
        //十进制转七进制
        String result = "";
        double yu = sum;
        while (yu != 0){
            if (yu < 7.0){
                result = String.valueOf(new Double(yu).intValue()) + result;
                break;
            }else {
                result = String.valueOf(new Double(yu % 7).intValue()) + result;
                yu /= 7;
            }

        }
        return result;
    }
}
