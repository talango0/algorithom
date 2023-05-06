package leetcode.arrays.binarySearch;
//给你一个按照非递减顺序排列的整数数组 nums，和一个目标值 target。
// 请你找出给定目标值在数组中的开始位置和结束位置。
//
//如果数组中不存在目标值 target，返回 [-1, -1]。
//
//你必须设计并实现时间复杂度为 O(log n) 的算法解决此问题。
//
//示例 1：
//输入：nums = [5,7,7,8,8,10], target = 8
//输出：[3,4]

//示例 2：
//输入：nums = [5,7,7,8,8,10], target = 6
//输出：[-1,-1]
//示例 3：
//
//输入：nums = [], target = 0
//输出：[-1,-1]
//提示：
//
//0 <= nums.length <= 105
//-109 <= nums[i] <= 109
//nums 是一个非递减数组
//-109 <= target <= 109
//Related Topics
//
//👍 1851, 👎 0

/**
 * @author mayanwei
 * @date 2022-08-13.
 */
public class _34_在排序数组中查找元素的第一个和最后一个位置{

    class Solution {
        // 寻找 target 的左边界和右边界
        public int[] searchRange(int[] nums, int target) {
            int leftIndex = -1, rightIndex = -1;
            return new int[]{left_bound(nums, target), right_bound(nums, target)};
        }

        int left_bound(int [] nums, int target) {
            int left = 0, right = nums.length-1;
            // 搜索区间为 [left, right]
            // while 结束的条件时 [right+1, right]
            while (left <= right) {
                int mid = left + ((right - left) >> 1);
                if (nums[mid] == target) {
                    // 收缩右侧边界
                    right = mid - 1;
                }
                else if (nums[mid] > target) {
                    // 搜索区间变成 [left, mid-1]
                    right = mid - 1;
                }
                else if (nums[mid] < target) {
                    // 搜索区间变成 [mid+1, right]
                    left = mid + 1;
                }
            }
            // 检查出界情况
            if (left >= nums.length || nums[left] != target) {
                return -1;
            }
            return left;
        }

        int right_bound(int [] nums, int target) {
            int left = 0, right = nums.length - 1;
            // while 结束的条件 left = right + 1, 此时区间为 [right + 1, right] 空
            while (left <= right) {
                int mid = left + ((right - left)>>1);
                if (nums[mid] == target) {
                    // 这里改成收缩左侧区间
                    left = mid + 1;
                }
                else if (nums[mid] < target) {
                    // 搜索区间变成 [mid + 1, right]
                    left = mid + 1;
                }
                else if (nums[mid] > target) {
                    // 搜索区间变成 [left, mid - 1]
                    right = mid - 1;
                }
            }
            // 这里改为检查 right 越界情况
            if (right < 0 || nums[right] != target) {
                return -1;
            }
            return right;
        }
    }

    class Solution2{
        // 寻找 target 的左边界和右边界
        public int[] searchRange(int[] nums, int target) {
            // 左右边界
            int leftBound = -1, rightBound = -1;
            int left = 0, right = nums.length;
            int mid;
            while (left < right) {
                mid = left + ((right - left) >> 1);
                if (nums[mid] >= target) {
                    right = mid;
                }
                else {
                    left = mid + 1;
                }
            }
            leftBound = (left == nums.length || nums[left] != target) ? -1 :left;

            left = 0;
            right = nums.length;
            while (left < right) {
                mid = left + ((right - left) >> 1);
                if (nums[mid] <= target) {
                    left = mid + 1;
                }
                else if (nums[mid] > target) {
                    right = mid;
                }
            }
            if (left != 0 && nums[left - 1] == target) {
                rightBound = left - 1;
            }
            return new int[]{leftBound, rightBound};
        }
    }
}
