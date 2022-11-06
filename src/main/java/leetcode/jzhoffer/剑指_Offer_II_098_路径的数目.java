package leetcode.jzhoffer;

import leetcode.dp._62_不同路径;

/**
 * @author mayanwei
 * @date 2022-11-05.
 * @see _62_不同路径
 */
public class 剑指_Offer_II_098_路径的数目{
    class Solution{
        // 添加备忘录或者改为自底向上迭代解法即可降低下面暴力解法的时间复杂度
        int[][] memo;

        public int uniquePaths(int m, int n) {
            memo = new int[m][n];
            return dp(m - 1, n - 1);
        }

        //定义：从(0,0) 到 (x,y) 有dp(x,y)条路径
        int dp(int x, int y) {
            if (x == 0 && y == 0) {
                return 1;
            }
            if (x < 0 || y < 0) {
                return 0;
            }
            // 避免冗余计算
            if (memo[x][y] > 0) {
                return memo[x][y];
            }
            // 状态转移方程：
            // 到达 (x, y) 的路径数等于到达 (x - 1, y) 和 (x, y - 1) 路径数之和
            memo[x][y] = dp(x - 1, y) + dp(x, y - 1);
            return memo[x][y];
        }
    }
}
