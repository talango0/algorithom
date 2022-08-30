package leetcode.stack;
//给你一个只包含 '(' 和 ')' 的字符串，找出最长有效（格式正确且连续）括号子串的长度。
//
//示例 1：
//
//输入：s = "(()"
//输出：2
//解释：最长有效括号子串是 "()"
//示例 2：
//
//输入：s = ")()())"
//输出：4
//解释：最长有效括号子串是 "()()"
//示例 3：
//
//输入：s = ""
//输出：0
//提示：
//
//0 <= s.length <= 3 * 104
//s[i] 为 '(' 或 ')'
//Related Topics
//
//👍 1979, 👎 0

/**
 * @author mayanwei
 * @date 2022-08-29.
 */
public class _32_最长有效括号{

    /**
     * 思路和算法
     * <p>
     * 在此方法中，我们利用两个计数器 left 和 right 。首先，我们从左到右遍历字符串，对于遇到的每个 ‘(’，我们增加 left 计数器，
     * 对于遇到的每个 ‘)’ ，我们增加 right 计数器。每当 left 计数器与 right 计数器相等时，我们计算当前有效字符串的长度，
     * 并且记录目前为止找到的最长子字符串。当 right 计数器比 left 计数器大时，我们将 left和 right 计数器同时变回 0。
     * <p>
     * 这样的做法贪心地考虑了以当前字符下标结尾的有效括号长度，每次当右括号数量多于左括号数量的时候之前的字符我们都扔掉不再考虑，
     * 重新从下一个字符开始计算，但这样会漏掉一种情况，就是遍历的时候左括号的数量始终大于右括号的数量，即 (() ，这种时候最长有效括号是求不出来的。
     * <p>
     * 解决的方法也很简单，我们只需要从右往左遍历用类似的方法计算即可，只是这个时候判断条件反了过来：
     * <p>
     * 当 left 计数器比 right 计数器大时，我们将 left和 right计数器同时变回 0
     * <p>
     * 当 left 计数器与 =right 计数器相等时，我们计算当前有效字符串的长度，并且记录目前为止找到的最长子字符串
     */

    class Solution{
        // 时间复杂度： O(n)，其中 n 为字符串长度。我们只要正反遍历两边字符串即可。
        // 空间复杂度： O(1)。我们只需要常数空间存放若干变量。

        public int longestValidParentheses(String s) {
            char[] chs = s.toCharArray();
            int left = 0, right = 0;
            int ans = 0;
            for (int i = 0; i < chs.length; i++) {
                char ch = chs[i];
                if (ch == ')') {
                    right++;
                }
                else {
                    left++;
                }

                if (right > left) {
                    left = right = 0;
                }
                if (left == right) {
                    ans = Math.max(ans, 2 * right);
                }
            }
            right = left = 0;
            for (int i = chs.length - 1; i >= 0; i--) {
                char ch = chs[i];
                if (ch == ')') {
                    right++;
                }
                else {
                    left++;
                }

                if (left > right) {
                    left = right = 0;
                }
                if (left == right) {
                    ans = Math.max(ans, 2 * left);
                }
            }
            return ans;
        }
    }
}
