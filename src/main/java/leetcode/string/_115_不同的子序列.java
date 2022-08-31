package leetcode.string;
//给定一个字符串 s 和一个字符串 t ，计算在 s 的子序列中 t 出现的个数。
//
//字符串的一个 子序列 是指，通过删除一些（也可以不删除）字符且不干扰剩余字符相对位置所组成的新字符串。（例如，"ACE" 是 "ABCDE" 的一个子序列，而 "AEC" 不是）
//
//题目数据保证答案符合 32 位带符号整数范围。
//
//示例 1：
//
//输入：s = "rabbbit", t = "rabbit"
//
//输出
//：
//3
//解释：
//如下图所示, 有 3 种可以从 s 中得到
//"rabbit" 的方案
//。
//rabbbit
//
//rabbbit
//
//rabbbit
//示例 2：
//
//输入：s = "babgbag", t = "bag"
//输出
//：
//5
//解释：
//如下图所示, 有 5 种可以从 s 中得到
//"bag" 的方案
//。
//babgbag
//
//babgbag
//
//babgbag
//
//babgbag
//
//babgbag
//
//提示：
//
//0 <= s.length, t.length <= 1000
//s 和 t 由英文字母组成
//Related Topics
//
//👍 842, 👎 0

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.HashSet;

/**
 * 字节
 * @author mayanwei
 * @date 2022-08-14.
 */
public class _115_不同的子序列{
    /**
     * 视角一，站在t的角度进行穷举：
     * <p>
     * 我们的原问题是求s[0..]的所有子序列中t[0..]出现的次数，那么可以先看t[0]在s中的什么位置，
     * <p>
     * 假设s[2], s[6]是字符t[0]，那么原问题转化成了在s[2..]和s[6..]的所有子序列中计算t[1..]出现的次数。
     * <p>
     * 带备忘录的动态规划算法的时间复杂度
     * <p>
     * = 子问题的个数 x 函数本身的时间复杂度
     * <p>
     * = 「状态」的个数 x 函数本身的时间复杂度
     * <p>
     * = O(MN) * O(M)
     * <p>
     * = O(N * M^2)
     */
    class Solution{
        private int[][] memo;

        public int numDistinct(String s, String t) {
            memo = new int[s.length()][t.length()];
            for (int[] row : memo) {
                Arrays.fill(row, -1);
            }
            return dp(s, 0, t, 0);
        }


        /**
         * s[i..] 的子序列 t[j..] 出现的次数为 dp(s, i, t, j)
         */
        int dp(String s, int i, String t, int j) {
            //base case
            if (j == t.length()) {
                // t已经全部匹配完成
                return 1;
            }
            if (s.length() - i < t.length() - j) {
                // s[i..]比t[j..]还短，必然没有匹配的子序列
                return 0;
            }
            if (memo[i][j] != -1) {
                return memo[i][j];
            }
            // 站在 t 的角度
            int res = 0;
            for (int k = i; k < s.length(); k++) {
                if (s.charAt(k) == t.charAt(j)) {
                    // 累计结果
                    res += dp(s, k + 1, t, j + 1);
                }
            }
            // 存入备忘录
            memo[i][j] = res;
            return res;
        }
    }


    /**
     * 视角二，站在s的角度进行穷举：
     * <p>
     * 我们的原问题是计算s[0..]的所有子序列中t[0..]出现的次数，可以先看看s[0]是否能匹配t[0]，如果不匹配，那没得说，原问题就可以转化为计算s[1..]的所有子序列中t[0..]出现的次数；
     * <p>
     * 但如果s[0]可以匹配t[0]，那么又有两种情况，这两种情况是累加的关系：
     * <p>
     * 1、让s[0]匹配t[0]，那么原问题转化为在s[1..]的所有子序列中计算t[1..]出现的次数。
     * <p>
     * 2、不让s[0]匹配t[0]，那么原问题转化为在s[1..]的所有子序列中计算t[0..]出现的次数。
     * <p>
     * 为啥明明s[0]可以匹配t[0]，还不让它俩匹配呢？主要是为了给s[0]之后的元素匹配的机会，比如s = "aab", t = "ab"，就有两种匹配方式：a_b和_ab。
     * <p>
     * 这个解法中dp函数递归的次数，即状态i, j的不同组合的个数为 O(MN)，而dp函数本身没有 for 循环，
     * 即时间复杂度为 O(1)，所以算法总的时间复杂度就是 O(MN)，比刚才的解法要好一些
     */
    class Solution2{
        // 这个解法中dp函数递归的次数，即状态i, j的不同组合的个数为 O(MN)，而dp函数本身没有 for 循环，即时间复杂度为 O(1)，所以算法总的时间复杂度就是 O(MN)
        private int[][] memo;

        public int numDistinct(String s, String t) {
            memo = new int[s.length()][t.length()];
            for (int[] row : memo) {
                Arrays.fill(row, -1);
            }
            return dp(s, 0, t, 0);
        }


        /**
         * s[i..] 的子序列 t[j..] 出现的次数为 dp(s, i, t, j)
         */
        int dp(String s, int i, String t, int j) {
            //base case
            if (j == t.length()) {
                // t已经全部匹配完成
                return 1;
            }
            if (s.length() - i < t.length() - j) {
                // s[i..]比t[j..]还短，必然没有匹配的子序列
                return 0;
            }
            if (memo[i][j] != -1) {
                return memo[i][j];
            }

            int res = 0;
            // 站在 s 的角度看
            if (s.charAt(i) == t.charAt(j)) {
                //匹配，两种情况，累加关系
                res += dp(s, i + 1, t, j + 1) + dp(s, i + 1, t, j);
            }
            else {
                // 不匹配, 在 s[i+1,...] 的子序列中计算 t[j..]的出现次数
                res += dp(s, i + 1, t, j);
            }
            // 存入备忘录
            memo[i][j] = res;
            return res;
        }
    }

    @Test
    public void test() {
        Solution solution = new Solution();
        System.out.println(solution.numDistinct(
                "rabbbit", "rabbit"
        ));


    }
}
