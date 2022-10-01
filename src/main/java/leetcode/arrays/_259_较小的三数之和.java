package leetcode.arrays;
//给定一个长度为 n 的整数数组和一个目标值 target，寻找能够使条件 nums[i] + nums[j] + nums[k] < target 成立的三元组  i, j, k 个数（0 <= i < j < k < n）。
//
//示例：
//输入: nums = [-2,0,1,3], target = 2
//输出: 2
//解释: 因为一共有两个三元组满足累加和小于 2:
//     [-2,0,1]
//     [-2,0,3]
//进阶：是否能在 O(n2) 的时间复杂度内解决？

import org.junit.Assert;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

/**
 * @author mayanwei
 * @date 2022-10-01.
 */
public class _259_较小的三数之和{

    //排序后，固定左端点 i ，内层双指针
    class Solution{
        public int threeSumSmaller(int[] nums, int target) {
            Arrays.sort(nums);
            int j, k, n = nums.length, count = 0, sum;
            for (int i = 0; i < n - 2; ++i) {
                j = i + 1;
                k = n - 1;
                while (j < k) {
                    sum = nums[i] + nums[j] + nums[k];
                    if (sum < target) {
                        count += k - j;
                        j++;
                    }
                    else if (sum >= target) {
                        k--;
                    }
                }
            }
            return count;
        }
    }
    @Test
    public void test(){
        int[] arr = {-2, 0, 1, 3};
        Solution solution = new Solution();
        Assert.assertEquals(solution.threeSumSmaller(arr, 2), 2);
    }
}
