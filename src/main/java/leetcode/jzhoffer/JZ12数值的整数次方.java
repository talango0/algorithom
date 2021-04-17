package leetcode.jzhoffer;


/***
 * 题目描述
 * 给定一个double类型的浮点数base和int类型的整数exponent。求base的exponent次方。
 *
 * 保证base和exponent不同时为0
 * 示例1
 * 输入

 * 2,3
 * 返回值

 * 8.00000
 */
public class JZ12数值的整数次方 {




    public class Solution {
        public double Power(double base, int exponent) {
            if(exponent==0){
                return 1.0;
            }else if(exponent == 1){
                return base;
            }
            if(base == 0){
                return 0;
            }
            float res = 1.0f;
            for(int i=0,n = Math.abs(exponent); i<n; i++){
                res *= base;
            }
            if(exponent<0){
                return 1.0/res;
            }
            return res;
        }
    }


    public class Solution2 {
        public double Power(double base, int exponent) {
            if(exponent<0){
                base = 1/base;
                exponent = -exponent;
            }
            double total = 1.0d;
            if(exponent>0){
                for (; exponent >=1 ; exponent--) {
                    total *=base;
                }

            }
            return total;
        }
    }
}
