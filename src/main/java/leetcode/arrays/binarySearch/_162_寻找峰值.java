package leetcode.arrays.binarySearch;
//峰值元素是指其值严格大于左右相邻值的元素。
//
//给你一个整数数组 nums，找到峰值元素并返回其索引。数组可能包含多个峰值，在这种情况下，返回 任何一个峰值 所在位置即可。
//
//你可以假设 nums[-1] = nums[n] = -∞ 。
//
//你必须实现时间复杂度为 O(log n) 的算法来解决此问题。
//
//示例 1：
//
//输入：nums =
//[1,2,3,1]
//
//输出：2
//解释：3 是峰值元素，你的函数应该返回其索引 2。
//示例 2：
//
//输入：nums =
//[
//1,2,1,3,5,6,4]
//输出：1 或 5
//解释：你的函数可以返回索引 1，其峰值元素为 2；
//     或者返回索引 5， 其峰值元素为 6。
//提示：
//
//1 <= nums.length <= 1000
//-231 <= nums[i] <= 231 - 1
//对于所有有效的 i 都有 nums[i] != nums[i + 1]
//Related Topics
//数组 | 二分查找

public class _162_寻找峰值 {
    class Solution {
        /**
         * 思路, 因为题目中要求 logn 的时间完成，所以应该是binarysearch，出去开头和结尾，对于 array 中的每个位置都有下面四种情况
         *
         * 1. nums[i-1] < nums[i] < nums[i+1] 在右侧一定有峰值
         * 2. nums[i-1] < nums[i] > nums[i+1] 该处就是峰值，直接返回 i
         * 3. nums[i-1] > nums[i] > nums[i+1] 在左侧一定有峰值
         * 4. nums[i-1] > nums[i] < nums[i+1] 在左右两侧都有峰值
         *
         * @param nums
         * @return
         */
        public int findPeakElement(int[] nums) {
            if(nums == null || nums.length == 0|| (nums.length == 2 && nums[0] == nums[1])) {
                return -1;
            }
            if (nums.length == 1) {
                return 0;
            }
            if (nums[nums.length-1] > nums[nums.length-2]) {
                return nums.length - 1;
            }
            if (nums[0] > nums[1]) {
                return  0;
            }
            int left = 0, right = nums.length-1;
            int mid = 0;
            while(left+1<right) {
                mid = left + (right-left)/2;
                if (nums[mid] > nums[mid-1] && nums[mid] > nums[mid+1]) {
                    // 峰值
                    return mid;
                }
                else if (nums[mid] < nums[mid-1] && nums[mid] < nums[mid+1]) {
                    // 左侧或者右侧
                    left = mid;
                }
                else if (nums[mid] < nums[mid-1] && nums[mid] > nums[mid+1]) {
                    // 左侧
                    right = mid;
                }
                else if (nums[mid] > nums[mid-1] && nums[mid] < nums[mid+1]) {
                    // 右侧
                    left = mid;
                }
            }
            return -1;
        }
    }
}
