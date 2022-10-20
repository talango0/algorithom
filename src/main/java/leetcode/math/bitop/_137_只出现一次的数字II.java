package leetcode.math.bitop;
//给你一个整数数组 nums ，除某个元素仅出现 一次 外，其余每个元素都恰出现 三次 。请你找出并返回那个只出现了一次的元素。
//
// 你必须设计并实现线性时间复杂度的算法且不使用额外空间来解决此问题。
//
//
//
// 示例 1：
//
//
//输入：nums = [2,2,3,2]
//输出：3
//
//
// 示例 2：
//
//
//输入：nums = [0,1,0,1,0,1,99]
//输出：99
//
//
//
//
// 提示：
//
//
// 1 <= nums.length <= 3 * 10⁴
// -2³¹ <= nums[i] <= 2³¹ - 1
// nums 中，除某个元素仅出现 一次 外，其余每个元素都恰出现 三次
//
//
// Related Topics 位运算 数组 👍 937 👎 0

import leetcode.dp._233_数字1的个数;

/**
 * @author mayanwei
 * @date 2022-10-20.
 * @see _260_只出现一次的数字III
 * @see _233_数字1的个数
 */
public class _137_只出现一次的数字II{
    class Solution{
        /**
         * <pre>
         * ┌───────────────┐
         * │3:0011         │
         * │3:0011         │
         * │6:0110         │
         * │3:0011         │
         * │               │
         * │  0143  0110=6 │
         * └───────────────┘
         * </pre>
         *
         * @param nums
         * @return
         */
        public int singleNumber(int[] nums) {
            // 最终的结果
            int res = 0;
            // int 类型有32位，统计每一位1的个数
            for (int i = 0; i < 32; i++) {
                // 统计第 i 位中1的个数
                int oneCount = 0;
                for (int j = 0; j < nums.length; j++) {
                    oneCount += (nums[j] >>> i) & 1;
                }
                // 如果1的个数不是3的倍数，说明那个只出现一次的数字
                // 的二进制位中在这一位是 1
                if (oneCount % 3 == 1) {
                    res |= 1 << i;
                }
            }
            return res;
        }
    }


}
