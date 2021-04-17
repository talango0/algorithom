package leetcode.jzhoffer;


/**
 * 题目描述
 * 给你一根长度为n的绳子，请把绳子剪成整数长的m段（m、n都是整数，n>1并且m>1，m<=n），每段绳子的长度记为k[1],...,k[m]。
 * 请问k[1]x...xk[m]可能的最大乘积是多少？例如，当绳子的长度是8时，我们把它剪成长度分别为2、3、3的三段，此时得到的最大乘积是18。
 * 输入描述:
 * 输入一个数n，意义见题面。（2 <= n <= 60）
 * 返回值描述:
 * 输出答案。
 * 示例1
 * 输入
 * 复制
 * 8
 * 返回值
 * 复制
 * 18
 */
public class JZ67剪绳子 {

    public class Solution {
        public int cutRope(int target) {
            if(target<=0) {
                return 0;
            }
            if(target==1 || target == 2) {
                return 1;
            }
            if(target==3) {
                return 2;
            }
            int m = target % 3;
            switch(m){
                case 0 :
                    return (int) Math.pow(3, target / 3);
                case 1 :
                    return (int) Math.pow(3, target / 3 - 1) * 4;
                case 2 :
                    return (int) Math.pow(3, target / 3) * 2;
            }
            return 0;
        }
    }
}
