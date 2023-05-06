package leetcode.dp;
//实现 strStr() 函数。
//
// 给你两个字符串 haystack 和 needle ，请你在 haystack 字符串中找出 needle 字符串出现的第一个位置（下标从 0 开始）。如
//果不存在，则返回 -1 。
//
// 说明：
//
// 当 needle 是空字符串时，我们应当返回什么值呢？这是一个在面试中很好的问题。
//
// 对于本题而言，当 needle 是空字符串时我们应当返回 0 。这与 C 语言的 strstr() 以及 Java 的 indexOf() 定义相符。
//
//
//
// 示例 1：
//
//
//输入：haystack = "hello", needle = "ll"
//输出：2
//
//
// 示例 2：
//
//
//输入：haystack = "aaaaa", needle = "bba"
//输出：-1
//
//
//
//
// 提示：
//
//
// 1 <= haystack.length, needle.length <= 10⁴
// haystack 和 needle 仅由小写英文字符组成
//
//
// Related Topics 双指针 字符串 字符串匹配 👍 1522 👎 0

import org.junit.Assert;
import org.junit.Test;

/**
 * @author mayanwei
 * @date 2022-07-31.
 */
public class _28_实现strStr{
    class Solution_BruteForce{
        public int strStr(String haystack, String needle) {
            if (haystack == null || needle == null) {
                return -1;
            }
            int m = haystack.length(), n = needle.length();
            int i = 0, j = 0;
            for (;i < m && j < n; i++) {
                if (haystack.charAt(i) == needle.charAt(j)) {
                    j++;
                }
                else {
                    i -= j;
                    j = 0;
                }
            }
            return j == n ? i - n : m;
        }

    }
    class Solution {
        public int strStr(String haystack, String needle) {
            KMP kmp = new KMP(needle);
            return kmp.search(haystack);
        }
        class KMP {
            private String pat;
            private int [][] dfa;
            public  KMP(String pat){
                //O(M)
                //有模式字符串构造dfa
                this.pat = pat;
                int M = pat.length();
                int R = 256;
                // dp[字符][状态]
                dfa = new int[R][M];
                // base case 只有遇到 pat[0] 这个字符才能使状态从 0 转移到 1，遇到其他字符的话还停留在状态 0 （java默认初始化数组全为0）
                dfa[pat.charAt(0)][0] = 1;
                // X（影子状态）初始化为0，当前状态j 从1开始
                for(int X = 0 ,j = 1; j< M; j++){
                    // 计算dfa[][j]
                    for(int c = 0; c < R; c++){
                        //匹配失败情况下的值
                        dfa[c][j] = dfa[c][X];
                    }
                    //设置匹配成功的值
                    dfa[pat.charAt(j)][j] = j+1;
                    //更新重启状态
                    X = dfa[pat.charAt(j)][X];
                }
            }
            public int search(String txt){
                //O(N)
                //在txt上模拟dfa的运行
                int i, j, N = txt.length(), M = pat.length();
                for( i = 0 ,j = 0; i < N && j < M; i++){
                    j = dfa[txt.charAt(i)][j];
                }
                if(j == M) {
                    return i-M;
                }else {
                    return -1;
                }
            }
        }
    }

    @Test
    public void testStrStr(){
        Solution solution = new Solution();
        Assert.assertEquals(2, solution.strStr("hello", "ll"));
        Solution_BruteForce solutionBruteForce = new Solution_BruteForce();
        Assert.assertEquals(2, solutionBruteForce.strStr("hello", "ll"));

        Assert.assertEquals(2, "hello".indexOf("ll"));

    }
}
