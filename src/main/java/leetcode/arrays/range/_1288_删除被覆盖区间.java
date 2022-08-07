package leetcode.arrays.range;
//给你一个区间列表，请你删除列表中被其他区间所覆盖的区间。
//
// 只有当 c <= a 且 b <= d 时，我们才认为区间 [a,b) 被区间 [c,d) 覆盖。
//
// 在完成所有删除操作后，请你返回列表中剩余区间的数目。
//
//
//
// 示例：
//
//
//输入：intervals = [[1,4],[3,6],[2,8]]
//输出：2
//解释：区间 [3,6] 被区间 [2,8] 覆盖，所以它被删除了。
//
//
//
//
// 提示：
//
//
// 1 <= intervals.length <= 1000
// 0 <= intervals[i][0] < intervals[i][1] <= 10^5
// 对于所有的 i != j：intervals[i] != intervals[j]
//
//
// Related Topics 数组 排序 👍 82 👎 0

import java.util.Arrays;

/**
 * @author mayanwei
 * @date 2022-08-06.
 */
public class _1288_删除被覆盖区间{
    class Solution{
        public int removeCoveredIntervals(int[][] intervals) {
            // 按起点升序排序，起点相同时将序排序
            Arrays.sort(intervals, (a, b) -> {
                if (a[0] == b[0]) {
                    return b[1] - a[1];
                }
                return a[0] - b[0];
            });

            // 记录合并区间的起点和终点
            int left = intervals[0][0];
            int right = intervals[0][1];

            int res = 0;
            for (int i = 1; i < intervals.length; i++) {
                int[] intv = intervals[i];
                // 情况1，找到覆盖区间
                if (left <= intv[0] && right >= intv[1]) {
                    res++;
                }
                // 情况2 找到相交区间，合并
                if (right >= intv[0] && right <= intv[1]) {
                    right = intv[1];
                }
                // 情况3 完全不相交，更新起点和终点
                if (right < intv[0]) {
                    left = intv[0];
                    right = intv[1];
                }
            }
            return intervals.length - res;
        }
    }
}
