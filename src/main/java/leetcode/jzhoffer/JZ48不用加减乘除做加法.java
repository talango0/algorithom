package leetcode.jzhoffer;

public class JZ48不用加减乘除做加法 {
    /*

    题目描述
写一个函数，求两个整数之和，要求在函数体内不得使用+、-、*、/四则运算符号。
     */

    public static class Solution {
        public int Add(int num1,int num2) {
            int carray = 0;
            int result = 0;
            do{
                //不带进位的加法
                result = num1 ^ num2;
                //进位
                carray = (num1 & num2) <<1;
                num1 = result;
                num2 = carray;
            }while (carray != 0);
            return result;
        }
    }
    public static void main(String[] args) {
        Solution solution = new Solution();
        System.out.println(solution.Add(-1,2));
    }
}
