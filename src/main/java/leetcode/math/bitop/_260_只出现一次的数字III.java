package leetcode.math.bitop;
//给你一个整数数组 nums，其中恰好有两个元素只出现一次，其余所有元素均出现两次。 找出只出现一次的那两个元素。你可以按 任意顺序 返回答案。
//
// 你必须设计并实现线性时间复杂度的算法且仅使用常量额外空间来解决此问题。
//
//
//
// 示例 1：
//
//
//输入：nums = [1,2,1,3,2,5]
//输出：[3,5]
//解释：[5, 3] 也是有效的答案。
//
//
// 示例 2：
//
//
//输入：nums = [-1,0]
//输出：[-1,0]
//
//
// 示例 3：
//
//
//输入：nums = [0,1]
//输出：[1,0]
//
//
//
//
// 提示：
//
//
// 2 <= nums.length <= 3 * 10⁴
// -2³¹ <= nums[i] <= 2³¹ - 1
// 除两个只出现一次的整数外，nums 中的其他数字都出现两次
//
//
// Related Topics 位运算 数组 👍 667 👎 0

/**
 * @author mayanwei
 * @date 2022-10-20.
 */
public class _260_只出现一次的数字III{
    /**
     * 我们试想一下，如果我们先将元素分成两组，然后每组包含一个目标值，那么异或之后，
     * 每组得到一个目标值，那么我们不就将两个目标值求出了吗？
     * <p>
     * 例： **a,b,a,b,c,d,e,f,e,f ** 分组后
     * <p>
     * A组：a, a , b, b, c 异或得到 c
     * <p>
     * B组：e, e, f, f, d 异或得到 d
     */
    class Solution{
        public int[] singleNumber(int[] nums) {
            int temp = 0;
            // 求出亦或值
            for (int x : nums) {
                temp ^= x;
            }
            // 保留最右边的一个1
            int group = temp & (-temp);
            int[] arr = new int[2];
            for (int y : nums) {
                // 分组位位0的组，组内异或
                if ((group & y) == 0) {
                    arr[0] ^= y;
                }
                // 分组位为 1 的组，组内异或
                else {
                    arr[1] ^= y;
                }
            }
            return arr;
        }
    }
}
