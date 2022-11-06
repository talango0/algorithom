package leetcode.jzhoffer;

import leetcode.arrays.binarySearch._875_爱吃香蕉的珂珂;
//珂珂喜欢吃香蕉。这里有 n 堆香蕉，第 i 堆中有 piles[i] 根香蕉。警卫已经离开了，将在 h 小时后回来。
//
// 珂珂可以决定她吃香蕉的速度 k （单位：根/小时）。每个小时，她将会选择一堆香蕉，从中吃掉 k 根。如果这堆香蕉少于 k 根，她将吃掉这堆的所有香蕉，然后
//这一小时内不会再吃更多的香蕉。
//
// 珂珂喜欢慢慢吃，但仍然想在警卫回来前吃掉所有的香蕉。
//
// 返回她可以在 h 小时内吃掉所有香蕉的最小速度 k（k 为整数）。
//
//
//
//
//
//
// 示例 1：
//
//
//输入：piles = [3,6,7,11], h = 8
//输出：4
//
//
// 示例 2：
//
//
//输入：piles = [30,11,23,4,20], h = 5
//输出：30
//
//
// 示例 3：
//
//
//输入：piles = [30,11,23,4,20], h = 6
//输出：23
//
//
//
//
// 提示：
//
//
// 1 <= piles.length <= 10⁴
// piles.length <= h <= 10⁹
// 1 <= piles[i] <= 10⁹
//
//
// Related Topics 数组 二分查找 👍 449 👎 0

/**
 * @author mayanwei
 * @date 2022-11-05.
 * @see _875_爱吃香蕉的珂珂
 */
public class 剑指_Offer_II_073_狒狒吃香蕉{
    class Solution{
        /**
         * 二分搜索的套路：
         * 思考 x、 f(x)、 target 分别代表什么，并且出单调函数 f 的代码
         * 这里珂珂吃香蕉的速度就是自变量 x，
         * 吃完所有香蕉所用的时间就是单调函数 f(x),
         * target 就是吃香蕉的时间限制 h
         */
        public int minEatingSpeed(int[] piles, int h) {
            //注意， right 是开区间，所以再加1
            int left = 1, right = (int) 10e8 + 1;
            while (left < right) {
                int mid = left + (right - left) / 2;
                if (f(piles, mid) <= h) {
                    right = mid;
                }
                else {
                    left = mid + 1;
                }
            }
            return left;
        }

        // 定义：速度为x时需要f(x) 小时吃完所有香蕉
        // f(x) 随着x增大而单调递减
        int f(int[] piles, int x) {
            int hours = 0;
            for (int i = 0; i < piles.length; i++) {
                hours += piles[i] / x;
                if (piles[i] % x > 0) {
                    hours++;
                }
            }
            return hours;
        }
    }
}
