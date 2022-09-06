package leetcode.math;

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
