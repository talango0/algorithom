package leetcode.jzhoffer;

import leetcode.stack._84_柱状图中最大的矩形;
import leetcode.stack._85_最大矩形;

import java.util.Arrays;
import java.util.Deque;
import java.util.LinkedList;

/**
 * @author mayanwei
 * @date 2022-10-12.
 * @see _84_柱状图中最大的矩形
 * @see _85_最大矩形
 */
public class 剑指_Offer_II_039_直方图最大矩形面积{
    class Solution{
        /**
         * 单调栈
         * 时间复杂度 O(N)
         */
        public int largestRectangleArea(int[] heights) {
            if (heights == null || heights.length == 0) {
                return 0;
            }
            int n = heights.length;
            Deque<Integer> monoStack = new LinkedList<>();
            // left[i] 表示 nums[0...i] 大于等于 nums[i] 时的最小下标
            int[] left = new int[n];
            // right[i] 表示 nums[i...n-1] 大于等于 nums[i] 时的最大下标
            int[] right = new int[n];
            Arrays.fill(right, n);
            for (int i = 0; i < n; i++) {
                while (!monoStack.isEmpty() && heights[monoStack.peek()] >= heights[i]) {
                    right[monoStack.peek()] = i;
                    monoStack.pop();
                }
                left[i] = monoStack.isEmpty() ? -1 :monoStack.peek();
                monoStack.push(i);
            }
            int ans = 0;
            for (int i = 0; i < n; i++) {
                ans = Math.max(ans, (right[i] - left[i] - 1) * heights[i]);
            }
            return ans;
        }
    }
}
