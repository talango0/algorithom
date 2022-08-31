package leetcode.slidewindow;
//给你一个整数数组 nums 和一个整数 k ，找出 nums 中和至少为 k 的 最短非空子数组 ，并返回该子数组的长度。如果不存在这样的 子数组 ，返回 -1 。
//
//子数组 是数组中 连续 的一部分。
//
//示例 1：
//
//输入：nums = [1], k = 1
//输出：1
//示例 2：
//
//输入：nums = [1,2], k = 4
//输出：-1
//示例 3：
//
//输入：nums = [2,-1,2], k = 3
//输出：3
//提示：
//
//1 <= nums.length <= 105
//-105 <= nums[i] <= 105
//1 <= k <= 109
//Related Topics
//
//👍 425, 👎 0

import java.util.Deque;
import java.util.LinkedList;

/**
 * 字节
 * @author mayanwei
 * @date 2022-08-26.
 */
public class _862_和至少为K的最短子数组{
    class Solution{
        // 维护一个关于前缀和数组 P的单调队列，它是一个双端队列，其中存放了 下标x = [x0,x1,x2...] 满足 p[x0], p[x1], p[x2]单调递增
        // 当我们遇到一个新的下标 y 时，我们会在队尾移出若干元素，直到 P[x0], P[x1], ... , P[y] 单调递增
        // 同时我们也会在队首移除若干元素，如果 P[y] >= P[x0] + K,则将对首元素移出，直到该不等式不满足
        public int shortestSubarray(int[] nums, int k) {
            int n = nums.length;
            // 前缀和数组
            long[] p = new long[n + 1];
            for (int i = 0; i < n; i++) {
                p[i + 1] = p[i] + (long) nums[i];
            }

            // want smallest y-x with p[y]-p[x] >= k
            int ans = n + 1; // n+1 is impossible
            // 单调递增双端队列, opt(y) Candidates,as indices of p
            Deque<Integer> monoq = new LinkedList<Integer>();
            for (int y = 0; y < p.length; y++) {
                // want opt(y) = largest x with p[x] <= p[y] - k
                while (!monoq.isEmpty() && p[y] <= p[monoq.getLast()]) {
                    monoq.removeLast();
                }
                while (!monoq.isEmpty() && p[y] >= p[monoq.getFirst()] + k) {
                    ans = Math.min(ans, y - monoq.removeFirst());
                }
                monoq.addLast(y);
            }

            return ans < n + 1 ? ans :-1;
        }
    }
}
