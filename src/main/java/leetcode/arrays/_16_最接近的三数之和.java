package leetcode.arrays;

import java.util.Arrays;

/**
 * @author mayanwei
 * @date 2022-10-01.
 * @see ThreeSum
 * @see _167_两数之和II_输入有序数组
 * @see _259_较小的三数之和
 */
public class _16_最接近的三数之和{
    class Solution{
        public int threeSumClosest(int[] nums, int target) {
            if (nums.length < 3) {
                return 0;
            }
            // 别忘了先对数组排序
            Arrays.sort(nums);
            // 记录三数之和与目标值的偏差
            int delta = Integer.MAX_VALUE;
            for (int i = 0; i < nums.length - 2; i++) {
                // 固定 nums[i] 为三数之和中的第一个数
                // 然后对 nums[i+1...] 搜索接近 target-nums[i] 的两数之和。
                int sum = nums[i] + twoSumClosed(nums, i + 1, target - nums[i]);
                if (Math.abs(delta) > Math.abs(target - sum)) {
                    delta = target - sum;
                }
            }
            return target - delta;
        }

        // 在 nums[start ..] 搜索最接近 target 的两数之和
        int twoSumClosed(int[] nums, int start, int target) {
            int lo = start, hi = nums.length - 1;
            // 记录两数之和与目标值的偏差
            int delta = Integer.MAX_VALUE;
            while (lo < hi) {
                int sum = nums[lo] + nums[hi];
                if (Math.abs(delta) > Math.abs(target - sum)) {
                    delta = target - sum;
                }
                if (sum < target) {
                    lo++;
                }
                else {
                    hi--;
                }
            }
            return target - delta;
        }

    }
}
