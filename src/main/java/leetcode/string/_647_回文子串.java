package leetcode.string;
//给你一个字符串 s ，请你统计并返回这个字符串中 回文子串 的数目。
//
// 回文字符串 是正着读和倒过来读一样的字符串。
//
// 子字符串 是字符串中的由连续字符组成的一个序列。
//
// 具有不同开始位置或结束位置的子串，即使是由相同的字符组成，也会被视作不同的子串。
//
//
//
// 示例 1：
//
//
//输入：s = "abc"
//输出：3
//解释：三个回文子串: "a", "b", "c"
//
//
// 示例 2：
//
//
//输入：s = "aaa"
//输出：6
//解释：6个回文子串: "a", "a", "a", "aa", "aa", "aaa"
//
//
//
// 提示：
//
//
// 1 <= s.length <= 1000
// s 由小写英文字母组成
//
//
// Related Topics 字符串 动态规划 👍 983 👎 0

import leetcode.dp._1312_让字符串成为回文串的最少插入次数;
import org.junit.jupiter.api.Test;

/**
 * @author mayanwei
 * @date 2022-09-30.
 * @see _1312_让字符串成为回文串的最少插入次数
 */
public class _647_回文子串 {
    class Solution {
        /**
         * 思路
         * 计算有多少个回文子串大的最朴素方法就是枚举所有的回文子串，枚举思路有两种
         * 1. 枚举所有的子串，然后判断这些子串是否时回文。O(n2) 枚举子串 s[l_i, r_i] ，然后用 O(n) 判断是否回文串。方法1 O(n^3)
         * 2. 枚举每一个可能的回文中心，然后用两个指针向左右两边扩展，当两个指针指向的元素相同的时候就扩展，
         * 否则停止扩展。枚举回文中心 O(n) ，对于每个回文中心扩展的次数也是 O(n)。所以时间复杂度是 O(n^2)
         */
        public int countSubstrings(String s) {
            int n = s.length(), ans = 0;
            //   a b c 有可能的回文中心2n个
            for (int i = 0; i < 2 * n - 1; i++) {
                int l = i / 2, r = i / 2 + i % 2;
                while (l >= 0 && r < n && s.charAt(l) == s.charAt(r)) {
                    l--;
                    r++;
                    ans++;
                }
            }
            return ans;
        }
    }


    /**
     * 回文串需要考虑长度奇偶位的两种情况
     * Manacher 算法在字符串各个位之间加入相同的特殊字符，比如 # ，"abaa" 会处理成 "#a#b#a#a#" 这样能够统一看成奇位字符串。
     * <p>
     * 把"#a#b#a#a#"  记作 s。
     * 用 f[i] 表示 s 的第 i 位为回文中心，可以扩展出的最大回文半径，那么 f[i] 就是以 i 为中心的最大回文串长度。
     * <p>
     * Manacher 算法需要枚举s的每一个位置并先假设它是回文中心，但是它会利用已经计算出来的状态更新 f[i] ，而不是向
     * 【中心扩展】一样的盲目扩展。具体而言，假设我们已经计算好了 [1, i-1] 区间内所有点的 f,那么我们也就知道了 [1, i-1]
     * 拓展除的回文达到最大半径时的回文右端点。例如i= 6时， f[i] = 4 ，说明第6个元素为回文中心，最大能拓展的半径是4，
     * 此时右端点为 6+4-1 = 9。所以当我们知道一个i对应的f(i)的时候，我们可以很容易得到它的右端点 i + f[i] -1。
     * <pre>
     * ┌───┬──────────────────────────────────────────┐
     * │ i │0 1 2 3 4 5 6 7 8 9 1011121314151617181920│
     * │ s │$ # a # b # c # b # p # b # c # b # p # ! │
     * │ f │1 1 2 1 2 1 4 1 2 1 8 1 2 1 6 1 2 1 2 1 1 │
     * └───┴──────────────────────────────────────────┘
     * </pre>
     * Manacher如何通过已经计算出的状态来更新 f[i] 呢？
     * <p>
     * 维护【当前最大回文串右端点 r_m】以及【这个回文串右端点对应的回文中心为 i_m】。
     * 顺序遍历s，假设当前遍历的下标为i。我们知道求解 f[i] 之前已经得到了 [1, i-1] 所有的 f，
     * 并且当前已经有了一个最大回文右端点 r_m 以及它对应的回文中心 i_m;
     * <p>
     * 1.初始化 f[i]
     * <p>
     * 若 i < r_m，说明i被包含在当前最大回文子串内，假设 j 是 i 关于这个最大回文的回文中心 i_m 的对称位置 (即 i+j = 2 * i_m),
     * f[i] 至少等于 min{f[j], r_m-i+1}。这里将 f[j] 和 r_m -j +1 取小，是先要保证这个回文串在当前最大回文串内。
     * 若 i >= r_m，先初始化为 f[i] = 1
     * <p>
     * 2. 中心拓展
     * <p>
     * 做完初始化之后，可以保证此时 s[i + f[i] -1] = s[i-f[i] + 1],要继续拓展这个区间，就需要继续判断
     * s[i+f[i]] 于 s[i-f[i]]是否相等，如果相等将 f[i] 自增。这样循环直到 s[i+f[i]] != s[i-f[i]]。
     * 这样我们可以得到 s 所有点为中心的最大回文半径，也就能够得到 S 中所有可能的回文中心的的最大回文半径，把它们累加就可以得到答案。
     */
    class Solution2 {
        /**
         * Manacher
         * 算法是时间复杂度 O(n),空间复杂度 O(n)
         */
        public int countSubstrings(String s) {
            int n = s.length();
            StringBuffer t = new StringBuffer("$#");
            for (int i = 0; i < n; i++) {
                t.append(s.charAt(i));
                t.append('#');
            }
            n = t.length();
            t.append('!');

            int[] f = new int[n];
            int iMax = 0, rMax = 0, ans = 0;
            for (int i = 1; i < n; i++) {
                // 初始化 f[i]
                f[i] = i < rMax ? Math.min(rMax - i + 1, f[2 * iMax - i]) : 1;
                // 中心拓展
                while (t.charAt(i + f[i]) == t.charAt(i - f[i])) {
                    f[i]++;
                }
                // 动态维护 iMax 和 rMax
                if (i + f[i] - 1 > rMax) {
                    iMax = i;
                    rMax = i + f[i] - 1;
                }
                // 统计答案，当前贡献 (f[i]-1)/2 上取整
                ans += f[i] / 2;
            }

            return ans;
        }
    }

    @Test
    public void test() {
        String s = "abcbpbcbp";
        Solution2 solution2 = new Solution2();
        int i = solution2.countSubstrings(s);
        System.out.println(i);
    }
}
