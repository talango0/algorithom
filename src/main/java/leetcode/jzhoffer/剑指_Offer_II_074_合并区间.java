package leetcode.jzhoffer;

import leetcode.arrays.range._56_合并区间;

import java.util.Arrays;
import java.util.LinkedList;
//以数组 intervals 表示若干个区间的集合，其中单个区间为 intervals[i] = [starti, endi] 。请你合并所有重叠的区间，并返
//回一个不重叠的区间数组，该数组需恰好覆盖输入中的所有区间。
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
//
//
//
// 注意：本题与主站 56 题相同： https://leetcode-cn.com/problems/merge-intervals/
//
// Related Topics 数组 排序 👍 37 👎 0


/**
 * @author mayanwei
 * @date 2022-11-05.
 * @see _56_合并区间
 */
public class 剑指_Offer_II_074_合并区间{
    class Solution {
        public int[][] merge(int[][] intervals) {
            LinkedList<int[]> res = new LinkedList<>();
            Arrays.sort(intervals, (a, b)->{
                return a[0] - b[0];
            });
            res.add(intervals[0]);
            for (int i = 1; i< intervals.length; i++) {
                int[] cur = intervals[i];
                // res中最后一个元素的引用
                int [] last = res.getLast();
                if (cur[0] <= last[1]) {
                    last[1] = Math.max(last[1], cur[1]);
                }
                else {
                    // 处理一下待合并区间
                    res.add(cur);
                }
            }
            return res.toArray(new int[0][0]);
        }
    }
}
