package leetcode.jzhoffer;
//给定一个非空字符串s，请判断如果最多 从字符串中删除一个字符能否得到一个回文字符串。
//
//
//
//示例 1:
//
//输入: s = "aba"
//输出: true
//示例 2:
//
//输入: s = "abca"
//输出: true
//解释: 可以删除 "c" 字符 或者 "b" 字符
//示例 3:
//
//输入: s = "abc"
//输出: false
//
//
//提示:
//
//1 <= s.length <= 105
//s 由小写英文字母组成
//
//来源：力扣（LeetCode）
//链接：https://leetcode.cn/problems/RQku0D
//著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。

import leetcode.string._680_验证回文串II;

/**
 * @see _680_验证回文串II
 */
public class 剑指_Offer_II_019_最多删除一个字符得到回文 {
    class Solution {
        /**贪心算法 */
        public boolean validPalindrome(String s) {
            int low = 0, high = s.length() - 1;
            while (low < high) {
                char c1 = s.charAt(low), c2 = s.charAt(high);
                if (c1 == c2) {
                    ++low;
                    --high;
                } else {
                    return validPalindrome(s, low, high - 1) || validPalindrome(s, low + 1, high);
                }
            }
            return true;
        }

        public boolean validPalindrome(String s, int low, int high) {
            for (int i = low, j = high; i < j; ++i, --j) {
                char c1 = s.charAt(i), c2 = s.charAt(j);
                if (c1 != c2) {
                    return false;
                }
            }
            return true;
        }
    }
}
