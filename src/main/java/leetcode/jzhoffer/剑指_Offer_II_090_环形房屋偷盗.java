package leetcode.jzhoffer;
//一个专业的小偷，计划偷窃一个环形街道上沿街的房屋，每间房内都藏有一定的现金。这个地方所有的房屋都 围成一圈 ，这意味着第一个房屋和最后一个房屋是紧挨着的。同
//时，相邻的房屋装有相互连通的防盗系统，如果两间相邻的房屋在同一晚上被小偷闯入，系统会自动报警 。
//
// 给定一个代表每个房屋存放金额的非负整数数组 nums ，请计算 在不触动警报装置的情况下 ，今晚能够偷窃到的最高金额。
//
//
//
// 示例 1：
//
//
//输入：nums = [2,3,2]
//输出：3
//解释：你不能先偷窃 1 号房屋（金额 = 2），然后偷窃 3 号房屋（金额 = 2）, 因为他们是相邻的。
//
//
// 示例 2：
//
//
//输入：nums = [1,2,3,1]
//输出：4
//解释：你可以先偷窃 1 号房屋（金额 = 1），然后偷窃 3 号房屋（金额 = 3）。
//     偷窃到的最高金额 = 1 + 3 = 4 。
//
// 示例 3：
//
//
//输入：nums = [0]
//输出：0
//
//
//
//
// 提示：
//
//
// 1 <= nums.length <= 100
// 0 <= nums[i] <= 1000
//
//
//
//
//
// 注意：本题与主站 213 题相同： https://leetcode-cn.com/problems/house-robber-ii/
//
// Related Topics 数组 动态规划 👍 31 👎 0

import leetcode.dp._213_打家劫舍II;

import java.util.Arrays;

/**
 * @author mayanwei
 * @date 2022-11-05.
 * @see _213_打家劫舍II
 */
public class 剑指_Offer_II_090_环形房屋偷盗{
    class Solution{
        /**
         * 首先，首尾房间不能同事被抢，那么只有3中不同的情况，要么都不被抢，要么第一间
         * 房子抢最后一间不抢；要么最后一间房子被抢第一间不抢。
         * <p>
         * 这三种情况哪个结果最大，就是最终答案。其实，情况一的结果肯定最小，
         * 我们只要比较情况二和情况三就行了，因为这两种情况对于房子的选择余地比情况一大，
         * 房子里的钱数都是非负数，所以选择余地大，最优决策结果肯定不会小。
         */
        public int rob(int[] nums) {
            int n = nums.length;
            if (n == 1) {
                return nums[0];
            }
            int[] memo1 = new int[n];
            int[] memo2 = new int[n];
            Arrays.fill(memo1, -1);
            Arrays.fill(memo2, -1);
            // 两次调用使用两个不同的备忘录
            return Math.max(
                    dp(nums, 0, n - 2, memo1),
                    dp(nums, 1, n - 1, memo2)
            );
        }

        // 定义： 计算闭区间 [start, end] 最优结果
        int dp(int[] nums, int start, int end, int[] memo) {
            if (start > end) {
                return 0;
            }
            if (memo[start] != -1) {
                return memo[start];
            }
            // 状态转移方程
            int res = Math.max(
                    dp(nums, start + 2, end, memo) + nums[start],
                    dp(nums, start + 1, end, memo)
            );
            memo[start] = res;
            return res;
        }
    }
}
