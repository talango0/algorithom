package leetcode.arrays.binarySearch;
//珂珂喜欢吃香蕉。这里有 n 堆香蕉，第 i 堆中有 piles[i] 根香蕉。警卫已经离开了，将在 h 小时后回来。
//
// 珂珂可以决定她吃香蕉的速度 k （单位：根/小时）。每个小时，她将会选择一堆香蕉，从中吃掉 k 根。
// 如果这堆香蕉少于 k 根，她将吃掉这堆的所有香蕉，然后这一小时内不会再吃更多的香蕉。
//
// 珂珂喜欢慢慢吃，但仍然想在警卫回来前吃掉所有的香蕉。
//
// 返回她可以在 h 小时内吃掉所有香蕉的最小速度 k（k 为整数）。
//
//
// 示例 1：
//输入：piles = [3,6,7,11], h = 8
//输出：4
//
//
// 示例 2：
//输入：piles = [30,11,23,4,20], h = 5
//输出：30
//
//
// 示例 3：
//输入：piles = [30,11,23,4,20], h = 6
//输出：23
//
// 提示：
// 1 <= piles.length <= 10⁴
// piles.length <= h <= 10⁹
// 1 <= piles[i] <= 10⁹
//
// Related Topics 数组 二分查找 👍 406 👎 0

import leetcode.math._1201_丑数III;

/**
 * @author mayanwei
 * @date 2022-07-01.
 *
 * @see _1011_在D天内送达包裹的能力
 * @see _392_判断子序列
 * @see _33_搜索旋转排序数组
 * @see _410_分割数组的最大值
 * @see _1201_丑数III
 */
public class _875_爱吃香蕉的珂珂{
    class Solution {
        int f (int [] piles, int x) {
            int hours = 0;
            for (int pile : piles) {
                hours += pile/x;
                if (pile % x > 0) {
                    hours ++;
                }
            }
            return hours;
        }
        // h 小时慢慢吃掉 piles的最小速度是多少
        public int minEatingSpeed(int[] piles, int h) {
            int left = 1;
            //注意， right 是开区间，所以再加1
            int right = 1000000000 + 1;
            while (left<right) {
                int mid = left + (right - left) / 2;
                if (f(piles, mid) == h) {
                    //搜索左侧区间，需要修改右侧边界
                    right = mid;
                }
                else if (f(piles, mid) < h) {
                    //需要让 f(x) 的返回值大一些，因此边界向左收缩
                    right = mid;
                }
                else if (f(piles, mid) > h) {
                    //需要让 f(x) 的返回值小一些，因此边界向右收缩
                    left = mid + 1;
                }
            }
            return left;
        }
    }
}
