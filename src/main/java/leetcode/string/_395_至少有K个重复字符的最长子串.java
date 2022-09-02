package leetcode.string;
//给你一个字符串 s 和一个整数 k ，请你找出 s 中的最长子串，要求该子串中的每一字符出现次数都不少于 k 。返回这一子串的长度。
//
//
//
//示例 1：
//
//输入：s = "aaabb", k = 3
//输出：3
//解释：最长子串为 "aaa" ，其中 'a' 重复了 3 次。
//示例 2：
//
//输入：s = "ababbc", k = 2
//输出：5
//解释：最长子串为 "ababb" ，其中 'a' 重复了 2 次， 'b' 重复了 3 次。
//
//
//提示：
//
//1 <= s.length <= 104
//s 仅由小写英文字母组成
//1 <= k <= 105
//
//来源：力扣（LeetCode）
//链接：https://leetcode.cn/problems/longest-substring-with-at-least-k-repeating-characters
//著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。

/**
 * @see _3_无重复字符的最长子串
 */
public class _395_至少有K个重复字符的最长子串 {
    class Solution {
        public int re = 0;
        public int longestSubstring(String s, int k) {
            getlongest(s, k);
            return re;
        }

        public void getlongest(String s, int k) { // 分治
            int len = s.length();
            if (len == 0)
                return;

            int[] count = new int[26];
            for (char c : s.toCharArray()) { // 计算每个字母出现的次数
                count[c - 'a']++;
            }

            int l = 0;
            boolean greater = true; // 记录是否所有字符个数都 > k



            for (int i = 0; i < len; i++) {
                if (count[s.charAt(i) - 'a'] != 0 && count[s.charAt(i) - 'a'] < k) { // 分隔处理
                    if (i > l)
                        getlongest(s.substring(l, i), k); //递归处理
                    greater = false; // 不是所有字符个数都 > k
                    l = i + 1;
                }
            }
            if(!greater && len > l) //最后一个分隔符到结尾
                getlongest(s.substring(l, len), k);
            if (greater) {
                re = Math.max(re, len);
            }
        }
    }
}
