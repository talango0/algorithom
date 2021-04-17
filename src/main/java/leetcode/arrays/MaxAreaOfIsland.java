package leetcode.arrays;

import java.util.Arrays;

/**
 * Given a non-empty 2D array grid of 0's and 1's, an island is a group of 1's (representing land) connected 4-directionally (horizontal or vertical.) You may assume all four edges of the grid are surrounded by water.
 *
 * Find the maximum area of an island in the given 2D array. (If there is no island, the maximum area is 0.)
 *
 * Example 1:
 *
 * [[0,0,1,0,0,0,0,1,0,0,0,0,0],
 *  [0,0,0,0,0,0,0,1,1,1,0,0,0],
 *  [0,1,1,0,1,0,0,0,0,0,0,0,0],
 *  [0,1,0,0,1,1,0,0,1,0,1,0,0],
 *  [0,1,0,0,1,1,0,0,1,1,1,0,0],
 *  [0,0,0,0,0,0,0,0,0,0,1,0,0],
 *  [0,0,0,0,0,0,0,1,1,1,0,0,0],
 *  [0,0,0,0,0,0,0,1,1,0,0,0,0]]
 * Given the above grid, return 6. Note the answer is not 11, because the island must be connected 4-directionally.
 * Example 2:
 *
 * [[0,0,0,0,0,0,0,0]]
 * Given the above grid, return 0.
 * Note: The length of each dimension in the given grid does not exceed 50.
 */
public class MaxAreaOfIsland {

    static  class Solution {
        int [][] nextStep = {{0,1},{1,0},{0,-1},{-1,0}};//右，下，左，上
        int res = 0;
        Integer step = 0;

        public int maxAreaOfIsland(int[][] grid) {

            for(int i = 0; i< grid.length; i++){
                for(int j = 0; j<grid[i].length; j++){
                    if(grid[i][j] ==1){
                        step = 0;
                        step = dfs(grid, step ,i,j);
                        res = Math.max(res, step );
                    }
                }
            }

            return res;
        }

        private Integer dfs(int[][] grid, Integer step, int i, int j) {
            if(i<0 || i>grid.length-1 || j<0 || j>grid[i].length-1 || grid[i][j]!=1) {

            }else{
                step  += 1;
                grid[i][j] = -1;
                for(int k=0; k<nextStep.length; k++){
                    step = dfs(grid,step,i+nextStep[k][0],j+nextStep[k][1]);
                }
            }
            return step;
        }
    }

    static class Tmp {
        static Integer step = 0;
    }
//    @Test
//    public void testMaxAreaOfIsland(){
//        int [][] grid = {{1,0,1,0,0,0,0,1,0,0,0,0,0},
//                         {1,0,0,0,0,0,0,1,1,1,0,0,0},
//                         {1,1,1,0,1,0,0,0,0,0,0,0,0},
//                         {0,1,0,0,1,1,0,0,1,0,1,0,0},
//                         {0,1,0,0,1,1,0,0,1,1,1,0,0},
//                         {0,0,0,0,0,0,0,0,0,0,1,0,0},
//                         {0,0,0,0,0,0,0,1,1,1,0,0,0},
//                         {0,0,0,0,0,0,0,1,1,0,0,0,0}};
//        int [][] grid = {{1,0,1,0},
//                         {1,0,0,0}};
//        System.out.println(Arrays.deepToString(grid));
//
//        Solution solution = new Solution();
//        int i = solution.maxAreaOfIsland(grid);
//        System.out.println(i);

//    }

    public static void main(String[] args) {
        int [][] grid = {{0,0,1,0,0,0,0,1,0,0,0,0,0},
                {0,0,0,0,0,0,0,1,1,1,0,0,0},
                {0,1,1,0,1,0,0,0,0,0,0,0,0},
                {0,1,0,0,1,1,0,0,1,0,1,0,0},
                {0,1,0,0,1,1,0,0,1,1,1,0,0},
                {0,0,0,0,0,0,0,0,0,0,1,0,0},
                {0,0,0,0,0,0,0,1,1,1,0,0,0},
                {0,0,0,0,0,0,0,1,1,0,0,0,0}};
        System.out.println(Arrays.deepToString(grid));

        Solution solution = new Solution();
        int i = solution.maxAreaOfIsland(grid);
        System.out.println(i);
    }

}
