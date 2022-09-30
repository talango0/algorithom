package leetcode.math;

import leetcode.math.bitop._191_位1的个数;
//两个整数之间的 汉明距离 指的是这两个数字对应二进制位不同的位置的数目。
//
// 给你两个整数 x 和 y，计算并返回它们之间的汉明距离。
//
//
//
// 示例 1：
//
//
//输入：x = 1, y = 4
//输出：2
//解释：
//1   (0 0 0 1)
//4   (0 1 0 0)
//       ↑   ↑
//上面的箭头指出了对应二进制位不同的位置。
//
//
// 示例 2：
//
//
//输入：x = 3, y = 1
//输出：1
//
//
//
//
// 提示：
//
//
// 0 <= x, y <= 2³¹ - 1
//
//
// Related Topics 位运算 👍 638 👎 0
/**
 * @author mayanwei
 * @date 2022-09-28.
 * @see _191_位1的个数
 */
public class _461_汉明距离{
    class Solution {
        public int hammingDistance(int x, int y) {
            return hamingWeight(x^y);
        }

        public int hamingWeight(int n) {
            int res = 0;
            while (n!=0) {
                n = n&(n-1);
                res ++;
            }
            return res;
        }
    }
}
