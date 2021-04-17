package leetcode.jzhoffer;
/*
题目描述
大家都知道斐波那契数列，现在要求输入一个整数n，请你输出斐波那契数列的第n项（从0开始，第0项为0，第1项是1）。
n ≤ 39

示例1
输入
4
返回值
3
 */
public class JZ7斐波那契数列 {
    static class Solution {
        /**
         * f(n) = f(n-1) + f(n-2) ;n>= 2
         * f(1) = 1;
         * f(0) = 0;
         *
         */
        public int Fibonacci(int n) {
            if(n == 1){
                return 0;
            }
            if(n == 2){
                return 1;
            }
            int a0 = 0;
            int a1 = 1;
            int tmp;
            for(int i = 2; i <= n; i++){
                tmp = a0 + a1;
                a0 = a1;
                a1 = tmp;
            }
            return a1;
        }
    }
    static class Solution2 {
        /**
         * 递归
         * f(n) = f(n-1) + f(n-2) ;n>= 2
         * f(1) = 1;
         * f(0) = 0;
         *
         */
        public int Fibonacci(int n) {
            if(n == 0) {
                return 0;
            }
            if(n == 1) {
                return 1;
            }
            return Fibonacci(n-1) + Fibonacci(n-2);
        }
    }


    public static void main(String[] args) {
        int fibonacci = new Solution().Fibonacci(4);
    }
}
