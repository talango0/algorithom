package leetcode.程序员面试金典;
//给定M×N矩阵，每一行、每一列都按升序排列，请编写代码找出某元素。
//
//示例:
//
//现有矩阵 matrix 如下：
//
//[
//  [1,   4,  7, 11, 15],
//  [2,   5,  8, 12, 19],
//  [3,   6,  9, 16, 22],
//  [10, 13, 14, 17, 24],
//  [18, 21, 23, 26, 30]
//]
//给定 target=5，返回true。
//
//给定target=20，返回false。
//
//来源：力扣（LeetCode）
//链接：https://leetcode.cn/problems/sorted-matrix-search-lcci
//著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。

import org.junit.Test;

/**
 * @author mayanwei
 * @date 2023-05-31.
 */
public class _10_09_排序矩阵查找{
    /**
     * <pre>
     * 简单解法：
     * 针对某一行，进行二分查找，以便找到目标元素，该矩阵有M行，搜索每一行用时 O(logN)，因此这个算法的时间复杂度为 O(MlogN)。
     * 在开始构思更好的算法之前，提一下这个算法。
     *       ◀───────
     * ┌─────────────┐ │
     * │15 20 40  85 │ │
     * │20 35 80  95 │ ▼
     * │30 55 95  105│
     * │40 80 100 120│
     * └─────────────┘
     * 1. 若列的开头大于x，那么 x 位于该列的左边。
     * 2. 若列的末端小于x，那么 x 位于该列的右边。
     * 3. 若行的开头大于x，那么 x 位于该行的上方。
     * 4. 若行的末端小于x，那么 x 位于该行的下方。
     *
     * </pre>
     */
    class Solution{
        public boolean searchMatrix(int[][] matrix, int target) {
            if (matrix == null || matrix.length == 0 || matrix[0].length == 0) {
                return false;
            }
            // 可以从任意位置开始搜索，不过，这里从列的起始元素开始,
            // 记录矩阵右上角的坐标
            int row = 0;
            int col = matrix[0].length - 1;
            while (row < matrix.length && col >= 0 && row >= 0 && col < matrix[0].length) {
                if (matrix[row][col] == target) {
                    return true;
                }
                else if (matrix[row][col] > target) {
                    col--;
                }
                else {
                    row++;
                }
            }
            return false;
        }
    }

    class Solution1{
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
     * <pre>
     * ┌──────┬───────┐
     * │15 20 │40  85 │
     * │20 35 │80  95 │
     * ├──────┼───────┤
     * │30 55 │95  105│
     * │40 80 │100 120│
     * └──────┴───────┘
     * 二分查找，
     * 仔细观察每个元素
     * 每个元素都大于它左边的元素，并且大于它右边的元素。
     * 每个元素都小于它右边的元素，并且小于它下边的元素。
     *
     * 这意味着，任何一个正方形，其右下角的元素是最大的。同样的，左上角的元素是最最小的。
     * 假设要找85，若顺着对角线搜索，可找到元素35 和95，利用这些信息可知85的位置吗？
     * 85不可能位于右下角区域，因为95位于该区域的左上角。
     * 85不可能位于左上角区域，因为35位于该区域的右下角。
     * 85必定位于左小角区域或者右上角区域。
     * 因此，我们将矩阵分为4个区域，以递归方式搜索左下区域和右上区域。这两个区域也会被分成子区域继续搜索。
     * 对角线已经排序了，因此可以利用二分查找法进行高效的搜索。
     * </pre>
     */
    class Solution2 {
        public boolean searchMatrix(int [][] matrix, int target) {
            if (matrix == null || matrix.length == 0 || matrix[0].length == 0) {
                return false;
            }
            Coordinate origin = new Coordinate(0, 0);
            Coordinate dest = new Coordinate(matrix.length-1, matrix[0].length-1);
            Coordinate coordinate = searchMatrix(matrix, origin, dest, target);
            return coordinate == null ? false : true;
        }

        private Coordinate searchMatrix(int[][] matrix, Coordinate origin, Coordinate dest, int target) {
            if (!origin.inbounds(matrix) || !dest.inbounds(matrix)) {
                return null;
            }
            if (matrix[origin.row][origin.col] == target) {
                return origin;
            }
            else if (!origin.isBefore(dest)) {
                return null;
            }

            // 将 start 和 end 分别设置为对角线的起点的和终点，矩阵不一定是正方形
            // 因此对角线的终点也可能不等于dest。
            Coordinate start = (Coordinate) origin.clone();
            int diagDist = Math.min(dest.row - origin.row, dest.col - origin.col);
            Coordinate end = new Coordinate(start.row + diagDist, start.col + diagDist);
            Coordinate p = new Coordinate(0, 0);

            // 在对角线上找到第一个大于x的元素
            while (start.isBefore(end)) {
                p.setToAverage(start, end);
                if (target > matrix[p.row][p.col]) {
                    start.row = p.row + 1 ;
                    start.col = p.col + 1 ;
                } else {
                    end.row = p.row - 1;
                    end.col = p.col - 1;
                }
            }

            // 该矩阵分为4个区域，搜索左下区域和右上区域
            return partitionAndSearch(matrix, origin, dest, start, target);
        }

        private Coordinate partitionAndSearch(int[][] matrix, Coordinate origin, Coordinate dest, Coordinate pivot, int target) {
            Coordinate lowerLeftOrigin = new Coordinate(pivot.row, pivot.col);

            Coordinate lowerLeftDest = new Coordinate(dest.row, pivot.col );
            Coordinate upperRightOrigin = new Coordinate(origin.row, pivot.col);
            Coordinate upperRightDest = new Coordinate(pivot.row-1, dest.col);
            Coordinate lowerLeft = searchMatrix(matrix, lowerLeftOrigin, lowerLeftDest, target);
            if (lowerLeft == null) {
                return searchMatrix(matrix, upperRightOrigin, upperRightDest, target);
            }
            return lowerLeft;

        }

        class Coordinate implements Cloneable {
            public int row, col;

            public Coordinate(int row, int col) {
                this.row = row;
                this.col = col;
            }
            public boolean inbounds(int [][] matrix) {
                return row >= 0 && row < matrix.length
                        && col >= 0 && col < matrix[0].length;
            }
            public boolean isBefore(Coordinate p) {
                return row <= p.row && col <= p.col;
            }
            public Object clone() {
                return new Coordinate(row, col);
            }
            public void setToAverage(Coordinate min, Coordinate max) {
                row = (min.row + max.row) / 2;
                col = (min.col + max.col) / 2;
            }
        }
    }
    @Test
    public void test(){
        Solution2 solution2 = new Solution2();
        boolean b = solution2.searchMatrix(new int[][]{{5},{6}}, 6);
        System.out.println(b);
    }
}
