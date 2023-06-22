package leetcode.程序员面试金典;
//编写一个方法，计算从 0 到 n (含 n) 中数字 2 出现的次数。
//
//示例:
//
//输入: 25
//输出: 9
//解释: (2, 12, 20, 21, 22, 23, 24, 25)(注意 22 应该算作两次)
//提示：
//
//n <= 10^9
//
//
//来源：力扣（LeetCode）
//链接：https://leetcode.cn/problems/number-of-2s-in-range-lcci
//著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。

import org.junit.jupiter.api.Test;

import java.util.Arrays;

/**
 * @author mayanwei
 * @date 2023-06-21.
 */
public class _17_06_2出现的次数{
    /**
     * 暴力法
     */
    class Solution0{
        public int numberOf2sInRange(int n) {
            int count = 0;
            for (int i = 2; i <= n; i++) {
                count += number2Of(i);
            }
            return count;
        }

        /**
         * 统计某个数字中有几个2
         *
         * @param n
         * @return
         */
        private int number2Of(int n) {
            int count = 0;
            while (n > 0) {
                if (n % 10 == 2) {
                    count++;
                }
                n /= 10;
            }
            return count;
        }

    }

    /**
     * <pre>
     * ┌─────────────────────────────────────────┐
     * │   0   1   2   3   4   5   6   7   8   9 │
     * │  10  11  12  13  14  15  16  17  18  19 │
     * │  20  21  22  23  24  25  26  27  28  29 │
     * │  ...                                    │
     * │ 110 111 112 113 114 115 116 117 118 119 │
     * └─────────────────────────────────────────┘
     * 每10个数字中，最后一位为2的情况大概会出现一次，因为 2 在连续的10个数组中会出现一次，实际上，任意位位2 的概率大概为1/10；
     * 为什么呢？因为存在边界条件，例如在 1～100 十位数为2 的概率正好为 1/10.然而，在 1～37 十位数为2的概率会大于1/10。
     *
     * 下面注意分析 digit < 2,digit = 2, digit > 2 这三种情况
     * <ol>
     *     <li>digit < 2</li>
     *     以 x = 61523 和 d = 3为例：
     *     x[d] = 1 （也即 x 的第 d 位为 1）。
     *     第3位为2的范围是 2000～2999、12000～12999、22000～22999、32000～32999、42000～42999和52000～52999，
     *     还没有到达 62000～62999，因此第3位数总共 6000 个2。
     *     相当于 1到 60000里第3位数位2的数量。
     *
     *     换句话说，可以将原来的树往下降至最近的 10^(d+1),然后噢再处以10就可以算出第d位数位2的数量。
     *     if x[d] < 2: count2sInRangeAtDigit(x, d) =
     *          let y = round down to nearest 10d+1 return y / 10
     *     <li>digit = 2</li>
     *      以 x = 63523 和d=3 为例：
     *      x[d] = 3 > 2，基本上，我们可以运用与之前相同的逻辑方法，确认范围为 0 ～ 70000 是相同的，
     *      因此，之前是往下酱，现在是往上提。
     *     if x[d] > 2: count2sInRangeAtDigit(x, d) =
     *          let y = round up to nearest 10d+1 return y / 10
     *     <li>digit > 2</li>
     *     以 x = 62523 和 d = 3为例:
     *     有之前的逻辑方法可以得到相同的范围（2000～2999、12000～12999、22000～22999、32000～32999、42000～42999和52000～52999）。
     *     在最后余下的 62000 ～ 62523 这个范围里，第 3 位数位2的数量有多少？
     *     比较明显，只有524 个（62000, 62001,...62523）。
     *
     *     if x[d] = 2: count2sInRangeAtDigit(x, d) =
     *              let y = round down to nearest 10d+1
     *              let z = right side of x (i.e., x % 10d)
     *              return y / 10 + z + 1
     *
     * </ol>
     *
     * </pre>
     */
    class Solution{
        public int numberOf2sInRange(int n) {
            int count = 0;
            int len = String.valueOf(n).length();
            for (int digit = 0; digit < len; digit++) {
                count += count2sInRangeAtDigt(n, digit);
            }
            return count;
        }

        private int count2sInRangeAtDigt(int n, int d) {
            int powerOf10 = (int) Math.pow(10, d);
            int nextPowerOf10 = powerOf10 * 10;
            int right = n % powerOf10;

            int roundDown = n - n % nextPowerOf10;
            int roundUp = roundDown + nextPowerOf10;

            int digit = (n / powerOf10) % 10;
            // 判断数位的值
            if (digit < 2) {
                return roundDown / 10;
            }
            else if (digit == 2) {
                return roundDown / 10 + right + 1;
            }
            else {
                return roundUp / 10;
            }
        }
    }

    class Solution2{
        char s[];
        int dp[][];

        public int numberOf2sInRange(int n) {
            s = Integer.toString(n).toCharArray();
            int m = s.length;
            dp = new int[m][m];
            for (int i = 0; i < m; i++) Arrays.fill(dp[i], -1);
            return f(0, 0, true);
        }

        int f(int i, int cnt2, boolean isLimit) {
            if (i == s.length) return cnt2;
            if (!isLimit && dp[i][cnt2] >= 0) return dp[i][cnt2];
            int res = 0;
            for (int d = 0, up = isLimit ? s[i] - '0' :9; d <= up; ++d) // 枚举要填入的数字 d
                res += f(i + 1, cnt2 + (d == 2 ? 1 :0), isLimit && d == up);
            if (!isLimit) dp[i][cnt2] = res;
            return res;
        }

    }

    @Test
    public void test(){
        Solution solution = new Solution();
        int i = solution.numberOf2sInRange(63523);
        System.out.println(i);
    }
}
