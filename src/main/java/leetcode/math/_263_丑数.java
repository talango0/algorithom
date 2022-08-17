package leetcode.math;

//丑数 就是只包含质因数 2、3 和 5 的正整数。
//
//给你一个整数 n ，请你判断 n 是否为 丑数 。如果是，返回 true ；否则，返回 false 。
//
//示例 1：
//
//输入：n = 6
//输出：true
//解释：6 = 2 × 3
//示例 2：
//
//输入：n = 1
//输出：true
//解释：1 没有质因数，因此它的全部质因数是 {2, 3, 5} 的空集。习惯上将其视作第一个丑数。
//示例 3：
//
//输入：n = 14
//输出：false
//解释：14 不是丑数，因为它包含了另外一个质因数
//7
//。
//提示：
//
//-231 <= n <= 231 - 1
//Related Topics
//
//👍 338, 👎 0

/**
 * @author mayanwei
 * @date 2022-08-15.
 * @see _264_丑数II
 * @see _313_超级丑数
 * @see _1201_丑数III
 */
public class _263_丑数{
    class Solution {
        // 基本思路：
        // 算术基本定理（正整数唯一分解定理），即：任意一个大于 1 的自然数，要么它本身就是质数，要么它可以分解为若干质数的乘积。
        // 那么题目所说的丑数当然也可以被分解成若干质数的乘积，且这些质数只包括 2, 3, 5。那么解题思路就很显然了，只要判断 n 是不是只有这三种质因子即可。
        public boolean isUgly(int n) {
            if (n <= 0) {
                return false;
            }
            // 如果是丑数，分解因子因该只有2，3，5
            while(n % 2 == 0) {
                n /= 2;
            }
            while (n % 3 == 0) {
                n /= 3;
            }
            while (n % 5 == 0) {
                n /= 5;
            }
            return n == 1;

        }
    }
}
