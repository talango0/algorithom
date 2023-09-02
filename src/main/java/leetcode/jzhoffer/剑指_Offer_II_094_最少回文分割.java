package leetcode.jzhoffer;
//给定一个字符串 s，请将 s 分割成一些子串，使每个子串都是回文串。
//
// 返回符合要求的 最少分割次数 。
// 示例 1：
//输入：s = "aab"
//输出：1
//解释：只需一次分割就可将s 分割成 ["aa","b"] 这样两个回文子串。
//
//
// 示例 2：
//输入：s = "a"
//输出：0
//
//
// 示例 3：
//输入：s = "ab"
//输出：1
//
// 提示：
// 1 <= s.length <= 2000
// s 仅由小写英文字母组成
//
//
// 注意：本题与主站 132 题相同： https://leetcode-cn.com/problems/palindrome-partitioning-
//ii/
//
// Related Topics 字符串 动态规划 👍 47 👎 0

import leetcode.stack._132_分割回文串II;
import leetcode.string._131_分割回文串;

import java.util.Arrays;

/**
 * @author mayanwei
 * @date 2022-10-13.
 * @see _131_分割回文串
 * @see _132_分割回文串II
 */
public class 剑指_Offer_II_094_最少回文分割{
    class Solution{
        int[] dp;

        public void extend(char[] chars, int i, int j) {
            while (j < chars.length && i >= 0
                    && chars[i] == chars[j]) {
                dp[j] = Math.min(dp[j], (i == 0 ? -1 :dp[i - 1]) + 1);
                i--;
                j++;
            }
        }

        public int minCut(String s) {
            char[] chars = s.toCharArray();
            dp = new int[chars.length];
            Arrays.fill(dp, dp.length - 1);
            for (int i = 0; i < s.length(); i++) {
                // 注意偶数长度与奇数长度回文串的特点
                extend(chars, i, i);  // 奇数回文串以1个字符为中心
                extend(chars, i, i + 1); // 偶数回文串以2个字符为中心
            }
            return dp[dp.length - 1];
        }
    }
}
