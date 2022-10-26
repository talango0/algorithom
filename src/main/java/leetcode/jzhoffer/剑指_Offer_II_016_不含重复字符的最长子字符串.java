package leetcode.jzhoffer;

import leetcode.string._3_无重复字符的最长子串;

import java.util.HashMap;
import java.util.Map;

/**
 * @author mayanwei
 * @date 2022-10-27.
 * @see _3_无重复字符的最长子串
 * @see 剑指_Offer_II_015_字符串中的所有变位词
 * @see 剑指_Offer_II_014_字符串中的变位词
 */
public class 剑指_Offer_II_016_不含重复字符的最长子字符串{
    class Solution{
        public int lengthOfLongestSubstring(String s) {
            int n = s.length(), ans = 0;
            Map<Character, Integer> map = new HashMap<Character, Integer>(); // current index of character
            // try to extend the range [i, j]
            for (int j = 0, i = 0; j < n; j++) {
                if (map.containsKey(s.charAt(j))) {
                    i = Math.max(map.get(s.charAt(j)), i);
                }
                ans = Math.max(ans, j - i + 1);
                map.put(s.charAt(j), j + 1);
            }
            return ans;
        }

    }
}
