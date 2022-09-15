package leetcode.arrays;
//给你一个n x n矩阵matrix ，其中每行和每列元素均按升序排序，找到矩阵中第 k 小的元素。
//请注意，它是 排序后 的第 k 小元素，而不是第 k 个 不同 的元素。
//
//你必须找到一个内存复杂度优于O(n2) 的解决方案。
//
//
//
//示例 1：
//
//输入：matrix = [[1,5,9],[10,11,13],[12,13,15]], k = 8
//输出：13
//解释：矩阵中的元素为 [1,5,9,10,11,12,13,13,15]，第 8 小元素是 13
//示例 2：
//
//输入：matrix = [[-5]], k = 1
//输出：-5
//
//
//提示：
//
//n == matrix.length
//n == matrix[i].length
//1 <= n <= 300
//-109 <= matrix[i][j] <= 109
//题目数据 保证 matrix 中的所有行和列都按 非递减顺序 排列
//1 <= k <= n2
//
//
//进阶：
//
//你能否用一个恒定的内存(即 O(1) 内存复杂度)来解决这个问题?
//你能在 O(n) 的时间复杂度下解决这个问题吗?这个方法对于面试来说可能太超前了，但是你会发现阅读这篇文章（this paper）很有趣。


import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.PriorityQueue;

public class _378_有序矩阵中第K小的元素 {

    /**
     * 直接排序
     */
    class Solution0 {
        public int kthSmallest(int[][] matrix, int k) {
            int rows = matrix.length, columns = matrix[0].length;
            int[] sorted = new int[rows * columns];
            int index = 0;
            for (int[] row : matrix) {
                for (int num : row) {
                    sorted[index++] = num;
                }
            }
            Arrays.sort(sorted);
            return sorted[k - 1];
        }
    }

    /**
     * 利用最小优先级队列（最小堆）,实际上是归并
     */
    class Solution {
        public int kthSmallest(int[][] matrix, int k) {
            // 存储二元组（matrix[i][j], i, j）
            // i,j 记录当前元素的索引位置，用于生成下一个节点
            PriorityQueue<int[]> pq = new PriorityQueue<>((a, b) -> {
                // 按照元素大小升序排序
                return a[0] - b[0];
            });
            // 初始化优先级队列，把每一行的第一个元素装进去
            for (int i = 0; i < matrix.length; i++) {
                pq.offer(new int[]{matrix[i][0], i, 0});
            }
            int res = -1;
            int count = k;
            // 执行合并多个有序链表的逻辑，找到第 k 小的元素
            while (!pq.isEmpty() && count > 0) {
                int[] cur = pq.poll();
                res = cur[0];
                count--;
                // 链表中的下一个节点加入优先级队列
                int i = cur[1], j = cur[2];
                if (j + 1 < matrix[i].length) {
                    pq.offer(new int[]{matrix[i][j + 1], i, j + 1});
                }
            }
            return res;
        }
    }

    /**
     * 二分查找
     */
    class Solution2 {
        public int kthSmallest(int[][] matrix, int k) {
            int n = matrix.length;
            int left = matrix[0][0];
            int right = matrix[n - 1][n - 1];
            while (left < right) {
                int mid = left + ((right - left) >> 1);
                if (check(matrix, mid, k, n)) {
                    right = mid;
                } else {
                    left = mid + 1;
                }
            }
            return left;
        }

        public boolean check(int[][] matrix, int mid, int k, int n) {
            int i = n - 1;
            int j = 0;
            int num = 0;
            while (i >= 0 && j < n) {
                if (matrix[i][j] <= mid) {
                    num += i + 1;
                    j++;
                } else {
                    i--;
                }
            }
            return num >= k;
        }
    }


}
