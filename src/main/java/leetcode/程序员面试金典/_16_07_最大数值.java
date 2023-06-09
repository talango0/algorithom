package leetcode.程序员面试金典;

//编写一个方法，找出两个数字a和b中最大的那一个。不得使用if-else或其他比较运算符。
//
//示例：
//
//输入： a = 1, b = 2
//输出： 2

/**
 * @author mayanwei
 * @date 2023-06-09.
 */
public class _16_07_最大数值{
    /**
     * 假设 k 代表 a-b 的正负号，如果 a - b >= 0, 则 k 为 1， 否则 k 为 0。另 q 为 k 的反数。
     */
    class Solution{
        // 将 1 翻转为 0，0翻转为 1
        int flip(int bit) {
            return 1 ^ bit;
        }

        // 如果是正数返回 1， 如果是负数，返回0
        int sign(int a) {
            return flip((a >> 31) & 0x1);
        }

        public int maximum(int a, int b) {
            int k = sign(a - b);
            int q = flip(k);
            return a * k + b * q;
        }
    }

    /**
     * <pre>
     * 上述代码在 a-b 处可能会右数据溢出。因此行不通。
     * a - b 什么时候溢出? 只会在 a 为正且 b 为负时溢出，或者反过来也有可能。专门检测溢出条件可能比较
     * 困难，不过，可以检测 a 和 b 何时会有不同的正负号。注意，如果 a 和 b 正负号不同，就让 k 等于 sign(a)
     * </pre>
     */
    class Solution1{
        int flip(int bit) {
            return 1 ^ bit;
        }

        // 如果是正数返回 1， 如果是负数，返回0
        int sign(int a) {
            return flip((a >> 31) & 0x1);
        }

        public int maximum(int a, int b) {
            int c = a - b;
            int sa = sign(a); // 如果 a >= 0, 则返回 1，否则返回0
            int sb = sign(b); // 如果 b >= 0, 则返回 1，否则返回0
            int sc = sign(c); // 取决于 a-b 是否溢出
            // 目标：定义k, 如果 a > b 则为1，如果 a < b则为0。如果 a = b，k 为何值不重要。

            // 如果 a 和 b 有不同的符号，则 k = sing(a)
            int use_sign_of_a = sa ^ sb;

            // 如果 a 和 b 有x相同的符号，则 k = sign(a-b)
            int use_sign_of_c = flip(sa ^ sb);

            int k = use_sign_of_a * sa + use_sign_of_c * sc;
            int q = flip(k); // k的相反值
            return a * k + b * q;
        }
    }
}
