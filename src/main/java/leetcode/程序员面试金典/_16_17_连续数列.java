package leetcode.程序员面试金典;
//给定一个整数数组，找出总和最大的连续数列，并返回总和。
//
//示例：
//
//输入： [-2,1,-3,4,-1,2,1,-5,4]
//输出： 6
//解释： 连续子数组 [4,-1,2,1] 的和最大，为 6。
//进阶：
//
//如果你已经实现复杂度为 O(n) 的解法，尝试使用更为精妙的分治法求解。
//
//来源：力扣（LeetCode）
//链接：https://leetcode.cn/problems/contiguous-sequence-lcci
//著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。

import org.junit.jupiter.api.Test;

/**
 * @author mayanwei
 * @date 2023-06-12.
 */
public class _16_17_连续数列{
    /**
     * <pre>
     * ┌──────────────────────────────────────────────┐
     * │  arr = [-2,1,-3,4,-1,2,1,-5,4]               │
     * │  dp[i] the max of subArray at the end of i,  │
     * │  dp[i] = max( dp[i-1],  dp[i-1]+arr[i])      │
     * └──────────────────────────────────────────────┘
     * </pre>
     */
    class Solution{
        public int maxSubArray(int[] nums) {
            int n = nums.length;
            if (n == 0) {
                return 0;
            }
            // 定义： dp[i] 记录以 nums[i] 为结尾的 最大子数组和
            int[] dp = new int[n];
            // base case
            // 第一个元素前面没有子数组
            dp[0] = nums[0];
            // 状态转移方程
            for (int i = 1; i < n; i++) {
                dp[i] = Math.max(nums[i], nums[i] + dp[i - 1]);
            }
            // 得到 nums 的最大子数组
            int res = Integer.MIN_VALUE;
            for (int i = 0; i < n; i++) {
                res = Math.max(res, dp[i]);
            }
            return res;
        }
    }

    class Solution2{
        public int maxSubArray(int[] nums) {
            if (nums == null || nums.length == 0) {
                return 0;
            }
            int maxSum = nums[0];
            int sum = nums[0];
            for (int i = 1; i < nums.length; i++) {
                sum = Math.max(nums[i], sum + nums[i]);
                if (maxSum < sum) {
                    maxSum = sum;
                }
                else if (sum < 0) {
                    sum = 0;
                }
            }
            return maxSum;
        }
    }

    class Solution3{
        public int maxSubArray(int[] nums) {
            if (nums == null || nums.length == 0) {
                return 0;
            }
            int maxSum = nums[0];
            int sum = nums[0];
            for (int i = 1; i < nums.length; i++) {
                if (sum >= 0) {
                    sum += nums[i];
                }
                if (maxSum < sum) {
                    maxSum = sum;
                }
                else if (sum < 0) {
                    sum = 0;
                }
            }
            return maxSum;
        }
    }

    @Test
    public void test() {
        Solution3 solution3 = new Solution3();
        System.out.println(solution3.maxSubArray(new int[]{-2, 1})); //1
        System.out.println(solution3.maxSubArray(new int[]{-1})); //-1
    }
}
