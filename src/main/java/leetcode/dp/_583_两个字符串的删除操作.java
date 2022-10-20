package leetcode.dp;
//给定两个单词 word1 和
// word2 ，返回使得
// word1 和
// word2 相同所需的最小步数。
//
// 每步 可以删除任意一个字符串中的一个字符。
//
//
//
// 示例 1：
//
//
//输入: word1 = "sea", word2 = "eat"
//输出: 2
//解释: 第一步将 "sea" 变为 "ea" ，第二步将 "eat "变为 "ea"
//
//
// 示例 2:
//
//
//输入：word1 = "leetcode", word2 = "etco"
//输出：4
//
//
//
//
// 提示：
//
//
//
// 1 <= word1.length, word2.length <= 500
// word1 和 word2 只包含小写英文字母
//
//
// Related Topics 字符串 动态规划 👍 470 👎 0
/**
 * @see _1143_最长公共子序列
 * @see _712_两个字符串的最小ASCII删除和
 * @author mayanwei
 * @date 2022-08-04.
 */
public class _583_两个字符串的删除操作{

    class Solution {
        // 题目要求计算两个字符串变得相同的最少删除次数
        // 删除的最后结果就是留下最长公共子串
        public int minDistance(String word1, String word2) {
            int m = word1.length(), n = word2.length();
            int lcs = longestCommonSubsequence(word1, word2);
            return m-lcs+n-lcs;
        }

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
