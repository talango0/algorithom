package leetcode.arrays;
//给你一个整数数组 nums，将它重新排列成 nums[0] < nums[1] > nums[2] < nums[3]... 的顺序。
//
// 你可以假设所有输入数组都可以得到满足题目要求的结果。
//
//
//
// 示例 1：
//
//
//输入：nums = [1,5,1,1,6,4]
//输出：[1,6,1,5,1,4]
//解释：[1,4,1,5,1,6] 同样是符合题目要求的结果，可以被判题程序接受。
//
//
// 示例 2：
//
//
//输入：nums = [1,3,2,2,3,1]
//输出：[2,3,1,3,1,2]
//
//
//
//
// 提示：
//
//
// 1 <= nums.length <= 5 * 10⁴
// 0 <= nums[i] <= 5000
// 题目数据保证，对于给定的输入 nums ，总能产生满足题目要求的结果
//
//
//
//
// 进阶：你能用 O(n) 时间复杂度和 / 或原地 O(1) 额外空间来实现吗？
//
// Related Topics 数组 分治 快速选择 排序 👍 497 👎 0

import org.junit.Assert;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;

/**
 * @author mayanwei
 * @date 2022-09-24.
 */
public class _324_摆动排序II{
    class Solution{
        public void wiggleSort(int[] nums) {
            int[] arr = nums.clone();
            Arrays.sort(arr);
            int n = nums.length;
            int x = (n + 1) / 2;
            for (int i = 0, j = x - 1, k = n - 1; i < n; i += 2, j--, k--) {
                // 0  <--> n+1/2 -1
                nums[i] = arr[j];
                if (i + 1 < n) {
                    // 1 <--> n-1
                    nums[i + 1] = arr[k];
                }
            }
        }
    }

    @Test
    public void test() {
        int[] arr = {0, 1, 2, 3, 4, 5};
        Solution solution = new Solution();
        solution.wiggleSort(arr);
        Assert.assertArrayEquals(new int[]{2, 5, 1, 4, 0, 3}, arr);
    }
}

