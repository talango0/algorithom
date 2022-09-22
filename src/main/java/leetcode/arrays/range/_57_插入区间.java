package leetcode.arrays.range;
//给你一个 无重叠的 ，按照区间起始端点排序的区间列表。
//
// 在列表中插入一个新的区间，你需要确保列表中的区间仍然有序且不重叠（如果有必要的话，可以合并区间）。
//
//
//
// 示例 1：
//
//
//输入：intervals = [[1,3],[6,9]], newInterval = [2,5]
//输出：[[1,5],[6,9]]
//
//
// 示例 2：
//
//
//输入：intervals = [[1,2],[3,5],[6,7],[8,10],[12,16]], newInterval = [4,8]
//输出：[[1,2],[3,10],[12,16]]
//解释：这是因为新的区间 [4,8] 与 [3,5],[6,7],[8,10] 重叠。
//
// 示例 3：
//
//
//输入：intervals = [], newInterval = [5,7]
//输出：[[5,7]]
//
//
// 示例 4：
//
//
//输入：intervals = [[1,5]], newInterval = [2,3]
//输出：[[1,5]]
//
//
// 示例 5：
//
//
//输入：intervals = [[1,5]], newInterval = [2,7]
//输出：[[1,7]]
//
//
//
//
// 提示：
//
//
// 0 <= intervals.length <= 10⁴
// intervals[i].length == 2
// 0 <= intervals[i][0] <= intervals[i][1] <= 10⁵
// intervals 根据 intervals[i][0] 按 升序 排列
// newInterval.length == 2
// 0 <= newInterval[0] <= newInterval[1] <= 10⁵
//
//
// Related Topics 数组 👍 640 👎 0

import java.util.ArrayList;
import java.util.List;

/**
 * @author mayanwei
 * @date 2022-09-18.
 * @see _56_合并区间
 */
public class _57_插入区间{
    /**
     * s时间复杂度：O(n), 空间负载度 O(1)
     */
    class Solution{
        public int[][] insert(int[][] intervals, int[] newInterval) {
            int left = newInterval[0];
            int right = newInterval[1];
            boolean place = false;
            List<int[]> ansList = new ArrayList<>();
            for (int[] interval : intervals) {
                int l = interval[0];
                int r = interval[1];
                // 在插入区间的右侧且无交集
                if (l > right) {
                    if (!place) {
                        ansList.add(new int[]{left, right});
                        place = true;
                    }
                    ansList.add(interval);
                }
                // 在插入区间的左侧且无交集
                else if (r < left) {
                    ansList.add(interval);
                }
                // 与插入区间有交集，计算它们的并集
                else {
                    left = Math.min(left, l);
                    right = Math.max(right, r);
                }
            }
            if (!place) {
                ansList.add(new int[]{left, right});
            }
            int[][] ans = new int[ansList.size()][2];
            for (int i = 0; i < ansList.size(); i++) {
                ans[i] = ansList.get(i);
            }

            return ans;
        }
    }
}
