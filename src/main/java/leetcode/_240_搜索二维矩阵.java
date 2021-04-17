package leetcode;


//编写一个高效的算法来搜索 m x n 矩阵 matrix 中的一个目标值 target。该矩阵具有以下特性：
//
//
// 每行的元素从左到右升序排列。
// 每列的元素从上到下升序排列。
//
//
// 示例:
//
// 现有矩阵 matrix 如下：
//
// [
//  [1,   4,  7, 11, 15],
//  [2,   5,  8, 12, 19],
//  [3,   6,  9, 16, 22],
//  [10, 13, 14, 17, 24],
//  [18, 21, 23, 26, 30]
//]
//
//
// 给定 target = 5，返回 true。
//
// 给定 target = 20，返回 false。
// Related Topics 二分查找 分治算法
// 👍 470 👎 0

public class _240_搜索二维矩阵 {
    class Solution1 {
        public boolean searchMatrix(int[][] matrix, int target) {
            for(int i = 0; i< matrix.length; i++){
                if(search(matrix[i], target)){
                    return true;
                }
            }
            return false;
        }
        public boolean search(int [] nums,int target){
            int i = 0;
            int j = nums.length-1;
            while (i <= j){
                int mid = i+((j-i)>>1);
                if(target == nums[mid]){
                    return true;
                }else if(target < nums[mid]){
                    j = mid - 1;
                }else {
                    i = mid + 1;
                }
            }
            return false;
        }
    }


    class Solution2{
        public boolean searchMatrix(int[][] matrix, int target) {
            if(matrix == null || matrix.length == 0){
                return false;
            }
            int lowerDim = Math.min(matrix.length, matrix[0].length);
            for(int i = 0; i < lowerDim; i++){
                if( searchMatrix(matrix, i, target, true) || searchMatrix(matrix, i, target, false)){
                    return true;
                }
            }
            return false;
        }
        public boolean searchMatrix(int [][] matrix, int i, int target, boolean vertical){

            if(matrix[i][i] == target){
                return  true;
            }

            if(!vertical){ // 水平方向
                int l=0, r=-1;
                if(matrix[i][i] < target){
                    l = i+1;
                    r = matrix[i].length-1;
                }else{
                    l = 0;
                    r = i-1;
                }
                while (l <= r){
                    int m = l + ((r-l)>>1);
                    if(matrix[i][m] == target){
                        return true;
                    }else if(matrix[i][m] > target){
                        r = m-1;
                    }else {
                        l = m+1;
                    }
                }
            }else {//竖直方向
                int l=0, r=-1;
                if(matrix[i][i] < target){
                    l = i+1;
                    r = matrix.length-1;
                }else{
                    l = 0;
                    r = i-1;
                }
                while (l <= r){
                    int m = l + ((r-l)>>1);
                    if(matrix[m][i] == target){
                        return true;
                    }else if(matrix[m][i] > target){
                        r = m-1;
                    }else {
                        l = m+1;
                    }
                }
            }
            return false;
        }
    }


    /**
     * 因为矩阵的行和列都是排序的（从左到右，总上到下），所以查看任何特定值，我们可以修改O（m）或O（n）元素。
     * 思路：
     * 假设矩阵的形状为 A[m][n]
     * 定义两个指针（row, col）。然后知道直到找到target，然后返回true（或者指针执行矩阵维度之外的（row，col））为止，执行如下操作：
     * 若 A[row][col] < target,则 row--，否则若A[row][col] > target,则col++。
     *
     * 这样做的原因在与元素遵循从左到右，从上大小递增的顺序，所以不会漏掉最优解。
     */
    class Solution3{
        public boolean searchMatrix(int[][] matrix, int target) {
            int row = matrix.length;
            if (matrix == null || row == 0) {
                return false;
            }
            int col = matrix[0].length;
            int left = 0;
            for (int i = row - 1; i >= 0; i--) {
                for (int j = left; j < col; j++) {
                    if (matrix[i][j] == target) {
                        return true;
                    } else if (matrix[i][j] > target) {
                        break;
                    } else {
                        left++;
                    }
                }
            }
            return false;
        }
    }


}
