package leetcode.arrays;
//给定一个包含 n + 1 个整数的数组 nums ，其数字都在 [1, n] 范围内（包括 1 和 n），
// 可知至少存在一个重复的整数。
//假设 nums 只有 一个重复的整数 ，返回 这个重复的数 。
//你设计的解决方案必须 不修改 数组 nums 且只用常量级 O(1) 的额外空间。
//
//
//
//示例 1：
//输入：nums = [1,3,4,2,2]
//输出：2
//
//示例 2：
//输入：nums = [3,1,3,4,2]
//输出：3
//
//
//提示：
//
//1 <= n <= 105
//nums.length == n + 1
//1 <= nums[i] <= n
//nums 中 只有一个整数 出现 两次或多次 ，其余整数均只出现 一次
//
//
//进阶：
//
//如何证明 nums 中至少存在一个重复的数字?
//你可以设计一个线性级时间复杂度 O(n) 的解决方案吗？
//Related Topics
//
//👍 1887, 👎 0

import leetcode.list._141_环形链表;

public class _287_寻找重复数{
    /**
     * <pre>
     * 二分查找
     * cnt[i] 表示nums数组中小于等于i的数有多少个1，
     * 假设我们重复的数是target，
     * 那么 [1, target-1]里的所有数满足 cnt[i]<=i,
     *     [target, n] 里的所有数满足cnt[i] > i,具有单调性。
     * ┌─────────────────┐
     * │ nums=[1,3,4,2,2]│
     * │                 │
     * │       1 2 3 4   │
     * │cnt[i]:1 3 4 5   │
     * └─────────────────┘
     * 重复的数是2 ，可以看到[1,1]中数满足 cnt[i] <= i, [2,4]中的数满足 cnt[i] > i。
     * 如果我们知道 cnt[] 数组随数字 i 逐渐增大具有单调性（即 target 前 cnt[i] <= i，
     * target 后 cnt[i]>i），那么我们就可以利用二分查找来找出重复的数。
     * </pre>
     */
    class Solution{
        public int findDuplicate(int[] nums) {
            int n = nums.length;
            int l = 1, r = n - 1, ans = -1;
            while (l <= r) {
                int mid = (l + r) >> 1;
                int cnt = 0;
                for (int i = 0; i < n; ++i) {
                    if (nums[i] <= mid) {
                        cnt++;
                    }
                }
                if (cnt <= mid) {
                    l = mid + 1;
                }
                else {
                    r = mid - 1;
                    ans = mid;
                }
            }
            return ans;
        }
    }

    /**
     *
     * <a href="https://zh.wikipedia.org/zh-hans/Floyd%E5%88%A4%E5%9C%88%E7%AE%97%E6%B3%95">Floyd 判圈算法（龟兔赛跑算法）</a>
     * 如果有限状态机、迭代函数或者链表存在环，那么一定存在一个起点可以到达某个环的某处(这个起点也可以在某个环上)
     * <p>
     * 快慢指针
     *
     * @see _141_环形链表
     */
    class Solution0{
        public int findDuplicate(int[] nums) {
            int slow = 0, fast = 0;
            do {
                slow = nums[slow];
                fast = nums[nums[fast]];
            } while (slow != fast);
            slow = 0;
            while (slow != fast) {
                slow = nums[slow];
                fast = nums[fast];
            }
            return slow;
        }
    }
}
