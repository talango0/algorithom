package leetcode.jzhoffer;
//给定一个只包含整数的有序数组 nums ，每个元素都会出现两次，唯有一个数只会出现一次，请找出这个唯一的数字。
//
// 你设计的解决方案必须满足 O(log n) 时间复杂度和 O(1) 空间复杂度。
//
//
//
// 示例 1:
//
//
//输入: nums = [1,1,2,3,3,4,4,8,8]
//输出: 2
//
//
// 示例 2:
//
//
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
//
//
//
// 注意：本题与主站 540 题相同：https://leetcode-cn.com/problems/single-element-in-a-sorted-
//array/
//
// Related Topics 数组 二分查找 👍 46 👎 0

import leetcode.arrays._540_有序数组中的单一元素;

/**
 * @author mayanwei
 * @date 2022-11-03.
 * @see _540_有序数组中的单一元素
 */
public class 剑指_Offer_II_070_排序数组中只出现一次的数字{
    class Solution{
        public int singleNonDuplicate(int[] nums) {
            if (nums == null || nums.length == 0) {
                return -1;
            }
            int res = nums[0];
            for (int i = 1; i < nums.length; i++) {
                res ^= nums[i];
            }
            return res;
        }
    }
}
