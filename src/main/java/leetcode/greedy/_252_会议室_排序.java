package leetcode.greedy;
//给定一个会议时间安排的数组，每个会议时间都会包括开始和结束的时间 [[s1,e1],[s2,e2],...] (si < ei)，请你判断一个人是否能够参加这里面的全部会议。
//
//示例 1:
//输入: [[0,30],[5,10],[15,20]]
//输出: false
//
//示例 2:
//输入: [[7,10],[2,4]]
//输出: true

import java.util.Arrays;

/**
 * @author mayanwei
 * @date 2022-08-01.
 */
public class _252_会议室_排序{
    class Solution {
        public boolean canAttendMeetings(int [][] intervals) {
            // 思路：按开始时间排序，依次检查相邻前一个的结束和后一个的开始时间是否重叠
            Arrays.sort(intervals, (int []a, int[]b)->(a[0]-b[0]));
            for (int i = 1; i < intervals.length; i++) {
                if (intervals[i-1][1] > intervals[i][0]) {
                    return false;
                }
            }
            return true;
        }
    }

}
