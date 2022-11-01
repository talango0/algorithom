package leetcode.jzhoffer;
//给定两个整数 a 和 b ，求它们的除法的商 a/b ，要求不得使用乘号 '*'、除号 '/' 以及求余符号 '%'。
//
//
//
//注意：
//
//整数除法的结果应当截去（truncate）其小数部分，例如：truncate(8.345) = 8以及truncate(-2.7335) = -2
//假设我们的环境只能存储 32 位有符号整数，其数值范围是 [−231,231−1]。本题中，如果除法结果溢出，则返回 231− 1
//
//
//示例 1：
//
//输入：a = 15, b = 2
//输出：7
//解释：15/2 = truncate(7.5) = 7
//示例 2：
//
//输入：a = 7, b = -3
//输出：-2
//解释：7/-3 = truncate(-2.33333..) = -2
//示例 3：
//
//输入：a = 0, b = 1
//输出：0
//示例 4：
//
//输入：a = 1, b = 1
//输出：1
//
//
//提示:
//
//-231<= a, b <= 231- 1
//b != 0
//
//来源：力扣（LeetCode）
//链接：https://leetcode.cn/problems/xoh6Oh
//著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。

import leetcode.math._29_两数相除;

/**
 * @author mayanwei
 * @date 2022-10-29.
 * @see _29_两数相除
 */
public class 剑指_Offer_II_001_整数除法{
    class Solution{
        // 时间复杂度：O(1)
        public int divide(int a, int b) {
            if (a == Integer.MIN_VALUE && b == -1)
                return Integer.MAX_VALUE;
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
}
