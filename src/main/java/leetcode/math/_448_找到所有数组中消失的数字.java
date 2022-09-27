package leetcode.math;

import java.util.LinkedList;
import java.util.List;
//给你一个含 n 个整数的数组 nums ，其中 nums[i] 在区间 [1, n] 内。请你找出所有在 [1, n] 范围内但没有出现在 nums 中的数字，并以数组的形式返回结果。
//
//
//
//示例 1：
//
//输入：nums = [4,3,2,7,8,2,3,1]
//输出：[5,6]
//示例 2：
//
//输入：nums = [1,1]
//输出：[2]
//
//
//提示：
//
//n == nums.length
//1 <= n <= 105
//1 <= nums[i] <= n
//进阶：你能在不使用额外空间且时间复杂度为 O(n) 的情况下解决这个问题吗? 你可以假定返回的数组不算在额外空间内。

public class _448_找到所有数组中消失的数字 {

    class Solution {
        public List<Integer> findDisappearedNumbers(int[] nums) {
            int n = nums.length;
            int[] count = new int[n+1];
            for (int num:nums) {
                count[num] ++;
            }
            List<Integer> res = new LinkedList<>();
            for (int num = 1; num<= n; num++){
                if (count[num] == 0) {
                    res.add(num);
                }
            }
            return res;
        }
    }


    class Solution2 {
        public List<Integer> findDisappearedNumbers(int[] nums) {
            for (int num:nums) {
                // 主要索引，元素大小从1开始，有一位索引偏移
                if (nums[Math.abs(num) - 1] < 0) {
                    // 之前已经把对应的索引的元素变成了负数了
                    // 这说明num重复出现了两次，但我们不用做，让索引继续保持负数
                }
                else {
                    // 把索引 num - 1 置为负数
                    nums[Math.abs(num) - 1] *= -1;
                }
            }

            List<Integer> res = new LinkedList<>();
            for(int i = 0; i< nums.length; i++) {
                if (nums[i] > 0) {
                    // 说明没有元素和这个索引对应，及找到一个确实元素
                    res.add(i+1);
                }
            }
            return res;
        }
    }
}
