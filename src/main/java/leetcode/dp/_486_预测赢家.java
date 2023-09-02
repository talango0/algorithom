package leetcode.dp;
//给你一个整数数组 nums 。玩家 1 和玩家 2 基于这个数组设计了一个游戏。
//
// 玩家 1 和玩家 2 轮流进行自己的回合，玩家 1 先手。开始时，两个玩家的初始分值都是 0 。
// 每一回合，玩家从数组的任意一端取一个数字（即，nums[0] 或 nums[nums.length - 1]），
// 取到的数字将会从数组中移除（数组长度减 1 ）。玩家选中的数字将会加到他的得分上。
// 当数组中没有剩余数字可取时，游戏结束。
//
// 如果玩家 1 能成为赢家，返回 true 。如果两个玩家得分相等，同样认为玩家 1 是游戏的赢家，
// 也返回 true 。你可以假设每个玩家的玩法都会使他的分数最大化。
//
//
//
// 示例 1：
//输入：nums = [1,5,2]
//输出：false
//解释：一开始，玩家 1 可以从 1 和 2 中进行选择。
//如果他选择 2（或者 1 ），那么玩家 2 可以从 1（或者 2 ）和 5 中进行选择。如果玩家 2 选择了 5 ，
// 那么玩家 1 则只剩下 1（或者 2 ）可选。
//所以，玩家 1 的最终分数为 1 + 2 = 3，而玩家 2 为 5 。
//因此，玩家 1 永远不会成为赢家，返回 false 。
//
// 示例 2：
//输入：nums = [1,5,233,7]
//输出：true
//解释：玩家 1 一开始选择 1 。然后玩家 2 必须从 5 和 7 中进行选择。无论玩家 2 选择了哪个，玩家 1 都可以选择 233 。
//最终，玩家 1（234 分）比玩家 2（12 分）获得更多的分数，所以返回 true，表示玩家 1 可以成为赢家。
//
//
//
// 提示：
//
//
// 1 <= nums.length <= 20
// 0 <= nums[i] <= 10⁷
//
//
// Related Topics 递归 数组 数学 动态规划 博弈 👍 567 👎 0

import org.junit.Test;

/**
 * @author mayanwei
 * @date 2022-07-31.
 * @see _877_石子游戏
 */
public class _486_预测赢家{

    class Solution{
        class Pair{
            int first;
            int second;

            public Pair(int first, int second) {
                this.first = first;
                this.second = second;
            }
        }

        /**
         * <pre>
         * dp[i][j] 表示nums[i,...,j]这些子数组中，先手和后手分别拿了多少分。
         * 结果就是dp[0][n-1],n为nums的长度。
         *
         * 对于nums[i,...,j]
         * 如果先手选择left，即选择nums[i]
         * 得分情况就是 left = nums[i] + dp[i+1][j].second
         *
         * 如果先手选择的right，即选择nums[j]
         * 得分请求就是 right = nums[j] + dp[i][j-1].second
         *
         * 先手选择得分高的
         *
         * left > right ?
         *  { dp[i][j].first = left;  dp[i][j].second = dp[i+1][j].first; }
         * :{ dp[i][j].first = right; dp[i][j].second = dp[i][j-1].first; }
         * </pre>
         * @param nums
         * @return
         */
        public boolean PredictTheWinner(int[] nums) {
            int n = nums.length;
            Pair[][] dp = new Pair[n][n];
            // 初始化dp
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    dp[i][j] = new Pair(0, 0);
                }
            }
            // base case
            for (int i = 0; i < n; i++) {
                dp[i][i] = new Pair(nums[i], 0);
            }

            // 倒着遍历数组
            for (int i = n - 2; i >= 0; i--) {
                for (int j = i + 1; j < n; j++) {
                    // 先手选择最左边或者最右边的分数
                    int left = nums[i] + dp[i + 1][j].second;
                    int right = nums[j] + dp[i][j - 1].second;
                    // 套用状态转移方程
                    // 先手肯定会选择更大的结果，后手的选择随之改变
                    if (left > right) {
                        dp[i][j].first = left;
                        dp[i][j].second = dp[i + 1][j].first;
                    }
                    else {
                        dp[i][j].first = right;
                        dp[i][j].second = dp[i][j - 1].first;
                    }
                }
            }
            Pair res = dp[0][n - 1];
            return res.first >= res.second;
        }
    }


    @Test
    public void test() {
        Solution solution = new Solution();
        boolean b = solution.PredictTheWinner(new int[]{2, 8, 3, 5});
        System.out.println(b);
    }
}
