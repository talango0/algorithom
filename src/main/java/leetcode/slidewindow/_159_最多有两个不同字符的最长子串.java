package leetcode.slidewindow;
// Longest Substring with At Most Two Distinct Characters 最多有两个不同字符的最长子串
//Given a string S, find the length of the longest substring T that contains at most two distinct characters.
//For example,
//Given S = "eceba",
//T is "ece" which its length is 3.
//
//给一个字符串，找出最多有两个不同字符的最长子串。还是滑动窗口法，定义一个HashMap或者Array来存储出现过的字符个数，左右指针初始位子为0，右指针向右扫，扫到新字符不同字符数加1，存入HashMap，扫到出现过的字符，HashMap对应字符数量加1。如果不同字符数大于2了，就把左指针位置的字符在HashMap中的数量减1，注意不一定是这一个，如果此时这个字符的数量大于0，说明还有这个字符存在，左指针加1继续右移，扫到的字符以后不会在窗口里了，要在HashMap中减1，如果这个字符减1后为0了，说明这个字符在窗口中没有了，此时不同字符数就可减1，左指针在右移1位指向下一个字符，统计此时窗口长度与最大长度比较，保留最大值。重复上面步骤，直到右指针扫完所有字符。最后返回最大长度。

import org.junit.Assert;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * @author mayanwei
 * @date 2022-08-28.
 */
public class _159_最多有两个不同字符的最长子串{
    class Solution {
        public int lengthOfLongestSubstringTwoDistinct(String s) {
            char[] chs = s.toCharArray();
            int l = 0, r = 0, n = chs.length, k=2;
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
        Assert.assertEquals(solution.lengthOfLongestSubstringTwoDistinct(s), 3);

    }
}
