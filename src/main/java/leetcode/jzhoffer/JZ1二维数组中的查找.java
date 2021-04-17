package leetcode.jzhoffer;

/**

 题目描述
 在一个二维数组中（每个一维数组的长度相同），每一行都按照从左到右递增的顺序排序，每一列都按照从上到下递增的顺序排序。请完成一个函数，输入这样的一个二维数组和一个整数，判断数组中是否含有该整数。
 示例1
 输入
 7,[[1,2,8,9],[2,4,9,12],[4,7,10,13],[6,8,11,15]]
 返回值
 true

 */
public class JZ1二维数组中的查找 {
    public class Solution {
        public boolean Find(int target, int [][] array) {
            for(int i = 0;i<array.length;i++){
                for(int j = 0;j<array[i].length;j++){
                    if(target == array[i][j]){
                        return true;
                    }
                }
            }
            return false;
        }
    }

    /**
     * 采用二分查找
     */
    public class Solution2 {
        public boolean Find(int target, int [][] array) {
            if(array == null){
                return false;
            }
            int rows = array.length;
            int cols = array[0].length;
            if(rows<=0 || cols<= 0){
                return false;
            }
            int row = 0;
            int col = cols -1;
            while(row<rows && col>=0){
                if(array[row][col]== target){
                    return true;
                }else if(array[row][col]>target){
                    col--;
                }else{
                    row++;
                }
            }
            return false;

        }
    }
}
