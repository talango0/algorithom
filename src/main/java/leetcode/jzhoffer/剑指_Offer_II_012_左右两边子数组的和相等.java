package leetcode.jzhoffer;

import leetcode.arrays._724_寻找数组的中心下标;

/**
 * @author mayanwei
 * @date 2022-10-26.
 * @see _724_寻找数组的中心下标
 */
public class 剑指_Offer_II_012_左右两边子数组的和相等{
    class Solution{
        /**
         * 前缀和
         * @param nums
         * @return
         */
        public int pivotIndex(int[] nums) {
            int n = nums.length;
            int[] preSum = new int[n + 1];
            for (int i = 0; i < n; i++) {
                preSum[i + 1] = preSum[i] + nums[i];
            }
            for (int i = 1; i <= n; i++) {
                if (preSum[i - 1] == preSum[n] - preSum[i]) {
                    return i - 1;
                }
            }
            return -1;
        }
    }
}
