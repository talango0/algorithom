package leetcode.jzhoffer;

import leetcode.stack._84_柱状图中最大的矩形;
import leetcode.stack._85_最大矩形;

/**
 * @author mayanwei
 * @date 2022-10-13.
 * @see _84_柱状图中最大的矩形
 * @see _85_最大矩形
 */
public class 剑指_Offer_II_040_矩阵中最大的矩形{
    class Solution{
        public int maximalRectangle(String[] matrix) {
            if (matrix == null || matrix.length == 0) {
                return 0;
            }
            char[][] chars = new char[matrix.length][matrix[0].length()];
            int i = 0;
            for (String str : matrix) {
                chars[i++] = str.toCharArray();
            }
            return maximalRectangle(chars);
        }

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
