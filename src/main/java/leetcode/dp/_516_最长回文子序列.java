package leetcode.dp;
//给你一个字符串 s ，找出其中最长的回文子序列，并返回该序列的长度。
// 子序列定义为：不改变剩余字符顺序的情况下，删除某些字符或者不删除任何字符形成的一个序列。
//
//
// 示例 1：
//输入：s = "bbbab"
//输出：4
//解释：一个可能的最长回文子序列为 "bbbb" 。
//
//
// 示例 2：
//输入：s = "cbbd"
//输出：2
//解释：一个可能的最长回文子序列为 "bb" 。
//
//
// 提示：
//
//
// 1 <= s.length <= 1000
// s 仅由小写英文字母组成
//
//
// Related Topics 字符串 动态规划 👍 851 👎 0

import leetcode.string._5_最长回文串;

/**
 * @see _5_最长回文串
 */
public class _516_最长回文子序列 {
    class Solution {
        // 在子串 s[i,...,j]中，最长回文子序列长度为 dp[i][j]
        // dp[i][j] = dp[i+1][j-1] + 2， 若 s[i] = s[j]
        // dp[i][j] = max(dp[i+1][j], dp[i][j-1])，若 s[i] != s[j]
        public int longestPalindromeSubseq(String s) {
            int n = s.length();
            // 数组全部初始化为0
            int [][] dp = new int[n][n];
            // base case
            for (int i = 0; i<n; i++) {
                dp[i][i] = 1;
            }
            // 反着遍历保存正确的的状态转移
            for (int i = n-1; i >=0; i--) {
                for (int j = i+1; j<n; j++) {
                    // 状态转移方程
                    if (s.charAt(i) == s.charAt(j)) {
                        dp[i][j] = dp[i+1][j-1] + 2;
                    }
                    else {
                        dp[i][j] = Math.max(dp[i + 1][j], dp[i][j - 1]);
                    }
                }
            }
            // 整个 s 的最长回文子序列长度
            return dp[0][n - 1];
        }
    }
}
