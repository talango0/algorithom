package leetcode.arrays.range;
//以数组 intervals 表示若干个区间的集合，其中单个区间为 intervals[i] = [starti, endi] 。请你合并所有重叠的区间，并返
//回 一个不重叠的区间数组，该数组需恰好覆盖输入中的所有区间 。
//
//
//
// 示例 1：
//
//
//输入：intervals = [[1,3],[2,6],[8,10],[15,18]]
//输出：[[1,6],[8,10],[15,18]]
//解释：区间 [1,3] 和 [2,6] 重叠, 将它们合并为 [1,6].
//
//
// 示例 2：
//
//
//输入：intervals = [[1,4],[4,5]]
//输出：[[1,5]]
//解释：区间 [1,4] 和 [4,5] 可被视为重叠区间。
//
//
//
// 提示：
//
//
// 1 <= intervals.length <= 10⁴
// intervals[i].length == 2
// 0 <= starti <= endi <= 10⁴
//
//
// Related Topics 数组 排序 👍 1591 👎 0

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author mayanwei
 * @date 2022-08-06.
 */
public class _56_合并区间{
    class Solution{
        public int[][] merge(int[][] intervals) {
            if (intervals == null || intervals.length == 0) {
                return new int[0][0];
            }
            Arrays.sort(intervals, (a, b) -> {
                return a[0] - b[0];
            });
            List<int[]> res = new ArrayList<int[]>(intervals.length);
            res.add(intervals[0]);
            for (int i = 1; i < intervals.length; i++) {
                int[] cur = intervals[i];
                int[] last = res.get(res.size() - 1);
                if (cur[0] <= last[1]) {
                    // 找到最大的end
                    last[1] = Math.max(last[1], cur[1]);
                }
                else {
                    res.add(cur);
                }
            }
            return transfer(res);
        }

        private int[][] transfer(List<int[]> l) {
            int[][] res = new int[l.size()][2];
            for (int i = 0; i < l.size(); i++) {
                res[i] = l.get(i);
            }
            return res;
        }
    }
}
