package leetcode.程序员面试金典;
//三步问题。有个小孩正在上楼梯，楼梯有n阶台阶，小孩一次可以上1阶、2阶或3阶。实现一种方法，计算小孩有多少种上楼梯的方式。结果可能很大，你需要对结果模1000000007。
//
//示例1:
// 输入：n = 3
// 输出：4
// 说明: 有四种走法
//
//示例2:
// 输入：n = 5
// 输出：13
//提示:
//
//n范围在[1, 1000000]之间
//
//来源：力扣（LeetCode）
//链接：https://leetcode.cn/problems/three-steps-problem-lcci
//著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。

import java.util.Arrays;

/**
 * @author mayanwei
 * @date 2023-05-23.
 */
public class _08_01_三步问题{
    static class Solution {
        private static int mod = 1000000007;
        int [] dp;
        public int waysToStep(int n) {
            dp = new int[n + 1];
            Arrays.fill(dp, -1);
            return step(n);
        }
        private int step(int n) {
            if (n < 0) {
                return 0;
            }
            if (n == 0) {
                return 1;
            }
            if (n == 1) {
                return 1;
            }
            if (dp[n] != -1) {
                return dp[n];
            }
            int n1 = step(n-1);
            int n2 = step(n-2);
            int n3 = step(n-3);
            dp[n] = (((n1 % mod) +( n2 % mod)) % mod + (n3 % mod)) % mod;
            return dp[n];
        }
    }


    /**
     * 上楼梯的总数很快会突破整数（int）的上限而移除，当n= 37时，结果就会溢出，使用long可以撑一会儿，不是根本方法
     * 可以使用BigInteger
     */
    static class Solution1 {
        public int waysToStep(int n) {
            int [] memo = new int[n+1];
            Arrays.fill(memo, -1);
            return countWays(n, memo);
        }

        private int countWays(int n, int[] memo) {
            if (n < 0) {
                return 0;
            }
            else if (n == 0) {
                return 1;
            }
            else if (memo[n] != -1) {
                return memo[n];
            } else {
                memo[n] = countWays(n-1, memo) + countWays(n-2, memo) + countWays(n-3, memo);
                return memo[n];
            }
        }
    }

}
