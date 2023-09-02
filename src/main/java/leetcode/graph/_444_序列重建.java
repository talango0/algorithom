package leetcode.graph;

import leetcode.jzhoffer.剑指_Offer_II_115_重建序列;

import java.util.*;

//Check whether the original sequence org can be uniquely reconstructed from the sequences in seqs. The org sequence is a permutation of the integers from 1 to n, with 1 ≤ n ≤ 104. Reconstruction means building a shortest common supersequence of the sequences in seqs (i.e., a shortest sequence so that all sequences in seqs are subsequences of it). Determine whether there is only one sequence that can be reconstructed from seqs and it is the org sequence.
//
//Example 1:
//
//Input:
//org: [1,2,3], seqs: [[1,2],[1,3]]
//
//Output:
//false
//
//Explanation:
//[1,2,3] is not the only one sequence that can be reconstructed, because [1,3,2] is also a valid sequence that can be reconstructed.
//Example 2:
//
//Input:
//org: [1,2,3], seqs: [[1,2]]
//
//Output:
//false
//
//Explanation:
//The reconstructed sequence can only be [1,2].
//Example 3:
//
//Input:
//org: [1,2,3], seqs: [[1,2],[1,3],[2,3]]
//
//Output:
//true
//
//Explanation:
//The sequences [1,2], [1,3], and [2,3] can uniquely reconstruct the original sequence [1,2,3].
//Example 4:
//
//Input:
//org: [4,1,5,2,6,3], seqs: [[5,2,6,3],[4,1,5,2]]
//
//Output:
//true

/**
 * @see 剑指_Offer_II_115_重建序列
 */
public class _444_序列重建 {

    class Solution {
        /**
         * 拓扑排序
         * <p>
         * 把 sequences 看成一个有向图，若按照拓扑排序，入度为 0 的点只有一个，则一定第一个最短
         * 超序列，否则不是。
         * 因此可以理解为能不能转换成一个唯一序列
         */
        public boolean sequenceReconstruction(int[] nums, int[][] sequences) {
            // 转化成边
            Map<Integer, Set<Integer>> edge = new HashMap<>();
            // 记录入度
            int[] inDegree = new int[nums.length + 1];
            for (int[] sequence : sequences) {
                // 注意这里是一个子序列，有多个元素，不是两个
                for (int i = 1; i < sequence.length; i++) {
                    int from = sequence[i - 1];
                    int to = sequence[i];
                    // 判断是否有次边
                    if (edge.containsKey(from) && edge.get(from).contains(to)) {
                        continue;
                    }
                    edge.putIfAbsent(from, new HashSet<>());
                    edge.get(from).add(to);
                    inDegree[to]++;
                }
            }
            // 记录入度为0的点
            Queue<Integer> queue = new ArrayDeque<>();
            for (int i = 1; i <= nums.length; i++) {
                if (inDegree[i] == 0) {
                    queue.offer(i);
                }
            }
            while (!queue.isEmpty()) {
                // 存在多个入度为 0 的点，会有多个超序列，直接返回false
                if (queue.size() > 1) {
                    return false;
                }
                int from = queue.poll();
                Set<Integer> set = edge.get(from);
                if (set == null) {
                    continue;
                }
                // 和此点连通的点入度减1
                for (int point : set) {
                    inDegree[point]--;
                    if (inDegree[point] == 0) {
                        queue.add(point);
                    }
                }
            }
            return true;
        }
    }
}
