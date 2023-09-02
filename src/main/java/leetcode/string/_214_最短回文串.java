package leetcode.string;

//给定一个字符串 s，你可以通过在字符串前面添加字符将其转换为回文串。找到并返回可以用这种方式转换的最短回文串。
//
//
//
//示例 1：
//
//输入：s = "aacecaaa"
//输出："aaacecaaa"
//示例 2：
//
//输入：s = "abcd"
//输出："dcbabcd"
//
//
//提示：
//
//0 <= s.length <= 5 * 104
//s 仅由小写英文字母组成

import java.util.Arrays;

/**
 * @author mayanwei
 * @date 2022-09-12.
 * @see SubStringSearch.RabinKarp
 */
public class _214_最短回文串{
    /**
     * <pre>
     *  s :       abccda
     *  s':  adccb
     *
     *  |s'| < |s|
     *           ┌──┬─────┐
     *        s  │s1│s2   │
     *           │ a│bccda│
     *           └──┴─────┘
     *
     * If we want to get shortest s', we can maximum the prefix s1
     * which is the palindrome string, then the reverse str of
     * s2 will be s'.
     * </pre>
     *
     */
    class Solution{
        /**
         * 一个字符串是回文串，当且仅当该字符串与它的反序相同。因此我们仍然暴力地枚举S1接结束位置，并计算S1与S1反序的哈希值。如果两个哈希值相等，
         * 说明我们找到了一个s的前缀回文串。
         * 在枚举 S1 的结束位置时，我们可以从小到大地进行枚举，这样就可以很容易地维护 S1 与 S1 反序的哈希值。
         * 设当前枚举到的结束位置为 i ，对应S1记为 S1_i，其反序记为 S1'_i. 通过递推的方式，在O(1)的时间通过 S1_i-1 S1'_i-1 的哈希值得到
         * S1_i 和 S1‘_i的哈希值。
         * hash(s1_i) = hash(s1_i-1) * base + ASCCI(s[i])
         * hash(s1'_i) = hash(s1'_i-1) + ASCII(s[i]) * base_i
         * @param s
         * @return
         */
        public String shortestPalindrome(String s) {
            if(null == s || s.length() == 0){
                return "";
            }
            int n = s.length();
            //投机取巧
            int base = 131, mod = 1000000007;
            int left = 0, right = 0, mul = 1;
            int best = -1;
            for (int i = 0; i < n; i++) {
                left = (int) (((long) left * base + s.charAt(i)) % mod);
                right = (int) ((right + (long) mul * s.charAt(i)) % mod);
                if (left == right) {
                    best = i;
                }
                mul = (int) ((long) mul * base % mod);
            }
            String add = (best == n - 1 ? "" :s.substring(best + 1));
            StringBuffer ans = new StringBuffer(add).reverse();
            ans.append(s);
            return ans.toString();
        }
    }


    /**
     * 利用KMP字符串匹配算法来找出这个最长的前缀回文串
     * 具体地，记 s' 为 s的反序，由于 s1 是 s 的前缀，那么 s1‘ 就是 s’ 的后缀。
     * 考虑到 s1 是一个回文串，因此 s1 = s1' , s1 同样是 s' 的后缀。
     *
     * 这样一来，我们将 s 作为回文串 s‘ 作为查询串进行匹配。当遍历到 s' 的末尾时，如果匹配到s中的第 i 个字符，那么说明 s 的前 i 个字符
     * 与 s'的后 i个字符相匹配（即相同）， s的前 i 个字符对应 s1，s'的后i个字符对应s1’，由于s1 = s1',因此 s1 就是一个回文串。
     * 如果存在更长的回文串，那么 KMP 算法的匹配结果也会大于 i，因此 s_1 就是最长的前缀回文串。
     *
     */
    class Solution2{
        public String shortestPalindrome(String s) {
            int n = s.length();
            int[] fail = new int[n];
            Arrays.fill(fail, -1);
            for (int i = 1; i < n; ++i) {
                int j = fail[i - 1];
                while (j != -1 && s.charAt(j + 1) != s.charAt(i)) {
                    j = fail[j];
                }
                if (s.charAt(j + 1) == s.charAt(i)) {
                    fail[i] = j + 1;
                }
            }
            int best = -1;
            for (int i = n - 1; i >= 0; --i) {
                while (best != -1 && s.charAt(best + 1) != s.charAt(i)) {
                    best = fail[best];
                }
                if (s.charAt(best + 1) == s.charAt(i)) {
                    ++best;
                }
            }
            String add = (best == n - 1 ? "" : s.substring(best + 1));
            StringBuffer ans = new StringBuffer(add).reverse();
            ans.append(s);
            return ans.toString();
        }

    }
}
