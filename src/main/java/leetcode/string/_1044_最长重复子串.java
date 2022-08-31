package leetcode.string;
//给你一个字符串 s ，考虑其所有 重复子串 ：即 s 的（连续）子串，在 s 中出现 2 次或更多次。这些出现之间可能存在重叠。
//
//返回 任意一个 可能具有最长长度的重复子串。如果 s 不含重复子串，那么答案为 "" 。
//
//示例 1：
//
//输入：s = "banana"
//输出："ana"
//示例 2：
//
//输入：s = "abcd"
//输出：""
//提示：
//
//2 <= s.length <= 3 * 104
//s 由小写英文字母组成
//Related Topics
//
//👍 335, 👎 0

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

/**
 * 字节
 * @author mayanwei
 * @date 2022-08-23.
 * @see _187_重复的DNA序列
 * @see SubStringSearch.RabinKarp
 */
public class _1044_最长重复子串{
    // 提示1:Binary search for the length of the answer. (If there's an answer of length 10, then there are answers of length 9, 8, 7, ...)
    // 提示2: To check whether an answer of length K exists, we can use Rabin-Karp 's algorithm.

    class Solution{
        // 字符串哈希加二分查找
        long[] h, p;

        public String longestDupSubstring(String s) {
            int P = 1313131, n = s.length();
            h = new long[n + 10];
            p = new long[n + 10];
            p[0] = 1;
            for (int i = 0; i < n; i++) {
                p[i + 1] = p[i] * P;
                h[i + 1] = h[i] * P + s.charAt(i);
            }
            String ans = "";
            int l = 0, r = n;
            while (l < r) {
                int mid = l + r + 1 >> 1;
                String t = check(s, mid);
                if (t.length() != 0) {
                    l = mid;
                }
                else {
                    r = mid - 1;
                }
                ans = t.length() > ans.length() ? t :ans;
            }
            return ans;
        }

        String check(String s, int len) {
            int n = s.length();
            Set<Long> set = new HashSet<>();
            for (int i = 1; i + len - 1 <= n; i++) {
                int j = i + len - 1;
                long cur = h[j] - h[i - 1] * p[j - i + 1];
                if (set.contains(cur)) {
                    return s.substring(i - 1, j);
                }
                set.add(cur);
            }
            return "";
        }
    }

    class Solution2{
        public String longestDupSubstring(String s) {
            Random random = new Random();
            // 生成两个进制
            int a1 = random.nextInt(75) + 26;
            int a2 = random.nextInt(75) + 26;
            // 生成两个模
            int mod1 = random.nextInt(Integer.MAX_VALUE - 1000000007 + 1) + 1000000007;
            int mod2 = random.nextInt(Integer.MAX_VALUE - 1000000007 + 1) + 1000000007;
            int n = s.length();
            // 先对所有字符进行编码
            int[] arr = new int[n];
            for (int i = 0; i < n; ++i) {
                arr[i] = s.charAt(i) - 'a';
            }
            // 二分查找的范围是[1, n-1]
            int l = 1, r = n - 1;
            int length = 0, start = -1;
            while (l <= r) {
                int m = l + (r - l + 1) / 2;
                int idx = check(arr, m, a1, a2, mod1, mod2);
                if (idx != -1) {
                    // 有重复子串，移动左边界
                    l = m + 1;
                    length = m;
                    start = idx;
                }
                else {
                    // 无重复子串，移动右边界
                    r = m - 1;
                }
            }
            return start != -1 ? s.substring(start, start + length) :"";
        }

        public int check(int[] arr, int m, int a1, int a2, int mod1, int mod2) {
            int n = arr.length;
            long aL1 = pow(a1, m, mod1);
            long aL2 = pow(a2, m, mod2);
            long h1 = 0, h2 = 0;
            for (int i = 0; i < m; ++i) {
                h1 = (h1 * a1 % mod1 + arr[i]) % mod1;
                h2 = (h2 * a2 % mod2 + arr[i]) % mod2;
                if (h1 < 0) {
                    h1 += mod1;
                }
                if (h2 < 0) {
                    h2 += mod2;
                }
            }
            // 存储一个编码组合是否出现过
            Set<Long> seen = new HashSet<Long>();
            seen.add(h1 * mod2 + h2);
            for (int start = 1; start <= n - m; ++start) {
                h1 = (h1 * a1 % mod1 - arr[start - 1] * aL1 % mod1 + arr[start + m - 1]) % mod1;
                h2 = (h2 * a2 % mod2 - arr[start - 1] * aL2 % mod2 + arr[start + m - 1]) % mod2;
                if (h1 < 0) {
                    h1 += mod1;
                }
                if (h2 < 0) {
                    h2 += mod2;
                }

                long num = h1 * mod2 + h2;
                // 如果重复，则返回重复串的起点
                if (!seen.add(num)) {
                    return start;
                }
            }
            // 没有重复，则返回-1
            return -1;
        }

        public long pow(int a, int m, int mod) {
            long ans = 1;
            long contribute = a;
            while (m > 0) {
                if (m % 2 == 1) {
                    ans = ans * contribute % mod;
                    if (ans < 0) {
                        ans += mod;
                    }
                }
                contribute = contribute * contribute % mod;
                if (contribute < 0) {
                    contribute += mod;
                }
                m /= 2;
            }
            return ans;
        }
    }
}
