package leetcode.string;

/**
 * @author mayanwei
 * @date 2022-09-12.
 * @see SubStringSearch.RabinKarp
 */
public class _214_最短回文串{
    /**
     *  s :       abccda
     *  s':  adccb
     *
     *  |s'| < |s|
     *           ┌──┬─────┐
     *        s  │s1│s2   │
     *           │ a│bccda│
     *           └──┴─────┘
     *
     * If we want to get shortest s', we can maximum the s1
     * which is the palindrome string then the reverse str of
     * s2 will be s'.
     *
     */
    class Solution{
        public String shortestPalindrome(String s) {
            int n = s.length();
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
}
