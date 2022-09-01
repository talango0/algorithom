package leetcode.math;
//给定一个非负整数numRows，生成「杨辉三角」的前numRows行。
//
//在「杨辉三角」中，每个数是它左上方和右上方的数的和。
//
//
//
//
//
//示例 1:
//
//输入: numRows = 5
//输出: [[1],[1,1],[1,2,1],[1,3,3,1],[1,4,6,4,1]]
//示例2:
//
//输入: numRows = 1
//输出: [[1]]
//
//
//提示:
//
//1 <= numRows <= 30
//
//来源：力扣（LeetCode）
//链接：https://leetcode.cn/problems/pascals-triangle
//著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
import java.util.ArrayList;
import java.util.List;

public class _118_杨辉三角 {
    class Solution {
        public List<List<Integer>> generate(int numRows) {
            List<List<Integer>> res = new ArrayList<>();
            if (numRows < 1) {
                return res;
            }
            // 先把第一层装进去作为 base case
            List<Integer> firstRow = new ArrayList<>();
            firstRow.add(1);
            res.add(firstRow);
            for (int i = 2; i<=numRows; i++) {
                List<Integer> prevRow = res.get(res.size()-1);
                res.add(generateNextRow(prevRow));
            }
            return res;
        }
        List<Integer> generateNextRow(List<Integer> prevRow) {
            List<Integer> curRow = new ArrayList<>();
            curRow.add(1);
            for (int i = 0; i< prevRow.size()-1; i++) {
                curRow.add(prevRow.get(i) + prevRow.get(i+1));
            }
            curRow.add(1);
            return curRow;
        }
    }

    // 递归方式
    class Solution2 {
        // 定义：输入 numRows，返回行数为 numRows 的杨辉三角
        public List<List<Integer>> generate(int numRows) {
            // 递归的 base case
            if (numRows == 1) {
                List<List<Integer>> triangle = new ArrayList<>();
                // 先把第一层装进去作为 base case
                List<Integer> firstRow = new ArrayList<>();
                firstRow.add(1);
                triangle.add(firstRow);
                return triangle;
            }

            // 先递归生成高度为 numRows - 1 的杨辉三角
            List<List<Integer>> triangle = generate(numRows - 1);

            // 根据最底层元素生成一行新元素
            List<Integer> bottomRow = triangle.get(triangle.size() - 1);
            List<Integer> newRow = new ArrayList<>();
            newRow.add(1);
            for (int i = 0; i < bottomRow.size() - 1; i++) {
                newRow.add(bottomRow.get(i) + bottomRow.get(i + 1));
            }
            newRow.add(1);
            // 把新的一行放到杨辉三角底部
            triangle.add(newRow);

            return triangle;
        }
    }
}
