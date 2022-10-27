package leetcode.string;
//给定一个字符串 s ，请你找出其中不含有重复字符的 最长子串 的长度。
//
//
//
// 示例 1:
//
//
//输入: s = "abcabcbb"
//输出: 3
//解释: 因为无重复字符的最长子串是 "abc"，所以其长度为 3。
//
//
// 示例 2:
//
//
//输入: s = "bbbbb"
//输出: 1
//解释: 因为无重复字符的最长子串是 "b"，所以其长度为 1。
//
//
// 示例 3:
//
//
//输入: s = "pwwkew"
//输出: 3
//解释: 因为无重复字符的最长子串是"wke"，所以其长度为 3。
//    请注意，你的答案必须是 子串 的长度，"pwke"是一个子序列，不是子串。
//
//
//
//
// 提示：
//
//
// 0 <= s.length <= 5 * 10⁴
// s 由英文字母、数字、符号和空格组成
//
//
// Related Topics 哈希表 字符串 滑动窗口 👍 8339 👎 0
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

public class _3_无重复字符的最长子串 {

    class Solution {
        public int lengthOfLongestSubstring(String s) {

            /* 方法 1*/
//        int n = s.length();
//        int ans = 0;
//        HashSet<Character> set = new HashSet<Character>();
//        for (int i = 0; i < n; i++) {
//            for (int k = i; k < n; k++) {
//                if (set.contains(s.charAt(k))) {
//                    ans = Math.max(ans, k - i);
//                    set.clear();
//                    break;
//                } else {
//                    set.add(s.charAt(k));
//                    ans = Math.max(ans, k - i + 1);
//
//                }
//            }
//        }
//        return ans;
            /* 方法2 */
            int n = s.length(), ans = 0;
            Map<Character, Integer> map = new HashMap<Character, Integer>(); // current index of character
            // try to extend the range [i, j]
            for (int j = 0, i = 0; j < n; j++) {
                if (map.containsKey(s.charAt(j))) {
                    i = Math.max(map.get(s.charAt(j)), i);
                }
                ans = Math.max(ans, j - i + 1);
                map.put(s.charAt(j), j + 1);
            }
            return ans;
        }

    }

    @Test
    public void test(){
        Solution solution = new Solution();
        String s = "abcabcda";
        int tmp = solution.lengthOfLongestSubstring(s);

        System.out.println(tmp);
    }

}

