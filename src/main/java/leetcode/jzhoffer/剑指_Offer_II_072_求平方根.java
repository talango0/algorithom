package leetcode.jzhoffer;
//给定一个非负整数 x ，计算并返回 x 的平方根，即实现 int sqrt(int x) 函数。
// 正数的平方根有两个，只输出其中的正数平方根。
// 如果平方根不是整数，输出只保留整数的部分，小数部分将被舍去。
//
// 示例 1:
//输入: x = 4
//输出: 2
//
// 示例 2:
//输入: x = 8
//输出: 2
//解释: 8 的平方根是 2.82842...，由于小数部分将被舍去，所以返回 2
//
// 提示:
//
// 0 <= x <= 2³¹ - 1
//
//
// 注意：本题与主站 69 题相同： https://leetcode-cn.com/problems/sqrtx/
//
// Related Topics 数学 二分查找 👍 37 👎 0

import leetcode.math._69_x的平方根;

/**
 * @author mayanwei
 * @date 2022-11-03.
 * @see _69_x的平方根
 */
public class 剑指_Offer_II_072_求平方根{
    /**
     * 二分查找
     * 时间复杂度 O(logx)
     */
    class Solution{
        public int mySqrt(int x) {
            int l = 0, r = x, ans = -1;
            while (l <= r) {
                int mid = l + (r - l) / 2;
                if ((long) mid * mid <= x) {
                    ans = mid;
                    l = mid + 1;
                }
                else {
                    r = mid - 1;
                }
            }
            return ans;
        }
    }
}
