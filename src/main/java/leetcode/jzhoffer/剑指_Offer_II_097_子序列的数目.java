package leetcode.jzhoffer;

import leetcode.string._115_不同的子序列;

import java.util.Arrays;

/**
 * @author mayanwei
 * @date 2022-10-09.
 * @see _115_不同的子序列
 */
public class 剑指_Offer_II_097_子序列的数目{
    /**
     * 站在t的角度，不用备忘录，超时
     */
    class Solution{
        public int numDistinct(String s, String t) {
            return dp(s, 0, t, 0);
        }

        // 定义：返回 s[i..] 中包含 t[j..] 的数量
        private int dp(String s, int i, String t, int j) {
            if (j == t.length()) {
                // 子序列全部匹配完全
                return 1;
            }
            if (i == s.length()) {
                return 0;
            }
            int res = 0;
            // 站在t的角度，在 s[i..] 中寻找匹配 t[j] 的那个索引 k
            for (int k = i; k < s.length(); k++) {
                if (s.charAt(k) == t.charAt(j)) {
                    // 然后去 s[k+1..]中寻找子序列 t[j+1]
                    res += dp(s, k + 1, t, j + 1);
                }
            }
            return res;
        }
    }

    /**
     * 站在t的角度，用备忘录，勉强通过,因为dp里有个for循环
     * 执行用时：1131 ms , 在所有 Java 提交中击败了 5.04% 的用户
     * 内存消耗：45.2 MB , 在所有 Java 提交中击败了 53.60% 的用户
     */
    class Solution1{
        int[][] memo;

        public int numDistinct(String s, String t) {
            memo = new int[s.length() + 1][t.length() + 1];
            for (int i = 0; i < memo.length; i++) {
                Arrays.fill(memo[i], -1);
            }
            return dp(s, 0, t, 0);
        }

        // 定义：返回 s[i..] 中包含 t[j..] 的数量
        private int dp(String s, int i, String t, int j) {
            if (j == t.length()) {
                // 子序列全部匹配完全
                return 1;
            }
            if (i == s.length()) {
                return 0;
            }
            if (memo[i][j] != -1) {
                return memo[i][j];
            }
            int res = 0;
            // 站在t的角度，在 s[i..] 中寻找匹配 t[j] 的那个索引 k
            for (int k = i; k < s.length(); k++) {
                if (s.charAt(k) == t.charAt(j)) {
                    // 然后去 s[k+1..]中寻找子序列 t[j+1]
                    res += dp(s, k + 1, t, j + 1);
                }
            }
            memo[i][j] = res;
            return res;
        }
    }

    /**
     * 站在s的角度
     * 执行用时：2 ms, 在所有 Java 提交中击败了99.77%的用户
     * 内存消耗：45.2 MB , 在所有 Java 提交中击败了47.40%的用户
     * O(mn)
     */
    class Solution3{
        int[][] memo;

        public int numDistinct(String s, String t) {
            memo = new int[s.length() + 1][t.length() + 1];
            for (int i = 0; i < memo.length; i++) {
                Arrays.fill(memo[i], -1);
            }
            return dp(s, 0, t, 0);
        }

        // 定义：返回 s[i..] 中包含 t[j..] 的数量
        private int dp(String s, int i, String t, int j) {
            int m = s.length(), n = t.length();
            if (j == t.length()) {
                // 子序列全部匹配完全
                return 1;
            }
            if (n - j > m - i) {
                // 带匹配子序列的长度不应该比字符串的长度还要短
                return 0;
            }
            if (memo[i][j] != -1) {
                return memo[i][j];
            }
            int res = 0;
            // 站在s的角度
            if (s.charAt(i) == t.charAt(j)) {
                // 1、让 s[0] 匹配 t[0]，那么原问题转化为让 s[1..] 去匹配 t[1..]
                // 2、不让 s[0] 匹配 t[0]，那么原问题转化为让 s[1..] 去匹配 t[0..]
                res += dp(s, i + 1, t, j + 1) + dp(s, i + 1, t, j);
            }
            else {
                res += dp(s, i + 1, t, j);
            }
            memo[i][j] = res;
            return res;
        }
    }


}
