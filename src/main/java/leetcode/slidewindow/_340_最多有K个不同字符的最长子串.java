package leetcode.slidewindow;
//Longest Substring with At Most K Distinct Characters 最多有K个不同字符的最长子串
//Given a string, find the length of the longest substring T that contains at most k distinct characters.
//
//For example, Given s = “eceba” and k = 2,
//
//T is "ece" which its length is 3.

import org.junit.Assert;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * @author mayanwei
 * @date 2022-08-28.
 * @see _159_最多有两个不同字符的最长子串
 */
public class _340_最多有K个不同字符的最长子串{
    class Solution {
        public int lengthOfLongestSubstringKDistinct(String s, int k) {
            char[] chs = s.toCharArray();
            int l = 0, r = 0, n = chs.length;
            Map<Character,Integer> window = new HashMap<>();
            int ans = 0, cnt = 0;
            while (r < n) {
                char ch = chs[r];
                r ++;
                cnt ++;
                window.put(ch, window.getOrDefault(ch,0)+ 1);
                while (window.keySet().size() > k) {
                    char dch = chs[l];
                    l++;
                    Integer count = window.get(dch);
                    if (count == 1) {
                        window.remove(dch);
                        cnt--;
                    }
                    else {
                        window.put(dch, --count);
                        cnt--;
                    }
                }
                if (window.keySet().size() == k){
                    ans = Math.max(ans, cnt);
                }
            }
            return ans;
        }
    }
    @Test
    public void test1(){
        String s = "eceba";
        Solution solution = new Solution();
        Assert.assertEquals(solution.lengthOfLongestSubstringKDistinct(s, 2), 3);

    }
}
