package leetcode.string;
//给定两个字符串 s 和 p，找到 s 中所有 p 的 异位词 的子串，返回这些子串的起始索引。不考虑答案输出的顺序。
//
// 异位词 指由相同字母重排列形成的字符串（包括相同的字符串）。
//
//
//
// 示例 1:
//
//
//输入: s = "cbaebabacd", p = "abc"
//输出: [0,6]
//解释:
//起始索引等于 0 的子串是 "cba", 它是 "abc" 的异位词。
//起始索引等于 6 的子串是 "bac", 它是 "abc" 的异位词。
//
//
// 示例 2:
//
//
//输入: s = "abab", p = "ab"
//输出: [0,1,2]
//解释:
//起始索引等于 0 的子串是 "ab", 它是 "ab" 的异位词。
//起始索引等于 1 的子串是 "ba", 它是 "ab" 的异位词。
//起始索引等于 2 的子串是 "ab", 它是 "ab" 的异位词。
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
// Related Topics 哈希表 字符串 滑动窗口 👍 1013 👎 0

import org.junit.Assert;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author mayanwei
 * @date 2022-08-15.
 */
public class _438_找到字符串中所有字母异位词{
    class Solution {
        public List<Integer> findAnagrams(String s, String p) {
            char [] schs = s.toCharArray();
            char [] pchs = p.toCharArray();
            int [] need = new int[26];
            int [] window = new int[26];
            for (char c : pchs) {
                need[c-'a'] ++;
            }
            int size = 0;
            for (int i = 0; i < need.length; i++) {
                if (need[i] > 0) {
                    size ++;
                }
            }
            int left = 0, right = 0;
            int valid = 0;
            List<Integer> res = new ArrayList<>();

            while (right < schs.length) {
                char c = schs[right];
                right ++;
                // 进行窗口内数据的一系列更新
                if(need[c-'a'] > 0) {
                    window[c-'a'] ++;
                    if (window[c-'a'] == need[c-'a']) {
                        valid ++;
                    }
                }

                // 判断左侧窗口是否要收缩
                while (right - left >= pchs.length) {
                    // 当窗口符合条件时， 把起始索引加入 res
                    if (valid == size) {
                        res.add(left);
                    }
                    char d = schs[left];
                    left ++;
                    // 进行窗口内数据的一系列更新
                    if (need[d-'a'] > 0 ) {
                        if (window[d-'a'] == need[d-'a']) {
                            valid --;
                        }
                        window[d-'a']--;
                    }
                }
            }
            return res;
        }
    }

    class Solution2 {
        public List<Integer> findAnagrams(String s, String p) {
            int[] need = new int[26];
            for (int i = 0; i < p.length(); i++) {
                need[p.charAt(i) - 'a']++;
            }
            int start = 0, end = 0;
            int[] window = new int[26];
            List<Integer> ans = new ArrayList<Integer>();
            while (end < s.length()) {
                window[s.charAt(end) - 'a']++;
                if (end - start + 1 == p.length()) {
                    if (Arrays.equals(window, need)) ans.add(start);
                    window[s.charAt(start) - 'a']--;
                    start++;
                }
                end++;
            }
            return ans;
        }
    }
    @Test
    public void test(){
        Solution solution = new Solution();
        List<Integer> anagrams = solution.findAnagrams("cbaebabacd",
                "abc");
        Assert.assertArrayEquals(new Integer[]{0,6}, anagrams.toArray(new Integer[0]));

    }
}
