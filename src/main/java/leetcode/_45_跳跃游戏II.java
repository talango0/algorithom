package leetcode;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
//给定一个非负整数数组，你最初位于数组的第一个位置。
// 数组中的每个元素代表你在该位置可以跳跃的最大长度。
// 你的目标是使用最少的跳跃次数到达数组的最后一个位置。
//
// 示例:
// 输入: [2,3,1,1,4]
//输出: 2
//解释: 跳到最后一个位置的最小跳跃数是 2。
//从下标为 0 跳到下标为 1 的位置，跳1步，然后跳3步到达数组的最后一个位置。
//
//
// 说明:
//
// 假设你总是可以到达数组的最后一个位置。
// Related Topics 贪心算法 数组
// 👍 733 👎 0

/**
 * @see _55_跳跃游戏
 */
public class _45_跳跃游戏II{

    class Solution{
        // 保证可以跳到最后，问最少要跳多少次
        public int jump(int[] nums) {
            int n = nums.length;
            // 备忘录都初始化为 n，相当于 INT_MAX
            // 因为从 0 跳到 n - 1 最多 n - 1 步
            memo = new int[n];
            Arrays.fill(memo, n);

            return dp(nums, 0);
        }

        int[] memo;

        // 定义：从索引 p 跳跃到最后一格，至少需要 dp(nums, p) 步
        private int dp(int[] nums, int p) {
            int n = nums.length;
            // base case
            if (p >= n - 1) {
                return 0;
            }
            // 子问题已经计算过
            if (memo[p] != n) {
                return memo[p];
            }
            int steps = nums[p];
            // 你可以选择跳 1 步，2 步...
            for (int i = 1; i <= steps; i++) {
                // 穷举每一个选择
                // 计算每一个子问题的结果
                int subProblem = dp(nums, p + i);
                // 取其中最小的作为最终结果
                memo[p] = Math.min(memo[p], subProblem + 1);
            }
            return memo[p];
        }
    }

    class Solution1{
        public int jump(int[] nums) {
            if (nums == null || nums.length == 0) {
                return 0;
            }
            int cur = 0, next = 0, jump = 0;
            for (int i = 0; i < nums.length; i++) {
                if (cur < i) {
                    jump++;
                    cur = next;
                }
                next = Math.max(next, i + nums[i]);
            }
            return jump;
        }
    }

    class Solution2{
        // 保证可以跳到最后，问最少要跳多少次
        public int jump(int[] nums) {
            int n = nums.length;
            int end = 0, farthest = 0;
            int jumps = 0;
            for (int i = 0; i < n - 1; i++) {
                farthest = Math.max(nums[i] + i, farthest);
                if (end == i) {
                    jumps++;
                    end = farthest;
                }
            }
            return jumps;
        }

    }

    @Test
    public void test() {
        Solution2 solution2 = new Solution2();
        int jump = solution2.jump(new int[]{3, 1, 4, 2, 1, 1, 1});
        System.out.println(jump);
    }


}
