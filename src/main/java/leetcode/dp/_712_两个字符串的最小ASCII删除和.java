package leetcode.dp;

import java.util.Arrays;
//给定两个字符串s1 和 s2，返回 使两个字符串相等所需删除字符的 ASCII 值的最小和 。
//
//
//
// 示例 1:
//
//
//输入: s1 = "sea", s2 = "eat"
//输出: 231
//解释: 在 "sea" 中删除 "s" 并将 "s" 的值(115)加入总和。
//在 "eat" 中删除 "t" 并将 116 加入总和。
//结束时，两个字符串相等，115 + 116 = 231 就是符合条件的最小和。
//
//
// 示例 2:
//
//
//输入: s1 = "delete", s2 = "leet"
//输出: 403
//解释: 在 "delete" 中删除 "dee" 字符串变成 "let"，
//将 100[d]+101[e]+101[e] 加入总和。在 "leet" 中删除 "e" 将 101[e] 加入总和。
//结束时，两个字符串都等于 "let"，结果即为 100+101+101+101 = 403 。
//如果改为将两个字符串转换为 "lee" 或 "eet"，我们会得到 433 或 417 的结果，比答案更大。
//
//
//
//
// 提示:
//
//
// 0 <= s1.length, s2.length <= 1000
// s1 和 s2 由小写英文字母组成
//
//
// Related Topics 字符串 动态规划 👍 300 👎 0
/**
 * @author mayanwei
 * @date 2022-08-04.
 * @see _583_两个字符串的删除操作
 */
public class _712_两个字符串的最小ASCII删除和{
    class Solution{
        // 备忘录
        int memo[][];

        /* 主函数 */
        int minimumDeleteSum(String s1, String s2) {
            int m = s1.length(), n = s2.length();
            // 备忘录值为 -1 代表未曾计算
            memo = new int[m][n];
            for (int[] row : memo) {
                Arrays.fill(row, -1);
            }

            return dp(s1, 0, s2, 0);
        }

        // 定义：将 s1[i..] 和 s2[j..] 删除成相同字符串，
        // 最小的 ASCII 码之和为 dp(s1, i, s2, j)。
        int dp(String s1, int i, String s2, int j) {
            int res = 0;
            // base case
            if (i == s1.length()) {
                // 如果 s1 到头了，那么 s2 剩下的都得删除
                for (; j < s2.length(); j++) {
                    res += s2.charAt(j);
                }
                return res;
            }
            if (j == s2.length()) {
                // 如果 s2 到头了，那么 s1 剩下的都得删除
                for (; i < s1.length(); i++) {
                    res += s1.charAt(i);
                }
                return res;
            }

            if (memo[i][j] != -1) {
                return memo[i][j];
            }

            if (s1.charAt(i) == s2.charAt(j)) {
                // s1[i] 和 s2[j] 都是在 lcs 中的，不用删除
                memo[i][j] = dp(s1, i + 1, s2, j + 1);
            }
            else {
                // s1[i] 和 s2[j] 至少有一个不在 lcs 中，删一个
                memo[i][j] = Math.min(
                        s1.charAt(i) + dp(s1, i + 1, s2, j),
                        s2.charAt(j) + dp(s1, i, s2, j + 1)
                );
            }
            return memo[i][j];
        }
    }
}
