package leetcode.arrays.range;
//给定两个由一些 闭区间 组成的列表，firstList 和 secondList ，其中 firstList[i] = [starti, endi] 而
//secondList[j] = [startj, endj] 。每个区间列表都是成对 不相交 的，并且 已经排序 。
//
// 返回这 两个区间列表的交集 。
//
// 形式上，闭区间 [a, b]（其中 a <= b）表示实数 x 的集合，而 a <= x <= b 。
//
// 两个闭区间的 交集 是一组实数，要么为空集，要么为闭区间。例如，[1, 3] 和 [2, 4] 的交集为 [2, 3] 。
//
//
//
// 示例 1：
//
//
//输入：firstList = [[0,2],[5,10],[13,23],[24,25]], secondList = [[1,5],[8,12],[15,
//24],[25,26]]
//输出：[[1,2],[5,5],[8,10],[15,23],[24,24],[25,25]]
//
//
// 示例 2：
//
//
//输入：firstList = [[1,3],[5,9]], secondList = []
//输出：[]
//
//
// 示例 3：
//
//
//输入：firstList = [], secondList = [[4,8],[10,12]]
//输出：[]
//
//
// 示例 4：
//
//
//输入：firstList = [[1,7]], secondList = [[3,10]]
//输出：[[3,7]]
//
//
//
//
// 提示：
//
//
// 0 <= firstList.length, secondList.length <= 1000
// firstList.length + secondList.length >= 1
// 0 <= starti < endi <= 10⁹
// endi < starti+1
// 0 <= startj < endj <= 10⁹
// endj < startj+1
//
//
// Related Topics 数组 双指针 👍 315 👎 0

import java.util.ArrayList;
import java.util.List;

/**
 * @author mayanwei
 * @date 2022-08-06.
 */
public class _986_区间列表的交集{
    class Solution{
        public int[][] intervalIntersection(int[][] firstList, int[][] secondList) {
            List<int[]> res = new ArrayList<>();
            int i = 0, j = 0;
            while (i < firstList.length && j < secondList.length) {
                int a1 = firstList[i][0], a2 = firstList[i][1];
                int b1 = secondList[j][0], b2 = secondList[j][1];
                // 两个区间存在交集
                if (b2 >= a1 && a2 >= b1) {
                    res.add(new int[]{
                            Math.max(a1, b1),
                            Math.min(a2, b2)
                    });
                }
                if (b2 < a2) {
                    j++;
                }
                else {
                    i++;
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
