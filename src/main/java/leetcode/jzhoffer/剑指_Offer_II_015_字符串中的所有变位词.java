package leetcode.jzhoffer;
//给定两个字符串 s 和 p，找到 s 中所有 p 的 变位词 的子串，返回这些子串的起始索引。不考虑答案输出的顺序。
//
// 变位词 指字母相同，但排列不同的字符串。
//
//
//
// 示例 1：
//
//
//输入: s = "cbaebabacd", p = "abc"
//输出: [0,6]
//解释:
//起始索引等于 0 的子串是 "cba", 它是 "abc" 的变位词。
//起始索引等于 6 的子串是 "bac", 它是 "abc" 的变位词。
//
//
// 示例 2：
//
//
//输入: s = "abab", p = "ab"
//输出: [0,1,2]
//解释:
//起始索引等于 0 的子串是 "ab", 它是 "ab" 的变位词。
//起始索引等于 1 的子串是 "ba", 它是 "ab" 的变位词。
//起始索引等于 2 的子串是 "ab", 它是 "ab" 的变位词。
//
//
//
//
// 提示:
//
//
// 1 <= s.length, p.length <= 3 * 10⁴
// s 和 p 仅包含小写字母
//
//
//
//
// 注意：本题与主站 438 题相同： https://leetcode-cn.com/problems/find-all-anagrams-in-a-
//string/
//
// Related Topics 哈希表 字符串 滑动窗口 👍 38 👎 0

import leetcode.string._438_找到字符串中所有字母异位词;

import java.util.ArrayList;
import java.util.List;

/**
 * @author mayanwei
 * @date 2022-10-27.
 * @see _438_找到字符串中所有字母异位词
 */
public class 剑指_Offer_II_015_字符串中的所有变位词{
    class Solution{
        public List<Integer> findAnagrams(String s, String p) {
            char[] schs = s.toCharArray();
            char[] pchs = p.toCharArray();
            int[] need = new int[26];
            int[] window = new int[26];
            for (char c : pchs) {
                need[c - 'a']++;
            }
            int size = 0;
            for (int i = 0; i < need.length; i++) {
                if (need[i] > 0) {
                    size++;
                }
            }
            int left = 0, right = 0;
            int valid = 0;
            List<Integer> res = new ArrayList<>();

            while (right < schs.length) {
                char c = schs[right];
                right++;
                // 进行窗口内数据的一系列更新
                if (need[c - 'a'] > 0) {
                    window[c - 'a']++;
                    if (window[c - 'a'] == need[c - 'a']) {
                        valid++;
                    }
                }

                // 判断左侧窗口是否要收缩
                while (right - left >= pchs.length) {
                    // 当窗口符合条件时， 把起始索引加入 res
                    if (valid == size) {
                        res.add(left);
                    }
                    char d = schs[left];
                    left++;
                    // 进行窗口内数据的一系列更新
                    if (need[d - 'a'] > 0) {
                        if (window[d - 'a'] == need[d - 'a']) {
                            valid--;
                        }
                        window[d - 'a']--;
                    }
                }
            }
            return res;
        }
    }
}
