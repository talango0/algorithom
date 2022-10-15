package leetcode.stack;

import leetcode.string._131_分割回文串;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
//给你一个字符串 s，请你将 s 分割成一些子串，使每个子串都是回文。
//
// 返回符合要求的 最少分割次数 。
//
// 示例 1：
//输入：s = "aab"
//输出：1
//解释：只需一次分割就可将 s 分割成 ["aa","b"] 这样两个回文子串。
//
// 示例 2：
//输入：s = "a"
//输出：0
//
//
// 示例 3：
//输入：s = "ab"
//输出：1
// 提示：
//
// 1 <= s.length <= 2000
// s 仅由小写英文字母组成
//
//
// Related Topics 字符串 动态规划 👍 624 👎 0

/**
 * @author mayanwei
 * @date 2022-10-13.
 * @see _131_分割回文串
 */
public class _132_分割回文串II{
    class Solution{
        public int minCut(String s) {
            int n = s.length();
            boolean[][] dp = new boolean[n][n];
            for (int i = 0; i < n; i++) {
                Arrays.fill(dp[i], true);
            }
            for (int i = n - 1; i >= 0; i--) {
                for (int j = i + 1; j < n; j++) {
                    dp[i][j] = (s.charAt(i) == s.charAt(j) && dp[i + 1][j - 1]);
                }
            }
            // f[i] 表示字符串的前缀 s[0..i] 的最少分割次数。
            // f[0] = 0, f[i] = max{f[j]} + 1, j:0 upto i-1, 其中 s[j+1, i]是一个回文串
            int[] f = new int[n];
            Arrays.fill(f, Integer.MAX_VALUE);
            for (int i = 0; i < n; i++) {
                if (dp[0][i]) {
                    f[i] = 0;
                }
                else {
                    for (int j = 0; j < i; j++) {
                        if (dp[j + 1][i]) {
                            f[i] = Math.min(f[i], f[j] + 1);
                        }
                    }
                }
            }
            return f[n - 1];
        }

    }


    class Solution2{
        int[] dp;
        public void extend(char[] chars, int i, int j) {
            while (j < chars.length && i >= 0 && chars[i] == chars[j]) {
                dp[j] = Math.min(dp[j], (i == 0 ? -1 :dp[i - 1]) + 1);
                i--;
                j++;
            }
        }
        public int minCut(String s) {
            char[] chars = s.toCharArray();
            // dp[i] 表示以i结尾的字符串分割成字符串最小的次数
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

    @Test
    public void test(){
        Solution2 solution2 = new Solution2();
        int res = solution2.minCut("aab");
        System.out.println(res);
    }
}
