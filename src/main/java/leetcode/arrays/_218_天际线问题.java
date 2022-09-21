package leetcode.arrays;

import java.util.*;
//城市的 天际线 是从远处观看该城市中所有建筑物形成的轮廓的外部轮廓。给你所有建筑物的位置和高度，请返回 由这些建筑物形成的 天际线 。
//
//每个建筑物的几何信息由数组 buildings 表示，其中三元组 buildings[i] = [lefti, righti, heighti] 表示：
//
//lefti 是第 i 座建筑物左边缘的 x 坐标。
//righti 是第 i 座建筑物右边缘的 x 坐标。
//heighti 是第 i 座建筑物的高度。
//你可以假设所有的建筑都是完美的长方形，在高度为 0 的绝对平坦的表面上。
//
//天际线 应该表示为由 “关键点” 组成的列表，格式 [[x1,y1],[x2,y2],...] ，并按 x 坐标 进行 排序 。关键点是水平线段的左端点。列表中最后一个点是最右侧建筑物的终点，y 坐标始终为 0 ，仅用于标记天际线的终点。此外，任何两个相邻建筑物之间的地面都应被视为天际线轮廓的一部分。
//
//注意：输出天际线中不得有连续的相同高度的水平线。例如 [...[2 3], [4 5], [7 5], [11 5], [12 7]...] 是不正确的答案；三条高度为 5 的线应该在最终输出中合并为一个：[...[2 3], [4 5], [12 7], ...]
//
//
//
//示例 1：
//
//
//输入：buildings = [[2,9,10],[3,7,15],[5,12,12],[15,20,10],[19,24,8]]
//输出：[[2,10],[3,15],[7,12],[12,0],[15,10],[20,8],[24,0]]
//解释：
//图 A 显示输入的所有建筑物的位置和高度，
//图 B 显示由这些建筑物形成的天际线。图 B 中的红点表示输出列表中的关键点。
//示例 2：
//
//输入：buildings = [[0,2,3],[2,5,3]]
//输出：[[0,3],[5,0]]
//
//
//提示：
//
//1 <= buildings.length <= 104
//0 <= lefti < righti <= 231 - 1
//1 <= heighti <= 231 - 1
//buildings 按 lefti 非递减排序
//Related Topics
//
//👍 717, 👎 0

public class _218_天际线问题 {
    /**
     * 关键点的横坐标总是落在建筑的左右边缘上。这样可以只考虑每一座建筑的边缘作为横坐标，这样其对应的纵坐标为 【包含该横坐标】 的所有建筑的最大高度。
     * 当关键点为某建筑的右边缘时，该建筑的高度对关键点的纵坐标是没有贡献的。
     * <p>
     * 基于此，给出【包含该横坐标】的定义：建筑的左边缘小于等于该横坐标，右边缘大于该横坐标。（也就是我们不考虑建筑的右边缘）。即包含该横坐标 x 的
     * 建筑 i ，有 left <= i < right
     * <p>
     * 在部分情况下，【包含该横坐标】的建筑并不存在，例如当图中只有一座建筑师，该建筑的左右边缘均对应一个关键点，这唯一的建筑对其纵坐标没有贡献，因此
     * 【该横坐标】对应的纵坐标大小为 0、
     * <p>
     * 暴力的算法: O(n)地枚举每一个边缘作为关键点的横坐标，过程中我们 O(n) 地检查每一座建筑是否【包含该横坐标】，找到最大高度，及该关键点的纵坐标，
     * 该算法的时间复杂度为 O(n^2)。然后进行优化。
     * <p>
     * 用优先队列来优化寻找最大高度的时间，在从左到右枚举横坐标的过程中，实时地更新该优先队列即可。这样无论如何，优先队列的队首元素即为最大高度。
     * 为了维护优先级队列，我们需要使用【延迟删除】的技巧，即无需没刺横坐标改变就立刻将该优先队列中所有不符合条件的元素删除，而只需要保证优先队列的
     * 对首元素【包含该横坐标】即可。
     */
    class Solution {
        public List<List<Integer>> getSkyline(int[][] buildings) {
            PriorityQueue<int[]> pq = new PriorityQueue<int[]>((a, b) -> b[1] - a[1]);
            List<Integer> boundries = new ArrayList<>();
            for (int[] building : buildings) {
                boundries.add(building[0]);
                boundries.add(building[1]);
            }
            Collections.sort(boundries);

            List<List<Integer>> ret = new ArrayList<List<Integer>>();
            int n = buildings.length, idx = 0;
            for (int boundary : boundries) {
                while (idx < n && buildings[idx][0] <= boundary) {
                    pq.offer(new int[]{buildings[idx][1], buildings[idx][2]});
                    idx++;
                }
                while (!pq.isEmpty() && pq.peek()[0] <= boundary) {
                    pq.poll();
                }

                int maxn = pq.isEmpty() ? 0 : pq.peek()[1];
                if (ret.size() == 0 || maxn != ret.get(ret.size() - 1).get(1)) {
                    ret.add(Arrays.asList(boundary, maxn));
                }
            }
            return ret;
        }
    }
}
