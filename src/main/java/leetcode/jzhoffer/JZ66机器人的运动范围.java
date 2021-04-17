package leetcode.jzhoffer;


/**
 地上有一个m行和n列的方格。一个机器人从坐标0,0的格子开始移动，每一次只能向左，右，上，下四个方向移动一格，但是不能进入行坐标和列坐标的数位之和大于k的格子。
 例如，当k为18时，机器人能够进入方格（35,37），因为3+5+3+7 = 18。但是，它不能进入方格（35,38），因为3+5+3+8 = 19。请问该机器人能够达到多少个格子？
 */
public class JZ66机器人的运动范围 {

    /**
     思路：
     1. 定义一个长度为 n*m 的一维数组boolean visited[],记录方格是否已经被访问过
     2. 开始（0，0）位置开始遍历
     3. 终止条件判断
        1）row<0||row>=m
        2) col<0||row>=n
        3) visited[i] == true
        4) checkSum(threshold, row, col)
     */
     /**
     *
     * @param threshold
     * @param rows
     * @param cols
     * @return
     */
    public int movingCount(int threshold, int rows, int cols){
        boolean[] visited = new boolean[rows * cols];
        //从（0，0）开始遍历
        return movingCountCore(threshold,rows,cols,0,0,visited);
    }

    private int movingCountCore(int threshold, int rows, int cols, int row, int col, boolean[] visited) {
        if(row<0 || row>= rows || col<0 || col>= cols){
            return 0;
        }
        int i = row * cols + col;

        if(visited[i] || !isRightSum(threshold, row, col)){
            return 0;
        }

        visited[i] = true;
        return 1 + movingCountCore(threshold, rows, cols, row, col+1, visited)
                 + movingCountCore(threshold, rows, cols, row, col-1, visited)
                 + movingCountCore(threshold, rows, cols, row+1, col, visited)
                 + movingCountCore(threshold, rows, cols, row-1, col, visited);
    }

    private boolean isRightSum(int threshold, int row, int col) {
        int sum = 0;
        while (row != 0){
            sum += row % 10;
            row = row/10;
        }
        while (col != 0){
            sum += col%10;
            col = col/10;
        }
        return sum<=threshold;
    }


    public static void main(String[] args) {
        JZ66机器人的运动范围 solution = new JZ66机器人的运动范围();
        System.out.println(solution.movingCount(18, 35, 37));
    }
}
