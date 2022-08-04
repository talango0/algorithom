package leetcode.dp;
//给定两个字符串 text1 和 text2，返回这两个字符串的最长 公共子序列 的长度。如果不存在 公共子序列 ，返回 0 。
//
// 一个字符串的 子序列 是指这样一个新的字符串：它是由原字符串在不改变字符的相对顺序的情况下删除某些字符（也可以不删除任何字符）后组成的新字符串。
//
//
// 例如，"ace" 是 "abcde" 的子序列，但 "aec" 不是 "abcde" 的子序列。
//
//
// 两个字符串的 公共子序列 是这两个字符串所共同拥有的子序列。
//
//
//
// 示例 1：
//
//
//输入：text1 = "abcde", text2 = "ace"
//输出：3
//解释：最长公共子序列是 "ace" ，它的长度为 3 。
//
//
// 示例 2：
//
//
//输入：text1 = "abc", text2 = "abc"
//输出：3
//解释：最长公共子序列是 "abc" ，它的长度为 3 。
//
//
// 示例 3：
//
//
//输入：text1 = "abc", text2 = "def"
//输出：0
//解释：两个字符串没有公共子序列，返回 0 。
//
//
//
//
// 提示：
//
//
// 1 <= text1.length, text2.length <= 1000
// text1 和 text2 仅由小写英文字符组成。
//
//
// Related Topics 字符串 动态规划 👍 1076 👎 0

import java.util.Arrays;

/**
 * @see _712_两个字符串的最小ASCII删除和
 * @see _583_两个字符串的删除操作
 * @author mayanwei
 * @date 2022-08-04.
 */
public class _1143_最长公共子序列{
    class Solution {
        // 对于两个字符串求子序列问题，都是用两个指针 i 和 j 分别在两个字符串上移动，大概率是动态规划思路
        // 定义：计算 s1[i..] 和 s2[j..] 的最长公共子序列长度
        // int dp(String s1, int i, String s2, int j)
        // 这个dp函数的定义是：dp(s1, i, s2, j)计算s1[i..]和s2[j..]的最长公共子序列长度。

        // 根据这个定义，那么我们想要的答案就是dp(s1, 0, s2, 0)，且 base case 就是i == len(s1)或j == len(s2)时，因为这时候s1[i..]或s2[j..]就相当于空串了，最长公共子序列的长度显然是 0：
        // 备忘录，消除重叠子问题
        int [][] memo;
        public int longestCommonSubsequence(String text1, String text2) {
            int m = text1.length();
            int n = text2.length();
            // 备忘录初始值为-1， 代表未曾计算
            memo = new int[m][n];
            for (int [] row : memo) {
                Arrays.fill(row, -1);
            }
            // 计算 s1[0..] 和 s2[0...]的 lcs 长度
            return dp(text1, 0, text2, 0);
        }

        // 定义：计算 s1[i..] 和 s2[j..] 的最长公共子序列长度
        int dp(String s1, int i, String s2, int j) {
            // base case
            if ( i == s1.length() || j == s2.length()) {
                return 0;
            }
            // 如果之前计算过，则直接返回备忘录中的答案
            if (memo[i][j] != -1) {
                return memo[i][j];
            }
            // 根据 s1[i] 和 s2[j] 的情况做出选择
            if (s1.charAt(i) == s2.charAt(j)) {
                //s1[i],s2[j]必然在lcs 中
                memo[i][j] = 1 + dp(s1, i+1, s2, j+1);
            }
            else {
                // s1[i], s2[j] 至少有一个不再 lcs 中
                memo[i][j] = Math.max(
                        dp(s1, i+1, s2, j),
                        dp(s1, i, s2, j+1)
                );
            }
            return memo[i][j];
        }
    }


    class Solution2 {
        public int longestCommonSubsequence(String text1, String text2) {
            int m = text1.length();
            int n = text2.length();
            int dp[][] = new int[m+1][n+1];
            for(int i = 1; i <= m; i++){
                for(int j = 1; j <= n; j++){
                    if(text1.charAt(i-1) == text2.charAt(j-1)){
                        dp[i][j] = dp[i-1][j-1] + 1;
                    }else {
                        dp[i][j] = Math.max(dp[i-1][j], dp[i][j-1]);
                    }
                }
            }
            return dp[m][n];
        }
    }
}
