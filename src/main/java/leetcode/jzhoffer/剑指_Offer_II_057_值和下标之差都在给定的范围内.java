package leetcode.jzhoffer;
//给你一个整数数组 nums 和两个整数 k 和 t 。请你判断是否存在 两个不同下标 i 和 j，使得 abs(nums[i] - nums[j]) <=
//t ，同时又满足 abs(i - j) <= k 。
//
// 如果存在则返回 true，不存在返回 false。
//
//
//
// 示例 1：
//
//
//输入：nums = [1,2,3,1], k = 3, t = 0
//输出：true
//
// 示例 2：
//
//
//输入：nums = [1,0,1,1], k = 1, t = 2
//输出：true
//
// 示例 3：
//
//
//输入：nums = [1,5,9,1,5,9], k = 2, t = 3
//输出：false
//
//
//
// 提示：
//
//
// 0 <= nums.length <= 2 * 10⁴
// -2³¹ <= nums[i] <= 2³¹ - 1
// 0 <= k <= 10⁴
// 0 <= t <= 2³¹ - 1
//
//
//
//
//
// 注意：本题与主站 220 题相同： https://leetcode-cn.com/problems/contains-duplicate-iii/
//
// Related Topics 数组 桶排序 有序集合 排序 滑动窗口 👍 62 👎 0

import leetcode.slidewindow._220_存在重复元素III;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeSet;

/**
 * @author mayanwei
 * @date 2022-11-01.
 * @see _220_存在重复元素III
 */
public class 剑指_Offer_II_057_值和下标之差都在给定的范围内{
    class Solution{
        /**
         * 思路：
         * 使用滑动窗口的思路，维护一个大小为 k 的滑动窗口，每次遍历道元素 x 时，滑动窗口中包含元素 x 前面的最多 k
         * 个元素，我们检查窗口中是否存在元素落在区间 [x-t, x+t] 中即可。
         * <p>
         * 如果使用队列维护滑动窗口内的元素，由于元素时无序的，我们只能对每个元素都遍历一次dailies来检查是否有元素符合条件。
         * 如果数组的长度为 n， 则使用队列的时间复杂度为 O(nk)，会超出时间限制。
         * <p>
         * 希望能够找到一个数据结构维护滑动窗口内的元素，该数据结构满足以下操作：
         * 1.支持添加和删除指定元素，否则我们无法维护滑动窗口
         * 2. 内部元素有序，支持二分查找操作，这样我们可以快速判断滑动窗口中是否存在元素满足条件，具体而言，对于元素 x， 当
         * 我们希望判断滑动窗口中是否存在某个数 y 落在区间 [x-t, x+t] 中，只需要判断滑动窗口中所有大于等于 x-t 的最小元素
         * 是否小于等于 x + t 即可。
         * <p>
         * 时间复杂度： O(nlog(min(n,k)))，每个元素之多被插入和删除有序集合一次，每次操作的时间均为 O(log(min(n,k)))
         * 空间复杂度： O(min(n,k)) 其中 n 是给定数组的长度
         */
        public boolean containsNearbyAlmostDuplicate(int[] nums, int k, int t) {
            int n = nums.length;
            // 为防止整形int移出，用long
            TreeSet<Long> set = new TreeSet<Long>();
            for (int i = 0; i < n; i++) {
                Long ceiling = set.ceiling((long) nums[i] - (long) t);
                if (ceiling != null && ceiling <= (long) nums[i] + (long) t) {
                    return true;
                }
                set.add((long) nums[i]);
                if (i >= k) {
                    set.remove((long) nums[i - k]);
                }
            }
            return false;
        }
    }

    class Solution2{
        /**
         * 桶排序
         */
        public boolean containsNearbyAlmostDuplicate(int[] nums, int k, int t) {
            int n = nums.length;
            Map<Long, Long> map = new HashMap<Long, Long>();
            long w = (long) t + 1;
            for (int i = 0; i < n; i++) {
                long id = getID(nums[i], w);
                if (map.containsKey(id)) {
                    return true;
                }
                if (map.containsKey(id - 1) && Math.abs(nums[i] - map.get(id - 1)) < w) {
                    return true;
                }
                if (map.containsKey(id + 1) && Math.abs(nums[i] - map.get(id + 1)) < w) {
                    return true;
                }
                map.put(id, (long) nums[i]);
                if (i >= k) {
                    map.remove(getID(nums[i - k], w));
                }
            }
            return false;
        }

        public long getID(long x, long w) {
            if (x >= 0) {
                return x / w;
            }
            return (x + 1) / w - 1;
        }
    }
}
