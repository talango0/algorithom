package leetcode.arrays;
//给你一个仅由整数组成的有序数组，其中每个元素都会出现两次，唯有一个数只会出现一次。
//
// 请你找出并返回只出现一次的那个数。
//
// 你设计的解决方案必须满足 O(log n) 时间复杂度和 O(1) 空间复杂度。
//
//
//
// 示例 1:
//输入: nums = [1,1,2,3,3,4,4,8,8]
//输出: 2
//
//
// 示例 2:
//输入: nums =  [3,3,7,7,10,11,11]
//输出: 10
//
//
//
//
//
//
//
// 提示:
//
//
// 1 <= nums.length <= 10⁵
// 0 <= nums[i] <= 10⁵
//
//
// Related Topics 数组 二分查找 👍 558 👎 0

/**
 * @author mayanwei
 * @date 2022-11-03.
 */
public class _540_有序数组中的单一元素{
    class Solution {
        public int singleNonDuplicate(int[] nums) {
            if (nums == null || nums.length == 0) {
                return -1;
            }
            int res = nums[0];
            for(int i = 1; i < nums.length; i++) {
                res ^= nums[i];
            }
            return res;
        }
    }

    class Solution1 {
        public int singleNonDuplicate(int[] nums) {
            int n = nums.length;
            int l = 0, r = n - 1;
            while (l < r) {
                int mid = l + r >> 1;
                if (mid % 2 == 0) {
                    if (mid + 1 < n && nums[mid] == nums[mid + 1]) {
                        l = mid +2;
                    }
                    else {
                        r = mid;
                    }
                } else {
                    if (mid - 1 >= 0 && nums[mid - 1] == nums[mid]) {
                        l = mid + 1;
                    }
                    else {
                        r = mid;
                    }
                }
            }
            return nums[r];
        }
    }
}
