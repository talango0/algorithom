package leetcode.jzhoffer;

import java.util.Arrays;

public class ArraySort {
    public static class Solution {
        public void reOrderArray(int [] array) {
            int length = array.length;
            if(length<2) {
                return;
            }
            int k = -1;//用于标示已经是奇数最终位置的元素的最后一个元素下标
            for(int i = 0; i<length; i++){
                if(isOdd(array[i])){
                    int j = i;
                    while (j>k+1){
                        reverse(j, j-1, array);
                        j--;
                    }
                    k++;
                }
            }
        }
        public boolean isOdd(int x){
            if(x%2 == 1) {
                return true;
            }
            return false;
        }
        public void reverse(int i,int j, int[] array){
            int tmp = array[i];
            array[i] = array[j];
            array[j] = tmp;
        }
    }

//    public static void main(String[] args) {
//        Solution solution = new Solution();
//        int [] array = {1,2,4,6,3,5,7};
//        solution.reOrderArray(array);
//        System.out.println(Arrays.toString(array));
//    }
    private static int getMinimumTimeCost(int n, int[][] area) {
        int [] array = {1,2,4,6,3,5,7};
        return 0;
    }


    public static void main(String[] args) {
        Solution solution = new Solution();
        int[][] array = {{1, 2, 3, 5, 7, 6},
                {2, 1, 4, 5, 7, 4},
                {3, 4, 5, 6, 3, 6},
                {2, 3, 1, 4, 6, 8},
                {5, 6, 1, 4, 6, 2},
                {4, 2, 4, 1, 1, 6}};
        int n = 6;
        int res = 0;
        int[][] direction = {{0, -1}, {0, 0}, {0, 1},
                {1, -1}, {1, 0}, {1, 1}};

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n - 1; j++) {

            }


            System.out.println(Arrays.toString(array));
        }
    }
//static  class Solution {
//    int [][] nextStep = {{0,1},{1,0},{0,-1},{-1,0}};//右，下，左，上
//    int res = 0;
//    Integer step = 0;
//
//    public int maxAreaOfIsland(int[][] grid) {
//
//        for(int i = 0; i< grid.length; i++){
//            for(int j = 0; j<grid[i].length; j++){
//                if(grid[i][j] ==1){
//                    step = 0;
//                    step = dfs(grid, step ,i,j);
//                    res = Math.max(res, step );
//                }
//            }
//        }
//
//        return res;
//    }
//
//    private Integer dfs(int[][] grid, Integer step, int i, int j) {
//        if(i<0 || i>grid.length-1 || j<0 || j>grid[i].length-1 || grid[i][j]!=1) {
//
//        }else{
//            step  += 1;
//            grid[i][j] = -1;
//            for(int k=0; k<nextStep.length; k++){
//                step = dfs(grid,step,i+nextStep[k][0],j+nextStep[k][1]);
//            }
//        }
//        return step;
//    }
//}

}
/**
 * 1 2 3 4 5 6 7
 * 1 2 3 =>1 3 2 4 5 6 7
 *
 *
 */
