package leetcode.slidewindow;

import java.util.HashMap;
import java.util.Map;
//给你一个字符串 s 、一个字符串 t 。返回 s 中涵盖 t 所有字符的最小子串。如果 s 中不存在涵盖 t 所有字符的子串，则返回空字符串 "" 。
//
//
//
// 注意：
//
//
// 对于 t 中重复字符，我们寻找的子字符串中该字符数量必须不少于 t 中该字符数量。
// 如果 s 中存在这样的子串，我们保证它是唯一的答案。
//
//
//
//
// 示例 1：
//
//
//输入：s = "ADOBECODEBANC", t = "ABC"
//输出："BANC"
//
//
// 示例 2：
//
//
//输入：s = "a", t = "a"
//输出："a"
//
//
// 示例 3:
//
//
//输入: s = "a", t = "aa"
//输出: ""
//解释: t 中两个字符 'a' 均应包含在 s 的子串中，
//因此没有符合条件的子字符串，返回空字符串。
//
//
//
// 提示：
//
//
// 1 <= s.length, t.length <= 10⁵
// s 和 t 由英文字母组成
//
//
//
//进阶：你能设计一个在 o(n) 时间内解决此问题的算法吗？ Related Topics 哈希表 字符串 滑动窗口 👍 1953 👎 0

public class _76_最小覆盖子串 {

    class Solution {
        public String minWindow(String s, String t) {
            // window 是活动窗口，need 是目标串对应字母字典
            char [] schars = s.toCharArray();
            char [] tchars = t.toCharArray();
            Map<Character,Integer> window = new HashMap<>(schars.length);
            Map<Character,Integer> need = new HashMap<>(tchars.length);

            for (char c : tchars ) {
                need.put(c, need.getOrDefault(c, 0)+1);
            }
            int left = 0, right = 0, start = 0, len = Integer.MAX_VALUE, valid = 0;
            while (right < schars.length) {
                //c是将要加入窗口的字符
                char c = schars[right];
                right++;
                //下面处理滑动窗口内数据的一系列更新
                if (need.keySet().contains(c)) {
                    window.put(c, window.getOrDefault(c, 0)+1);
                    if (window.get(c).equals(need.get(c))) {
                        valid ++;
                    }
                }

                //判断左侧是否需要收缩
                while (valid == need.size()) {
                    // 在这里更新最小子串
                    if (right-left < len) {
                        start = left;
                        len = right-left;
                    }
                    //需要收缩的字符
                    char d = schars[left];
                    left++;
                    //进行窗口内数据的一些列更新
                    if (need.keySet().contains(d)) {
                        if (window.get(d).equals(need.get(d))) {
                            valid--;
                        }
                        window.put(d, window.getOrDefault(d, 0)-1);

                    }
                }
            }
            return len == Integer.MAX_VALUE ? "":s.substring(start, start+len);
        }
    }
}
