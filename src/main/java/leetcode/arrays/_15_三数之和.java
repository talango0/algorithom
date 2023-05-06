package leetcode.arrays;
//给你一个整数数组 nums ，判断是否存在三元组 [nums[i], nums[j], nums[k]]
// 满足 i != j、i != k 且 j != k ，同时还满足 nums[i] + nums[j] + nums[k] == 0 。
// 请你返回所有和为 0 且不重复的三元组。
// 注意：答案中不可以包含重复的三元组。

// 示例 1：
//输入：nums = [-1,0,1,2,-1,-4]
//输出：[[-1,-1,2],[-1,0,1]]
//解释：
//nums[0] + nums[1] + nums[2] = (-1) + 0 + 1 = 0 。
//nums[1] + nums[2] + nums[4] = 0 + 1 + (-1) = 0 。
//nums[0] + nums[3] + nums[4] = (-1) + 2 + (-1) = 0 。
//不同的三元组是 [-1,0,1] 和 [-1,-1,2] 。
//注意，输出的顺序和三元组的顺序并不重要。
//
//
// 示例 2：
//输入：nums = [0,1,1]
//输出：[]
//解释：唯一可能的三元组和不为 0 。
//
//
// 示例 3：
//输入：nums = [0,0,0]
//输出：[[0,0,0]]
//解释：唯一可能的三元组和为 0 。
//
//
//
//
// 提示：
//
//
// 3 <= nums.length <= 3000
// -10⁵ <= nums[i] <= 10⁵
//
//
// Related Topics 数组 双指针 排序 👍 5328 👎 0

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author mayanwei
 * @date 2022-10-21.
 * @see _167_两数之和II_输入有序数组
 * @see _16_最接近的三数之和
 */
public class _15_三数之和{
    class Solution{
        public List<List<Integer>> threeSum(int[] nums) {
            Arrays.sort(nums);
            return nSumTarget(nums, 3, 0, 0);
        }

        /**
         * <pre>
         * n == 2 时是 twoSum 的双指针解法，
         * n > 2 时就是穷举第一个数字，然后递归调用计算 (n-1)Sum，组装答案。
         *
         * 需要注意的是，调用这个 nSum 函数之前一定要先给 nums 数组排序，
         * 因为 nSum 是一个递归函数，如果在 nSum 函数里调用排序函数，
         * 那么每次递归都会进行没有必要的排序，效率会非常低
         * </pre>
         * @param nums
         * @param n
         * @param start
         * @param target
         * @return
         */
        private List<List<Integer>> nSumTarget(int[] nums, int n, int start, int target) {
            int sz = nums.length;
            List<List<Integer>> res = new ArrayList<>();
            if (n < 2 || sz < n) {
                return res;
            }
            // 2Sum 是base case
            if (n == 2) {
                int lo = start, hi = sz - 1;
                while (lo < hi) {
                    int sum = nums[lo] + nums[hi];
                    int left = nums[lo], right = nums[hi];
                    if (sum < target) {
                        while (lo < hi && nums[lo] == left) {
                            lo++;
                        }
                    }
                    else if (sum > target) {
                        while (lo < hi && nums[hi] == right) {
                            hi--;
                        }
                    }
                    else {
                        List<Integer> l = new ArrayList<Integer>();
                        l.add(left);
                        l.add(right);
                        res.add(l);
                        while (lo < hi && nums[lo] == left) {
                            lo++;
                        }
                        while (lo < hi && nums[hi] == right) {
                            hi--;
                        }
                    }
                }

            }
            // n > 2时，递归计算 (n-1)Sum 的结果
            else {
                for (int i = start; i < sz; i++) {
                    List<List<Integer>> sub = nSumTarget(nums, n - 1, i + 1, target - nums[i]);
                    for (List<Integer> arr : sub) {
                        // (n-1)Sum 加上 nums[i] 就是 nSum
                        arr.add(nums[i]);
                        res.add(arr);
                    }
                    while (i < sz - 1 && nums[i] == nums[i + 1]) {
                        i++;
                    }
                }
            }
            return res;
        }
    }
}
