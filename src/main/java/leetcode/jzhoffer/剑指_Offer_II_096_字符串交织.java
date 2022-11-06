package leetcode.jzhoffer;

import leetcode.dp._97_交错字符串;

import java.util.Arrays;
//给定一个字符串 s 和一个字符串 t ，计算在 s 的子序列中 t 出现的个数。
//
// 字符串的一个 子序列 是指，通过删除一些（也可以不删除）字符且不干扰剩余字符相对位置所组成的新字符串。（例如，"ACE" 是 "ABCDE" 的一个子序列
//，而 "AEC" 不是）
//
// 题目数据保证答案符合 32 位带符号整数范围。
//
//
//
// 示例 1：
//
//
//输入：s = "rabbbit", t = "rabbit"
//输出：3
//解释：
//如下图所示, 有 3 种可以从 s 中得到 "rabbit" 的方案。
//rabbbit
//rabbbit
//rabbbit
//
// 示例 2：
//
//
//输入：s = "babgbag", t = "bag"
//输出：5
//解释：
//如下图所示, 有 5 种可以从 s 中得到 "bag" 的方案。
//babgbag
//babgbag
//babgbag
//babgbag
//babgbag
//
//
//
//
// 提示：
//
//
// 0 <= s.length, t.length <= 1000
// s 和 t 由英文字母组成
//
//
//
//
//
// 注意：本题与主站 115 题相同： https://leetcode-cn.com/problems/distinct-subsequences/
//
// Related Topics 字符串 动态规划 👍 41 👎 0

/**
 * @author mayanwei
 * @date 2022-11-05.
 * @see _97_交错字符串
 */
public class 剑指_Offer_II_096_字符串交织{
    class Solution {

        public boolean isInterleave(String s1, String s2, String s3) {
            int m = s1.length(), n = s2.length();
            // 如果长度对不上，必然不可能
            if (m + n != s3.length()) {
                return false;
            }
            // 备忘录，其中 -1 代表未计算，0代表 false，1 代表true
            memo = new int[m + 1][n + 1];
            for(int [] row: memo) {
                Arrays.fill(row, -1);
            }
            return dp(s1, 0, s2, 0, s3);
        }
        int [][] memo;
        // 定义：计算 s1[i..], s2[j..]是否能组合出 s3[i+j...]
        boolean dp(String s1, int i, String s2, int j, String s3) {
            int k = i + j;
            // base case ,s3构造完成
            if (k == s3.length()) {
                return true;
            }
            // 查找备忘录，如果已经计算过，直接返回
            if (memo[i][j] != -1) {
                return memo[i][j] == 1 ? true : false;
            }

            boolean res = false;
            // 如果， s1[i] 可以匹配 s3[k], 那么填入 s1[i]试一下
            if (i < s1.length() && s1.charAt(i) == s3.charAt(k)) {
                res = dp(s1, i + 1, s2, j, s3);
            }
            // 如果，s1[i] 匹配不了，s2[j] 可以匹配，那么填入 s2[j] 试一下
            if (j < s2.length() && s2.charAt(j) == s3.charAt(k)) {
                res = res || dp(s1, i, s2, j + 1, s3);
            }
            // 如果 s1[i] 和 s2[j] 都匹配不了，则返回 false
            // 将结果存入备忘录
            memo[i][j] = res == true ? 1 : 0;

            return res;
        }
    }
}
