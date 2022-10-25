package leetcode.arrays;

//713. 乘积小于 K 的子数组
//给你一个整数数组 nums 和一个整数 k ，请你返回子数组内所有元素的乘积严格小于 k 的连续子数组的数目。
//
//
//示例 1：
//
//输入：nums = [10,5,2,6], k = 100
//输出：8
//解释：8 个乘积小于 100 的子数组分别为：[10]、[5]、[2],、[6]、[10,5]、[5,2]、[2,6]、[5,2,6]。
//需要注意的是 [10,5,2] 并不是乘积小于 100 的子数组。
//示例 2：
//
//输入：nums = [1,2,3], k = 0
//输出：0
//
//
//提示:
//
//1 <= nums.length <= 3 * 104
//1 <= nums[i] <= 1000
//0 <= k <= 106

public class _713_乘积小于K的子数组 {
    class Solution {
        /**
         * 二分查找
         * nums[i...j]的元素乘积小于k
         * k = 0, 由于元素均为正数，所有子数组乘积均大于0，因此乘积小于0的子数组的数目为0
         * k > 0, 计算子数组乘积是会出现溢出情况，为避免溢出，可以采用 子数组[i...j]的对数和小于logk
         * 预处理数组的元素前缀和 logPrifix ,即 logPrifix[j +1] = sum(lognum[l]), l in [0, i],因为nums是正整数，所以logPrefix是
         * 非递减的。
         * 枚举子数组的右端点j，在logPrifix的区间 [0,j] 内二分查找满足 logPrefix[j+1] - logPrefix[l] < logk,
         * 即logPrefix[l] > logPrefix[j+1] - logk 的最小下标 l，那么以 j 为右端点且元素乘积小于 k 的子数组数目为 j + 1 -l,返回所有数目之和。
         */
        public int numSubarrayProductLessThanK(int[] nums, int k) {
            if (k == 0) {
                return 0;
            }
            int n = nums.length;
            double[] logPrefix = new double[n + 1];
            for (int i = 0; i < n; i++) {
                logPrefix[i + 1] = logPrefix[i] + Math.log(nums[i]);
            }
            double logk = Math.log(k);
            int ret = 0;
            for (int j = 0; j < n; j++) {
                int l = 0;
                int r = j + 1;
                int idx = j + 1;
                double val = logPrefix[j + 1] - logk + 1e-10;
                while (l <= r) {
                    int mid = (l + r) >> 1;
                    if (logPrefix[mid] > val) {
                        idx = mid;
                        r = mid - 1;
                    } else {
                        l = mid + 1;
                    }
                }
                ret += j + 1 - idx;
            }
            return ret;
        }

    }

    class Solution2 {
        /**
         * 滑动窗口
         * 固定子数组 [i,j] 的右端点 j 时，显然左端点i越大，子数组元素乘积越小。
         * 对于子数组 [i,j], 当左端点 i >= l1 时，所有子数组的元素乘积都小于 k，当左端点 i < l1 时，所有子数组的元素乘积都大于等于k。
         * 那么对于右端点为 i + 1 的所有子数组，它的左端点就不需要从 0 开始枚举，因为对于所有 i < l1 的子数组，它们的元素乘积都大于等于k。
         * 所以我们只要 i = l1 处开始枚举，知道子数组 i = l2时子数组 [l2, j+1] 的元素乘积小于 k， 那么左端点 i >= l2 所有子数组的元素乘积都小于k。
         */
        public int numSubarrayProductLessThanK(int[] nums, int k) {
            int n = nums.length, ret = 0;
            int prod = 1, i = 0;
            for (int j = 0; j < n; j++) {
                prod *= nums[j];
                while (i <= j && prod >= k) {
                    prod /= nums[i];
                    i++;
                }
                ret += j - i + 1;
            }
            return ret;
        }

    }
}
