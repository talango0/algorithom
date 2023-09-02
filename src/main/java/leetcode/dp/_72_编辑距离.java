package leetcode.dp;
//给你两个单词 word1 和 word2， 请返回将 word1 转换成 word2 所使用的最少操作数 。
// 你可以对一个单词进行如下三种操作：
// 插入一个字符
// 删除一个字符
// 替换一个字符
//
// 示例 1：
//输入：word1 = "horse", word2 = "ros"
//输出：3
//解释：
//horse -> rorse (将 'h' 替换为 'r')
//rorse -> rose (删除 'r')
//rose -> ros (删除 'e')
//
//
// 示例 2：
//输入：word1 = "intention", word2 = "execution"
//输出：5
//解释：
//intention -> inention (删除 't')
//inention -> enention (将 'i' 替换为 'e')
//enention -> exention (将 'n' 替换为 'x')
//exention -> exection (将 'n' 替换为 'c')
//exection -> execution (插入 'u')
//
//
// 提示：
//
//
// 0 <= word1.length, word2.length <= 500
// word1 和 word2 由小写英文字母组成
//
//
// Related Topics 字符串 动态规划 👍 2518 👎 0

import java.util.Arrays;

/**
 * 字节
 *
 * @author mayanwei
 * @date 2022-08-04.
 * @see _1143_最长公共子序列
 * @see _712_两个字符串的最小ASCII删除和
 * @see _583_两个字符串的删除操作
 */
public class _72_编辑距离{
    // 暴力穷举 超时
    class Solution{

        public int minDistance(String s1, String s2) {
            int m = s1.length(), n = s2.length();
            // i, j初始化指向一个索引
            return dp(s1, m - 1, s2, n - 1);
        }

        private int dp(String s1, int i, String s2, int j) {
            // base case
            // i 走完s1 或者 j 走完s2,可以直接返回另一个字符串剩下的长度
            if (i == -1) {
                return j + 1;
            }
            if (j == -1) {
                return i + 1;
            }

            if (s1.charAt(i) == s2.charAt(j)) {
                // 啥都不做
                return dp(s1, i - 1, s2, j - 1);
            }
            return min(
                    dp(s1, i, s2, j - 1) + 1, // 插入
                    dp(s1, i - 1, s2, j) + 1, // 删除
                    dp(s1, i - 1, s2, j - 1) + 1 // 替换
            );
        }

        private int min(int a, int b, int c) {
            return Math.min(a, Math.min(b, c));
        }
    }

    // 备忘录，避免重叠子问题重复计算
    class Solution2{
        int[][] memo;

        public int minDistance(String s1, String s2) {
            int m = s1.length(), n = s2.length();
            memo = new int[m][n];
            for (int i = 0; i < m; i++) {
                Arrays.fill(memo[i], -1);
            }
            // i, j初始化指向一个索引
            return dp(s1, m - 1, s2, n - 1);
        }

        // 定义： 返回 s1[0..i] 和 s2[0..j] 的最小编辑距离
        private int dp(String s1, int i, String s2, int j) {
            // base case
            // i 走完s1 或者 j 走完s2,可以直接返回另一个字符串剩下的长度
            if (i == -1) {
                return j + 1;
            }
            if (j == -1) {
                return i + 1;
            }
            if (memo[i][j] != -1) {
                return memo[i][j];
            }
            if (s1.charAt(i) == s2.charAt(j)) {
                // 啥都不做
                memo[i][j] = dp(s1, i - 1, s2, j - 1);
            }
            else {
                memo[i][j] = min(
                        dp(s1, i, s2, j - 1) + 1, // 插入
                        dp(s1, i - 1, s2, j) + 1, // 删除
                        dp(s1, i - 1, s2, j - 1) + 1 // 替换
                );
            }
            return memo[i][j];
        }

        private int min(int a, int b, int c) {
            return Math.min(a, Math.min(b, c));
        }
    }


    // 自低向上解法，动态规划

    class Solution3{

        public int minDistance(String s1, String s2) {
            int m = s1.length(), n = s2.length();
            // dp[i+1][j+1] 存储 s1[0..i] 和 s2[0..j] 的最小编辑距离
            int[][] dp = new int[m + 1][n + 1];
            //base case
            for (int i = 1; i <= m; i++) {
                dp[i][0] = i;
            }
            for (int j = 1; j <= n; j++) {
                dp[0][j] = j;
            }

            // 自低向上求解
            for (int i = 1; i <= m; i++) {
                for (int j = 1; j <= n; j++) {
                    if (s1.charAt(i - 1) == s2.charAt(j - 1)) {
                        dp[i][j] = dp[i - 1][j - 1];
                    }
                    else {
                        dp[i][j] = min(
                                dp[i - 1][j] + 1, // 删除
                                dp[i][j - 1] + 1, // 插入
                                dp[i - 1][j - 1] + 1// 替换
                        );
                    }
                }
            }
            // 存储这整个 s1 s2 的最小编辑距离
            return dp[m][n];
        }

        private int min(int a, int b, int c) {
            return Math.min(a, Math.min(b, c));
        }
    }


}
