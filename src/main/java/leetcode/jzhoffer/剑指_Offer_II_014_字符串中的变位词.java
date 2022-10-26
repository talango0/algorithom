package leetcode.jzhoffer;
//给定两个字符串 s1 和 s2，写一个函数来判断 s2 是否包含 s1 的某个变位词。
//
// 换句话说，第一个字符串的排列之一是第二个字符串的 子串 。
//
//
//
// 示例 1：
//
//
//输入: s1 = "ab" s2 = "eidbaooo"
//输出: True
//解释: s2 包含 s1 的排列之一 ("ba").
//
//
// 示例 2：
//
//
//输入: s1= "ab" s2 = "eidboaoo"
//输出: False
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
//
//
//
//
// 注意：本题与主站 567 题相同： https://leetcode-cn.com/problems/permutation-in-string/
//
// Related Topics 哈希表 双指针 字符串 滑动窗口 👍 71 👎 0

import leetcode.slidewindow._567_字符串的排列;
import leetcode.slidewindow._76_最小覆盖子串;

import java.util.HashMap;
import java.util.Map;

/**
 * @author mayanwei
 * @date 2022-10-26.
 * @see _567_字符串的排列
 * @see _76_最小覆盖子串
 */
public class 剑指_Offer_II_014_字符串中的变位词{
    class Solution{
        public boolean checkInclusion(String s1, String s2) {
            char[] ch1s = s1.toCharArray();
            char[] ch2s = s2.toCharArray();
            Map<Character, Integer> need = new HashMap<>(), window = new HashMap<>();
            for (char ch : ch1s) {
                need.put(ch, need.getOrDefault(ch, 0) + 1);
            }
            int left = 0, right = 0, valid = 0, n = ch2s.length;
            while (right < n) {
                char cur = ch2s[right++];
                if (need.containsKey(cur)) {
                    window.put(cur, window.getOrDefault(cur, 0) + 1);
                    if (window.get(cur).equals(need.get(cur))) {
                        valid++;
                    }
                }
                while (valid == need.size()) {
                    if (right - left == ch1s.length) {
                        return true;
                    }

                    char d = ch2s[left++];

                    if (need.keySet().contains(d)) {
                        if (window.get(d).equals(need.get(d))) {
                            valid--;
                        }
                        window.put(d, window.get(d) - 1);
                    }
                }
            }
            return false;
        }
    }
}
