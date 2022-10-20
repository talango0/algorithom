package leetcode.jzhoffer;
//给定两个字符串 s 和 t 。返回 s 中包含 t 的所有字符的最短子字符串。如果 s 中不存在符合条件的子字符串，则返回空字符串 "" 。
//
// 如果 s 中存在多个符合条件的子字符串，返回任意一个。
//
//
//
// 注意： 对于 t 中重复字符，我们寻找的子字符串中该字符数量必须不少于 t 中该字符数量。
//
//
//
// 示例 1：
//
//
//输入：s = "ADOBECODEBANC", t = "ABC"
//输出："BANC"
//解释：最短子字符串 "BANC" 包含了字符串 t 的所有字符 'A'、'B'、'C'
//
// 示例 2：
//
//
//输入：s = "a", t = "a"
//输出："a"
//
//
// 示例 3：
//
//
//输入：s = "a", t = "aa"
//输出：""
//解释：t 中两个字符 'a' 均应包含在 s 的子串中，因此没有符合条件的子字符串，返回空字符串。
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
//
// 进阶：你能设计一个在 o(n) 时间内解决此问题的算法吗？
//
//
//
//
// 注意：本题与主站 76 题相似（本题答案不唯一）：https://leetcode-cn.com/problems/minimum-window-
//substring/
//
// Related Topics 哈希表 字符串 滑动窗口 👍 65 👎 0

import java.util.HashMap;
import java.util.Map;

/**
 * @author mayanwei
 * @date 2022-10-09.
 */
public class 剑指_Offer_II_017_含有所有字符的字段字符串{
    class Solution{
        public String minWindow(String s, String t) {
            char[] schar = s.toCharArray();
            char[] tchar = t.toCharArray();
            Map<Character, Integer> need = new HashMap<>(tchar.length);
            Map<Character, Integer> window = new HashMap<>(schar.length);
            for (char c : tchar) {
                need.put(c, need.getOrDefault(c, 0) + 1);
            }
            int left = 0, right = 0, valid = 0, start = 0, len = Integer.MAX_VALUE;
            while (right < schar.length) {
                // c 是将要加入窗口的字符
                char c = schar[right];
                right++;
                // 下面处理滑动窗口内的一系列更新
                if (need.keySet().contains(c)) {
                    window.put(c, window.getOrDefault(c, 0) + 1);
                    if (window.get(c).equals(need.get(c))) {
                        valid++;
                    }
                }
                // 判断左侧是否需要收缩
                while (valid == need.size()) {
                    // 在这里更新最小子串
                    if (right - left < len) {
                        start = left;
                        len = right - left;
                    }
                    // dCh是将要移除窗口的元素
                    char d = schar[left];
                    left++;
                    if (need.keySet().contains(d)) {
                        if (window.get(d).equals(need.get(d))) {
                            valid--;
                        }
                        window.put(d, window.getOrDefault(d, 0) - 1);
                    }
                }
            }
            return len == Integer.MAX_VALUE ? "" :s.substring(start, start + len);
        }
    }
}
