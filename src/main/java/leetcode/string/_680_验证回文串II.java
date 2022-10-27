package leetcode.string;
//给你一个字符串s，最多 可以从中删除一个字符。
//
//请你判断 s 是否能成为回文字符串：如果能，返回 true ；否则，返回 false 。
//
//
//
//示例 1：
//
//输入：s = "aba"
//输出：true
//示例 2：
//
//输入：s = "abca"
//输出：true
//解释：你可以删除字符 'c' 。
//示例 3：
//
//输入：s = "abc"
//输出：false
//
//
//提示：
//
//1 <= s.length <= 105
//s 由小写英文字母组成
//
//来源：力扣（LeetCode）
//链接：https://leetcode.cn/problems/valid-palindrome-ii
//著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。

/**
 * @see _125_验证回文串
 */
public class  _680_验证回文串II {
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
