package leetcode.arrays.binarySearch;
//给定一个排序数组和一个目标值，在数组中找到目标值，并返回其索引。
// 如果目标值不存在于数组中，返回它将会被按顺序插入的位置。
//
// 请必须使用时间复杂度为 O(log n) 的算法。
//
// 示例 1:
//输入: nums = [1,3,5,6], target = 5
//输出: 2
//
//
// 示例 2:
//输入: nums = [1,3,5,6], target = 2
//输出: 1
//
//
// 示例 3:
//输入: nums = [1,3,5,6], target = 7
//输出: 4
//
//
// 提示:
//
//
// 1 <= nums.length <= 10⁴
// -10⁴ <= nums[i] <= 10⁴
// nums 为 无重复元素 的 升序 排列数组
// -10⁴ <= target <= 10⁴
//
//
// Related Topics 数组 二分查找 👍 1764 👎 0

/**
 * @author mayanwei
 * @date 2022-11-03.
 */
public class _35_搜索插入位置{
    class Solution{
        /**
         * 左侧边界的二分查找
         * <p>
         * 1. 返回的这个值是 nums 中等于 target 的最小索引（左边界）。
         * <p>
         * 2. 返回的这个值是 target 应该插入在 nums 中的索引位置。
         * <p>
         * 3. 返回这个值是 nums 中小于 target 的元素个数。
         */
        public int searchInsert(int[] nums, int target) {
            return left_bound(nums, target);
        }

        int left_bound(int[] nums, int target) {
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
