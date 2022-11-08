package leetcode.jzhoffer;

import leetcode.arrays._528_按权重随机选择;

import java.util.Random;

/**
 * @author mayanwei
 * @date 2022-11-05.
 * @see _528_按权重随机选择
 */
public class 剑指_Offer_II_071_按权重生成随机数{
    class Solution{
        private int[] preSum;
        private Random random = new Random();

        public Solution(int[] w) {
            int n = w.length;
            //构建前缀和数组，便宜一位留给presSum[0]
            preSum = new int[n + 1];
            for (int i = 1; i <= n; i++) {
                preSum[i] = preSum[i - 1] + w[i - 1];
            }
        }

        public int pickIndex() {
            int n = preSum.length;
            // 在闭区间 [1, preSum[n-1]] 中随机选择一个数字
            int target = random.nextInt(preSum[n - 1]) + 1;
            // 获取target在前缀数组 preSum 中的索引
            // 前缀和数组 preSum 和原始数组 w 有一位索引偏移
            return left_bound(preSum, target) - 1;
        }

        // 搜索左侧边界的二分搜索
        public int left_bound(int[] nums, int target) {
            if (nums.length == 0) {
                return -1;
            }
            int left = 0, right = nums.length;
            while (left < right) {
                int mid = left + (right - left) / 2;
                if (nums[mid] == target) {
                    right = mid;
                }
                else if (nums[mid] < target) {
                    left = mid + 1;
                }
                else if (nums[mid] > target) {
                    right = mid;
                }
            }
            return left;
        }
    }

}

