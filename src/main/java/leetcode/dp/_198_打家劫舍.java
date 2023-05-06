package leetcode.dp;
//你是一个专业的小偷，计划偷窃沿街的房屋。每间房内都藏有一定的现金，影响你偷窃的唯一制约因素就是相邻的房屋装有相互连通的防盗系统，
// 如果两间相邻的房屋在同一晚上被小偷闯入，系统会自动报警。
//
// 给定一个代表每个房屋存放金额的非负整数数组，计算你 不触动警报装置的情况下 ，一夜之内能够偷窃到的最高金额。
//
//
//
// 示例 1：
//
//
//输入：[1,2,3,1]
//输出：4
//解释：偷窃 1 号房屋 (金额 = 1) ，然后偷窃 3 号房屋 (金额 = 3)。
//     偷窃到的最高金额 = 1 + 3 = 4 。
//
// 示例 2：
//
//
//输入：[2,7,9,3,1]
//输出：12
//解释：偷窃 1 号房屋 (金额 = 2), 偷窃 3 号房屋 (金额 = 9)，接着偷窃 5 号房屋 (金额 = 1)。
//     偷窃到的最高金额 = 2 + 9 + 1 = 12 。
//
//
//
//
// 提示：
//
//
// 1 <= nums.length <= 100
// 0 <= nums[i] <= 400
//
// Related Topics 数组 动态规划 👍 2175 👎 0

import java.util.Arrays;

/**
 * @see _213_打家劫舍II
 * @see _337_打家劫舍III
 * @author mayanwei
 * @date 2022-06-26.
 */
public class _198_打家劫舍{
    //超时
    class Solution0 {
        public int rob(int[] nums) {
            if (nums == null) {
                return 0;
            }
            return rob(nums, 0);
        }
        private int rob(int [] nums, int index) {
            if (index >= nums.length) {
                return 0;
            }
            return Math.max(
                    //不抢，抢下家
                    rob(nums, index+1),
                    //抢，抢下下家
                    nums[index] + rob(nums, index+2)
            );
        }
    }

    /**
     * 采用备忘录优化重叠子问题
     */
    class Solution1 {
        private int [] mem ;
        public int rob(int[] nums) {
            if (nums == null) {
                return 0;
            }
            mem = new int[nums.length];
            Arrays.fill(mem, -1);
            return rob(nums, 0);
        }

        private int rob(int [] nums, int index) {
            if (index >= nums.length) {
                return 0;
            }
            //存在重叠子问题，可以采用备忘录优化
            if (mem[index] != -1) {
                return mem[index];
            }
            int res = Math.max(
                    //不抢，抢下家
                    rob(nums, index+1),
                    //抢，抢下下家
                    nums[index] + rob(nums, index+2)
            );
            mem[index] = res;
            return res;
        }
    }


    /**
     * 采用自顶向下的动态规划及空间优化的动态规划
     */
    class Solution2 {
        /**
         * 采用自顶向下的动态规划解法
         */
         public int rob0(int[] nums) {
             if (nums == null) {
                 return 0;
             }
             int n = nums.length;
             // dp[i] = x 表示：从第i间房子开始抢劫，最多能抢到的钱为 xx
             // basecase dp[n] = 0; dp[n+1] = 0;
             int [] dp = new int[n+2];
             for (int i = n-1; i >= 0; i--) {
                 dp[i] = Math.max(dp[i+1], dp[i+2] + nums[i]);
             }
             return dp[0];
         }

        /**
         * 可以看出动态规划依赖于最近的两个状态，因此可以进一步优化空间
         */
        public int rob1(int[] nums) {
            if (nums == null) {
                return 0;
            }
            int n = nums.length;
            // dp[i] = x 表示：从第i间房子开始抢劫，最多能抢到的钱为 xx
            // basecase dp[n] = 0; dp[n+1] = 0;
            int dp_i=0, dp_i1=0, dp_i2=0;
            for (int i = n-1; i >= 0; i--) {
                dp_i = Math.max(dp_i1, dp_i2 + nums[i]);
                dp_i2 = dp_i1;
                dp_i1 = dp_i;

            }
            return dp_i;
        }
    }


}
