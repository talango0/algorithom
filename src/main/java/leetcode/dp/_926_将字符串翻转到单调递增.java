package leetcode.dp;
//如果一个二进制字符串，是以一些 0（可能没有 0）后面跟着一些 1（也可能没有 1）的形式组成的，
// 那么该字符串是 单调递增 的。
//
// 给你一个二进制字符串 s，你可以将任何 0 翻转为 1 或者将 1 翻转为 0 。
//
// 返回使 s 单调递增的最小翻转次数。
//
//
//
// 示例 1：
//
//
//输入：s = "00110"
//输出：1
//解释：翻转最后一位得到 00111.
//
//
// 示例 2：
//
//
//输入：s = "010110"
//输出：2
//解释：翻转得到 011111，或者是 000111。
//
//
// 示例 3：
//
//
//输入：s = "00011000"
//输出：2
//解释：翻转得到 00000000。
//
//
//
//
// 提示：
//
//
// 1 <= s.length <= 10⁵
// s[i] 为 '0' 或 '1'
//
//
// Related Topics 字符串 动态规划 👍 290 👎 0

/**
 * @author mayanwei
 * @date 2022-11-04.
 */
public class _926_将字符串翻转到单调递增{
    class Solution1 {
        int [][] dp;
        // public int minFlipsMonoIncr(String s) {
        //    int [][] dp = new int[s.length()][2];
        //    char [] cs = s.toCharArray();
        //    dp[0][0] = cs[0] == '0' ? 0 : 1;
        //    dp[0][1] = cs[0] == '1' ? 0 : 1;
        //    for (int i = 1; i< dp.length; i++) {
        //        dp[i][0] = dp[i - 1][0] + (cs[i] == '0' ? 0 : 1);
        //        dp[i][1] = Math.min(dp[i-1][0], dp[i-1][1]) + (cs[i] == '1' ? 0 : 1);
        //    }
        //    return Math.min(dp[dp.length-1][0], dp[dp.length - 1][1]);
        // }
        public int minFlipsMonoIncr(String s) {
            int a, b;
            char[] cs = s.toCharArray();
            a = cs[0] == '0' ? 0 : 1;
            b = cs[0] == '1' ? 0 : 1;
            for (int i = 1; i < cs.length; i++) {
                // 先更新b后更新a，因为b依赖a的值，但是a不依赖b的值
                b = Math.min(a, b) + (cs[i] == '1' ? 0 : 1);
                a = a + (cs[i] == '0' ? 0 : 1);
            }
            return Math.min(a, b);
        }

    }
    class Solution {
        public int minFlipsMonoIncr(String s) {
            int dp_0=0,dp_1=0;
            for(char ch:s.toCharArray()){
                dp_1=dp_1>dp_0?dp_0:dp_1+'1'-ch;
                dp_0+=ch-'0';
            }
            return dp_0>dp_1?dp_1:dp_0;
        }
    }

}
