package leetcode.stack;
//给定一个仅包含 0 和 1 、大小为 rows x cols 的二维二进制矩阵，找出只包含 1 的最大矩形，并返回其面积。
//
//示例 1：
//
//
//输入：matrix =
// [["1","0","1","0","0"],
//  ["1","0","1","1","1"],
//  ["1","1","1","1","1"],
//  ["1","0","0","1","0"]]
//输出：6
//解释：最大矩形如上图所示。
//示例 2：
//
//输入：matrix = []
//输出：0
//示例 3：
//
//输入：matrix = [["0"]]
//输出：0
//示例 4：
//
//输入：matrix = [["1"]]
//输出：1
//示例 5：
//
//输入：matrix = [["0","0"]]
//输出：0
//提示：
//
//rows == matrix.length
//cols == matrix[0].length
//1 <= row, cols <= 200
//matrix[i][j] 为 '0' 或 '1'
//Related Topics
//
//👍 1356, 👎 0


import leetcode.jzhoffer.剑指_Offer_II_040_矩阵中最大的矩形;

import java.util.Stack;

/**
 * @author mayanwei
* @date 2022-08-27.
 * @see _84_柱状图中最大的矩形
 * @see 剑指_Offer_II_040_矩阵中最大的矩形
 */
public class _85_最大矩形{

    // 暴力法

    class Solution{
        public int maximalRectangle(char[][] matrix) {
            if (matrix.length == 0) {
                return 0;
            }
            // 保存以当前数组结尾的连续 1 的个数
            int[][] width = new int[matrix.length][matrix[0].length];
            int maxArea = 0;
            // 遍历每一行
            for (int row = 0; row < matrix.length; row++) {
                for (int col = 0; col < matrix[0].length; col++) {
                    // 更新width
                    if (matrix[row][col] == '1') {
                        if (col == 0) {
                            width[row][col] = 1;
                        }
                        else {
                            width[row][col] = width[row][col - 1] + 1;
                        }
                    }
                    else {
                        width[row][col] = 0;
                    }
                    // 记录所有行中最小数
                    int minWidth = width[row][col];
                    // 向上扩展
                    for (int up_row = row; up_row >= 0; up_row--) {
                        int height = row - up_row + 1;
                        // 找到最小的数作为矩阵的宽
                        minWidth = Math.min(minWidth, width[up_row][col]);
                        // 更新面积
                        maxArea = Math.max(maxArea, height * minWidth);
                    }
                }

            }
            return maxArea;
        }
    }

    // 参考84题
    class Solution2{
        public int maximalRectangle(char[][] matrix) {
            if (matrix.length == 0) {
                return 0;
            }
            int[] heights = new int[matrix[0].length];
            int maxArea = 0;
            for (int row = 0; row < matrix.length; row++) {
                // 遍历每一列更新高度
                for (int col = 0; col < matrix[0].length; col++) {
                    if (matrix[row][col] == '1') {
                        heights[col] += 1;
                    }
                    else {
                        heights[col] = 0;
                    }
                }
                // 调用 84 题的解法，更新函数
                maxArea = Math.max(maxArea, largetRetangle(heights));
            }
            return maxArea;
        }

        public int largetRetangle(int[] heights) {
            int maxArea = 0;
            Stack<Integer> stack = new Stack<>();
            int p = 0;
            while (p < heights.length) {
                // 栈空入占
                if (stack.isEmpty()) {
                    stack.push(p);
                    p++;
                }
                else {
                    int top = stack.peek();
                    // 当前高度大于等于栈顶，入栈
                    if (heights[p] >= heights[top]) {
                        stack.push(p);
                        p++;
                    }
                    else {
                        // 保存栈顶高度
                        int height = heights[stack.pop()];
                        // 左边第一个小于当前柱子的下标
                        int leftLessMin = stack.isEmpty() ? -1 :stack.peek();
                        // 右边第一个小于当前柱子的下标
                        int rightLessMin = p;
                        // 计算面积
                        int area = (rightLessMin - leftLessMin - 1) * height;
                        maxArea = Math.max(maxArea, area);

                    }
                }
            }
            while (!stack.isEmpty()) {
                // 保存栈顶高度
                int height = heights[stack.pop()];
                // 左边第一个小于当前柱子的下标
                int leftLessMin = stack.isEmpty() ? -1 :stack.peek();
                // 右边没有小于当前高度的柱子，所以赋值为数组长度便于计算
                int rightLessMin = heights.length;
                int area = (rightLessMin - leftLessMin - 1) * height;
                maxArea = Math.max(area, maxArea);
            }
            return maxArea;
        }
    }


    /**
     * 1ms
     */
    class Solution3{
        //矩阵搜索
        //递归
        //递归=循环+栈
        public int maximalRectangle(char[][] matrix) {
            // 矩阵宽度
            int n = matrix.length;
            if (n == 0) {
                return 0;
            }
            int ans = 0;
            // 高度
            int[] heights = new int[matrix[0].length];

            for (char[] rows : matrix) {
                buildHeights(rows, heights);//构建高度表
                ans = Math.max(ans, maxRect(heights));//保存最大面积
            }

            return ans;
        }

        public void buildHeights(char[] rows, int[] heights) {
            for (int i = 0; i < rows.length; i++) {
                if (rows[i] == '1') {
                    heights[i]++;//高度
                }
                else {
                    heights[i] = 0;//0
                }
            }
        }

        public int maxRect(int[] heights) {
            // 构建栈
            int[] stack = new int[heights.length + 1];
            // 栈为空
            int top = -1;
            // 保存第一个
            stack[++top] = -1;
            int ans = 0;
            //进栈
            for (int i = 0; i < heights.length; i++) {
                while (stack[top] > -1 && heights[i] < heights[stack[top]]) {
                    int height = heights[stack[top--]]; //弹出的数据
                    ans = Math.max(ans, height * (i - stack[top] - 1));//保存面积
                }
                stack[++top] = i;//压栈
            }
            //出栈
            while (stack[top] > -1) {
                int height = heights[stack[top--]]; //弹出的数据
                ans = Math.max(ans, height * (heights.length - stack[top] - 1));//保存面积
            }
            return ans;//面积
        }

    }

}
