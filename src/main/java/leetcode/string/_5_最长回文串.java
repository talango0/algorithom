package leetcode.string;
//给你一个字符串 s，找到 s 中最长的回文子串。
//
//
//
// 示例 1：
//
//
//输入：s = "babad"
//输出："bab"
//解释："aba" 同样是符合题意的答案。
//
//
// 示例 2：
//
//
//输入：s = "cbbd"
//输出："bb"
//
//
//
//
// 提示：
//
//
// 1 <= s.length <= 1000
// s 仅由数字和英文字母组成
//
// Related Topics 字符串 动态规划 👍 5389 👎 0

import leetcode.dp._1312_让字符串成为回文串的最少插入次数;
import leetcode.dp._516_最长回文子序列;

import java.util.ArrayList;

/**
 * @see _1312_让字符串成为回文串的最少插入次数
 * @see _516_最长回文子序列
 * @see _647_回文子串
 * @see _5_最长回文串
 */
public class _5_最长回文串 {
    /**
     * <pre>
     * 左右指针
     *
     * 寻找回文串的问题核心思想是：从中间开始向两边扩散来判断回文串，对于最长回文子串，就是这个意思：
     *
     * for 0 <= i < len(s):
     *     找到以 s[i] 为中心的回文串
     *     更新答案
     * 找回文串的关键技巧是传入两个指针 l 和 r 向两边扩散，因为这样实现可以同时处理回文串长度为奇数和偶数的情况。
     *
     * for 0 <= i < len(s):
     *     # 找到以 s[i] 为中心的回文串
     *     palindrome(s, i, i)
     *     # 找到以 s[i] 和 s[i+1] 为中心的回文串
     *     palindrome(s, i, i + 1)
     *     更新答案
     * </pre>
     */
    class Solution {
        public String longestPalindrome(String s) {
            if(s == null || s.length()<2){
                return s;
            }
            String res = "";
            for (int i = 0; i<s.length(); i++){
                // 以 s[i] 为中心的最长回文子串
                String s1 = palingdrome(s, i, i);
                // 以 s[i] 和 s[i+1] 为中心的最长回文子串
                String s2 = palingdrome(s, i, i+1);
                // res = longest(res, s1, s2)
                res = res.length() > s1.length()? res:s1;
                res = res.length() > s2.length()? res:s2;
            }
            return res;
        }

        //在 s 中寻找以 s[l] 和 s[r] 为中心的最长回文串
        String palingdrome(String s, int l, int r) {
            int n = s.length();
            // 防止越界，
            // 如果输入相同的 l 和 r，就相当于于奇数长度的回文串，
            // 如果输入相邻的两个l和r，则相当于长度为偶数的回文串
            while (l>=0 && r<n
                    && s.charAt(l) == s.charAt(r)) {
                // 双指针，向两边展开
                l--; r++;
            }
            // 返回以 s[l] 和 s[r] 为中心的最长回文串
            return s.substring(l+1, r);
        }
    }

    /**
     * Manacher
     */
    class Solution2{
        public String longestPalindrome(String s) {
            int start = 0, end = -1;
            StringBuilder t = new StringBuilder('#');
            for (int i = 0; i < s.length(); i++) {
                t.append(s.charAt(i));
                t.append('#');
            }
            t.append('#');
            s = t.toString();
            ArrayList<Integer> arm_len = new ArrayList<>();
            int right = -1, j = -1;
            for (int i = 0; i < s.length(); i++) {

                int cur_arm_len;
                if (right >= i) {
                    int i_sym = j * 2 - i;
                    int min_arm_len = Math.min(arm_len.get(i_sym), right - i);
                    cur_arm_len = expand(s, i - min_arm_len, i +min_arm_len);
                }
                else {
                    cur_arm_len = expand(s, i, i);
                }
                arm_len.add(cur_arm_len);
                if (i + cur_arm_len > right) {
                    j = i;
                    right = i + cur_arm_len;
                }
                if (cur_arm_len * 2 + 1 > end - start) {
                    start = i - cur_arm_len;
                    end = i +cur_arm_len;
                }
            }
            StringBuffer ans = new StringBuffer();
            for (int i = 0; i <= end; i++) {
                if(s.charAt(i) != '#') {
                    ans.append(s.charAt(i));
                }
            }
            return ans.toString();
        }
        public int expand(String s, int left, int right) {
            while (left >= 0 && right < s.length() && s.charAt(left) == s.charAt(right)) {
                --left;
                ++right;
            }
            return (right - left - 2) / 2;
        }
    }
}
