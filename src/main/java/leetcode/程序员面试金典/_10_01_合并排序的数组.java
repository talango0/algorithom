package leetcode.程序员面试金典;
//给定两个排序后的数组 A 和 B，其中 A 的末端有足够的缓冲空间容纳 B。 编写一个方法，将 B 合并入 A 并排序。
//
//初始化A 和 B 的元素数量分别为m 和 n。
//
//示例:
//
//输入:
//A = [1,2,3,0,0,0], m = 3
//B = [2,5,6],       n = 3
//
//输出:[1,2,2,3,5,6]
//说明:
//
//A.length == n + m
//
//来源：力扣（LeetCode）
//链接：https://leetcode.cn/problems/sorted-merge-lcci
//著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
/**
 * @author mayanwei
 * @date 2023-05-30.
 */
public class _10_01_合并排序的数组{
    // 思路，已知数组A末端有足够的缓冲，不需要再额外分配空间，处理方法很简单，就是逐一比较A和B的中的元素，
    // 并按顺序插入数组，直至耗尽A，B中的所有元素。
    // 问题在与：如果将元素插入数组A的前段，就必须要将原有的元素往后移动，以腾出空间，更好的做法是，将元素
    // 插入A的末端将最大的元素放到数组A的末端。
    // 处理完B的剩余元素后，你不需要复制A的剩余元素，因为这些元素已经就位了。
    class Solution {
        public void merge(int[] A, int m, int[] B, int n) {
            // 数组A和B的最后元素的索引
            int indexA = m - 1, indexB = n - 1;
            // 合并后数组的最后元素索引
            int indexMerged = m + n -1;
            // 合并A和B，从这两个数组的最后一个元素开始
            while (indexB >= 0) {
                // 数组 A 最后元素 > 数组元素 B 最后的元素
                if (indexA >= 0 && A[indexA] > B[indexB]) {
                    A[indexMerged] = A[indexA]; // 复制元素
                    indexA--;
                }
                else {
                    A[indexMerged] = B[indexB];// 复制元素
                    indexB--;
                }
                indexMerged--;// 更新索引
            }
        }
    }
}
