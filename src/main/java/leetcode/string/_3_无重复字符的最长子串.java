package leetcode.string;

import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

public class _3_无重复字符的最长子串 {

    class Solution {
        public int lengthOfLongestSubstring(String s) {

            /* 方法 1*/
//        int n = s.length();
//        int ans = 0;
//        HashSet<Character> set = new HashSet<Character>();
//        for (int i = 0; i < n; i++) {
//            for (int k = i; k < n; k++) {
//                if (set.contains(s.charAt(k))) {
//                    ans = Math.max(ans, k - i);
//                    set.clear();
//                    break;
//                } else {
//                    set.add(s.charAt(k));
//                    ans = Math.max(ans, k - i + 1);
//
//                }
//            }
//        }
//        return ans;
            /* 方法2 */
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

    @Test
    public void test(){
        Solution solution = new Solution();
        String s = "abcabcda";
        int tmp = solution.lengthOfLongestSubstring(s);

        System.out.println(tmp);
    }

}

