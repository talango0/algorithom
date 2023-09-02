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
import java.util.HashSet;
import java.util.Map;

public class _3_无重复字符的最长子串{
    class Solution0{
        public int lengthOfLongestSubstring(String s) {
            /* 方法 1*/
            int n = s.length();
            int ans = 0;
            HashSet<Character> set = new HashSet<Character>();
            for (int i = 0; i < n; i++) {
                for (int k = i; k < n; k++) {
                    if (set.contains(s.charAt(k))) {
                        ans = Math.max(ans, k - i);
                        set.clear();
                        break;
                    }
                    else {
                        set.add(s.charAt(k));
                        ans = Math.max(ans, k - i + 1);

                    }
                }
            }
            return ans;
        }
    }


    /**
     * 1. 定义一个 map 数据结构存储 (k, v)，其中 key 值为字符，value 值为字符位置 +1，加 1 表示从字符位置后一个才开始不重复
     * 2. 我们定义不重复子串的开始位置为 start，结束位置为 end
     * 3. 随着 end 不断遍历向后，会遇到与 [start, end] 区间内字符相同的情况，此时将字符作为 key 值，获取其 value 值，并更新 start，
     * 此时 [start, end] 区间内不存在重复字符
     * 4. 无论是否更新 start，都会更新其 map 数据结构和结果 ans。
     * 5. 时间复杂度： O(n)
     */
    class Solution{
        public int lengthOfLongestSubstring(String s) {
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

    /**
     * 滑动窗口
     */
    class Solution1{
        public int lengthOfLongestSubstring(String s) {
            char[] chs = s.toCharArray();
            HashMap<Character, Integer> window = new HashMap<>();
            int left = 0, right = 0, ans = 0;
            while (right < chs.length) {
                char c = chs[right++];
                // 进行窗口内数据的一系列更新
                window.put(c, window.getOrDefault(c, 0) + 1);
                // 判断左侧窗口是否要收缩
                while (window.get(c) > 1) {//当 window[c] 值大于 1 时，说明窗口中存在重复字符，不符合条件，就该移动 left 缩小窗口了。
                    // 进行窗口内数据的一系列更新
                    char d = chs[left++];
                    window.put(d, window.get(d) - 1);
                }
                // 在这里更新答案
                ans = Math.max(ans, right - left);
            }
            return ans;
        }
    }

    @Test
    public void test() {
        Solution solution = new Solution();
        String s = "abcabcda";
        int tmp = solution.lengthOfLongestSubstring(s);

        System.out.println(tmp);
        Solution1 solution1 = new Solution1();
        int res = solution1.lengthOfLongestSubstring(s);
        System.out.println(res);
    }

}

