package leetcode.arrays;
//给定一个整数数组 nums，处理以下类型的多个查询:
//
//
// 计算索引 left 和 right （包含 left 和 right）之间的 nums 元素的 和 ，其中 left <= right
//
//
// 实现 NumArray 类：
//
//
// NumArray(int[] nums) 使用数组 nums 初始化对象
// int sumRange(int i, int j) 返回数组 nums 中索引 left 和 right 之间的元素的 总和 ，包含 left 和
//right 两点（也就是 nums[left] + nums[left + 1] + ... + nums[right] )
//
//
//
//
// 示例 1：
//
//
//输入：
//["NumArray", "sumRange", "sumRange", "sumRange"]
//[[[-2, 0, 3, -5, 2, -1]], [0, 2], [2, 5], [0, 5]]
//输出：
//[null, 1, -1, -3]
//
//解释：
//NumArray numArray = new NumArray([-2, 0, 3, -5, 2, -1]);
//numArray.sumRange(0, 2); // return 1 ((-2) + 0 + 3)
//numArray.sumRange(2, 5); // return -1 (3 + (-5) + 2 + (-1))
//numArray.sumRange(0, 5); // return -3 ((-2) + 0 + 3 + (-5) + 2 + (-1))
//
//
//
//
// 提示：
//
//
// 1 <= nums.length <= 10⁴
// -10⁵ <= nums[i] <= 10⁵
// 0 <= i <= j < nums.length
// 最多调用 10⁴ 次 sumRange 方法
//
// Related Topics 设计 数组 前缀和 👍 474 👎 0

/**
 * 前缀和：一维，二维
 * @see _303_区域和检索_数组不可变
 * @see _304_二维区域和检索_矩阵不可变
 */
public class _303_区域和检索_数组不可变 {
    class NumArray {
        //前缀和数组
        private int preSum[];
        //输入一个数组，构造前缀和
        public NumArray(int[] nums) {
            if ( nums == null) {
                return;
            }
            preSum = new int[nums.length+1];
            int sum = 0;
            for (int i = 1; i<preSum.length; i++) {
                preSum[i] = preSum[i-1] + nums[i-1];
            }
        }
        //求区间 [left, right]之间的和,最坏的时间复杂度为 O（1）
        public int sumRange(int left, int right) {
            return preSum[right+1] - preSum[left];

        }
    }
}
