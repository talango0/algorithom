package leetcode.程序员面试金典;

import leetcode.arrays._42_接雨水;
//给定一个直方图(也称柱状图)，假设有人从上面源源不断地倒水，最后直方图能存多少水量?直方图的宽度为 1。
//
//
//
// 上面是由数组 [0,1,0,2,1,0,1,3,2,1,2,1] 表示的直方图，在这种情况下，可以接 6 个单位的水（蓝色部分表示水）。 感谢 Marco
//s 贡献此图。
//
// 示例:
//
// 输入: [0,1,0,2,1,0,1,3,2,1,2,1]
//输出: 6
// Related Topics 栈 数组 双指针
// 👍 32 👎 0

/**
 * 字节
 *
 * @see _42_接雨水
 */
public class _17_21_直方图的水量{

    class Solution{
        public int trap(int[] height) {
            int left = 0, right = height.length - 1;
            int res = 0;
            int l_max = 0, r_max = 0;
            while (left < right) {
                // l_max 是 height[0..left] 中最高柱子的高度，
                // r_max 是 height[right..end] 的最高柱子的高度
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
