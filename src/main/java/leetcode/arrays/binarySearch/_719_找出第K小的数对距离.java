package leetcode.arrays.binarySearch;
//数对 (a,b) 由整数 a 和 b 组成，其数对距离定义为 a 和 b 的绝对差值。
//
// 给你一个整数数组 nums 和一个整数 k ，数对由 nums[i] 和 nums[j] 组成且满足 0 <= i < j < nums.length 。
//返回 所有数对距离中 第 k 小的数对距离。
//
//
//
// 示例 1：
//输入：nums = [1,3,1], k = 1
//输出：0
//解释：数对和对应的距离如下：
//(1,3) -> 2
//(1,1) -> 0
//(3,1) -> 2
//距离第 1 小的数对是 (1,1) ，距离为 0 。
//
//
// 示例 2：
//输入：nums = [1,1,1], k = 2
//输出：0
//
//
// 示例 3：
//输入：nums = [1,6,1], k = 3
//输出：5
//
// 提示：
// n == nums.length
// 2 <= n <= 10⁴
// 0 <= nums[i] <= 10⁶
// 1 <= k <= n * (n - 1) / 2
//
//
// Related Topics 数组 双指针 二分查找 排序 👍 391 👎 0


import java.util.Arrays;
import java.util.TreeSet;

/**
 * @author mayanwei
 * @date 2022-10-11.
 */
public class _719_找出第K小的数对距离{
    class Solution{
        /**
         * 先将数组 nums 从小到大进行排序。因为第k小的数对距离必然在区间 [0, max(nums) - min(nums)]内，
         * left = 0, right = max(nums) - max(nums), 在 [left, right] 上进行二分。
         * 对于当前搜索的距离mid,计算所有距离小于等于 mid 的数对数目 cnt，如果cnt >= k, 那么 right = mid - 1,
         * 否则，left = mid + 1, 当 left > right 时，停止搜索，那么第k小的数对距离为 left。
         * 排序 + 二分查找
         */
        public int smallestDistancePair(int[] nums, int k) {
            Arrays.sort(nums);
            int n = nums.length;
            int left = 0, right = nums[n - 1] - nums[0];
            while (left <= right) {
                int mid = left + ((right - left) >> 1);
                if (f(nums, mid) >= k) {
                    right = mid - 1;
                }
                else {
                    left = mid + 1;
                }
            }
            return left;
        }

        private int f(int[] nums, int target) {
            int cnt = 0;
            for (int j = 0; j < nums.length; j++) {
                int i = binarySearch(nums, j, nums[j] - target);
                cnt += j - i;
            }
            return cnt;
        }

        //左边界
        private int binarySearch(int[] nums, int end, int target) {
            int left = 0, right = end;
            while (left < right) {
                int mid = left + ((right - left) >> 1);
                if (nums[mid] < target) {
                    left = mid + 1;
                }
                else {
                    right = mid;
                }
            }
            return left;
        }
    }

    class Solution2{
        /**
         * 先将数组 nums 从小到大进行排序。因为第k小的数对距离必然在区间
         * [0, max(nums) - min(nums)]内，
         * left = 0, right = max(nums) - max(nums), 在 [left, right] 上进行二分。
         * 对于当前搜索的距离mid,计算所有距离小于等于 mid 的数对数目 cnt，
         * 如果cnt >= k, 那么 right = mid - 1,
         * 否则，left = mid + 1, 当 left > right 时，停止搜索，那么第k小的数对距离为 left。
         * 排序 + 二分查找 + 双指针
         */
        public int smallestDistancePair(int[] nums, int k) {
            Arrays.sort(nums);
            int n = nums.length;
            int left = 0, right = nums[n - 1] - nums[0];
            while (left <= right) {
                int mid = left + ((right - left) >> 1);
                if (f(nums, mid) >= k) {
                    right = mid - 1;
                }
                else {
                    left = mid + 1;
                }
            }
            return left;
        }

        private int f(int[] nums, int target) {
            int cnt = 0;
            for (int i = 0, j = 0; j < nums.length; j++) {
                while (nums[j] - nums[i] > target) {
                    i++;
                }
                cnt += j - i;
            }
            return cnt;
        }
    }
}
