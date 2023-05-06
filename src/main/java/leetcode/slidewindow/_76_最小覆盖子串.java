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

/**
 * 字节
 */
public class _76_最小覆盖子串{

    class Solution{
        /**
         * init window
         * @param s
         * @param t
         * @return
         */
        public String minWindow(String s, String t) {
            // window 是活动窗口，need 是目标串对应字母字典
            char[] schars = s.toCharArray();
            char[] tchars = t.toCharArray();
            Map<Character, Integer> window = new HashMap<>(schars.length);
            Map<Character, Integer> need = new HashMap<>(tchars.length);

            for (char c : tchars) {
                need.put(c, need.getOrDefault(c, 0) + 1);
            }
            int left = 0, right = 0, start = 0, len = Integer.MAX_VALUE, valid = 0;
            while (right < schars.length) {
                //c是将要加入窗口的字符
                char c = schars[right];
                right++;
                //下面处理滑动窗口内数据的一系列更新
                if (need.keySet().contains(c)) {
                    window.put(c, window.getOrDefault(c, 0) + 1);
                    if (window.get(c).equals(need.get(c))) {
                        valid++;
                    }
                }

                //判断左侧是否需要收缩
                while (valid == need.size()) {
                    // 在这里更新最小子串
                    if (right - left < len) {
                        start = left;
                        len = right - left;
                    }
                    //需要收缩的字符
                    char d = schars[left];
                    left++;
                    //进行窗口内数据的一些列更新
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


    class Solution2{
        public String minWindow(String s, String t) {
            int ns = s.length(), nt = t.length();
            if (ns < nt) {
                return "";
            }
            int[] hash = new int[58]; // A~Z: 65~90, a~z: 97~122
            int diff = 0;
            for (int i = 0; i < nt; i++) {
                hash[t.charAt(i) - 'A']++;
                hash[s.charAt(i) - 'A']--;
            }
            for (int val : hash) { // 只关心未抵消的字符
                if (val > 0) {
                    diff++;
                }
            }
            if (diff == 0) {
                return s.substring(0, nt); // 第一个窗为最小覆盖子串时
            }
            int l = 0, r = nt, lmin = 0, rmin = ns;
            for (; r < ns; r++) { // 只要当前窗还未覆盖，向右侧扩窗
                int in = s.charAt(r) - 'A';
                hash[in]--;
                if (hash[in] == 0) {
                    diff--; // in入窗后使得窗内该字符个数与t中相同
                }
                if (diff != 0) {
                    continue; // diff不为0则继续扩窗
                }
                for (; diff == 0; l++) { // 从左侧缩窗
                    int out = s.charAt(l) - 'A';
                    hash[out]++;
                    if (hash[out] == 1) {
                        diff++;
                    }
                }
                if (r - l + 1 < rmin - lmin) { // 缩窗后得到一个合格窗，若窗宽更小，更新窗界
                    lmin = l - 1;
                    rmin = r;
                }
            }
            return rmin == ns ? "" :s.substring(lmin, rmin + 1); // 根据窗界是否有过更新来返回相应的结果
        }
    }
}
