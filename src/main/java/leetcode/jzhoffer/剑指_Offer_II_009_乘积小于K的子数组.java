package leetcode.jzhoffer;
//给定一个正整数数组nums和整数 k，请找出该数组内乘积小于k的连续的子数组的个数。
//
//
//
//示例 1:
//
//输入: nums = [10,5,2,6], k = 100
//输出: 8
//解释: 8 个乘积小于 100 的子数组分别为: [10], [5], [2], [6], [10,5], [5,2], [2,6], [5,2,6]。
//需要注意的是 [10,5,2] 并不是乘积小于100的子数组。
//示例 2:
//
//输入: nums = [1,2,3], k = 0
//输出: 0
//
//
//提示:
//
//1 <= nums.length <= 3 * 104
//1 <= nums[i] <= 1000
//0 <= k <= 106

import leetcode.arrays._713_乘积小于K的子数组;

/**
 * @see _713_乘积小于K的子数组
 */
public class 剑指_Offer_II_009_乘积小于K的子数组 {
    class Solution {
        public int numSubarrayProductLessThanK(int[] nums, int k) {
            //同样排除k为1的情况比如 [1,1,1] k=1

            if (k <= 1) {
                return 0;
            }
            int left = 0;
            int right = 0;
            // 创建一个变量记录路上的乘积
            int mul = 1; //记录连续数组的组合个数
            int ans = 0;
            //用右指针遍历整个数组，每次循环右指针右移一次
            while (right < nums.length) { //记录乘积
                mul *= nums[right]; //当大于等于k，左指针右移并把之前左指针的数除掉
                while (mul >= k) {
                    mul /= nums[left];
                    left++;
                }
                //每次右指针位移到一个新位置，应该加上 x 种数组组合：
                // nums[right]
                // nums[right-1], nums[right]
                // nums[right-2], nums[right-1], nums[right]
                // nums[left], ......, nums[right-2], nums[right-1], nums[right]
                // 共有 right - left + 1 种
                ans += right - left + 1;
                // 右指针右移
                right++;
            }
            return ans;
        }
    }

}
