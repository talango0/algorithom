package leetcode.slidewindow;

import java.util.HashMap;
import java.util.Map;

//给你两个字符串 s1 和 s2 ，写一个函数来判断 s2 是否包含 s1 的排列。如果是，返回 true ；否则，返回 false 。
//
// 换句话说，s1 的排列之一是 s2 的 子串 。
//
//
//
// 示例 1：
//
//
//输入：s1 = "ab" s2 = "eidbaooo"
//输出：true
//解释：s2 包含 s1 的排列之一 ("ba").
//
//
// 示例 2：
//
//
//输入：s1= "ab" s2 = "eidboaoo"
//输出：false
//
//
//
//
// 提示：
//
//
// 1 <= s1.length, s2.length <= 10⁴
// s1 和 s2 仅包含小写字母
//
// Related Topics 哈希表 双指针 字符串 滑动窗口 👍 705 👎 0
public class _567_字符串的排列 {
    class Solution {
        public boolean checkInclusion(String s1, String s2) {
            char [] s2Chars = s2.toCharArray();
            char [] s1Chars = s1.toCharArray();
            Map<Character, Integer> window = new HashMap<>(), need = new HashMap<>();
            for (char c : s1Chars) {
                need.put(c, need.getOrDefault(c, 0)+1);
            }
            int left = 0, right = 0, start = 0, len = Integer.MAX_VALUE, valid = 0;
            while (right < s2Chars.length) {
                char c = s2Chars[right];
                right++;
                if (need.keySet().contains(c)) {
                    window.put(c, window.getOrDefault(c, 0)+1);
                    if (window.get(c).equals(need.get(c))) {
                        valid++;
                    }
                }
                while(valid == need.size()) {
                    if (right-left == s1Chars.length) {
                        return true;
                    }
                    char d = s2Chars[left++];

                    if (need.keySet().contains(d)){
                        if (window.get(d).equals(need.get(d))) {
                            valid--;
                        }
                        window.put(d, window.get(d)-1);
                    }
                }
            }
            return false;

        }
    }
}
