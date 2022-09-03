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
public class _395_至少有K个重复字符的最长子串{
    /**
     * 递归的思路：如果某个字符串出现次数大于0小于k，那么包含这个字符的子串都不满足要求。所以按照这个字符来切分整个字符串，满足题意的最长子串
     * 一定不包含切分的字符，切分完去除最长子串即可。
     * <p>
     * 时间负载度 O(26*n),空间负载度 O(26^2)
     */
    class Solution{
        public int ans = 0;

        public int longestSubstring(String s, int k) {
            getlongest(s, k);
            return ans;
        }

        public void getlongest(String s, int k) { // 分治
            int len = s.length();
            if (len == 0) {
                return;
            }

            int[] count = new int[26];
            for (char c : s.toCharArray()) { // 计算每个字母出现的次数
                count[c - 'a']++;
            }

            int l = 0;
            // 记录是否所有字符个数都 > k,如果都大于，则该字符串是一个有效的字符串
            boolean allGreater = true;

            for (int i = 0; i < len; i++) {
                // 分隔处理，如果遇到小于k的字母，则该字母是能够给阻隔断前面部分的子串的长度的，前面作为子串
                if (count[s.charAt(i) - 'a'] != 0 && count[s.charAt(i) - 'a'] < k) {
                    if (i > l) {
                        getlongest(s.substring(l, i), k); //递归处理
                    }
                    allGreater = false; // 不是所有字符个数都 > k
                    l = i + 1;
                }
            }
            //最后一个分隔符到结尾
            if (!allGreater && len > l) {
                getlongest(s.substring(l, len), k);
            }
            if (allGreater) {
                ans = Math.max(ans, len);
            }
        }
    }

    /**
     * 思路：滑动窗口
     * <p>
     * 窗口移动的条件：
     * 此题要求最长字符串，那么最终的字符串内包含的字符种类最多包含 26 种。字符种类就是窗口移动的条件。
     * 依次枚举字符种类，如果当前窗口内的字符种类小于当前枚举的字符种类，则窗口右移，否则左移动。
     * <p>
     * 窗口移动中需要动态维护freq频次数组。用一个变量 lessK 记录当前次数小于k次的字符类数。如果 freq 为 0，
     * 说明小于k次的字符种类要发生变化，如果是右移窗口，那么 less ++， 如果左移窗口，那么 less --。同理如果 freq 为k，
     * 说明小于k次的字符种类药变化，如果是右窗口移动，那么 less--， 如果是左窗口移动，那么 less ++
     * <p>
     * 时间复杂度 O(26 * n)
     * 空间复杂度 O(26)
     */
    class Solution2{
        public int longestSubstring(String s, int k) {
            int ans = 0;
            int n = s.length();
            int[] freq = new int[26];
            int total = 0, lessK = 0;
            int left = 0, right = -1;
            for (int i = 0; i <= 26; i++) {
                left = 0;
                right = -1;
                while (left < n) {
                    // 当前窗口内的字符种类小于当前枚举的字符种类，则窗口右移，否则左移动
                    if (right + 1 < n && total <= i) {
                        if (freq[s.charAt(right + 1) - 'a'] == 0) {
                            total++;
                            lessK++;
                        }
                        freq[s.charAt(right + 1) - 'a']++;
                        if (freq[s.charAt(right + 1) - 'a'] == k) {
                            lessK--;
                        }
                        right++;
                    }
                    else {
                        if (freq[s.charAt(left) - 'a'] == k) {
                            lessK++;
                        }
                        freq[s.charAt(left) - 'a']--;
                        if (freq[s.charAt(left) - 'a'] == 0) {
                            total--;
                            lessK--;
                        }
                        left++;
                    }
                    // 表示窗口中的所有字符串都是大于等于k的
                    if (lessK == 0) {
                        ans = Math.max(ans, right - left + 1);
                    }
                }
            }
            return ans;
        }
    }


}
