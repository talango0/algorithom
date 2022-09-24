package leetcode.math;
//给定一个整数，写一个函数来判断它是否是 3 的幂次方。如果是，返回 true ；否则，返回 false 。
//
// 整数 n 是 3 的幂次方需满足：存在整数 x 使得 n == 3ˣ
//
//
//
// 示例 1：
//
//
//输入：n = 27
//输出：true
//
//
// 示例 2：
//
//
//输入：n = 0
//输出：false
//
//
// 示例 3：
//
//
//输入：n = 9
//输出：true
//
//
// 示例 4：
//
//
//输入：n = 45
//输出：false
//
//
//
//
// 提示：
//
//
// -2³¹ <= n <= 2³¹ - 1
//
//
//
//
// 进阶：你能不使用循环或者递归来完成本题吗？
//
// Related Topics 递归 数学 👍 270 👎 0

/**
 * @author mayanwei
 * @date 2022-09-24.
 */
public class _326_3的幂{
    /**
     * 时间复杂度 O(log n)
     * 空间复杂度 O(1)
     */
    class Solution{
        /**
         * 试除法
         */
        public boolean isPowerOfThree(int n) {
            while (n != 0 && n % 3 == 0) {
                n /= 3;
            }
            return n == 1;
        }
    }
}
