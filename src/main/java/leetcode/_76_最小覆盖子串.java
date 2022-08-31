package leetcode;

import jdk.nashorn.internal.ir.CallNode;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class _76_最小覆盖子串 {

    //给你一个字符串 S、一个字符串 T 。请你设计一种算法，可以在 O(n) 的时间复杂度内，从字符串 S 里面找出：包含 T 所有字符的最小子串。
//
//
//
// 示例：
//
// 输入：S = "ADOBECODEBANC", T = "ABC"
//输出："BANC"
//
//
//
// 提示：
//
//
// 如果 S 中不存这样的子串，则返回空字符串 ""。
// 如果 S 中存在这样的子串，我们保证它是唯一的答案。
//
// Related Topics 哈希表 双指针 字符串 Sliding Window
// 👍 806 👎 0

    /**
     * 分析，
     * <p>
     * S = ADDBECODEBANC
     * <p>
     * T = ABC
     * <p>
     * 朴素解法：从T中找出所有的字串，所有包含ABC的字串中长度最小的即为所要返回的字串，如果不存在，返回""
     * <p>
     * 滑动窗口：设置两个指针，一个用于延伸现有窗口的r指针，和一个用于收缩的l指针。在任意时刻，只有一个指针运动，而另一个保持静止。
     * <p>
     * 在s上滑动窗口，通过移动r指针不断扩张窗口。当窗口包含l全部所需字符后，如果能收缩，就收缩窗口直到最小窗口。
     * <p>
     * 如何判断当前窗口是否包含t中所有的字符。用一个hash表表示所有的字符及它们的个数，用一个hash表动态维护窗口中所有字符以及它们的个数，
     * 如果这个动态表中包含t的hash表中的所有字符，并且对应的个数都不小于t的hash表中各个字符的个数，那么当前窗口是可行的。
     */

    static class Solution{
        Map<Character, Integer> ori = new HashMap<>();
        Map<Character, Integer> cnt = new HashMap<>();

        public String minWindow(String s, String t) {
            int tLen = t.length();
            for (int i = 0; i < tLen; i++) {
                char c = t.charAt(i);
                ori.put(c, ori.getOrDefault(c, 0) + 1);
            }

            int l = 0, r = -1;
            int len = Integer.MAX_VALUE, ansL = -1, ansR = -1;
            int sLen = s.length();

            while (r < sLen) {
                ++r;
                if (r < sLen && ori.containsKey(s.charAt(r))) {
                    cnt.put(s.charAt(r), cnt.getOrDefault(s.charAt(r), 0) + 1);
                }
                while (check() && l <= r) {
                    if (r - l + 1 < len) {
                        len = r - l + 1;
                        ansL = l;
                        ansR = l + len;
                    }
                    if (ori.containsKey(s.charAt(l))) {
                        cnt.put(s.charAt(l), cnt.getOrDefault(s.charAt(l), 0) - 1);
                    }
                    ++l;
                }
            }
            return ansL == -1 ? "" :s.substring(ansL, ansR);
        }

        private boolean check() {
            Iterator<Map.Entry<Character, Integer>> iter = ori.entrySet().iterator();
            while (iter.hasNext()) {
                Map.Entry<Character, Integer> entry = iter.next();
                Character key = entry.getKey();
                Integer value = entry.getValue();
                if (cnt.getOrDefault(key, 0) < value) {
                    return false;
                }
            }
            return true;
        }
    }

}
