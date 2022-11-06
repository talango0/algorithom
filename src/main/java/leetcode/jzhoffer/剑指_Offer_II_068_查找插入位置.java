package leetcode.jzhoffer;
//给定一个排序的整数数组 nums 和一个整数目标值 target ，请在数组中找到 target ，并返回其下标。如果目标值不存在于数组中，返回它将会被按顺
//序插入的位置。
//
// 请必须使用时间复杂度为 O(log n) 的算法。
//
//
//
// 示例 1:
//
//
//输入: nums = [1,3,5,6], target = 5
//输出: 2
//
//
// 示例 2:
//
//
//输入: nums = [1,3,5,6], target = 2
//输出: 1
//
//
// 示例 3:
//
//
//输入: nums = [1,3,5,6], target = 7
//输出: 4
//
//
// 示例 4:
//
//
//输入: nums = [1,3,5,6], target = 0
//输出: 0
//
//
// 示例 5:
//
//
//输入: nums = [1], target = 0
//输出: 0
//
//
//
//
// 提示:
//
//
// 1 <= nums.length <= 10⁴
// -10⁴ <= nums[i] <= 10⁴
// nums 为无重复元素的升序排列数组
// -10⁴ <= target <= 10⁴
//
//
//
//
//
// 注意：本题与主站 35 题相同： https://leetcode-cn.com/problems/search-insert-position/
//
// Related Topics 数组 二分查找 👍 34 👎 0

import leetcode.arrays.binarySearch._35_搜索插入位置;

/**
 * @author mayanwei
 * @date 2022-11-03.
 * @see _35_搜索插入位置
 */
public class 剑指_Offer_II_068_查找插入位置{
    class Solution{
        /**
         * 左侧边界的二分查找
         * 1. 返回的这个值是 nums 中大小等于 target 的最小元素索引。
         * 2. 返回的这个值是 target 应该插入在 nums 中的索引位置。
         * 3. 返回这个值是 nums 中小于 target 的元素个数。
         */
        public int searchInsert(int[] nums, int target) {
            return leftBound(nums, target);
        }

        int leftBound(int[] nums, int target) {
            if (nums.length == 0) {
                return -1;
            }
            int left = 0;
            int right = nums.length;
            while (left < right) {
                int mid = left + (right - left) / 2;
                if (nums[mid] == target) {
                    right = mid;
                }
                else if (nums[mid] < target) {
                    left = mid + 1;
                }
                else if (nums[mid] > target) {
                    right = mid;
                }
            }
            return left;
        }
    }
}
