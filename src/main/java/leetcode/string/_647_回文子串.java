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
public class _647_回文子串{
    class Solution{
        /**
         * 思路
         * 计算有多少个回文子串大的最朴素方法就是枚举所有的回文子串，枚举思路有两种
         * 1. 枚举所有的子串，然后判断这些子串是否时回文。O(n2) 枚举子串 s[l_i, r_i] ，然后用 O(n) 判断是否回文串。方法1 O(n^3)
         * 2. 枚举每一个可能的回文中心，然后用两个执政分别致信阿哥左右两边扩展，当两个指针指向的元素相同的时候就扩展，否则停止扩展。枚举回文中心呢时 O(n) ，对于每个回文中心扩展的次数也是 O(n)。所以时间复杂度是 O(n^2)
         */
        public int countSubstrings(String s) {
            int n = s.length(), ans = 0;
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


    class Solution2{
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
                f[i] = i < rMax ? Math.min(rMax - i + 1, f[2 * iMax - i]) :1;
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
    public void test(){
        String s = "abba";
        Solution2 solution2 = new Solution2();
        int i = solution2.countSubstrings(s);
        System.out.println(i);
    }
}
