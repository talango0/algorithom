package leetcode.arrays;
//给你一个 下标从 0 开始 的正整数数组 w ，其中 w[i] 代表第 i 个下标的权重。
//
// 请你实现一个函数 pickIndex ，它可以 随机地 从范围 [0, w.length - 1] 内（含 0 和 w.length - 1）选出并返回一
//个下标。选取下标 i 的 概率 为 w[i] / sum(w) 。
//
//
//
//
//
// 例如，对于 w = [1, 3]，挑选下标 0 的概率为 1 / (1 + 3) = 0.25 （即，25%），而选取下标 1 的概率为 3 / (1 +
// 3) = 0.75（即，75%）。
//
//
//
//
// 示例 1：
//
//
//输入：
//["Solution","pickIndex"]
//[[[1]],[]]
//输出：
//[null,0]
//解释：
//Solution solution = new Solution([1]);
//solution.pickIndex(); // 返回 0，因为数组中只有一个元素，所以唯一的选择是返回下标 0。
//
// 示例 2：
//
//
//输入：
//["Solution","pickIndex","pickIndex","pickIndex","pickIndex","pickIndex"]
//[[[1,3]],[],[],[],[],[]]
//输出：
//[null,1,1,1,1,0]
//解释：
//Solution solution = new Solution([1, 3]);
//solution.pickIndex(); // 返回 1，返回下标 1，返回该下标概率为 3/4 。
//solution.pickIndex(); // 返回 1
//solution.pickIndex(); // 返回 1
//solution.pickIndex(); // 返回 1
//solution.pickIndex(); // 返回 0，返回下标 0，返回该下标概率为 1/4 。
//
//由于这是一个随机问题，允许多个答案，因此下列输出都可以被认为是正确的:
//[null,1,1,1,1,0]
//[null,1,1,1,1,1]
//[null,1,1,1,0,0]
//[null,1,1,1,0,1]
//[null,1,0,1,0,0]
//......
//诸若此类。
//
//
//
//
// 提示：
//
//
// 1 <= w.length <= 10⁴
// 1 <= w[i] <= 10⁵
// pickIndex 将被调用不超过 10⁴ 次
//
// Related Topics 数学 二分查找 前缀和 随机化 👍 250 👎 0

import java.util.Random;

/**
 * @author mayanwei
 * @date 2022-07-01.
 */
public class _528_按权重随机选择{

    class Solution {
        private int [] preSum;
        private Random rand = new Random();
        public Solution(int[] w) {

            int n = w.length;
            //构建前缀和数组，偏移一位留给 preSum[0]
            preSum = new int[n+1];
            preSum[0] = 0;
            // preSum[i] = sum(s[0..i-1])
            for (int i = 1; i <= n; i++) {
                preSum[i] = preSum[i-1] + w[i-1];
            }

        }
        // 搜索左侧边界的二分搜索
        public int left_bount(int [] nums, int target) {
            if (nums.length == 0) {
                return -1;
            }
            int left = 0, right = nums.length;
            while (left<right) {
                int mid = left + (right-left)/2;
                if (nums[mid] == target) {
                    right = mid;
                } else if (nums[mid] < target) {
                    left = mid + 1;
                } else if (nums[mid] > target) {
                    right = mid;
                }
            }
            return left;
        }
        public int pickIndex() {
            int n = preSum.length;
            // 在闭区间 [1, preSum[n-1]] 中随机选择一个数字
            int target = rand.nextInt(preSum[n-1]) + 1;
            // 获取 target 在前缀和数组 preSumpreSum 中的索引
            // 前缀和数组 preSumpreSum 和原始数组 w 有一位索引偏移
            return left_bount(preSum, target) - 1;
        }
    }

}
