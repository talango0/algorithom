package leetcode.arrays;
//给定一个长度为 n 的整数数组 height 。有 n 条垂线，第 i 条线的两个端点是 (i, 0) 和 (i, height[i]) 。
//
// 找出其中的两条线，使得它们与 x 轴共同构成的容器可以容纳最多的水。
//
// 返回容器可以储存的最大水量。
//
// 说明：你不能倾斜容器。
//
//
//
// 示例 1：
//
//
//
//
//输入：[1,8,6,2,5,4,8,3,7]
//输出：49
//解释：图中垂直线代表输入数组 [1,8,6,2,5,4,8,3,7]。在此情况下，容器能够容纳水（表示为蓝色部分）的最大值为 49。
//
// 示例 2：
//
//
//输入：height = [1,1]
//输出：1
//
//
//
//
// 提示：
//
//
// n == height.length
// 2 <= n <= 10⁵
// 0 <= height[i] <= 10⁴
//
//
// Related Topics 贪心 数组 双指针 👍 3711 👎 0

/**
 * @author mayanwei
 * @date 2022-08-09.
 */
public class _11_盛最多水的容器{
    class Solution{
        // 本题中竖线没有宽度，所以 left 和 right之间能够盛的水就是 min(height[left], height[right]) * (right-left)
        // 用 left 和 right 两个指针从两端向中心收缩，一边收缩一边计算 [left, right] 之间的矩形面积，取最大的面积值即是答案
        public int maxArea(int[] height) {
            int left = 0, right = height.length - 1;
            int res = 0;
            while (left < right) {
                // [left, right] 之间的矩形面积
                int cur_area = Math.min(height[left], height[right]) * (right - left);
                res = Math.max(res, cur_area);
                // 双指针技巧，移动较低的一边
                if (height[left] < height[right]) {
                    left++;
                }
                else {
                    right--;
                }
            }
            return res;
        }
    }
}
