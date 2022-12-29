package leetcode.arrays;
//给定 n 个非负整数表示每个宽度为 1 的柱子的高度图，计算按此排列的柱子，下雨之后能接多少雨水。
//
//
//
// 示例 1：
//
//
//
//
//输入：height = [0,1,0,2,1,0,1,3,2,1,2,1]
//输出：6
//解释：上面是由数组 [0,1,0,2,1,0,1,3,2,1,2,1] 表示的高度图，在这种情况下，可以接 6 个单位的雨水（蓝色部分表示雨水）。
//
//
// 示例 2：
//
//
//输入：height = [4,2,0,3,2,5]
//输出：9
//
//
//
//
// 提示：
//
//
// n == height.length
// 1 <= n <= 2 * 10⁴
// 0 <= height[i] <= 10⁵
//
//
// Related Topics 栈 数组 双指针 动态规划 单调栈 👍 3676 👎 0

/**
 * 字节
 * @see _11_盛最多水的容器
 * @author mayanwei
 * @date 2022-08-09.
 */
public class _42_接雨水{

    /**
     * 暴力法
     * <pre>
     * ▲         ┌─┐
     * │         │ │
     * ├─┐       │ │
     * │ │   ┌─┐ │ │
     * │ ├─┐ │ ├─┤ │
     * │4│2│0│3│2│5│
     * └─┴─┴─┴─┴─┴─┴─▶
     *  0 1 2 3 4 5
     *  </pre>
     */
    class Solution{
        public int trap(int[] height) {
            int n = height.length;
            int res = 0;
            for (int i = 1; i < n - 1; i++) {
                int l_max = 0, r_max = 0;
                // 找右边最高的柱子
                for (int j = i; j < n; j++) {
                    r_max = Math.max(r_max, height[j]);
                }
                // 找左边最高的柱子
                for (int j = i; j >= 0; j--) {
                    l_max = Math.max(l_max, height[j]);
                }
                // 如果自己就是最高的话
                // l_max = r_max = height[i]
                res += Math.min(l_max, r_max) - height[i];
            }
            return res;
        }
    }

    //备忘录优化

    class Solution2{
        public int trap(int[] height) {
            int n = height.length;
            int res = 0;
            // 数组充当备忘录
            int[] l_max = new int[n];
            int[] r_max = new int[n];
            // 初始化 base case
            l_max[0] = height[0];
            r_max[n - 1] = height[n - 1];
            for (int i = 1; i < n; i++) {
                l_max[i] = Math.max(height[i], l_max[i - 1]);
            }
            for (int i = n - 2; i >= 0; i--) {
                r_max[i] = Math.max(height[i], r_max[i + 1]);
            }
            for (int i = 1; i < n - 1; i++) {
                res += Math.min(l_max[i], r_max[i]) - height[i];
            }
            return res;
        }
    }

    //双指针解法

    class Solution3{
        public int trap(int[] height) {
            int left = 0, right = height.length - 1;
            int res = 0;
            int l_max = 0, r_max = 0;
            while (left < right) {
                l_max = Math.max(l_max, height[left]);
                r_max = Math.max(r_max, height[right]);
                // res += min(l_max, r_max)-height[i]
                if (l_max < r_max) {
                    res += l_max - height[left];
                    left++;
                }
                else {
                    res += r_max - height[right];
                    right--;
                }
            }
            return res;
        }
    }
}
