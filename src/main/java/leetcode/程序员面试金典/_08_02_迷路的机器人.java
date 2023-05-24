package leetcode.程序员面试金典;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author mayanwei
 * @date 2023-05-24.
 */
public class _08_02_迷路的机器人{
    /**
     * 算法时间负载：O(2^(r+c)) ，因为每个路径都有 r + c 步，每步都有两种选择。
     */
    class Solution{
        public List<List<Integer>> pathWithObstacles(int[][] obstacleGrid) {
            if (obstacleGrid == null || obstacleGrid.length == 0) {
                return null;
            }
            ArrayList<List<Integer>> path = new ArrayList<>();
            if (getPath(obstacleGrid, obstacleGrid.length - 1, obstacleGrid[0].length - 1, path)) {
                return path;
            }
            return new ArrayList<>();
        }

        private boolean getPath(int[][] obstacleGrid, int r, int c, ArrayList<List<Integer>> path) {
            if (r < 0 || c < 0 || obstacleGrid[r][c] == 1) {
                return false;
            }
            boolean isAtOrigin = (r == 0) && (c == 0);
            // 如果有一条路径从起点通向这里，把它添加到我的位置
            if (isAtOrigin || getPath(obstacleGrid, r, c - 1, path) || getPath(obstacleGrid, r - 1, c, path)) {
                path.add(Arrays.asList(r, c));
                return true;
            }
            return false;
        }
    }


    /**
     * 时间复杂度：O(r*c),即每个格子只访问一次
     */
     class Solution1{
            public List<List<Integer>> pathWithObstacles(int[][] obstacleGrid) {
                if (obstacleGrid == null || obstacleGrid.length == 0) {
                    return null;
                }
                ArrayList<List<Integer>> path = new ArrayList<>();
                int[][] memo = new int[obstacleGrid.length][obstacleGrid[0].length];
                for (int[] arr : memo) {
                    Arrays.fill(arr, -1);
                }
                if (getPath(obstacleGrid, obstacleGrid.length - 1, obstacleGrid[0].length - 1, path, memo)) {
                    return path;
                }
                return new ArrayList<>();
            }

            private boolean getPath(int[][] obstacleGrid, int r, int c, ArrayList<List<Integer>> path, int[][] memo) {
                if (r < 0 || c < 0 || obstacleGrid[r][c] == 1) {
                    return false;
                }
                // 如果该节点访问过了，则直接返回
                if (memo[r][c] != -1) {
                    return false;
                }
                boolean isAtOrigin = (r == 0) && (c == 0);
                // 如果有一条路径从起点通向这里，把它添加到我的位置
                if (isAtOrigin || getPath(obstacleGrid, r, c - 1, path, memo) || getPath(obstacleGrid, r - 1, c, path, memo)) {
                    path.add(Arrays.asList(r, c));
                    return true;
                }
                memo[r][c] = 1;
                return false;
            }
        }
}
