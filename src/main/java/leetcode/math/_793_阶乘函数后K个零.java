package leetcode.math;
//f(x) 是 x! 末尾是 0 的数量。回想一下 x! = 1 * 2 * 3 * ... * x，且 0! = 1 。
//
//
// 例如， f(3) = 0 ，因为 3! = 6 的末尾没有 0 ；而 f(11) = 2 ，因为 11!= 39916800 末端有 2 个 0 。
//
//
// 给定 k，找出返回能满足 f(x) = k 的非负整数 x 的数量。
//
//
//
// 示例 1：
//
//
//输入：k = 0
//输出：5
//解释：0!, 1!, 2!, 3!, 和 4! 均符合 k = 0 的条件。
//
//
// 示例 2：
//
//
//输入：k = 5
//输出：0
//解释：没有匹配到这样的 x!，符合 k = 5 的条件。
//
// 示例 3:
//
//
//输入: k = 3
//输出: 5
//
//
//
//
// 提示:
//
//
// 0 <= k <= 10⁹
//
//
// Related Topics 数学 二分查找 👍 90 👎 0

/**
 * @see _172_阶乘后的零
 * @author mayanwei
 * @date 2022-08-05.
 */
public class _793_阶乘函数后K个零{
    class Solution{
        // 比较直观的就是穷举
        // 对于这种具有单调性的函数，用 for 循环遍历，可以用二分查找进行降维打击
        // 搜索有多少个 n 满足 trailingZeroes(n) == K，其实就是在问，满足条件的 n 最小是多少，最大是多少，最大值和最小值一减，
        // 就可以算出来有多少个 n 满足条件了，对吧？那不就是 二分查找 中「搜索左侧边界」和「搜索右侧边界」这两个事儿嘛？
        // 这道题目实际上给了限制，K 是在 [0, 10^9] 区间内的整数，也就是说，trailingZeroes(n) 的结果最多可以达到 10^9。
        //
        // 确定上下界 [0,LONG_MAX]
        // 然后我们可以反推，当 trailingZeroes(n) 结果为 10^9 时，n 为多少？这个不需要你精确计算出来，你只要找到一个数 hi，
        // 使得 trailingZeroes(hi) 比 10^9 大，就可以把 hi 当做正无穷，作为搜索区间的上界。

        // 刚才说了，trailingZeroes 函数是单调函数，那我们就可以猜，先算一下 trailingZeroes(INT_MAX) 的结果，比 10^9 小一些，
        // 那再用 LONG_MAX 算一下，远超 10^9 了，所以 LONG_MAX 可以作为搜索的上界。

        // 注意为了避免整型溢出的问题，trailingZeroes 函数需要把所有数据类型改成 long
        // 时间复杂度主要是二分搜索，从数值上来说 LONG_MAX 是 2^63 - 1，大得离谱，但是二分搜索是对数级的复杂度，
        // log(LONG_MAX) 是一个常数；每次二分的时候都会调用一次 trailingZeroes 函数，复杂度 O(logK)；所以总体的时间复杂度就是 O(logK)。
        public int preimageSizeFZF(int k) {
            // 左边界和右边界之差 + 1就是答案
            return (int) (right_bound(k) - left_bound(k) + 1);
        }

        // 搜索 trailingZeroes(n) == K 的左侧边界
        long right_bound(int target) {
            long lo = 0, hi = Long.MAX_VALUE;
            while (lo < hi) {
                long mid = lo + ((hi - lo) >> 1);
                long v = trailingZeroes(mid);
                if (v < target) {
                    lo = mid + 1;
                }
                else if (v > target) {
                    hi = mid;
                }
                else {
                    lo = mid + 1;
                }
            }
            return lo - 1;
        }

        long left_bound(int target) {
            long lo = 0, hi = Long.MAX_VALUE;
            while (lo < hi) {
                long mid = lo + ((hi - lo) >> 1);
                long v = trailingZeroes(mid);
                if (v < target) {
                    lo = mid + 1;
                }
                else if (v > target) {
                    hi = mid;
                }
                else {
                    hi = mid;
                }
            }
            return lo;
        }

        // 为避免一处的问题，该函数把所有数据类型改成long
        private long trailingZeroes(long n) {
            long res = 0;
            for (long d = n; d / 5 > 0; d = d / 5) {
                res += d / 5;
            }
            return res;
        }
    }
}
