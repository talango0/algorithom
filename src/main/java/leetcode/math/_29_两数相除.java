package leetcode.math;
//给定两个整数，被除数 dividend 和除数 divisor。将两数相除，要求不使用乘法、除法和 mod 运算符。
//
// 返回被除数 dividend 除以除数 divisor 得到的商。
//
// 整数除法的结果应当截去（truncate）其小数部分，例如：truncate(8.345) = 8 以及 truncate(-2.7335) = -2
//
//
//
// 示例 1:
//
// 输入: dividend = 10, divisor = 3
//输出: 3
//解释: 10/3 = truncate(3.33333..) = truncate(3) = 3
//
// 示例 2:
//
// 输入: dividend = 7, divisor = -3
//输出: -2
//解释: 7/-3 = truncate(-2.33333..) = -2
//
//
//
// 提示：
//
//
// 被除数和除数均为 32 位有符号整数。
// 除数不为 0。
// 假设我们的环境只能存储 32 位有符号整数，其数值范围是 [−2³¹, 231 − 1]。本题中，如果除法结果溢出，则返回 231 − 1。
//
//
// Related Topics 位运算 数学 👍 1004 👎 0

public class _29_两数相除 {

    /**
     * 当被除数为 -2^31 时，
     * <p>
     * 除数为1，返回答案 -2^31
     * 除数为-1,那么答案为 2^31 溢出，返回答案 2^31 -1
     * <p>
     * 当除数为 -2^31 时，
     * <p>
     * 被除数为 -2^31,返回答案 1
     * 其余情况，返回答案 0
     * <p>
     * 为了方便编码，可以使除数和被除数的符号一致，
     * <p>
     * 如果我们将被除数都变为正数，则可能会导致溢出，例如当被除数为 -2^31 时，他的相反数 2^31 溢出了。
     * 因此，可以考虑被除数和除数都变为负数，这样就不会有溢出的问题了，在编码时只需要考虑一种情况了。
     * <p>
     * 如果将被除数和除数中的其中（恰好）一个变为了正数，那么在返回答案之前，需要对答案也取反数。
     */
    class Solution {
        public int divide(int dividend, int divisor) {
            if (divisor == 1 || dividend == 0) {
                return dividend;
            }
            if (divisor == -1) {
                return dividend == Integer.MIN_VALUE ?
                        Integer.MAX_VALUE :
                        -dividend;
            }

            boolean isNeg = (dividend > 0 && divisor < 0) || (dividend < 0 && divisor > 0);

            dividend = -Math.abs(dividend);
            divisor = -Math.abs(divisor);

            int ans = 0;
            while (dividend <= divisor) {
                int a = divisor;
                int temp = 1;
                while (dividend - a <= a) {
                    a += a;
                    temp += temp;
                }
                ans += temp;
                dividend = dividend - a;
            }
            return isNeg ? -ans : ans;

        }
    }

    class Solution3 {
        // 时间复杂度：O(logn * logn)，n 是最大值 2147483647 --> 10^10
        public int divide(int a, int b) {
            if (a == Integer.MIN_VALUE && b == -1) {
                return Integer.MAX_VALUE;
            }
            int sign = (a > 0) ^ (b > 0) ? -1 : 1;
            if (a > 0) {
                a = -a;
            }
            if (b > 0) {
                b = -b;
            }
            int res = 0;
            while (a <= b) {
                int value = b;
                int k = 1;
                // 0xc0000000 是十进制 -2^30 的十六进制的表示
                // 判断 value >= 0xc0000000 的原因：保证 value + value 不会溢出
                // 可以这样判断的原因是：0xc0000000 是最小值 -2^31 的一半，
                // 而 a 的值不可能比 -2^31 还要小，所以 value 不可能比 0xc0000000 小
                // -2^31 / 2 = -2^30
                while (value >= 0xc0000000 && a <= value + value) {
                    value += value;
                    // 代码优化：如果 k 已经大于最大值的一半的话，那么直接返回最小值
                    // 因为这个时候 k += k 的话肯定会大于等于 2147483648 ，这个超过了题目给的范围
                    if (k > Integer.MAX_VALUE / 2) {
                        return Integer.MIN_VALUE;
                    }
                    k += k;
                }
                a -= value;
                res += k;
            }
            // bug 修复：因为不能使用乘号，所以将乘号换成三目运算符
            return sign == 1 ? res : -res;
        }
    }

