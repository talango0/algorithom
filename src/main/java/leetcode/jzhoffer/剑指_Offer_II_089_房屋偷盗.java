package leetcode.jzhoffer;

import leetcode.dp._198_打家劫舍;

import java.util.Arrays;
//一个专业的小偷，计划偷窃沿街的房屋。每间房内都藏有一定的现金，影响小偷偷窃的唯一制约因素就是相邻的房屋装有相互连通的防盗系统，如果两间相邻的房屋在同一晚上被
//小偷闯入，系统会自动报警。
//
// 给定一个代表每个房屋存放金额的非负整数数组 nums ，请计算 不触动警报装置的情况下 ，一夜之内能够偷窃到的最高金额。
//
//
//
// 示例 1：
//
//
//输入：nums = [1,2,3,1]
//输出：4
//解释：偷窃 1 号房屋 (金额 = 1) ，然后偷窃 3 号房屋 (金额 = 3)。
//    偷窃到的最高金额 = 1 + 3 = 4 。
//
// 示例 2：
//
//
//输入：nums = [2,7,9,3,1]
//输出：12
//解释：偷窃 1 号房屋 (金额 = 2), 偷窃 3 号房屋 (金额 = 9)，接着偷窃 5 号房屋 (金额 = 1)。
//    偷窃到的最高金额 = 2 + 9 + 1 = 12 。
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
//
//
//
//
// 注意：本题与主站 198 题相同： https://leetcode-cn.com/problems/house-robber/
//
// Related Topics 数组 动态规划 👍 25 👎 0


/**
 * @author mayanwei
 * @date 2022-11-05.
 * @see _198_打家劫舍
 */
public class 剑指_Offer_II_089_房屋偷盗{
    class Solution {
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
}
