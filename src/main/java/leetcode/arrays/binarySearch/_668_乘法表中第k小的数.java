package leetcode.arrays.binarySearch;
//几乎每一个人都用 乘法表。但是你能在乘法表中快速找到第 k 小的数字吗？
//
// 乘法表是大小为 m x n 的一个整数矩阵，其中 mat[i][j] == i * j（下标从 1 开始）。
//
// 给你三个整数 m、n 和 k，请你在大小为 m x n 的乘法表中，找出并返回第 k 小的数字。
//
//
//
//
//
//
//
// 示例 1：
//
//
//输入：m = 3, n = 3, k = 5
//输出：3
//解释：第 5 小的数字是 3 。
//
//
// 示例 2：
//
//
//输入：m = 2, n = 3, k = 6
//输出：6
//解释：第 6 小的数字是 6 。
//
//
//
//
// 提示：
//
//
// 1 <= m, n <= 3 * 10⁴
// 1 <= k <= m * n
//
//
// Related Topics 数学 二分查找 👍 331 👎 0


import leetcode.arrays._378_有序矩阵中第K小的元素;

/**
 * @author mayanwei
 * @date 2022-09-18.
 * @see _378_有序矩阵中第K小的元素
 */
public class _668_乘法表中第k小的数{
    class Solution{
        /**
         * 时间复杂度 O(mlogmn)
         * 空间复杂度 O(1)
         */
        public int findKthNumber(int m, int n, int k) {
            int left = 1, right = m * n;
            while (left < right) {
                int x = left + ((right - left) >> 1);
                int count = x / n * n;
                for (int i = x / n + 1; i <= m; i++) {
                    count += x / i;
                }
                if (count >= k) {
                    right = x;
                }
                else {
                    left = x + 1;
                }
            }
            return left;
        }
    }
}