    class Solution4 {
        // 时间复杂度：O(1)
        public int divide(int a, int b) {
            if (a == Integer.MIN_VALUE && b == -1) {
                return Integer.MAX_VALUE;
            }
            int sign = (a > 0) ^ (b > 0) ? -1 : 1;
            a = Math.abs(a);
            b = Math.abs(b);
            int res = 0;
            for (int i = 31; i >= 0; i--) {
                // 首先，右移的话，再怎么着也不会越界
                // 其次，无符号右移的目的是：将 -2147483648 看成 2147483648

                // 注意，这里不能是 (a >>> i) >= b 而应该是 (a >>> i) - b >= 0
                // 这个也是为了避免 b = -2147483648，如果 b = -2147483648
                // 那么 (a >>> i) >= b 永远为 true，但是 (a >>> i) - b >= 0 为 false
                if ((a >>> i) - b >= 0) { // a >= (b << i)
                    a -= (b << i);
                    // 代码优化：这里控制 res 大于等于 INT_MAX
                    if (res > Integer.MAX_VALUE - (1 << i)) {
                        return Integer.MIN_VALUE;
                    }
                    res += (1 << i);
                }
            }
            // bug 修复：因为不能使用乘号，所以将乘号换成三目运算符
            return sign == 1 ? res : -res;
        }
    }


    /**
     * 二分法 快速乘
     */
    class Solution2 {
        public int divide(int dividend, int divisor) {
            // 考虑被除数为最小值的情况
            if (dividend == Integer.MIN_VALUE) {
                if (divisor == 1) {
                    return Integer.MIN_VALUE;
                }
                if (divisor == -1) {
                    return Integer.MAX_VALUE;
                }
            }
            // 考虑除数为最小值的情况
            if (divisor == Integer.MIN_VALUE) {
                return dividend == Integer.MIN_VALUE ? 1 : 0;
            }
            // 考虑被除数为 0 的情况
            if (dividend == 0) {
                return 0;
            }

            // 一般情况，使用二分查找
            // 将所有的正数取相反数，这样就只需要考虑一种情况
            boolean rev = false;
            if (dividend > 0) {
                dividend = -dividend;
                rev = !rev;
            }
            if (divisor > 0) {
                divisor = -divisor;
                rev = !rev;
            }

            int left = 1, right = Integer.MAX_VALUE, ans = 0;
            while (left <= right) {
                // 注意溢出，并且不能使用除法
                int mid = left + ((right - left) >> 1);
                boolean check = quickAdd(divisor, mid, dividend);
                if (check) {
                    ans = mid;
                    // 注意溢出
                    if (mid == Integer.MAX_VALUE) {
                        break;
                    }
                    left = mid + 1;
                } else {
                    right = mid - 1;
                }
            }

            return rev ? -ans : ans;
        }

        // 快速乘
        public boolean quickAdd(int y, int z, int x) {
            // x 和 y 是负数，z 是正数
            // 需要判断 z * y >= x 是否成立
            int result = 0, add = y;
            while (z != 0) {
                if ((z & 1) != 0) {
                    // 需要保证 result + add >= x
                    if (result < x - add) {
                        return false;
                    }
                    result += add;
                }
                if (z != 1) {
                    // 需要保证 add + add >= x
                    if (add < x - add) {
                        return false;
                    }
                    add += add;
                }
                // 不能使用除法
                z >>= 1;
            }
            return true;
        }
    }
}
