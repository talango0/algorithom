package leetcode.jzhoffer;
//给定一个含有 n 个正整数的数组和一个正整数 target 。
//
// 找出该数组中满足其和 ≥ target 的长度最小的 连续子数组 [numsl, numsl+1, ..., numsr-1, numsr] ，并返回其长
//度。如果不存在符合条件的子数组，返回 0 。
//
//
//
// 示例 1：
//
//
//输入：target = 7, nums = [2,3,1,2,4,3]
//输出：2
//解释：子数组 [4,3] 是该条件下的长度最小的子数组。
//
//
// 示例 2：
//
//
//输入：target = 4, nums = [1,4,4]
//输出：1
//
//
// 示例 3：
//
//
//输入：target = 11, nums = [1,1,1,1,1,1,1,1]
//输出：0
//
//
//
//
// 提示：
//
//
// 1 <= target <= 10⁹
// 1 <= nums.length <= 10⁵
// 1 <= nums[i] <= 10⁵
//
//
//
//
// 进阶：
//
//
// 如果你已经实现 O(n) 时间复杂度的解法, 请尝试设计一个 O(n log(n)) 时间复杂度的解法。
//
//
//
//
//
// 注意：本题与主站 209 题相同：https://leetcode-cn.com/problems/minimum-size-subarray-sum/
//
//
// Related Topics 数组 二分查找 前缀和 滑动窗口 👍 94 👎 0

import leetcode.slidewindow._209_长度最小的子数组;

import java.util.Arrays;

/**
 * @author mayanwei
 * @date 2022-10-20.
 * @see _209_长度最小的子数组
 */
public class 剑指_Offer_II_008_和大于等于target的最短子数组{
    class Solution{
        /**
         * 因为所有的数字都是正数，所以前缀和一定是递增的，这一点保证了二分的正确性。
         */
        public int minSubArrayLen(int target, int[] nums) {
            int n = nums.length;
            if (n == 0) {
                return 0;
            }

            // sum 前缀和数组，
            // sum[i] 表示数组 nums[0,..,i-1] 元素之和。
            int[] sum = new int[n + 1];
            for (int i = 1; i <= n; i++) {
                sum[i] = nums[i - 1] + sum[i - 1];
            }

            int ans = Integer.MAX_VALUE;
            for (int i = 1; i <= n; i++) {
                int t = target + sum[i - 1];
                int bound = Arrays.binarySearch(sum, t);
                if (bound < 0) {
                    bound = -bound - 1;
                }
                if (bound <= n) {
                    ans = Math.min(ans, bound - (i - 1));
                }
            }
            return ans == Integer.MAX_VALUE ? 0 :ans;
        }
    }
}
