package leetcode.arrays;

//给定一个大小为 n 的数组，找到其中的多数元素。多数元素是指在数组中出现次数大于 ⌊ n/2 ⌋ 的元素。
//
// 你可以假设数组是非空的，并且给定的数组总是存在多数元素。
//
//
//
// 示例 1:
//
// 输入: [3,2,3]
//输出: 3
//
// 示例 2:
//
// 输入: [2,2,1,1,1,2,2]
//输出: 2
//
// Related Topics 位运算 数组 分治算法
// 👍 798 👎 0


import java.util.Arrays;

public class _169_多数元素 {

    /**
     * 排序方法
     * 时间复杂度 O(nlogn)
     * 空间复杂度 O（1）
     */
    class Solution {
        public int majorityElement(int[] nums) {
            Arrays.sort(nums);
            return nums[nums.length >> 1];
        }
    }


    /**
     *
     * 摩尔投票方法
     *  时间复杂度 O(n)
     *  空间复杂度 O（1）
     */
    class Solution2 {
        public int majorityElement(int[] nums) {
            int count = 0;
            Integer candidate = null;

            for (int num : nums) {
                if (count == 0) {
                    candidate = num;
                }
                count += (num == candidate) ? 1 : -1;
            }

            return candidate;
        }

    }

}
