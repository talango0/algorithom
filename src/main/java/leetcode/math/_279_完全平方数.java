package leetcode.math;
//给你一个整数 n ，返回 和为 n 的完全平方数的最少数量 。
//
//完全平方数 是一个整数，其值等于另一个整数的平方；换句话说，其值等于一个整数自乘的积。例如，1、4、9 和 16 都是完全平方数，而 3 和 11 不是。
//
//
//
//示例1：
//
//输入：n = 12
//输出：3 
//解释：12 = 4 + 4 + 4
//示例 2：
//
//输入：n = 13
//输出：2
//解释：13 = 4 + 9
//
//提示：
//
//1 <= n <= 104

import java.util.Arrays;

/**
 * @see _264_丑数II
 * @see _204_计数质数
 */
public class _279_完全平方数 {
    class Solution {
        /**
         * dp[i] 表示最少要多少个数的平方来表示整数 i.
         * 这些数必然落在 [1, sqrt(n)]
         * <p>
         * 时间辅助度 O(n*sqrt(n))
         * 空间复杂度 o(n)
         */
        public int numSquares(int n) {
            int[] dp = new int[n + 1];
            Arrays.fill(dp, Integer.MAX_VALUE);
            dp[0] = 0;
            for (int i = 1; i <= n; i++) {
                for (int j = 1; j * j <= i; j++) {
                    dp[i] = Math.min(dp[i], dp[i - j * j] + 1);
                }
            }
            return dp[n];
        }
    }
}
