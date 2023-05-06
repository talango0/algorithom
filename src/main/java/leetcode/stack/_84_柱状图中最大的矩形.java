package leetcode.stack;
//给定 n 个非负整数，用来表示柱状图中各个柱子的高度。每个柱子彼此相邻，且宽度为 1 。
//
//求在该柱状图中，能够勾勒出来的矩形的最大面积。
//
//示例 1:
//
//
//
//输入：heights = [2,1,5,6,2,3]
//输出：10
//解释：最大的矩形为图中红色区域，面积为 10
//示例 2：
//
//
//
//输入： heights = [2,4]
//输出： 4
//提示：
//
//1 <= heights.length <=105
//0 <= heights[i] <= 104
//Related Topics
//
//👍 2128, 👎 0

import leetcode.jzhoffer.剑指_Offer_II_040_矩阵中最大的矩形;
import org.junit.jupiter.api.Test;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;
import java.util.LinkedList;

/**
 * 字节
 * @author mayanwei
 * @date 2022-08-27.
 * @see 剑指_Offer_II_040_矩阵中最大的矩形
 * @see _85_最大矩形
 */
public class _84_柱状图中最大的矩形{

    //暴力超时
    class Solution{
        public int largestRectangleArea(int[] heights) {
            int n = heights.length;
            int ans = 0;
            for (int k = 0; k < n; k++) {
                int i = k + 1, j = k - 1;
                while (i < n && heights[i] >= heights[k]) {
                    i++;
                }
                while (j >= 0 && heights[j] >= heights[k]) {
                    j--;
                }
                int areaK = (i - j - 1) * heights[k];
                ans = Math.max(ans, areaK);
            }
            return ans;
        }
    }

    //单调栈
    class Solution2{
        public int largestRectangleArea(int[] heights) {
            int n = heights.length;
            int[] left = new int[n];
            int[] right = new int[n];

            Deque<Integer> mono_stack = new ArrayDeque<Integer>();
            for (int i = 0; i < n; ++i) {
                while (!mono_stack.isEmpty() && heights[mono_stack.peek()] >= heights[i]) {
                    mono_stack.pop();
                }
                left[i] = (mono_stack.isEmpty() ? -1 :mono_stack.peek());
                mono_stack.push(i);
            }

            mono_stack.clear();
            for (int i = n - 1; i >= 0; --i) {
                while (!mono_stack.isEmpty() && heights[mono_stack.peek()] >= heights[i]) {
                    mono_stack.pop();
                }
                right[i] = (mono_stack.isEmpty() ? n :mono_stack.peek());
                mono_stack.push(i);
            }

            int ans = 0;
            for (int i = 0; i < n; ++i) {
                ans = Math.max(ans, (right[i] - left[i] - 1) * heights[i]);
            }
            return ans;

        }
    }


    class Solution3{
        public int largestRectangleArea(int[] heights) {
            int n = heights.length;
            int[] left = new int[n];
            int[] right = new int[n];
            Arrays.fill(right, n);
            Deque<Integer> mono_stack = new LinkedList<Integer>();
            for (int i = 0; i < n; i++) {
                while (!mono_stack.isEmpty() && heights[mono_stack.peek()] >= heights[i]) {
                    right[mono_stack.peek()] = i;
                    mono_stack.pop();
                }
                left[i] = mono_stack.isEmpty() ? -1 :mono_stack.peek();
                mono_stack.push(i);
            }

            int ans = 0;
            for (int i = 0; i < n; i++) {
                ans = Math.max(ans, (right[i] - left[i] - 1) * heights[i]);
            }
            return ans;
        }
    }

    @Test
    public void test(){
        Solution2 solution2 = new Solution2();
        System.out.println(solution2.largestRectangleArea(new int[]{2,1,5,6,2,3}));
    }
}
