package leetcode.dp;

import java.util.Arrays;
//给定三个字符串 s1、s2、s3，请你帮忙验证 s3 是否是由 s1 和 s2 交错 组成的。
//
// 两个字符串 s 和 t 交错 的定义与过程如下，其中每个字符串都会被分割成若干 非空 子字符串：
//
//
// s = s1 + s2 + ... + sn
// t = t1 + t2 + ... + tm
// |n - m| <= 1
// 交错 是 s1 + t1 + s2 + t2 + s3 + t3 + ... 或者 t1 + s1 + t2 + s2 + t3 + s3 + ...
//
//
// 注意：a + b 意味着字符串 a 和 b 连接。
//
//
//
// 示例 1：
//
//
//输入：s1 = "aabcc", s2 = "dbbca", s3 = "aadbbcbcac"
//输出：true
//
//
// 示例 2：
//
//
//输入：s1 = "aabcc", s2 = "dbbca", s3 = "aadbbbaccc"
//输出：false
//
//
// 示例 3：
//
//
//输入：s1 = "", s2 = "", s3 = ""
//输出：true
//
//
//
//
// 提示：
//
//
// 0 <= s1.length, s2.length <= 100
// 0 <= s3.length <= 200
// s1、s2、和 s3 都由小写英文字母组成
//
//
//
//
// 进阶：您能否仅使用 O(s2.length) 额外的内存空间来解决它?
//
// Related Topics 字符串 动态规划 👍 790 👎 0

/**
 * @author mayanwei
 * @date 2022-11-05.
 */
public class _97_交错字符串{
    /**
     * 题目巴拉巴拉说了一大堆，实则就是一个使用双指针技巧合并两个字符串的过程。
     * <p>
     * 双指针的大致逻辑如下：
     * <pre>
     *  int i = 0, j = 0, k = 0;
     *  for (int k = 0; k < s3.length; k++) {
     *      if (s1[i] == s3[k]) {
     *          i++;
     *      } else if (s2[j] == s3[k]) {
     *          j++;
     *      }
     *  }
     * </pre>
     * 但本题跟普通的数组/链表双指针技巧不同的是，这里需要穷举所有情况。
     * 比如 s1[i], s2[j] 都能匹配 s3[k] 的时候，到底应该让谁来匹配，才能完全合并出 s3 呢？
     * <p>
     * 回答这个问题很简单，我不知道让谁来，那就都来试一遍好了，
     * 前文 经典动态规划：最长公共子序列 和 经典动态规划：编辑距离 都处理过类似的情况。
     * <p>
     * 所以本题肯定需要一个递归函数来穷举双指针的匹配过程，然后用一个备忘录消除递归过程中的重叠子问题，
     * 也就能写出自顶向下的递归的动态规划解法了。
     */
    class Solution{

        public boolean isInterleave(String s1, String s2, String s3) {
            int m = s1.length(), n = s2.length();
            // 如果长度对不上，必然不可能
            if (m + n != s3.length()) {
                return false;
            }
            // 备忘录，其中 -1 代表未计算，0代表 false，1 代表true
            memo = new int[m + 1][n + 1];
            for (int[] row : memo) {
                Arrays.fill(row, -1);
            }
            return dp(s1, 0, s2, 0, s3);
        }

        int[][] memo;

        // 定义：计算 s1[i..], s2[j..]是否能组合出 s3[i+j...]
        boolean dp(String s1, int i, String s2, int j, String s3) {
            int k = i + j;
            // base case ,s3构造完成
            if (k == s3.length()) {
                return true;
            }
            // 查找备忘录，如果已经计算过，直接返回
            if (memo[i][j] != -1) {
                return memo[i][j] == 1 ? true :false;
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
            memo[i][j] = res == true ? 1 :0;

            return res;
        }
    }
}
