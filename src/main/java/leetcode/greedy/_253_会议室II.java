package leetcode.greedy;
//给定一个会议时间安排的数组，每个会议时间都会包括开始和结束的时间 [[s1,e1],[s2,e2],…] (si < ei)，
// 为避免会议冲突，同时要考虑充分利用会议室资源，请你计算至少需要多少间会议室，才能满足这些会议安排。
//
//示例 1:
//输入: [[0, 30],[5, 10],[15, 20]]
//输出: 2
//
//示例 2:
//输入: [[7,10],[2,4]]
//输出: 1

import java.util.Arrays;
import java.util.PriorityQueue;

/**
 * @author mayanwei
 * @date 2022-08-01.
 */
public class _253_会议室II{
    //思路：贪心+优先队列
    class Solution{
        public int minMeetingRooms(int[][] intervals) {
            if (intervals == null && intervals.length == 0) {
                return 0;
            }
            Arrays.sort(intervals, (int[] a, int[] b) -> {
                if (a[0] == b[0]) {
                    // 开始时间一样，先结束的排在前面
                    return a[1] - b[1];
                }
                else {
                    // 开始早的在前面
                    return a[0] - b[0];
                }
            });
            // 最小优先队列
            PriorityQueue<Integer> pq = new PriorityQueue<>();
            pq.offer(intervals[0][1]);
            for (int i = 1; i < intervals.length; i++) {
                if (intervals[i][0] < pq.peek()) {
                    pq.poll();
                }
                pq.offer(intervals[i][1]);
            }
            return pq.size();
        }
    }

    class Solution2{
        public int minMeetingRooms(int[][] intervals) {
            int n = intervals.length;
            int[] start = new int[n];
            int[] end = new int[n];
            for (int i = 0; i < n; i++) {
                start[i] = intervals[i][0];
                end[i] = intervals[i][1];
            }
            Arrays.sort(start);
            Arrays.sort(end);

            // 扫描过程中的计数器
            int count = 0;
            // 双指针技巧
            int res = 0, i = 0, j = 0;
            while (i < n && j < n) {
                if (start[i] < end[j]) {
                    // 扫描到一个红点
                    count++;
                    i++;
                }
                else {
                    // 扫描到一个绿点
                    count--;
                    j++;
                }
                // 记录扫描过程中的最大值
                res = Math.max(res, count);
            }
            return res;
        }
    }
}
