package leetcode.math;
//一个正整数如果能被 a 或 b 整除，那么它是神奇的。
//
// 给定三个整数 n , a , b ，返回第 n 个神奇的数字。因为答案可能很大，所以返回答案 对 10⁹ + 7 取模 后的值。
//
//
//
//
//
//
// 示例 1：
//
//
//输入：n = 1, a = 2, b = 3
//输出：2
//
//
// 示例 2：
//
//
//输入：n = 4, a = 2, b = 3
//输出：6
//
//
//
//
// 提示：
//
//
// 1 <= n <= 10⁹
// 2 <= a, b <= 4 * 10⁴
//
//
//
//
// Related Topics 数学 二分查找 👍 104 👎 0

import leetcode.arrays.binarySearch._704_二分查找;
import leetcode.math.gcd_lcm.LeastCommonMultiple;

/**
 * @author mayanwei
 * @date 2022-09-22.
 * @see LeastCommonMultiple
 * @see _704_二分查找
 */
public class _878_第N个神奇数字{
    class Solution{
        // f(x) 为小于等于x的神奇数字个数，f(x) = x/a + x/b  - x/l，其中l为 l = lcm(a,b) = a*b/gcd(a,b)
        // 小于等于x的神奇数字的格式是一个单调递增函数，可以采用二分搜索处理
        public int nthMagicalNumber(int N, int A, int B) {
            int MOD = 1_000_000_007;
            int L = A / gcd(A, B) * B;

            long lo = 0;
            long hi = (long) 1e15;
            while (lo < hi) {
                long mi = lo + (hi - lo) / 2;
                // If there are not enough magic numbers below mi...
                if (mi / A + mi / B - mi / L < N) {
                    lo = mi + 1;
                }
                else {
                    hi = mi;
                }
            }

            return (int) (lo % MOD);
        }

        public int gcd(int x, int y) {
            return x == 0 ? y :gcd(y % x, x);
        }
    }

    class Solution2{
        // 设 L为 A，B的最小公倍数，如果 X<=L,那么 X + L 也是神奇数字
        public int nthMagicalNumber(int n, int a, int b) {
            int MOD = 1_000_000_007;
            // l 为a,b 的最小公倍数
            int l = a / gcd(a, b) * b;
            int m = l / a + l / b - 1;
            int q = n / m, r = n % m;
            long ans = (long) q * l % MOD;
            if (r == 0) {
                return (int) ans;
            }
            int[] heads = new int[]{a, b};
            for (int i = 0; i < r - 1; i++) {
                if (heads[0] < heads[1]) {
                    heads[0] += a;
                }
                else {
                    heads[1] += b;
                }
            }
            ans += Math.min(heads[0], heads[1]);
            return (int) (ans % MOD);
        }

        public int gcd(int a, int b) {
            if (a == 0) {
                return b;
            }
            return gcd(b % a, a);
        }

    }
}
