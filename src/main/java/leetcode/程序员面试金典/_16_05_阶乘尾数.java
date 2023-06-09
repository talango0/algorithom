package leetcode.程序员面试金典;
//设计一个算法，算出 n 阶乘有多少个尾随零。
//
//示例 1:
//
//输入: 3
//输出: 0
//解释:3! = 6, 尾数中没有零。
//示例2:
//
//输入: 5
//输出: 1
//解释:5! = 120, 尾数中有 1 个零.
//说明: 你算法的时间复杂度应为O(logn)。
//
//来源：力扣（LeetCode）
//链接：https://leetcode.cn/problems/factorial-zeros-lcci
//著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。

import leetcode.math._172_阶乘后的零;

/**
 * @author mayanwei
 * @date 2023-06-09.
 * @see _172_阶乘后的零
 */
public class _16_05_阶乘尾数{
    class Solution {
        public int trailingZeroes(int n) {
            int res = 0;
            for (int d = n; d / 5 > 0; d = d / 5) {
                res += d / 5;
            }
            return res;
        }
    }
}
