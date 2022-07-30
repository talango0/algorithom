package leetcode.dp;

/**
 * @author mayanwei
 * @date 2022-07-31.
 */
public class _312_戳气球{

    class Solution {

        // dp[i][j] = x表示，戳破气球i和气球j之间（开区间，不包括i和j）的所有气球，可以获得的最高分数为x
        int maxCoins(int[] nums) {
            int n = nums.length;
            // 添加两侧的虚拟气球
            int[] points = new int[n + 2];
            points[0] = points[n + 1] = 1;
            for (int i = 1; i <= n; i++) {
                points[i] = nums[i - 1];
            }
            // base case 已经都被初始化为 0
            int[][] dp = new int[n + 2][n + 2];
            // 开始状态转移
            // i 应该从下往上
            for (int i = n; i >= 0; i--) {
                // j 应该从左往右
                for (int j = i + 1; j < n + 2; j++) {
                    // 最后戳破的气球是哪个？
                    for (int k = i + 1; k < j; k++) {
                        // 择优做选择
                        dp[i][j] = Math.max(
                                dp[i][j],
                                dp[i][k] + dp[k][j] + points[i]*points[j]*points[k]
                        );
                    }
                }
            }
            return dp[0][n + 1];
        }
    }
}
