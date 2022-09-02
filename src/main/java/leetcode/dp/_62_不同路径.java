package leetcode.dp;
//一个机器人位于一个 m x n网格的左上角 （起始点在下图中标记为 “Start” ）。
//
//机器人每次只能向下或者向右移动一步。机器人试图达到网格的右下角（在下图中标记为 “Finish” ）。
//
//问总共有多少条不同的路径？
//
//
//
//示例 1：
//
//
//输入：m = 3, n = 7
//输出：28
//示例 2：
//
//输入：m = 3, n = 2
//输出：3
//解释：
//从左上角开始，总共有 3 条路径可以到达右下角。
//1. 向右 -> 向下 -> 向下
//2. 向下 -> 向下 -> 向右
//3. 向下 -> 向右 -> 向下
//示例 3：
//
//输入：m = 7, n = 3
//输出：28
//示例 4：
//
//输入：m = 3, n = 3
//输出：6
//
//
//提示：
//
//1 <= m, n <= 100
//题目数据保证答案小于等于 2 * 109
//
//来源：力扣（LeetCode）
//链接：https://leetcode.cn/problems/unique-paths
//著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。


public class _62_不同路径 {
    // 超时
    class Solution {
        // 添加备忘录或者改为自底向上迭代解法即可降低下面暴力解法的时间复杂度
        public int uniquePaths(int m, int n) {
            return dp(m-1, n-1);
        }
        //定义：从(0,0) 到 (x,y) 有dp(x,y)条路径
        int dp(int x, int y) {
            if (x == 0 && y == 0) {
                return 1;
            }
            if (x < 0 || y < 0) {
                return 0;
            }
            // 状态转移方程
            // 到达 (x,y) 的路径数等于到达(x-1, y) 和 (x, y-1) 路径数之和
            return dp(x-1, y) + dp(x, y-1);
        }
    }

    //加上备忘录
    class Solution1 {
        // 添加备忘录或者改为自底向上迭代解法即可降低下面暴力解法的时间复杂度
        int [][] memo;
        public int uniquePaths(int m, int n) {
            memo = new int[m][n];
            return dp(m-1, n-1);
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
