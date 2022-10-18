package leetcode.dp;
//给你一个字符串 s 和一个字符规律 p，请你来实现一个支持 '.' 和 '*' 的正则表达式匹配。
//
//
// '.' 匹配任意单个字符
// '*' 匹配零个或多个前面的那一个元素
//
//
// 所谓匹配，是要涵盖 整个 字符串 s的，而不是部分字符串。
//
//
// 示例 1：
//
//
//输入：s = "aa" p = "a"
//输出：false
//解释："a" 无法匹配 "aa" 整个字符串。
//
//
// 示例 2:
//
//
//输入：s = "aa" p = "a*"
//输出：true
//解释：因为 '*' 代表可以匹配零个或多个前面的那一个元素, 在这里前面的元素就是 'a'。因此，字符串 "aa" 可被视为 'a' 重复了一次。
//
//
// 示例 3：
//
//
//输入：s = "ab" p = ".*"
//输出：true
//解释：".*" 表示可匹配零个或多个（'*'）任意字符（'.'）。
//
//
// 示例 4：
//
//
//输入：s = "aab" p = "c*a*b"
//输出：true
//解释：因为 '*' 表示零个或多个，这里 'c' 为 0 个, 'a' 被重复一次。因此可以匹配字符串 "aab"。
//
//
// 示例 5：
//
//
//输入：s = "mississippi" p = "mis*is*p*."
//输出：false
//
//
//
// 提示：
//
//
// 1 <= s.length <= 20
// 1 <= p.length <= 30
// s 可能为空，且只包含从 a-z 的小写字母。
// p 可能为空，且只包含从 a-z 的小写字母，以及字符 . 和 *。
// 保证每次出现字符 * 时，前面都匹配到有效的字符
//
// Related Topics 字符串 动态规划 回溯算法
// 👍 1701 👎 0
import org.junit.Assert;
import org.junit.jupiter.api.Test;

public class _10_正则表达式匹配 {

    /**
     * 分析：
     * 用f[i][j] 表示s的前i个字符与p中的前j个字符是否匹配。在状态转移时，考虑p的第j个字符的匹配情况：
     *
     * 1. p[j] != '*'
     *       f[i][j] = matches(s[i],p[j])  ? f[i-1][j-1] : false
     * 2. p[j] == '*'
     *      f[i][j] = matches(s[i], p[j-1]) ? (f[i-1][j] or f[i][j-2]) : f[i][j-2]
     * 初始状态 f[0][0] = true,表示空字符串为true，最终的答案为f[m][n]
     *
     * 空间复杂度 O(m*n)
     * 时间复杂度 O(n*n)
     */
    static class Solution {
        public boolean isMatch(String s, String p) {
            int m = s.length(), n = p.length();
            boolean[][] f = new boolean[m + 1][n + 1];
            f[0][0] = true;
            for(int i = 0; i <= m; i++){
                for(int j = 1; j<= n; j++){
                    if(p.charAt(j - 1) == '*'){
                        f[i][j] = f[i][j-2];
                        if(matches(s, p, i, j-1)){
                            f[i][j] = f[i][j] ||  f[i-1][j];
                        }
                    }else{
                        if(matches(s, p, i, j)){
                            f[i][j] = f[i-1][j-1];
                        }
                    }
                }
            }
            return f[m][n];
        }

        private boolean matches(String s, String p, int i, int j) {
            if(i == 0){
                return false;
            }
            if(p.charAt(j-1) == '.'){
                return true;
            }
            return s.charAt(i-1) == p.charAt(j-1);
        }
    }
    /**
     * 以一个例子详解动态规划转移方程：
     * S = abbbbc
     * P = ab*d*c
     * 1. 当 i, j 指向的字符均为字母（或 '.' 可以看成一个特殊的字母）时，
     *    只需判断对应位置的字符即可，
     *    若相等，只需判断 i,j 之前的字符串是否匹配即可，转化为子问题 f[i-1][j-1].
     *    若不等，则当前的 i,j 肯定不能匹配，为 false.
     *
     *        f[i-1][j-1]   i
     *             |        |
     *    S [a  b  b  b  b][c]
     *
     *    P [a  b  *  d  *][c]
     *                      |
     *                      j
     *
     *
     * 2. 如果当前 j 指向的字符为 '*'，则不妨把类似 'a*', 'b*' 等的当成整体看待。
     *    看下面的例子
     *
     *             i
     *             |
     *    S  a  b [b] b  b  c
     *
     *    P  a [b  *] d  *  c
     *             |
     *             j
     *
     *    注意到当 'b*' 匹配完 'b' 之后，它仍然可以继续发挥作用。
     *    因此可以只把 i 前移一位，而不丢弃 'b*', 转化为子问题 f[i-1][j]:
     *
     *          i
     *          | <--
     *    S  a [b] b  b  b  c
     *
     *    P  a [b  *] d  *  c
     *             |
     *             j
     *
     *    另外，也可以选择让 'b*' 不再进行匹配，把 'b*' 丢弃。
     *    转化为子问题 f[i][j-2]:
     *
     *             i
     *             |
     *    S  a  b [b] b  b  c
     *
     *    P [a] b  *  d  *  c
     *       |
     *       j <--
     *
     * 3. 冗余的状态转移不会影响答案，
     *    因为当 j 指向 'b*' 中的 'b' 时, 这个状态对于答案是没有用的,
     *    当 j 指向 '*' 时,
     *    dp[i][j]只与dp[i][j-2]有关, 跳过了 dp[i][j-1].
     */



    class Solution3 {
        public boolean isMatch(String s, String p) {
            if (s == null && p == null){
                return true;
            }
            if ((s!=null && p ==null )|| (s== null && p!=null)){
                return false;
            }
            int m = s.length();
            int n = p.length();

            // dp[i][j] 表示 s 前 i 位和 p 前 j 位是否匹配， 这样 dp[m][n] 表示 s 和 p 是否匹配
            boolean dp[][] = new boolean[m+1][n+1];
            // 初始状态设置
            // 1. 当 s 和 p 都是空串的时候true
            dp[0][0] = true;
            // 2. 下面两个for循环分别表示当 s 为空和 p 为空的情况
            // for (int i = 1; i<= m; i++) {
            //     dp[i][0] = false;
            // }

            for (int j = 1; j < dp[0].length; j++) {
                char ch = p.charAt(j-1);
                if (j > 1) {
                    if (ch == '*') {
                        dp[0][j] = dp[0][j-2];
                    } else {
                        dp[0][j] = false;
                    }
                } else {
                    if (ch == '*') {
                        dp[0][j] = true;
                    }
                }
            }
            for (int row = 1; row<dp.length; row++){
                char ch1 = s.charAt(row-1);
                for(int col = 1; col<dp[row].length; col++) {
                    char ch2 = p.charAt(col-1);
                    if (ch1 == ch2 || ch2 == '.') {
                        dp[row][col] = dp[row-1][col-1];
                    }
                    else if (ch2 == '*') {
                        if (col > 1) {
                            if (dp[row][col-2]) {
                                dp[row][col] = true;
                            }
                            else{
                                char prev = p.charAt(col-2);
                                if (prev == ch1 || prev=='.') {
                                    dp[row][col] = dp[row-1][col];
                                }
                            }
                        }
                    }
                    // else {
                    //     dp[row][col] = false;
                    // }
                }
            }
            return dp[m][n];
        }
    }
    @Test
    void testSolution(){
        Solution3 solution = new Solution3();
        Assert.assertEquals(true, solution.isMatch("aab","c*a*b"));
    }
}
