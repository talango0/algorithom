package leetcode.dp;
//给定一个三角形 triangle ，找出自顶向下的最小路径和。
//
// 每一步只能移动到下一行中相邻的结点上。相邻的结点 在这里指的是 下标 与 上一层结点下标 相同或者等于 上一层结点下标 + 1 的两个结点。也就是说，如果
//正位于当前行的下标 i ，那么下一步可以移动到下一行的下标 i 或 i + 1 。
//
//
//
// 示例 1：
//
//
//输入：triangle = [[2],[3,4],[6,5,7],[4,1,8,3]]
//输出：11
//解释：如下面简图所示：
//   2
//  3 4
// 6 5 7
//4 1 8 3
//自顶向下的最小路径和为11（即，2+3+5+1= 11）。
//
//
// 示例 2：
//
//
//输入：triangle = [[-10]]
//输出：-10
//
//
//
//
// 提示：
//
//
// 1 <= triangle.length <= 200
// triangle[0].length == 1
// triangle[i].length == triangle[i - 1].length + 1
// -10⁴ <= triangle[i][j] <= 10⁴
//
//
//
//
// 进阶：
//
//
// 你可以只使用 O(n) 的额外空间（n 为三角形的总行数）来解决这个问题吗？
//
//
// Related Topics 数组 动态规划 👍 1123 👎 0

import java.util.Arrays;
import java.util.List;

/**
 * @author mayanwei
 * @date 2022-11-05.
 */
public class _120_三角形最小路径和{
    class Solution{
        /**
         * 第 i 行的第 j 个元素从哪里来？可以从第 i - 1 行第 j 或第 j - 1 个元素下落过来，这就是所谓的状态转移关系：
         * <p>
         * 落到 triangle[i][j] 的最小路径和可以通过落到 triangle[i-1][j] 和 triangle[i-1][j-1] 的最小路径和推导出来。
         * <p>
         * 所以我们造一个 dp 数组，dp[i][j] 表示从 triangle[0][0] 走到 triangle[i][j] 的最小路径和。
         * <p>
         * 进一步，base case 就是 dp[0][0] = triangle[0][0]，我们要找的答案就是 dp[n-1][..] 中的最大值。
         * <p>
         * 状态转移方程：
         * dp[i][j] = min(dp[i - 1][j], dp[i - 1][j - 1]) + triangle[i][j]
         */
        public int minimumTotal(List<List<Integer>> triangle) {
            int n = triangle.size();
            // 定义：走到第i行第j个元素的最小路径和是 dp[i][j]
            int[][] dp = new int[n][n];
            for (int i = 0; i < dp.length; i++) {
                // 因为求最小值，所以全部初始化为极大值
                Arrays.fill(dp[i], Integer.MAX_VALUE);
            }
            // base case
            dp[0][0] = triangle.get(0).get(0);
            // 进行状态转移
            for (int i = 1; i < dp.length; i++) {
                List<Integer> row = triangle.get(i);
                for (int j = 0; j < row.size(); j++) {
                    // 状态转移方程
                    if (j - 1 >= 0) {
                        dp[i][j] = Math.min(dp[i - 1][j], dp[i - 1][j - 1]) + row.get(j);
                    }
                    else {
                        dp[i][j] = dp[i - 1][j] + row.get(j);
                    }
                }
            }
            // 找出落到最后一层的最小路径和
            int res = Integer.MAX_VALUE;
            for (int j = 0; j < dp[n - 1].length; j++) {
                res = Math.min(res, dp[n - 1][j]);
            }
            return res;
        }
    }
}
