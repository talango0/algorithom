package leetcode.dp;

//有 n 个气球，编号为0 到 n - 1，每个气球上都标有一个数字，这些数字存在数组nums中。
//
//现在要求你戳破所有的气球。戳破第 i 个气球，你可以获得nums[i - 1] * nums[i] * nums[i + 1] 枚硬币。
// 这里的 i - 1 和 i + 1代表和i相邻的两个气球的序号。
// 如果 i - 1或 i + 1 超出了数组的边界，那么就当它是一个数字为 1 的气球。
//
//求所能获得硬币的最大数量。
//
//示例 1：
//输入：nums = [3,1,5,8]
//输出：167
//解释：
//nums = [3,1,5,8] --> [3,5,8] --> [3,8] --> [8] --> []
//coins =  3*1*5    +   3*5*8   +  1*3*8  + 1*8*1 = 167
//示例 2：
//
//输入：nums = [1,5]
//输出：10
//
//
//提示：
//
//n == nums.length
//1 <= n <= 300
//0 <= nums[i] <= 100

/**
 * @author mayanwei
 * @date 2022-07-31.
 */
public class _312_戳气球{
//
//    回溯算法遍历框架
//        int res = Integer.MIN_VALUE;
//        /* 输入一组气球，返回戳破它们获得的最大分数 */
//        int maxCoins(int[] nums) {
//            backtrack(nums, 0);
//            return res;
//        }
//        /* 回溯算法的伪码解法 */
//        void backtrack(int[] nums, int socre) {
//            if (nums 为空) {
//                res = max(res, score);
//                return;
//            }
//            for (int i = 0; i < nums.length; i++) {
//                int point = nums[i-1] * nums[i] * nums[i+1];
//                int temp = nums[i];
//                // 做选择
//                在 nums 中删除元素 nums[i]
//                // 递归回溯
//                backtrack(nums, score + point);
//                // 撤销选择
//                将 temp 还原到 nums[i]
//            }
//        }

    class Solution {

        // dp[i][j] = x表示，戳破气球i和气球j之间（开区间，不包括i和j）的所有气球，可以获得的最高分数为x，
        // 题目要求的结果就是dp[0][n+1]的值，而 base case 就是dp[i][j] = 0，其中0 <= i <= n+1, j <= i+1，
        // 因为这种情况下，开区间(i, j)中间根本没有气球可以戳
        // 反向思考，想一想气球i和气球j之间最后一个被戳破的气球可能是哪一个。
        // 这个题目dp[i][j] 依赖的是 dp[j][k] 和 dp[k][j] 已经被计算出来了（其中 i < k < j）
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
